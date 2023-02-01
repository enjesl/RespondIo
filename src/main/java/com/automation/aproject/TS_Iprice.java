package com.automation.aproject;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.framework.TestBase;
import com.automation.framework.data.DataReader;

public class TS_Iprice extends TestBase {

	@DataProvider(name = "tc_IpriceDress")
	public Object[][] dataTables_dt_IpriceDress() {
		return getTableArray(DataReader.getDataTable("dt_IpriceDress"));
	}

	@Test(dataProvider = "tc_IpriceDress")
	public final void tc_IpriceDress(final String dt_IpriceDress_url, final String dt_IpriceDress_WaitTime,
			final String dt_IpriceDress_ReplaceValue) {
		LIB_Common.bc_OpenUrl(this, dt_IpriceDress_url, dt_IpriceDress_WaitTime);
		LIB_Iprice.bc_SortPriceDecending(this, dt_IpriceDress_WaitTime, dt_IpriceDress_ReplaceValue);
	}
	
	@DataProvider(name = "tc_IpriceLaptop")
	public Object[][] dataTables_dt_IpriceLaptop() {
		return getTableArray(DataReader.getDataTable("dt_IpriceLaptop"));
	}

	@Test(dataProvider = "tc_IpriceLaptop")
	public final void tc_IpriceLaptop(final String dt_IpriceDress_url, final String dt_IpriceDress_WaitTime,
			final String dt_IpriceDress_Message) {
		LIB_Common.bc_OpenUrl(this, dt_IpriceDress_url, dt_IpriceDress_WaitTime);
		LIB_Iprice.bc_SelectBrand(this, dt_IpriceDress_Message);
	}
	
	@DataProvider(name = "tc_IpriceIphone")
	public Object[][] dataTables_dt_IpriceIphone() {
		return getTableArray(DataReader.getDataTable("dt_IpriceIphone"));
	}

	@Test(dataProvider = "tc_IpriceIphone")
	public final void tc_IpriceIphone(final String dt_IpriceIphone_url, final String dt_IpriceIphone_WaitTime,
			final String dt_IpriceIphone_SearchTerm, final String dt_IpriceIphone_Message) {
		LIB_Common.bc_OpenUrl(this, dt_IpriceIphone_url, dt_IpriceIphone_WaitTime);
		LIB_Iprice.bc_SearchDevice(this, dt_IpriceIphone_SearchTerm, dt_IpriceIphone_Message);
	}
}
