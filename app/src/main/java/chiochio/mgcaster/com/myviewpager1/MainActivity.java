package chiochio.mgcaster.com.myviewpager1;

import android.app.Activity;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity{

    public ViewPager viewPager;
    private View view1,view2,view3;
    private TextView txtView1;
    private TextView txtView2;
    private TextView txtView3;
    private List<View> viewList;
    private PagerAdapter pagerAdapter;
    private boolean autoLoop;
    private int currentPosition;



    public AutoScrollHandler autoScrollHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLoop = false;
        setContentView(R.layout.activity_main);
        autoScrollHandler = new AutoScrollHandler(new WeakReference<MainActivity>(this));
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

        view1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("onLongClick",txtView1.getText().toString());
                autoLoop = !autoLoop;
                return false;
            }
        });
        view2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("onLongClick",txtView2.getText().toString());
                autoLoop = !autoLoop;
                return false;
            }
        });
        view3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("onLongClick",txtView3.getText().toString());
                autoLoop = !autoLoop;
                return false;
            }
        });

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
                currentPosition = position;
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
                Log.e("PageScrollStateChanged", "onPageScrollStateChanged " + state);

                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        autoScrollHandler.sendEmptyMessage(AutoScrollHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
//                        autoScrollHandler.sendEmptyMessageDelayed(AutoScrollHandler.MSG_UPDATE_IMAGE, AutoScrollHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }

            }
        });

        //开始轮播效果
        autoScrollHandler.sendEmptyMessageDelayed(AutoScrollHandler.MSG_UPDATE_IMAGE, AutoScrollHandler.MSG_DELAY);
    }

    private class AutoScrollHandler extends Handler {

        /**
         * 请求更新显示的View。
         */
        protected static final int MSG_UPDATE_IMAGE  = 1;
        /**
         * 请求暂停轮播。
         */
        protected static final int MSG_KEEP_SILENT   = 2;
        /**
         * 请求恢复轮播。
         */
        protected static final int MSG_BREAK_SILENT  = 3;
        /**
         * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
         * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
         * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
         */
        protected static final int MSG_PAGE_CHANGED  = 4;

        //轮播间隔时间
        protected static final long MSG_DELAY = 1000;

        //使用弱引用避免Handler泄露.这里的泛型参数可以不是Activity，也可以是Fragment等
        private WeakReference<MainActivity> weakReference;
        private int currentItem = 0;

        private String LOG_TAG = "AutoScrollHandler";

        protected AutoScrollHandler(WeakReference<MainActivity> wk){
            weakReference = wk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(LOG_TAG, "receive message " + msg.what);
            MainActivity activity = weakReference.get();
            if (activity==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (activity.autoScrollHandler.hasMessages(MSG_UPDATE_IMAGE)){
                activity.autoScrollHandler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    activity.viewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    activity.autoScrollHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    if (activity.autoScrollHandler.hasMessages(MSG_UPDATE_IMAGE)){
                        activity.autoScrollHandler.removeMessages(MSG_UPDATE_IMAGE);
                    }
                    break;
                case MSG_BREAK_SILENT:
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }


}
