package com.example.wb.myshoppingmall.user.fragment;


import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.app.LoginActivity;
import com.example.wb.myshoppingmall.base.BaseFragment;
import com.example.wb.myshoppingmall.user.activity.MessageCenterActivity;
import com.example.wb.myshoppingmall.utils.BitmapUtils;
import com.example.wb.myshoppingmall.utils.DensityUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


public class UserFragment extends BaseFragment implements View.OnClickListener {
    private ImageButton ibUserIconAvator;
    private Button tbUsername;
    private TextView tvAllOrder;
    private TextView tvUserPay;
    private TextView tvUserReceive;
    private TextView tvUserFinish;
    private TextView tvUserDrawback;
    private TextView tvUserLocation;
    private TextView tvUserCollect;
    private TextView tvUserCoupon;
    private TextView tvUserScore;
    private TextView tvUserPrize;
    private TextView tvUserTicket;
    private TextView tvUserInvitation;
    private TextView tvUserCallcenter;
    private TextView tvUserFeedback;
    private TextView tvUsercenter;
    private ImageButton ibUserSetting;
    private ImageButton ibUserMessage;
    private ScrollView scrollView;

    /**
     *
     * @param view
     */
    private void findViews(View view) {
        ibUserIconAvator = (ImageButton) view.findViewById(R.id.ib_user_icon_avator);
        tbUsername = (Button) view.findViewById(R.id.tv_username);
        tvAllOrder = (TextView) view.findViewById(R.id.tv_all_order);
        tvUserPay = (TextView) view.findViewById(R.id.tv_user_pay);
        tvUserReceive = (TextView) view.findViewById(R.id.tv_user_receive);
        tvUserFinish = (TextView) view.findViewById(R.id.tv_user_finish);
        tvUserDrawback = (TextView) view.findViewById(R.id.tv_user_drawback);
        tvUserLocation = (TextView) view.findViewById(R.id.tv_user_location);
        tvUserCollect = (TextView) view.findViewById(R.id.tv_user_collect);
        tvUserCoupon = (TextView) view.findViewById(R.id.tv_user_coupon);
        tvUserScore = (TextView) view.findViewById(R.id.tv_user_score);
        tvUserPrize = (TextView) view.findViewById(R.id.tv_user_prize);
        tvUserTicket = (TextView) view.findViewById(R.id.tv_user_ticket);
        tvUserInvitation = (TextView) view.findViewById(R.id.tv_user_invitation);
        tvUserCallcenter = (TextView) view.findViewById(R.id.tv_user_callcenter);
        tvUserFeedback = (TextView) view.findViewById(R.id.tv_user_feedback);
        tvUsercenter = (TextView) view.findViewById(R.id.tv_usercenter);
        ibUserSetting = (ImageButton) view.findViewById(R.id.ib_user_setting);
        ibUserMessage = (ImageButton) view.findViewById(R.id.ib_user_message);
        scrollView = (ScrollView) view.findViewById(R.id.scrollview);

        ibUserIconAvator.setOnClickListener(this);
        ibUserSetting.setOnClickListener(this);
        ibUserMessage.setOnClickListener(this);
        tbUsername.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == ibUserIconAvator) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }else if(v == tbUsername){
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        }else if (v == ibUserSetting) {
            Toast.makeText(mContext, "设置", Toast.LENGTH_SHORT).show();
        } else if (v == ibUserMessage) {
            Intent intent = new Intent(mContext, MessageCenterActivity.class);
            startActivity(intent);
        }
    }



    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        findViews(view);
        tvUsercenter.setAlpha(0);//设置透明度为0，当滚动条在最顶端时不显示个人中心
        return view;
    }

    @Override
    public void initData() {

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("Range")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN://按下状态
                        break;
                    case MotionEvent.ACTION_MOVE://下滑是正，上滑是负，滑动状态
                        ibUserIconAvator.getLocationOnScreen(location);//初始状态为125,即最大值是125，全部显示不透明是（40？）
                        float i = (location[1] - 40) / 85f;
                        tvUsercenter.setAlpha(1 - i);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            String screen_name = data.getStringExtra("screen_name");
            String profile_image_url = data.getStringExtra("profile_image_url");

            //图片加载缓存框架,和glide差不多
            Picasso.with(mContext).load(profile_image_url).transform(new Transformation() {
                @Override
                public Bitmap transform(Bitmap bitmap) {
                    //先对图片进行压缩
                //Bitmap zoom = BitmapUtils.zoom(bitmap, DensityUtil.dip2px(mContext, 62), DensityUtil.dip2px(mContext, 62));
                    Bitmap zoom = BitmapUtils.zoom(bitmap, 90, 90);
                    //对请求回来的Bitmap进行圆形处理
                    Bitmap ciceBitMap = BitmapUtils.circleBitmap(zoom);
                    bitmap.recycle();//必须对更改之前的进行回收
                    return ciceBitMap;
                }

                @Override
                public String key() {
                    return "";
                }
            }).into(ibUserIconAvator);

            tbUsername.setText(screen_name);
        }
    }
}
