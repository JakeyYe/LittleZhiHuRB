package com.example.mrye.littlezhihurb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mrye.littlezhihurb.R;
import com.example.mrye.littlezhihurb.bean.StoriesBean;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的适配器
 */

public class RecyclerAdapter extends
        RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<StoriesBean> contentData = new ArrayList<>();
    private Context mContext;

    public OnItemClickListener itemClickListener;

    private View mHeaderView;
    private View mIndexView;

    public List<String> getIndexStrList() {
        return indexStrList;
    }

    private List<String> indexStrList=new ArrayList<>();

    public List<Integer> getIndexList() {
        return indexList;
    }

    private List<Integer> indexList = new ArrayList<>();
    private boolean firstIndex = true;

    public void setHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
        notifyItemInserted(0);
    }

    public void addIndexView(String index) {
        if (firstIndex) {
            indexList.add(1);
            indexStrList.add(index);
            notifyItemInserted(1);
            firstIndex = false;
        } else {
            indexList.add(getItemCount());
            indexStrList.add(index);
            notifyItemInserted(getItemCount());//放置在列表中的最后的位置
        }
    }

    //RecyclerView中会有三种类型的Item
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_CONTENT = 1;
    public static final int TYPE_INDEX = 2;

    public RecyclerAdapter(Context context, List<StoriesBean> contentData) {
        this.mContext = context;
        this.contentData = contentData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        if (viewType == TYPE_INDEX) {
            mIndexView=LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_index, parent, false);
            return new ViewHolder(mIndexView);
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.zhihu_recycler_item, parent, false);
        return new ViewHolder(v);
        //创建并返回不同样式的ViewHolder

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_CONTENT) {
            if (indexList.size() > 0) {
                int i = 0;
                while (i<indexList.size()&&position >= indexList.get(i)) {
                    ++i;
                }
                holder.setData(contentData.get(position - i - 1));
            } else {

                holder.setData(contentData.get(position - 1));
            }
        }
        if(getItemViewType(position)==TYPE_INDEX){
            int i=indexList.indexOf(position);
            ((TextView)holder.itemView.findViewById(R.id.tv_index)).setText(indexStrList.get(i));
        }
        return;
        //绑定不同的数据
    }

    @Override
    public int getItemCount() {
        if (mHeaderView == null) {
            return contentData.size();
        } else if (indexList.size() > 0) {
            return contentData.size() + 1 + indexList.size();
        } else {
            return contentData.size() + 1;
        }

    }

    public void addContentData(List<StoriesBean> list) {
        this.contentData.addAll(list);
        notifyDataSetChanged();//刷新适配器
    }

    @Override
    public int getItemViewType(int position) {

        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        }
        if (indexList.contains(position)) {
            return TYPE_INDEX;
        }
        return TYPE_CONTENT;
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView tv_text;
        public ImageView img_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView ||itemView==mIndexView) {
                return;
            }
            tv_text = (TextView) itemView.findViewById(R.id.tv_title);
            img_icon = (ImageView) itemView.findViewById(R.id.img_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position=getAdapterPosition();
                if (indexList.size() > 0) {
                    int i = 0;
                    while (i<indexList.size()&&position >= indexList.get(i)) {
                        ++i;
                    }
                    itemClickListener.onItemClick(v, position - i - 1);
                } else {
                    itemClickListener.onItemClick(v, position  - 1);
                }

            }
        }

        public void setData(StoriesBean data) {
            tv_text.setText(data.getTitle());
            Glide.with(mContext)
                    .load(data.getImages().get(0))
                    .placeholder(R.drawable.image_load_failed)
                    .centerCrop()
                    .into(img_icon);
        }
    }
}
