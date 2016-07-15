import java.io.IOException;

import fileIO.CSVFile;
import processing.RiskSurveyPrettyPrinter;

public class RiskSurveyAnalyzerDriver {
	
	public static void main(String[] args) throws IOException{

		//RiskSurvey.writeInputFile();
		
		CSVFile results=CSVFile.read("C:\\Users\\dsmullen\\Downloads\\20160712161142-SurveyExport.csv");
		RiskSurveyPrettyPrinter.analyseDemographics(results);
		//RiskSurvey3.analyseDiscomfort(results);
		//RiskSurvey3.analyseBehaviour1(results);
		//RiskSurvey2.analyseBehaviour2(results);
		RiskSurveyPrettyPrinter.analyseVignettes(results);
		//RiskSurvey2.compare();
		RiskSurveyPrettyPrinter.changeWTS();
		System.err.println("End of Processing");		
	
	}
}
