package edu.sintez.smsmultibanking.app.banks;


import edu.sintez.smsmultibanking.app.builder.AbstractOperations;
import edu.sintez.smsmultibanking.app.builder.BankDataModel;
import edu.sintez.smsmultibanking.app.builder.OperationNotAllowedException;

public class PrivatBank implements AbstractOperations {

    private String bankPhone = "10060";

    @Override
    public String getBalance(BankDataModel model) {
        return String.valueOf(model.getCardNumber());
    }

    @Override
    public String blockCard(BankDataModel model) {
        return new StringBuilder().append("BLOCK").append("+").append(String.valueOf(model.getCardNumber())).toString();
    }

    @Override
    public String getReport(BankDataModel model) {
        throw new OperationNotAllowedException();
    }

    @Override
    public String moneyTransfer(BankDataModel model) {
        return new StringBuilder().append("SEND").append(model.getPaySum()).append(model.getTransferCurrency()).append("+").append(model.getCardNumber()).append("+").append(model.getTransferNumber()).toString();
    }

    @Override
    public String unblockCard(BankDataModel model) {
        return new StringBuilder().append("UNBLOCK").append("+").append(model.getCardNumber()).toString();
    }

    @Override
    public String mobileTransfer(BankDataModel model) {
        return new StringBuilder().append("PAY").append(model.getPaySum()).append("+").append(String.valueOf(model.getCardNumber())).append("+").append(model.getTransferNumber()).toString();
    }

    @Override
    public String getBankPhone() {
        return bankPhone;
    }
}
