package com.example.wb.myshoppingmall.app;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.base.BaseFragment;
import com.example.wb.myshoppingmall.community.fragment.CommunityFragment;
import com.example.wb.myshoppingmall.home.fragment.HomeFragment;
import com.example.wb.myshoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.example.wb.myshoppingmall.type.fragment.TypeFragment;
import com.example.wb.myshoppingmall.user.fragment.UserFragment;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity {

    private FrameLayout frameLayout;
    private RadioGroup rgMain;

    //装多个Fragment的实例集合
    private ArrayList<BaseFragment> fragments;

    //取Fragment的位置
    private int position = 0;

    //缓存的Fragment或者上一次显示的Fragment
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MMKV.initialize(this);

        rgMain = (RadioGroup) findViewById(R.id.rg_main);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);



       //初始化Fragment方法
        initFragment();

        //设置RadioGroup的监听
        initListener();

    }

    private void initListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home://主页
                        position = 0;
                        break;
                    case R.id.rb_type://分类
                        position = 1;
                        break;
                    case R.id.rb_community://社区
                        position = 2;
                        break;
                    case R.id.rb_cart://购物车
                        position = 3;
                        break;
                    case R.id.rb_user://用户中心
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }

                //根据位置取不同的Fragment
                BaseFragment baseFragment = getFragment(position);

                /**
                 * 第一个参数：上次显示的Fragment
                 * 第二个参数：当前正要显示的Fragment
                 */
                switchFragment(tempFragment,baseFragment);
            }
        });

        rgMain.check(R.id.rb_home);
    }

    //添加的时候要按照顺序初始化
    private void initFragment(){
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new TypeFragment());
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());

    }

    //根据位置取得对应的Fragment
    private BaseFragment getFragment(int position){
        if(fragments != null && fragments.size() > 0){
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    /**
     * 切换Fragment
     * @param fromFragment  之前缓存显示的参数
     * @param nextFragment  当前正要显示的参数
     */
    private void switchFragment(Fragment fromFragment,BaseFragment nextFragment){
        if(tempFragment != nextFragment){   //如果上一次缓存的参数和当前正要显示的参数相同就不进行判断
            tempFragment = nextFragment;    //如果不相同，那就将当前正要显示的参数赋值给上一次缓存的参数
            if(nextFragment != null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加
                if(!nextFragment.isAdded()){
                    //隐藏当前Fragment
                    if(fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    //添加Fragment
                    transaction.add(R.id.frameLayout,nextFragment).commit();
                }else {
                    //隐藏当前Fragment
                    if(fromFragment != null){
                        transaction.hide(fromFragment);
                    }
                    //如果添加过，就显示Fragment
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }


}