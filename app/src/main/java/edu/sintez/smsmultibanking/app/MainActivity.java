package edu.sintez.smsmultibanking.app;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import edu.sintez.smsmultibanking.app.adapter.TabsPagerAdapter;
import edu.sintez.smsmultibanking.app.db.DBWork;
import edu.sintez.smsmultibanking.app.menu.AboutProgram;
import edu.sintez.smsmultibanking.app.menu.BanksInfoList;
import edu.sintez.smsmultibanking.app.tab1actions.unique.dialogsfragments.BalanceDialog;
import edu.sintez.smsmultibanking.app.tab1actions.unique.dialogsfragments.BlockCardDialog;
import edu.sintez.smsmultibanking.app.tab1actions.unique.dialogsfragments.UnblockCardDialog;
import edu.sintez.smsmultibanking.app.tab1actions.common.fragments.Actions;
import edu.sintez.smsmultibanking.app.tab1actions.common.fragments.UserCards;
import edu.sintez.smsmultibanking.app.tab1actions.unique.fragments.*;
import edu.sintez.smsmultibanking.app.tab2mycards.AddCard;
import edu.sintez.smsmultibanking.app.tab2mycards.MyCards;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

	private static final String LOG_TAG = MainActivity.class.getName();

	private static final int[] tabs = {R.string.action, R.string.my_cards};

	private static ViewPager viewPager;
	private static ActionBar actionBar;
	private static TabsPagerAdapter tabsPagerAdapter;

	private static ArrayList<Fragment> pageItemArrayList;

	private  Fragment tab1;
	private  Fragment tab2;


//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		Log.d(LOG_TAG, "onSaveInstanceState");
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState) {
//		super.onRestoreInstanceState(savedInstanceState);
//		Log.d(LOG_TAG, "onRestoreInstanceState");
//	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager);



		Log.d(LOG_TAG, "onCreate");

		//инициализация БД
		DBWork.initialized(getApplicationContext());

		Log.d(LOG_TAG, "MainActivity:onCreate");

		tab1 = new Actions();
		tab2 = new MyCards();
		//создаем и заполняем все 2 таба при создании программы
		//=========================
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		pageItemArrayList = new ArrayList<Fragment>();
		pageItemArrayList.add( tab1 );
		pageItemArrayList.add( tab2 );

		tabsPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), pageItemArrayList);
		tabsPagerAdapter.setPagerAllItems(pageItemArrayList);

		viewPager = (ViewPager)findViewById(R.id.pager);
		viewPager.setAdapter(tabsPagerAdapter);
		//=====================================

		//добавляем все табы на actionBar
		for (int tabName : tabs){
			actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int pos) {
				actionBar.setSelectedNavigationItem(pos);
			}

			@Override
			public void onPageScrolled(int pos, float v, int i2) {
			}

			@Override
			public void onPageScrollStateChanged(int i) {
			}
		});
	}


	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	}


//	===========================================
//				Фрагменты для Tab 1
//	===========================================
	// первое окно (общее) - действия
	public void showTab1Action(){
		tab1 = null;
		loadFrToTab1(new Actions());
	}


	// второе окно (общее) - выбор карты
	public void showTab1UserCard(){

		loadFrToTab1(new UserCards());
	}


	//третье окно (уникальное) - переход на выбранное действие
	public void showTab1SelUniqueFr(String action, FragmentManager fm){
		if (action.equals(AllActions.BALANCE)){
			BalanceDialog balanceDialog = new BalanceDialog(AtributesBean.getInstance().getSelCardName(), AtributesBean.getInstance().getSelBankName());
			balanceDialog.show(fm, "fm");

		} else if ( action.equals(AllActions.REPLENISH_MOB_TEL) ){
			loadFrToTab1(new ReplenishMobTel());

		} else if(action.equals(AllActions.BLOCK_CARD) ){
			BlockCardDialog blockCardDialog = new BlockCardDialog(AtributesBean.getInstance().getSelCardName(), AtributesBean.getInstance().getSelBankName());
			blockCardDialog.show(fm, "fm");

		} else if ( action.equals(AllActions.UNBLOCK_CARD) ){
			UnblockCardDialog unblockCardDialog = new UnblockCardDialog(AtributesBean.getInstance().getSelCardName(), AtributesBean.getInstance().getSelBankName());
			unblockCardDialog.show(fm, "fm");

		} else if ( action.equals(AllActions.MONEY_TRANSFER) ){
			loadFrToTab1(new MoneyTransfer());
		}
	}

	public void showTab1AddCard(){
		loadFrToTab1(new AddCard());
	}

