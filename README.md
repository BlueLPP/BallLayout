# BallLayout



一个Android上的球形布局UI组件



# 效果展示

Demo下载：https://github.com/BlueLPP/BallLayout/blob/master/BallDemo.apk

Demo效果：https://github.com/BlueLPP/BallLayout/blob/master/demo.mp4



# 坐标系



1，球坐标系：



球坐标系定义方式及其和笛卡儿坐标系的转换公式参见维基百科：

https://zh.wikipedia.org/wiki/%E7%90%83%E5%BA%A7%E6%A8%99%E7%B3%BB



通过两个角度表示一个球面上的点：

theta表示该点与z轴之间的夹角，即天顶角。

phi表示该点的半径在xy平面的投影与x轴之间的夹角。



该坐标系有两个奇点，theta等于0和PI的两个点，即南北极点。



球坐标系转换为笛卡儿坐标系参见 SphericalCoordinate.toVector()

笛卡儿坐标系转换为球坐标系参见 SphericalCoordinate.createByCartesianCoordinate(Vector vector)



2，手机UI坐标系



根据Android系统的UI坐标系定义：左为x轴负方向，右为x轴正方向。上为y轴负方向，下为y轴正方向。

和常见的平面笛卡儿坐标系的y轴方向相反，其他相同。



这里使用的笛卡儿坐标系的x轴和手机坐标系完全一致，笛卡儿坐标系的z轴和手机坐标系的y轴方向一致，笛卡儿坐标系的y轴的正方向由手机屏幕射向手机正面。



这里将笛卡儿坐标系的z轴作为手机UI坐标系的y轴，目的是将两个奇点置于手机的上下顶点。如果奇点在手机正中央则手指滑动时需要每次对其做特殊处理，增加逻辑复杂度。





# 接入方法



1，xml中配置方式如下即可使用球型控件：

<com.blue.view.balllayout.BallLayout

android:id="@+id/ball_layout"

android:layout_width="match_parent"

android:layout_height="match_parent"

app:acceleration="0.09817477"

app:autoRotationByFinger="true"

app:backAlpha="0.5"

app:backScale="0.5"

app:endSpeed="0.19634954"

app:fixedSpeed="true"

app:frontAlpha="0.9"

app:frontScale="0.8"

app:radius="150dp" />



2，常用配置接口如下：

setAdapter(Adapter adapter) 设置Adapter，所有子View都通过此对象生成

setRadius(int radius) 设置球半径，也可在xml中通过radius配置

setChildAlpha(float frontAlpha, float backAlpha) 设置子View移动到球体靠近人和远离人部分的透明度，也可在xml中通过frontAlpha和backAlpha配置

setChildScale(float frontScale, float backScale)设置子View移动到球体靠近人和远离人部分的缩放比例，也可在xml中通过frontScale和backScale配置



enableAutoRotationByFinger(boolean enable) 是否打开跟随手指滚动，也可在xml中通过autoRotationByFinger配置

enableFixedSpeed(boolean enable, double acceleration, double endSpeed) 是否最终按固定速度自动滚动，endSpeed是最终角速度，acceleration是加速度，也可在xml中通过fixedSpeed、acceleration、endSpeed配置



startupAutoRotation(int x1, int y1, int x2, int y2)启动自动滚动，初始速度和方向有参数决定，四个参数即笛卡儿坐标系坐标的x和y，函数内部会自动转换成球坐标系

startupAutoRotation(SphericalCoordinate from, SphericalCoordinate to)启动自动滚动，初始速度和方向有参数决定，四个参数即球坐标系坐标

shutdownAutoRotation()停止自动滚动

pauseAutoRotation()、resumeAutoRotation()暂停和恢复自动滚动，建议在onResume和onPause中调用



setOnItemClickListener(OnItemClickListener onItemClickListener)设置点击监听器，当子View被点击时回调

setOnItemPositionChangedListener(OnItemPositionChangedListener onItemPositionChangedListener)设置位置改变监听器，当子View位置改变时回调，即发生滚动时回调
