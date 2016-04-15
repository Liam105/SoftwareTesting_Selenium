package com.example.tests;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import com.csvreader.CsvReader;

@RunWith(Parameterized.class)
public class SeleniumTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private String id, pwd,email;
  
public SeleniumTest(String id, String email)
{
	this.id = id;
	this.pwd = id.substring(4);
	this.email = email;
}
  
  @Before
  public void setUp() throws Exception {
	System.setProperty("webdriver.firefox.bin", "C:/Program Files/Mozilla Firefox/firefox.exe");
	driver = new FirefoxDriver();
    baseUrl = "http://www.ncfxy.com/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

	@Parameters
	public static Collection<Object[]> getData() throws IOException {
		Object[][] obj = new Object[109][];
		CsvReader r = new CsvReader("C:/Users/³©/Downloads/info.csv", ',',
				Charset.forName("GBK"));
		 int count = 0;
         r.readHeaders();
         while(r.readRecord()){
             obj[count] = new Object[]{r.get("id"), r.get("email")};
             count++;
         }
         return Arrays.asList(obj);
     }
  @Test
  public void testUntitled() throws Exception {
    driver.get(baseUrl);
    driver.findElement(By.id("name")).clear();
    driver.findElement(By.id("name")).sendKeys(this.id);
    driver.findElement(By.id("pwd")).clear();
    driver.findElement(By.id("pwd")).sendKeys(this.pwd);
    driver.findElement(By.id("submit")).click();
    assertEquals(this.email, driver.findElement(By.xpath("//tbody[@id='table-main']/tr/td[2]")).getText());
  }

  @After
  public void tearDown() throws Exception {
    driver.close();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }
}