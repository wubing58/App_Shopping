package com.example.wb.myshoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.home.bean.ResultBeanData;
import com.example.wb.myshoppingmall.utils.Constants;

import java.util.List;

/**
 * 频道适配器
 */
public class ChannelAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<ResultBeanData.ResultBean.ChannelInfoBean> datas;

    public ChannelAdapter(Context mContext, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = mContext;
        this.datas =channel_info;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler viewHoler;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.item_channel,null);
            viewHoler = new ViewHoler();
            viewHoler.iv_icon = (ImageView) convertView.findViewById(R.id.iv_channel);
            viewHoler.tv_title = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHoler);
        }else {
            viewHoler = (ViewHoler) convertView.getTag();
        }

        //根据位置得到对应的数据
        ResultBeanData.ResultBean.ChannelInfoBean channelInfoBean = datas.get(position);
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+channelInfoBean.getImage()).into(viewHoler.iv_icon);
        viewHoler.tv_title.setText(channelInfoBean.getChannel_name());

        return convertView;
    }

    static class ViewHoler{
        ImageView iv_icon;
        TextView tv_title;
    }
}