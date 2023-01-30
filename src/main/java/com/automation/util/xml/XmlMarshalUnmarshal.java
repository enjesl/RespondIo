package com.automation.util.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.automation.table.view.DataTable;
import com.automation.table.view.Page;

/**
 * This class responsible for marshal objects to xml data and unmarshal xml data
 * to java objects
 * 
 * @author
 *
 */
public class XmlMarshalUnmarshal {

	public static final Logger logger = LogManager.getLogger(XmlMarshalUnmarshal.class);

	private XmlMarshalUnmarshal() {

	}

	/**
	 * Marshal java objects to xml data
	 * 
	 * @param page {@link Page}
	 */

	/**
	 * Unmarshal xml data to java objects
	 * 
	 * @param filePath {@link String}
	 * 
	 * @return {@link Page}
	 */
	public static Page unmarshallingPageXml(String filePath) {
		Page page = new Page();
		try {
			File file = new File(filePath);
			JAXBContext context = JAXBContext.newInstance(Page.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			page = (Page) unmarshaller.unmarshal(file);
		} catch (JAXBException ex) {

			List<String> strings = printFileContent(filePath);
			if (!strings.isEmpty()) {
				strings.forEach(logger::info);
			}
		}
		return page;
	}

	public static DataTable unmarshallingDataTableXml(String filePath) {
		DataTable dataTable = new DataTable();
		try {
			File file = new File(filePath);
			JAXBContext context = JAXBContext.newInstance(DataTable.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			dataTable = (DataTable) unmarshaller.unmarshal(file);
		} catch (JAXBException ex) {
			logger.info(ex.getMessage());

			List<String> strings = printFileContent(filePath);
			if (!strings.isEmpty()) {
				strings.forEach(logger::info);
			}
		}
		return dataTable;
	}

	/**
	 * Read file
	 * 
	 * @param filePath {@link String}
	 * @return {@link List}
	 */
	private static List<String> printFileContent(String filePath) {
		File file = new File(filePath);
		List<String> lines = new ArrayList<>();
		String line;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			while ((line = br.readLine()) != null) {
				lines.add(line + "\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return lines;
	}
}
