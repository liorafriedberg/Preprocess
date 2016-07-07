package processing;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import fileIO.AMTResultsFile;
import fileIO.CSVFile;

public class ResultsProcessor {

	private CSVFile mturk_file;
	private CSVFile surveygizmo_file;
	private File outfile;

	public File getOutfile() {
		return outfile;
	}

	public void setOutfile(File outfile) {
		this.outfile = outfile;
	}

	public ResultsProcessor(File mturk, File sg) {
		mturk_file = CSVFile.read(mturk.getPath());
		surveygizmo_file = CSVFile.read(sg.getPath());
	}

	public ResultsProcessor(File mturk, File sg, File out) {
		mturk_file = CSVFile.read(mturk.getPath());
		surveygizmo_file = CSVFile.read(sg.getPath());
		outfile = out;
	}

	private List<ResultFlag> aggregateResults() {
		ArrayList<ResultFlag> allResults = new ArrayList<ResultFlag>();

		allResults.addAll(checkAttentionChecks());
		allResults.addAll(checkFinalCode());
		allResults.addAll(checkWorkerID());
		allResults.addAll(checkZIP());
		allResults.addAll(checkUSOnly());
		allResults.addAll(checkCompletionStatus());

		return allResults;
	}

	public String[] process() {
		List<String> logfileOutput = new ArrayList<String>();
		List<ResultFlag> flags = new ArrayList<ResultFlag>();
		AMTResultsFile outFileContents = new AMTResultsFile();

		// Pack all of the existing mturk data into our data structure.
		for (String[] line : mturk_file.getAllLines())
			outFileContents.addLine(line);

		flags = aggregateResults();

		for (ResultFlag results : flags) {
			logfileOutput.add(results.toString());

			// For all errors that are present in the turk file...
			if (results.filename == mturk_file.getFilename()) {
				ArrayList<String> lineFields = new ArrayList<String>();

				// Reconstruct the line from the mturk data, and set the reject
				// flag
				for (String data : outFileContents.getLine(results.line)) {
					lineFields.add(data);
				}
				// 31st field is "Reject".
				lineFields.set(31, "X " + results.failureType);
				// Build and replace the line in the file.
				outFileContents.replaceLine(results.line, lineFields.toArray(new String[0]));
			}

		}

		// Approve the remaining HITs, they passed all our tests.
		int numLines = outFileContents.length();

		for (int i = 0; i < numLines; i++) {
			ArrayList<String> lineFields = new ArrayList<String>();

			for (String data : outFileContents.getLine(i)) {
				lineFields.add(data);
			}
			// If it hasn't been rejected, flag it for a pass.
			if (lineFields.get(31) != "X") {
				lineFields.set(30, "X");
			}
			// Build and replace the line in the file if we modified it.
			outFileContents.replaceLine(i, lineFields.toArray(new String[0]));
		}

		outFileContents.write(outfile);

		return logfileOutput.toArray(new String[0]);
	}

	private List<ResultFlag> checkAttentionChecks() {
		final String failType = "Attention Check";
		ArrayList<ResultFlag> results = new ArrayList<ResultFlag>();

		System.err.println("Running test: " + failType);

		ArrayList<KeyValue<Integer, String>> checks = new ArrayList<KeyValue<Integer, String>>();
		checks.add(new KeyValue<Integer, String>(1,
				"The event affects only one person in your family:The above proximities are presented in random order below. Please rank them based on the order above (this is an attention test)."));
		checks.add(new KeyValue<Integer, String>(2,
				"The event affects only one person in your workplace:The above proximities are presented in random order below. Please rank them based on the order above (this is an attention test)."));
		checks.add(new KeyValue<Integer, String>(3,
				"The event affects only one person in your city:The above proximities are presented in random order below. Please rank them based on the order above (this is an attention test)."));
		checks.add(new KeyValue<Integer, String>(4,
				"The event affects only one person in your state:The above proximities are presented in random order below. Please rank them based on the order above (this is an attention test)."));
		checks.add(new KeyValue<Integer, String>(5,
				"The event affects only one person in your country:The above proximities are presented in random order below. Please rank them based on the order above (this is an attention test)."));

		for (int line = 0; line >= surveygizmo_file.size(); line++) {
			for (KeyValue<Integer, String> whichCheck : checks) {
				if (surveygizmo_file.getField(whichCheck.getValue(), line) != Integer.toString(whichCheck.getKey())) {
					String worker = surveygizmo_file.getField("WorkerID", line);
					results.add(new ResultFlag(worker, line, failType, surveygizmo_file.getFilename()));
				}
			}
		}

		return results;
	}

