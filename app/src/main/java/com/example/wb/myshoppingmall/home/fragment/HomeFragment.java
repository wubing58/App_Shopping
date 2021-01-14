package com.example.wb.myshoppingmall.home.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.base.BaseFragment;
import com.example.wb.myshoppingmall.home.adapter.HomeFragmentAdapter;
import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.home.bean.ResultBeanData;
import com.example.wb.myshoppingmall.user.activity.MessageCenterActivity;
import com.example.wb.myshoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

/**
 * 作用：主页面的Fragment
 */

public class HomeFragment extends BaseFragment {

    private TextView tv_search_home;
    private TextView tv_message_home;
    private ImageView ib_top;
    private RecyclerView rv_home;
    private HomeFragmentAdapter adapter;
    /**
     * 返回的数据
     */
    private ResultBeanData.ResultBean resultBean;


    @Override
    public View initView() {
        Log.e(TAG,"主页面的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_home,null);

        tv_search_home = (TextView)view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView)view.findViewById(R.id.tv_message_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        rv_home = (RecyclerView) view.findViewById(R.id.rv_home);


        //设置点击事件
        initListener();

        return view;
    }

    private void initListener() {

        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_home.scrollToPosition(0);
                Toast.makeText(mContext,"回到顶部",Toast.LENGTH_LONG).show();
            }
        });

        //搜索的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"搜索",Toast.LENGTH_LONG).show();
            }
        });

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                startActivity(intent);
                Toast.makeText(mContext,"进入消息中心",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void initData(List<GoodsBean> goodsBeanList) {
        super.initData(goodsBeanList);
        Log.e(TAG,"主页面的Fragment的数据被初始化了");


        //连网请求主页的数据
        getDataFromNet();

    }


    private void getDataFromNet() {

        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {

                    /**
                     * 当请求失败的时候
                     * @param call
                     * @param e
                     * @param id
                     */
                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Log.e(TAG,"首页请求失败=="+e.getMessage());
                    }

                    /**
                     * 当联网成功的时候回调
                     * @param response  请求成功的数据
                     * @param id
                     */
                    @Override
                    public void onResponse(String response, int id) {

                        Log.e(TAG,"首页请求成功=="+response);
                        //解析数据
                        processData(response);
                    }
                });
    }

    private void processData(String json) {

        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);
        resultBean  = resultBeanData.getResult();
        if(resultBean != null){
            //有数据

            //设置适配器
            adapter = new HomeFragmentAdapter(mContext,resultBean);
            rv_home.setAdapter(adapter);

            GridLayoutManager manager = new GridLayoutManager(mContext,1);

            //设置滑动到哪个位置监听,跨度大小监听
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if(position <= 3){
                        //隐藏
                        ib_top.setVisibility(View.GONE);
                    }else{
                        //显示
                        ib_top.setVisibility(View.VISIBLE);
                    }
                    //只能返回1
                    return 1;
                }
            });

            //设置布局管理者
            rv_home.setLayoutManager(manager);

        }else{
            //没有数据

        }
        //Log.e(TAG,"解析成功=="+resultBean.getHot_info().get(0).getName());
    }

}
