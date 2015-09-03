package edu.sintez.smsmultibanking.app.banks;


import edu.sintez.smsmultibanking.app.builder.AbstractOperations;
import edu.sintez.smsmultibanking.app.builder.BankDataModel;
import edu.sintez.smsmultibanking.app.builder.OperationNotAllowedException;

public class UniCredit implements AbstractOperations {

    private String bankPhone = "332";

    @Override
    public String getBalance(BankDataModel model) {
        return new StringBuilder().append("BAL").append(" ").append(model.getCardNumber()).toString();
    }

    @Override
    public String blockCard(BankDataModel model) {
        return new StringBuilder().append("BLOCK").append(" ").append(model.getCardNumber()).toString();
    }

    @Override
    public String getReport(BankDataModel model) {
        return new StringBuilder().append("STM").append(" ").append(model.getCardNumber()).toString();
    }

    @Override
    public String moneyTransfer(BankDataModel model) {
        return new StringBuilder().append("TRANS").append(" ").append(model.getCardNumber()).append(" ").append(model.getTransferNumber()).append(" ").append(model.getPaySum()).append(model.getTransferCurrency()).toString();
    }

    @Override
    public String unblockCard(BankDataModel model) {
        throw new OperationNotAllowedException();
    }

    @Override
    public String mobileTransfer(BankDataModel model) {
        throw new OperationNotAllowedException();
    }

    @Override
    public String getBankPhone() {
        return bankPhone;
    }
}

