package edu.sintez.smsmultibanking.app.tab1actions.common.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.sintez.smsmultibanking.app.AllActions;
import edu.sintez.smsmultibanking.app.AtributesBean;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.R;
import edu.sintez.smsmultibanking.app.db.Card;
import edu.sintez.smsmultibanking.app.db.DBWork;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Max on 11.07.2014.
 *
 * Таб: Действия
 * Фрагмент 2 - список карт, которые добавил/добавляет пользователь
 * в процессе использования приложения.
 * Этот фрагмент всегда будет показватся вторым (после фрагмента 1 - список возможных действий)
 * вне зависимости от настроек и т.д. !!!
 * Если пользователь не добавил ни одной карты - показать textView.setText("Нет добавленных карт")
 */
public class UserCards extends Fragment {

	private static final String LOG_TAG = UserCards.class.getName();

	private MainActivity mainActivity;
	private Button btnAddCard;
	private ArrayAdapter<String> arrayAdapter; //????????
	private ListView listViewUserCards;
	CardsAdapter cardsAdapter;


	//хранит все карты ползователя, а у каждой карты есть свои поля(имя, название, номер ...)
	private List<Card> cardsFromDB;

	// хранит локальную копию всех ИМЕН карт пользователя (для заполнения LIstVew)
	List<String> cardsNamesList = new ArrayList<String>();

	// хранит локальную копию всех ID карт пользователя
	List<Integer> cardId = new ArrayList<Integer>();

	//Хранит имя банка, для выбранной карты
	List<String> bankNames = new ArrayList<String>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_tab1_user_cards, null);

		mainActivity = (MainActivity)getActivity();
		mainActivity.isActionsFragmentsShow = false;

		fillUserCardsAttributesFromDB();

