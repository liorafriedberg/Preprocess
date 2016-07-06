import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class CSVFile {

	private List<String> columns;
	private File myFile;
	private List<String> lines;

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
		lines = new ArrayList<String>();

			try {
				lines.addAll(Files.readAllLines(myFile.toPath(), StandardCharsets.ISO_8859_1));
			} catch (MalformedInputException e) {
				System.err.println("Double check the character encoding for the input file.");
			}
		

		String[] column_names = lines.get(0).split(",");

		for (String col : column_names) {
			columns.add(col);
		}
	}

	/**
	 * @param filename The path and filename to the file. Should work on all platforms. Relative paths supported.
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
		} catch (Exception e){
			System.err.println("Something's broken!");
			e.printStackTrace();
		}

		// If we failed to read the file, returns null.
		return null;
	}

	/**
	 * @return Returns the number of value rows (number of rows in the CSV file, minus the header row).
	 */
	public int size() {
		// Remove 1, because we don't count the header row.
		return lines.size() - 1;
	}

	/**
	 * @param column_name The name of the column you want to retrieve. Column names are specified in the header row of the CSV file. Only the first instance of the column name specified will be returned. 
	 * @param row_number Row number that you want to retrieve. Zero indexed (0 represents the first row of values, not the row header).
	 * @return Returns the value at the corresponding row, or column. Null if the column or row wasn't found.
	 */
	public String getField(String column_name, int row_number){
		int whichColumn = columns.indexOf(column_name);
		
		if (whichColumn == -1)
			return null;
		
		//Add one to skip the header row.
		String value;
		try {
			value = lines.get(row_number + 1).split(",")[whichColumn];
		} catch (Exception e) {
			value = null;
		}
		
		return value;
	}

}
