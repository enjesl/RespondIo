package com.automation.table.view;


import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Row class
 * 
 * @author Chamin
 *
 */
@XmlRootElement(name = "Row")
public class DataRow {
	
	private List<DataField> fields;
	

	public List<DataField> getFields(){
	       return fields;
	   }
	   
	   @XmlElement(name ="data")
	   public void setFields(List<DataField> fields){
	       this.fields = fields;
	   }
		 
	   @Override
	   public String toString() {
		   return "DataRow ["+ fields + "]";
	}
	   
}
