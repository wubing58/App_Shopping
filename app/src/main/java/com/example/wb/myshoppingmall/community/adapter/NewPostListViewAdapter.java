package com.example.wb.myshoppingmall.community.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.community.bean.NewPostBean;
import com.example.wb.myshoppingmall.utils.BitmapUtils;
import com.example.wb.myshoppingmall.utils.Constants;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NewPostListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<NewPostBean.ResultBean> result;
    private List<String> comment_list;

    public NewPostListViewAdapter(Context mContext, List<NewPostBean.ResultBean> result) {
        this.mContext = mContext;
        this.result = result;
    }

    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_listview_newpost, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NewPostBean.ResultBean resultBean = result.get(position);

        holder.tvCommunityUsername.setText(resultBean.getUsername());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE +resultBean.getFigure()).into(holder.ivCommunityFigure);
        holder.tvCommunitySaying.setText(resultBean.getSaying());
        holder.tvCommunityLikes.setText(resultBean.getLikes());
        holder.tvCommunityComments.setText(resultBean.getComments());

        Picasso.with(mContext).load(resultBean.getAvatar()).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap bitmap) {
                //先对图片进行压缩
                Bitmap zoom = BitmapUtils.zoom(bitmap, 70, 70);
                //对请求回来的Bitmap进行圆形处理
                Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                bitmap.recycle();//必须队更改之前的进行回收
                return ciceBitMap;
            }

            @Override
            public String key() {
                return "";
            }
        }).into(holder.ibNewPostAvatar);

        //设置弹幕
        comment_list = (List<String>) resultBean.getComment_list();
        if (comment_list != null && comment_list.size() > 0) {
            holder.danmakuView.setVisibility(View.VISIBLE);

            List<IDanmakuItem> list = new ArrayList<>();
            for (int i = 0; i < comment_list.size(); i++) {
                IDanmakuItem item = new DanmakuItem(mContext, comment_list.get(i), holder.danmakuView.getWidth());
                list.add(item);
            }
            //Collections:使用默认随机源对列表进行置换，所有置换发生的可能性都是大致相等的。
            Collections.shuffle(comment_list);

            holder.danmakuView.addItem(list, true);
            holder.danmakuView.show();
        }else{
            holder.danmakuView.setVisibility(View.GONE);
        }
        return convertView;
    }


    static class ViewHolder {

        private RelativeLayout rl;
        private TextView tvCommunityUsername;
        private TextView tvCommunityAddtime;
        private ImageView ivCommunityFigure;
        private DanmakuView danmakuView;
        private TextView tvCommunitySaying;
        private TextView tvCommunityLikes;
        private TextView tvCommunityComments;
        private ImageButton ibNewPostAvatar;

        ViewHolder(View view) {
            rl = (RelativeLayout)view.findViewById( R.id.rl );
            tvCommunityUsername = (TextView)view.findViewById( R.id.tv_community_username );
            tvCommunityAddtime = (TextView)view.findViewById( R.id.tv_community_addtime );
            ivCommunityFigure = (ImageView)view.findViewById( R.id.iv_community_figure );
            danmakuView = (DanmakuView)view.findViewById( R.id.danmakuView );
            tvCommunitySaying = (TextView)view.findViewById( R.id.tv_community_saying );
            tvCommunityLikes = (TextView)view.findViewById( R.id.tv_community_likes );
            tvCommunityComments = (TextView)view.findViewById( R.id.tv_community_comments );
            ibNewPostAvatar = (ImageButton)view.findViewById( R.id.ib_new_post_avatar );
        }
    }
}
