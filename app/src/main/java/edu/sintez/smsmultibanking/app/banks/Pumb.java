package edu.sintez.smsmultibanking.app.banks;

import edu.sintez.smsmultibanking.app.builder.AbstractOperations;
import edu.sintez.smsmultibanking.app.builder.BankDataModel;
import edu.sintez.smsmultibanking.app.builder.OperationNotAllowedException;

public class Pumb implements AbstractOperations {

    private String bankPhone = "4682";

    @Override
    public String getBalance(BankDataModel model) {
        return String.valueOf(model.getAccessCode());
    }

    @Override
    public String blockCard(BankDataModel model) {
        return "BLOCK";
    }

    @Override
    public String getReport(BankDataModel model) {
        throw new OperationNotAllowedException("String msg");
    }

    @Override
    public String moneyTransfer(BankDataModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String unblockCard(BankDataModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String mobileTransfer(BankDataModel model) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getBankPhone() {
        return bankPhone;
    }
}