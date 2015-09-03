package edu.sintez.smsmultibanking.app.tab2mycards;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ExpandableListView;
import edu.sintez.smsmultibanking.app.MainActivity;
import java.util.ArrayList;
import java.util.List;
import edu.sintez.smsmultibanking.app.R;
import edu.sintez.smsmultibanking.app.adapter.ExpListAdapter;
import edu.sintez.smsmultibanking.app.db.Card;
import edu.sintez.smsmultibanking.app.db.DBWork;



public class MyCards extends Fragment {

	public static final String LOG_TAG = MyCards.class.getName();


	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_tab2_mycard_my_cards, container, false);

		final MainActivity mainActivity = (MainActivity) getActivity();

		final ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.myCardsListView);

		ArrayList< ArrayList<String> > groups = new ArrayList< ArrayList<String> >();




		List<Card> cards = DBWork.getCardsFromDB();
		List<String> cardHistory;
		for (Card card : cards){
			int idCard = card.getIdCard();
			cardHistory = DBWork.getHistory(idCard);

//			for (String hitoryItem : cardHistory){
//				Log.d(LOG_TAG, "idCard = " + idCard + " ; " + "hitoryItem = " + hitoryItem);
//			}

			groups.add((ArrayList<String>) cardHistory);
		}



		DisplayMetrics displMetrics = new DisplayMetrics();
		mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displMetrics);
		int width = displMetrics.widthPixels;
		expListView.setIndicatorBounds(width - 50, width);

		ExpListAdapter expListAdapter = new ExpListAdapter(getActivity(), groups);
		expListView.setAdapter(expListAdapter);



		//========================================
		// обработка нажатия на кнопку "Добавить"
		final Button buttonAddCard = (Button) rootView.findViewById(R.id.btnAddCard);
		buttonAddCard.setText("Добавить карту");

		View.OnClickListener listener = new View.OnClickListener() {
			//сработает при нажатии на buttonAddCard[добавлении карты]
			@Override
			public void onClick(View view) {
				expListView.setVisibility(View.INVISIBLE);
				buttonAddCard.setVisibility(View.INVISIBLE);
				if (getActivity() != null) {
					onClickGoToAddCard();
				}

			}
		};
		buttonAddCard.setOnClickListener(listener);
		//========================================>>>1

		return rootView;
	}

	//1>>>=====================================
	// [my cards]
	//обработчик для кнопки добавление карты
	public void onClickGoToAddCard(){
//		//для перехода в новое активити при добавления новой карты при нажатии [добавить карту] в табе [мои карты]
		MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.showTab2AddCard();
	}
	//=====================================

}
