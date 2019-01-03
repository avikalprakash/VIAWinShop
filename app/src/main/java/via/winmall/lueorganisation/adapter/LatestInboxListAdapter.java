package via.winmall.lueorganisation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;


public class LatestInboxListAdapter extends BaseAdapter {

    ArrayList<SectorPojo> latestpojoList =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    TextView title, readStatus , shortDesc;



    public LatestInboxListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList){
        this.latestpojoList=introducerPojoArrayList;
        this.context=activity;
    }
    @Override
    public int getCount() {
        return latestpojoList.size();
    }

    @Override
    public Object getItem(int i) {
        return latestpojoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {
            layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.latest_news_list_adapter, null);
        }catch ( Exception e){}
        title = (TextView)view.findViewById(R.id.title);
        readStatus = (TextView)view.findViewById(R.id.readStatus);
        shortDesc = (TextView)view.findViewById(R.id.shortDesc);
        title.setText(latestpojoList.get(i).getTitle());

        shortDesc.setText(latestpojoList.get(i).getDescription());


        String read = latestpojoList.get(i).getRead_status();
        if (read.equals("0")){
            readStatus.setText("New");
            readStatus.setTextColor(Color.GREEN);
        } else if (read.equals("1")) {
            readStatus.setText("Read");
            readStatus.setTextColor(Color.GRAY);
        }

        return view;
    }
}
