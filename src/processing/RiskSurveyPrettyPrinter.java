package processing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import fileIO.CSVFile;

//30 April 2016 - risk survey with 3 harms

public class RiskSurveyPrettyPrinter {
	public static TreeMap<String, Float> score1 = new TreeMap<String, Float>();
	public static TreeMap<String, Float> score2 = new TreeMap<String, Float>();
	public static TreeMap<String, String> Wcity = new TreeMap<String, String>();
	public static TreeMap<String, String> Wshares = new TreeMap<String, String>();
	public static TreeMap<String, String> Wfamily = new TreeMap<String, String>();
	public static TreeMap<String, String> Wworkplace = new TreeMap<String, String>();
	public static TreeMap<String, String> Wworkplacesize = new TreeMap<String, String>();
	public static TreeMap<String, String> WworkplaceInteract = new TreeMap<String, String>();
	public static TreeMap<String, String> Wzips = new TreeMap<String, String>();

	// WorkerID, value
	public static TreeMap<String, String> Wpop = new TreeMap<String, String>();
	public static TreeMap<String, String> Wgender = new TreeMap<String, String>();
	public static TreeMap<String, String> Weducation = new TreeMap<String, String>();
	public static TreeMap<String, String> Wethnicity = new TreeMap<String, String>();
	public static TreeMap<String, String> Wagegroup = new TreeMap<String, String>();
	public static TreeMap<String, String> Wincome = new TreeMap<String, String>();
	public static TreeMap<String, String> Wcond = new TreeMap<String, String>();
	public static TreeMap<String, String> Wshopping = new TreeMap<String, String>();
	public static TreeMap<String, Float> Wssn = new TreeMap<String, Float>();
	public static TreeMap<String, Float> Wemailaddress = new TreeMap<String, Float>();
	public static TreeMap<String, Float> Wdiseases = new TreeMap<String, Float>();
	public static TreeMap<String, Float> Wdateofbirth = new TreeMap<String, Float>();
	public static TreeMap<String, Float> Whomestate = new TreeMap<String, Float>();
	public static TreeMap<String, Float> Wmedicalprocedure = new TreeMap<String, Float>();

	public static void changeWTS() throws IOException {

		CSVFile amt = CSVFile.read("C:\\Users\\dsmullen\\Downloads\\vignettes.csv"); // this
																								// is
																								// out-ed
																								// by
																								// another
																								// file
																								// (execute
																								// all
																								// from
																								// desktop)
		FileWriter out = new FileWriter(new File("C:\\Users\\dsmullen\\Downloads\\results-afterchangedWTS.csv"));
		out.write("willingness\n");

		for (int i = 0; i < amt.size(); i++) {
			String s = amt.getField("willingness", i);
			String str = "";
			if (Integer.parseInt(s) >= 10){
				str = Integer.toString(Integer.parseInt(s) - 9);
			}
			
			if (s.equals("1"))
				str = "8";
			if (s.equals("2"))
				str = "7";
			if (s.equals("3"))
				str = "6";
			if (s.equals("4"))
				str = "5";
			if (s.equals("5"))
				str = "4";
			if (s.equals("6"))
				str = "3";
			if (s.equals("7"))
				str = "2";
			if (s.equals("8"))
				str = "1";

			out.write(str + "\n");
		}
		out.close();
	}

	public static void compare() throws IOException {
		CSVFile amt = CSVFile.read("/Users/liorafriedberg/Desktop/wiscs_test.csv"); // This
																					// is
																					// the
																					// file
																					// we
																					// input
																					// to
																					// mturk
																					// that
																					// contains
																					// the
																					// conditions.
		CSVFile gizmo = CSVFile.read("/Users/liorafriedberg/Desktop/gizmo.csv"); // This
																					// is
																					// the
																					// results
																					// table
																					// from
																					// surveygizmo
																					// -
																					// change
																					// name
																					// if
																					// needed

		for (int i = 0; i < amt.size(); i++) {
			String w_amt = amt.getField("WorkerId", i);
			String code = amt.getField("Answer.surveycode", i);
			String code_gizmo = "";
			for (int row = 0; row < gizmo.size(); row++) {
				String w_gizmo = gizmo.getField("WorkerID", row);
				if (w_gizmo.equals(w_amt)) {
					code_gizmo = gizmo.getField("FinalCode", row);
					break;
				}
			}

			if ((!code_gizmo.equals(code)) || code_gizmo.equalsIgnoreCase("")) {
				System.err.println("NOT found in Gizmo" + w_amt);
			}
		}
	}

	public static void writeInputFile() throws IOException {
		FileWriter out = new FileWriter(new File("C:\\Users\\Daniel Smullen\\Downloads\\inputFile-3.csv"));
		out.write("cond\n");
		String[] risk = { "r1", "r2", "r3", "r4" };
		String[] event = { "e1", "e2", "e3", "e4" };
		String[] infotype = { "i1", "i2", "i3" };
		String[] vagueness = { "c", "g", "m", "n" };
		ArrayList<String> vague = new ArrayList<String>();

		for (int i = 0; i < vagueness.length; i++)
			vague.add(vagueness[i]);

		for (int i = 0; i < risk.length; i++) {
			for (int j = 0; j < event.length; j++) {
				for (int k = 0; k < infotype.length; k++) {
					// String v1="",v2="";
					// Collections.shuffle(vague);

					// for(String s:vague)
					// v1=v1+s;
					String str1 = risk[i] + event[j] + infotype[k];

					// Collections.shuffle(vague);
					// for(String s:vague)
					// v2=v2+s;
					String str2 = risk[i] + event[j] + infotype[k];
					// out.write(str1+"\n"+str2+"\n");

				}
			}
		}

		for (int j = 0; j < 25; j++) {
			String v1 = "", v2 = "";
			Collections.shuffle(vague);
			for (String s : vague)
				v1 = v1 + s;
			String str1 = v1 + "0";
			String str2 = v1 + "1";

			Collections.shuffle(vague);
			for (String s : vague)
				v2 = v2 + s;
			// String str2=v1+"1";

			out.write(str1 + "\n" + str2 + "\n");

		}

		out.close();
	}

	public static float changeWTSDiscomfort(float s) {
		float str = 0;
		if (s == 1)
			str = 11;
		if (s == 2)
			str = 10;
		if (s == 3)
			str = 9;
		if (s == 4)
			str = 8;
		if (s == 5)
			str = 7;
		if (s == 6)
			str = 6;
		if (s == 7)
			str = 5;
		if (s == 8)
			str = 4;
		if (s == 9)
			str = 3;
		if (s == 10)
			str = 2;
		if (s == 11)
			str = 1;
		return str;
	}

