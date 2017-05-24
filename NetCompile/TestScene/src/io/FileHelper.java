package io;

import java.util.Scanner;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import testscene.TestSceneController;

public class FileHelper
{
   public static String[] uploadEditorContent(TestSceneController controller) throws Exception
   {
      String url = "http://danieloluwadare.com/write.php";
      url = "http://ec2-52-14-237-83.us-east-2.compute.amazonaws.com/write.php";
      //local
      url = "http://localhost/danielOluwadare.com/write.php";
      
      String charset = java.nio.charset.StandardCharsets.UTF_8.name();

      HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
      /*********************/
      /*
      school = "neiu";
      dept = "304-17";
      semester = "spr2017";
      test = "midterm";
      studentId = "265456";      
      */      

      String content = controller.getCode();
      String newContent = "";
      for( int i=0; i<content.length(); i++ ) 
      {
         if( content.charAt(i) == '+' ) 
            newContent += "%2B";
         else if( content.charAt(i) == '\t')
             newContent += "    ";
         else
            newContent += content.charAt(i);
      }

      String urlParameters  = "classID=" + controller.getCourse() + "&semester=" + controller.getSemester() + 
                     "&testDesc=" + controller.getTest() + "&studentID=" + controller.getStudentId() + 
                     "&school=" + controller.getSchool() + "&fileName=" + controller.getProblemName() + 
                     "&content=" + newContent;
      byte[] postData       = urlParameters.getBytes( java.nio.charset.StandardCharsets.UTF_8 );
      int    postDataLength = postData.length;
      String request        = url;
      URL    url2            = new URL( request );
      HttpURLConnection conn= (HttpURLConnection) url2.openConnection();           
      conn.setDoOutput( true );
      conn.setInstanceFollowRedirects( false );
      conn.setRequestMethod( "POST" );
      conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
      conn.setRequestProperty( "charset", "utf-8");
      conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
      conn.setUseCaches( false );
      try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
         wr.write( postData );
      }
      
      StringBuilder sb = new StringBuilder();  
      int HttpResult = conn.getResponseCode(); 
      if (HttpResult == HttpURLConnection.HTTP_OK) 
      {
         BufferedReader br = new BufferedReader(
            new InputStreamReader(conn.getInputStream(), "utf-8"));
         String line = null;  
         while ((line = br.readLine()) != null) 
         {  
            sb.append(line);  
         }
         br.close();
            
         String res = sb.toString();
         System.out.println(res);  

         res = res.replaceAll("\\[", "").replaceAll("\\]","");
         String[] retList = res.split(",", -1);
         
         return retList;
      }
      else 
      {
         System.out.println(conn.getResponseMessage());  
         return new String[0];
      }        
   }
   public static String[] uploadTest() throws Exception
   {
       String s = "1 + 1 = 2";
       return null;
   }
}
