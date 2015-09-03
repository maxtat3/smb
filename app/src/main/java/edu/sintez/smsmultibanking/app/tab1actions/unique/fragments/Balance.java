package edu.sintez.smsmultibanking.app.tab1actions.unique.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import edu.sintez.smsmultibanking.app.R;

/**
 * Created by Max on 12.07.2014.
 */
public class Balance extends Fragment{

	public static final String LOG_TAG = Balance.class.getName();

	private double balance = 7510.15;
	private String cardName = "Работа";
	private String bankName = "ПриватБанк";


	public Balance() {
	}

	public Balance(double balance, String cardName, String bankName) {
		this.balance = balance;
		this.cardName = cardName;
		this.bankName = bankName;
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab1_balance, container, false);

		TextView txtBalance =  (TextView)view.findViewById(R.id.txtBalance);
		txtBalance.setText("Баланс  " + balance + " uan");

		TextView txtUserCardName = (TextView)view.findViewById(R.id.txtUserCardName);
		txtUserCardName.setText("Имя карты  " + cardName);

		ImageView imgLogoBank = (ImageView)view.findViewById(R.id.imgLogoBank);
		imgLogoBank.setImageResource(R.drawable.ic_bank_aval);

		return view;
	}

	//	private void viewCurrentBalance(){
//		Log.d(LOG_TAG, "current balance is = ");
//	}

}