//	===========================================
//				Фрагменты для  Tab 2
//	===========================================

	// основное окно во втором табе
	public void showTab2MyCards(){
		tab2 = null;
		loadFrToTab2(new MyCards());
	}

	// позволяет добавить новую карту
	public void showTab2AddCard(){
		tab2 = null;
		loadFrToTab2(new AddCard());
	}
//	============================================

	private void loadFrToTab1(Fragment t1){
		Log.d(LOG_TAG, "loadFrToTab1");
		tab1 = t1;
		exeTabs(0);
	}

	private void loadFrToTab2(Fragment t2){
		tab2 = t2;
		exeTabs(1);
	}

	/**
	 * Выполняет загрузку фрагментов в табы
	 * и отображает на экран
	 * @param tabNumber
	 */
	private void exeTabs(int tabNumber){
		Log.d(LOG_TAG, "exeTabs");
		pageItemArrayList = new ArrayList<Fragment>();
		Log.d(LOG_TAG, "TAB 1 = " + tab1);
		Log.d(LOG_TAG, "TAB 2 = " + tab2);
		pageItemArrayList.add(tab1);
		pageItemArrayList.add(tab2);

		tabsPagerAdapter.setPagerItem(pageItemArrayList, tabNumber);

		ViewPager viewPager1 = (ViewPager) findViewById(R.id.pager);
		viewPager1.setAdapter(tabsPagerAdapter);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.getTabAt(1);
	}




	private int isBack = 0; //счетчик нажатий на кнопку BACK
	public boolean isActionsFragmentsShow = false; //разрешает упрвление счетчиком только если находимся в Actions
	/**z
	 * Обработка нажатия на кнопку #BACK на телефоне
	 */
	@Override
	public void onBackPressed() {
		if (isActionsFragmentsShow){
			isBack++;
			if (isBack == 2){
				ExitDialog exitDialog = new ExitDialog();
				exitDialog.show(tab1.getFragmentManager(), "back");
				isBack = 0;
				isActionsFragmentsShow = false;
			}
		}
		showTab1Action();
		showTab2MyCards();
	}


