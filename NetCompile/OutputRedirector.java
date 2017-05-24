import java.io.PrintStream;
import java.io.File;
import java.lang.reflect.Method;

public class OutputRedirector
{
   /* args[0] - class to launch, args[1]/args[2] file to direct System.out/System.err to */
   public static void main(String[] args) throws Exception
   {  // error checking omitted for brevity
      System.setOut(outputFile(args[1]));
      System.setErr(outputFile(args[2]));
      Class app = Class.forName(args[0]);
      Method main = app.getDeclaredMethod("main", new Class[] { (new String[1]).getClass()});
      String[] appArgs = new String[args.length-3];
      System.arraycopy(args, 3, appArgs, 0, appArgs.length);
      main.invoke(null, appArgs);
   }
   protected static PrintStream outputFile(String name) {
       return new PrintStream(new BufferedOutputStream(new FileOutputStream(name)));
   }
}