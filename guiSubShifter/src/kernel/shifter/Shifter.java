package kernel.shifter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Shifter implements _Shifter {

	private static String script = "ressources/subShifter2.0";  //path of the perlScript

	private final String subFilePath;   //path (whith name) of the srt file

	private final String newPath;   //path (whith name) of the resulting file

	private final long offset;   //offset required by the user.

	private static boolean scriptUnzipped = false;	//indicates if the script was already unzipped or not.

	public Shifter(String subFilePath, long offset, String newPath) {
		this.subFilePath = subFilePath;
		this.offset = offset;
		this.newPath = newPath;
	}


	public int shift() {

		try {
			return execCommand(true, script, subFilePath, ""+offset, newPath);
		} catch (IOException e) {
			e.printStackTrace();
			return 256;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 259;
		}

	}

	public static void unzipScript() throws IOException, InterruptedException {

		if(scriptUnzipped) {
			System.err.println("unzipScript should be used only once. This is the second time so it won't have any effect.");
			return;
		} else if (new File(script).exists()) {
			System.out.println("no need to try and unzip the script, we can already access the script from here: "+script);
			scriptUnzipped = true;
			return;
		}

		Path tempPath = Files.createTempDirectory("");

		execCommand("unzip", "-d", tempPath.toString(), "subShifter.jar", script);
		script = tempPath.toString() + "/" + script;

		execCommand("chmod", "a+x", script);

		scriptUnzipped = true;
	}

	public static int shift(String srcPath, long offset, String destPath) {
		if(!scriptUnzipped)
			try {
				unzipScript();
			} catch (IOException e) {
				e.printStackTrace();
				return 257;
			} catch (InterruptedException e) {
				e.printStackTrace();
				return 258;
			}

		return (new Shifter(srcPath, offset, destPath)).shift();
	}


	public static int execCommand(boolean printOutput, String...command) throws IOException, InterruptedException {
		Process p = new ProcessBuilder(command).start();
		BufferedReader reader =
				new BufferedReader(new InputStreamReader(p.getInputStream()));

		int exitValue = p.waitFor();

		String line = "";

		StringBuffer output = new StringBuffer();

		while ((line = reader.readLine())!= null) {
			output.append(line + "\n");
		}

		if(printOutput) {
			System.out.println(output.toString());
		}

		return exitValue;
	}

	public static int execCommand(String...command) throws IOException, InterruptedException {
		return execCommand(false,command);
	}
}
