package fileIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

import processing.StringUtils;

public class AMTResultsFile {

	private final List<String> headings = AMTHeadings();
	private List<String[]> lines;

	public int length() {
		return lines.size();
	}

	public boolean write(File filename) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(filename), ',');
			// Write the header.
			String[] header = this.headings.toArray(new String[0]);
			writer.writeNext(header);

			// Write all lines.
			for (String[] line : this.lines) {
				writer.writeNext(line);
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public AMTResultsFile() {
		this.lines = new ArrayList<String[]>();
	}

	public AMTResultsFile(List<String[]> lines) {
		this.lines = lines;
	}

	public String[] getLine(Integer line) {
		String[] outLine = this.lines.get(line);
		ArrayList<String> l = new ArrayList<String>();
		for (String s : outLine){
			l.add(s);
		}
		//If the line has less than 32 entries, pad it with blank entries on the right.
		while(l.size() < 32){
			l.add("");
		}
		
		return l.toArray(new String[0]);
	}

	public List<String[]> getAllLines() {
		return lines;
	}

	public void addLine(String[] line) {
		if (line.length >= 30 && line.length <= 32) {
			lines.add(line);
		} else {
			System.err.println("Line does not conform to AMT results file schema.");
		}
	}

	public void replaceLine(Integer line, String[] data) {
		if (line > lines.size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		lines.add(line, data);
		lines.remove(line + 1);
	}

	public static boolean validate(File filename) {
		CSVFile toValidate = CSVFile.read(filename.toString());
		System.err.println("Validating AMT Results File...");
		if (StringUtils.stringArrayContainsAll(AMTResultsFile.AMTHeadings().toArray(new String[0]),
				toValidate.getHeader())) {
			System.err.println("Validation success.");
			return true;
		}

		// If we got here, the header row is different, the file is corrupt, or
		// not an AMT results file.
		System.err.println("Validation failed.");
		return false;
	}

	public static ArrayList<String> AMTHeadings() {
		ArrayList<String> truth = new ArrayList<String>();
		truth.add("HITId");
		truth.add("HITTypeId");
		truth.add("Title");
		truth.add("Description");
		truth.add("Keywords");
		truth.add("Reward");
		truth.add("CreationTime");
		truth.add("MaxAssignments");
		truth.add("RequesterAnnotation");
		truth.add("AssignmentDurationInSeconds");
		truth.add("AutoApprovalDelayInSeconds");
		truth.add("Expiration");
		truth.add("NumberOfSimilarHITs");
		truth.add("LifetimeInSeconds");
		truth.add("AssignmentId");
		truth.add("WorkerId");
		truth.add("AssignmentStatus");
		truth.add("AcceptTime");
		truth.add("SubmitTime");
		truth.add("AutoApprovalTime");
		truth.add("ApprovalTime");
		truth.add("RejectionTime");
		truth.add("RequesterFeedback");
		truth.add("WorkTimeInSeconds");
		truth.add("LifetimeApprovalRate");
		truth.add("Last30DaysApprovalRate");
		truth.add("Last7DaysApprovalRate");
		truth.add("Input.cond");
		truth.add("Answer.Comments");
		truth.add("Answer.surveycode");
		truth.add("Approve");
		truth.add("Reject");
		return truth;
	}

}
