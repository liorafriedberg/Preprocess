import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class RiskSurveyExample {
	
	public static void main(String[] args) throws IOException{

		//RiskSurvey.writeInputFile();
		
		CSVFile results=CSVFile.read("/Users/liorafriedberg/Desktop/gizmo.csv");
		RiskSurvey3.analyseDemographics(results);
		//RiskSurvey3.analyseDiscomfort(results);
		//RiskSurvey3.analyseBehaviour1(results);
		//RiskSurvey2.analyseBehaviour2(results);
		RiskSurvey3.analyseVignettes(results);
		//RiskSurvey2.compare();
		//RiskSurvey3.changeWTS();
		System.err.println("End of Processing");		
	
	}
}
