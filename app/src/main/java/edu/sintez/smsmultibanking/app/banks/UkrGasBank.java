package edu.sintez.smsmultibanking.app.banks;


import edu.sintez.smsmultibanking.app.builder.AbstractOperations;
import edu.sintez.smsmultibanking.app.builder.BankDataModel;
import edu.sintez.smsmultibanking.app.builder.OperationNotAllowedException;

public class UkrGasBank implements AbstractOperations {

    private String bankPhone = "0504415045";

    @Override
    public String getBalance(BankDataModel model) {
        return new StringBuilder().append("BL").append(" ").append(model.getCardNumber()).toString();
    }

    @Override
    public String blockCard(BankDataModel model) {
        return new StringBuilder().append("SL").append(" ").append(model.getCardNumber()).toString();
    }

    @Override
    public String getReport(BankDataModel model) {
        return new StringBuilder().append("ST").append(" ").append(model.getCardNumber()).toString();
    }

    @Override
    public String moneyTransfer(BankDataModel model) {
        throw new OperationNotAllowedException();
    }

    @Override
    public String unblockCard(BankDataModel model) {
        return new StringBuilder().append("OP").append(" ").append(model.getCardNumber()).toString();
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