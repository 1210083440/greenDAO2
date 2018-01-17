package net.bwie.greendao2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.bwie.greendao2.R;
import net.bwie.greendao2.bean.DataBean;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<DataBean> mDatas;

    public FoodAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
    }

    // 添加数据
    public void addDatas(List<DataBean> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    // 设置新数据源
    public void setDatas(List<DataBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_food, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataBean data = mDatas.get(position);

        holder.mTitleTextView.setText(data.getTitle());

        Glide.with(holder.mPicImageView.getContext())
                .load(data.getPic())
                .apply(new RequestOptions().circleCrop())
                .into(holder.mPicImageView);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleTextView;
        ImageView mPicImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.title_tv);
            mPicImageView = itemView.findViewById(R.id.pic_iv);
        }
    }

}
