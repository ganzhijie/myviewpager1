package chiochio.mgcaster.com.myviewpager1;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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
        return mListViews.size();
//        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mListViews.get(position), 0);
        Log.e("tag", "instantiateItem " + position);
        return mListViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListViews.get(position));
        Log.e("tag", "destroyItem " + position + "getItemPosition:"+ super.getItemPosition(object));
    }

    @Override
    public int getItemPosition(Object object) {
//        Log.e("ItemPosition", "ItemPosition " + super.getItemPosition(object));
        return super.getItemPosition(object);
    }


}
