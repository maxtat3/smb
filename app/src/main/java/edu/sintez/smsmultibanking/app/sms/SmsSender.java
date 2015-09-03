package edu.sintez.smsmultibanking.app.sms;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by Max on 17.07.2014.
 */
public class SmsSender {

	public static final String LOG_TAG = SmsSender.class.getName();

	public boolean sendSms(String bankPhone, String request){
		Log.d(LOG_TAG, "bankPhone + request=" + bankPhone + request);

		SmsManager smsSender = SmsManager.getDefault();
		smsSender.sendTextMessage(bankPhone, null, request , null, null);
		return true;
	}


}
