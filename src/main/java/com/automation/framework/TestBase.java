package com.automation.framework;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.automation.common.constants.CommonConstants;
import com.automation.common.singleton.CommonProperties;
import com.automation.framework.pages.PageReader;
import com.automation.table.view.DataTable;
import com.automation.util.KeyCodes;
import com.automation.utils.services.RestAssuredServices;
import com.google.common.collect.ImmutableList;
import com.relevantcodes.extentreports.LogStatus;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.restassured.response.Response;

/**
 * Test base for execution
 * 
 * @author
 *
 */
public class TestBase extends Config {

	public static final String GET_TEXT = "getText";
	public static final String OR_AND = "or_and";
	public static final String LOOP_START = "start[";
	public static final String LOOP_END = "]finish";
	public static final String CONDITION_TYPE_COMMON = "common";

	private static Dimension windowSize;
	private static Duration SCROLL_DUR = Duration.ofMillis(1000);

	public TestBase() {
		super();
	}

	/*********************************************************************/

	@BeforeTest
	public void before() {
		beforeExecution(this.getClass().getSimpleName());
	}

	@BeforeMethod
	public void mName(Method method) {

		beforeTest("", method.getName(), "");

	}

	@AfterMethod
	public static void quiteDriver() {

		afterTest();

		afterExecution();

	}

	/**
	 * if variables exist in the xpath create a new xpath replacing variables
	 * 
	 * @param iteamlist
	 * @param objx
	 * @return
	 */
	public static String newXpath(String[] itemlist, String objx) {
		String xpath = null;
		if (itemlist != null) {
			String newobjx = objx.split("\\.")[1];

			xpath = PageReader.getObject(objx.split("\\.")[0], newobjx.split("\\[")[0]);

			for (int x = 0; x < itemlist.length; x++) {
				xpath = xpath.replaceFirst("<idf_.[^>]*>", itemlist[x]);
			}
		} else {
			xpath = PageReader.getObject(objx.split("\\.")[0], objx.split("\\.")[1]);
		}
		return xpath;
	}

	public static WebElement findElement(String locator) {

		String elementLocator;
		WebElement element = null;

		try {

			if (locator.contains("css")) {
				elementLocator = locator.substring(locator.indexOf("=") + 1, locator.length()).trim();
				element = driver.findElement(By.cssSelector(elementLocator));

			} else {
				element = driver.findElement(By.xpath(locator));
			}

		} catch (Exception e) {
			logger.info("Unable to locate element {}", e.getMessage());
		}

		return element;
	}

	/*****************************
	 * API Operations
	 ********************************************************/

	public Response getAPIresponse(String url, String headers, String parameters, String method, String body,
			String authType, String username, String password) {

		if (headers.contains("+")) {
			for (Map.Entry<String, String> entry : CommonProperties.getInstance().getRandomText().entrySet()) {
				if ((headers).equals("+" + entry.getKey().trim() + "+")) {
					headers = headers.replace(headers, entry.getValue());
				}
			}
		}

		return callWebServiceRestAssured(url, headers, method, parameters, body, authType, username, password);

	}

	/*******************************************
	 * Web Service Commands(RestAssured)
	 **********************************************************************/

	/**
	 * Call A Webservice (get/post) and get the response as RESTASSURED Response<br>
	 * Returns the response string<br>
	 * 
	 * @param url        : url of the service
	 * @param headers    : headers to be added to the request
	 * @param method     : Request method GET/POST/SOAP
	 * @param parameters : request parameters
	 * @param body       : request body
	 * @param authType   : authentication type for the api if exist
	 * @param username   : username for basic auth
	 * @param password   : password for basic auth
	 * @return
	 */
	public final Response callWebServiceRestAssured(String url, String headers, String method, String parameters,
			String body, String authType, String username, String password) {
		/*
		 * Log variables
		 */
		String message = "CALL WEB SERVICE with headers : (%s), parameters : (%s) "; // Default
		// value
		String errorMsg = "%";

		try {
			switch (method.toLowerCase()) {
			case "get": {
				RestAssuredServices runner = new RestAssuredServices(url);
				Response response = runner.getRequest(headers, parameters, authType, username, password);
				message = String.format(message, headers, parameters);
				reportbuilder.getLogger().log(LogStatus.PASS, message);
				return response;
			}
			case "post": {
				RestAssuredServices runner = new RestAssuredServices(url);
				Response response = runner.postRequest(headers, parameters, body, authType, username, password);
				message = String.format(message, headers, parameters);
				reportbuilder.getLogger().log(LogStatus.PASS, message);
				return response;
			}
			default: {
				throw new Exception("InvalidMethod");
			}
			}
		} catch (UnknownHostException e) {
			errorMsg = "Invalid hostname";
			reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			e.getStackTrace();
			return null;
		} catch (SSLHandshakeException e) {
			errorMsg = "Unable to access endpoint";
			reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			e.getStackTrace();
			return null;
		} catch (Exception e) {
			if (e.getMessage().equals("InvalidMethod")) {
				errorMsg = "Invalid method has provided. Valid methods are get/post";
				reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			} else {
				errorMsg = "Unknown exception";
				reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			}
			e.getStackTrace();
			return null;
		}
	}

