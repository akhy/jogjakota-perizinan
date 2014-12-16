package id.go.jogjakota.perizinan;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by akhyar
 */
public class StatusView extends FrameLayout {
    public static final String DEFAULT_ERROR = "Gagal memuat data";
    public static final String DEFAULT_EMPTY = "No data available";

    @InjectView(R.id.statusMessage)
    TextView mStatusMessage;
    @InjectView(R.id.exceptionMessage)
    TextView mExceptionMessage;
    @InjectView(R.id.messageArea)
    LinearLayout mMessageArea;
    @InjectView(R.id.progress)
    ProgressBar mProgress;

    private OnRefreshListener mOnRefreshListener;

    public StatusView(Context context) {
        super(context);
        init(context);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_status, this, true);
        ButterKnife.inject(this, this);

        setClickable(true);
        showMessage(DEFAULT_ERROR);
        hide();
    }

    public String getStatusMessage() {
        return mStatusMessage.getText().toString();
    }

    public void setStatusMessage(String statusMessage) {
        mStatusMessage.setText(statusMessage);
    }

    public String getExceptionMessage() {
        return mExceptionMessage.getText().toString();
    }

    public void setExceptionMessage(String message) {
        mExceptionMessage.setVisibility(TextUtils.isEmpty(message) ? GONE : VISIBLE);
        mExceptionMessage.setText(message);
    }

    @Override
    public boolean performClick() {
        if (mOnRefreshListener != null) {
            mOnRefreshListener.onRefresh(this);
            showProgress();
        }

        return super.performClick();
    }

    public void showMessage(String message) {
        showMessage(message, null);
    }

    public void showMessage(String message, Throwable error) {
        setStatusMessage(message);
        setExceptionMessage(error == null ? null : error.getMessage());
        hideProgress();
        show();
    }

    public void showProgress() {
        mProgress.setVisibility(VISIBLE);
        mMessageArea.setVisibility(GONE);
        show();
    }

    private void hideProgress() {
        mProgress.setVisibility(GONE);
        mMessageArea.setVisibility(VISIBLE);
    }

    private void show() {
        setVisibility(VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public OnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        mOnRefreshListener = onRefreshListener;
    }

    public static interface OnRefreshListener {
        public void onRefresh(StatusView statusView);
    }
}
