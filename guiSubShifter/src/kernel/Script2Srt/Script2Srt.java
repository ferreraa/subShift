package kernel.Script2Srt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author alexandre
 *
 * Create srt files out of the txt files in the source directory
 */
public class Script2Srt {

	private String sourceDirectoryPath;
//	private String destinationDirectoryPath;

	public Script2Srt(String source) {
		this.sourceDirectoryPath = source;
	//	this.destinationDirectoryPath = dest;
	}

	public int turnAll() {
//		Path sourcePath = Paths.get(sourceDirectoryPath);
//		List<File> fileList = Files.wal

		try (Stream<Path> paths = Files.walk(Paths.get(sourceDirectoryPath))) {
		    paths
		        .filter(Files::isRegularFile)
		        .forEach(this::turnOne);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}
		return 0;
	}



	public void turnOne(Path path) {
		List<String> result = new LinkedList<String>();

		String destString = path.toString();
		destString = destString.substring(0,destString.lastIndexOf("."));
		Path dest = Paths.get(destString + ".srt");


		try (Stream<String> lines = Files.lines(path, StandardCharsets.ISO_8859_1)) {
		    lines
		        //.filter(Files::isRegularFile)
		        .forEach(line -> {processLine(result,line);});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Files.write(dest, result);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(dest.toString() + " created");
	}


	private void processLine(List<String> list, String line) {

		if(line.matches(".*\\(ensemble\\).+") && list.size() > 0) {
			if(list.get(list.size()-1).matches(".*\\(ensemble\\).+")) {
				list.add(line);
				return;
			}
		}
		if(list.size() > 0)
			list.add("");
		list.add("00:00:00,000 --> 00:00:00,000");
		list.add(line);
	}

	public static void main(String[] args) {
		new Script2Srt("../../donjon-texte/").turnAll();
	}

}
