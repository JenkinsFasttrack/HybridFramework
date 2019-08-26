package driverScript;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctionLibrary.CommonFunctions;
import Utilities.ExcelFileUtil;

public class DriverScript extends CommonFunctions{

	public static void main(String[] args) throws Exception {
		
		ExtentReports report=null;
		
		ExtentTest logger=null;
		
		ExcelFileUtil exl=new ExcelFileUtil();
		int rowCount_TestCases=exl.rowCount("TestCases");
		
		String testStepStatus=null;
		String testCaseStatus="Pass"; 
		
		for(int i=1;i<=rowCount_TestCases;i++) {
			String executionStatus = exl.getData("TestCases", i, 2);
			String tcId_TestCases = exl.getData("TestCases", i, 0);
			
			if(executionStatus.equalsIgnoreCase("Y")){
				report=new ExtentReports("./Reports/"+tcId_TestCases+CommonFunctions.generateDate()+".html");
				logger=report.startTest(tcId_TestCases);
				int rowCount_TestSteps=exl.rowCount("TestSteps");
					for(int j=1;j<rowCount_TestSteps;j++){
						String tcId_TestSteps=exl.getData("TestSteps", j, 0);
						String stepDescription_TestSteps = exl.getData("TestSteps", j, 2);
						String keyword =  exl.getData("TestSteps", j, 3);
						
						if(tcId_TestCases.equalsIgnoreCase(tcId_TestSteps)){
							CommonFunctions cf=new CommonFunctions();
							
							 switch(keyword) {
								 case "launch":
									 	testStepStatus= cf.launch();
									 	logger.log(LogStatus.INFO, stepDescription_TestSteps);
									 	exl.setData("TestSteps", j, 4, testStepStatus);
									 	break;
								 case "login":
								    	testStepStatus=cf.login(exl,tcId_TestSteps);  
								    	logger.log(LogStatus.INFO, stepDescription_TestSteps);
								    	exl.setData("TestSteps", j, 4, testStepStatus);
										break;
								 case "logout":
		  								testStepStatus=cf.logout(); 
		  								logger.log(LogStatus.INFO, stepDescription_TestSteps);
								    	exl.setData("TestSteps", j, 4, testStepStatus);
										break;
								 case "empReg":
									    testStepStatus=cf.EmployeeRegistration(exl,tcId_TestSteps);  
									    logger.log(LogStatus.INFO, stepDescription_TestSteps);
									    exl.setData("TestSteps", j, 4, testStepStatus);
									    break;	
								 case "userReg":
									    testStepStatus=cf.UserRegistration(exl,tcId_TestSteps);
									    logger.log(LogStatus.INFO, stepDescription_TestSteps);
									    exl.setData("TestSteps", j, 4, testStepStatus);
									    break;		  
							 }
							 
							 if(testStepStatus.equalsIgnoreCase("Fail")) {
	  							exl.setData("TestCases", i, 3, testStepStatus);
	  							logger.log(LogStatus.FAIL,testStepStatus);
	  							File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	  							FileUtils.copyFile(srcFile, new File("./Screenshots/"+stepDescription_TestSteps+CommonFunctions.generateDate()+".png"));
	  							break;
	  						 }else{
	  							logger.log(LogStatus.PASS,testStepStatus); 
	  							exl.setData("TestCases",i, 3,"Pass");
	  						 }	
						}	
					}
					report.endTest(logger);
					report.flush();	
			}
			else if(executionStatus.equalsIgnoreCase("N")) {
				exl.setData("TestCases", i, 3, "Not Executed");
	        }
		}
	
	}
	
}
