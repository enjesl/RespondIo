package com.automation.table.view;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@link PageObject} List Object class
 * 
 * @author JoJo
 *
 */
@XmlRootElement(name = "Page")
public class Page {

	private List<PageObject> objects;

	public List<PageObject> getObjects() {
		return objects;
	}

	@XmlElement(name = "Object")
	public void setObjects(List<PageObject> objects) {
		this.objects = objects;
	}

	@Override
	public String toString() {
		return "Page [objects=" + objects + "]";
	}

}
