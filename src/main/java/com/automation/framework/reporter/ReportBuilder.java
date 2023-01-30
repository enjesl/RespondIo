package com.automation.framework.reporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.automation.common.singleton.CommonProperties;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ReportBuilder {

	private ExtentReports extent;
	private ExtentTest logger;

	public ReportBuilder() {
		/**
		 * 
		 */

	}

	public ExtentReports getExtent() {
		return extent;
	}

	public void setExtent(ExtentReports extent) {
		this.extent = extent;
	}

	public ExtentTest getLogger() {
		return logger;
	}

	public void setLogger(ExtentTest logger) {
		this.logger = logger;
	}

	/**
	 * Start Reporting for a single test case
	 * 
	 * @param reportname {@link String}
	 * @param extent     {@link ExtentReports}
	 * @param logger     {@link ExtentTest}
	 */
	public void startReport(String reportname, String browser) {

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		CommonProperties.getInstance().setTimestamp(timeStamp, reportname);
		extent = new ExtentReports(System.getProperty("user.dir") + "\\Reports\\"
				+ CommonProperties.getInstance().getTimestamp() + "\\" + reportname + "_" + timeStamp + ".html", false);
		extent.addSystemInfo("Browser", browser).addSystemInfo("Browser version", "").addSystemInfo("User Name",
				System.getProperty("user.name"));
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));

	}

	public void startTestcase(String testcasename) {
		logger = extent.startTest(testcasename);
	}

	// end the reporter and write to file
	public void endTestcase() {
		extent.endTest(logger);
	}

	// end the reporter and write to file
	public void endReport() {
		extent.flush();
	}

}