//		arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.pattern_tab1_user_cards_list_view_row, cardsNamesList);
		cardsAdapter = new CardsAdapter(getActivity(), cardsFromDB);
		listViewUserCards = (ListView) view.findViewById(R.id.listViewUserCards);
		listViewUserCards.setAdapter(cardsAdapter);
		listViewUserCards.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


				Log.d(LOG_TAG, "in UserCards listView selected pos = " + position);

				// запоминаем в bean , id выбранной карты из ListView
				// id карты соответствует позиции в listView
				AtributesBean.getInstance().setIdCard(cardId.get(position));
				Log.d(LOG_TAG, "id card = " + AtributesBean.getInstance().getIdCard());

				// запоминаем в bean , имя  выбранной карты из ListView
				AtributesBean.getInstance().setSelCardName(cardsNamesList.get(position));
				Log.d(LOG_TAG, "card name = " + AtributesBean.getInstance().getSelCardName());

				// запоминаем в bean , имя банка для выбранной карты
				AtributesBean.getInstance().setSelBankName(bankNames.get(position));
				Log.d(LOG_TAG, "bank name = " + AtributesBean.getInstance().getSelBankName());


				// переход к действию (уникальному фрагменту), которое было выбрано в первом фрагменте (ActionFragment)
				if(checkAllowBank()){
					mainActivity.showTab1SelUniqueFr(AtributesBean.getInstance().getSelAction(), getFragmentManager());
				}else {
					mainActivity.showTab1Action();
				}
			}
		});


		//обработчик для клавиши "добавить карту" [действия - карты пользователя]
		btnAddCard = (Button)view.findViewById(R.id.btnAddCardActionFragment);
		btnAddCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity mainActivity = (MainActivity) getActivity();
				mainActivity.showTab1AddCard();
			}
		});
		return view;
	}


	/**
	 * Класс - замена  ArrayAdapter<String> arrayAdapter
	 * Нужен для заполнения "карт пользователя" в табе "Действия"
	 * значок банка - имя карты - имя банка
	 */
	public class CardsAdapter extends BaseAdapter {
		Context context;
		List<Card> myList;
		LayoutInflater inflater1;

		public CardsAdapter(Context context, List<Card> myList) {
			this.context = context;
			this.myList = myList;
			inflater1 = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return myList.size();
		}

		public Object getItem(int position) {
			return myList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View row = convertView;
			if (row == null) {
				row = inflater1.inflate(R.layout.pattern_tab1_user_cards_list_view_row, parent, false);
			}

			//В icon устанавливать нужные иконки банка, как в MyCards
			ImageView icon = (ImageView)row.findViewById(R.id.imgIcon);

			String bankName = cardsFromDB.get(position).getBankName();

			if (bankName.equals("ПриватБанк")){
				icon.setImageResource(R.drawable.ic_bank_privat);

			}else if (bankName.equals("Укрсоцбанк(uniCredit)")){
				icon.setImageResource(R.drawable.ic_bank_unicredit);

			}else if (bankName.equals("Укрэксимбанк")){
				icon.setImageResource(R.drawable.ic_bank_exim);

			}else if (bankName.equals("Аваль")){
				icon.setImageResource(R.drawable.ic_bank_aval);

			}else if (bankName.equals("Кредобанк")){
				icon.setImageResource(R.drawable.ic_bank_kredo);

			}else if (bankName.equals("Марфин Банк")){
				icon.setImageResource(R.drawable.ic_bank_marfin);

			}else if (bankName.equals("Правэкс-Банк")){
				icon.setImageResource(R.drawable.ic_bank_pravex);

			}else if (bankName.equals("Профин банк")){
				icon.setImageResource(R.drawable.ic_bank_profin);

			}else if (bankName.equals("ПУМБ")){
				icon.setImageResource(R.drawable.ic_bank_pumb);

			}else if (bankName.equals("Укргазбанк")){
				icon.setImageResource(R.drawable.ic_bank_ukrgas);

			}else if (bankName.equals("Укринбанк")){
				icon.setImageResource(R.drawable.ic_bank_ukrin);

			}else if (bankName.equals("УкрСиббанк")){
				icon.setImageResource(R.drawable.ic_bank_ukrsib);

			}

			TextView cardName = (TextView) row.findViewById(R.id.cardName);
			TextView cardBank = (TextView) row.findViewById(R.id.cardBank);
			cardName.setText(cardsFromDB.get(position).getNameCard());
			cardBank.setText(cardsFromDB.get(position).getBankName());


			return row;
		}
	}


	/**
	 * Метод заполняет локальные Arraylist-ы из БД
	 */
	private void fillUserCardsAttributesFromDB(){
		//читаем с бд все карты (в наше случае одну)
		cardsFromDB = DBWork.getCardsFromDB();
		for (Card card : cardsFromDB) {
			//добавляем id, имена карт, имена банков (для соответствующих карт) - всех карт пользователя в локальные коллекции
			cardId.add(card.getIdCard());
			cardsNamesList.add(card.getNameCard());
			bankNames.add(card.getBankName());
		}
	}


	/**
	 * Простая замена матрицы операций
	 * @return
	 */
	public boolean checkAllowBank(){
		if (AtributesBean.getInstance().getSelAction().equals(AllActions.REPLENISH_MOB_TEL)
				&& AtributesBean.getInstance().getSelBankName().equals("Укргазбанк")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.MONEY_TRANSFER)
				&& AtributesBean.getInstance().getSelBankName().equals("Укргазбанк")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.UNBLOCK_CARD)
				&& AtributesBean.getInstance().getSelBankName().equals("Укрсоцбанк(uniCredit)")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.REPLENISH_MOB_TEL)
				&& AtributesBean.getInstance().getSelBankName().equals("Укрсоцбанк(uniCredit)")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.BALANCE)
				&& AtributesBean.getInstance().getSelBankName().equals("Профин банк")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.REPLENISH_MOB_TEL)
				&& AtributesBean.getInstance().getSelBankName().equals("Профин банк")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.UNBLOCK_CARD)
				&& AtributesBean.getInstance().getSelBankName().equals("Профин банк")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.MONEY_TRANSFER)
				&& AtributesBean.getInstance().getSelBankName().equals("Профин банк")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.BALANCE)
				&& AtributesBean.getInstance().getSelBankName().equals("ПУМБ")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.UNBLOCK_CARD)
				&& AtributesBean.getInstance().getSelBankName().equals("ПУМБ")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.MONEY_TRANSFER)
				&& AtributesBean.getInstance().getSelBankName().equals("ПУМБ")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		} else if (AtributesBean.getInstance().getSelAction().equals(AllActions.REPLENISH_MOB_TEL)
				&& AtributesBean.getInstance().getSelBankName().equals("ПУМБ")){
			Toast.makeText(mainActivity.getApplicationContext(), "Этот банк не поддерживает выбранное действие !", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

}
