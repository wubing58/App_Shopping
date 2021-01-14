package com.example.wb.myshoppingmall.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.shoppingcart.utils.CartStorage;

import java.util.List;

/*
作用：基类Fragment
首页：HomeFragment
分类：TypeFragment
发现：CommunityFragment
购物车：ShoppingCartFragment
用户中心：UserFragment
等等都要继承该类
 */

public abstract class BaseFragment extends Fragment {

    protected Context mContext;


    /**
     * 当该类被系统创建的时候被回调
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();


    }


    /**
     * 当该类被系统创建的时候被回调，负责UI的创建
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    /**
     * 抽象类，由孩子实现，实现不同的点击后的效果
     * @return
     */
    public abstract View initView() ;

    /**
     * 当Activity被创建的时候回调这个方法，负责得到数据，显示UI
     * 这个方法一定要晚于onCreateView方法来调用
     * 不然会出现空值异常
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 当子类需要联网请求数据的时候，可以重写该方法，在该方法中联网请求
     */
    public void initData() {
        initData(CartStorage.getInstance(this.getContext()).getAllData());
    }


    /**
     * 当子类需要联网请求数据的时候，可以重写该方法，在该方法中联网请求
     * @param goodsBeanList
     */
    public void initData(List<GoodsBean> goodsBeanList) {
    }
}
