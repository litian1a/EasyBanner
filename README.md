# EasyBanner
一个简易轮播图控件 支持无限轮播 点击事件 导航条。
## 效果
[show](https://github.com/litian1a/EasyBanner/blob/master/gif/easy_banner.gif)
## 使用方法
```
        banner.setImaForId(ints);//从本地区图片源
        banner.setImaForUrl(strings);//从网络取数据源
        banner.setDelayTime(1000);//轮播速度（默认为5秒）
        banner.setItemClickListener();//设置轮播图片的点击事件

```
布局中定义
```
  <com.ltc.banner.EasyBanner
        android:layout_width="match_parent"
        android:layout_height="260dp"
        android:id="@+id/banner"/>
```
## 实现
基于viewpager控件实现  无限轮播的实现并没有使用常规的给一个很大的值 而是在左右两边加入了两个缓存页面 例如页面123 其实显示的是31231 让页面到达左右缓存页面则让他们回到对应的页面
### 代码如下:
```
   //实现无限轮播 左右两边缓存
                if (mPager.getCurrentItem() == 0) {
                    mPager.setCurrentItem(mImaCount, false);
                } else if (mPager.getCurrentItem() == mImaCount + 1) {
                    mPager.setCurrentItem(1, false);
                }

```