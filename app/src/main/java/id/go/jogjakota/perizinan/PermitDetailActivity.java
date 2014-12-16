package id.go.jogjakota.perizinan;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import id.go.jogjakota.perizinan.domain.Permit;

public class PermitDetailActivity extends BaseActivity {

    @InjectView(R.id.icon)
    ImageView mIcon;
    @InjectView(R.id.title)
    TextView mTitle;
    @InjectView(R.id.status)
    TextView mStatus;
    @InjectView(R.id.date)
    TextView mDate;
    @InjectView(R.id.recycler)
    RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_detail);
        ButterKnife.inject(this);

        Permit permit = getIntent().getParcelableExtra("permit");
        mTitle.setText(permit.getType().getName());
        mStatus.setText(permit.getStatusString());
        mDate.setText(String.format("Tanggal pengajuan: %s", permit.getRequestTimeString()));
        mStatus.setTextColor(getResources().getColor(permit.isApproved()
                ? R.color.green : R.color.red));
        mIcon.setImageResource(permit.isApproved() ? R.drawable.yes : R.drawable.no);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(new PermitFieldAdapter(permit));
    }

    private class PermitFieldAdapter extends RecyclerView.Adapter<TwoLineViewHolder> {

        private Permit mPermit;
        private List<String> mKeys;

        private PermitFieldAdapter(Permit mPermit) {
            this.mPermit = mPermit;
            this.mKeys = new ArrayList<>(mPermit.getFieldValues().keySet());
        }

        @Override
        public TwoLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_two_lines, parent, false);
            return new TwoLineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TwoLineViewHolder holder, int position) {
            String key = mKeys.get(position);

            String name = mPermit.getType().getFieldNames().get(key);
            String value = mPermit.getFieldValues().get(key);
            holder.mText1.setText(name);
            holder.mText2.setText(TextUtils.isEmpty(value) ? "-" : value);
        }

        @Override
        public int getItemCount() {
            return mKeys.size();
        }
    }
}
