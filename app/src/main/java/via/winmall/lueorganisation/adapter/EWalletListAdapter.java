package via.winmall.lueorganisation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;


public class EWalletListAdapter extends BaseAdapter {

    ArrayList<SectorPojo> evalletarraylist =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    TextView walletText;
    LinearLayout linear;




    public EWalletListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList){
        this.evalletarraylist =introducerPojoArrayList;
        this.context=activity;
    }
    @Override
    public int getCount() {
        return evalletarraylist.size();
    }

    @Override
    public Object getItem(int i) {
        return evalletarraylist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
         view = layoutInflater.inflate(R.layout.ewallet_list_adapter, null);
        walletText = (TextView)view.findViewById(R.id.walletText);
        SectorPojo sectorPojo = evalletarraylist.get(i);
        walletText.setText("MYR "+sectorPojo.getRm_value()+" for "+ sectorPojo.getCredit_value()+" credits");

        linear = view.findViewById(R.id.linear);
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
