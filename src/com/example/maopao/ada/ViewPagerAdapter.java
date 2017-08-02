package com.example.maopao.ada;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> fragmentlist;

	public ViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentlist) {
		super(fm);
		this.fragmentlist = fragmentlist;
		Log.d("home", "fragmentadapter");
	}

	@Override
	public int getCount() {
		return fragmentlist.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragmentlist.get(arg0);
	}

}
