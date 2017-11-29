package kernel;

import kernel._Shifter;

public class JavaShifter implements _Shifter {

	private final String subFilePath;   //path (whith name) of the srt file
	private final String newPath;   //path (whith name) of the resulting file
	private final int offset;   //offset required by the user.


	public JavaShifter(String subFilePath, int offset, String newPath) {
		this.subFilePath = subFilePath;
		this.offset = offset;
		this.newPath = newPath;
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

	  return String.format("%#.2d:%#.2d:%#.2d,%#.3d", HH, mm, ss, mss);
	}

	@Override
	public int shift() {
		// TODO Auto-generated method stub
		return 0;
	}

}
