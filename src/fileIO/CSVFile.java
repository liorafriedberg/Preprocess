package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

import processing.StringUtils;

public class CSVFile {

	private List<String> columns;
	private File myFile;
	private List<String[]> lines;

	/**
	 * Private constructor. Loads the contents of the CSV file into memory, and
	 * creates a list of the columns.
	 * 
	 * @param filename
	 *            The path and filename to the file.
	 * @throws IOException
	 *             This will get thrown if the filename can't be read.
	 * @throws FileNotFoundException
	 *             This will get thrown if the filename doesn't exist.
	 */
	private CSVFile(String filename) throws IOException, FileNotFoundException {
		columns = new ArrayList<String>();
		myFile = new File(filename);
		lines = new ArrayList<String[]>();

		CSVReader reader = new CSVReader(new FileReader(filename));
		String[] nextLine;

		while ((nextLine = reader.readNext()) != null) {
			// nextLine[] is an array of values from the line
			
			ArrayList<String> processedLine = new ArrayList<String>();
			for (String element : nextLine){
				//Strip out all non-ASCII characters.
				processedLine.add(element.replaceAll("[^\\x00-\\x7F]", ""));
			}
			String[] result = processedLine.toArray(new String[0]); 
			lines.add(result);
		}

		String[] column_names = lines.get(0);

		for (String col : column_names) {
			columns.add(col);
		}

		// Remove the header row from the lines.
		lines.remove(0);
		reader.close();
	}

	/**
	 * @param filename
	 *            The path and filename to the file. Should work on all
	 *            platforms. Relative paths supported.
	 * @return Returns a CSVFile object.
	 */
	public static CSVFile read(String filename) {
		try {
			return new CSVFile(filename);
		} catch (FileNotFoundException e) {
			System.err.println("File not found: " + filename);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Something's broken!");
			e.printStackTrace();
		}

		// If we failed to read the file, returns null.
		return null;
	}

	public static boolean write(List<String> header, List<String> lines, File filename) {
		// TODO: pull this method from AMTResultsFile
		try {
			FileWriter out;
			out = new FileWriter(filename);
			// Write the header.
			out.write(String.join(",", header) + "\n");
			out.flush();

			// Write all lines.
			for (String line : lines) {
				out.write(line + "\n");
				out.flush();
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * @return Returns the number of value rows (number of rows in the CSV file,
	 *         minus the header row).
	 */
	public int size() {
		return lines.size();
	}

	public String getFilename() {
		return myFile.toString();
	}

	public String[] getHeader() {
		return columns.toArray(new String[0]);
	}

	public String[] getLine(int line) {
		return lines.get(line);
	}

	public List<String[]> getAllLines() {
		return lines;
	}
	
	/**
	 * @param column_name
	 *            The strings or substrings that the column name must contain.
	 * @param row_number
	 *            Row number that you want to retrieve. Zero indexed (0
	 *            represents the first row of values, not the row header).
	 * @return Returns the value at the corresponding row, or column. Null if
	 *         the column or row wasn't found.
	 */
	public String getField(String[] column_name, int row_number) {
		int whichColumn = StringUtils.findWhichStringContainsSubstrings(columns.toArray(new String[0]), column_name);		
		
		if (whichColumn == -1)
			return null;

		String value;

		value = lines.get(row_number)[whichColumn];

		return value;
	}

	/**
	 * @param column_name
	 *            The name of the column you want to retrieve. Column names are
	 *            specified in the header row of the CSV file. Only the first
	 *            instance of the column name specified will be returned. Don't
	 *            worry about quotes, commas, or any other special characters,
	 *            any regular string will do.
	 * @param row_number
	 *            Row number that you want to retrieve. Zero indexed (0
	 *            represents the first row of values, not the row header).
	 * @return Returns the value at the corresponding row, or column. Null if
	 *         the column or row wasn't found.
	 */
	public String getField(String column_name, int row_number) {
		int whichColumn = columns.indexOf(column_name);
		

		if (whichColumn == -1)
			return null;

		String value;

		value = lines.get(row_number)[whichColumn];

		return value;
	}

}