	// WE PROBABLY DON'T USE THIS... BEWARE.
	public static void analyseDiscomfort(CSVFile results) throws IOException {
		FileWriter out = new FileWriter("/Users/liorafriedberg/Desktop/discomfort60.csv");
		for (int row = 0; row < results.size(); row++) {
			String w = "";
			float s = 0, str = 0;
			float temp = 0;

			w = results.getField("WorkerID", row);
			s = Float.parseFloat(results.getField("ssn", row));
			str = changeWTSDiscomfort(s);
			Wssn.put(w, str);

			s = Float.parseFloat(results.getField("emailaddress", row));
			str = changeWTSDiscomfort(s);
			Wemailaddress.put(w, str);

			s = Float.parseFloat(results.getField("homestate", row));
			str = changeWTSDiscomfort(s);
			Whomestate.put(w, str);

			s = Float.parseFloat(results.getField("diseases", row));
			str = changeWTSDiscomfort(s);
			Wdiseases.put(w, str);

			s = Float.parseFloat(results.getField("medicalprocedures", row));
			str = changeWTSDiscomfort(s);
			Wmedicalprocedure.put(w, str);

			s = Float.parseFloat(results.getField("dateofbirth", row));
			str = changeWTSDiscomfort(s);
			Wdateofbirth.put(w, str);
		}

		float ssn = 0, email = 0, dob = 0, medicalprocedure = 0, diseases = 0, homestate = 0;
		for (String workerID : Wssn.keySet()) {
			ssn = ssn + Wssn.get(workerID);
			email = email + Wemailaddress.get(workerID);
			homestate = homestate + Whomestate.get(workerID);
			dob = dob + Wdateofbirth.get(workerID);
			diseases = diseases + Wdiseases.get(workerID);
			medicalprocedure = medicalprocedure + Wmedicalprocedure.get(workerID);
		}
		System.err.println("SSN : " + ssn / Wssn.size());
		System.err.println("email : " + email / Wemailaddress.size());
		System.err.println("dob: " + dob / Wdateofbirth.size());
		System.err.println(" home state : " + homestate / Whomestate.size());
		System.err.println("diseases: " + diseases / Wdiseases.size());
		System.err.println("medical procedure: " + medicalprocedure / Wmedicalprocedure.size());

	}

	public static void analyseDemographics(CSVFile results) throws IOException {
		FileWriter out = new FileWriter("C:\\Users\\dsmullen\\Downloads\\demographics80.csv");

		final int participants = 80;

		// these are counts of the vars, above tree maps are the results
		TreeMap<String, Float> shares = new TreeMap<String, Float>();
		TreeMap<String, Float> gender = new TreeMap<String, Float>();
		TreeMap<String, Float> city = new TreeMap<String, Float>();
		TreeMap<String, Float> wp = new TreeMap<String, Float>();
		TreeMap<String, Float> wpInteract = new TreeMap<String, Float>();
		TreeMap<String, Float> family = new TreeMap<String, Float>();
		TreeMap<String, Float> ageRangeD = new TreeMap<String, Float>();
		TreeMap<String, Float> ethnicity = new TreeMap<String, Float>();
		TreeMap<String, Float> education = new TreeMap<String, Float>();
		TreeMap<String, Float> householdIncome = new TreeMap<String, Float>();

		for (int row = 0; row < results.size(); row++) {
			String s = "";
			float temp = 0;
			// condition
			s = results.getField("variability", row);
			if (s.equals("0"))
				Wcond.put(results.getField("WorkerID", row), "v0");
			else if (s.equals("1"))
				Wcond.put(results.getField("WorkerID", row), "v1");

			Wworkplacesize.put(results.getField("WorkerID", row),
					results.getField(
							"Given your answer for the previous question, please give us an estimate of the number of people at your workplace.",
							row));

			// City
			// Name of the column in survey gizmo data
			s = results.getField("How would you describe your city, based on its population?", row);
			Wcity.put(results.getField("WorkerID", row),
					results.getField("How would you describe your city, based on its population?", row));
			if (city.containsKey(s)) {
				temp = city.get(s);
				city.remove(s);
				city.put(s, temp + 1);
			}

			else {
				city.put(s, (float) 1);
			}

			// workplace
			s = results.getField("How would you measure the size of your workplace?", row);
			Wworkplace.put(results.getField("WorkerID", row),
					results.getField("How would you measure the size of your workplace?", row));
			if (wp.containsKey(s)) {
				temp = wp.get(s);
				wp.remove(s);
				wp.put(s, temp + 1);
			}

			else {
				wp.put(s, (float) 1);
			}

			// workplace interact - added
			s = results.getField("How many people do you personally know or interact with at your workplace? ", row);
			WworkplaceInteract.put(results.getField("WorkerID", row), s); // more
																			// eff?
			if (wpInteract.containsKey(s)) {
				temp = wp.get(s);
				wp.remove(s);
				wp.put(s, temp + 1);
			} else {
				wp.put(s, (float) 1);
			}

			// family
			s = results.getField("What is the size of your immediate family?", row);
			Wfamily.put(results.getField("WorkerID", row),
					results.getField("What is the size of your immediate family?", row));
			if (family.containsKey(s)) {
				temp = family.get(s);
				family.remove(s);
				family.put(s, temp + 1);
			}

			else {
				family.put(s, (float) 1);
			}

			// Gender
			s = results.getField("What is your gender?", row);
			Wgender.put(results.getField("WorkerID", row), results.getField("What is your gender?", row));
			if (gender.containsKey(s)) {
				temp = gender.get(s);
				gender.remove(s);
				gender.put(s, temp + 1);
			}

			else {
				gender.put(s, (float) 1);
			}
			
			// Has personal info on their workplace pc
			s = results.getField("In this survey, we ask participants to consider the privacy risk of storing their personal information on their personal smart phone or their workplace computer. Do you store personal information on your workplace computer?", row);
			Wshares.put(results.getField("WorkerID", row), results.getField("In this survey, we ask participants to consider the privacy risk of storing their personal information on their personal smart phone or their workplace computer. Do you store personal information on your workplace computer?", row));
			if (shares.containsKey(s)) {
				temp = shares.get(s);
				shares.remove(s);
				shares.put(s, temp + 1);
			}

			else {
				shares.put(s, (float) 1);
			}

			// age range
			s = results.getField("What is your age group?", row);
			Wagegroup.put(results.getField("WorkerID", row), results.getField("What is your age group?", row));
			if (ageRangeD.containsKey(s)) {
				temp = ageRangeD.get(s);
				ageRangeD.remove(s);
				ageRangeD.put(s, temp + 1);
			}

			else {
				ageRangeD.put(s, (float) 1);
			}

			// Ethnicity
			s = results.getField("What is your ethnicity?", row);
			Wethnicity.put(results.getField("WorkerID", row), results.getField("What is your ethnicity?", row));
			if (ethnicity.containsKey(s)) {
				temp = ethnicity.get(s);
				ethnicity.remove(s);
				ethnicity.put(s, temp + 1);
			}

			else {
				ethnicity.put(s, (float) 1);
			}

			// Education
			s = results.getField("What is the highest degree of education that you have completed:", row);
			Weducation.put(results.getField("WorkerID", row),
					results.getField("What is the highest degree of education that you have completed:", row));
			if (education.containsKey(s)) {
				temp = education.get(s);
				education.remove(s);
				education.put(s, temp + 1);
			}

			else {
				education.put(s, (float) 1);
			}

			// Household income
			s = results.getField("What is your household income?", row);
			Wincome.put(results.getField("WorkerID", row), results.getField("What is your household income?", row));
			if (householdIncome.containsKey(s)) {
				temp = householdIncome.get(s);
				householdIncome.remove(s);
				householdIncome.put(s, temp + 1);
			}

			else {
				householdIncome.put(s, (float) 1);
			}

		}

		// Write to file - family
		out.write("family,Frequence,%\n");
		for (String str : family.keySet()) {
			out.write(str + "," + family.get(str) + "," + (family.get(str)) / participants * 100 + "%\n");
		}

		// Write to file - workplace
		out.write("\nworkplace,Frequence,%\n");
		for (String str : wp.keySet()) {
			out.write(str + "," + wp.get(str) + "," + (wp.get(str)) / participants * 100 + "%\n");
		}

		// write to file - interaction
		out.write("interaction,Frequence,%\n");
		for (String str : wpInteract.keySet()) {
			out.write(str + "," + wpInteract.get(str) + "," + (wpInteract.get(str)) / participants * 100 + "%\n");
		}

		// Write to file - city
		out.write("\ncity,Frequence,%\n");
		for (String str : city.keySet()) {
			out.write(str + "," + city.get(str) + "," + (city.get(str)) / participants * 100 + "%\n");
		}

		// Write to file - gender
		out.write("\nGender,Frequence,%\n");
		for (String str : gender.keySet()) {
			out.write(str + "," + gender.get(str) + "," + (gender.get(str)) / participants * 100 + "%\n");
		}

		// Write to file - age range
		out.write("\n\nAge Range,Frequence,%\n");
		for (String str : ageRangeD.keySet()) {
			out.write(escapeQuotes(str) + "," + ageRangeD.get(str) + "," + (ageRangeD.get(str)) / participants * 100
					+ "%\n");
		}

		// Write to file - gender
		out.write("\nEthnicity,Frequence,%\n");
		for (String str : ethnicity.keySet()) {
			out.write(escapeQuotes(str) + "," + ethnicity.get(str) + "," + (ethnicity.get(str)) / participants * 100
					+ "%\n");
		}

		// Write to file - education
		out.write("\nEducation,Frequence,%\n");
		for (String str : education.keySet()) {
			out.write(escapeQuotes(str) + "," + education.get(str) + "," + (education.get(str)) / participants * 100
					+ "%\n");
		}

		// Write to file - householdIncome
		out.write("Household Income,Frequence,%\n");
		for (String str : householdIncome.keySet()) {
			out.write(escapeQuotes(str) + "," + householdIncome.get(str) + ","
					+ (householdIncome.get(str)) / participants * 100 + "%\n");
		}

		out.close();
	}

