package kernel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import kernel._Shifter;

public class JavaShifter implements _Shifter {

	private final Path subFilePath;   //path (whith name) of the srt file
	private final Path newPath;   //path (whith name) of the resulting file
	private final int offset;   //offset required by the user.


	public JavaShifter(String subFilePath, int offset, String newPath) {
		this.subFilePath = Paths.get(subFilePath);
		this.offset = offset;
		this.newPath = Paths.get(newPath);
	}



	private String shiftTime(String time) {
	  String [] tableOfTimes = time.split(":|,");
	  //my @t1 = split(/:|,/, $time);

	  int [] fields = new int[tableOfTimes.length];
	  for(int i = tableOfTimes.length-1 ; i>=0 ; i--) {
		  fields[i] = Integer.parseInt(tableOfTimes[i]);
	  }

	  //total in ms
	  int total = fields[0]*3600000 + fields[1]*60000 + fields[2]*1000 + fields[3] + this.offset;

	  if(total < 0 || total > 86400000) { //less than 0ms, or more than a day
	    System.out.print("AAAARRRGHHHHHH What did you do to me??\n(tried to shift a subtitle too much)\n");
	  }


	  int HH = total/3600000;
	  int mm = total%3600000/60000;
	  int ss = total%3600000%60000/1000;
	  int mss = total%3600000%60000%1000;

	  return String.format("%02d:%02d:%02d,%03d", HH, mm, ss, mss);
	}

	private String shiftLine(String line) {
	  String [] t = line.split(" --> ");
	  t[0] = shiftTime(t[0]);
	  t[1] = shiftTime(t[1]);

	  return t[0] + " --> " + t[1];
	}


	@Override
	public int shift() {

		List<String> lines;
		try {
			lines = Files.readAllLines(subFilePath, StandardCharsets.ISO_8859_1);
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		for(int i = lines.size()-1 ; i>=0 ; i--) {
			String line = lines.get(i);
			if(isTimeLine(line)) {
	    	  line = shiftLine(line);
	    	  lines.set(i, line);
			}
			//resultingFile += line +"\n";

		}

		try {
			Files.write(newPath, lines);
		} catch (IOException e) {
			e.printStackTrace();
			return 2;
		}

		return 0;
	}



	private boolean isTimeLine(String line) {
		return line.matches("..:..:..,... --> ..:..:..,...");
	}

}
