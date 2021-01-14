package com.example.wb.myshoppingmall.home.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.home.bean.ResultBeanData;
import com.example.wb.myshoppingmall.utils.Constants;

import java.util.List;

/**
 * 秒杀的适配器
 */
public class SeckillRecyclerViewAdapter extends RecyclerView.Adapter<SeckillRecyclerViewAdapter.ViewHolder> {

    private final Context mContext;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list;

    public SeckillRecyclerViewAdapter(Context mContext, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_seckill,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //1、根据位置得到对应的数据
        ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = list.get(position);
        //2、绑定数据
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+listBean.getFigure()).into(holder.iv_figure);
        holder.tv_cover_price.setText(listBean.getCover_price());
        holder.tv_origin_price.setText(listBean.getOrigin_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_figure;
        private TextView tv_cover_price;
        private TextView tv_origin_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_figure = itemView.findViewById(R.id.iv_figure);
            tv_cover_price = itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = itemView.findViewById(R.id.tv_origin_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //Toast.makeText(mContext,"position"+getLayoutPosition(),Toast.LENGTH_LONG).show();
                    if(onSeckillRecyclerView != null){
                        onSeckillRecyclerView.onItemClick(getLayoutPosition());
                    }
                }
            });

        }
    }

    /**
     * 监听器
     */
    public interface OnSeckillRecyclerView{
        /**
         * 当某条被点击的时候回调
         * @param position
         */
        public void onItemClick(int position);
    }

    private OnSeckillRecyclerView onSeckillRecyclerView;

    /**
     * 设置item的监听
     * @param onSeckillRecyclerView
     */
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }
}