package com.example.wb.myshoppingmall.shoppingcart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 *购物车的存储类
 */
public class CartStorage {

    public static final String JSON_CART = "json_cart";
    public static CartStorage instance=null;
    private static Context mContext;
    //sparseArray的性能优于HashMap
    private SparseArray<GoodsBean> sparseArray;

    private  CartStorage(Context context){
        this.mContext = context;
        //把之前存储的数据读取出来
        sparseArray = new SparseArray<>(100);

        listToSparseArray();
    }

    /**
     * 从本地读取的数据加入到sparseArray中
     */
    private void listToSparseArray() {
        List<GoodsBean> goodsBeanList = getAllData();
        //把List数据转为SparseArray数据
        for (int i = 0; i < goodsBeanList.size(); i++){
            GoodsBean goodsBean = goodsBeanList.get(i);
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        }
    }

    /**
     * 获取本地所有的数据
     * @return
     */
    public List<GoodsBean> getAllData() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        //1、从本地获取
        String json = CacheUtils.getString(mContext, JSON_CART);
        //2、使用Gson转换成列表
        if( !TextUtils.isEmpty(json) ){//判断不为空的时候执行
            //把String类型转为List
            goodsBeanList = new Gson().fromJson(json,new TypeToken<List<GoodsBean>>(){}.getType());
        }
        return goodsBeanList;
    }

    /**
     * 得到购物车实例,懒汉式
     * @return
     */
    public static CartStorage getInstance(Context mContext){

        if(instance == null){
            synchronized (CartStorage.class){
                if(instance == null){
                    instance = new CartStorage(mContext);
                }
            }
        }
        return instance;
    }



    /**
     * 添加数据
     * @param goodsBean
     */
    public void addData(GoodsBean goodsBean){

        if (goodsBean == null) {
            Log.e("Tag", "good is null");
        }

        //1、添加到内存中SparseArray
        //如果当前数据已经存在，就修改成number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));
        if(tempData != null){
            //内存中有了这条数据
            tempData.setNumber(tempData.getNumber()+goodsBean.getNumber());
        }else {
            //内存中没有这条数据
            tempData = goodsBean;
            tempData.setNumber(1);
        }
        //同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);

        //2、同步到本地
        saveLocal();

    }

    /**
     * 删除数据
     * @param goodsBean
     */
    public void deleteData(GoodsBean goodsBean) {
        //1、内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));
        //2、把内存的保存到本地
        saveLocal();
    }

    /**
     * 更新数据
     * @param goodsBean
     */
    public void updateData(GoodsBean goodsBean) {
        //1、内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        //2、同步到本地
        saveLocal();
    }
    /**
     * 保存数据到本地
     */
    private void saveLocal() {
        //SparseArray转换成List
        List<GoodsBean> goodsBeanList =  sparseToList();
        //使用Gson把列表转换成String类型
        String json = new Gson().toJson(goodsBeanList);
        //把String数据保存
        CacheUtils.saveString(mContext,JSON_CART,json);
    }

    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        if (sparseArray != null && sparseArray.size() > 0) {
            for (int i = 0; i < sparseArray.size(); i++) {
                GoodsBean goodsBean = sparseArray.valueAt(i);
                goodsBeanList.add(goodsBean);
            }
        }
        return goodsBeanList;
    }
}
