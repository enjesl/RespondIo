package com.automation.table.view;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * {@link DataRow} List DataRow class
 * 
 * @author Chamin
 *
 */
@XmlRootElement(name = "Table")
public class DataTable {

	private List<DataRow> dataRows;
	private String tableName;
	
	public List<DataRow> getDataRows() {
		return dataRows;
	}
	
	@XmlElement(name = "Row")
	public void setDataRows(List<DataRow> dataRows) {
		this.dataRows = dataRows;
	}
	
	
	public String getTableName() {
		return tableName;
	}
	
	@XmlAttribute(name = "name")
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "DataTable [dataRows =" + dataRows + "]";
	}



}
