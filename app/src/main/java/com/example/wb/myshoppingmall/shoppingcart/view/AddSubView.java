package com.example.wb.myshoppingmall.shoppingcart.view;


import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.wb.myshoppingmall.R;

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private final Context mContext;
    private ImageView iv_sub;
    private TextView tv_value;
    private ImageView iv_add;
    private int value = 1;//当前值
    private int minValue = 1;//最小值
    private int maxValue = 10;//最大值


    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //把布局文件实例化，并且加载到AddSubView类中
        View.inflate(context, R.layout.add_sub_view,this);
        iv_sub = findViewById(R.id.iv_sub);
        tv_value = findViewById(R.id.tv_value);
        iv_add = findViewById(R.id.iv_add);

        int value = getValue();
        setValue(value);

        //设置点击事件
        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_sub://减
                subNumber();
                break;
            case R.id.iv_add://加
                addNumber();
                break;
        }
        //Toast.makeText(mContext,"当前商品数="+value,Toast.LENGTH_LONG).show();
    }

    /**
     * 减
     */
    private void subNumber() {
        if(value > minValue){
            value--;
        }
        setValue(value);

        if(onNumberChangeListener != null){
            onNumberChangeListener.OnNumberChange(value);
        }
    }

    /**
     * 加
     */
    private void addNumber() {
        if(value < maxValue){
            value++;
        }
        setValue(value);

        if(onNumberChangeListener != null){
            onNumberChangeListener.OnNumberChange(value);
        }
    }

    /**
     * 当数量发生变化的时候回调
     */
    public interface OnNumberChangeListener{
        /**
         * 当数据发生变化的时候回调
         * @param value
         */
        public void OnNumberChange(int value);
    }

    private OnNumberChangeListener onNumberChangeListener;

    /**
     * 设置数量变化的监听
     * @param onNumberChangeListener
     */
    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    public int getValue() {
        String valueStr = tv_value.getText().toString().trim();
        if(!TextUtils.isEmpty(valueStr)){
            value = Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}