现在app开发中使用下拉刷新的效果是很频繁的。网上也有各种第三方的实现方法，github上也有很多开源的项目。Google在v4包里也添加了一个下拉刷新的控件，就是SwipeRefreshLayout。SwipeRefreshLayout在5.0之前的v4包的效果是顶部有一个颜色条，5.0之后就是拉出一个悬浮的旋转的圈。说实话SwipeRefreshLayout这个控件很好用，用起来也很简单方便，但是下拉的效果不是我想要的，因为项目要求使用传统的下拉效果也就是下面这种效果：
![输入图片说明](http://loongwind-loongwind.stor.sinaapp.com/pulltorefresh.png "下拉刷新")
所以我就在想能不能修改SwipeRefreshLayout的源码来达到自己想要的效果呢？说做就做，从sdk卡的extras\android\support\v4\src\java\android\support\v4\widget目录下找到了SwipeRefreshLayout.java的源文件以及依赖的两个文件SwipeProgressBar.java、BakedBezierInterpolator.java。将这三个文件拷出来看是看源码并着手修改。花了一下午的时间终于实现了，先看一下效果：

![输入图片说明](http://loongwind-loongwind.stor.sinaapp.com/swipe_n_1.gif "下拉效果")
主要修改了以下地方：
1. 1、取消了下拉高度的限制和下拉停止不动时超时的限制
1. 2、添加了默认的传统的下拉效果
1. 3、允许添加自定义的下拉效果
1. 4、可以隐藏顶部的颜色条

修改后的SwipeRefreshLayout添加默认的传统的下拉效果，也允许设置自定义的下拉效果。
使用方式有三种：
1、默认下拉效果
这种效果跟Google官方的使用方法一样，这里我就不多介绍了，只是最后效果跟Google提供的不一样。这里设置下拉刷新的监听还是setOnRefreshListener
2、代码里自定义设置下拉效果
```
    <com.cmad.swipe.SwipeRefreshLayout
        android:id="@+id/refresh_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <ListView
            android:id="@+id/listview"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        </ListView>

    </com.cmad.swipe.SwipeRefreshLayout>
```

xml跟Google原版的使用方式一样，然后看一下在代码里怎么添加自定义下拉布局
```
        TextView view = new TextView(this);
        view.setPadding(100,100,100,100);
        mRefreshLayout.setHeadView(view);//mRefreshLayout就是我们修改的SwipeRefreshLayout
```
这里我添加了一个简单的Textview作为下拉显示的控件，接下拉看一下怎么控制下拉时显示的效果：
```
        mRefreshLayout.setOnPullListener(new OnPullListener() {

            /**
             * 下拉时调用
             * @param headview
             */
            @Override
            public void onPulling(View headview) {
                TextView view = (TextView) headview;
                view.setText("下拉刷新");
            }

            /**
             * 松开可以刷新时调用
             * @param headview
             */
            @Override
            public void onCanRefreshing(View headview) {
                TextView view = (TextView) headview;
                view.setText("松开开始刷新");
            }

            /**
             * 刷新时调用
             * @param headview
             */
            @Override
            public void onRefreshing(View headview) {
                TextView view = (TextView) headview;
                view.setText("正在刷新...");
                //这里模拟加载数据
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //关闭下拉
                        mRefreshLayout.setRefreshing(false);
                        //刷新数据……
                    }
                }, 2000);
            }
        });
```
通过设置OnPullListener来控制我们添加的Textview在下拉、松开刷新、正在刷新时显示的效果。这样我们就实现了自定义的下拉展示效果。
3、在xml布局文件添加自定义下拉布局
```
    <com.cmad.swipe.SwipeRefreshLayout
        android:id="@+id/refresh_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:id="@+id/headview"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" 
            android:padding="20dp"/>

        <ListView
            android:id="@+id/listview"
            android:layout_width="match_parent"
            android:layout_height="match_parent">

        </ListView>

    </com.cmad.swipe.SwipeRefreshLayout>
```
直接将你自定义的下拉布局放在SwipeRefreshLayout节点里的第一位然后下面放置你要显示的内容布局（注：SwipeRefreshLayout里面最多只能放两个直接子控件）。代码里就不需要在去手动调用setHeadView方法来设置了。设置下拉时效果的处理跟第二中方法的处理一样这里就不多说了。

最后如果你不想要顶部的颜色条的功能可以调用
```
   mRefreshLayout.hideColorProgressBar();
```
将其隐藏。
