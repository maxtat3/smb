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
public class BlockCardDialog extends DialogFragment implements DialogInterface.OnClickListener {

	private static final String LOG_TAG = BlockCardDialog.class.getName();

	private String cardName = "Работа";
	private String bankName = "ПриватБанк";

	private BankSelector bankSelector;
	private Handler handler;


	public BlockCardDialog(String cardName, String bankName) {
		this.cardName = cardName;
		this.bankName = bankName;
	}






	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		dialog.setTitle("Блокирование карты");
		dialog.setMessage("Вы действительно хотите заблокировать карту " + cardName + "(" + bankName + ")");
		dialog.setPositiveButton("Да", this);
		dialog.setNegativeButton("Нет", this);

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


				handler = new Handler(Integer.valueOf(getCardNumber()));
				handler.handler();

//				DBWork.addToHistory(AtributesBean.getInstance().getIdCard(), "blocking card");

				mainActivity.msgBlockCard(true, cardName, bankName);
				break;
			case Dialog.BUTTON_NEGATIVE:
				mainActivity.msgBlockCard(false, "---", "---");
				break;
		}
	}
}
