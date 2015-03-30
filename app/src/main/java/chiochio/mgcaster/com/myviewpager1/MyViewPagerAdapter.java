package chiochio.mgcaster.com.myviewpager1;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * Created by ganzhijie on 15-3-27.
 */
public class MyViewPagerAdapter extends PagerAdapter{

    private List<View> mListViews;

    public MyViewPagerAdapter(List<View> listViews) {
        mListViews = listViews;


    }

    @Override
    public int getCount() {
//        return mListViews.size();
        //设置成最大，使用户看不到边界
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        container.addView(mListViews.get(position), 0);
//        Log.e("tag", "instantiateItem " + position);
//        return mListViews.get(position);

        //对ViewPager页号求模取出View列表中要显示的项
        position %= mListViews.size();
        if (position<0){
            position = mListViews.size()+position;
        }
        View view = mListViews.get(position);
        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        ViewParent vp =view.getParent();
        if (vp!=null){
            ViewGroup parent = (ViewGroup)vp;
            parent.removeView(view);
        }
        container.addView(view);
        //add listeners here if necessary
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //Warning：不要在这里调用removeView
        //container.removeView(mListViews.get(position));
        Log.e("tag", "destroyItem " + position + "getItemPosition:"+ super.getItemPosition(object));
    }
}
