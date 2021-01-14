package com.example.wb.myshoppingmall.shoppingcart.adapter;

import android.content.Context;
import android.icu.text.Edits;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.shoppingcart.utils.CartStorage;
import com.example.wb.myshoppingmall.shoppingcart.view.AddSubView;
import com.example.wb.myshoppingmall.utils.Constants;

import java.util.Iterator;
import java.util.List;

/**
 * 购物车的适配器的构造方法
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private final Context mContext;
    private final List<GoodsBean> datas;    //数据
    private final TextView tvShopcartTotal; //总计
    private final CheckBox checkboxAll; //全选
    private final CheckBox cb_all;//完成界面的全选
    private final CartStorage cartStorage;


    public ShoppingCartAdapter(Context mContext, List<GoodsBean> goodsBeanList, TextView tvShopcartTotal, CartStorage cartStorage, CheckBox cbAll, CheckBox cb_all) {
        this.mContext = mContext;
        this.datas = goodsBeanList;
        this.tvShopcartTotal = tvShopcartTotal;
        this.cartStorage = cartStorage;
        this.checkboxAll = cbAll;
        this.cb_all = cb_all;

        showTotalPrice();

        //设置点击事件
        setListener();
        //校验是否权限
        checkAll();
    }

    public void showTotalPrice() {
        tvShopcartTotal.setText("￥"+getTotalPrice());
    }

    /**
     * 计算总价格
     * @return
     */
    public double getTotalPrice() {
        double totalPrice = 0.0;
        if(datas != null && datas.size() > 0){
            for(int i = 0 ;i < datas.size(); i++){
                GoodsBean goodsBean =datas.get(i);
                if(goodsBean.isSelected()){
                    //总价 = 总价+数量*价格 String转换为double
                    totalPrice = totalPrice + Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());
                }
            }
        }
        return totalPrice;
    }

    private void setListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //1、根据位置找到对应的Bean对象
                GoodsBean goodsBean = datas.get(position);
                //2、设置取反状态
                goodsBean.setSelected(!goodsBean.isSelected());
                //3、刷新状态
                notifyItemChanged(position);
                //4、校验是否全选
                checkAll();
                //5、重新计算总价格
                showTotalPrice();
            }
        });

        //CheckBox的点击事件
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、得到状态
                boolean isCheck = checkboxAll.isChecked();
                //2、根据状态设置全选和非全选
                checkAll_none(isCheck);
                //3、重新计算总价格
                showTotalPrice();
            }
        });

        //cb_all的点击事件
        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1、得到状态
                boolean isCheck = cb_all.isChecked();
                //2、根据状态设置全选和非全选
                checkAll_none(isCheck);
            }
        });
    }


    /**
     * 点击item的监听者
     */
    public interface OnItemClickListener{
        /**
         * 当点击某点的时候被回调
         * @param position
         */
        public void onItemClick(int position);
    }
    public OnItemClickListener onItemClickListener;
    /**
     * 设置item的监听
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 设置全选和非全选
     * @param isCheck
     */
    public void checkAll_none(boolean isCheck) {
        if(datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                GoodsBean goodsBean = datas.get(i);
                goodsBean.setSelected(isCheck);
                notifyItemChanged(i);
            }
        }
    }

    public void checkAll() {
        if(datas != null && datas.size() > 0){
            int number = 0;
            for(int i = 0;i < datas.size();i++){
                GoodsBean goodsBean = datas.get(i);
                if(!goodsBean.isSelected()){
                    //非全选
                    checkboxAll.setChecked(false);
                    cb_all.setChecked(false);
                }else {
                    //选中的就+1
                    number++;
                }
            }
            //如果number = 集合中的总大小相同，就证明全选
            if(number == datas.size()){
                checkboxAll.setChecked(true);
                cb_all.setChecked(true);
            }
        }else {
            checkboxAll.setChecked(false);
            cb_all.setChecked(false);
        }
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.item_shop_cart,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //1、根据位置得到对应的Bean对象
        GoodsBean goodsBean = datas.get(position);
        //2、设置数据
        holder.cb_gov.setChecked(goodsBean.isSelected());
        Glide.with(mContext).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("￥"+goodsBean.getCover_price());
        holder.addSubView.setValue(goodsBean.getNumber());
        holder.addSubView.setMinValue(1);
        holder.addSubView.setMaxValue(10);

        //设置商品数量的变化
        holder.addSubView.setOnNumberChangeListener(new AddSubView.OnNumberChangeListener() {
            @Override
            public void OnNumberChange(int value) {
                //1、当前列表内存中更新
                goodsBean.setNumber(value);
                // 2、本地要更新
                CartStorage.getInstance(mContext).updateData(goodsBean);
                // 3、刷新适配器
                notifyItemChanged(position);//局部刷新notifyItemChanged
                // 3、再次计算总价格
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
/*
    另一种删除
    public void deleteData() {
        if(datas != null && datas.size() > 0){
            for(int i = 0;i < datas.size();i++){
                //删除选中的
                GoodsBean goodsBean = datas.get(i);
                if(goodsBean.isSelected()){
                    //内存-移除
                    datas.remove(goodsBean);
                    //保存和同步到本地
                    CartStorage.getInstance(mContext).deleteData(goodsBean);
                    //刷新
                    notifyItemRemoved(i);
                    i--;
                }
            }
        }
    }

 */

    public void deleteData() {
        if(datas != null && datas.size() > 0){
            for(Iterator iterator = datas.iterator(); iterator.hasNext();){
                //删除选中的
                GoodsBean goodsBean = (GoodsBean) iterator.next();
                if(goodsBean.isSelected()){
                    //根据对象找到对应的位置
                    int position = datas.indexOf(goodsBean);
                    //从本地中删除
                    CartStorage.getInstance(mContext).deleteData(goodsBean);
                    iterator.remove();
                    //刷新
                    notifyItemRemoved(position);
                }
            }
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox cb_gov;
        private ImageView iv_gov;
        private TextView tv_desc_gov;
        private TextView tv_price_gov;
        private AddSubView addSubView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_gov = itemView.findViewById(R.id.cb_gov);
            iv_gov = itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = itemView.findViewById(R.id.tv_price_gov);
            addSubView = itemView.findViewById(R.id.addSubView);

            //设置item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(getLayoutPosition());
                    }
                }
            });
        }
    }



}
