package edu.sintez.smsmultibanking.app.tab1actions.unique.dialogsfragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import edu.sintez.smsmultibanking.app.AtributesBean;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.builder.BankSelector;
import edu.sintez.smsmultibanking.app.builder.Handler;
import edu.sintez.smsmultibanking.app.db.Card;
import edu.sintez.smsmultibanking.app.db.DBWork;
import edu.sintez.smsmultibanking.app.tab1actions.common.fragments.UserCards;

import java.util.List;

/**
 * Created by Max on 16.07.2014.
 */
public class ReplenishMobTelDialog extends DialogFragment implements DialogInterface.OnClickListener{

	private static final String LOG_TAG = ReplenishMobTelDialog.class.getName();

	private int sum;
	private String mobTel;
	private BankSelector bankSelector;
	private Handler handler;
	private UserCards userCards;


	public ReplenishMobTelDialog() {
	}

	public ReplenishMobTelDialog(String mobTel, int sum) {
		this.sum = sum;
		this.mobTel = mobTel;
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
		dialog.setTitle("Пополнение мобильного телефона");
		dialog.setPositiveButton("Да", this);
		dialog.setNegativeButton("Нет", this);
		dialog.setMessage("Будет произведено пополнение номера " + mobTel + " на сумму " + String.valueOf(sum) + " UAH");

		return dialog.create();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		MainActivity mainActivity = (MainActivity)getActivity();
		switch (which){
			case Dialog.BUTTON_POSITIVE:
				Log.d(LOG_TAG, "dialog fragment - OK");

				//-----------------------
				//	add to history
//				DBWork.addToHistory(AtributesBean.getInstance().getIdCard(), "popolnenie mob tel");
				Log.d(LOG_TAG, "card name = " + AtributesBean.getInstance().getSelCardName());
				Log.d(LOG_TAG, "id  = " + AtributesBean.getInstance().getIdCard());
				//-----------------------

				bankSelector = new BankSelector();
				bankSelector.selectBank(getBankName());



				handler = new Handler(Integer.valueOf(getCardNumber()) ,sum, mobTel);
				handler.handler();

				mainActivity.msgReplMobTel(true);
				mainActivity.showTab1Action();
				break;
			case Dialog.BUTTON_NEGATIVE:
				Log.d(LOG_TAG, "dialog fragment - NO");
				mainActivity.msgReplMobTel(false);
				break;
		}
	}
}
