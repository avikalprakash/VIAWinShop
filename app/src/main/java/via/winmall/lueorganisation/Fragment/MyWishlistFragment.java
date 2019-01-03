package via.winmall.lueorganisation.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import via.winmall.lueorganisation.R;


public class MyWishlistFragment extends Fragment {


    public MyWishlistFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        return view;
    }
}
