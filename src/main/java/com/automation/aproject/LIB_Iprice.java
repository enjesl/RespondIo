package com.automation.aproject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.automation.common.singleton.CommonProperties;
import com.automation.framework.TestBase;

public class LIB_Iprice {

	// Common business component for Sort price by decending order
	public static final void bc_SortPriceDecending(final TestBase caller, final String prm_WaitTime, final String prm_Replace) {

		caller.writeToReport("Start price decending function");
		caller.checkElementPresent("pg_IpriceDress.lbl_DressHeader");
		while (!(caller.checkElement("pg_IpriceDress.ele_PriceDecending"))) {
			caller.click("pg_IpriceDress.lnk_Price");
			caller.pause(prm_WaitTime);
		}
		caller.getScreenshot("fullpage", "Price Decending order");
		caller.Compare("pg_IpriceDress.lbl_PriceTag", "", prm_Replace);

	}
	
	// Common business component for Select Brands
		public static final void bc_SelectBrand(final TestBase caller, final String prm_Message) {

			caller.writeToReport("Start function select brands");
			caller.checkElementPresent("PG_IpriceLaptop.lnk_Brands");
			caller.click("PG_IpriceLaptop.lnk_Brands");
			caller.click("PG_IpriceLaptop.lnk_BrandType");
			caller.click("PG_IpriceLaptop.lnk_Brands");
			caller.getScreenshot("window", prm_Message);
			List<WebElement> elements = CommonProperties.getInstance().getWebDriver().findElements(By.xpath("//a/div/div/h3[contains(text(),'Dell')]"));
			int elementCount = elements.size();
			System.out.println("Number of elements: " + elementCount);
			for(int i=0;i<elementCount;i++) {
			    WebElement element = elements.get(i);
			    caller.checkElement("PG_IpriceLaptop.lnk_Brands["+i+"]");
			}
			caller.checkElement("PG_IpriceLaptop.lnk_Brands["+1+"]");
			caller.pause("3000");
			caller.getScreenshot("fullpage", prm_Message);
			caller.pause("3000");
			
		}
		
		// Common business component for Search device
				public static final void bc_SearchDevice(final TestBase caller, final String prm_SearchTerm, final String prm_Message) {

					caller.writeToReport("Start function Search device iphone 14");
					caller.checkElementPresent("pg_IpriceIphone.tf_Search");
					caller.type("pg_IpriceIphone.tf_Search",prm_SearchTerm);
					caller.click("pg_IpriceIphone.btn_Search");
					List<WebElement> elements = CommonProperties.getInstance().getWebDriver().findElements(By.xpath("//h3[contains(text(),'Apple iPhone 14')]"));
					int elementCount = elements.size();
					System.out.println("Number of elements: " + elementCount);
					for(int i=0;i<elementCount;i++) {
					    WebElement element = elements.get(i);
					    caller.checkElement("pg_IpriceIphone.btn_Search["+i+"]");
					}
					caller.pause("3000");
					caller.getScreenshot("fullpage", prm_Message);
					caller.pause("3000");
					
				}
			

}