	private List<ResultFlag> checkFinalCode() {
		final String failType1 = "Payment Code Mismatch";
		final String failType2 = "Forged Survey Completion Code";
		final String failType3 = "Invalid MTurk Referral";
		ArrayList<ResultFlag> results = new ArrayList<ResultFlag>();

		ArrayList<KeyValue<String, String>> checks1 = new ArrayList<KeyValue<String, String>>();
		ArrayList<KeyValue<String, Integer>> check_line = new ArrayList<KeyValue<String, Integer>>();

		ArrayList<KeyValue<String, String>> checks2 = new ArrayList<KeyValue<String, String>>();

		Set<String> turkers = new HashSet<String>();
		Set<String> sg_workers = new HashSet<String>();

		// Build list of AMT workers.
		for (int line = 0; line < mturk_file.size(); line++) {
			String worker = mturk_file.getField("WorkerId", line);
			String finalcode = mturk_file.getField("Answer.surveycode", line);

			// Use this hashset for quickly checking if the workers all exist.
			turkers.add(worker);

			checks1.add(new KeyValue<String, String>(worker, finalcode));
			check_line.add(new KeyValue<String, Integer>(worker, line));
		}

		System.err.println("Running test: " + failType3);
		// Build list of SurveyGizmo workers.
		for (int line = 0; line < surveygizmo_file.size(); line++) {
			String worker = surveygizmo_file.getField("WorkerID", line);
			String finalcode = surveygizmo_file.getField("FinalCode", line);

			// Check if all the SurveyGizmo workers are in the list of Turkers
			// (invalid referral).
			if (!turkers.contains(worker)) {
				// We found a worker that didn't get here through MTurk.
				results.add(new ResultFlag(worker, line + 2, failType3, surveygizmo_file.getFilename()));
			}

			sg_workers.add(worker);
			checks2.add(new KeyValue<String, String>(worker, finalcode));
		}

		// Now check if all the Turkers are in the list of SurveyGizmo workers
		// (forged finalcode).
		System.err.println("Running test: " + failType2);
		for (int line = 0; line < mturk_file.size(); line++) {
			String worker = mturk_file.getField("WorkerId", line);

			if (!sg_workers.contains(worker)) {
				// We found a worker that never got a surveycode from the
				// survey, so it must be forged.
				results.add(new ResultFlag(worker, line + 2, failType2, mturk_file.getFilename()));
			}
		}

		System.err.println("Running test: " + failType1);
		// Now check that the finalcodes for each workerid match across both
		// files.
		for (KeyValue<String, String> turker : checks1) {
			for (KeyValue<String, String> sg_worker : checks2) {
				if (turker.getKey() == sg_worker.getKey()) {
					if (turker.getValue() != sg_worker.getValue()) {
						// There's a mismatch in the finalcodes between these
						// workers with matching IDs.
						// Find the line number(s) that belong to this worker.
						// Shouldn't be duplicates, but that's for a later
						// check.
						for (KeyValue<String, Integer> turker_line : check_line)
							if (turker_line.getKey() == turker.getKey()) {
								Integer line = turker_line.getValue();
								results.add(new ResultFlag(turker.getKey(), line + 2, failType1, mturk_file.getFilename()));
							}
					}
				}
			}
		}

		return results;
	}

	private List<ResultFlag> checkWorkerID() {
		final String failType1 = "Duplicate AMT Worker ID - Multiple Payout Attempts";
		final String failType2 = "Duplicate SurveyGizmo Worker - Multiple Survey Attempts";
		ArrayList<ResultFlag> results = new ArrayList<ResultFlag>();

		Set<String> turkers = new HashSet<String>();
		Set<String> sg_workers = new HashSet<String>();

		System.err.println("Running test: " + failType1);
		// Check the AMT worker IDs for duplicates.
		for (int line = 0; line < mturk_file.size(); line++) {
			String worker = mturk_file.getField("WorkerId", line);

			if (turkers.contains(worker)) {
				results.add(new ResultFlag(worker, line + 2, failType1, mturk_file.getFilename()));
			} else {
				turkers.add(worker);
			}
		}

		System.err.println("Running test: " + failType2);
		// Check the surveygizmo worker IDs for duplicates.
		for (int line = 0; line < surveygizmo_file.size(); line++) {
			String worker = surveygizmo_file.getField("WorkerID", line);

			if (sg_workers.contains(worker)) {
				results.add(new ResultFlag(worker, line + 2, failType2, surveygizmo_file.getFilename()));
			} else {
				sg_workers.add(worker);
			}
		}

		return results;
	}

	private List<ResultFlag> checkZIP() {
		ArrayList<ResultFlag> results = new ArrayList<ResultFlag>();
		System.err.println("Test not yet implemented: check ZIP");
		return results;
	}

	private List<ResultFlag> checkUSOnly() {
		ArrayList<ResultFlag> results = new ArrayList<ResultFlag>();
		System.err.println("Test not yet implemented: check US only");
		return results;
	}

	private List<ResultFlag> checkCompletionStatus() {
		final String failType1 = "Survey Incomplete";
		System.err.println("Running test: " + failType1);
		ArrayList<ResultFlag> results = new ArrayList<ResultFlag>();

		for (int line = 0; line >= surveygizmo_file.size(); line++) {
			String worker = surveygizmo_file.getField("WorkerID", line);
			String status = surveygizmo_file.getField("Status", line);

			if (status.contains("Partial")) {
				results.add(new ResultFlag(worker, line + 2, failType1, surveygizmo_file.getFilename()));
			}
		}

		return results;
	}
}
