package via.winmall.lueorganisation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;


public class HalfListAdapter extends BaseAdapter {

    ArrayList<SectorPojo> introducerPojoArrayList =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    TextView brand_name, store_name;
    ImageView cat_image;



    public HalfListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList){
        this.introducerPojoArrayList=introducerPojoArrayList;
        this.context=activity;
    }
    @Override
    public int getCount() {
        return introducerPojoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return introducerPojoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.half_list_adapter, null);
        }catch ( Exception e){}
        brand_name = (TextView)view.findViewById(R.id.brand_name);
        brand_name.setText(introducerPojoArrayList.get(i).getBrand());
        cat_image = view.findViewById(R.id.cat_image);
        store_name = (TextView)view.findViewById(R.id.store_name);
        store_name.setText(introducerPojoArrayList.get(i).getStore_name());
        Picasso.with(context).load(introducerPojoArrayList.get(i).getSmall_image()).into(cat_image);
        return view;
    }
}
