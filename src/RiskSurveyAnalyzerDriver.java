import java.io.IOException;

import fileIO.CSVFile;
import processing.RiskSurveyPrettyPrinter;

public class RiskSurveyAnalyzerDriver {
	
	public static void main(String[] args) throws IOException{

		//RiskSurvey.writeInputFile();
		
		CSVFile results=CSVFile.read("/Users/liorafriedberg/Desktop/gizmo.csv");
		RiskSurveyPrettyPrinter.analyseDemographics(results);
		//RiskSurvey3.analyseDiscomfort(results);
		//RiskSurvey3.analyseBehaviour1(results);
		//RiskSurvey2.analyseBehaviour2(results);
		RiskSurveyPrettyPrinter.analyseVignettes(results);
		//RiskSurvey2.compare();
		//RiskSurvey3.changeWTS();
		System.err.println("End of Processing");		
	
	}
}
