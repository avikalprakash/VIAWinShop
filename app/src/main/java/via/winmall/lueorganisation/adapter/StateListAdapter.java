package via.winmall.lueorganisation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import via.winmall.lueorganisation.Modal.SectorPojo;
import via.winmall.lueorganisation.R;


public class StateListAdapter extends BaseAdapter {

    ArrayList<SectorPojo> statePojoList =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    TextView stateName;
    LinearLayout nameLayout;
    MyAdapterListener myAdapterListener;



    public StateListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList, MyAdapterListener myAdapterListener){
        this.statePojoList=introducerPojoArrayList;
        this.context=activity;
        this.myAdapterListener = myAdapterListener;
    }


    public StateListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList){
        this.statePojoList=introducerPojoArrayList;
        this.context=activity;
    }


    public interface MyAdapterListener {

        void onContainerClick(View view, String state_id, String state_name);
    }
    @Override
    public int getCount() {
        return statePojoList.size();
    }

    @Override
    public Object getItem(int i) {
        return statePojoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        try {
            layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.state_list_adapter, null);
        }catch ( Exception e){}
        stateName = (TextView)view.findViewById(R.id.stateName);
        nameLayout = (LinearLayout)view.findViewById(R.id.nameLayout);
        stateName.setText(statePojoList.get(i).getState_name());




        nameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stateID = statePojoList.get(i).getState_id();
                String state_name = statePojoList.get(i).getState_name();
                myAdapterListener.onContainerClick(v, stateID, state_name);


             /*   final int sdk = android.os.Build.VERSION.SDK_INT;
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    nameLayout.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.btn_red_high_radious) );
                } else {
                    nameLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_red_high_radious));
                }*/


            }
        });


        return view;
    }
}
