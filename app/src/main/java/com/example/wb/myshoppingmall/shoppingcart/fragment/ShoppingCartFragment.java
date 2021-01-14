package com.example.wb.myshoppingmall.shoppingcart.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.app.MainActivity;
import com.example.wb.myshoppingmall.base.BaseFragment;
import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.shoppingcart.activity.ShoppingCartActivity;
import com.example.wb.myshoppingmall.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.wb.myshoppingmall.shoppingcart.shoukuanma.Shoukuanma;
import com.example.wb.myshoppingmall.shoppingcart.utils.CartStorage;

import java.util.List;
import static android.content.ContentValues.TAG;

/**
 * 作用：购物车的Fragment
 */

public class ShoppingCartFragment extends BaseFragment implements View.OnClickListener {


    private TextView tvShopcartEdit;
    private RecyclerView recyclerview;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView tvShopcartTotal;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;

    private LinearLayout ll_empty_shopcart;
    private ImageView ivEmpty;
    private TextView tvEmptyCartTobuy;

    private ShoppingCartAdapter adapter;

    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;


    @Override
    public View initView() {
        Log.e(TAG,"购物车的Fragment的UI被初始化了");
        View view = View.inflate(mContext, R.layout.fragment_shopping_cart,null);

        tvShopcartEdit = (TextView)view.findViewById( R.id.tv_shopcart_edit );
        recyclerview = (RecyclerView)view.findViewById( R.id.recyclerview );
        llCheckAll = (LinearLayout)view.findViewById( R.id.ll_check_all );
        checkboxAll = (CheckBox)view.findViewById( R.id.checkbox_all );
        tvShopcartTotal = (TextView)view.findViewById( R.id.tv_shopcart_total );
        btnCheckOut = (Button)view.findViewById( R.id.btn_check_out );
        llDelete = (LinearLayout)view.findViewById( R.id.ll_delete );
        cbAll = (CheckBox)view.findViewById( R.id.cb_all );
        btnDelete = (Button)view.findViewById( R.id.btn_delete );
        btnCollection = (Button)view.findViewById( R.id.btn_collection );

        ll_empty_shopcart = (LinearLayout)view.findViewById(R.id.ll_empty_shopcart);
        ivEmpty = (ImageView)view.findViewById( R.id.iv_empty );
        tvEmptyCartTobuy = (TextView)view.findViewById( R.id.tv_empty_cart_tobuy );

        btnCheckOut.setOnClickListener( this );
        btnDelete.setOnClickListener( this );
        btnCollection.setOnClickListener( this );
        tvEmptyCartTobuy.setOnClickListener( this );

        initListener();

        return view;
    }

    private void initListener() {
        //设置默认的编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int action = (int)v.getTag();
                if(action == ACTION_EDIT){
                    //切换为完成界面
                    showDelete();
                }else {
                    //切换为编辑界面
                    hideDelete();
                }
            }
        });
    }

    private void showDelete() {
        //1、设置状态和文本，变为完成状态
        tvShopcartEdit.setTag(ACTION_COMPLETE);
        tvShopcartEdit.setText("完成");
        //2、变成未勾选
        if (adapter != null){
            adapter.checkAll_none(false);
            adapter.checkAll();
        }
        //3、删除视图显示
        llDelete.setVisibility(View.VISIBLE);
        //4、结算视图隐藏
        llCheckAll.setVisibility(View.GONE);
    }

    private void hideDelete() {
        //1、设置状态和文本，变为编辑状态
        tvShopcartEdit.setTag(ACTION_EDIT);
        tvShopcartEdit.setText("编辑");
        //2、变成未勾选
        if (adapter != null){
            adapter.checkAll_none(true);
            adapter.checkAll();
            adapter.showTotalPrice();
        }
        //3、删除视图隐藏
        llDelete.setVisibility(View.GONE);
        //4、结算视图显示
        llCheckAll.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnCheckOut ) {
            // Handle clicks for btnCheckOut
            //去结算
        Intent intent = new Intent();
        intent.setClass(mContext, Shoukuanma.class);
        startActivity(intent);
        Toast.makeText(mContext,"fragment去结算",Toast.LENGTH_SHORT).show();
        } else if ( v == btnDelete ) {
            // Handle clicks for btnDelete
            //删除选中的
            adapter.deleteData();
            //校验状态
            adapter.checkAll();
            //数据大小为0
            if(adapter.getItemCount() == 0){
                emptyShoppingCart();
            }
        } else if ( v == btnCollection ) {
            // Handle clicks for btnCollection
            Toast.makeText(mContext,"收藏",Toast.LENGTH_SHORT).show();
        }else if(v == tvEmptyCartTobuy){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void initData() {
        super.initData();
        Log.e(TAG,"购物车的Fragment的数据被初始化了");

    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }

    /**
     * 显示数据
     */
    public void showData() {
        CartStorage cartStorage =  CartStorage.getInstance(this.getContext());
        List<GoodsBean> goodsBeanList =cartStorage.getAllData();
        if(goodsBeanList != null && goodsBeanList.size() > 0){
            //有数据
            //设置初始布局显示
            tvShopcartEdit.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.VISIBLE);
            hideDelete();
            //把当没有数据显示的布局-隐藏
            ll_empty_shopcart.setVisibility(View.GONE);
            //设置适配器
            adapter = new ShoppingCartAdapter(mContext, goodsBeanList, tvShopcartTotal, cartStorage, checkboxAll, cbAll);
            recyclerview.setAdapter(adapter);

            //设置布局管理器
            recyclerview.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        }else {
            //没有数据
            //显示数据为空的布局
            emptyShoppingCart();
        }


    }

    private void emptyShoppingCart() {
        ll_empty_shopcart.setVisibility(View.VISIBLE);
        tvShopcartEdit.setVisibility(View.GONE);
        llDelete.setVisibility(View.GONE);
    }



}
