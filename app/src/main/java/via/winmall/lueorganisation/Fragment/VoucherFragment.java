package via.winmall.lueorganisation.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import via.winmall.lueorganisation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VoucherFragment extends Fragment {


    public VoucherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voucher, container, false);
    }

}