	/**
	 * Verify a Value in a RestAssured Response
	 * 
	 * @param response    : Restassured response
	 * @param path        : json path to the value
	 * @param expectedVal : Expected value
	 */
	public final void checkRestAssuredValues(Response response, String path, String expectedVal) {

		/*
		 * Log Variables
		 */
		String message = "Check value for path (%s), expected value (%s) and received (%s) "; // Default
		// value
		String errorMsg = "%";
		try {
			String value = response.getBody().jsonPath().get(path);
			if (value == null) {
				errorMsg = String.format("Value is not received, given path (%s) might be wrong", path);
				reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			} else if (value.equals(expectedVal)) {
				message = String.format(message, path, expectedVal, value);
				reportbuilder.getLogger().log(LogStatus.PASS, message);

			} else {
				errorMsg = String.format("Received value (%s) is not equal to expected value (%s)", value, expectedVal);
				reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);

			}

		} catch (Exception e) {
			errorMsg = "Error while reading ValidatableResponse";
			reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			e.getStackTrace();
		}
	}

	/**
	 * extract a Value in a RestAssured Response Returns the response string
	 *
	 * @param response : Restassured response
	 * @param path     : json path to the value
	 * @return String
	 */
	public final String getRestAssuredValues(Response response, String path) {

		/*
		 * Log Variables
		 */
		String message = "Getting value for the json path (%s), and received (%s) "; // Default
		// value
		String errorMsg = "%";

		try {
			String value = response.getBody().jsonPath().get(path);
			if (value == null) {
				errorMsg = String.format("Value is not received, given path (%s) might be wrong", path);
				reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
				return null;
			} else {
				message = String.format(message, path, value);
				reportbuilder.getLogger().log(LogStatus.PASS, message);
				return value;
			}

		} catch (Exception e) {
			errorMsg = "Unknown exception";
			reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			e.getStackTrace();
			return null;
		}
	}

	/**
	 * Verify the ResponseCode in RestAssured Response
	 *
	 *
	 * @param response     : Restassured response
	 * @param responseCode : expected response code
	 *
	 */
	public final void checkRestAssuredResponseCode(Response response, Integer responseCode) {

		/*
		 * Log Variables
		 */

		String message = "Expected ResponseCode (%s) and received %s "; // Default
		// value
		String errorMsg = "%";

		try {
			Integer value = response.getStatusCode();
			if (value != null && value.equals(responseCode)) {
				message = String.format(message, responseCode, value);
				reportbuilder.getLogger().log(LogStatus.PASS, message);
			} else {
				errorMsg = String.format("Received value (%s) is not equal to expected value (%s)", value,
						responseCode);
				reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			}

		} catch (Exception e) {
			errorMsg = "Error while reading ValidatableResponse";
			reportbuilder.getLogger().log(LogStatus.FAIL, errorMsg);
			e.getStackTrace();
		}
	}

	/**
	 * perform key command
	 * 
	 * @param key
	 */
	public void keyCommand(String key) {

		Keys keys = null;
		String keyText = "";

		switch (key) {
		case "enter":
			keys = Keys.ENTER;
			break;
		case "add":
			keys = Keys.ADD;
			break;
		case "alt":
			keys = Keys.ALT;
			break;
		case "arrowdown":
			keys = Keys.ARROW_DOWN;
			break;
		case "arrowleft":
			keys = Keys.ARROW_LEFT;
			break;
		case "arrowright":
			keys = Keys.ARROW_RIGHT;
			break;
		case "arrowup":
			keys = Keys.ARROW_UP;
			break;
		case "backspace":
			keys = Keys.BACK_SPACE;
			break;
		case "decimal":
			keys = Keys.DECIMAL;
			break;
		case "tab":
			keys = Keys.TAB;
			break;
		case "space":
			keys = Keys.SPACE;
			break;
		case "shift":
			keys = Keys.SHIFT;
			break;
		case "substract":
			keys = Keys.SUBTRACT;
			break;
		case "escape":
			keys = Keys.ESCAPE;
			break;
		case "pageup":
			keys = Keys.PAGE_UP;
			break;
		case "pagedown":
			keys = Keys.PAGE_DOWN;
			break;
		default:
			keyText = key;
		}

		if (keys != null) {
			new Actions(driver).sendKeys(keys).perform();
		} else {
			new Actions(driver).sendKeys(keyText).perform();
		}

	}

	// *********************************************************************************************************************************
	// Edited commands for script generator
	// Web Automation commands
	// *********************************************************************************************************************************

	/*
	 * open url in the browser
	 * 
	 * URl should include the https://
	 * 
	 */
	public void open(String url) {

		try {
			driver.get(url);
			String urlvalue = url;
			reportbuilder.getLogger().log(LogStatus.PASS, " OPEN URL : " + urlvalue);
			logger.info("Pass" + " OPEN URL : {} ", urlvalue);

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL,
					" OPEN : " + e.getMessage() + reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " OPEN : {}", e.getMessage());
		}

	}

	/*
	 * Type in a text field
	 * 
	 */
	public void type(String objx, String txt) {
		String xpath;
		String[] iteamlist = null;
		String iteams = "";

		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		// get object
		xpath = newXpath(iteamlist, objx);

		try {
			findElement(xpath).sendKeys(txt);
			reportbuilder.getLogger().log(LogStatus.PASS, "TYPE : text[" + txt + "] [" + objx + "]");
			logger.info("Pass" + " TYPE : text {} {}", txt, objx);

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "TYPE : text[" + txt + "] [" + objx + "][" + e.getMessage()
					+ "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " TYPE : text {} {} {}", txt, objx, e.getMessage());
		}

	}

	public void click(String objx) {
		String[] iteamlist = null;
		String iteams = "";

		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);
		try {
			findElement(xpath).click();
			reportbuilder.getLogger().log(LogStatus.PASS, "CLICK : [" + objx + "]");
			logger.info("Pass" + " CLICK : {}", objx);
		} catch (Exception e) {
			try {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].click();", findElement(xpath));
				reportbuilder.getLogger().log(LogStatus.PASS, "CLICK : [" + objx + "]");
				logger.info("Pass" + " CLICK : {}", objx);

			} catch (Exception ex) {

				String screenshotPath = this.getScreenshotforReport();
				reportbuilder.getLogger().log(LogStatus.FAIL, "CLICK : [" + objx + "][" + e.getMessage() + "]"
						+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
				logger.info("Fail" + " CLICK : {} {}", objx, e.getMessage());
			}

		}

	}

	public void checkElementPresent(String objx) {
		String xpath;
		String[] iteamlist = null;
		String iteams = "";

		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}
		// get object
		xpath = newXpath(iteamlist, objx);

		try {
			driver.findElement(By.xpath(xpath));
			logger.info("Pass" + " CHECK ELEMENT PRESENT : {}", objx);
			reportbuilder.getLogger().log(LogStatus.PASS, "CHECK ELEMENT PRESENT : [" + objx + "]");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " CHECK ELEMENT PRESENT: {} {}", objx, e);
			reportbuilder.getLogger().log(LogStatus.FAIL, "CHECK ELEMENT PRESENT : [" + objx + "][" + e.getMessage()
					+ "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}
	}

	/*
	 * Assert value check for element presence
	 */
	public void Compare(String objx, String Message, String replacer) {

		String[] iteamlist = null;
		String iteams = "";
		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);

		try {
			List<WebElement> priceElements = driver.findElements(By.xpath(xpath));
			List<Double> prices = new ArrayList<Double>();
			for (WebElement priceElement : priceElements) {
				String priceText = priceElement.getText();
				// remove the "RM" prefix
				priceText = priceText.replace(replacer, "").trim();
				// replace the "," separator with ""
				priceText = priceText.replace(",", "");
				// convert the text to double
				double price = Double.parseDouble(priceText);
				prices.add(price);
			}

			for (int i = 0; i < prices.size() - 1; i++) {
				double currentPrice = prices.get(i);
				double nextPrice = prices.get(i + 1);
				// check if the current price is greater than the next price
				assertTrue(currentPrice >= nextPrice, "Prices are not in descending order");
			}

			logger.info("Pass" + " COMPARE VALUE : {}", prices);
			reportbuilder.getLogger().log(LogStatus.PASS, "PRICE COMPARE DECENDING : [" + prices + "]");

		} catch (Exception e) {

			logger.info("Fail" + " CHECK ELEMENT PRESENT: {} {}", objx, e);
			reportbuilder.getLogger().log(LogStatus.FAIL, "COMPARE VALUE : [" + objx + "][" + e.getMessage() + "]");

		}
	}

	/*
	 * Boolean value check for element presence
	 */
	public boolean checkElement(String objx) {
		String xpath;
		String[] iteamlist = null;
		String iteams = "";

		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}
		// get object
		xpath = newXpath(iteamlist, objx);

		try {
			driver.findElement(By.xpath(xpath));
			logger.info("Pass" + " CHECK ELEMENT PRESENT : {}", objx);
			reportbuilder.getLogger().log(LogStatus.PASS, "CHECK ELEMENT  : [" + objx + "]");
			return true;
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " CHECK ELEMENT PRESENT: {} {}", objx, e);
			reportbuilder.getLogger().log(LogStatus.PASS, "CHECK ELEMENT  : [" + objx + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			return false;
		}
	}

	/* * take screen shots and save with a given name */
	public void getScreenshot(String category, String screenshotName) {
		File shot = null;
		try {
			CommonProperties.getInstance().getTimestamp();
			String timeStamp = new SimpleDateFormat(CommonConstants.TIME_STAMP)
					.format(Calendar.getInstance().getTime());
			String path = System.getProperty("user.dir") + CommonConstants.REPORTS_DIR
					+ CommonProperties.getInstance().getTimestamp() + CommonConstants.SCREENSHOTS_DIR;
			File directory = new File(path);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			String filePath = path + timeStamp + "_" + screenshotName + ".jpg";
			File file = new File(filePath);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (category.equals("fullpage")) {
				Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
						.takeScreenshot(driver);
				ImageIO.write(s.getImage(), "jpg", file);
			} else {
				shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(shot, file);
			}
			logger.info("Pass" + " SCREENSHOT TEXT : {}", screenshotName);
			reportbuilder.getLogger().log(LogStatus.PASS,
					"ScreenShot Text  : [" + screenshotName + "]" + reportbuilder.getLogger().addScreenCapture(path));
		} catch (IOException e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " ScreenShot : {}", e);
			reportbuilder.getLogger().log(LogStatus.FAIL,
					"[" + e.getMessage() + "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}
	}

	/**
	 * pause the execution wait time is given as milliseconds
	 * 
	 * @param waitTime
	 */
	public void pause(String waitTime) {

		try {
			Thread.sleep(Integer.parseInt(waitTime));
			reportbuilder.getLogger().log(LogStatus.PASS, "PAUSE : [" + waitTime + "]");
			logger.info("Pass" + " PAUSE : {}", waitTime);
		} catch (Exception e) {
			logger.info("Fail" + " PAUSE: {} {}", waitTime, e);
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "PAUSE : [" + waitTime + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			Thread.currentThread().interrupt();

		}

	}

	public String getScreenshotforReport() {
		// append the date format with the screenshot name to avoid duplicate names
		String timeStamp = new SimpleDateFormat(CommonConstants.TIME_STAMP).format(Calendar.getInstance().getTime());
		TakesScreenshot ts = null;
		if (driver != null) {
			ts = (TakesScreenshot) driver;
		}

		File source = ts.getScreenshotAs(OutputType.FILE);
		// After execution, you could see a folder "screenshots" under project folder
		String destination = System.getProperty("user.dir") + CommonConstants.REPORTS_DIR
				+ CommonProperties.getInstance().getTimestamp() + CommonConstants.SCREENSHOTS_DIR + "error_" + timeStamp
				+ ".jpg";
		File finalDestination = new File(destination);
		try {
			FileUtils.copyFile(source, finalDestination);
		} catch (IOException e) {
			logger.error(e);
		}
		// Returns the captured file path
		return destination;
	}

	/**
	 * navigate through the web pages types - backward - go back forward - go
	 * forward url - navigate to the given url
	 * 
	 * @param navigateType
	 */
	public void navigationType(String navigateType) {

		try {
			if (navigateType.trim().equalsIgnoreCase("backward")) {
				driver.navigate().back();
			} else if (navigateType.trim().equalsIgnoreCase("forward")) {
				driver.navigate().forward();
			} else if (navigateType.trim().equalsIgnoreCase("refresh")) {
				driver.navigate().refresh();
			} else {
				driver.navigate().to(navigateType);
			}
			reportbuilder.getLogger().log(LogStatus.PASS, "NAVIGATE : [" + navigateType + "]");
			logger.info("Pass" + " NAVIGATE : {}", navigateType);
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "NAVIGATE : [" + navigateType + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " NAVIGATE : {} {}", navigateType, e.getMessage());
		}

	}

	/**
	 * mouse over to the given elemnet
	 * 
	 * @param obj
	 */
	public void mouseover(String obj) {
		String iteams = "";
		String[] iteamlist = null;

		if (obj.contains("[")) {
			iteams = obj.substring(obj.indexOf("[") + 1, obj.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, obj);

		try {
			Actions action = new Actions(driver);
			WebElement we = findElement(xpath);
			action.moveToElement(we).build().perform();
			logger.info("Pass" + " MOUSE OVER : {}", obj);
			reportbuilder.getLogger().log(LogStatus.PASS, "MOUSE OVER : [" + obj + "]");
		} catch (Exception e) {
			logger.info("Fail" + " MOUSE OVER: {} {}", obj, e);
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "MOUSE OVER : [" + obj + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}
	}

	/**
	 * select items from the drop down
	 * 
	 * @param obj          - xpath to drop down
	 * @param itemToSelect - exact item text to de selected
	 */
	public void select(String obj, String itemToSelect) {
		String[] iteamlist = null;
		String iteams = "";

		// check the existence of parameters
		if (obj.contains("[")) {
			iteams = obj.substring(obj.indexOf("[") + 1, obj.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, obj);

		try {
			WebElement mySelectedElement = findElement(xpath);
			Select dropdown = new Select(mySelectedElement);
			dropdown.selectByVisibleText(itemToSelect);
			logger.info("Pass" + " SELECT DROP DOWN : text {} {}", itemToSelect, obj);
			reportbuilder.getLogger().log(LogStatus.PASS, "SELECT : text[" + itemToSelect + "] [" + obj + "]");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "SELECT : text[" + itemToSelect + "] [" + obj + "]["
					+ e.getMessage() + "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}

	}

	/**
	 * switch to iframes
	 * 
	 * @param objx
	 */
	public void selectframe(String objx) {
		String[] iteamlist = null;
		String iteams = "";
		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);

		try {
			driver.switchTo().frame(xpath);
			logger.info("Pass" + " SELECTED FRAME : {}", objx);
			reportbuilder.getLogger().log(LogStatus.PASS, "SELECTED FRAME  : [" + objx + "]");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " SELECT FRAME: {} {}", objx, e);
			reportbuilder.getLogger().log(LogStatus.FAIL, "SELECTED FRAME [" + objx + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}

	}

	public void getElementScreenshot(String objx, String screenshotName) {
		String[] iteamlist = null;
		String iteams = "";

		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);

		try {

			CommonProperties.getInstance().getTimestamp();
			String timeStamp = new SimpleDateFormat(CommonConstants.TIME_STAMP)
					.format(Calendar.getInstance().getTime());
			WebElement element = driver.findElement(By.xpath(xpath));
			File shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			BufferedImage fullScreen = ImageIO.read(shot);
			Point location = element.getLocation();
			int width = element.getSize().getWidth();
			int height = element.getSize().getHeight();
			BufferedImage logoImage = fullScreen.getSubimage(location.getX(), location.getY(), width, height);
			ImageIO.write(logoImage, "png", shot);
			String path = Config.getProjectPath() + CommonConstants.REPORTS_DIR
					+ CommonProperties.getInstance().getTimestamp() + CommonConstants.SCREENSHOTS_DIR + timeStamp + "_"
					+ screenshotName + ".jpg";
			FileUtils.copyFile(shot, new File(path));
			logger.info("Pass" + " ELEMENT SCREENSHOT : {}", screenshotName);
			reportbuilder.getLogger().log(LogStatus.PASS, "Element ScreenShot  : [" + screenshotName + "]"
					+ reportbuilder.getLogger().addScreenCapture(path));
		} catch (IOException e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " Element ScreenShot : {}", e);
			reportbuilder.getLogger().log(LogStatus.FAIL,
					"[" + e.getMessage() + "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}
	}

	/**
	 * scroll do to the given element
	 * 
	 * @param objx
	 */
	public void scroll(String objx) {
		String[] iteamlist = null;
		String iteams = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			js.executeScript("arguments[0].scrollIntoView();", element);
			reportbuilder.getLogger().log(LogStatus.PASS, "SCROLL TO ELEMENT : [" + objx + "]");
			logger.info("Pass" + " SCROLL TO ELEMENT : {}", objx);
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "SCROLL TO ELEMENT : [" + objx + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " SCROLL TO ELEMENT : {} {}", objx, e.getMessage());
		}

	}

	/**
	 * clear existing text in a text field
	 * 
	 * @param objx
	 */
	public void clear(String objx) {
		String[] iteamlist = null;
		String iteams = "";

		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);
		try {
			Thread.sleep(3000);
			driver.findElement(By.xpath(xpath)).clear();
			reportbuilder.getLogger().log(LogStatus.PASS, "CLEAR TEXT : [" + objx + "]");
			logger.info("Pass" + " CLEAR TEXT : {}", objx);
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "CLEAR TEXT : [" + objx + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " CLEAR TEXT : {} {}", objx, e.getMessage());
		}

	}

	/**
	 * switch or create new browser tab if tab name is existing switch to that tab
	 * if not create a new tab and switch to it
	 * 
	 * @param tabName
	 */
	public void switchTab(String tabName) {

		try {
			if (CommonProperties.getInstance().getNewTabs().containsKey(tabName)) {
				driver.switchTo().window(CommonProperties.getInstance().getNewTabs().get(tabName));
				reportbuilder.getLogger().log(LogStatus.PASS, "SWITCH TO TAB : [" + tabName + "]");
			} else {
				driver.switchTo().newWindow(WindowType.TAB);
				CommonProperties.getInstance().getNewTabs().put(tabName, driver.getWindowHandle());
				reportbuilder.getLogger().log(LogStatus.PASS, "SWITCH TO NEW TAB : [" + tabName + "]");
			}
		} catch (Exception e) {
			reportbuilder.getLogger().log(LogStatus.FAIL, "SWITCH TO TAB : [" + tabName + "] [" + e.getMessage() + "]");
		}

	}

	/**
	 * switch or create new browser window if tab name is existing switch to that
	 * window if not create a new window and switch to it to switch to parent window
	 * use parent as the windowName
	 * 
	 * @param tabName
	 */
	public void switchWindow(String windowName) {

		try {
			if (CommonProperties.getInstance().getNewWindwos().containsKey(windowName)) {
				driver.switchTo().window(CommonProperties.getInstance().getNewWindwos().get(windowName));
				reportbuilder.getLogger().log(LogStatus.PASS, "SWITCH TO WINDOW : [" + windowName + "]");
				logger.info("Pass" + " SWITCH TO WINDOW : {}", windowName);
			} else {
				driver.switchTo().newWindow(WindowType.WINDOW);
				CommonProperties.getInstance().getNewWindwos().put(windowName, driver.getWindowHandle());
				reportbuilder.getLogger().log(LogStatus.PASS, "SWITCH TO NEW WINDOW : [" + windowName + "]");
				logger.info("Pass" + " SWITCH TO NEW WINDOW : {}", windowName);
			}
		} catch (Exception e) {
			reportbuilder.getLogger().log(LogStatus.FAIL,
					"SWITCH TO WINDOW : [" + windowName + "] [" + e.getMessage() + "]");
			logger.info("Fail" + " SWITCH TO WINDOW : {} {}", windowName, e.getMessage());
		}

	}

	public void switchtoParentFrame() {
		try {
			driver.switchTo().parentFrame();
			logger.info("Pass" + "SWITCHED TO PARENT FRAME");
			reportbuilder.getLogger().log(LogStatus.PASS, "SWITCHED TO PARENT FRAME");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "SWITCHED TO PARENT FRAME : [" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + "SWITCHED TO PARENT FRAME: {}", e);
		}
	}

	public String getText(String objx) {
		String[] iteamlist = null;
		String iteams = "";
		String vName = null;
		String elementText = null;
		WebElement element;
		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			if (xpath.contains("css")) {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.cssSelector(xpath.substring(xpath.indexOf("=") + 1, xpath.length()).trim())));
			} else {
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			}

			if (element != null) {

				elementText = findElement(xpath).getText();
				logger.info("Pass" + " TEXT : {}", elementText);
				reportbuilder.getLogger().log(LogStatus.PASS, "Text : [" + elementText + "]");

				// replace variable value
				CommonProperties.getInstance().getRandomText().put(vName, elementText);

			}

			return elementText;

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL,
					"Text : [" + e.getMessage() + "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " TEXT: {}", e);

			return null;

		}

	}

	private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyz";
	private static final String NUMBER_LIST = "0123456789";

	/**
	 * This method generates random string.
	 * 
	 * @param length set to length
	 * @return String
	 * 
	 */
	private String generateRandomString(final int length) {

		StringBuilder randStr = new StringBuilder();
		for (int i = 0; i < length; i++) {
			Random randomGenerator = new Random(System.nanoTime());
			int number = randomGenerator.nextInt(CHAR_LIST.length());
			char ch = CHAR_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	private String generateRandomNumber(final int length) {

		StringBuilder randStr = new StringBuilder();
		for (int i = 0; i < length; i++) {
			Random randomGenerator = new Random(System.nanoTime());
			int number = randomGenerator.nextInt(NUMBER_LIST.length());
			char ch = NUMBER_LIST.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	public String randomGenerator(String prefix, String type, String length, String suffix) {

		String text = null;

		try {
			if (type.equalsIgnoreCase("text")) {
				text = generateRandomString(Integer.parseInt(length));
			} else if (type.equalsIgnoreCase("number")) {
				text = generateRandomNumber(Integer.parseInt(length));
			}

			return prefix + text + suffix;

		} catch (Exception e) {
			return null;
		}

	}

	public void switchToAlert() {

		try {
			driver.switchTo().alert();
			reportbuilder.getLogger().log(LogStatus.PASS, "Switched to Alert ");
			logger.info("Pass" + " Switched to Alert}");

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "Switched to Alert  [" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + "Switched to Alert  {}", e.getMessage());
		}

	}

	public void alertHandle(String alertOperation, String alertText) {

		String operation = alertOperation;
		String text = alertText;

		try {
			if (text != null && text != "") {
				driver.switchTo().alert().sendKeys(text);
				reportbuilder.getLogger().log(LogStatus.PASS, "Send Text to Alert : text[" + text + "] ");
				logger.info("Pass" + " Send Text to Alert : text {}", text);
			}

			if (operation.equalsIgnoreCase("accept")) {
				driver.switchTo().alert().accept();
				reportbuilder.getLogger().log(LogStatus.PASS, " Alert : Accepted");
				logger.info("Pass" + " Alert : {} ", "Accepted");

			} else if (operation.equalsIgnoreCase("dismiss")) {
				driver.switchTo().alert().dismiss();
				reportbuilder.getLogger().log(LogStatus.PASS, " Alert : Dismiss");
				logger.info("Pass" + " Alert : {} ", "Dimiss");
			}

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL,
					" Alert : " + e.getMessage() + reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " Alert : {}", e.getMessage());
		}

	}

	public void jstype(String objx, String txt) {
		String xpath;
		String[] iteamlist = null;
		String iteams = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		// get object
		xpath = newXpath(iteamlist, objx);
		WebElement element = driver.findElement(By.xpath(xpath));
		String jsType = "arguments[0].value='" + txt + "';";

		try {
			js.executeScript(jsType, element);
			reportbuilder.getLogger().log(LogStatus.PASS, "JSTYPE : text[" + txt + "] [" + objx + "]");
			logger.info("Pass" + " JSTYPE : text {} {}", txt, objx);

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "JSTYPE : text[" + txt + "] [" + objx + "][" + e.getMessage()
					+ "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " JSTYPE : text {} {} {}", txt, objx, e.getMessage());
		}

	}

	public void jsclick(String objx) {
		String[] iteamlist = null;
		String iteams = "";
		JavascriptExecutor js = (JavascriptExecutor) driver;

		// check the existence of parameters
		if (objx.contains("[")) {
			iteams = objx.substring(objx.indexOf("[") + 1, objx.indexOf("]"));
			iteamlist = iteams.split(",");
		}

		String xpath = newXpath(iteamlist, objx);
		WebElement jsClick = driver.findElement(By.xpath(xpath));

		try {
			js.executeScript("arguments[0].click();", jsClick);
			reportbuilder.getLogger().log(LogStatus.PASS, "JS_CLICK : [" + objx + "]");
			logger.info("Pass" + " JS_CLICK : {}", objx);
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL, "JS_CLICK : [" + objx + "][" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " JS_CLICK : {} {}", objx, e.getMessage());
		}

	}

	public void keycommand(String key) {

		try {
			keyCommand(key);
			reportbuilder.getLogger().log(LogStatus.PASS, "KEY COMMAND : [" + key + "]");
			logger.info("Pass" + " KEY COMMAND : {}", key);

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " UNABLE TO PERFORM KEY COMMAND : {}", e);
			reportbuilder.getLogger().log(LogStatus.FAIL, "UNABLE TO PERFORM KEY COMMAND : [" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));

		}

	}

	public void fireKeyEvent(String key) {

		try {
			String[] commandSet = key.split("\\|");

			for (String fullCommand : commandSet) {
				String[] charArr = fullCommand.split("(?<!\\\\)\\+");
				type(charArr);
				reportbuilder.getLogger().log(LogStatus.PASS, "KEY COMMAND : [" + fullCommand + "]");
				logger.info("Pass" + " KEY COMMAND : {}", key);

			}

		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " UNABLE TO PERFORM KEY COMMAND : {}", e);
			reportbuilder.getLogger().log(LogStatus.FAIL, "UNABLE TO PERFORM KEY COMMAND : [" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));

		}

	}

	public final void type(final String[] character) {

		KeyCodes keys = new KeyCodes();
		int[] keycodes = new int[character.length];
		if (character.length > 1) {
			int number = 0;
			for (int i = 0; i < character.length; i++) {
				keycodes[i] = keys.getKeyCodes(character[i])[number];
			}

		} else {
			keycodes = keys.getKeyCodes(character[0].trim());

		}
		doType(keycodes);
	}

	/**
	 * Do type.
	 *
	 * @param keyCodes the key codes
	 */
	private void doType(final int... keyCodes) {
		doTypeKeys(keyCodes, 0, keyCodes.length);
	}

	private void doTypeKeys(final int[] keyCodes, final int offset, final int length) {
		if (length == 0) {
			return;
		}

		try {
			Robot robot = new Robot();
			robot.keyPress(keyCodes[offset]);
			doTypeKeys(keyCodes, offset + 1, length - 1);
			robot.keyRelease(keyCodes[offset]);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void quit() {
		try {
			driver.quit();
			logger.info("Pass" + "QUITE");
			reportbuilder.getLogger().log(LogStatus.PASS, "QUITE");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL,
					"QUITE : [" + e.getMessage() + "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + "QUIT: {}", e);
		}
	}

	public void close() {
		try {
			driver.close();
			logger.info("Pass" + " CLOSED");
			reportbuilder.getLogger().log(LogStatus.PASS, "CLOSE");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			reportbuilder.getLogger().log(LogStatus.FAIL,
					"CLOSE : [" + e.getMessage() + "]" + reportbuilder.getLogger().addScreenCapture(screenshotPath));
			logger.info("Fail" + " CLOSE: {}", e);
		}
	}

	/**
	 * get current page title
	 */
	public void getTitle() {
		String pageTitle = null;
		try {
			pageTitle = driver.getTitle();
			logger.info("Pass" + " CURRENT PAGE TITLE : {}", pageTitle);
			reportbuilder.getLogger().log(LogStatus.PASS, "CURRENT PAGE TITLE  : [" + pageTitle + "]");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " CURRENT PAGE TITLE : {}", e);
			reportbuilder.getLogger().log(LogStatus.FAIL, "CURRENT PAGE TITLE : [" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}
	}

	/**
	 * get current url of the web page
	 */
	public void getCurrentUrl() {
		String currentUrl = null;
		try {
			currentUrl = driver.getCurrentUrl();
			logger.info("Pass" + " CURRENT URL : {}", currentUrl);
			reportbuilder.getLogger().log(LogStatus.PASS, "GET CURRENT URL : [" + currentUrl + "]");
		} catch (Exception e) {
			String screenshotPath = this.getScreenshotforReport();
			logger.info("Fail" + " CURRENT URL: {}", e);
			reportbuilder.getLogger().log(LogStatus.FAIL, "GET CURRENT URL : [" + e.getMessage() + "]"
					+ reportbuilder.getLogger().addScreenCapture(screenshotPath));
		}
	}

	public final Object[][] getTableArray(final DataTable table) {
		Object[][] tabArray = null;
		try {
			Integer rowcount = table.getDataRows().size();
			Integer colcount = table.getDataRows().get(0).getFields().size();

			tabArray = new Object[rowcount][colcount];

			for (int row = 0; row < rowcount; row++) {
				for (int col = 0; col < colcount; col++) {
					tabArray[row][col] = table.getDataRows().get(row).getFields().get(col).getData();
				}

			}
		} catch (Exception e) {
			System.out.println("no data provider");
		}

		return tabArray;

	}

	/*****************************
	 * API Operations
	 ********************************************************/

	// add rest api commands here

	public void checkAPIresponse(String path, String expectedVal, Response responseName) {
		checkRestAssuredValues(responseName, path, expectedVal);

	}

	public void checkResponseCode(String code, Response responseName) {
		checkRestAssuredResponseCode(responseName, Integer.parseInt(code));
	}

	/*
	 * write to extent report as a pass statement
	 */
	public final void writeToReport(final String message) {
		try {
			reportbuilder.getLogger().log(LogStatus.PASS, "WRITE TO REPORT message[" + message + "]");
			logger.info("Pass" + " WRITE TO REPORT [{}]", message);

		} catch (Exception e) {
			String errorString = e.getMessage();
			reportbuilder.getLogger().log(LogStatus.FAIL, "write to report :" + errorString);
			logger.info("Fail" + " WRITE TO REPORT {}", errorString);

		}
	}

	/*
	 * write to extent report as a fail statement
	 */
	public final void writeToReportFail(final String message) {
		try {
			reportbuilder.getLogger().log(LogStatus.FAIL, "WRITE TO REPORT message[" + message + "]");
			logger.info("Pass" + " WRITE TO REPORT [{}]", message);

		} catch (Exception e) {
			String errorString = e.getMessage();
			reportbuilder.getLogger().log(LogStatus.FAIL, "write to report :" + errorString);
			logger.info("Fail" + " WRITE TO REPORT {}", errorString);

		}
	}
}

/**************************************************************************************************************************************/