	// WE DEFINITELY DO NOT USE THIS.
	public static void analyseBehaviour1(CSVFile results) throws IOException {
		FileWriter out_OB = new FileWriter("data/RiskPerception/3june/output/onlinebehaviour1_120.csv");
		TreeMap<String, String> response = new TreeMap<String, String>();
		TreeMap<String, Float> q1 = new TreeMap<String, Float>();
		TreeMap<String, Float> q2 = new TreeMap<String, Float>();
		TreeMap<String, Float> q3 = new TreeMap<String, Float>();
		TreeMap<String, Float> q4 = new TreeMap<String, Float>();
		TreeMap<String, Float> q5 = new TreeMap<String, Float>();
		TreeMap<String, Float> q6 = new TreeMap<String, Float>();

		response.put("1", "Several times a day");
		response.put("2", "About once a day");
		response.put("3", "A few times a week");
		response.put("4", "A few times a month");
		response.put("5", "A few times a year");
		response.put("6", "Never");
		String s1 = "", s2 = "", s3 = "", s4 = "", s5 = "", s6 = "";
		for (int row = 0; row < results.size(); row++) {
			float score = 0;

			String s = "";
			float temp = 0;
			// Q1
			s1 = escapeQuotes("While using the Internet how often if ever do you use a social network service?");
			s = results.getField("While using the Internet how often if ever do you use a social network service?",
					row);
			score += Integer.parseInt(s);
			System.err.println(s);
			if (q1.containsKey(s)) {
				temp = q1.get(s);
				q1.remove(s);
				q1.put(s, temp + 1);
			} else {
				q1.put(s, (float) 1);
			}

			// Q2
			s2 = escapeQuotes("While using the Internet how often if ever do you shop for products or services?");
			s = results.getField("While using the Internet how often if ever do you shop for products or services?",
					row);
			Wshopping.put(results.getField("WorkerID", row), s);
			score += Integer.parseInt(s);
			System.err.println(s);
			if (q2.containsKey(s)) {
				temp = q2.get(s);
				q2.remove(s);
				q2.put(s, temp + 1);
			} else {
				q2.put(s, (float) 1);
			}

			// Q3
			s3 = escapeQuotes(
					"While using the Internet, how often, if ever, do you pay bills, check account balances, or transfer money?");
			s = results.getField(
					"While using the Internet, how often, if ever, do you pay bills, check account balances, or transfer money?",
					row);
			score += Integer.parseInt(s);
			System.err.println(s);
			if (q3.containsKey(s)) {
				temp = q3.get(s);
				q3.remove(s);
				q3.put(s, temp + 1);
			} else {
				q3.put(s, (float) 1);
			}

			// Q4
			s4 = escapeQuotes("While using the Internet, how often, if ever, do you lookup health information online?");
			s = results.getField(
					"While using the Internet, how often, if ever, do you lookup health information online?", row);
			score += Integer.parseInt(s);
			System.err.println(s);
			if (q4.containsKey(s)) {
				temp = q4.get(s);
				q4.remove(s);
				q4.put(s, temp + 1);
			} else {
				q4.put(s, (float) 1);
			}

			// Q5
			s5 = escapeQuotes("While using the Internet, how often, if ever, do you visit dating websites?");
			s = results.getField("While using the Internet, how often, if ever, do you visit dating websites?", row);
			score += Integer.parseInt(s);
			System.err.println(s);
			if (q5.containsKey(s)) {
				temp = q5.get(s);
				q5.remove(s);
				q5.put(s, temp + 1);
			} else {
				q5.put(s, (float) 1);
			}

			// Q6
			s6 = escapeQuotes("While using the Internet, how often, if ever, have you searched for a job online?");
			s = results.getField("While using the Internet, how often, if ever, have you searched for a job online?",
					row);
			score += Integer.parseInt(s);
			System.err.println(s);
			if (q6.containsKey(s)) {
				temp = q6.get(s);
				q6.remove(s);
				q6.put(s, temp + 1);
			} else {
				q6.put(s, (float) 1);
			}
			score1.put(results.getField("WorkerID", row), score / 6);

		}

		// Write to file
		out_OB.write("," + s1 + "," + s2 + "," + s3 + "," + s4 + "," + s5 + "," + s6 + "," + "\n");
		for (String s : q1.keySet()) {
			out_OB.write("\n" + response.get(s) + "," + q1.get(s) + "," + q2.get(s) + "," + q3.get(s) + "," + q4.get(s)
					+ "," + q5.get(s) + "," + q6.get(s) + "\n");
		}

		out_OB.close();
	}

