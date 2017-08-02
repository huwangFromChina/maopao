package com.example.maopao.aty;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.maopao.R;

public class Guider extends Activity {

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private List<View> mListViews;
    private Button exit;
    private Editor editor;
    private SharedPreferences myshare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.guide_layout);
       
        mListViews = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.guide_item0, null);


        View view2 = inflater.inflate(R.layout.guide_item1, null);


        View view3 = inflater.inflate(R.layout.guide_item2, null);
        

        mListViews.add(view1);
        mListViews.add(view2);
        mListViews.add(view3);

        exit=(Button)view3.findViewById(R.id.guider_start);
        mViewPager = (ViewPager) findViewById(R.id.guide_view_group);
        mPagerAdapter = new PagerAdapter(mListViews);
        mViewPager.setAdapter(mPagerAdapter); 
        myshare=getSharedPreferences("myshare", MODE_PRIVATE);
        editor=myshare.edit();
        exit.setOnClickListener(new OnClickListener()
        {

			@Override
			public void onClick(View v) {
				editor.putBoolean("firstflag", false);
				editor.commit();
				finish();
			}
        	
        });
       
    }

    private class PagerAdapter extends android.support.v4.view.PagerAdapter {

        private List<View> mViews;

        
        public PagerAdapter(List<View> views) {
            super();
            mViews = views;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View v, Object obj) {
            return v == obj;
        }

        @Override
        public void destroyItem(View view, int position, Object object) {
            ((ViewPager) view).removeView(mViews.get(position));
        }

        @Override
        public Object instantiateItem(View view, int position) {

            ((ViewPager) view).addView(mViews.get(position), 0);
            return mViews.get(position);
        }
    }


}


