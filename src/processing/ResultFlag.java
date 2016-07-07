package processing;

public class ResultFlag {

	String filename;
	String workerID;
	int line;
	String failureType;
	
	public ResultFlag(String workerID, int line, String failureType, String file) {
		this.workerID = workerID;
		this.line = line;
		this.failureType = failureType;
		this.filename = file;
	}
	
	public String toString(){
		String line_num = Integer.toString(line + 2);
		return "ERROR in " + filename.toString() + ": " + workerID + " failed check: " + failureType + " line#" + line_num;
		
	}

}
