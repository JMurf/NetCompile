import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Arrays;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

public class FileHandler implements ActionListener
{
   int initialDelay;
   int interval;   
   Timer delayTimer;
   List<Path> dirsModded;
   Map<String, Long> fileList;
   SimpleDateFormat sdf;
   String[] extensionList = {"java"};
      
   public FileHandler(int interval)
   {
      dirsModded = new ArrayList<Path>();
      fileList = new HashMap<>();
      
      delayTimer = new Timer(interval, this);
      sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
   }
   public FileHandler()
   {
      this(2000);
   }   
   public void actionPerformed(ActionEvent ae)
   {
      System.out.println( "Tick!" );
      delayTimer.stop();

      for( Iterator<Path> iterator = dirsModded.iterator(); iterator.hasNext(); )
      {
         Path p = iterator.next();
         
         System.out.println( "UPLOADING FILES FROM: " + p );
         
         File[] fs = p.toFile().listFiles(); 
         if( fs == null || fs.length == 0 ) continue;
         ArrayList<File> files = 
            new ArrayList<File>(Arrays.asList(fs));  
            
         System.out.println( "Files: " );
         for( File f: files )
         {
            System.out.println( f.getPath() + ", " + f.lastModified() + 
               "\n" + sdf.format(f.lastModified()));
            String fName = f.getName();
            boolean valid = false;
            for( int i=0; i<extensionList.length; i++ ) 
            {
               if( fName.contains(extensionList[i]) ) valid=true;
            }            
            if( !valid ) continue;
            Long l = fileList.get(f.getPath());
            System.out.println( "***  " + fileList.get(f.getPath()) + ", " + f.lastModified() );
            if( l == null || !l.equals(f.lastModified()) )
            {
               System.out.print( f.getPath() + " --- ");
               System.out.println( f.getName() );
               fileList.put(f.getPath(), f.lastModified());
               try
               {
                  FileHelper.postReq(f);
               }
               catch( Exception e )
               {
                  System.out.println( "exception: " + e.toString() );
               }  
               System.out.println( "Back from post!" );
            }
         }   
         iterator.remove();         
      }
   }
   
   public void startTimer()
   {
      delayTimer.restart();
   }
   
   public void directoryModified( Path path )
   {
      System.out.println( "Directory Modified!" );
      try
      {
         System.out.println( path.toRealPath() );
      }
      catch(IOException ioe)
      {
         return;
      }      
      delayTimer.stop();
      if( dirsModded.contains(path) == false )
      {
         dirsModded.add(path);
      }
      delayTimer.restart();  //reset regardless of whether or not this is a new directory
   }

   public void initializeWatchFiles(Path initialDir) throws IOException 
   {
        Files.walkFileTree(initialDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes attrs)
                throws IOException
            {
                System.out.println(path);
                File dir = path.toFile();
                if( dir.isDirectory() )
                {
                     ArrayList<File> files = 
                        new ArrayList<File>(Arrays.asList(dir.listFiles()));  
                     for( int i=0; i<files.size(); i++ ) 
                     {
                        File f = files.get(i);
                        String extension = "";

                        int p = f.getName().lastIndexOf('.');
                        if (p > 0) {
                           extension = f.getName().substring(p+1);
                        }
                        if( extension.equals("java") )
                           fileList.put(files.get(i).getPath(), files.get(i).lastModified());              
                     }
                }
                return FileVisitResult.CONTINUE;
            }
        });      

         System.out.println( "Size of fileList: " + fileList.size() );
         System.out.println( "Here are the initial files: \n" );
         
         
         Set<Map.Entry<String, Long>> set = fileList.entrySet();

         for (Map.Entry<String, Long> me : set) 
         {
            System.out.println("Key :" + me.getKey() + " Value : " + me.getValue() + 
            "\nSimplified Date = " + sdf.format(me.getValue()));
         }
         System.out.println( "Size of fileList: " + fileList.size() );
   }
}