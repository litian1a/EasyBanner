package com.ltc.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;

/**
 * Created by ltc on 2016/10/28.
 */

public class EasyBanner extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final String TAG = "EasyBanner";
    private int delayTime = 5000;//轮播间隔时间
    private ViewPager mPager;//内部viewpager
    private String[] imaUrls;
    private int[] imageIds;
    private View[] imageViews;
    private RadioGroup mRadioBar;
    private int mImaCount;
    private RadioButton[] mNavigationViews;
    private int mCurrentItem;
    private Handler mHandler = new Handler();

    public EasyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EasyBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EasyBanner(Context context) {
        this(context, null);
    }

    /**
     * 解决滑动冲突
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 初始化view
     */
    private void initViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner, this, true);
        mPager = (ViewPager) view.findViewById(R.id.easy_banner_pager);
        mRadioBar = (RadioGroup) view.findViewById(R.id.easy_banner_radio_bar);
        mPager.removeAllViews();
        mRadioBar.removeAllViews();
        initImageViews();
    }

    /**
     * 初始化imaView
     */
    private void initImageViews() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        for (int i = 0; i < imageViews.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            //判断图片数据来自网络还是来自本地
            if (imaUrls != null) {
                if (i == 0)
                    Glide.with(getContext()).load(imaUrls[mImaCount - 1]).into(imageView);
                else if (i == imageViews.length-1)
                    Glide.with(getContext()).load(imaUrls[0]).into(imageView);
                else
                    Glide.with(getContext()).load(imaUrls[i - 1]).into(imageView);

            } else {
                if (i == 0)
                    imageView.setImageResource(imageIds[mImaCount - 1]);
                else if (i == imageViews.length-1)
                    imageView.setImageResource(imageIds[0]);
                else
                    imageView.setImageResource(imageIds[i - 1]);
            }


            imageViews[i] = imageView;
            Log.i(TAG, "initImageViews: "+imageViews[i]);

        }

    }

    /**
     * 获得图片的url
     *
     * @param urls
     */
    public void setImaForUrl(String[] urls) {
        imageIds = null;
        this.imaUrls = urls;
        mImaCount = urls.length;

        init();


    }

    /**
     * 获得图片的本地id
     *
     * @param urls
     */
    public void setImaForId(int[] urls) {
        imaUrls = null;
        this.imageIds = urls;
        mImaCount = urls.length;

        init();


    }

    private void init() {
        //左右加两缓存 无限轮播
        imageViews = new View[mImaCount + 2];

        initViews();
        //  添加导航条
        addNavigation();
        //  添加adapter
        setAdapter();
        //启动轮播
        startLoop();
    }

    private void startLoop() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mImaCount <= 0)
                    return;

                mPager.setCurrentItem(mCurrentItem % (mImaCount+2));
                mCurrentItem++;
                startLoop();
            }
        }, delayTime);
    }

    private void setAdapter() {
        BannerPager bannerPager = new BannerPager();
        mPager.setAdapter(bannerPager);
        mPager.setCurrentItem(1);
        mPager.addOnPageChangeListener(this);

    }

    private void addNavigation() {
        if (mImaCount <= 0)
            return;

        mNavigationViews = new RadioButton[mImaCount];
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(20, 20);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 5;

        for (int i = 0; i < mNavigationViews.length; i++) {
            RadioButton radioButton = new RadioButton(getContext());
            ViewCompat.setBackground(radioButton, getContext().getResources().getDrawable(R.drawable.selector_navigation_radio));
            radioButton.setButtonDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
            radioButton.setLayoutParams(layoutParams);
            mRadioBar.addView(radioButton);
            mNavigationViews[i] = radioButton;

        }
        mNavigationViews[0].setSelected(true);

    }

    /**
     * 轮播时间跳转
     *
     * @param delayTime 单位ms
     */
    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mNavigationViews.length; i++) {
            mNavigationViews[i].setSelected(i == position-1);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING: //滑动中
                break;
            case ViewPager.SCROLL_STATE_SETTLING:   //手指从屏幕抬起来

                break;
            case ViewPager.SCROLL_STATE_IDLE:  //滑动完全结束
                if (mPager.getCurrentItem() == 0) {
                    mPager.setCurrentItem(mImaCount, false);
                } else if (mPager.getCurrentItem() == mImaCount + 1) {
                    mPager.setCurrentItem(1, false);
                }
                mCurrentItem = mPager.getCurrentItem();

                break;
        }
    }


    private class BannerPager extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position]);
            return imageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews[position]);
        }
    }

}
