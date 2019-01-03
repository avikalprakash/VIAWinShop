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


public class PopularMenuListAdapter extends BaseAdapter {

    ArrayList<SectorPojo> sectorPojoArrayList =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    ImageView prod_image;
    TextView prod_name_text, prod_price_text, prod_desc_txt, prod_spec_price_text;



    public PopularMenuListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList){
        this.sectorPojoArrayList =introducerPojoArrayList;
        this.context=activity;
    }
    @Override
    public int getCount() {
        return sectorPojoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return sectorPojoArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
         view = layoutInflater.inflate(R.layout.popular_menu_adapter_layout, null);
        prod_name_text = (TextView)view.findViewById(R.id.product_name);
        prod_price_text = (TextView)view.findViewById(R.id.price);
        prod_desc_txt = (TextView)view.findViewById(R.id.product_description);
        prod_spec_price_text = (TextView)view.findViewById(R.id.sp_price);
        SectorPojo sectorPojo = sectorPojoArrayList.get(i);
        prod_image = (ImageView)view.findViewById(R.id.logomimf);
        prod_name_text.setText(sectorPojo.getProduct_title());

        String intro_string = sectorPojo.getProduct_title();

        if (intro_string.length()>20){
            String upToNCharacters = intro_string.substring(0, Math.min(intro_string.length(), 20));
            prod_name_text.setText(upToNCharacters+"...");
        }else {
            prod_name_text.setText(sectorPojo.getProduct_title());
        }
        String desc_string = sectorPojo.getProduct_desc();
        if (desc_string.length()>25){
            String upToNCharacters = desc_string.substring(0, Math.min(desc_string.length(), 25));
            prod_desc_txt.setText(upToNCharacters+"...");
        }else {
            prod_desc_txt.setText(sectorPojo.getProduct_desc());
        }
        prod_price_text.setText("RM "+sectorPojo.getProduct_price()+".00");

        prod_spec_price_text.setText("RM "+sectorPojo.getProduct_sp_price()+".00");

        Picasso.with(context).load(sectorPojo.getProduct_image())
                .resize(100,100)
                .into(prod_image);

        return view;
    }
}
