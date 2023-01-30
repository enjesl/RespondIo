package com.automation.table.view;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Object class
 * 
 * @author JoJo
 *
 */
@XmlRootElement(name = "Object")
public class PageObject {

	private String name;

	private String url;

	private String objectName;

	private String searchLocator;

	public PageObject(String name, String url, String objectName, String searchLocator) {
		this.name = name;
		this.url = url;
		this.objectName = objectName;
		this.searchLocator = searchLocator;
	}

	public PageObject() {
	}

	public String getName() {
		return name;
	}

	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	@XmlAttribute
	public void setUrl(String url) {
		this.url = url;
	}

	public String getObjectName() {
		return objectName;
	}

	@XmlElement
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getSearchLocator() {
		return searchLocator;
	}

	@XmlElement
	public void setSearchLocator(String searchLocator) {
		this.searchLocator = searchLocator;
	}

	@Override
	public String toString() {
		return "Object [name=" + name + ", url=" + url + ", objectName=" + objectName + ", searchLocator="
				+ searchLocator + "]";
	}

}
