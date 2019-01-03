package via.winmall.lueorganisation.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import via.winmall.lueorganisation.R;
import via.winmall.lueorganisation.adapter.PagerInboxTabDetails;
import via.winmall.lueorganisation.adapter.PagerMerchantTabDetails;

public class InboxActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    TextView tvheadertitle;
    ImageView back;
    ViewPager viewpager;
    TabLayout tablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        tvheadertitle=(TextView)findViewById(R.id.tvheadertitle);
        tvheadertitle.setText("Inbox");
        back = (ImageView)findViewById(R.id.ivheaderleft);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        viewpager = (ViewPager) findViewById(R.id.pagermerchants);
        tablayout = (TabLayout) findViewById(R.id.tablayoutmerchants);

        TabLayout.Tab firsttab = tablayout.newTab();
        tablayout.addTab(firsttab, 0, true);
        tablayout.addTab(tablayout.newTab(), 1, false);
        tablayout.setOnTabSelectedListener(this);
        PagerInboxTabDetails adapter=new PagerInboxTabDetails(getSupportFragmentManager(),tablayout.getTabCount());
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        for(int i=0; i < tablayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tablayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 50, 0);
            tab.requestLayout();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
