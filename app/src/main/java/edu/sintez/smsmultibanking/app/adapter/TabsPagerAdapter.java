package edu.sintez.smsmultibanking.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import java.util.ArrayList;
import android.view.ViewGroup;
import edu.sintez.smsmultibanking.app.tab1actions.common.fragments.Actions;
import edu.sintez.smsmultibanking.app.tab2mycards.MyCards;



/**
 * Created by Max on 11.07.2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter{

	private static final String LOG_TAG = TabsPagerAdapter.class.getName();

	private FragmentManager fm;
	private ArrayList<Fragment> pageItemsArrayList;


	public TabsPagerAdapter(FragmentManager fm, ArrayList<Fragment> pageItems) {
		super(fm);
		Log.d(LOG_TAG, "TabsPagerAdapter:constructor");
		this.fm	= fm;
		this.pageItemsArrayList = pageItems;
	}


//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		Fragment fragment = pageItemsArrayList.get(0);
//		return fragment;
//	}




	public void setPagerItem(ArrayList<Fragment> pageItems, int tabNumber){
		Log.d(LOG_TAG, "setPagerItem");
		if (pageItemsArrayList != null){
			fm.beginTransaction().remove( pageItemsArrayList.get(tabNumber) ).commit();
		}
//		for (int i = 0; i < 2; i++) {
//			getItem(i);
//		}
		pageItemsArrayList = pageItems;
	}

	public void setPagerAllItems(ArrayList<Fragment> pageItems){
		Log.d(LOG_TAG, "setPagerAllItems");
		if (pageItemsArrayList != null){
			for (int i = 0; i < pageItemsArrayList.size(); i++){
				fm.beginTransaction().remove( pageItemsArrayList.get(i) ).commit();

			}
		}
		pageItemsArrayList = pageItems;
	}


//	Actions actionsFragment = new Actions();
//	MyCards myCardsFragment = new MyCards();


	@Override
	public Fragment getItem(int index) {
		Log.d(LOG_TAG, "@getItem");
		if (index == 0){
			Log.d(LOG_TAG, "@getItem#index0" +  pageItemsArrayList.get(0));
			return pageItemsArrayList.get(0);
//			return actionsFragment;
		}else if (index == 1){
			Log.d(LOG_TAG, "@getItem#index1" +  pageItemsArrayList.get(1));
			return pageItemsArrayList.get(1);
//			return myCardsFragment;
		}

//		for(Fragment fr : pageItemsArrayList){
//			Log.d(LOG_TAG, "is fragment = " + fr);
//		}

		return null;
	}


	@Override
	public int getCount() {
//		return getItemId();
		return 2;
	}




}
