package com.automation.framework.data;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automation.table.view.DataTable;
import com.automation.util.xml.XmlMarshalUnmarshal;

public class DataReader {

	public static final Logger logger = LogManager.getLogger(DataReader.class);

	private DataReader() {

	}

	/**
	 * return data table
	 * 
	 * @param tableName {@link String}
	 * @return {@link dataTable}
	 */
	public static DataTable getDataTable(String tableName) {
		try {
			if (Files.exists(Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\datatables"
					+ File.separator + tableName + ".xml"))) {
				return XmlMarshalUnmarshal.unmarshallingDataTableXml(System.getProperty("user.dir")
						+ "\\src\\main\\resources\\datatables" + File.separator + tableName + ".xml");

			}
		} catch (Exception e) {
			return null;
		}
		return null;

	}

}
