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
 * Created by Max on 22.07.2014.
 */
public class BalanceDialog extends DialogFragment implements DialogInterface.OnClickListener {

	public static final String LOG_TAG = BalanceDialog.class.getName();

//	private String cardName = "Работа";
//	private String bankName = "ПриватБанк";
	private String cardName;
	private String bankName;

	private BankSelector bankSelector;
	private Handler handler;


	public BalanceDialog(String cardName, String bankName) {
		this.cardName = cardName;
		this.bankName = bankName;
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
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("Баланс");
		dialog.setMessage("Выполнить запрос баланса по карте " + cardName + "(" + bankName + ")");
		dialog.setPositiveButton("Да", this);
		dialog.setNegativeButton("Нет", this);

		return dialog.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		MainActivity mainActivity = (MainActivity)getActivity();
		switch (which){
			case Dialog.BUTTON_POSITIVE:

				bankSelector = new BankSelector();
				bankSelector.selectBank(getBankName());


				handler = new Handler( Integer.valueOf(getCardNumber()) );
				handler.handler();

				mainActivity.msgGetBalance(true);
				break;
			case Dialog.BUTTON_NEGATIVE:
				mainActivity.msgGetBalance(false);
				break;
		}
	}
}
