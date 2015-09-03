package edu.sintez.smsmultibanking.app.builder;

import android.util.Log;

public class OperationsHandler {

    private static  final String LOG_TAG = OperationsHandler.class.getName();

    AbstractOperations bank = null;
    String operation = "";

    public AbstractOperations getBank(){
        return bank;
    }

    public void setBank(AbstractOperations b){
        this.bank = b;
    }

    public void setOperation(String oper){
        this.operation = oper;
    }

    public String getOperation(){
        return operation;
    }

    public void handler() {
        String request = "";
        BankDataModel bdm = new BankDataModel("1111222233334444", 1, "9847342354226711", "555", null, -1, -1, "qw");
		if (operation.equals("blockCard")) {
			request = bank.blockCard(bdm);
			Log.i(LOG_TAG, request);


		} else if (operation.equals("unblockCard")) {
			request = bank.unblockCard(bdm);
			Log.i(LOG_TAG, request);


		} else if (operation.equals("moneyTransfer")) {
			request = bank.moneyTransfer(bdm);
			Log.i(LOG_TAG, request);


		} else if (operation.equals("getBalance")) {
			request = bank.getBalance(bdm);
			Log.i(LOG_TAG, request);


		} else if (operation.equals("mobileTransfer")) {
			request = bank.mobileTransfer(bdm);
			Log.i(LOG_TAG, request);

		} else {
			Log.i(LOG_TAG, request);
		}
        //SmsSender.sendSMS(request);

    }

/*
    public  void select2() {
        AbstractOperations bank = null;
        BankDataModel bdm = new BankDataModel("0123000099991111", 10, null, null, null, -1, -1,"name");   // данные вводятся после выбора операции  в диалоге
        String query = null;
        switch ('p') {
            case 'p':
                bank = new PrivatBankOperations();
                break;
            case 'a':
                bank = new BankAvalOperations();
                break;
            case 'P':
                bank = new PumbOperations();
                break;
        }
        switch ('b') {
            case 'B':
                query = bank.blockCard(bdm);
                break;
            case 'b':
                query = bank.getBalance(bdm);
                break;
            case 'r':
                query = bank.getReport(bdm);
                break;
            case 'T':
                query = bank.moneyTransfer(bdm);
                break;
            case 'u':
                query = bank.unblockCard(bdm);
                break;
            case 't':
                query = bank.mobileTransfer(bdm);
                break;
        }
        SmsSender.sendSMS(String.valueOf(bank.getBankPhone()), query);
    }
*/
}
