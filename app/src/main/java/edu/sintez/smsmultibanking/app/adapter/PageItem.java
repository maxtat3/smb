package edu.sintez.smsmultibanking.app.adapter;

import android.support.v4.app.Fragment;

/**
 * Created by Max on 11.07.2014.
 */
public class PageItem {

	private Fragment fragment;


	public PageItem(Fragment fragment) {
		this.fragment = fragment;
	}



	public Fragment getFragment() {
		return fragment;
	}

	public void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}
}
