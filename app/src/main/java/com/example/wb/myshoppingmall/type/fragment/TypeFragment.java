package com.example.wb.myshoppingmall.type.fragment;

import android.util.Log;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.base.BaseFragment;
import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 作用：分类的Fragment
 */

public class TypeFragment extends BaseFragment {
    private SegmentTabLayout segmentTabLayout;
    private ImageView iv_type_search;
    private FrameLayout fl_type;
    private List<BaseFragment> fragmentList;
    private Fragment tempFragment;
    public TagFragment tagFragment; //标签
    public ListFragment listFragment;//分类


    @Override
    public View initView() {

        View view = View.inflate(mContext, R.layout.fragment_type, null);
        segmentTabLayout = (SegmentTabLayout) view.findViewById(R.id.tl_1);
        iv_type_search = (ImageView) view.findViewById(R.id.iv_type_search);
        fl_type = (FrameLayout) view.findViewById(R.id.fl_type);

        Log.e(TAG,"分类的Fragment的UI被初始化了");

        return view;
    }

    @Override
    public void initData(List<GoodsBean> goodsBeanList) {
        super.initData(goodsBeanList);

        initFragment();

        String[] titles = {"分类", "标签"};

        segmentTabLayout.setTabData(titles);

        segmentTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchFragment(tempFragment, fragmentList.get(position));
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        
        Log.e(TAG,"分类的Fragment的数据被初始化了");

    }

    @Override
    public void onResume() {
        super.onResume();
        switchFragment(tempFragment, fragmentList.get(0));
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {
        if (tempFragment != nextFragment) {
            tempFragment = nextFragment;
            if (nextFragment != null) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.fl_type, nextFragment, "tagFragment").commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        listFragment = new ListFragment();
        tagFragment = new TagFragment();

        fragmentList.add(listFragment);
        fragmentList.add(tagFragment);

        switchFragment(tempFragment, fragmentList.get(0));
    }
}
