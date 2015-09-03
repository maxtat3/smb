package edu.sintez.smsmultibanking.app.tab2mycards;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.sintez.smsmultibanking.app.CustomToast;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.R;
import edu.sintez.smsmultibanking.app.db.DBWork;

/**
 * Created by Max on 15.07.2014.
 */
public class AddCard extends android.support.v4.app.Fragment {

	public static final String LOG_TAG = AddCard.class.getName();

//	private MainActivity mainActivity;

	private EditText editTxtLastFourDigCard;
	private EditText editTxtUserCardName;
	private Spinner spinnerChooseBank;

	Integer[] banks_icons = {
			R.drawable.ic_bank_privat,
			R.drawable.ic_bank_unicredit,
			R.drawable.ic_bank_profin,
			R.drawable.ic_bank_pumb,
			R.drawable.ic_bank_ukrgas,
	};



//	private String lastFourDigCard; //Последние 4 цифры номера карты пользователя
//	private String cardName; // Имя карты
//	private String chooseBank; // Выбранный банк


	@Override
	public View onCreateView(LayoutInflater inflater,
							 ViewGroup container,
							 Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_tab2_mycard_add_card, container, false);

//		mainActivity = (MainActivity) getActivity();


		TextView txtCardNumber = (TextView)view.findViewById(R.id.txtLastFourDigCard);
		txtCardNumber.setText("Последние 4 цифры номера Вашей карты");

		TextView txtChooseBank = (TextView)view.findViewById(R.id.txtChooseBank);
		txtChooseBank.setText("Выберите банк");

		TextView txtCardName = (TextView)view.findViewById(R.id.txtUserCardName);
		txtCardName.setText("Введите название карты");



		editTxtLastFourDigCard = (EditText)view.findViewById(R.id.editTxtLastFourDigCard);

		editTxtUserCardName = (EditText)view.findViewById(R.id.editTxtUserCardName);

		spinnerChooseBank = (Spinner)view.findViewById(R.id.spinnerChooseBank);

		Button btnAddCard = (Button)view.findViewById(R.id.btnAddCard);
		btnAddCard.setText("Добавить");
		btnAddCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final MainActivity mainActivity = (MainActivity) getActivity();
				// Проверки вводимых данных оставим на потом !
				mainActivity.showTab2MyCards();
				mainActivity.showTab1Action();
				boolean addState = DBWork.addCard(editTxtLastFourDigCard.getText().toString(), editTxtUserCardName.getText().toString(), spinnerChooseBank.getSelectedItem().toString());
				if (addState){
					CustomToast.makeToast(mainActivity.getApplicationContext(), "Карта успешно добавлена", Toast.LENGTH_SHORT);
				}else {
					CustomToast.makeToast(mainActivity.getApplicationContext(), "Ошибка при добавлении карты", Toast.LENGTH_SHORT);
				}
				Log.d(LOG_TAG, "card add !");
			}
		});


		SpinnerAdapter adapter = new SpinnerAdapter(getActivity(),
				R.layout.pattern_tab2_addcard_selbank_spinner_row, getResources().getStringArray(R.array.banks));

		// Вызываем адапетр
		spinnerChooseBank.setAdapter(adapter);
		spinnerChooseBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		return view;
	}




	public class SpinnerAdapter extends ArrayAdapter<String> {

		public SpinnerAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// return super.getView(position, convertView, parent);

			LayoutInflater inflater = getActivity().getLayoutInflater();
			View row = inflater.inflate(R.layout.pattern_tab2_addcard_selbank_spinner_row, parent, false);
			TextView label = (TextView) row.findViewById(R.id.bank);
			label.setText(getResources().getStringArray(R.array.banks)[position]);

			ImageView icon = (ImageView) row.findViewById(R.id.icon);
			icon.setImageResource(banks_icons[position]);

			return row;
		}
	}




//	private boolean getFields(){
//
//		// проверка на пустоту в поле
//		if ( !String.valueOf(editTxtLastFourDigCard.getText()).isEmpty() ){
//
//			//ограничение ввода - в поле должно быть ТОЛЬКО 4 цифры
//			int num = Integer.parseInt( String.valueOf( editTxtLastFourDigCard.getText().toString().length() ) );
//			if (num == 4){
//
//				lastFourDigCard = editTxtLastFourDigCard.getText().toString();
//				return true;
//
//			} else{
//				Toast.makeText(mainActivity.getApplicationContext(), "Должны быть введены только 4 цифры номера Вашей карты !", Toast.LENGTH_SHORT).show();
//				return false;
//			}
//
//		} else{
//			Toast.makeText(mainActivity.getApplicationContext(), "Поле номера карты не может быть пустым !", Toast.LENGTH_SHORT).show();
//			return false;
//		}
//
//	}

}
