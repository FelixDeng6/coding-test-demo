package demo.test.com.codingtestdemo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import demo.test.com.codingtestdemo.model.TestBean;
import demo.test.com.codingtestdemo.ui.CustomListView;
import demo.test.com.codingtestdemo.ui.CustomPagerAdapter;

public class MainActivity extends AppCompatActivity implements OnDataChangedListener {

    private static final String TAG = "MainActivity";

    private Context mContext;

    private int mScreenMax; // Screen height
    private int mScreenMin; // Screen Width

    //three title text views
    private TextView textViewCityGuide;
    private TextView textViewShop;
    private TextView textViewEat;

    //viewPager
    private ViewPager viewPager;

    private List<CustomListView> customListViews;

    private DataStore dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        getScreenSize();

        textViewCityGuide = (TextView)findViewById(R.id.id_text_cityguide);
        textViewShop = (TextView)findViewById(R.id.id_text_shop);
        textViewEat = (TextView)findViewById(R.id.id_text_eat);

        Vector<View> pages = new Vector<View>();

        //create 3 list views and add them to viewPager
        customListViews = new ArrayList<CustomListView>();

        for(int i = 0; i < 3; i++) {
            CustomListView customListView = new CustomListView(mContext, mScreenMin);
            customListViews.add(customListView);

            pages.add(customListView.getListView());
        }

        //get view pager and set its adapter
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(mContext, pages));

        //add an page change listener
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.v(TAG, "onPageSelected: " + position);

                //change the color of the title text
                switch (position){
                    case 0:
                        textViewCityGuide.setTextColor(Color.BLACK);
                        textViewShop.setTextColor(ContextCompat.getColor(mContext, R.color.colorLightGray));
                        textViewEat.setTextColor(ContextCompat.getColor(mContext, R.color.colorLightGray));
                        break;
                    case 1:
                        textViewCityGuide.setTextColor(ContextCompat.getColor(mContext, R.color.colorLightGray));
                        textViewShop.setTextColor(Color.BLACK);
                        textViewEat.setTextColor(ContextCompat.getColor(mContext, R.color.colorLightGray));
                        break;
                    case 2:
                        textViewCityGuide.setTextColor(ContextCompat.getColor(mContext, R.color.colorLightGray));
                        textViewShop.setTextColor(ContextCompat.getColor(mContext, R.color.colorLightGray));
                        textViewEat.setTextColor(Color.BLACK);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        dataStore = new DataStore(mContext, this);
        loadData();
    }

    /*
    * fetched data
    * */
    @Override
    public void onDataChanged(List<TestBean> testBeen) {
        if(testBeen != null) {
            for(int i = 0; i < 3; i++) {

                customListViews.get(i).updateListView(testBeen);

            }
        }
    }

    /*
    * load data
    * */
    public void loadData(){
        dataStore.fetchCityGuide();
    }

    /*
    * get screen size
    * */
    public void getScreenSize(){
        Display display = getWindowManager().getDefaultDisplay();
        Point screen = new Point();
        display.getSize(screen);
        mScreenMax = screen.x > screen.y ? screen.x : screen.y;
        mScreenMin = screen.x < screen.y ? screen.x : screen.y;
    }
}
