package via.winmall.lueorganisation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import via.winmall.lueorganisation.Fragment.InboxArchieveFragment;
import via.winmall.lueorganisation.Fragment.NewsArchieveFragment;
import via.winmall.lueorganisation.Fragment.NewsLatestFragment;
//import com.example.lue.eatapp.fragments.NotifyFrag;

public class PagerNewsTabDetails extends FragmentStatePagerAdapter {
    private SparseArray<String> TITLES;

    int count;
    public PagerNewsTabDetails(FragmentManager fm, int count) {
        super(fm);
        this.count=count;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                NewsLatestFragment tab1=new NewsLatestFragment();
                return tab1;
            case 1:
                NewsArchieveFragment tab2=new NewsArchieveFragment();
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
