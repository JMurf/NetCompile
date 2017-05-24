/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testscene;

import io.FileHelper;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import java.util.Date;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 *
 * @author Handydanny
 */
public class TestSceneController implements Initializable {
      
    @FXML
    private WebView problemView, wvTest;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnStart,btnRun;
    @FXML
    private TextField student_id,school;
    @FXML 
    private TextArea code_output;
    @FXML
    private Label test,date,time;
    @FXML
    TabPane problem_tabs;

    public String getSchool(){
        String s = school.getText();
        if( s == null || s.length() == 0 ) return "NEIU";
        return s;
    }
    public String getStudentId() {
        String s = student_id.getText();
        if( s == null || s.length() == 0 ) return "632629";
        return s;
    }
    public String getTest() {
        String s = test.getText();
        if( s == null || s.length() == 0 ) return "FINAL";
        return s;
    }
    public String getSemester() {
        //String s = semester.getText();
        //if( s == null || s.length() == 0 ) return "NEIU";
        return "Spr2016";
    }
    public String getCourse() {
        return "324-02";
    }
    public String getProblemName() {
        return problem_tabs.getSelectionModel().getSelectedItem().getText();
    }
    public String getCode() {
        WebView w =(WebView) problem_tabs.getSelectionModel().getSelectedItem().getContent(); 
        String code = (String)w.getEngine().executeScript("editor.getValue()");    
        return code;
    }
    
    private static final String URL = "http://localhost/danielOluwadare.com/";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        test.setVisible(false);
        date.setVisible(false);
        time.setVisible(false);
        code_output.setVisible(false);
        btnRun.setVisible(false);
        time.setText("test time");
        problem_tabs.setVisible(false);
        
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, ''yy");
        String sd=sdf.format(d);
        date.setText(sd);
        //startTimer();
        //problemView.getEngine().loadContent("<h1>HELLO WORLD<h1>");
    }  

    public void onClick() throws IOException{
        String id = student_id.getText();
        if( id == null || id.length() == 0 ) id = "629632";
        String sch = school.getText();
        if( sch == null || sch.length() == 0 ) sch = "NEIU";
        
        String course = "324-02";
        String semester = "Spr2016";
        String curTest = "FINAL";
        
        if(!id.isEmpty() && !sch.isEmpty()){
            student_id.setEditable(false);
            school.setEditable(false);
            btnStart.setDisable(true);
            getProblemList(id,sch,course, semester, curTest);
        }
        code_output.setVisible(true);
        btnRun.setVisible(true);
    }
    
    public void setTabs(String []fileNames, String dir) throws IOException{
       //get file contents to set web view
        //test.setText(jsonArray[jsonArray.length-1].replace("\"", "")); 

        //set test type by getting last element in array    
         for (int i =0 ; i <fileNames.length; i++) {
            Tab tab = new Tab();
            String s = fileNames[i].substring(1,fileNames[i].indexOf(".")) ;
            tab.setText(s);
            WebView w = new WebView();
            String file = dir + "/" + s + ".html";
            w.getEngine().load( file );
            problem_tabs.getTabs().add(tab);
            problem_tabs.getTabs().get(i).setContent(w);
        }       
        problem_tabs.setVisible(true);
        //test.setVisible(true);
        //date.setVisible(true);
        //time.setVisible(true);
    }
    
    private void getProblemList(String id,String sch, String course, String semester, String test) throws IOException {
        String postParams = "studentId="+id;
        postParams += "&school=" + sch;
        postParams += "&course=" + course;
        postParams += "&semester=" + semester;
        postParams += "&test=" + test;
                
        String[] probList;
                
	URL url = new URL(URL + "getProblemList.php");
	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	con.setRequestMethod("POST");
	//con.setRequestProperty("User-Agent", USER_AGENT);

	// For POST only - START
	con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
	os.write(postParams.getBytes());
	os.flush();
	os.close();
	// For POST only - END

	int responseCode = con.getResponseCode();
	//System.out.println("POST Response Code :: " + responseCode);

	if (responseCode == HttpURLConnection.HTTP_OK) { //success
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
	
            in.close();
            String res = response.toString();
            res = res.replaceAll("\\[", "").replaceAll("\\]","");
            probList = res.split(",", -1);
            // print result
            String dir = URL + "data/" + sch + "/" + course + "/" + semester + "/" + test + "/$admin";
            setTabs(probList, dir);
	} else {
            System.out.println("POST request not worked");
	}
    }
  
    public void showTabPane(){
        //anchorPane.setBottomAnchor(problem_tabs, 2.0);
        problem_tabs.setVisible(true);
        code_output.setVisible(true);
        btnRun.setVisible(true);
    }
    
    public String getProblems(String postParams)throws IOException{
        
        String retString="";
         

        URL obj = new URL(URL + "getProblems.php");
	HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	con.setRequestMethod("POST");
		//con.setRequestProperty("User-Agent", USER_AGENT);

		// For POST only - START
	con.setDoOutput(true);
	OutputStream os = con.getOutputStream();
	os.write(postParams.getBytes());
	os.flush();
	os.close();
		// For POST only - END

	int responseCode = con.getResponseCode();
	//System.out.println("POST Response Code :: " + responseCode);

	if (responseCode == HttpURLConnection.HTTP_OK) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
            }
            in.close();
            retString = response.toString();
	} else {
            System.out.println("POST request not worked");
	}
        return retString;
    }
    
    public void saveCompile() throws Exception {       
        WebView w =(WebView) problem_tabs.getSelectionModel().getSelectedItem().getContent(); 
        String code = (String)w.getEngine().executeScript("editor.getValue()");
        System.out.println(code);
        code_output.setText("SUBMITTING...\n-------------------------------------\n");
        String[] ret = FileHelper.uploadEditorContent(this);
        for( int i=0; i<ret.length; i++ ) {
            if( ret[i].length() == 0 ) continue;
            if( ret[i].charAt(0) == '\"' ) {
                ret[i] = ret[i].substring(1);
            }
            if( ret[i].charAt(ret[i].length()-1) == '\"' ) {
                ret[i] = ret[i].substring(0, ret[i].length()-1);
            }
            //    ret[i] = ret[i].substring(1, ret[i].length()-1);
            code_output.setText(code_output.getText() + ret[i] + "\n");
        }
        //code_output.setText(code_output.getText() + "-------------------------------------\n");
    }
}
