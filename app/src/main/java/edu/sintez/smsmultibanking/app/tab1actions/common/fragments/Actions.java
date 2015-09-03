package edu.sintez.smsmultibanking.app.tab1actions.common.fragments;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import edu.sintez.smsmultibanking.app.AtributesBean;
import edu.sintez.smsmultibanking.app.AllActions;
import edu.sintez.smsmultibanking.app.MainActivity;
import edu.sintez.smsmultibanking.app.R;

/**
 * Created by Max on 11.07.2014.
 *
 * Таб: Действия
 * Фрагмент 1 - список всех возможных действий, которые поддерживает приложение
 * Этот фрагмент будет показыватся всегда первым, в независимости от других параметров,
 * например от доступных карт пользователя и т.д. !!!
 */
public class Actions extends Fragment {

	private static final String LOG_TAG = Actions.class.getName();
	private View rootView;
	private int width;


//	public Actions() {
//		setRetainInstance(true);
//	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		Log.d(LOG_TAG, "onCreate:setRetainInstance");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_tab1_actions, container, false);
		final MainActivity mainActivity = (MainActivity)getActivity();
		mainActivity.isActionsFragmentsShow = true;
		Log.d(LOG_TAG, "onCreateview");

		//получаем размер экрана
		DisplayMetrics displMetrics = new DisplayMetrics();
		mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displMetrics);
		double widthDisplay = displMetrics.widthPixels;
		double heightDisplay = displMetrics.heightPixels;

		//размеры кнопок относительно размеров экрана
		width = (int) (heightDisplay / 7);
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
			width = (int) (widthDisplay / 7);
		}

		//если экран квадратный (пока закоментируем, больше нудно для планшетов)
//		if ((heightDisplay / widthDisplay <= 1.5)&(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)){
//			rootView = inflater.inflate(R.layout.fragment_tab1_actions_square, container, false);
//			Log.d(LOG_TAG, "width =<= 1.5");
//		} else {
//			c;
//			rootView = inflater.inflate(R.layout.fragment_tab1_actions, container, false);
//		}


		// Баланс
		Bitmap balance = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_balance),  width,  width,  false);
		ImageButton btnBalance = (ImageButton) rootView.findViewById(R.id.btnBalance);
		btnBalance.setImageBitmap(balance);
		TextView txtBalance = (TextView) rootView.findViewById(R.id.txtBalance);
		txtBalance.setText(R.string.balance);
		btnBalance.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AtributesBean.getInstance().setSelAction(AllActions.BALANCE);
				mainActivity.showTab1UserCard();
			}
		});

		// Пополнение мобильного телефона
		Bitmap phone = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_phone),  width,  width,  false);
		ImageButton btnReplMobTel = (ImageButton) rootView.findViewById(R.id.btnReplMobTel);
		btnReplMobTel.setImageBitmap(phone);
		TextView txtReplMobTel = (TextView) rootView.findViewById(R.id.txtReplMobTel);
		txtReplMobTel.setText(R.string.replMobTel);
		btnReplMobTel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AtributesBean.getInstance().setSelAction(AllActions.REPLENISH_MOB_TEL);
				mainActivity.showTab1UserCard();
			}
		});


		// Заблокировать карту
		Bitmap lock = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_lock),  width,  width,  false);
		ImageButton btnBlockCard = (ImageButton) rootView.findViewById(R.id.btnBlockCard);
		btnBlockCard.setImageBitmap(lock);
		TextView txtBlockCard = (TextView) rootView.findViewById(R.id.txtBlockCard);
		txtBlockCard.setText(R.string.blockCard);
		btnBlockCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AtributesBean.getInstance().setSelAction(AllActions.BLOCK_CARD);
				mainActivity.showTab1UserCard();
			}
		});

		// Разблокировать карту
		Bitmap unlock = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_unlock),  width,  width,  false);
		ImageButton btnUnblockCard = (ImageButton) rootView.findViewById(R.id.btnUnblockCard);
		btnUnblockCard.setImageBitmap(unlock);
		TextView txtUnblockCard = (TextView) rootView.findViewById(R.id.txtUnblockCard);
		txtUnblockCard.setText(R.string.unblockCard);
		btnUnblockCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AtributesBean.getInstance().setSelAction(AllActions.UNBLOCK_CARD);
				mainActivity.showTab1UserCard();
			}
		});


		// Перевод денег с одной карты на другую
		Bitmap transfer = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_transfer_card),  width,  width,  false);
		ImageButton btnMoneyTransfer = (ImageButton) rootView.findViewById(R.id.btnMoneyTransfer);
		btnMoneyTransfer.setImageBitmap(transfer);
		TextView txtMoneyTransfer = (TextView) rootView.findViewById(R.id.txtMoneyTransfer);
		txtMoneyTransfer.setText(R.string.moneyTransfer);
		btnMoneyTransfer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AtributesBean.getInstance().setSelAction(AllActions.MONEY_TRANSFER);
				mainActivity.showTab1UserCard();
			}
		});


		return rootView;
	}

}
