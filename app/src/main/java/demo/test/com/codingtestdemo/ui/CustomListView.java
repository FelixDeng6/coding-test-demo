package demo.test.com.codingtestdemo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;

import demo.test.com.codingtestdemo.MainActivity;
import demo.test.com.codingtestdemo.R;
import demo.test.com.codingtestdemo.model.TestBean;

/**
 * Created by felix on 2018/4/25.
 */
public class CustomListView {

    private static final String TAG = "CustomListView";

    private Context mContext;

    private ListView listView;

    private ListViewAdapter listViewAdapter;

    private View loadMoreView;

    private int last_index;
    private int total_index;

    private boolean isLoading;

    public CustomListView(Context context, int screenMin){

        this.mContext = context;

        listView = new ListView(mContext);

        loadMoreView = LayoutInflater.from(context).inflate(R.layout.load_more, null);//get "loading" view
        loadMoreView.setVisibility(View.GONE);//default invisible

        listViewAdapter = new ListViewAdapter(mContext, screenMin);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                last_index = firstVisibleItem+visibleItemCount;
                total_index = totalItemCount;
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                //see if scrolled the last item
                if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE))
                {
                    if(!isLoading)
                    {
                        isLoading = true;
                        //show "loading" view
                        loadMoreView.setVisibility(View.VISIBLE);
                        onLoad();
                    }
                }
            }
        });

        listView.addFooterView(loadMoreView, null, false);
        listView.setAdapter(listViewAdapter);
    }

    /**
     * load more data
     */
    private void onLoad() {
        ((MainActivity)mContext).loadData();
    }

    /**
     * load completed
     */
    private void loadCompleted()
    {
        loadMoreView.setVisibility(View.GONE);
        isLoading = false;
        listView.removeFooterView(loadMoreView);
    }

    public ListView getListView() {
        return listView;
    }

    public void updateListView(List<TestBean> testBeen) {
        loadCompleted();
        listViewAdapter.updateView(testBeen);
    }
}