	// Main function to generate input for R
	public static void analyseVignettes(CSVFile results) throws IOException {
		System.err.println("Analyzing Vignettes");
		FileWriter out = new FileWriter("C:\\Users\\dsmullen\\Downloads\\vignettes.csv");

		// took out cond,shopping,harm

		out.write(
				"workerID,population,sharesPersonalData,gender,agegroup,education,ethnicity,income,cond,harm,wp,wpInteract,citySize,family,wpSize,risk,device,economicScore,intellectualScore,deathScore,terrorismScore,purpose,infoType,willingness\n");
		//TreeMap<String, String> info_levels = new TreeMap<String, String>();

		FileWriter out_null = new FileWriter("C:\\Users\\dsmullen\\Downloads\\city_not_found.csv");

		/*
		 * info_levels.put("334", "SSN"); info_levels.put("335",
		 * "EmailAddress"); info_levels.put("336", "HomeState");
		 * info_levels.put("337", "Diseases"); info_levels.put("338",
		 * "MedicalProcedure"); info_levels.put("361", "DateOfBirth");
		 */

		CSVFile census = CSVFile.read("C:\\Users\\dsmullen\\workspace\\Preprocess\\Data\\out.csv");
		TreeMap<String, String> population = new TreeMap<String, String>();
		for (int row = 0; row < census.size(); row++) {
			String city = census.getField("city", row).toUpperCase();
			String state = census.getField("state", row).toUpperCase();
			city = city + ", " + state;
			String pop = census.getField("pop", row);
			population.put(city, pop);
		}

//		for (String s : population.keySet()) {
//			System.err.println(s + "--" + population.get(s));
//		}
		int count_null = 0;
		for (int row = 0; row < results.size(); row++) {
			String c1 = "empty";
			c1 = results.getField("City", row);
			c1 = c1 + ", " + results.getField("State/Region", row);
			c1 = c1.toUpperCase();
			String pop1 = population.get(c1);
			System.err.println(c1 + "--" + pop1);
			if (pop1 == null) {
				count_null++;
				out_null.write(c1 + "\n");
			}

			String z = results.getField("zip", row);

			// TODO: Add the real values
			String p = "pop";
			String c = "city";
			String st = "state";
			String harm = "Surveillance";

			String first, second, third, fourth;
			String economicScore = "";
			String intellectualScore = "";
			String deathScore = "";
			String terrorismScore = "";

			String[] q1 = { "[question(\"value\"), id=\"834\"]" };
			String[] q2 = { "[question(\"value\"), id=\"839\"]" };
			String[] q3 = { "[question(\"value\"), id=\"840\"]" };
			String[] q4 = { "[question(\"value\"), id=\"841\"]" };

			first = results.getField(new String[] { "Please choose the most" }, row);
			second = results.getField(new String[] { "Please choose the second" }, row);
			third = results.getField(new String[] { "Please choose the third" }, row);
			fourth = results.getField(new String[] { "Please choose the least" }, row);

			if (first.contains("investigating imminent threat of death or harm to an individual, including children")) {
				deathScore = results.getField(q1, row);
			} else if (first.contains("investigating economic harm, fraud or identity theft")) {
				economicScore = results.getField(q1, row);
			} else if (first.contains("investigating intellectual property and trade secrets")) {
				intellectualScore = results.getField(q1, row);
			} else {
				terrorismScore = results.getField(q1, row);
			}

			if (second
					.contains("investigating imminent threat of death or harm to an individual, including children")) {
				deathScore = results.getField(q2, row);
			} else if (second.contains("investigating economic harm, fraud or identity theft")) {
				economicScore = results.getField(q2, row);
			} else if (second.contains("investigating intellectual property and trade secrets")) {
				intellectualScore = results.getField(q2, row);
			} else {
				terrorismScore = results.getField(q2, row);
			}

			if (third.contains("investigating imminent threat of death or harm to an individual, including children")) {
				deathScore = results.getField(q3, row);
			} else if (third.contains("investigating economic harm, fraud or identity theft")) {
				economicScore = results.getField(q3, row);
			} else if (third.contains("investigating intellectual property and trade secrets")) {
				intellectualScore = results.getField(q3, row);
			} else {
				terrorismScore = results.getField(q3, row);
			}

			if (fourth
					.contains("investigating imminent threat of death or harm to an individual, including children")) {
				deathScore = results.getField(q4, row);
			} else if (fourth.contains("investigating economic harm, fraud or identity theft")) {
				economicScore = results.getField(q4, row);
			} else if (fourth.contains("investigating intellectual property and trade secrets")) {
				intellectualScore = results.getField(q4, row);
			} else {
				terrorismScore = results.getField(q4, row);
			}

			String s = results.getField("WorkerID", row) + "," + pop1 + "," 
					+ Wshares.get(results.getField("WorkerID", row)) + ","
					+ Wgender.get(results.getField("WorkerID", row)) + ","
					+ Wagegroup.get(results.getField("WorkerID", row)) + ","
					+ escapeQuotes(Weducation.get(results.getField("WorkerID", row))) + ","
					+ Wethnicity.get(results.getField("WorkerID", row)) + ","
					+ escapeQuotes(Wincome.get(results.getField("WorkerID", row))) + ","
					+ results.getField("condition", row) + "," + harm + "," + Wworkplace.get(results.getField("WorkerID", row)) + ","
					+ WworkplaceInteract.get(results.getField("WorkerID", row)) + "," + Wcity.get(results.getField("WorkerID", row)) + ","
					+ Wfamily.get(results.getField("WorkerID", row)) + ","
					+ Wworkplacesize.get(results.getField("WorkerID", row)) + "," + results.getField("risk", row) + ","
					+ results.getField("Device", row) + "," + economicScore + "," + intellectualScore + "," + deathScore
					+ "," + terrorismScore;

			String qp1 = "[question(\"value\"), id=\"432\"]";
			String qp2 = "[question(\"value\"), id=\"431\"]";
			String qp3 = "[question(\"value\"), id=\"433\"]";
			String qp4 = "[question(\"value\"), id=\"434\"]";

			String[] AgeRangeP1 = { "Age Range", qp1 };
			String[] AgeRangeP2 = { "Age Range", qp2 };
			String[] AgeRangeP3 = { "Age Range", qp3 };
			String[] AgeRangeP4 = { "Age Range", qp4 };

			String ID1 = s + "," + "Economic,Age Range," + results.getField(AgeRangeP1, row);
			String I1 = s + "," + "Intellectual,Age Range," + results.getField(AgeRangeP2, row);
			String S1 = s + "," + "Death,Age Range," + results.getField(AgeRangeP3, row);
			String A1 = s + "," + "Terrorism,Age Range," + results.getField(AgeRangeP4, row);
			out.write(ID1 + "\n");
			out.write(I1 + "\n");
			out.write(S1 + "\n");
			out.write(A1 + "\n");

			String[] DeviceInformationP1 = { "Device Information", qp1 };
			String[] DeviceInformationP2 = { "Device Information", qp2 };
			String[] DeviceInformationP3 = { "Device Information", qp3 };
			String[] DeviceInformationP4 = { "Device Information", qp4 };

			String ID2 = s + "," + "Economic,Device Information," + results.getField(DeviceInformationP1, row);
			String I2 = s + "," + "Intellectual,Device Information," + results.getField(DeviceInformationP2, row);
			String S2 = s + "," + "Death,Device Information," + results.getField(DeviceInformationP3, row);
			String A2 = s + "," + "Terrorism,Device Information," + results.getField(DeviceInformationP4, row);
			out.write(ID2 + "\n");
			out.write(I2 + "\n");
			out.write(S2 + "\n");
			out.write(A2 + "\n");

			String[] DeviceIDP1 = { "Device ID", qp1 };
			String[] DeviceIDP2 = { "Device ID", qp2 };
			String[] DeviceIDP3 = { "Device ID", qp3 };
			String[] DeviceIDP4 = { "Device ID", qp4 };

			String ID3 = s + "," + "Economic,Device ID," + results.getField(DeviceIDP1, row);
			String I3 = s + "," + "Intellectual,Device ID," + results.getField(DeviceIDP2, row);
			String S3 = s + "," + "Death,Device ID," + results.getField(DeviceIDP3, row);
			String A3 = s + "," + "Terrorism,Device ID," + results.getField(DeviceIDP4, row);
			out.write(ID3 + "\n");
			out.write(I3 + "\n");
			out.write(S3 + "\n");
			out.write(A3 + "\n");

			String[] UDIDIMEIP1 = { "UDID/IMEI", qp1 };
			String[] UDIDIMEIP2 = { "UDID/IMEI", qp2 };
			String[] UDIDIMEIP3 = { "UDID/IMEI", qp3 };
			String[] UDIDIMEIP4 = { "UDID/IMEI", qp4 };

			String ID4 = s + "," + "Economic,UDID/IMEI," + results.getField(UDIDIMEIP1, row);
			String I4 = s + "," + "Intellectual,UDID/IMEI," + results.getField(UDIDIMEIP2, row);
			String S4 = s + "," + "Death,UDID/IMEI," + results.getField(UDIDIMEIP3, row);
			String A4 = s + "," + "Terrorism,UDID/IMEI," + results.getField(UDIDIMEIP4, row);
			out.write(ID4 + "\n");
			out.write(I4 + "\n");
			out.write(S4 + "\n");
			out.write(A4 + "\n");

			String[] SensorDataP1 = { "Sensor Data", qp1 };
			String[] SensorDataP2 = { "Sensor Data", qp2 };
			String[] SensorDataP3 = { "Sensor Data", qp3 };
			String[] SensorDataP4 = { "Sensor Data", qp4 };

			String ID5 = s + "," + "Economic,Sensor Data," + results.getField(SensorDataP1, row);
			String I5 = s + "," + "Intellectual,Sensor Data," + results.getField(SensorDataP2, row);
			String S5 = s + "," + "Death,Sensor Data," + results.getField(SensorDataP3, row);
			String A5 = s + "," + "Terrorism,Sensor Data," + results.getField(SensorDataP4, row);
			out.write(ID5 + "\n");
			out.write(I5 + "\n");
			out.write(S5 + "\n");
			out.write(A5 + "\n");

			String[] NetworkInformationP1 = { "Network Information", qp1 };
			String[] NetworkInformationP2 = { "Network Information", qp2 };
			String[] NetworkInformationP3 = { "Network Information", qp3 };
			String[] NetworkInformationP4 = { "Network Information", qp4 };

			String ID6 = s + "," + "Economic,Network Information," + results.getField(NetworkInformationP1, row);
			String I6 = s + "," + "Intellectual,Network Information," + results.getField(NetworkInformationP2, row);
			String S6 = s + "," + "Death,Network Information," + results.getField(NetworkInformationP3, row);
			String A6 = s + "," + "Terrorism,Network Information," + results.getField(NetworkInformationP4, row);
			out.write(ID6 + "\n");
			out.write(I6 + "\n");
			out.write(S6 + "\n");
			out.write(A6 + "\n");

			String[] IPAddressesP1 = { "IP Addresses", qp1 };
			String[] IPAddressesP2 = { "IP Addresses", qp2 };
			String[] IPAddressesP3 = { "IP Addresses", qp3 };
			String[] IPAddressesP4 = { "IP Addresses", qp4 };

			String ID7 = s + "," + "Economic,IP Addresses," + results.getField(IPAddressesP1, row);
			String I7 = s + "," + "Intellectual,IP Addresses," + results.getField(IPAddressesP2, row);
			String S7 = s + "," + "Death,IP Addresses," + results.getField(IPAddressesP3, row);
			String A7 = s + "," + "Terrorism,IP Addresses," + results.getField(IPAddressesP4, row);
			out.write(ID7 + "\n");
			out.write(I7 + "\n");
			out.write(S7 + "\n");
			out.write(A7 + "\n");

			String[] PacketDataP1 = { "Packet Data", qp1 };
			String[] PacketDataP2 = { "Packet Data", qp2 };
			String[] PacketDataP3 = { "Packet Data", qp3 };
			String[] PacketDataP4 = { "Packet Data", qp4 };

			String ID8 = s + "," + "Economic,Packet Data," + results.getField(PacketDataP1, row);
			String I8 = s + "," + "Intellectual,Packet Data," + results.getField(PacketDataP2, row);
			String S8 = s + "," + "Death,Packet Data," + results.getField(PacketDataP3, row);
			String A8 = s + "," + "Terrorism,Packet Data," + results.getField(PacketDataP4, row);
			out.write(ID8 + "\n");
			out.write(I8 + "\n");
			out.write(S8 + "\n");
			out.write(A8 + "\n");

			String[] MACAddressesP1 = { "MAC Addresses", qp1 };
			String[] MACAddressesP2 = { "MAC Addresses", qp2 };
			String[] MACAddressesP3 = { "MAC Addresses", qp3 };
			String[] MACAddressesP4 = { "MAC Addresses", qp4 };

			String ID9 = s + "," + "Economic,MAC Addresses," + results.getField(MACAddressesP1, row);
			String I9 = s + "," + "Intellectual,MAC Addresses," + results.getField(MACAddressesP2, row);
			String S9 = s + "," + "Death,MAC Addresses," + results.getField(MACAddressesP3, row);
			String A9 = s + "," + "Terrorism,MAC Addresses," + results.getField(MACAddressesP4, row);
			out.write(ID9 + "\n");
			out.write(I9 + "\n");
			out.write(S9 + "\n");
			out.write(A9 + "\n");

			String[] UsernamesPasswordsP1 = { "Usernames and Passwords", qp1 };
			String[] UsernamesPasswordsP2 = { "Usernames and Passwords", qp2 };
			String[] UsernamesPasswordsP3 = { "Usernames and Passwords", qp3 };
			String[] UsernamesPasswordsP4 = { "Usernames and Passwords", qp4 };

			String ID10 = s + "," + "Economic,Usernames and Passwords," + results.getField(UsernamesPasswordsP1, row);
			String I10 = s + "," + "Intellectual,Usernames and Passwords,"
					+ results.getField(UsernamesPasswordsP2, row);
			String S10 = s + "," + "Death,Usernames and Passwords," + results.getField(UsernamesPasswordsP3, row);
			String A10 = s + "," + "Terrorism,Usernames and Passwords," + results.getField(UsernamesPasswordsP4, row);
			out.write(ID10 + "\n");
			out.write(I10 + "\n");
			out.write(S10 + "\n");
			out.write(A10 + "\n");

			String[] OSTypeVersionP1 = { "OS Type and Version", qp1 };
			String[] OSTypeVersionP2 = { "OS Type and Version", qp2 };
			String[] OSTypeVersionP3 = { "OS Type and Version", qp3 };
			String[] OSTypeVersionP4 = { "OS Type and Version", qp4 };

			String ID12 = s + "," + "Economic,OS Type and Version," + results.getField(OSTypeVersionP1, row);
			String I12 = s + "," + "Intellectual,OS Type and Version," + results.getField(OSTypeVersionP2, row);
			String S12 = s + "," + "Death,OS Type and Version," + results.getField(OSTypeVersionP3, row);
			String A12 = s + "," + "Terrorism,OS Type and Version," + results.getField(OSTypeVersionP4, row);
			out.write(ID12 + "\n");
			out.write(I12 + "\n");
			out.write(S12 + "\n");
			out.write(A12 + "\n");

			String[] RunningProcessesP1 = { "Running Processes", qp1 };
			String[] RunningProcessesP2 = { "Running Processes", qp2 };
			String[] RunningProcessesP3 = { "Running Processes", qp3 };
			String[] RunningProcessesP4 = { "Running Processes", qp4 };

			String ID13 = s + "," + "Economic,Running Processes," + results.getField(RunningProcessesP1, row);
			String I13 = s + "," + "Intellectual,Running Processes," + results.getField(RunningProcessesP2, row);
			String S13 = s + "," + "Death,Running Processes," + results.getField(RunningProcessesP3, row);
			String A13 = s + "," + "Terrorism,Running Processes," + results.getField(RunningProcessesP4, row);
			out.write(ID13 + "\n");
			out.write(I13 + "\n");
			out.write(S13 + "\n");
			out.write(A13 + "\n");

			String[] MemoryDataP1 = { "Memory Data", qp1 };
			String[] MemoryDataP2 = { "Memory Data", qp2 };
			String[] MemoryDataP3 = { "Memory Data", qp3 };
			String[] MemoryDataP4 = { "Memory Data", qp4 };

			String ID14 = s + "," + "Economic,Memory Data," + results.getField(MemoryDataP1, row);
			String I14 = s + "," + "Intellectual,Memory Data," + results.getField(MemoryDataP2, row);
			String S14 = s + "," + "Death,Memory Data," + results.getField(MemoryDataP3, row);
			String A14 = s + "," + "Terrorism,Memory Data," + results.getField(MemoryDataP4, row);
			out.write(ID14 + "\n");
			out.write(I14 + "\n");
			out.write(S14 + "\n");
			out.write(A14 + "\n");

			String[] RegistryInformationP1 = { "Registry Information", qp1 };
			String[] RegistryInformationP2 = { "Registry Information", qp2 };
			String[] RegistryInformationP3 = { "Registry Information", qp3 };
			String[] RegistryInformationP4 = { "Registry Information", qp4 };

			String ID15 = s + "," + "Economic,Registry Information," + results.getField(RegistryInformationP1, row);
			String I15 = s + "," + "Intellectual,Registry Information," + results.getField(RegistryInformationP2, row);
			String S15 = s + "," + "Death,Registry Information," + results.getField(RegistryInformationP3, row);
			String A15 = s + "," + "Terrorism,Registry Information," + results.getField(RegistryInformationP4, row);
			out.write(ID15 + "\n");
			out.write(I15 + "\n");
			out.write(S15 + "\n");
			out.write(A15 + "\n");

			String[] TemporaryFilesP1 = { "Temporary Files", qp1 };
			String[] TemporaryFilesP2 = { "Temporary Files", qp2 };
			String[] TemporaryFilesP3 = { "Temporary Files", qp3 };
			String[] TemporaryFilesP4 = { "Temporary Files", qp4 };

			String ID16 = s + "," + "Economic,Temporary Files," + results.getField(TemporaryFilesP1, row);
			String I16 = s + "," + "Intellectual,Temporary Files," + results.getField(TemporaryFilesP2, row);
			String S16 = s + "," + "Death,Temporary Files," + results.getField(TemporaryFilesP3, row);
			String A16 = s + "," + "Terrorism,Temporary Files," + results.getField(TemporaryFilesP4, row);
			out.write(ID16 + "\n");
			out.write(I16 + "\n");
			out.write(S16 + "\n");
			out.write(A16 + "\n");

			String[] ApplicationInformationP1 = { "Application Information", qp1 };
			String[] ApplicationInformationP2 = { "Application Information", qp2 };
			String[] ApplicationInformationP3 = { "Application Information", qp3 };
			String[] ApplicationInformationP4 = { "Application Information", qp4 };

			String ID17 = s + "," + "Economic,Application Information,"
					+ results.getField(ApplicationInformationP1, row);
			String I17 = s + "," + "Intellectual,Application Information,"
					+ results.getField(ApplicationInformationP2, row);
			String S17 = s + "," + "Death,Application Information," + results.getField(ApplicationInformationP3, row);
			String A17 = s + "," + "Terrorism,Application Information,"
					+ results.getField(ApplicationInformationP4, row);
			out.write(ID17 + "\n");
			out.write(I17 + "\n");
			out.write(S17 + "\n");
			out.write(A17 + "\n");

			String[] ApplicationSessionDataP1 = { "Application Session Data", qp1 };
			String[] ApplicationSessionDataP2 = { "Application Session Data", qp2 };
			String[] ApplicationSessionDataP3 = { "Application Session Data", qp3 };
			String[] ApplicationSessionDataP4 = { "Application Session Data", qp4 };

			String ID18 = s + "," + "Economic,Application Session Data,"
					+ results.getField(ApplicationSessionDataP1, row);
			String I18 = s + "," + "Intellectual,Application Session Data,"
					+ results.getField(ApplicationSessionDataP2, row);
			String S18 = s + "," + "Death,Application Session Data," + results.getField(ApplicationSessionDataP3, row);
			String A18 = s + "," + "Terrorism,Application Session Data,"
					+ results.getField(ApplicationSessionDataP4, row);
			out.write(ID18 + "\n");
			out.write(I18 + "\n");
			out.write(S18 + "\n");
			out.write(A18 + "\n");

			// String ID19 = s + "," + "Economic,Application Session Data,"
			// + results.getField("ApplicationSessionDataP1", row);
			// String I19 = s + "," + "Intellectual,Application Session Data,"
			// + results.getField("ApplicationSessionDataP2", row);
			// String S19 = s + "," + "Death,Application Session Data,"
			// + results.getField("ApplicationSessionDataP3", row);
			// String A19 = s + "," + "Terrorism,Application Session Data,"
			// + results.getField("ApplicationSessionDataP4", row);
			// out.write(ID19 + "\n");
			// out.write(I19 + "\n");
			// out.write(S19 + "\n");
			// out.write(A19 + "\n");

			String[] EmailsP1 = { "Emails", qp1 };
			String[] EmailsP2 = { "Emails", qp2 };
			String[] EmailsP3 = { "Emails", qp3 };
			String[] EmailsP4 = { "Emails", qp4 };

			String ID21 = s + "," + "Economic,Emails," + results.getField(EmailsP1, row);
			String I21 = s + "," + "Intellectual,Emails," + results.getField(EmailsP2, row);
			String S21 = s + "," + "Death,Emails," + results.getField(EmailsP3, row);
			String A21 = s + "," + "Terrorism,Emails," + results.getField(EmailsP4, row);
			out.write(ID21 + "\n");
			out.write(I21 + "\n");
			out.write(S21 + "\n");
			out.write(A21 + "\n");

			String[] ChatHistoryP1 = { "Chat History", qp1 };
			String[] ChatHistoryP2 = { "Chat History", qp2 };
			String[] ChatHistoryP3 = { "Chat History", qp3 };
			String[] ChatHistoryP4 = { "Chat History", qp4 };

			String ID22 = s + "," + "Economic,Chat History," + results.getField(ChatHistoryP1, row);
			String I22 = s + "," + "Intellectual,Chat History," + results.getField(ChatHistoryP2, row);
			String S22 = s + "," + "Death,Chat History," + results.getField(ChatHistoryP3, row);
			String A22 = s + "," + "Terrorism,Chat History," + results.getField(ChatHistoryP4, row);
			out.write(ID22 + "\n");
			out.write(I22 + "\n");
			out.write(S22 + "\n");
			out.write(A22 + "\n");

			String[] ContactInformationP1 = { "Contact Information", qp1 };
			String[] ContactInformationP2 = { "Contact Information", qp2 };
			String[] ContactInformationP3 = { "Contact Information", qp3 };
			String[] ContactInformationP4 = { "Contact Information", qp4 };

			String ID23 = s + "," + "Economic,Contact Information," + results.getField(ContactInformationP1, row);
			String I23 = s + "," + "Intellectual,Contact Information," + results.getField(ContactInformationP2, row);
			String S23 = s + "," + "Death,Contact Information," + results.getField(ContactInformationP3, row);
			String A23 = s + "," + "Terrorism,Contact Information," + results.getField(ContactInformationP4, row);
			out.write(ID23 + "\n");
			out.write(I23 + "\n");
			out.write(S23 + "\n");
			out.write(A23 + "\n");

			String[] VideoImageFilesP1 = { "Video/Image Files", qp1 };
			String[] VideoImageFilesP2 = { "Video/Image Files", qp2 };
			String[] VideoImageFilesP3 = { "Video/Image Files", qp3 };
			String[] VideoImageFilesP4 = { "Video/Image Files", qp4 };

			String ID24 = s + "," + "Economic,Video/Image Files," + results.getField(VideoImageFilesP1, row);
			String I24 = s + "," + "Intellectual,Video/Image Files," + results.getField(VideoImageFilesP2, row);
			String S24 = s + "," + "Death,Video/Image Files," + results.getField(VideoImageFilesP3, row);
			String A24 = s + "," + "Terrorism,Video/Image Files," + results.getField(VideoImageFilesP4, row);
			out.write(ID24 + "\n");
			out.write(I24 + "\n");
			out.write(S24 + "\n");
			out.write(A24 + "\n");

			String[] KeywordSearchesP1 = { "Keyword Searches", qp1 };
			String[] KeywordSearchesP2 = { "Keyword Searches", qp2 };
			String[] KeywordSearchesP3 = { "Keyword Searches", qp3 };
			String[] KeywordSearchesP4 = { "Keyword Searches", qp4 };

			String ID25 = s + "," + "Economic,Keyword Searches," + results.getField(KeywordSearchesP1, row);
			String I25 = s + "," + "Intellectual,Keyword Searches," + results.getField(KeywordSearchesP2, row);
			String S25 = s + "," + "Death,Keyword Searches," + results.getField(KeywordSearchesP3, row);
			String A25 = s + "," + "Terrorism,Keyword Searches," + results.getField(KeywordSearchesP4, row);
			out.write(ID25 + "\n");
			out.write(I25 + "\n");
			out.write(S25 + "\n");
			out.write(A25 + "\n");

			String[] BrowserHistoryP1 = { "Browser History", qp1 };
			String[] BrowserHistoryP2 = { "Browser History", qp2 };
			String[] BrowserHistoryP3 = { "Browser History", qp3 };
			String[] BrowserHistoryP4 = { "Browser History", qp4 };

			String ID26 = s + "," + "Economic,Browser History," + results.getField(BrowserHistoryP1, row);
			String I26 = s + "," + "Intellectual,Browser History," + results.getField(BrowserHistoryP2, row);
			String S26 = s + "," + "Death,Browser History," + results.getField(BrowserHistoryP3, row);
			String A26 = s + "," + "Terrorism,Browser History," + results.getField(BrowserHistoryP4, row);
			out.write(ID26 + "\n");
			out.write(I26 + "\n");
			out.write(S26 + "\n");
			out.write(A26 + "\n");

			String[] WebsitesVisitedP1 = { "Websites Visited", qp1 };
			String[] WebsitesVisitedP2 = { "Websites Visited", qp2 };
			String[] WebsitesVisitedP3 = { "Websites Visited", qp3 };
			String[] WebsitesVisitedP4 = { "Websites Visited", qp4 };

			String ID27 = s + "," + "Economic,Websites Visited," + results.getField(WebsitesVisitedP1, row);
			String I27 = s + "," + "Intellectual,Websites Visited," + results.getField(WebsitesVisitedP2, row);
			String S27 = s + "," + "Death,Websites Visited," + results.getField(WebsitesVisitedP3, row);
			String A27 = s + "," + "Terrorism,Websites Visited," + results.getField(WebsitesVisitedP4, row);
			out.write(ID27 + "\n");
			out.write(I27 + "\n");
			out.write(S27 + "\n");
			out.write(A27 + "\n");

			String[] KeyloggingDataP1 = { "Keylogging Data", qp1 };
			String[] KeyloggingDataP2 = { "Keylogging Data", qp2 };
			String[] KeyloggingDataP3 = { "Keylogging Data", qp3 };
			String[] KeyloggingDataP4 = { "Keylogging Data", qp4 };

			String ID28 = s + "," + "Economic,Keylogging Data," + results.getField(KeyloggingDataP1, row);
			String I28 = s + "," + "Intellectual,Keylogging Data," + results.getField(KeyloggingDataP2, row);
			String S28 = s + "," + "Death,Keylogging Data," + results.getField(KeyloggingDataP3, row);
			String A28 = s + "," + "Terrorism,Keylogging Data," + results.getField(KeyloggingDataP4, row);
			out.write(ID28 + "\n");
			out.write(I28 + "\n");
			out.write(S28 + "\n");
			out.write(A28 + "\n");

		}

		System.err.println(count_null);
		out.close();
		out_null.close();
	}

