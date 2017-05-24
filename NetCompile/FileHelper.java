
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.util.Map;
import java.io.DataOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;

public class FileHelper
{
   public static void postReq(File f) throws Exception
   {
      String url = "http://danieloluwadare.com/write.php";
      url = "http://ec2-52-15-200-194.us-east-2.compute.amazonaws.com/write.php";
      String charset = java.nio.charset.StandardCharsets.UTF_8.name();

      HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
      String fileName = f.getName();
      fileName = fileName.substring(0, fileName.indexOf("."));
      Scanner inp = new Scanner(f);
      String content = ""; 
      while( inp.hasNext() )
      {
         content += inp.nextLine() + "\n";
      }
      String newContent = "";
      for( int i=0; i<content.length(); i++ ) 
      {
         if( content.charAt(i) != '"' && content.charAt(i) != '+') 
            newContent += content.charAt(i);
         else
            newContent += "\\" + content.charAt(i);
      }
      /*********************/
      /*
      school = "neiu";
      dept = "304-17";
      semester = "spr2017";
      test = "midterm";
      studentId = "265456";      
      */      

      
      String urlParameters  = "classID=" + MainApp.classID + "&semester=" + MainApp.semester + 
                     "&testDesc=" + MainApp.testDesc + "&studentID=" + MainApp.studentID + 
                     "&school=" + MainApp.school + "&fileName=" + fileName + 
                     "&content=" + content;
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
            sb.append(line + "\n");  
         }
         br.close();
         System.out.println("" + sb.toString());  
      }
      else 
      {
         System.out.println(conn.getResponseMessage());  
      }        
   }
}
