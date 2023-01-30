package com.automation.aproject;

import com.automation.common.singleton.CommonProperties;
import com.automation.framework.TestBase;

public class LIB_Common {

	//Common business component for navigate to the URL
	public static final void bc_OpenUrl(final TestBase caller, final String prm_Url, final String prm_WaitTime) {

		caller.writeToReport("Navigated Url "+ prm_Url);
		caller.open(prm_Url);
		caller.pause(prm_WaitTime);
		String CurrentUrl = CommonProperties.getInstance().getWebDriver().getCurrentUrl();
	    caller.writeToReport("Current Url is valid : "+ CurrentUrl);
	}
}
