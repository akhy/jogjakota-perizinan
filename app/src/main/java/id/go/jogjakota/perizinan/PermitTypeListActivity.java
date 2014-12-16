package id.go.jogjakota.perizinan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import id.go.jogjakota.perizinan.data.PermitTypes;
import id.go.jogjakota.perizinan.domain.PermitType;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PermitTypeListActivity extends BaseActivity {
    @InjectView(R.id.recycler)
    RecyclerView mRecycler;

    private PermitTypeAdapter mAdapter = new PermitTypeAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_type_list);
        ButterKnife.inject(this);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);

        Observable.create(new Observable.OnSubscribe<List<PermitType>>() {
            @Override
            public void call(Subscriber<? super List<PermitType>> subscriber) {
                subscriber.onNext(PermitTypes.getAll());
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PermitType>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PermitType> permits) {
                        mAdapter.setPermitTypeList(permits);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void openFormForPermitType(int position) {
        PermitType type = mAdapter.getItem(position);
        Intent intent = new Intent(this, PermitFormActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    private class PermitTypeAdapter extends RecyclerView.Adapter<TwoLineViewHolder> {

        private List<PermitType> permitTypeList = new ArrayList<>();

        @Override
        public TwoLineViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_two_lines, parent, false);
            return new TwoLineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TwoLineViewHolder twoLineViewHolder, final int i) {
            PermitType type = permitTypeList.get(i);

            twoLineViewHolder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFormForPermitType(i);
                }
            });
            twoLineViewHolder.mText1.setText(type.getName());
            twoLineViewHolder.mText2.setText(type.getMaxDaysString());
        }

        @Override
        public int getItemCount() {
            return permitTypeList.size();
        }

        public PermitType getItem(int pos) {
            return permitTypeList.get(pos);
        }

        public void setPermitTypeList(List<PermitType> permitTypeList) {
            this.permitTypeList = permitTypeList;
        }
    }

}
