import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

public class FileWorker {
	public static String FILENAME = "database.txt";

	public static void readFile(DataTable dataTable) {
		try {
			File f = new File(FileWorker.FILENAME);

			if (!f.exists()) {
				f.createNewFile();
			} else {
				try (BufferedReader br = new BufferedReader(new FileReader(FileWorker.FILENAME))) {
					String line;
					while ((line = br.readLine()) != null) {
						String sessionID = line.split(",")[0];
						String today = line.split(",")[1];
						String round = line.split(",")[2];
						String action = line.split(",")[3];
						String currWatchTimestamp = line.split(",")[4];
						String diff = line.split(",")[5];

						String orgasmType = "";
						if (line.split(",").length == 7) {
							orgasmType = line.split(",")[6];
						}
						dataTable.initTable(sessionID, today, round, action, currWatchTimestamp, diff, orgasmType);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeToFile(String row) {
		List<String> lines = Arrays.asList(row);
		Path file = Paths.get(FileWorker.FILENAME);

		try {
			Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reset(GUI ui) {
		File f = new File(FileWorker.FILENAME);

		if (f.exists()) {
			f.delete();
		}
		
		ui.kill();		
		EdgeTimer.start();
	}
}
