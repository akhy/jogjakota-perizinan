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
import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.InjectView;
import id.go.jogjakota.perizinan.data.PermitDB;
import id.go.jogjakota.perizinan.data.Session;
import id.go.jogjakota.perizinan.domain.Permit;
import id.go.jogjakota.perizinan.domain.User;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PermitListActivity extends BaseActivity implements StatusView.OnRefreshListener {

    @InjectView(R.id.recycler)
    RecyclerView mRecycler;
    @InjectView(R.id.status)
    StatusView mStatus;

    PermitAdapter mAdapter = new PermitAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_list);
        ButterKnife.inject(this);

        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setAdapter(mAdapter);

        mStatus.setOnRefreshListener(this);
        onRefresh(mStatus);
    }

    @Override
    public void onRefresh(StatusView statusView) {
        mStatus.showProgress();
        Observable.create(new Observable.OnSubscribe<List<Permit>>() {
            @Override
            public void call(Subscriber<? super List<Permit>> subscriber) {
                User user = Session.get().getUser();
                subscriber.onNext(PermitDB.get().getPermitsBy(user));
                subscriber.onCompleted();
            }
        })
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Permit>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mStatus.showMessage("Gagal memuat data", e);
                    }

                    @Override
                    public void onNext(List<Permit> permits) {
                        if (permits == null || permits.size() == 0)
                            mStatus.showMessage("Tidak ada pengajuan surat izin ditemukan.");
                        else
                            mStatus.hide();

                        mAdapter.setPermitList(permits);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void openPermitDetail(Permit permit) {
        Intent intent = new Intent(this, PermitDetailActivity.class);
        intent.putExtra("permit", permit);
        startActivity(intent);
    }

    private class PermitAdapter extends RecyclerView.Adapter<TwoLineViewHolder> {

        private List<Permit> permitList = new ArrayList<>();

        @Override
        public TwoLineViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_two_lines, parent, false);
            return new TwoLineViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TwoLineViewHolder holder, int i) {
            final Permit permit = permitList.get(i);

            holder.mText1.setText(permit.getType().getName());
            holder.mText2.setText(permit.getRequestTimeString());
            holder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openPermitDetail(permit);
                }
            });
        }

        @Override
        public int getItemCount() {
            return permitList.size();
        }

        public void setPermitList(List<Permit> permitList) {
            this.permitList = permitList;
        }
    }
}
