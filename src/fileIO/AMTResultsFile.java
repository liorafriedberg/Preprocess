package fileIO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import processing.StringUtils;

public class AMTResultsFile {

	private final List<String> headings = AMTHeadings();
	private List<String> lines;
	
	public boolean write(File filename){
		try {
			FileWriter out;
			out = new FileWriter(filename);
			//Write the header.
			out.write(String.join(",", this.headings) + "\n");
			out.flush();
			
			//Write all lines.
			for (String line : this.lines){
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

	public AMTResultsFile() {
		this.lines = new ArrayList<String>();
	}
	
	public AMTResultsFile(List<String> lines) {
		this.lines = lines;
	}
	
	public String[] getLine(Integer line){
		return lines.get(line).split(",");
	}

	public List<String> getAllLines() {
		return lines;
	}

	public void addLine(String line) {
		String[] fields = line.split(",");
		if (fields.length == 32){
			lines.add(line);
		}else
		{
			System.err.println("Line does not conform to AMT results file schema.");
		}
	}
	
	public void replaceLine(Integer line, String text){
		lines.add(line,text);
	}

	public static boolean validate(File filename) {
		CSVFile toValidate = CSVFile.read(filename.toString());

		if (StringUtils.stringArrayContainsAll(AMTResultsFile.AMTHeadings().toArray(new String[0]),
				toValidate.getHeader())) {
			return true;
		}

		// If we got here, the header row is different, the file is corrupt, or
		// not an AMT results file.
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
