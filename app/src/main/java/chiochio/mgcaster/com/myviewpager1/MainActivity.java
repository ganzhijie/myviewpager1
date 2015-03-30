package chiochio.mgcaster.com.myviewpager1;

import android.app.Activity;
import android.media.ImageReader;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity{

    private ViewPager viewPager;
    private View view1,view2,view3;
    private TextView txtView1;
    private TextView txtView2;
    private TextView txtView3;
    private List<View> viewList;
    private PagerAdapter pagerAdapter;
    private int current_position;
    private int count_right = 100;
    private int count_left = 98;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }
    public void initViews(){
        viewPager =(ViewPager)findViewById(R.id.viewpager);
        LayoutInflater lf = getLayoutInflater().from(this);
        view1 = lf.inflate(R.layout.layout1,null);
        view2 = lf.inflate(R.layout.layout2,null);
        view3 = lf.inflate(R.layout.layout3,null);
        txtView1 = (TextView)view1.findViewById(R.id.page1text);
        txtView2 = (TextView)view2.findViewById(R.id.page2text);
        txtView3 = (TextView)view3.findViewById(R.id.page3text);

        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);


        pagerAdapter = new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.e("viewpager", "onPageScrolled " + position);
            }

            @Override
            public void onPageSelected(int position) {
//                Log.e("viewpager", "onPageSelected " + position) ;
                current_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //arg0表示滑动的状态，arg=0表示什么都没做，arg=1表示正在滑动，arg=2表示滑动结束
//                /**
//                 * Indicates that the pager is in an idle, settled state. The current page
//                 * is fully in view and no animation is in progress.
//                 */
//                public static final int SCROLL_STATE_IDLE = 0;
//
//                /**
//                 * Indicates that the pager is currently being dragged by the user.
//                 */
//                public static final int SCROLL_STATE_DRAGGING = 1;
//
//                /**
//                 * Indicates that the pager is in the process of settling to a final position.
//                 */
//                public static final int SCROLL_STATE_SETTLING = 2;
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        Log.e("viewpager", " onPageScrollStateChanged " + state + "current_position: " + current_position);
//                        if(current_position == 2) {
//                            count_right++;
//                            txtView3.setText(Integer.toString(count_right));
//                        }
//                        else if(current_position == 0) {
//                            count_left--;
//                            txtView1.setText(Integer.toString(count_left));
//                        }
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        Log.e("viewpager", " onPageScrollStateChanged " + state + "current_position: " + current_position);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
