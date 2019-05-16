package com.james.card;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.james.card.bean.CardDataItem;
import com.james.card.library.CardAdapter;
import com.james.card.library.CardSlidePanel;

import java.util.ArrayList;
import java.util.List;

/**
 * github:  https://github.com/james-gan 欢迎star
 */
public class MainActivity extends FragmentActivity {

    private CardSlidePanel.CardSwitchListener cardSwitchListener;

    private String imagePaths[] = {"file:///android_asset/wall01.jpg",
            "file:///android_asset/wall02.jpg", "file:///android_asset/wall03.jpg",
            "file:///android_asset/wall04.jpg", "file:///android_asset/wall05.jpg",
            "file:///android_asset/wall06.jpg", "file:///android_asset/wall07.jpg",
            "file:///android_asset/wall08.jpg", "file:///android_asset/wall09.jpg",
            "file:///android_asset/wall10.jpg", "file:///android_asset/wall11.jpg",
            "file:///android_asset/wall12.jpg"}; // 12个图片资源
    private String names[] = {"柳岩1", "柳岩2", "柳岩3", "柳岩4", "杨幂1", "杨幂2", "杨幂3",
            "刘亦菲1", "刘亦菲2", "刘亦菲3", "杨颖1", "杨颖2"}; // 12个人名
    private List<CardDataItem> dataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        final CardSlidePanel slidePanel = (CardSlidePanel) findViewById(R.id.image_slide_panel);
        // 1. 左右滑动监听
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {
            @Override
            public void onShow(int index) {
                Log.d("Card", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
//           type=  CardSlidePanel.VANISH_TYPE_LEFT    左边
//           type= CardSlidePanel.VANISH_TYPE_RIGHT    右边
                Log.d("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);
        // 2. 绑定Adapter
        slidePanel.setAdapter(new CardAdapter() {
            @Override
            public int getLayoutId() {
                return R.layout.card_item;
            }

            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public void bindView(View view, int index) {
                Object tag = view.getTag();
                ViewHolder viewHolder;
                if (null != tag) {
                    viewHolder = (ViewHolder) tag;
                } else {
                    viewHolder = new ViewHolder(view);
                    view.setTag(viewHolder);
                }
                viewHolder.bindData(dataList.get(index));
            }
            @Override
            public Object getItem(int index) {
                return dataList.get(index);
            }

            @Override
            public Rect obtainDraggableArea(View view) {
                // 可滑动区域定制，该函数只会调用一次
                View contentView = view.findViewById(R.id.card_item_content);
                View topLayout = view.findViewById(R.id.card_top_layout);
                View bottomLayout = view.findViewById(R.id.card_bottom_layout);
                int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
                int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
                int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
                int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
                return new Rect(left, top, right, bottom);
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        }, 500);
        // 3. notifyDataSetChanged调用
        findViewById(R.id.notify_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDataList();
                slidePanel.getAdapter().notifyDataSetChanged();
            }
        });
    }
    private void prepareDataList() {
        for (int i = 0; i < 12; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = names[i];
            dataItem.imagePath = imagePaths[i];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }
    //重新加入数据
    private void appendDataList() {
        for (int i = 0; i < 12; i++) {
            CardDataItem dataItem = new CardDataItem();
            dataItem.userName = names[i];
            dataItem.imagePath = imagePaths[i];
            dataItem.likeNum = (int) (Math.random() * 10);
            dataItem.imageNum = (int) (Math.random() * 6);
            dataList.add(dataItem);
        }
    }
    class ViewHolder {
        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView likeNumTv;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.card_image_view);
            maskView = view.findViewById(R.id.maskView);
            userNameTv = (TextView) view.findViewById(R.id.card_user_name);
            imageNumTv = (TextView) view.findViewById(R.id.card_pic_num);
            likeNumTv = (TextView) view.findViewById(R.id.card_like);
        }
        public void bindData(CardDataItem itemData) {
            Glide.with(MainActivity.this).load(itemData.imagePath).into(imageView);
            userNameTv.setText(itemData.userName);
            imageNumTv.setText(itemData.imageNum + "");
            likeNumTv.setText(itemData.likeNum + "");
        }
    }

}
