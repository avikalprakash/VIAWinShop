package via.winmall.lueorganisation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import via.winmall.lueorganisation.Fragment.InboxArchieveFragment;
import via.winmall.lueorganisation.Fragment.InboxLatestFragment;
import via.winmall.lueorganisation.Fragment.NewsLatestFragment;
//import com.example.lue.eatapp.fragments.NotifyFrag;

public class PagerInboxTabDetails extends FragmentStatePagerAdapter {
    private SparseArray<String> TITLES;

    int count;
    public PagerInboxTabDetails(FragmentManager fm, int count) {
        super(fm);
        this.count=count;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                InboxLatestFragment tab1=new InboxLatestFragment();
                return tab1;
            case 1:
                InboxArchieveFragment tab2=new InboxArchieveFragment();
                return tab2;
            default:return null;
        }
    }

    @Override
    public int getCount() {
        return count;
    }
    //
    @Override
    public CharSequence getPageTitle(int position) {



        switch (position) {
            case 0:

                return "Latest";

            case 1:
                return "Archieve";
        }
        return super.getPageTitle(position);
    }

}
