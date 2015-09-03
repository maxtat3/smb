package edu.sintez.smsmultibanking.app.banks;

import edu.sintez.smsmultibanking.app.builder.AbstractOperations;
import edu.sintez.smsmultibanking.app.builder.BankDataModel;
import edu.sintez.smsmultibanking.app.builder.OperationNotAllowedException;

public class ProfinBank implements AbstractOperations {

    private String bankPhone = "4682";

    @Override
    public String getBalance(BankDataModel model) {
        return model.getAccessCode();
    }

    @Override
    public String blockCard(BankDataModel model) {
        return new StringBuilder().append("BLOCK").append(" ").append(model.getCardNumber()).toString();
    }

    @Override
    public String getReport(BankDataModel model) {
        throw new OperationNotAllowedException();
    }

    @Override
    public String moneyTransfer(BankDataModel model) {
        throw new OperationNotAllowedException();
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
