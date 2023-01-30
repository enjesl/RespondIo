package com.automation.aproject;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.automation.framework.TestBase;
import com.automation.framework.data.DataReader;

public class TS_WebService extends TestBase {
	@DataProvider(name = "tc_CreateBooking")
	public Object[][] dataTable_dt_CreateBooking() {
		return getTableArray(DataReader.getDataTable("dt_CreateBooking"));
	}

	@Test(dataProvider = "tc_CreateBooking")
	public final void tc_CreateBooking(final String endPoint, final String headers, final String method,
			final String body, final String expectedCode, final String firstName, final String lastName,
			final String totalPrice, final String depositPaid, final String checkIn, final String checkOut,
			final String additionalNeeds) {
		LIB_ApiCreateBooking.bc_CreateBooking(this, endPoint, headers, method, body, expectedCode, firstName, lastName,
				Integer.parseInt(totalPrice), Boolean.parseBoolean(depositPaid), checkIn, checkOut, additionalNeeds);
	}
}