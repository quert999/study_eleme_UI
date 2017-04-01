package com.xw.imagedetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/4/1.
 */

public class FragmentSample extends Fragment{
    int resId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resId = getArguments().getInt("resId",0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,null);
        DetailView detailView = (DetailView) view.findViewById(R.id.dv);
        detailView.setImg(resId);
        detailView.setOnCaptainPager(new DetailView.OnCaptainPager() {
            @Override
            public void onCaptainPager(boolean isCaptain) {
                ((MainActivity) getActivity()).vp.setScrollble(!isCaptain);
            }
        });
        return view;
    }
}
