package config;

public class Constants {
	//This is the list of System Variables
	//Declared as 'public', so that it can be used in other classes of this project
	//Declared as 'static', so that we do not need to instantiate a class object
	//Declared as 'final', so that the value of this variable can be changed
	// 'String' & 'int' are the data type for storing a type of value	
	public static final String URL = "http://www.store.demoqa.com";
	public static final String Path_TestData = "C://Users//ttc.mk//workspace//KDFramework//src//dataEngine//DataEngine.xlsx";
	public static final String File_TestData = "DataEngine.xlsx";
	public static final String Path_OR = "C://Users//ttc.mk//workspace//KDFramework//src////config//OR.txt";
	public static final String Report_Path = "C://Users//ttc.mk//Desktop//MK//AutomationReport.html";
	public static final String Screenshots_Path = "D:\\";

	//List of Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;	
	public static final int Col_TestScenarioID =1 ;
	public static final int Col_TestCaseDesc =1;

	public static final int Col_PageObject =4 ;
	public static final int Col_ActionKeyword =5 ;
	public static final int Col_TestData = 6;
	public static final int Col_TestStepDesc =2;

	public static final int Col_RunMode =2 ;

	//List of Data Engine Excel sheets
	public static final String Sheet_TestSteps = "TestSteps";
	//New entry in Constant variable
	public static final String Sheet_TestCases = "TestCases";


	public static final int Col_Result =3 ;
	public static final int Col_TestStepResult =7 ;

	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";


}
