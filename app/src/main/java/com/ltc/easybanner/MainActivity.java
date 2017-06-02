package com.ltc.easybanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.ltc.banner.EasyBanner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  EasyBanner.BannerItemClick {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.main_list);
        View view = View.inflate(this, R.layout.item_header, null);
        EasyBanner banner = (EasyBanner) view.findViewById(R.id.banner);

//        int[] ints = {R.mipmap.ima1, R.mipmap.ima2, R.mipmap.ima3};
//        banner.setImaForId(ints);//从本地区图片源
        String[] urls={"http://imgbbs.heiguang.net/forum/201510/06/104432cjc7c8tx7xxqqkgq.jpg","http://imgbbs.heiguang.net/forum/201510/06/104432cjc7c8tx7xxqqkgq.jpg","http://imgbbs.heiguang.net/forum/201510/06/104432cjc7c8tx7xxqqkgq.jpg","http://imgbbs.heiguang.net/forum/201510/06/104432cjc7c8tx7xxqqkgq.jpg"};
        banner.setImaForUrl(urls);
//        banner.setDelayTime(1000);//轮播速度
//
//        banner.setItemClickListener(this);
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i <100; i++) {
            strings.add(i+"");
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, android.R.id.text1,strings);
        listView.setAdapter(stringArrayAdapter);
        listView.addHeaderView(view);
    }



    @Override
    public void OnBannerItemClick(Object o) {
        Toast.makeText(this, o.toString(), Toast.LENGTH_SHORT).show();
    }
}
