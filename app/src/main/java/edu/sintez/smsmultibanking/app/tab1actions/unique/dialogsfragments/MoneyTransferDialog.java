package edu.sintez.smsmultibanking.app.tab1actions.unique.dialogsfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import edu.sintez.smsmultibanking.app.AtributesBean;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.builder.BankSelector;
import edu.sintez.smsmultibanking.app.builder.Handler;
import edu.sintez.smsmultibanking.app.db.Card;
import edu.sintez.smsmultibanking.app.db.DBWork;

import java.util.List;

/**
 * Created by Max on 21.07.2014.
 */
public class MoneyTransferDialog extends DialogFragment implements DialogInterface.OnClickListener {

	public static final String LOG_TAG = MoneyTransferDialog.class.getName();

	private String fourLastUserCardNumber; // 4 последние цифры карты пользователя
	private String transferCardNumber; // 16-и значный номер карты, на которую пользователь переводит деньги
	private String transferSum; // сумма для перевода
	private String transferCurrency; // Валюта перевода

	private BankSelector bankSelector;
	private Handler handler;



	public MoneyTransferDialog() {
	}

	public MoneyTransferDialog(String fourLastUserCardNumber, String transferCardNumber, String transferSum, String transferCurrency) {
		this.fourLastUserCardNumber = fourLastUserCardNumber;
		this.transferCardNumber = transferCardNumber;
		this.transferSum = transferSum;
		this.transferCurrency = transferCurrency;
	}

	public MoneyTransferDialog(String transferCardNumber, String transferSum, String transferCurrency) {
		this.transferCardNumber = transferCardNumber;
		this.transferSum = transferSum;
		this.transferCurrency = transferCurrency;
	}



	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("Перевод на другую карты");
		dialog.setPositiveButton("Да", this);
		dialog.setNegativeButton("Нет", this);
		dialog.setMessage("Будет выполнен перевод " + transferSum + " " + transferCurrency + " на карту с номером  " + transferCardNumber);

		return dialog.create();
	}




	public String getBankName(){
		String cardName = AtributesBean.getInstance().getSelCardName();
		Log.d(LOG_TAG, "cardNamre: " + cardName);
		//userCards = new UserCards();
		List<Card> cards = DBWork.getCardsFromDB();
		//List<CardModel> list = new ArrayList<CardModel>();
		//list.add(new CardModel(23, "Работа", "ПриватБанк"));
		//List<Card> cardsFromDB = Card.getCardsFromDB();

		//по имени карты, возвращаем имя банка
		for (int i = 0; i < cards.size(); i++){
			if (cardName.equals( cards.get(i).getNameCard()) ) {
				return cards.get(i).getBankName();
			}
		}
		return "not";
	}


	public String getCardNumber(){
		//по id Card возвратим номер карты
		List<Card> cards = DBWork.getCardsFromDB();
		for (Card card : cards){
			if ( (AtributesBean.getInstance().getIdCard()) == card.getIdCard()){
				return card.getNumCard();
			}
		}
		return "error";
	}




	@Override
	public void onClick(DialogInterface dialog, int which) {
		MainActivity mainActivity = (MainActivity)getActivity();
		switch (which){
			case Dialog.BUTTON_POSITIVE:

				bankSelector = new BankSelector();
				bankSelector.selectBank(getBankName());


				handler = new Handler(Integer.valueOf(transferSum), transferCurrency, Integer.valueOf(fourLastUserCardNumber), transferCardNumber);
				handler.handler();

				//-----------------------
				//	add to history
//				DBWork.addToHistory(AtributesBean.getInstance().getIdCard(), "perevod deneg");
				Log.d(LOG_TAG, "card name = " + AtributesBean.getInstance().getSelCardName());
				Log.d(LOG_TAG, "id  = " + AtributesBean.getInstance().getIdCard());
				//-----------------------
				mainActivity.msgMoneyTransfer(true, transferSum, transferCardNumber, transferCurrency);
				break;
			case Dialog.BUTTON_NEGATIVE:
				mainActivity.msgMoneyTransfer(false, "---", "---", "---");
				break;
		}
	}
}
