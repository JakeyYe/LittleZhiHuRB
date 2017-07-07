package com.example.mrye.littlezhihurb.activity;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.mrye.littlezhihurb.R;
import com.jaeger.library.StatusBarUtil;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * 封装BaseActivity基类
 */

public abstract class BaseActivity extends AppCompatActivity
        implements BGASwipeBackHelper.Delegate,View.OnClickListener{

    private static final String TAG=BaseActivity.class.getSimpleName();

    protected BGASwipeBackHelper mSwipeBackHelper;
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);

        mToolbar=getViewById(R.id.toolbar);

        initView(savedInstanceState);//初始化View

        setListener();
        processLogic(savedInstanceState);
    }

    /**初始化布局以及View控件*/
    protected abstract void initView(Bundle savedInstanceState);

    /**给View控件添加事件监听器*/
    protected abstract void setListener();

    /**处理业务逻辑，状态恢复等操作*/
    protected abstract void processLogic(Bundle savedInstanceState);
    /**
     * 查找View
     * */
    protected <VT extends View> VT getViewById(@IdRes int id){
        return (VT) findViewById(id);
    }

    @Override
    public void onClick(View v) {

    }

    /**初始化滑动返回，在super.onCreate()方法之前运行*/
    private void initSwipeBackFinish(){
        mSwipeBackHelper=new BGASwipeBackHelper(this,this);

    }
    /*设置当前Activity是否可以滑动返回，这里在父类中默认返回true，
    * 如果不想滑动，覆写该方法返回false即可
    * */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

     /**
      * 正在滑动返回时的事件处理
      * @param slideOffset 取值是从0到1的
      * */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {

    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回的动作，回到默认状态
     * 的事件处理
     * */
    @Override
    public void onSwipeBackLayoutCancel() {

    }

    /**
     * 滑动返回执行完毕，销毁当前的Activity
     * */
    @Override
    public void onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        //正在滑动返回的时候取消返回按钮事件
        if(mSwipeBackHelper.isSliding()){
            return;
        }
        mSwipeBackHelper.backward();
    }

    /**
     * 设置状态栏颜色
     * */
    protected void setStatusBarColor(@ColorInt int color){
        setStatesBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param defaultStatusBarAlpha 透明度
     * */
    private void setStatesBarColor(int color, int defaultStatusBarAlpha) {
        StatusBarUtil.setColorForSwipeBack(this,color,defaultStatusBarAlpha);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: "+TAG);
    }
}
