package com.example.mrye.littlezhihurb.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrye.littlezhihurb.R;
import com.example.mrye.littlezhihurb.activity.ZhiHuDescribeActivity;
import com.example.mrye.littlezhihurb.bean.TopStoriesBean;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * RollViewPager图片轮播控件的适配器
 */

public class BannerAdapter extends StaticPagerAdapter{
    private Context context;
    private List<TopStoriesBean> list=new ArrayList<>();
    View view;
    ImageView img;
    TextView tv;

    public BannerAdapter(Context context, List<TopStoriesBean> data) {
        this.context = context;
        this.list = data;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        view= View.inflate(context,R.layout.view_rollviewpager,null);
        img=(ImageView) view.findViewById(R.id.img_rollViewPager);
        tv=(TextView) view.findViewById(R.id.tv_rollViewPager);

        tv.setText(list.get(position).getTitle());
        //Glide加载图片数据
        Glide.with(context)
                .load(list.get(position).getImage())
                //.placeholder(R.drawable.image_load_failed)//占位图的原因，导致图片不能全屏显示
                .centerCrop()//拉伸截取中间部分显示
                .into(img);
        //RollViewPager中的ImageView的点击事件
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZhiHuDescribeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", list.get(position).getId());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
