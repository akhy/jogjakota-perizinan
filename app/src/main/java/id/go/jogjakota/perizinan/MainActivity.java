package id.go.jogjakota.perizinan;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import id.go.jogjakota.perizinan.data.Session;
import id.go.jogjakota.perizinan.domain.User;

public class MainActivity extends BaseActivity {

    @InjectView(R.id.title)
    TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        getToolbar().setBackgroundColor(0x00ffFFff);

        User user = Session.get().getUser();
        if (user != null)
            mTitle.setText(getString(R.string.welcome, user.getFullName()));
    }

    @OnClick(R.id.open_type_list)
    public void toType() {
        startActivity(new Intent(this, PermitTypeListActivity.class));
    }

    @OnClick(R.id.open_status_list)
    public void toStatus() {
        startActivity(new Intent(this, PermitListActivity.class));
    }
}
