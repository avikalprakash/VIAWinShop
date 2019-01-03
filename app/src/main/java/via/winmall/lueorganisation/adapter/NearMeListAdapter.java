package via.winmall.lueorganisation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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


public class NearMeListAdapter extends BaseAdapter {

    ArrayList<SectorPojo> sectorPojoArrayList =new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    ImageView store_name;
    TextView store_name_text, open_text, away_txt;
    RatingBar rating;
    String opentime;
    String closetime;




    public NearMeListAdapter(FragmentActivity activity, ArrayList<SectorPojo> introducerPojoArrayList){
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
         view = layoutInflater.inflate(R.layout.near_me_adapter, null);
        store_name_text = (TextView)view.findViewById(R.id.product_name);
        open_text = (TextView)view.findViewById(R.id.store_status);
        away_txt = (TextView)view.findViewById(R.id.away_txt);
        rating = view.findViewById(R.id.rating);
        SectorPojo sectorPojo = sectorPojoArrayList.get(i);
        store_name = (ImageView)view.findViewById(R.id.product_image);
        String intro_string = sectorPojo.getStore_name();

        if (intro_string.length()>35){
            String upToNCharacters = intro_string.substring(0, Math.min(intro_string.length(), 35));
            store_name_text.setText(upToNCharacters+"...");
        }else {
            store_name_text.setText(sectorPojo.getStore_name());
        }

        closetime = sectorPojo.getCloseTime();
        opentime = sectorPojo.getOpenTime();



        rating.setRating(Float.parseFloat(sectorPojo.getRating()));
        away_txt.setText(sectorPojo.getDistance()+" KM");



        Picasso.with(context).load(sectorPojo.getSmall_image())
                .resize(100,100)
                .into(store_name);

        setMerchantStatus();

        return view;
    }


    public void setMerchantStatus(){
        try {
            //String string1 = "20:11:13";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(opentime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            // String string2 = "14:49:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(closetime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            //String someRandomTime = "01:00:00";

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());

            Log.d("svdavasvasfv",currentDateandTime);

            Date d = new SimpleDateFormat("HH:mm:ss").parse(currentDateandTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                open_text.setText(context.getString(R.string.close_now));
            }else
            {
                open_text.setText(context.getString(R.string.open_now));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
