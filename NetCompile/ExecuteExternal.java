/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package executeexternal;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.Properties;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
/**
 *
 * @author jonathanmurfey
 */
public class ExecuteExternal 
{
    /**
     * @param args the command line arguments
     */

   public static void main(String[] args) throws IOException, InterruptedException
   {

      // Store console print stream.
      PrintStream conStream = System.out;

      // file output stream
      File file = new File("file.txt");
      FileOutputStream fos = new FileOutputStream(file, true);

      //Properties props;
      //props = System.getProperties();
      
      // Create new print stream for file.
      PrintStream fileStream = new PrintStream(fos, true);
      //props.list(ps);

      System.setOut(fileStream);
      System.out.println("Print in the file !!");
      ProcessBuilder p = new ProcessBuilder().inheritIO();
      p.redirectErrorStream(true);
      
      p.command("javac", "Test.java");
      File fil = new File("test.txt");
	   p.redirectOutput(fil);
      Process process = p.start();
      
      p.command("java", "Test");
	   process = p.start();
      process.waitFor();
      //System.out.println( "Done: " + process.waitFor() );
      // Set console print stream.
      //System.setOut(ps_console);
      //System.out.println("Console again !!");
   }
}
