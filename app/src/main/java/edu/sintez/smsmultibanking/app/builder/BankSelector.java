package edu.sintez.smsmultibanking.app.builder;

import android.util.Log;
import edu.sintez.smsmultibanking.app.AtributesBean;
import edu.sintez.smsmultibanking.app.banks.*;

/**
 * Created by Max on 17.07.2014.
 */
public class BankSelector {

	private static final String LOG_TAG = BankSelector.class.getName();

	public void selectBank (String bankName){
		Log.d(LOG_TAG, "selectBank " + bankName);
		if (bankName.equals("ПриватБанк")){
			AtributesBean.getInstance().setAbstractOperation(new PrivatBank());
		}
		else if (bankName.equals("Укргазбанк")){
			AtributesBean.getInstance().setAbstractOperation(new UkrGasBank());
		}
		else if (bankName.equals("Укрсоцбанк(uniCredit)")){
			AtributesBean.getInstance().setAbstractOperation(new UniCredit());
		}
		else if (bankName.equals("Профин банк")){
			AtributesBean.getInstance().setAbstractOperation(new ProfinBank());
		}
		else if (bankName.equals("ПУМБ")){
			AtributesBean.getInstance().setAbstractOperation(new Pumb());
		}
	}
}
