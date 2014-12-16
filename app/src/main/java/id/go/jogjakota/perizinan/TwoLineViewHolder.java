package id.go.jogjakota.perizinan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TwoLineViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.text1)
    TextView mText1;
    @InjectView(R.id.text2)
    TextView mText2;
    @InjectView(R.id.root)
    ViewGroup mRoot;

    public TwoLineViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }
}
