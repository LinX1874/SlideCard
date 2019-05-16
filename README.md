# 预览图
![img](https://github.com/james-gan/SlideCard/blob/master/app/src/main/res/gif/aaaa.gif)
# 监听的回调如下：
``` java
// 1. 左右滑动监听
   cardSwitchListener = new CardSlidePanel.CardSwitchListener() {
            @Override
            public void onShow(int index) {
                Log.d("Card", "正在显示-" + dataList.get(index).userName);
            }

            @Override
            public void onCardVanish(int index, int type) {
             //   type=  CardSlidePanel.VANISH_TYPE_LEFT    左边
            //   type= CardSlidePanel.VANISH_TYPE_RIGHT    右边
                Log.d("Card", "正在消失-" + dataList.get(index).userName + " 消失type=" + type);
            }
    };
```
