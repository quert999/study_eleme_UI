package com.xw.imagedetail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyPager vp;
    List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        vp = (MyPager) findViewById(R.id.vp);
        fragments = new ArrayList<>();
        addFragment(R.drawable.test);
        addFragment(R.drawable.test1);
        addFragment(R.drawable.test2);
        vp.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private void addFragment(int resId){
        Fragment f = new FragmentSample();
        Bundle bundle = new Bundle();
        bundle.putInt("resId",resId);
        f.setArguments(bundle);
        fragments.add(f);
    }


    public class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

    }
}
