package com.example.mrye.littlezhihurb.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrye.littlezhihurb.R;
import com.example.mrye.littlezhihurb.api.ApiService;
import com.example.mrye.littlezhihurb.api.ServiceCreator;
import com.example.mrye.littlezhihurb.bean.ZhiHuItemData;
import com.example.mrye.littlezhihurb.utils.ToastUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**点击一条数据跳转到ZhiHuDescribeActivity*/
public class ZhiHuDescribeActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ImageView imgDescribe;
    private TextView tvFromDescribe;
    private TextView tvTitleDescribe;
    private WebView webDescribe;

    private Integer id;
    private String mShareUrl;


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_zhi_hu_describe);
        mToolbar=getViewById(R.id.toolbar);
        imgDescribe=getViewById(R.id.img_describe);
        tvFromDescribe=getViewById(R.id.tv_from_describe);
        tvTitleDescribe=getViewById(R.id.tv_title_describe);
        webDescribe=getViewById(R.id.web_describe);

        id=getIntent().getIntExtra("id",0);
        setSupportActionBar(mToolbar);
        //设置返回箭头图标
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void setListener() {
        mSwipeBackHelper.setIsWeChatStyle(false);
        mSwipeBackHelper.setSwipeBackEnable(true);
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(false);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
          getData();
    }

    private void getData(){
        ApiService apiService= ServiceCreator.getInstance().createService();
        Call<ZhiHuItemData> itemData=apiService.getZhiHUItemData(id);
        itemData.enqueue(new Callback<ZhiHuItemData>() {
            @Override
            public void onResponse(Call<ZhiHuItemData> call, Response<ZhiHuItemData> response) {
                ZhiHuItemData data=response.body();

                Glide.with(ZhiHuDescribeActivity.this)
                        .load(data.getImage())
                        .centerCrop()
                        .into(imgDescribe);
                tvTitleDescribe.setText(data.getTitle());
                tvFromDescribe.setText(data.getImage_source());
                //设置WebView,并加载URL
                webDescribe.getSettings()
                        .setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webDescribe.setVerticalScrollBarEnabled(false);
                webDescribe.setHorizontalScrollBarEnabled(true);
                webDescribe.loadData(getHtmlData(data.getCss().get(0),data.getBody()),
                        "text/html;charset=UTF-8",null);
                mShareUrl=data.getShare_url();
            }

            @Override
            public void onFailure(Call<ZhiHuItemData> call, Throwable t) {
                ToastUtil.showToast(ZhiHuDescribeActivity.this,"网络出错，获取数据失败");
            }
        });
    }

    private String getHtmlData(String css,String bodyHtml){
        String head="<head>"+
                "<link href=\""+css+"\" rel=\"stylesheet\""+"</head>";
        String body=bodyHtml.replace("class=\"headline\"","");//去掉这部分
        String html="<html>"+head+"<body>"+body+"</body></html>";
        return html;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {//分享事件
            if (!TextUtils.isEmpty(mShareUrl)) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);//Intent.ACTION_SEND分享Action
                shareIntent.putExtra(Intent.EXTRA_TEXT, mShareUrl);
                shareIntent.setType("text/plain");//设置数据类型
                startActivity(shareIntent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
