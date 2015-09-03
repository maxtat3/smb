package edu.sintez.smsmultibanking.app.tab1actions.unique.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import edu.sintez.smsmultibanking.app.AtributesBean;
import edu.sintez.smsmultibanking.app.CustomToast;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.R;
import edu.sintez.smsmultibanking.app.db.Card;
import edu.sintez.smsmultibanking.app.db.DBWork;
import edu.sintez.smsmultibanking.app.tab1actions.unique.dialogsfragments.MoneyTransferDialog;

import java.util.List;

/**
 * Created by Max on 16.07.2014.
 */
public class MoneyTransfer extends Fragment {

	private static final String LOG_TAG = MoneyTransfer.class.getName();
	private static final int SHOW_TOAST_TIME = 2;

	private EditText editTransferCardNumberMT; // 16-и значный номер карты, на которую пользователь переводит деньги
	private EditText editTransferSumMT; // сумма для перевода
	private CheckBox cbUan;
	private CheckBox cbUsd;
	private CheckBox cbEur;
	private CheckBox cbRus;
	private String transferCurrency = ""; // Валюта перевода
	private Button btnMoneyTransferMT; //выполнить перевод
	private MainActivity mainActivity;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab1_money_transfer, null);
		mainActivity = (MainActivity)getActivity();

		editTransferCardNumberMT = (EditText)view.findViewById(R.id.editTransferCardNumberMT);
		editTransferSumMT = (EditText)view.findViewById(R.id.editTransferSumMT);

		cbUan = (CheckBox)view.findViewById(R.id.cbUan);
		cbUsd = (CheckBox)view.findViewById(R.id.cbUsd);
		cbEur = (CheckBox)view.findViewById(R.id.cbEur);
		cbRus = (CheckBox)view.findViewById(R.id.cbRus);

		cbUan.setOnCheckedChangeListener(listener);
		cbUsd.setOnCheckedChangeListener(listener);
		cbEur.setOnCheckedChangeListener(listener);
		cbRus.setOnCheckedChangeListener(listener);

		btnMoneyTransferMT = (Button)view.findViewById(R.id.btnMoneyTransferMT);
		btnMoneyTransferMT.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//еслт все проверки прошли - показываем диалог
				if (checkFields()) {
					MoneyTransferDialog dialog = new MoneyTransferDialog(
							getCardNumber(),
							editTransferCardNumberMT.getText().toString(),
							editTransferSumMT.getText().toString(),
							transferCurrency
					);
					dialog.show(getFragmentManager(), "moneyTransfer");
				}
			}
		});

		return view;
	}


	public CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked){
				switch (buttonView.getId()){
					case R.id.cbUan:
						cbUan.setChecked(true);
						cbUsd.setChecked(false);
						cbEur.setChecked(false);
						cbRus.setChecked(false);
						transferCurrency = "UAH";
						Log.d(LOG_TAG, "uan");
						break;
					case R.id.cbUsd:
						cbUan.setChecked(false);
						cbUsd.setChecked(true);
						cbEur.setChecked(false);
						cbRus.setChecked(false);
						transferCurrency = "USD";
						Log.d(LOG_TAG, "usd");
						break;
					case R.id.cbEur:
						cbUan.setChecked(false);
						cbUsd.setChecked(false);
						cbEur.setChecked(true);
						cbRus.setChecked(false);
						transferCurrency = "EUR";
						Log.d(LOG_TAG, "eur");
						break;
					case R.id.cbRus:
						cbUan.setChecked(false);
						cbUsd.setChecked(false);
						cbEur.setChecked(false);
						cbRus.setChecked(true);
						transferCurrency = "RUS";
						Log.d(LOG_TAG, "rus");
						break;
				}
			}
		}
	};


	public String getCardNumber(){
		//по id Card возвратим номер карты
		List<Card> cards = DBWork.getCardsFromDB();
		for (Card card : cards){
			if ( (AtributesBean.getInstance().getIdCard()) == card.getIdCard()){
				Log.d(LOG_TAG, "cardNumber(last) dig = " + card.getNumCard());
				return card.getNumCard();
			}
		}
		return "error";
	}


	/**
	 * проверка на допустимые значения
	 * @return - результат проверки
	 */
	private boolean checkFields(){

		// 1.1 введен ли номер карты получателя (проверка на пустоту) ?
		if ( !editTransferCardNumberMT.getText().toString().isEmpty() ){

			// 1.2 введены ли именно 16-цифр номера карты получателя ?
			if( editTransferCardNumberMT.getText().toString().length() == 16 ){

				// 2 введена ли сумма перевода (проверка на пустоту) ?
				if ( !editTransferSumMT.getText().toString().isEmpty() ){

					// 3 выбрана ли валюта ?
					if ( !transferCurrency.isEmpty() ){

						//show money transfer dialog
						return true;
					} else {
						CustomToast.makeToast(mainActivity.getApplicationContext(), "Выберите валюту !", SHOW_TOAST_TIME);
						return false;
					}
				} else {
					CustomToast.makeToast(mainActivity.getApplicationContext(), "Введите сумму первода !", SHOW_TOAST_TIME);
					return false;
				}
			} else {
				CustomToast.makeToast(mainActivity.getApplicationContext(), "Номер карты получателя должен состоять из 16-и цифр !", SHOW_TOAST_TIME);
				return false;
			}
		} else {
			CustomToast.makeToast(mainActivity.getApplicationContext(), "Введите номер карты получателя !", SHOW_TOAST_TIME);
			return false;
		}

	}


}
