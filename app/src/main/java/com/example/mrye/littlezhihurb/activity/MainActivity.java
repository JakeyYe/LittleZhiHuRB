package com.example.mrye.littlezhihurb.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.mrye.littlezhihurb.R;
import com.example.mrye.littlezhihurb.adapter.BannerAdapter;
import com.example.mrye.littlezhihurb.adapter.RecyclerAdapter;
import com.example.mrye.littlezhihurb.api.ApiService;
import com.example.mrye.littlezhihurb.api.ServiceCreator;
import com.example.mrye.littlezhihurb.bean.BeforeZhiHuStory;
import com.example.mrye.littlezhihurb.bean.LatestZhiHuStory;
import com.example.mrye.littlezhihurb.utils.DisplayUtil;
import com.example.mrye.littlezhihurb.utils.GetIdUtil;
import com.example.mrye.littlezhihurb.utils.ToastUtil;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MainActivity主界面，使用SwipeRefreshLayout下拉刷新，
 * 监听RecyclerView上拉到底部时加载更多数据
 */
public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<Integer> mIndexList;
    private List<String> mIndexStrList;

    private Handler handler = new Handler();
    private int page = 1;//根据这个page获取时间日期

    /**
     * 覆写该方法，返回false，主界面MainActivity不需要滑动返回，
     * 不覆写默认返回true
     */
    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mToolbar = getViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_title);//Toolbar文字切换
        mToolbar.setLogo(R.drawable.ic_menu_white_24dp);
        setSupportActionBar(mToolbar);

        refreshLayout = getViewById(R.id.swipe_refresh_layout);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        refreshLayout.setOnRefreshListener(this);

        mRecyclerView = getViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                //得到当前显示的最后一个item的view
                View lastChildView = mLayoutManager.
                        getChildAt(mLayoutManager.getChildCount() - 1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到RecyclerView的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition = mLayoutManager.getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                if (lastChildBottom == recyclerBottom && lastPosition == mLayoutManager.getItemCount() - 1) {
//                    ToastUtil.showToast(MainActivity.this, "滑动到底了");
                    getMoreData();
                }


                //先找到index Item，最后再判断这个Item是否是第一个可见的Item
                mIndexList = ((RecyclerAdapter) recyclerView.getAdapter()).getIndexList();
                mIndexStrList = ((RecyclerAdapter) recyclerView.getAdapter()).getIndexStrList();
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if (mIndexList.contains(firstVisibleItemPosition)) {

                    int i=mIndexList.indexOf(firstVisibleItemPosition);
                    mToolbar.setTitle(mIndexStrList.get(i));
                }
                if(firstVisibleItemPosition==0){
                    mToolbar.setTitle(R.string.app_title);
                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        getLatestData();
    }

    /*获取最新数据*/
    private void getLatestData() {
        ApiService apiService = ServiceCreator.getInstance().createService();
        Call<LatestZhiHuStory> latestData = apiService.getLatestZhiHuStory();
        latestData.enqueue(new Callback<LatestZhiHuStory>() {
            @Override
            public void onResponse(Call<LatestZhiHuStory> call, Response<LatestZhiHuStory> response) {

                final LatestZhiHuStory l = response.body();
                mRecyclerAdapter = new RecyclerAdapter(MainActivity.this, l.getStories());
                Log.i(TAG, "onResponse: l.getStories().size() " + l.getStories().size());

                RollPagerView header = new RollPagerView(MainActivity.this);
                //设置RollViewPager的指示器（圆点）
                header.setHintView(new ColorPointHintView(MainActivity.this, Color.WHITE, Color.GRAY));
                //设置指示View的位置，设置四个方向上的Padding值
                header.setHintPadding(0, 0, 0, DisplayUtil.dip2px(MainActivity.this, 8f));
                header.setPlayDelay(3000);//设置轮播时间，默认为0，表示不循环播放
                header.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        DisplayUtil.dip2px(MainActivity.this, 200)));
                header.setAdapter(new BannerAdapter(MainActivity.this, l.getTop_stories()));

                //向RecyclerView中添加一个Header头布局
                mRecyclerAdapter.setHeaderView(header);
                addIndexView("今日热闻");

                mRecyclerAdapter.setItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {

                        Intent intent = new Intent(MainActivity.this, ZhiHuDescribeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", l.getStories().get(position).getId());
                        intent.putExtras(bundle);
                        mSwipeBackHelper.forward(intent);
                    }
                });
                mRecyclerView.setAdapter(mRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<LatestZhiHuStory> call, Throwable t) {
                ToastUtil.showToast(MainActivity.this, "网络出错，获取数据失败");
                Log.i(TAG, "onFailure: 网络出错，获取数据失败");
            }
        });
    }

    /*获取之前的数据*/
    private void getMoreData() {
        ApiService apiService = ServiceCreator.getInstance().createService();
        final String date = GetIdUtil.getId(page);
        Log.i(TAG, "getMoreData: " + date);
        Call<BeforeZhiHuStory> moreData = apiService.getBeforeZhiHuStory(date);
        moreData.enqueue(new Callback<BeforeZhiHuStory>() {
            @Override
            public void onResponse(Call<BeforeZhiHuStory> call, Response<BeforeZhiHuStory> response) {
                //TODO
                addIndexView(date);
                mRecyclerAdapter.addContentData(response.body().getStories());
            }

            @Override
            public void onFailure(Call<BeforeZhiHuStory> call, Throwable t) {
                ToastUtil.showToast(MainActivity.this, "网络出错，获取数据失败");
                Log.i(TAG, "onFailure: 网络出错，获取数据失败");
            }
        });
        page++;
    }

    private void addIndexView(String index) {

        if (index.equals("今日热闻")) {

        } else {
            String str = index.substring(4);//0705
            index = str.substring(0, 2) + "月" + str.substring(2) + "日 " + GetIdUtil.getWeek(index);
        }
        mRecyclerAdapter.addIndexView(index);
    }

    /*SwipeRefreshLayout刷新回调onRefresh()方法，该方法是执行在子线程中的*/
    @Override
    public void onRefresh() {

        handler.postDelayed(new Runnable() {//这里可以判断一下是否一直在当天，在当天就不会再有更新的数据了
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                ToastUtil.showToast(MainActivity.this, "没有更多最新数据了！");//这里向下滑动刷新什么都不显示，因为已经加载了最新的数据了
            }
        }, 2000);
    }

}
