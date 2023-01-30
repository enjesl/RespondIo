package com.automation.framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.automation.common.singleton.CommonProperties;
import com.automation.framework.reporter.ReportBuilder;
import com.automation.table.view.DataTable;

import io.appium.java_client.android.AndroidDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * base configurations for test base
 * 
 * @author
 *
 */

public class Config {

	static WebDriver driver = null;
	DataTable dataTable = null;
	protected static Properties prop = null;
	private static String projectPath = null;
	private static Path selectedItemPath = null;
	static final String PROJECT_BROWSER = "project.browser";
	static final String PROJECT_PATH = "project.path";
	static final String PROJECT_TECH = "project.tech";
	static final String PROJECT_WEBDRIVER = "project.webdriver";
	static final String USER_DIR = "user.dir";
	static final String MANUAL = "manual";
	static final int WAIT_TIME = 10;

	public static final Logger logger = LogManager.getLogger(Config.class);
	// reporter
	public static final ReportBuilder reportbuilder = new ReportBuilder();

	// Select framework
	public static void beforeExecution(String reportName) {
		// read properties
		readProperties();
		// create reporter
		if (CommonProperties.getInstance().getBrowserName() != null) {
			reportbuilder.startReport(reportName, CommonProperties.getInstance().getBrowserName());
		} else {
			reportbuilder.startReport(reportName, prop.getProperty(PROJECT_BROWSER));
		}

	}

	/*
	 * @testCaseName - report name
	 */
	public static void beforeTest(String rName1, String testCaseName, String rName2) {
		String chromeView = "web";
		// Project type-Technology
		try {
			if (prop.getProperty(testCaseName + ".view").contains("mobile")) {
				chromeView = "mobile";
			}
		} catch (Exception e) {
			chromeView = "web";
		}

		
			if (CommonProperties.getInstance().getBrowserName() != null) {
				selectBrowser(CommonProperties.getInstance().getBrowserName(), chromeView);
			} else {
				selectBrowser(prop.getProperty(PROJECT_BROWSER), chromeView);
			}

		

		// start test case in the extend report
		String reportName = rName1 + testCaseName + rName2;
		reportbuilder.startTestcase(reportName);

	}

	public static void afterTest() {

		driver.quit();
		// end test case in the report
		reportbuilder.endTestcase();

	}

	public static void afterExecution() {
		try {
			/**
			 * end the entire report
			 */
			reportbuilder.endReport();

		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * getting default browser for execution set implicitly waiting time for the
	 * browser
	 * 
	 * @param browser
	 */
	private static void selectBrowser(String browser, String chromeView) {
		int waitTime;
		ChromeOptions chromeOptions = new ChromeOptions();
		// update tab list when creating a new driver
		Map<String, String> newTabs = new HashMap<>();
		CommonProperties.getInstance().setNewTabs(newTabs);

		// update window list when creating a new driver
		Map<String, String> newWindows = new HashMap<>();
		CommonProperties.getInstance().setNewTabs(newWindows);

		// define explicit wait time using config file
		if (prop.getProperty("project.wait.time") != null) {
			waitTime = Integer.parseInt(prop.getProperty("project.wait.time"));
		} else {
			waitTime = WAIT_TIME;
		}

		try {
			/**
			 * browser types : Chrome / FireFox / Microsoft Edge
			 */
			switch (browser.toUpperCase()) {
			// execute using Chrome
			case "GOOGLE CHROME":
				// check web driver extraction manual / auto
				if (prop.getProperty(PROJECT_WEBDRIVER).equalsIgnoreCase(MANUAL)) {
					System.setProperty("webdriver.chrome.driver",
							System.getProperty(USER_DIR) + "\\drivers\\chromedriver.exe");
				} else {
					WebDriverManager.chromedriver().setup();
				}

				if (prop.getProperty("project.profile.path") != null) {

					DesiredCapabilities desiredCapabilities2 = new DesiredCapabilities();
					desiredCapabilities2.setAcceptInsecureCerts(true);
					chromeOptions.addArguments(prop.getProperty("project.profile.path"));
					chromeOptions.merge(desiredCapabilities2);

					driver = new ChromeDriver(chromeOptions);
					setWebDriver(driver, waitTime);
					CommonProperties.getInstance().setWebDriver(driver);
					break;
				} else {
					driver = new ChromeDriver(chromeOptions);
					setWebDriver(driver, waitTime);
					CommonProperties.getInstance().setWebDriver(driver);
					break;
				}

			case "MICROSOFT EDGE":
				// check web driver extraction manual / auto
				if (prop.getProperty(PROJECT_WEBDRIVER).equalsIgnoreCase(MANUAL)) {
					System.setProperty("webdriver.edge.driver",
							System.getProperty(USER_DIR) + "\\drivers\\msedgedriver.exe");
				} else {
					WebDriverManager.edgedriver().setup();
				}
				driver = new EdgeDriver();
				setWebDriver(driver, waitTime);
				CommonProperties.getInstance().setWebDriver(driver);
				break;
			case "FIRE FOX":
				// check web driver extraction manual / auto
				if (prop.getProperty(PROJECT_WEBDRIVER).equalsIgnoreCase(MANUAL)) {
					System.setProperty("webdriver.firefox.driver",
							System.getProperty(USER_DIR) + "\\drivers\\geckodriver.exe");
				} else {
					WebDriverManager.firefoxdriver().setup();
				}

				driver = new FirefoxDriver();
				setWebDriver(driver, waitTime);
				CommonProperties.getInstance().setWebDriver(driver);
				break;

			default:
				logger.error("Select a Correct Browser");
			}

		} catch (Exception e) {
			driver.quit();
			logger.info("Driver is not compatible, check the version with your browser : {}", e.getMessage());
		}

	}

	// Load configuration file
	private static void readProperties() {

		try (InputStream input = new FileInputStream(System.getProperty(USER_DIR) + "\\config.settings")) {

			prop = new Properties();
			// load a properties file
			prop.load(input);

			// get the property value and print it out
			logger.info(prop.getProperty(PROJECT_TECH));

		} catch (IOException ex) {
			logger.error(ex);
		}
	}

	public static String getProjectPath() {
		return projectPath;
	}

	public static void setProjectPath(String projectPath) {
		Config.projectPath = projectPath;
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		Config.prop = prop;
	}

	public static Path getSelectedItemPath() {
		return selectedItemPath;
	}

	public static void setSelectedItemPath(Path selectedItemPath) {
		Config.selectedItemPath = selectedItemPath;
	}

	/**
	 * 
	 * @param driver   - web driver option
	 * @param waitTime select the web driver and open it maximize the driver
	 */
	public static void setWebDriver(WebDriver driver, int waitTime) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(waitTime));
		driver.manage().window().maximize();
		CommonProperties.getInstance().getNewTabs().put("parent", driver.getWindowHandle());
		CommonProperties.getInstance().getNewWindwos().put("parent", driver.getWindowHandle());
	}

}