	// WE DEFINITELY DO NOT USE THIS
	public static void analyseVignettes2(CSVFile results) throws IOException {
		FileWriter out = new FileWriter("data/RiskPerception/3june/output/vignettes_discomfort.csv");
		out.write(
				"workerID,city,population,gender,agegroup,education,ethnicity,income,condition,cond,shopping,workplace,family, citysize, workplacesize,risk,harm,infoType,willingness,discomfort\n");
		TreeMap<String, String> info_levels = new TreeMap<String, String>();

		FileWriter out_null = new FileWriter("data/RiskPerception/3june/output/city_not_found.csv");

		/*
		 * info_levels.put("334", "SSN"); info_levels.put("335",
		 * "EmailAddress"); info_levels.put("336", "HomeState");
		 * info_levels.put("337", "Diseases"); info_levels.put("338",
		 * "MedicalProcedure"); info_levels.put("361", "DateOfBirth");
		 */

		info_levels.put("334", "SSN");
		info_levels.put("335", "EmailAddress");
		info_levels.put("336", "HomeState");
		info_levels.put("337", "Diseases");
		info_levels.put("338", "MedicalProcedure");
		info_levels.put("361", "DateOfBirth");
		// **//
		// Cities process

		CSVFile states = CSVFile.read("data/RiskPerception/10may/states.csv");
		TreeMap<String, String> abbs = new TreeMap<String, String>();
		for (int row = 0; row < states.size(); row++) {
			abbs.put(states.getField("state", row), states.getField("abb", row));
			// System.err.println(states.getField("state",
			// row)+"--"+states.getField("abb", row));
		}

		CSVFile census = CSVFile.read("data/RiskPerception/10may/census.csv");
		TreeMap<String, String> population = new TreeMap<String, String>();
		for (int row = 0; row < census.size(); row++) {
			String city = census.getField("city", row).replace("city", "");
			// System.err.println(census.getField("state",
			// row).trim()+"--"+abbs.get(census.getField("state", row).trim()));
			city = city.trim() + ";" + abbs.get(census.getField("state", row).trim()).replace(" ", "");
			String pop = census.getField("population", row);
			population.put(city, pop);
		}

		for (String s : population.keySet()) {
			System.err.println(s + "--" + population.get(s));
		}
		int count_null = 0;
		// **//
		for (int row = 0; row < results.size(); row++) {
			String c = "empty";
			c = results.getField("City", row).replace("city", "");
			c = c + ";" + results.getField("State", row);
			String pop1 = population.get(c);
			// System.err.println(c+"--"+pop1);
			if (pop1 == null) {
				count_null++;
				out_null.write(c + "\n");
			}

			String s = results.getField("WorkerID", row) + "," + c + "," + pop1 + ","
					+ Wgender.get(results.getField("WorkerID", row)) + ","
					+ Wagegroup.get(results.getField("WorkerID", row)) + ","
					+ escapeQuotes(Weducation.get(results.getField("WorkerID", row))) + ","
					+ Wethnicity.get(results.getField("WorkerID", row)) + ","
					+ escapeQuotes(Wincome.get(results.getField("WorkerID", row))) + ","
					+ results.getField("condition", row) + "," + Wcond.get(results.getField("WorkerID", row)) + ","
					+ Wshopping.get(results.getField("WorkerID", row)) + ","
					+ Wworkplace.get(results.getField("WorkerID", row)) + ","
					+ Wfamily.get(results.getField("WorkerID", row)) + ","
					+ Wcity.get(results.getField("WorkerID", row)) + ","
					+ Wworkplacesize.get(results.getField("WorkerID", row)) + "," + results.getField("risk", row);

			if (Wcond.get(results.getField("WorkerID", row)).equals("v0")) {

				float sDiscomfort = Wssn.get(results.getField("WorkerID", row));
				String ID1 = s + "," + "InducedDisclosure,SSN," + results.getField("ID;334", row) + "," + sDiscomfort;
				String I1 = s + "," + "Insecurity,SSN," + results.getField("I;334", row) + "," + sDiscomfort;
				String S1 = s + "," + "Surveillance,SSN," + results.getField("S;334", row) + "," + sDiscomfort;
				out.write(ID1 + "\n");
				out.write(I1 + "\n");
				out.write(S1 + "\n");

				sDiscomfort = Wemailaddress.get(results.getField("WorkerID", row));
				String ID2 = s + "," + "InducedDisclosure,EmailAddress," + results.getField("ID;335", row) + ","
						+ sDiscomfort;
				String I2 = s + "," + "Insecurity,EmailAddress," + results.getField("I;335", row) + "," + sDiscomfort;
				String S2 = s + "," + "Surveillance,EmailAddress," + results.getField("S;335", row) + "," + sDiscomfort;
				out.write(ID2 + "\n");
				out.write(I2 + "\n");
				out.write(S2 + "\n");

				sDiscomfort = Whomestate.get(results.getField("WorkerID", row));
				String ID3 = s + "," + "InducedDisclosure,HomeState," + results.getField("ID;336", row) + ","
						+ sDiscomfort;
				String I3 = s + "," + "Insecurity,HomeState," + results.getField("I;336", row) + "," + sDiscomfort;
				String S3 = s + "," + "Surveillance,HomeState," + results.getField("S;336", row) + "," + sDiscomfort;
				out.write(ID3 + "\n");
				out.write(I3 + "\n");
				out.write(S3 + "\n");

				sDiscomfort = Wdiseases.get(results.getField("WorkerID", row));
				String ID4 = s + "," + "InducedDisclosure,Diseases," + results.getField("ID;337", row) + ","
						+ sDiscomfort;
				String I4 = s + "," + "Insecurity,Diseases," + results.getField("I;337", row) + "," + sDiscomfort;
				String S4 = s + "," + "Surveillance,Diseases," + results.getField("S;337", row) + "," + sDiscomfort;
				out.write(ID4 + "\n");
				out.write(I4 + "\n");
				out.write(S4 + "\n");

				sDiscomfort = Wmedicalprocedure.get(results.getField("WorkerID", row));
				String ID5 = s + "," + "InducedDisclosure,MedicalProcedure," + results.getField("ID;338", row) + ","
						+ sDiscomfort;
				String I5 = s + "," + "Insecurity,MedicalProcedure," + results.getField("I;338", row) + ","
						+ sDiscomfort;
				String S5 = s + "," + "Surveillance,MedicalProcedure," + results.getField("S;338", row) + ","
						+ sDiscomfort;
				out.write(ID5 + "\n");
				out.write(I5 + "\n");
				out.write(S5 + "\n");

				sDiscomfort = Wdateofbirth.get(results.getField("WorkerID", row));
				String ID6 = s + "," + "InducedDisclosure,DateOfBirth," + results.getField("ID;361", row) + ","
						+ sDiscomfort;
				String I6 = s + "," + "Insecurity,DateOfBirth," + results.getField("I;361", row) + "," + sDiscomfort;
				String S6 = s + "," + "Surveillance,DateOfBirth," + results.getField("S;361", row) + "," + sDiscomfort;
				out.write(ID6 + "\n");
				out.write(I6 + "\n");
				out.write(S6 + "\n");

			} else if (Wcond.get(results.getField("WorkerID", row)).equals("v1")) {
				float sDiscomfort = 0;
				sDiscomfort = Wssn.get(results.getField("WorkerID", row));
				String ID1 = s + "," + "InducedDisclosure,SSN," + results.getField("334;ID", row) + "," + sDiscomfort;
				String I1 = s + "," + "Insecurity,SSN," + results.getField("334;I", row) + "," + sDiscomfort;
				String S1 = s + "," + "Surveillance,SSN," + results.getField("334;S", row) + "," + sDiscomfort;
				out.write(ID1 + "\n");
				out.write(I1 + "\n");
				out.write(S1 + "\n");

				sDiscomfort = Wemailaddress.get(results.getField("WorkerID", row));
				String ID2 = s + "," + "InducedDisclosure,EmailAddress," + results.getField("335;ID", row) + ","
						+ sDiscomfort;
				String I2 = s + "," + "Insecurity,EmailAddress," + results.getField("335;I", row) + "," + sDiscomfort;
				String S2 = s + "," + "Surveillance,EmailAddress," + results.getField("335;S", row) + "," + sDiscomfort;
				out.write(ID2 + "\n");
				out.write(I2 + "\n");
				out.write(S2 + "\n");

				sDiscomfort = Whomestate.get(results.getField("WorkerID", row));
				String ID3 = s + "," + "InducedDisclosure,HomeState," + results.getField("336;ID", row) + ","
						+ sDiscomfort;
				String I3 = s + "," + "Insecurity,HomeState," + results.getField("336;I", row) + "," + sDiscomfort;
				String S3 = s + "," + "Surveillance,HomeState," + results.getField("336;S", row) + "," + sDiscomfort;
				out.write(ID3 + "\n");
				out.write(I3 + "\n");
				out.write(S3 + "\n");

				sDiscomfort = Wdiseases.get(results.getField("WorkerID", row));
				String ID4 = s + "," + "InducedDisclosure,Diseases," + results.getField("337;ID", row) + ","
						+ sDiscomfort;
				String I4 = s + "," + "Insecurity,Diseases," + results.getField("337;I", row) + "," + sDiscomfort;
				String S4 = s + "," + "Surveillance,Diseases," + results.getField("337;S", row) + "," + sDiscomfort;
				out.write(ID4 + "\n");
				out.write(I4 + "\n");
				out.write(S4 + "\n");

				sDiscomfort = Wmedicalprocedure.get(results.getField("WorkerID", row));
				String ID5 = s + "," + "InducedDisclosure,MedicalProcedure," + results.getField("338;ID", row) + ","
						+ sDiscomfort;
				String I5 = s + "," + "Insecurity,MedicalProcedure," + results.getField("338;I", row) + ","
						+ sDiscomfort;
				String S5 = s + "," + "Surveillance,MedicalProcedure," + results.getField("338;S", row) + ","
						+ sDiscomfort;
				out.write(ID5 + "\n");
				out.write(I5 + "\n");
				out.write(S5 + "\n");

				sDiscomfort = Wdateofbirth.get(results.getField("WorkerID", row));
				String ID6 = s + "," + "InducedDisclosure,DateOfBirth," + results.getField("361;ID", row) + ","
						+ sDiscomfort;
				String I6 = s + "," + "Insecurity,DateOfBirth," + results.getField("361;I", row) + "," + sDiscomfort;
				String S6 = s + "," + "Surveillance,DateOfBirth," + results.getField("361;S", row) + "," + sDiscomfort;
				out.write(ID6 + "\n");
				out.write(I6 + "\n");
				out.write(S6 + "\n");

			}

			else {
				System.err.println("Error");
			}

		}

		System.err.println(count_null);
		out.close();
		out_null.close();
	}

	public static String escapeQuotes(String s) {
		s = "\"" + s.replaceAll("\"", "\"\"") + "\"";
		return s;
	}
}