package via.winmall.lueorganisation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import via.winmall.lueorganisation.Fragment.OtherDetailsFragment;
import via.winmall.lueorganisation.Fragment.PopularMenuFragment;
import via.winmall.lueorganisation.Fragment.TandCFragment;
//import com.example.lue.eatapp.fragments.NotifyFrag;

public class PagerMerchantTabDetails extends FragmentStatePagerAdapter {
    private SparseArray<String> TITLES;

    int count;
    public PagerMerchantTabDetails(FragmentManager fm, int count) {
        super(fm);
        this.count=count;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                PopularMenuFragment tab1=new PopularMenuFragment();
                return tab1;
            case 1:
                OtherDetailsFragment tab2=new OtherDetailsFragment();
                return tab2;
            case 2:
                TandCFragment tab3=new TandCFragment();
                return tab3;
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

                return "Popular Menu";

            case 1:
                return "Other details";


            case 2:
                return "T & C";
        }
        return super.getPageTitle(position);
    }

}
