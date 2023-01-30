package com.automation.table.view;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 * Row class
 * 
 * @author Chamin
 *
 */
@XmlRootElement(name = "data")
public class DataField {
	
	private String name;
	private String value;
	
	public DataField(String name,String value) {
		this.value = value;
		this.name = name;
	}
	
	public DataField() {
		
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = "name")
	public void setName(String name) {
		this.name = name;
	}
	
	public String getData() {
		return value;
	}
	
	@XmlValue
	public void setData(String data) {
		this.value = data;
	}

	@Override
	public String toString() {
		return "DataField [name=" + name + ", value=" + value + "]";
	}

}
