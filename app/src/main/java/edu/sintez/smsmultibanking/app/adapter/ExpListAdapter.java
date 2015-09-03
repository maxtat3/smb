package edu.sintez.smsmultibanking.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.sintez.smsmultibanking.app.R;
import edu.sintez.smsmultibanking.app.db.Card;
import edu.sintez.smsmultibanking.app.db.DBWork;

import java.util.ArrayList;
import java.util.List;


public class ExpListAdapter extends BaseExpandableListAdapter {

	private static final String LOG_TAG = ExpListAdapter.class.getName();

	private ArrayList <ArrayList<String> > groups;
	private Context context;



	//хранит все карты ползователя, а у каждой карты есть свои поля(имя, название, номер ...)
	private List<Card> cards;

	// хранит локальную копию всех ИМЕН карт пользователя (для заполнения LIstVew)
	List<String> cardsNamesList = new ArrayList<String>();

	//хранит имена банков (для соотв карт)
	List<String> banksNamesList = new ArrayList<String>();

	//хранит историю по конкретной карте
	List<String> cardHistory = new ArrayList<String>();




	public ExpListAdapter(Context context, ArrayList< ArrayList<String> > groups) {
		this.context = context;
		this.groups = groups;

		//---------------------------------------
		//работа с бд
		cards = DBWork.getCardsFromDB();
		for (Card card : cards){
			cardsNamesList.add( card.getNameCard() );
			banksNamesList.add( card.getBankName() );
		}
		//---------------------------------------
//		Log.d(LOG_TAG, "banks names = " + banksNamesList);
//		Log.d(LOG_TAG, "created ExpListAdapter");
	}



	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}




	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		BankHolder holder = null;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pattern_tab2_mycard_group_view, null);

			holder = new BankHolder();
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon);
			holder.txtGroup1CardName = (TextView) convertView.findViewById(R.id.txtCardName);
			holder.textGroup2BankName = (TextView) convertView.findViewById(R.id.txtBankName);
			holder.textGroup3LastSumCheckedBalance = (TextView) convertView.findViewById(R.id.txtLastSumCheckedBalance);
			holder.textGroup4LastDateCheckedBalance = (TextView) convertView.findViewById(R.id.txtLastDateCheckedBalance);

			convertView.setTag(holder);
		} else {
			holder = (BankHolder) convertView.getTag();
		}


		if ( groupPosition < cardsNamesList.size() ) {
//			Log.d(LOG_TAG, "groupPosition = " + groupPosition);

//			// показать логотип банка в Tab2
			if (banksNamesList.get(groupPosition).equals("ПриватБанк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_privat);

			}else if (banksNamesList.get(groupPosition).equals("Укрсоцбанк(uniCredit)")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_unicredit);

			}else if (banksNamesList.get(groupPosition).equals("Укрэксимбанк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_exim);

			}else if (banksNamesList.get(groupPosition).equals("Аваль")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_aval);

			}else if (banksNamesList.get(groupPosition).equals("Кредобанк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_kredo);

			}else if (banksNamesList.get(groupPosition).equals("Марфин Банк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_marfin);

			}else if (banksNamesList.get(groupPosition).equals("Правэкс-Банк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_pravex);

			}else if (banksNamesList.get(groupPosition).equals("Профин банк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_profin);

			}else if (banksNamesList.get(groupPosition).equals("ПУМБ")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_pumb);

			}else if (banksNamesList.get(groupPosition).equals("Укргазбанк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_ukrgas);

			}else if (banksNamesList.get(groupPosition).equals("Укринбанк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_ukrin);

			}else if (banksNamesList.get(groupPosition).equals("УкрСиббанк")){
				holder.imgIcon.setImageResource(R.drawable.ic_bank_ukrsib);

			}

//			holder.imgIcon.setImageResource(R.drawable.ic_bank_privat);
			holder.txtGroup1CardName.setText(cardsNamesList.get(groupPosition));
			holder.textGroup2BankName.setText(banksNamesList.get(groupPosition));
			holder.textGroup3LastSumCheckedBalance.setText(""); //последний проверенный баланс
			holder.textGroup4LastDateCheckedBalance.setText("История"); //дата и время последний проверенный баланс
		}



		if (isExpanded) {
			//Изменяем что-нибудь, если текущая Group раскрыта
		} else {
			//Изменяем что-нибудь, если текущая Group скрыта
		}

		return convertView;
	}



	//Метод для получения (просмотра) истории о выбранной карте.
	//Вызывается несколько раз, когда мы нажимаем на любую из карт в [мои карты].
	//Метод вызывается столько раз, сколько есть элементов expandableListView, например 11 элементов = 11 вызовов.
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//		Log.d(LOG_TAG, "==============called: getChildView");
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pattern_tab2_mycard_child_view, null);
		}

		TextView textChild = (TextView) convertView.findViewById(R.id.textViewChieldView);


		// cards group - выводим историю по кадой карте
		if (groupPosition < cards.size()){

			Card card = cards.get(groupPosition);
			int idCard = card.getIdCard();
			cardHistory = DBWork.getHistory(idCard);

			//history for this card
			if (childPosition < cardHistory.size()){
				textChild.setText(cardHistory.get(childPosition));
			}
		}


		return convertView;
	}



	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}




	static class BankHolder {
		ImageView imgIcon;
		TextView txtGroup1CardName;
		TextView textGroup2BankName;
		TextView textGroup3LastSumCheckedBalance;
		TextView textGroup4LastDateCheckedBalance;
	}
}