//	============================================
//			Заполнение и обработка меню
//	============================================

	/**
	 * Заполнение элементов меню при старте программы
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;

	}

	/**
	 * Обработка выбранного пункта меню
	 * @param item
	 * @return
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
//			case R.id.menu_settings:
//				return true;
			case R.id.menu_banks_informs:
				startActivity(new Intent(this, BanksInfoList.class));
				return true;
			case R.id.menu_about_program:
				startActivity(new Intent(this, AboutProgram.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
//	====================================================



//	===============================================
//		Все следующие методы должны выполнятся
//		в соответствующих классах.
//		Сейчас это просто заглушки !!!
//	===============================================

	/**
	 * В этом методе производится выбор
	 * телефонного номера из доступных контактов
	 * @return String - выбранный номер телефона из книги контактов
	 */
	public String getSelectedPhoneBookContact(){
		return "+380551234511";
	}


	/**
	 * В этом методе возвращается номер телефона, пользователя
	 * @return String - собственный номер телефона пользователя (из sim карты)
	 */
	public String getMyTelNumber(){
		TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getLine1Number();
//		return "0961234567";  //оставлен для отладки
	}


	/**
	 * Метод показывает сообщение (Toast) о запрошенном балансе
	 * @param state
	 */
	public void msgGetBalance(boolean state){
//		if (state){
//			Toast.makeText(getApplicationContext(), "Ваш запрос в процессе выполнения", Toast.LENGTH_SHORT).show();
//		}else {
//			Toast.makeText(getApplicationContext(), "Действие отменено", Toast.LENGTH_SHORT).show();
//		}

		if (state){
			CustomToast.makeToast(getApplicationContext(), "Ваш запрос бааланса в процессе выполнения", 5);
		}else {
			CustomToast.makeToast(getApplicationContext(), "Действие отменено", 1);
		}
	}



	/**
	 * Метод показывает сообщение (Toast) о пополнении моб тел
	 * @param state
	 */
	public void msgReplMobTel(boolean state){
//		if (state){
//			Toast.makeText(getApplicationContext(), "Ваш запрос пополнения мобильного телефона в процессе выполнения", Toast.LENGTH_SHORT).show();
//		}else {
//			Toast.makeText(getApplicationContext(), "Действие отменено", Toast.LENGTH_SHORT).show();
//		}

		if (state){
			CustomToast.makeToast(getApplicationContext(), "Ваш запрос пополнения мобильного телефона в процессе выполнения", 5);
		}else {
			CustomToast.makeToast(getApplicationContext(), "Действие отменено", 1);
		}
	}


	/**
	 * Метод показывает сообщение (Toast) о блокирование карты
	 * @param state
	 * @param cardName
	 * @param bankName
	 */
	public void msgBlockCard(boolean state, String cardName, String bankName){
//		if (state){
//			Toast.makeText(
//					getApplicationContext(),
//					"Блокирование карты " + cardName + "(" + bankName + ")" + " в процессе выполнения !",
//					Toast.LENGTH_SHORT
//			).show();
//			showTab1Action();
//		}else {
//			Toast.makeText(getApplicationContext(), "Действие отменено", Toast.LENGTH_SHORT).show();
//		}

		if (state){
			CustomToast.makeToast(
					getApplicationContext(),
					"Блокирование карты " + cardName + "(" + bankName + ")" + " в процессе выполнения !",
					5
			);
			showTab1Action();
		}else {
			CustomToast.makeToast(getApplicationContext(), "Действие отменено", 1);
		}
	}


	/**
	 * Метод показывает сообщение (Toast) о разблокирование карты
	 * @param state
	 * @param cardName
	 * @param bankName
	 */
	public void msgUnblockCard(boolean state, String cardName, String bankName){
//		if (state){
//			Toast.makeText(
//					getApplicationContext(),
//					"Разблокирование карты " + cardName + "(" + bankName + ")" + " в процессе выполнения !",
//					Toast.LENGTH_SHORT
//			).show();
//			showTab1Action();
//		}else {
//			Toast.makeText(getApplicationContext(), "Действие отменено", Toast.LENGTH_SHORT).show();
//		}

		if (state){
			CustomToast.makeToast(
					getApplicationContext(),
					"Разблокирование карты " + cardName + "(" + bankName + ")" + " в процессе выполнения !",
					5
			);
			showTab1Action();
		}else {
			CustomToast.makeToast(getApplicationContext(), "Действие отменено", 1);
		}
	}


	/**
	 * Метод показывает сообщение (Toast) о пополнение карты на заданную сумму и заданную валюту
	 * @param state
	 * @param transferSum
	 * @param transferCardNumber
	 * @param transferCurrency
	 */
	public void msgMoneyTransfer(boolean state, String transferSum, String transferCardNumber, String transferCurrency){
//		if (state){
//			Toast.makeText(
//					getApplicationContext(),
//					"Перевод " + transferSum + " " + transferCurrency + " на номер карты " + transferCardNumber +  " в процессе выполнения !",
//					Toast.LENGTH_SHORT
//			).show();
//			showTab1Action();
//		}else {
//			Toast.makeText(getApplicationContext(), "Действие отменено", Toast.LENGTH_SHORT).show();
//		}

		if (state){
			CustomToast.makeToast(
					getApplicationContext(),
					"Перевод " + transferSum + " " + transferCurrency + " на номер карты " + transferCardNumber +  " в процессе выполнения !",
					5
			);
			showTab1Action();
		}else {
			CustomToast.makeToast(getApplicationContext(), "Действие отменено", 1);
		}
	}
//	================================================================================================

	/**
	 * Метод вызывается при получении сообщения
	 * @param intent
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		Log.d("MyActivity", "onNewIntent is called!");

		String phoneNumber = intent.getStringExtra("phone");
		String msg = intent.getStringExtra("message");

		CustomToast.makeToast(getApplicationContext(), "Сообщение от: " + phoneNumber + "\nДанные: " + msg, 5);

		addMsgToHistory(AtributesBean.getInstance().getIdCard(), msg);

		CustomToast.makeToast(getApplicationContext(), "Ваш запрос выполнен !", 1);

		super.onNewIntent(intent);
	}


	private void addMsgToHistory(int idCard, String msg){
		DBWork.addToHistory(idCard, msg);
	}





//		public void onClickedBtn(View view){
//		switch (view.getId()){
//			case R.id.btnBalance:
//				Log.d(LOG_TAG, "balance is cliscked");
////				AtributesBean.getInstance().setSelAction(AllActions.BALANCE);
//				showTab1AddCard();
//				break;
//			default:
//				break;
//		}
//	}



}

