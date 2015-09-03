package edu.sintez.smsmultibanking.app.builder;

import android.util.Log;
import android.widget.Toast;
import edu.sintez.smsmultibanking.app.AtributesBean;
import edu.sintez.smsmultibanking.app.AllActions;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.sms.SmsSender;

/**
 * Created by Max on 17.07.2014.
 */
public class Handler extends MainActivity{

	private int sum; 			//сумма для поплнения моб тел
	private String mobTel; 		//пополняемый моб тел
	private int cardNumber; 	// 4 последние цифры карты пользователя
	private int paySum; 		//сумма перевода (при переводе с одной карты на другую)
	private String transferCurrency;   //валюта перевода
	private String transferNumber;               //номер карты или телефона получателя



	public Handler() {
	}

	public Handler(int cardNumber, int sum, String mobTel) {
		this.cardNumber = cardNumber;
		this.sum = sum;
		this.mobTel = mobTel;
	}

	public Handler(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Handler(int paySum, String transferCurrency, int cardNumber, String transferNumber) {
		this.paySum = paySum;
		this.transferCurrency = transferCurrency;
		this.cardNumber = cardNumber;
		this.transferNumber = transferNumber;
	}






	public void handler() {
		String request = "";

		if (AtributesBean.getInstance().getSelAction().equals(AllActions.REPLENISH_MOB_TEL)){
			BankDataModel bdm = new BankDataModel(String.valueOf(cardNumber), sum, mobTel);
			request = AtributesBean.getInstance().getAbstractOperation().mobileTransfer(bdm);
			String bankPhone = AtributesBean.getInstance().getAbstractOperation().getBankPhone();
			new SmsSender().sendSms(bankPhone, request);
			Log.d("selAction=" , AtributesBean.getInstance().getSelAction());
			Log.d("request=", request);
			Log.d("bankPhone=", bankPhone);

		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.BALANCE)) {
			BankDataModel bdm = new BankDataModel(String.valueOf(cardNumber));
			request = AtributesBean.getInstance().getAbstractOperation().getBalance(bdm);
			String bankPhone = AtributesBean.getInstance().getAbstractOperation().getBankPhone();
			new SmsSender().sendSms(bankPhone, request);
			Log.d("selAction=", AtributesBean.getInstance().getSelAction());
			Log.d("request=", request);
			Log.d("bankPhone=", bankPhone);

		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.BLOCK_CARD)) {
			BankDataModel bdm = new BankDataModel(String.valueOf(cardNumber));
			request = AtributesBean.getInstance().getAbstractOperation().blockCard(bdm);
			String bankPhone = AtributesBean.getInstance().getAbstractOperation().getBankPhone();
			new SmsSender().sendSms(bankPhone, request);
			Log.d("selAction=", AtributesBean.getInstance().getSelAction());
			Log.d("request=", request);
			Log.d("bankPhone=", bankPhone);

		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.UNBLOCK_CARD)) {
			BankDataModel bdm = new BankDataModel(String.valueOf(cardNumber));
			request = AtributesBean.getInstance().getAbstractOperation().unblockCard(bdm);
			String bankPhone = AtributesBean.getInstance().getAbstractOperation().getBankPhone();
			new SmsSender().sendSms(bankPhone, request);
			Log.d("selAction=", AtributesBean.getInstance().getSelAction());
			Log.d("request=", request);
			Log.d("bankPhone=", bankPhone);

		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.MONEY_TRANSFER)) {
			BankDataModel bdm = new BankDataModel(paySum, transferCurrency, String.valueOf(cardNumber), transferNumber);
			request = AtributesBean.getInstance().getAbstractOperation().moneyTransfer(bdm);
			String bankPhone = AtributesBean.getInstance().getAbstractOperation().getBankPhone();
			new SmsSender().sendSms(bankPhone, request);
			Log.d("selAction=", AtributesBean.getInstance().getSelAction());
			Log.d("request=", request);
			Log.d("bankPhone=", bankPhone);
		}



//		} else if (operation.equals("unblockCard")) {
//			request = bank.unblockCard(bdm);
//			Log.i(LOG_TAG, request);
//
//
//		} else if (operation.equals("moneyTransfer")) {
//			request = bank.moneyTransfer(bdm);
//			Log.i(LOG_TAG, request);
//
//
//		} else if (operation.equals("getBalance")) {
//			request = bank.getBalance(bdm);
//			Log.i(LOG_TAG, request);
//
//
//		} else if (operation.equals("mobileTransfer")) {
//			request = bank.mobileTransfer(bdm);
//			Log.i(LOG_TAG, request);
//
//		} else {
//			Log.i(LOG_TAG, request);
//		}

//	new SmsSender().sendSms(request);
	}





}
