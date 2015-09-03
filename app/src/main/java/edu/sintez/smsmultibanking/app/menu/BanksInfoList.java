package edu.sintez.smsmultibanking.app.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import edu.sintez.smsmultibanking.app.R;



/**
 * Created by Max on 24.07.2014.
 * Список всех банков представлен в виде списка.
 * При нажатии на любой банк - открывается краткая контактня информация
 * Вызывается из меню.
 */
public class BanksInfoList extends Activity{

	String[] banks = {
			"ПриватБанк",
			"Укрсоцбанк(uniCredit)",
			"Укрэксимбанк",
			"Аваль",
			"Кредобанк",
			"Марфин Банк",
			"Правэкс-Банк",
			"Профин банк",
			"ПУМБ",
			"Укргазбанк",
			"Укринбанк",
			"УкрСиббанк"
	};

	Integer[] banks_icons = {
			R.drawable.ic_bank_privat,
			R.drawable.ic_bank_unicredit,
			R.drawable.ic_bank_exim,
			R.drawable.ic_bank_aval,
			R.drawable.ic_bank_kredo,
			R.drawable.ic_bank_marfin,
			R.drawable.ic_bank_pravex,
			R.drawable.ic_bank_profin,
			R.drawable.ic_bank_pumb,
			R.drawable.ic_bank_ukrgas,
			R.drawable.ic_bank_ukrin,
			R.drawable.ic_bank_ukrsib
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_list_banks_info);

		ListView listView = (ListView) findViewById(R.id.listViewBanksInfo);

		BIAdapter adapter = new BIAdapter(this, R.layout.pattern_info_row_banks_info, banks);

		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), BankInfo.class);
				intent.putExtra("selectedBank", position);
				startActivity(intent);
			}
		});
	}



	public class BIAdapter extends ArrayAdapter<String> {

		public BIAdapter(Context context, int textViewResourceId, String[] objects) {
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

			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.pattern_info_row_banks_info, parent, false);
			TextView label = (TextView) row.findViewById(R.id.bank);
			label.setText(banks[position]);

			ImageView icon = (ImageView) row.findViewById(R.id.icon);
			icon.setImageResource(banks_icons[position]);

			return row;
		}
	}



}