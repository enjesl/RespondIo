package com.automation.common.singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import io.restassured.response.Response;

/**
 * Store common properties of project to access from anywhere
 * 
 * @author
 *
 */
public class CommonProperties {

	private static CommonProperties instance = null;

	private CommonProperties() {
	}

	public static CommonProperties getInstance() {
		if (instance == null) {
			instance = new CommonProperties();
		}
		return instance;
	}

	private Boolean saveStatus = true;

	private Map<String, String> treeViewItemPathMap = new HashMap<>();

	private WebDriver driver;

	private ExecutorService executor;

	private String timestamp;

	private String reportName;

	private String browserName;

	private List<List<String>> finalStringList;

	private Map<String, String> randomText = new HashMap<>();

	private Map<String, Response> APIResponse = new HashMap<>();

	private Map<String, String> newTabs = new HashMap<>();

	private Map<String, String> newWindwos = new HashMap<>();

	private int retryCount;

	// **********************

	public Map<String, String> getTreeViewItemPathMap() {
		return treeViewItemPathMap;
	}

	public ExecutorService getExecutor() {
		return executor;
	}

	public void setExecutor(ExecutorService executor) {
		this.executor = executor;
	}

	// **********************

	public Boolean getSaveStatus() {
		return saveStatus;
	}

	public void setSaveStatus(Boolean saveStatus) {
		this.saveStatus = saveStatus;
	}

	public String getTimestamp() {
		return reportName + "_" + timestamp;
	}

	public void setTimestamp(String timestamp, String reportName) {
		this.timestamp = timestamp;
		this.reportName = reportName;
	}

	public String getBrowserName() {
		return browserName;
	}

	public void setBrowserName(String browserName) {
		this.browserName = browserName;
	}

	public List<List<String>> getFinalStringList() {
		return finalStringList;
	}

	public void setFinalStringList(List<List<String>> finalStringList) {
		this.finalStringList = finalStringList;
	}

	public Map<String, String> getRandomText() {
		return randomText;
	}

	public void setRandomText(HashMap<String, String> randomText) {
		this.randomText = randomText;
	}

	public Map<String, Response> getAPIResponse() {
		return APIResponse;
	}

	public void setAPIResponse(Map<String, Response> aPIResponse) {
		APIResponse = aPIResponse;
	}

	public Map<String, String> getNewTabs() {
		return newTabs;
	}

	public void setNewTabs(Map<String, String> newTabs) {
		this.newTabs = newTabs;
	}

	public Map<String, String> getNewWindwos() {
		return newWindwos;
	}

	public void setNewWindwos(Map<String, String> newWindwos) {
		this.newWindwos = newWindwos;
	}

	public WebDriver getWebDriver() {
		return driver;
	}

	public void setWebDriver(WebDriver driver) {
		this.driver = driver;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

}
