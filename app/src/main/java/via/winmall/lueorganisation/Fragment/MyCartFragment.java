package via.winmall.lueorganisation.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import via.winmall.lueorganisation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {
    ImageView minus, add;
    TextView quantity, priceTextView;
    int qty=1;
    int price=123;

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        minus = view.findViewById(R.id.minus);
        add = view.findViewById(R.id.add);
        quantity = view.findViewById(R.id.quantity);
        priceTextView = view.findViewById(R.id.price);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (qty<2){
                    qty=1;
                }else{
                    qty--;
                    quantity.setText(String.valueOf(qty));
                    int totalPrice = qty*price;
                    priceTextView.setText("MYR "+String.valueOf(totalPrice)+".00");
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty++;
                quantity.setText(String.valueOf(qty));
                int totalPrice = qty*price;
                priceTextView.setText("MYR "+String.valueOf(totalPrice)+".00");
            }
        });
        return view;
    }

}
