package com.automation.common.constants;



/**
 * This class responsible for hold Common constant values
 * 
 * @author JoJo
 *
 */
public final class CommonConstants {

	private CommonConstants() {
	}

	
	/*
	 * variable declarations
	 */
	public static final String IF_PASS_MESSAGE = "If condition passed";
	public static final String ILLEGALARGUMENT_MESSAGE = "Illegal argument";
	public static final String IF_BODY_PATTER= "\\((.*?)\\)";
	public static final String VARIABLE_PATTER= "\\+[^>]*\\+";
	public static final String REPORTS_DIR= "\\Reports\\";
	public static final String SCREENSHOTS_DIR= "\\screenshots\\";
	public static final String RESPONSE_IDF = "response_identifier";
	public static final String TIME_STAMP = "yyyyMMdd_HHmmss";
	public static final String CONDITION = "condition";
	

	/**
	 * Table View properties
	 */
	public static final String COLUMN_1_NAME = "Object Name";
	public static final String COLUMN_2_NAME = "Search Locator";
	public static final String JAXB_EXCEPTION_MESSAGE = "Content doesn't match Used Java object or Content is not a proper XML format content or Empty XML file";
	public static final String JAXB_EXCEPTION_MESSAGE_PAGE = "New Page Created Successfully.";
	
	/**************************************************************************************************/
	public static final String FILE_TYPE = "File";
	public static final String FOLDER_TYPE = "Folder";
	public static final String FILE_TYPE_STAGE = "File Type Stage";
	public static final String FOLDER_TYPE_STAGE = "Folder Type Stage";
	public static final String DATA_TABLES = "datatables";
	public static final String LIBRARIES = "libraries";
	public static final String TEST_SUITE = "testsuites";
	public static final String TEST_PLANS = "testplans";
	public static final String PAGES = "pages";
	public static final String DB_STRING = "datatables"; 

	
	public static final String PAREN_PATTERN = "\\(|\\)";
	public static final String AND_PATTERN = "&";
	public static final String OR_PATTERN = "\\|";
	public static final String BRACE_PATTERN = "\\{|\\}";
	public static final String BRACKET_PATTERN = "\\[|\\]";
	public static final String SEMICOLON_PATTERN = "\\;";
	public static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	public static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	

}
