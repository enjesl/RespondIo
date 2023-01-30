package com.automation.framework.pages;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automation.table.view.Page;
import com.automation.util.xml.XmlMarshalUnmarshal;

public class PageReader {

	public static final Logger logger = LogManager.getLogger(PageReader.class);

	private PageReader() {

	}

	/**
	 * String return object locator(xpath etc..) for a page object
	 * 
	 * @param pageName {@link String}
	 * @param object   {@link String}
	 * @return {@link String}
	 */
	public static String getObject(String pageName, String object) {

		String searchLocator = null;
		Page page = XmlMarshalUnmarshal.unmarshallingPageXml(
				System.getProperty("user.dir") + "\\src\\main\\resources" + "\\pages\\" + pageName + ".xml");

		List<com.automation.table.view.PageObject> result = page.getObjects().stream()
				.filter(item -> item.getObjectName() != null && item.getObjectName().trim().equals(object))
				.collect(Collectors.toList());
		searchLocator = result.get(0).getSearchLocator().trim();

		return searchLocator;

	}

}
