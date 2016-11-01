package com.ltc.easybanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ltc.banner.EasyBanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EasyBanner banner = (EasyBanner) findViewById(R.id.banner);
        int[] ints = {R.mipmap.ima1, R.mipmap.ima2, R.mipmap.ima3};
        banner.setImaForId(ints);
//        String[] strings={"http://imgbbs.heiguang.net/forum/201510/06/104432cjc7c8tx7xxqqkgq.jpg","http://imgbbs.heiguang.net/forum/201510/06/104432cjc7c8tx7xxqqkgq.jpg","http://imgbbs.heiguang.net/forum/201510/06/104432cjc7c8tx7xxqqkgq.jpg"};
//        banner.setImaForUrl(strings);
    }
}
