package CoreFramework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class TextFileReader {
	private static Logger log = Logger.getLogger(TextFileReader.class.getName());

	public static ArrayList<String> returnlines(String filepathname) {
		// filepathname = filepathname.replace("\\","/");
		ArrayList<String> lines = new ArrayList<String>();
		try {
			BufferedReader buffer = new BufferedReader(new FileReader(filepathname));
			String line;
			while ((line = buffer.readLine()) != null) {
				lines.add(line);
			}
			buffer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static void removeLineFromFile(String filepathname, String lineToRemove) {

		try {
			// filepathname = filepathname.replace("\\","/");
			File inFile = new File(filepathname);
			if (!inFile.isFile()) {
				log.error(filepathname + "is not an existing file.");
				return;
			}

			// Construct the new file that will later be renamed to the original
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(filepathname));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			// Read from the original file and write to the new
			// unless content matches data to be removed.
			while ((line = br.readLine()) != null) {
				if (!line.trim().toLowerCase().contains(lineToRemove.trim().toLowerCase())) {
					pw.println(line);
					pw.flush();
				} else {
					log.info("Nothing found in file.");
				}
			}

			pw.close();
			br.close();

			// Delete the original file
			if (!inFile.delete()) {
				log.error("Could not delete file" + inFile.getName());
				return;
			}

			// Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile)) {
				log.error("Could not rename file" + inFile.getName());
			}
		} catch (FileNotFoundException ex) {
			log.error("There is error when reading file: " + ex.getMessage());
		} catch (IOException ex) {
			log.error("There is error when reading file: " + ex.getMessage());
		}
	}

	public void appendLineToFile(String filepath, String line) {
		BufferedWriter writer = null;
		try {
			File logFile = new File(filepath);
			// writer = new BufferedWriter(new FileWriter(logFile));
			writer = new BufferedWriter(new FileWriter(logFile, true));
			writer.write(line);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				log.error("There is error when writing file: " + e.getMessage());
			}
		}

	}

	public void overwriteLineToFile(String filepath, String line) {
		BufferedWriter writer = null;
		try {
			File logFile = new File(filepath);
			writer = new BufferedWriter(new FileWriter(logFile));
			writer.write(line);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				log.error("There is error when writing file: " + e.getMessage());
			}
		}

	}

}
