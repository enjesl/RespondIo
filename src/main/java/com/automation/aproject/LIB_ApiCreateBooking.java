package com.automation.aproject;

import com.automation.common.singleton.CommonProperties;
import com.automation.framework.TestBase;

import io.restassured.response.Response;

public class LIB_ApiCreateBooking {

	// Common business component for navigate to the URL
	public static final void bc_CreateBooking(final TestBase caller, final String prm_EndPoint,
			final String prm_Headers, final String prm_Method, final String prm_Body, final String prm_ExpectedCode,
			final String prm_firstname, final String prm_lastname, final int prm_totalprice,
			final boolean prm_depositpaid, final String prm_checkin, final String prm_checkout,
			final String prm_additionalneeds) {

		caller.writeToReport("API endpoin " + prm_EndPoint);
		Response CreateBooking = caller.callWebServiceRestAssured(prm_EndPoint, prm_Headers, prm_Method, "", prm_Body,
				"", "", "");
		caller.checkResponseCode(prm_ExpectedCode, CreateBooking);
		String responseBody = CreateBooking.getBody().print();
		String statusCode = Integer.toString(CreateBooking.getStatusCode());
		if (statusCode.equals("200")) {
			caller.writeToReport(responseBody);
			caller.checkRestAssuredValues(CreateBooking, "booking.firstname", prm_firstname);
			caller.checkRestAssuredValues(CreateBooking, "booking.lastname", prm_lastname);
			if (CreateBooking.getBody().jsonPath().get("booking.totalprice").equals(prm_totalprice)) {
				caller.writeToReport(CreateBooking.getBody().jsonPath().get("booking.totalprice")
						+ "Equel to matched value:" + prm_totalprice);
			} else {
				caller.writeToReportFail(CreateBooking.getBody().jsonPath().get("booking.totalprice")
						+ "Not matched to expected value:" + prm_totalprice);
			}
			if (CreateBooking.getBody().jsonPath().get("booking.depositpaid").equals(prm_depositpaid)) {
				caller.writeToReport(CreateBooking.getBody().jsonPath().get("booking.depositpaid")
						+ "Equel to matched value:" + prm_depositpaid);
			} else {
				caller.writeToReportFail(CreateBooking.getBody().jsonPath().get("booking.depositpaid")
						+ "Not matched to expected value:" + prm_depositpaid);
			}
			caller.checkRestAssuredValues(CreateBooking, "booking.bookingdates.checkin", prm_checkin);
			caller.checkRestAssuredValues(CreateBooking, "booking.bookingdates.checkout", prm_checkout);
			caller.checkRestAssuredValues(CreateBooking, "booking.additionalneeds", prm_additionalneeds);
		} else if (statusCode.equals("400")) {

			caller.writeToReport(responseBody);
		}

		else if (statusCode.equals("500")) {

			caller.writeToReport(responseBody);
		}
	}
}
