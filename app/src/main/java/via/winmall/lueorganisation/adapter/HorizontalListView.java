package via.winmall.lueorganisation.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;


public class HorizontalListView extends HorizontalScrollView {

    private String TAG = "HorizontalListView";
    private ViewGroup mContainer = null;
    private Context mContext = null;
    private ListAdapter mAdapter = null;
    private OnListItemClickListener mListItemClickListener = null;


    public interface OnListItemClickListener {

        void onClick(View v, int position);
    }


    public void registerListItemClickListener(OnListItemClickListener listItemClickListener) {
        mListItemClickListener = listItemClickListener;
    }

    /**
     * Custom OnClickListener for list item
     */
    public class CustomOnClickListener implements OnClickListener {
        private int mPosition;
        public CustomOnClickListener(int position) {
            mPosition = position;
        }
        @Override
        public void onClick(View v) {
            if (mListItemClickListener != null) {
                mListItemClickListener.onClick(v, mPosition);
            }
        }
    }




    public HorizontalListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mContext = context;

        // Init child view
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContainer = container;
        addView(mContainer);

        // Remove horizontal scrollbar
        setHorizontalScrollBarEnabled(false);
    }


    public void setAdapter(ListAdapter adapter) {
        mAdapter = adapter;

        if (getChildCount() == 0 || adapter == null)
            return;

        mContainer.removeAllViews();

        for (int i = 0; i < adapter.getCount(); i++) {
            View v = adapter.getView(i, null, mContainer);
            if (v != null) {
                v.setOnClickListener(new CustomOnClickListener(i));
                mContainer.addView(v);
            }
        }
    }
}
