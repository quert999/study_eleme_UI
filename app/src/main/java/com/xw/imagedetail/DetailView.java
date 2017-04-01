package com.xw.imagedetail;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/4/1.
 */

public class DetailView extends LinearLayout{
    public DetailView(Context context) {
        this(context,null);
    }

    public DetailView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    ImageView img;
    RelativeLayout text_container;
    View container;
    Scroller scroller;

    public void setImg(int resId){
        img.setBackgroundResource(resId);
    }

    //最下面是scroll到-950
    public DetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);

        scroller = new Scroller(context);
        View.inflate(context,R.layout.detail_layout,this);
        img = (ImageView) findViewById(R.id.img);
        text_container = (RelativeLayout) findViewById(R.id.text_container);
        container = findViewById(R.id.container);
    }

    float downY,downX;
    float downImgTop;
    int gestureDirector = -1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curX = event.getX();
        float curY = event.getY();
        int scrollToY = (int) (curY - downY - downImgTop);
        scrollToY = scrollToY > -200 ? (int) scrollToY : -200;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = curY;
                downX = curX;
                downImgTop = getScrollY();

                gestureDirector = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (gestureDirector == -1){
                    if (Math.max(Math.abs(curX - downX),Math.abs(curY - downY)) > 50){
                        if (Math.abs(curX - downX) > Math.abs(curY - downY)){
                            gestureDirector = 1;    //横向
                        }else{
                            isCaptain = true;
                            if (onCaptainPager != null){
                                onCaptainPager.onCaptainPager(isCaptain);
                            }
                            gestureDirector = 2;    //竖向
                        }
                    }
                }
                if (gestureDirector == 1){
                    if (!isCaptain) {
                        System.out.println("被占据   123");;
                        return false;
                    }else{
                        System.out.println("被占据");;
                        return true;
                    }
//                }else if(gestureDirector == 2 || gestureDirector == -1) {
                }else if(gestureDirector == 2) {
                    int top = scrollToY + 200;
                    float scale = 0.7f;
                    if (top >= 0 && top <= 200) {
                        scale = (float) (-(1d * top) / 200 * 0.3d + 1);
                        text_container.setAlpha((1f * 200 - top) / 200);
                    } else {
                        text_container.setAlpha(0);
                    }
                    container.setScaleX(scale);
                    container.setScaleY(scale);
                    scrollTo(0,-scrollToY);
                    return true;
                }
                if (!isCaptain) {
                    System.out.println("被占据   123");;
                    return false;
                }else{
                    System.out.println("被占据");;
                    return true;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(gestureDirector == 2){
                    int scrollYBak = scrollToY;
                    if(scrollToY < -150){
                        scrollToY = -200;
                    }else if(scrollToY < 300){
                        scrollToY = 0;
                    }else{
                        scrollToY = 950;
                    }
                    //因为dy是scrollBy的形式，所以使用finalY更加方便
                    scroller.startScroll(0, -scrollYBak,0,0);
                    scroller.setFinalX(0);
                    scroller.setFinalY(-scrollToY);
                    invalidate();
                }
                break;
        }
        return true;
    }

    boolean isCaptain;
    OnCaptainPager onCaptainPager;

    public void setOnCaptainPager(OnCaptainPager onCaptainPager){
        this.onCaptainPager = onCaptainPager;
    }

    public interface OnCaptainPager{
        void onCaptainPager(boolean isCaptain);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            int curY = scroller.getCurrY();
            if (curY == 0){
//                Toast.makeText(getContext(), "取消占据", Toast.LENGTH_SHORT).show();
                isCaptain = false;
                if (onCaptainPager != null){
                    onCaptainPager.onCaptainPager(isCaptain);
                }
            }
            int top = curY;
            float scale = 0.7f;
            if (top >= 0 && top <= 200) {
                scale = (float) (-(200 - 1d * top) / 200 * 0.3d + 1);
                text_container.setAlpha((1f * top) / 200);
            } else {
                text_container.setAlpha(0);
            }
            container.setScaleX(scale);
            container.setScaleY(scale);
            invalidate();
        }
    }
}
