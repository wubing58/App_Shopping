package com.example.wb.myshoppingmall.community.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.base.BaseFragment;
import com.example.wb.myshoppingmall.community.adapter.CommunityViewPagerAdapter;
import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.user.activity.MessageCenterActivity;
import com.google.android.material.tabs.TabLayout;


import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 作用：社区的Fragment
 */

public class CommunityFragment extends BaseFragment {

    private ImageButton ibCommunityIcon;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private ImageButton ibCommunityMessage;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_community, null);
        ibCommunityIcon = (ImageButton) view.findViewById(R.id.ib_community_icon);
        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        ibCommunityMessage = (ImageButton) view.findViewById(R.id.ib_community_message);

        CommunityViewPagerAdapter adapter = new CommunityViewPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tablayout.setVisibility(View.VISIBLE);
        tablayout.setupWithViewPager(viewPager);

        ibCommunityMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MessageCenterActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(),"进入消息中心",Toast.LENGTH_SHORT).show();
            }
        });

        Log.e(TAG,"社区的Fragment的UI被初始化了");
        return view;
    }

    @Override
    public void initData(List<GoodsBean> goodsBeanList) {
        super.initData(goodsBeanList);
        Log.e(TAG,"社区的Fragment的数据被初始化了");
    }
}
