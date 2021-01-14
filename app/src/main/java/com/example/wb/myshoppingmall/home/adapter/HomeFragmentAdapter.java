package com.example.wb.myshoppingmall.home.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wb.myshoppingmall.R;
import com.example.wb.myshoppingmall.app.GoodsInfoActivity;
import com.example.wb.myshoppingmall.home.bean.GoodsBean;
import com.example.wb.myshoppingmall.home.bean.ResultBeanData;
import com.example.wb.myshoppingmall.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.youth.banner.transformer.RotateDownTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    /**
     * 广告条幅类型
     */
    public static final int BANNER = 0;

    /**
     * 频道类型
     */
    public static final int CHANNEL = 1;

    /**
     * 活动类型
     */
    public static final int  ACT = 2;

    /**
     * 秒杀类型
     */
    public static final int  SECKILL = 3;

    /**
     * 推荐类型
     */
    public static final int  RECOMMEND = 4;

    /**
     * 热卖类型
     */
    public static final int  HOT = 5;

    private static final String GOODS_BEAN = "goodsBean";

    /**
     * 用来初始化布局
     * LayoutInflater 的作用就是将XML布局文件实例化为相应的 View 对象
     * LayoutInflater：加载布局并实例化
     */
    private final LayoutInflater mLayoutInflater;

    private  Context mContext;

    /**
     * 数据
     */
    private  ResultBeanData.ResultBean resultBean;


    /**
     * 当前类型
     */
    private int currentType = BANNER;

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于getView的创建ViewHolder部分代码
     * RecyclerView.ViewHolder 作用：用户滑动屏幕切换视图时，上一个视图会回收利用，RecyclerView所做的就是回收再利用，循环往复
     * 创建ViewHolder 作用：容纳View视图
     * @param parent    父类
     * @param viewType  当前的类型
     * @return
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == BANNER){
            //显示广告视图
            return new BannerViewHolder(mContext,mLayoutInflater.inflate(R.layout.banner_viewpager,null));
        }else if(viewType == CHANNEL){
            //显示频道视图
            return new ChannelViewHolder(mContext,mLayoutInflater.inflate(R.layout.channel_item,null));
        }else if(viewType == ACT){
            //显示活动视图
            return new ActViewHolder(mContext,mLayoutInflater.inflate(R.layout.act_item,null));
        }else if(viewType == SECKILL){
            //显示秒杀视图
            return new SeckillViewHolder(mContext,mLayoutInflater.inflate(R.layout.seckill_item,null));
        }else if(viewType == RECOMMEND){
            //显示推荐视图
            return new RecommendViewHolder(mContext,mLayoutInflater.inflate(R.layout.recommend_item,null));
        }else if(viewType == HOT){
            //显示热卖视图
            return new HotViewHolder(mContext,mLayoutInflater.inflate(R.layout.hot_item,null));
        }
        return null;
    }

    /**
     * 相当于getView中的绑定数据模块
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //getItemViewType得到当前视图位置，相等就显示出来
        if(getItemViewType(position) == BANNER){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        }else if(getItemViewType(position) == CHANNEL){
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        }else if(getItemViewType(position) == ACT){
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        } else if(getItemViewType(position) == SECKILL){
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        }else if(getItemViewType(position) == RECOMMEND){
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        }else if(getItemViewType(position) == HOT){
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = itemView.findViewById(R.id.banner);
        }

        public void setData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {
            //设置Banner的数据

            //得到图片集合地址
            List<String> imagesUrl = new ArrayList<>();
            for(int i = 0; i < banner_info.size(); i++ ){
                String imageUrl = banner_info.get(i).getImage();
                imagesUrl.add(imageUrl);
            }
            //设置循环指示点
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            //设置手风琴效果
            banner.setBannerAnimation(Transformer.Accordion);

            banner.setImages(imagesUrl, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {

                    /**
                     *  连网请求图片 使用Glide请求
                     * Glide 是一个 Android 上的图片加载和缓存库，其目的是实现平滑的图片列表滚动效果。
                     */
                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE+url).into(view);

                }
            });

            //设置item的点击事件
            banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext,"广告 position"+position,Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    class ChannelViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;

        public ChannelViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv_channel =  itemView.findViewById(R.id.gv_channel);

            //设置item的点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Toast.makeText(mContext,"频道 position"+position,Toast.LENGTH_LONG).show();
                }
            });
        }


        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            //得到了数据
            //设置GridView的适配器
            adapter = new ChannelAdapter(mContext,channel_info);
            gv_channel.setAdapter(adapter);

        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private ViewPager act_viewpager;


        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            act_viewpager =  itemView.findViewById(R.id.act_viewpager);

        }

        public void setData(List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            //设置间距、动画效果
            act_viewpager.setPageMargin(60);
            act_viewpager.setOffscreenPageLimit(3);
            //setPageTransformer决定动画效果
            act_viewpager.setPageTransformer(true,new RotateDownTransformer());

            //1、有数据了
            //2、设置适配器
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                /**
                 *
                 * @param view      页面
                 * @param object    instantiateItem方法返回的值
                 * @return
                 */
                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                    return view == object;
                }

                /**
                 *
                 * @param container     ViewPaper
                 * @param position      对应页面的位置
                 * @return
                 */
                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, int position) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE+act_info.get(position).getIcon_url()).into(imageView);
                    //添加到容器中
                    container.addView(imageView);

                    //设置点击事件
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(mContext,"活动 position"+position,Toast.LENGTH_LONG).show();
                        }
                    });

                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View)object);
                }

            });
        }
    }



    class SeckillViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter adapter;

        /**
         * 相差多少时间 -毫秒
         */
        private long dt = 0;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                dt = dt - 1000;

                //将毫秒转化为时分秒
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String time = dateFormat.format(new Date(dt));
                tv_time_seckill.setText(time);

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0,1000);

                if(dt <= 0){
                    //把消息移除
                     handler.removeCallbacksAndMessages(null);
                }

            }
        };

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_seckill = itemView.findViewById(R.id.tv_more_seckill);
            tv_time_seckill = itemView.findViewById(R.id.tv_time_seckill);
            rv_seckill = itemView.findViewById(R.id.rv_seckill);

            //设置查看更多点击事件
            tv_more_seckill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"查看更多",Toast.LENGTH_LONG).show();
                }
            });

        }

        public void setData(ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            //1、得到数据了
            //2、设置数据：文本和RecylerView的数据
            adapter = new SeckillRecyclerViewAdapter(mContext,seckill_info.getList());
            rv_seckill.setAdapter(adapter);

            //设置布局管理器
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));


            //秒杀倒计时 -毫秒
            dt = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());

            //设置item点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext,"秒杀 position"+position,Toast.LENGTH_LONG).show();

                    //秒杀商品信息类
                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean= seckill_info.getList().get(position);
                    //商品信息类
                    String name = listBean.getName();
                    String cover_price = listBean.getCover_price();
                    String figure = listBean.getFigure();
                    String product_id = listBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setFigure(listBean.getFigure());
                    goodsBean.setName(listBean.getName());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });

            handler.sendEmptyMessageDelayed(0,1000);

        }
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private TextView tv_more_recommend;
        private GridView gv_recommend;
        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;

            tv_more_recommend = itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = itemView.findViewById(R.id.gv_recommend);

            //点击事件
            tv_more_recommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"查看更多",Toast.LENGTH_LONG).show();
                }
            });

        }


        public void setData(List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            //得到数据了
            //设置适配器
            adapter = new RecommendGridViewAdapter(mContext,recommend_info);
            gv_recommend.setAdapter(adapter);

            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Toast.makeText(mContext,"新品推荐 position"+position,Toast.LENGTH_LONG).show();
                    //推荐商品信息类
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                    //商品信息类
                    String cover_price = recommend_info.get(position).getCover_price();
                    String name = recommend_info.get(position).getName();
                    String figure = recommend_info.get(position).getFigure();
                    String product_id = recommend_info.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    class HotViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private TextView tv_more_hot;
        private GridView gv_hot;
        private HotGridViewAdapter adapter;

        public HotViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            tv_more_hot = itemView.findViewById(R.id.tv_more_hot);
            gv_hot = itemView.findViewById(R.id.gv_hot);

            //点击事件
            tv_more_hot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"查看更多",Toast.LENGTH_LONG).show();
                }
            });

        }

        public void setData(List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            //得到数据了
            //设置适配器
            adapter = new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);

            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Toast.makeText(mContext,"热门 position"+position,Toast.LENGTH_LONG).show();
                    //热卖商品信息类
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                    //商品信息类
                    String cover_price = hot_info.get(position).getCover_price();
                    String name = hot_info.get(position).getName();
                    String figure = hot_info.get(position).getFigure();
                    String product_id = hot_info.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }

    /**
     * 启动商品信息列表页面
     * @param goodsBean
     */
    private void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }

    /**
     * 得到类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position){
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;

        }
        return currentType;
    }

    /**
     * 总共有多少个item，Json的result里的6个类型
     * @return
     */
    @Override
    public int getItemCount() {
        return 6;
    }
}