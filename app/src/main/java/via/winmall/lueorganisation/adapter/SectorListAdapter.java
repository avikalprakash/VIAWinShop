package via.winmall.lueorganisation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;


public class SectorListAdapter extends BaseAdapter {

    ArrayList<SectorPojo> introducerPojoArrayList =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    TextView sectorName;



    public SectorListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList){
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
            view = layoutInflater.inflate(R.layout.sector_list_adapter, null);
        }catch ( Exception e){}
        sectorName = (TextView)view.findViewById(R.id.sectorName);
        sectorName.setText(introducerPojoArrayList.get(i).getCat_title());

        return view;
    }
}
