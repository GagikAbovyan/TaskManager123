package vtc.room.a101.taskmanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import vtc.room.a101.taskmanager.R;

public class PagesAdapter extends android.support.v4.view.PagerAdapter {

    private final Context context;
    private final int[] images;

    public PagesAdapter(final Context context, final int[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.view_pager_item, container, false);
        final ImageView image = (ImageView) view.findViewById(R.id.image_pager_item);
        image.setImageResource(images[position]);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



}
