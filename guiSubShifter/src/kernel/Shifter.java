package kernel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Shifter implements _Shifter {

  private final String script = "ressources/subShifter";  //path of the perlScript
  
  private final String subFilePath;   //path (whith name) of the srt file

  private final String newPath;   //path (whith name) of the resulting file

  private final long offset;   //offset required by the user.

  public Shifter(String subFilePath, long offset, String newPath) {
	  this.subFilePath = subFilePath;
	  this.offset = offset;
	  this.newPath = newPath;
  }
  
  
  public int shift() {
    StringBuffer output = new StringBuffer();

    String [] command = {script, subFilePath, ""+offset, newPath};

    Process p;
    try {
      p = Runtime.getRuntime().exec(command);
      p.waitFor();
      BufferedReader reader =
                            new BufferedReader(new InputStreamReader(p.getInputStream()));

                        String line = "";
      while ((line = reader.readLine())!= null) {
        output.append(line + "\n");
      }

      
      System.out.println(output.toString());

      return p.exitValue();

    } catch (Exception e) {
      e.printStackTrace();
      return 255;
    }

  }   
}
