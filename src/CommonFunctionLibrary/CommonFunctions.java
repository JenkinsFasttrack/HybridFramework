package CommonFunctionLibrary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import Utilities.ExcelFileUtil;

public class CommonFunctions {

	public static WebDriver driver ;
	public String status=null;
	
	public String launch() throws Exception {
		try{	
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//Driver//chromedriver.exe");
			driver=new ChromeDriver();
			driver.get(getproperty("URL"));
			driver.manage().window().maximize();
			 status="Pass";
		}catch(Exception e) {
			status="Fail";
		}	
		return status; 
	}
	
	public String login(ExcelFileUtil exlu,String tcid) throws Exception{
		
		try{
			int testcaseRow=exlu.getTestcaseRow(tcid);
			
			String uname=exlu.getData("TestData", testcaseRow, 1);
			String password=exlu.getData("TestData", testcaseRow, 2);
			
			driver.findElement(By.id("txtUsername")).sendKeys(uname);
		 	driver.findElement(By.id("txtPassword")).sendKeys(password);
		 	Thread.sleep(20000);
		 	driver.findElement(By.id("btnLogin")).click();
		 	Assert.assertEquals(driver.findElement(By.id("welcome")).getAttribute("id"),"welcome","Login Failed");
		 	status="Pass";
		}catch(Exception e) {
			status="Fail";
		}
		
		return status; 	
	}
	
	public String logout(){
		 try {
				Thread.sleep(3000);
			 	String expval="LOGIN";
			 	driver.findElement(By.partialLinkText("Welcome")).click();
			 	Thread.sleep(3000);
			 	driver.findElement(By.linkText("Logout")).click();
			 	String actval=driver.findElement(By.id("btnLogin")).getAttribute("value");
			 	Assert.assertEquals(actval,expval,"Logout Failed");	
			 	status="Pass";
			 	driver.close();
		    }catch(Exception e) {
		    	System.out.println(e.getMessage());
		    	status="Fail";
			}
			return status;
	}
	
	public String EmployeeRegistration(ExcelFileUtil exlu,String tcid) throws Exception {
		try {
			int testcaseRow=exlu.getTestcaseRow(tcid);
			
			String firstName=exlu.getData("TestData", testcaseRow, 5);
			String lastname=exlu.getData("TestData", testcaseRow, 6);
			
			driver.findElement(By.xpath("//b[contains(text(),'PIM')]")).click();
			driver.findElement(By.linkText("Employee List")).click();
			driver.findElement(By.xpath("//input[@id='btnAdd']")).click();
			
			
			driver.findElement(By.xpath("//input[@id='firstName']")).sendKeys(firstName);
			driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys(lastname);
			driver.findElement(By.xpath("//input[@id='btnSave']")).click();
			Thread.sleep(6000);
			
			Assert.assertEquals(driver.findElement(By.xpath("//*[@id=\"profile-pic\"]/h1")).getText(),firstName+" "+lastname,"Employee Registration Failed");
			status="Pass";
		}catch(Exception e) {
	    	System.out.println(e.getMessage());
	    	status="Fail";
		}
			return status;	
	}
	
	public String UserRegistration(ExcelFileUtil exlu,String tcid) {
		try {
			int testcaseRow=exlu.getTestcaseRow(tcid);
			String empName=exlu.getData("TestData", testcaseRow, 5)+exlu.getData("TestData", testcaseRow, 6);
			
			String username=exlu.getData("TestData", testcaseRow, 8);
			String pwd=exlu.getData("TestData", testcaseRow, 7);
			
			driver.findElement(By.linkText("Admin")).click();
			driver.findElement(By.id("btnAdd")).click();
			
			driver.findElement(By.id("systemUser_employeeName_empName")).sendKeys(empName);
			driver.findElement(By.id("systemUser_userName")).sendKeys(username);
			driver.findElement(By.id("systemUser_password")).sendKeys(pwd);
			driver.findElement(By.id("systemUser_confirmPassword")).sendKeys(pwd);
			driver.findElement(By.id("btnSave")).click();
			
			status="Pass";
		}catch(Exception e) {
	    	System.out.println(e.getMessage());
	    	status="Fail";
		}
			return status;
	}
	
	public String getproperty(String keyToGet) throws Exception{
		Properties p=new Properties();
		FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"\\config.properties");
		p.load(file);
		return (String) p.get(keyToGet);
	}
	
	
	public static String generateDate(){
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
		return sdf.format(date);
	}
	
}
