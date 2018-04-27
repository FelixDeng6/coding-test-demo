package demo.test.com.codingtestdemo.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import demo.test.com.codingtestdemo.R;
import demo.test.com.codingtestdemo.model.TestBean;

/**
 * Created by felix on 2018/4/25.
 */
public class ListViewAdapter extends BaseAdapter {

    public static final int TYPE_TEXT_IMAGE_ITEM = 0;
    public static final int TYPE_IMAGE_ITEM = 1;

    private static final int TYPE_COUNT =  2;

    private int screenMin;

    private List<TestBean> testBeen = new ArrayList<TestBean>();
    private LayoutInflater inflater;

    public ListViewAdapter(Context context, int screenMin) {
        this.inflater = LayoutInflater.from(context);
        this.screenMin = screenMin;
    }

    @Override
    public int getCount() {

        return testBeen.size();
    }

    @Override
    public TestBean getItem(int position) {

        return testBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        int type = TYPE_TEXT_IMAGE_ITEM;

        if(testBeen.get(position) != null) {

            TestBean testBean = testBeen.get(position);

            if(testBean.getTitle().isEmpty()){
                type = TYPE_IMAGE_ITEM;
            }
        }

        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int currentType = getItemViewType(position);

        if(currentType == TYPE_TEXT_IMAGE_ITEM) {

            ViewHolder viewHolder;

            if (convertView == null) {

                viewHolder = new ViewHolder();

                convertView = inflater.inflate(R.layout.list_item, null);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_imageView);
                viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.id_title);
                viewHolder.textViewDescription = (TextView) convertView.findViewById(R.id.id_description);

                convertView.setTag(viewHolder);//for reuse view holder
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            TestBean testBean = getItem(position);

            if (testBean != null) {

                if (!testBean.getImage().isEmpty()) {
                    if (testBean.getImage().equals("a")) {
                        viewHolder.imageView.setImageResource(R.drawable.image_a);
                    } else if (testBean.getImage().equals("b")) {
                        viewHolder.imageView.setImageResource(R.drawable.image_b);
                    } else if (testBean.getImage().equals("c")) {
                        viewHolder.imageView.setImageResource(R.drawable.image_c);
                    }

                    ViewGroup.LayoutParams lp = viewHolder.imageView.getLayoutParams();
                    lp.width = screenMin / 3;
                    lp.height = screenMin / 4;
                    viewHolder.imageView.setLayoutParams(lp);
                }


                if (!testBean.getTitle().isEmpty()) {
                    viewHolder.textViewTitle.setText(testBean.getTitle());
                }

                if (!testBean.getDescription().isEmpty()) {
                    viewHolder.textViewDescription.setText(testBean.getDescription());
                }
            }
        } else {
            ImageViewHolder imageViewHolder;

            if (convertView == null) {

                imageViewHolder = new ImageViewHolder();

                convertView = inflater.inflate(R.layout.second_list_item, null);
                imageViewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_imageView);

                convertView.setTag(imageViewHolder);//for reuse view holder
            } else {
                imageViewHolder = (ImageViewHolder) convertView.getTag();
            }

            TestBean testBean = getItem(position);

            if (testBean != null) {

                if (!testBean.getImage().isEmpty()) {
                    if (testBean.getImage().equals("a")) {
                        imageViewHolder.imageView.setImageResource(R.drawable.image_a);
                    } else if (testBean.getImage().equals("b")) {
                        imageViewHolder.imageView.setImageResource(R.drawable.image_b);
                    } else if (testBean.getImage().equals("c")) {
                        imageViewHolder.imageView.setImageResource(R.drawable.image_c);
                    }

                    ViewGroup.LayoutParams lp = imageViewHolder.imageView.getLayoutParams();
                    lp.width = screenMin;
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    imageViewHolder.imageView.setLayoutParams(lp);

                    imageViewHolder.imageView.setMaxWidth(screenMin);
                    imageViewHolder.imageView.setMaxHeight(screenMin);
                }
            }
        }

        return convertView;
    }


    public void updateView(List<TestBean> testBeen) {
        this.testBeen.addAll(testBeen);
        this.notifyDataSetChanged();//force to refresh
    }


    class ViewHolder {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDescription;
    }

    class ImageViewHolder {
        ImageView imageView;
    }
}
