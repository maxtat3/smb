package edu.sintez.smsmultibanking.app.sms.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.banks.PrivatBank;

import java.util.ArrayList;

public class IncomingSMS extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {

		final Bundle bundle = intent.getExtras();
		try {
			if (bundle != null) {
				//Получаем protocol data unit полученной СМСки
				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {
					SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
					//получаем номер телефона
					String phoneNumber = currentMessage.getOriginatingAddress();
					//проверяем номер отправителя - является ли он номером какого-то банка
					Log.i("TAG", "senderNum: " + phoneNumber);
					if (checkNumber(phoneNumber)) {
						//если да, то производим перехват СМСки - в стандартное приложение оно не дойдет
						abortBroadcast();
						//получаем тело сообщения
						Log.i("TAG", "senderNum: " + phoneNumber);
						String message = currentMessage.getDisplayMessageBody();
						//отправляем полученные данные в Activity
						Intent mIntent = new Intent(context, MainActivity.class);

						mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						mIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

						mIntent.putExtra("phone", phoneNumber);
						mIntent.putExtra("message", message);
						context.startActivity(mIntent);
					}
				}
			}

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);

		}
	}

	private boolean checkNumber(String number) {
		ArrayList<String> listOfNumbers = new ArrayList<String>();
		listOfNumbers.add(new PrivatBank().getBankPhone());
		listOfNumbers.add("+380936815824");
		listOfNumbers.add("5433");
		Log.i("DATA from ArrayList: ", listOfNumbers.get(0) + "  " + listOfNumbers.get(1) + "  " + listOfNumbers.get(2));

		for (int i = 0; i < listOfNumbers.size(); i++) {
			String listOfPhone = listOfNumbers.get(i);
			Log.i("TAG", "ELEMENT OF listOfPhones: " + listOfPhone);
			if (number.equals(listOfPhone)) {
				return true;
			}
		}
		return false;
	}
}