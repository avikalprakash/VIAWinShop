package via.winmall.lueorganisation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import via.winmall.lueorganisation.Modal.HomeServicePojo;
import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.activity.ProductDetailsActivity;


public class GiftListAdapter extends BaseAdapter {

    ArrayList<HomeServicePojo> introducerPojoArrayList =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    ImageView grid_image;
    TextView gift_name_text, price_text, merchant_name;
    RelativeLayout fullRelative;



    public GiftListAdapter(FragmentActivity activity, ArrayList<HomeServicePojo> introducerPojoArrayList){
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        try {
            layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.gift_adapter_layout, null);
        }catch ( Exception e){}
        gift_name_text = (TextView)view.findViewById(R.id.product_name);
        price_text = (TextView)view.findViewById(R.id.price);
        grid_image = (ImageView)view.findViewById(R.id.logomimf);
        fullRelative = (RelativeLayout)view.findViewById(R.id.fullRelative);
        gift_name_text.setText(introducerPojoArrayList.get(i).getProduct_title());
        price_text.setText("RM "+introducerPojoArrayList.get(i).getProduct_price());
        Picasso.with(context).load(introducerPojoArrayList.get(i).getProduct_image()).into(grid_image);

        fullRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(context, ProductDetailsActivity.class);
              intent.putExtra("product_id", introducerPojoArrayList.get(i).getProduct_id());
              context.startActivity(intent);
            }
        });

        return view;
    }
}
