package com.example.wb.myshoppingmall.app;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.home.adapter.HomeFragmentAdapter;
import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.shoppingcart.activity.ShoppingCartActivity;
import com.example.wb.myshoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.example.wb.myshoppingmall.shoppingcart.utils.CartStorage;
import com.example.wb.myshoppingmall.utils.Constants;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {

    private ImageButton ibGoodInfoBack;
    private ImageButton ibGoodInfoMore;
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;
    private TextView tvGoodInfoCollection;
    private TextView tvGoodInfoCart;
    private Button btnGoodInfoAddcart;
    private TextView tv_more_share;
    private TextView tv_more_search;
    private TextView tv_more_home;
    private Button btn_more;
    private GoodsBean goodsBead;
    private LinearLayout ll_root;


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-11-22 22:17:56 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        ibGoodInfoBack = (ImageButton)findViewById( R.id.ib_good_info_back );
        ibGoodInfoMore = (ImageButton)findViewById( R.id.ib_good_info_more );
        ivGoodInfoImage = (ImageView)findViewById( R.id.iv_good_info_image );
        tvGoodInfoName = (TextView)findViewById( R.id.tv_good_info_name );
        tvGoodInfoDesc = (TextView)findViewById( R.id.tv_good_info_desc );
        tvGoodInfoPrice = (TextView)findViewById( R.id.tv_good_info_price );
        tvGoodInfoStore = (TextView)findViewById( R.id.tv_good_info_store );
        tvGoodInfoStyle = (TextView)findViewById( R.id.tv_good_info_style );
        wbGoodInfoMore = (WebView)findViewById( R.id.wb_good_info_more );
        llGoodsRoot = (LinearLayout)findViewById( R.id.ll_goods_root );
        tvGoodInfoCallcenter = (TextView)findViewById( R.id.tv_good_info_callcenter );
        tvGoodInfoCollection = (TextView)findViewById( R.id.tv_good_info_collection );
        tvGoodInfoCart = (TextView)findViewById( R.id.tv_good_info_cart );
        btnGoodInfoAddcart = (Button)findViewById( R.id.btn_good_info_addcart );
        tv_more_share = (TextView)findViewById(R.id.tv_more_share);
        tv_more_search = (TextView)findViewById(R.id.tv_more_search);
        tv_more_home = (TextView)findViewById(R.id.tv_more_home);
        btn_more = (Button) findViewById(R.id.btn_more);
        ll_root = (LinearLayout)findViewById(R.id.ll_root);

        ibGoodInfoBack.setOnClickListener( this );
        ibGoodInfoMore.setOnClickListener( this );
        btnGoodInfoAddcart.setOnClickListener( this );

        tvGoodInfoCallcenter.setOnClickListener( this );
        tvGoodInfoCollection.setOnClickListener( this );
        tvGoodInfoCart.setOnClickListener( this );

        tv_more_share.setOnClickListener( this );
        tv_more_search.setOnClickListener( this );
        tv_more_home.setOnClickListener( this );


    }


    @Override
    public void onClick(View v) {
        if ( v == ibGoodInfoBack ) {
            // Handle clicks for ibGoodInfoBack
            finish();
        } else if ( v == ibGoodInfoMore ) {
            // Handle clicks for btnGoodInfoAddcart
            if (ll_root.getVisibility() == View.VISIBLE) {
                ll_root.setVisibility(View.GONE);
            } else {
                ll_root.setVisibility(View.VISIBLE);
            }
            Toast.makeText(this,"更多",Toast.LENGTH_LONG).show();
        } else if ( v == btnGoodInfoAddcart ) {
            // Handle clicks for btnGoodInfoAddcart
            CartStorage.getInstance(this.getApplicationContext()).addData(goodsBead);
            Toast.makeText(this,"添加到购物车成功了",Toast.LENGTH_LONG).show();
        } else if ( v == tvGoodInfoCallcenter ) {
            Toast.makeText(this,"联系客服",Toast.LENGTH_LONG).show();
        }else if ( v == tvGoodInfoCollection ) {
            Toast.makeText(this,"收藏",Toast.LENGTH_LONG).show();
        }else if ( v == tvGoodInfoCart ) {
            Intent intent = new Intent(this, ShoppingCartActivity.class);
            startActivity(intent);
            Toast.makeText(this,"跳转到购物车",Toast.LENGTH_LONG).show();
        }else if ( v == tv_more_share ) {
            Toast.makeText(this,"分享",Toast.LENGTH_LONG).show();
        }else if ( v == tv_more_search ) {
            Toast.makeText(this,"搜索",Toast.LENGTH_LONG).show();
        }else if ( v == tv_more_home ) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this,"主页面",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);


        findViews();

        //接收数据
        goodsBead = (GoodsBean)getIntent().getSerializableExtra("goodsBean");
        if(goodsBead != null){
            //Toast.makeText(this,"goodsBead"+goodsBead,Toast.LENGTH_LONG).show();
            setDataForView(goodsBead);
        }
    }

    /**
     * 设置数据
     * @param goodsBead
     */
    private void setDataForView(GoodsBean goodsBead) {

        //设置图片
        Glide.with(this).load(Constants.BASE_URL_IMAGE+goodsBead.getFigure()).into(ivGoodInfoImage);
        //设置文本
        tvGoodInfoName.setText(goodsBead.getName());
        //设置价格
        tvGoodInfoPrice.setText("￥"+goodsBead.getCover_price());

        setWebDataView(goodsBead.getProduct_id());
    }

    private void setWebDataView(String product_id) {
        if(product_id != null){
            wbGoodInfoMore.loadUrl("http://www.bilibili.com");
            //设置支持JavaScript
            WebSettings webSettings = wbGoodInfoMore.getSettings();
            webSettings.setUseWideViewPort(true);//支持双击页面变大变小
            webSettings.setJavaScriptEnabled(true);//支持JavaScript
            //优先使用缓存
            webSettings.setCacheMode(webSettings.LOAD_CACHE_ELSE_NETWORK);


            wbGoodInfoMore.setWebViewClient(new WebViewClient(){

                //版本低于21
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制webview打开，为false调用系统浏览器或者第三方浏览器
                    view.loadUrl(url);
                    return true;
                }

                //版本大于21
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
            });

        }
    }
}