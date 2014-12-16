package id.go.jogjakota.perizinan;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import id.go.jogjakota.perizinan.data.PermitDB;
import id.go.jogjakota.perizinan.data.Session;
import id.go.jogjakota.perizinan.domain.Permit;
import id.go.jogjakota.perizinan.domain.PermitType;

import static butterknife.ButterKnife.findById;

public class PermitFormActivity extends BaseActivity {

    private PermitType mType;

    @InjectView(R.id.form_area)
    LinearLayout mFormArea;

    private List<EditText> inputs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permit_form);
        ButterKnife.inject(this);
        ButterKnife.inject(this);

        mType = getIntent().getParcelableExtra("type");
        setTitle(mType.getName());
        setUpForm(mType);
    }

    public void setUpForm(PermitType type) {
        for (Map.Entry<String, String> field : type.getFieldNames().entrySet()) {
            View view = getLayoutInflater().inflate(R.layout.item_field_input, null);
            TextView label = findById(view, R.id.text1);
            EditText input = findById(view, R.id.input);
            label.setText(field.getValue());
            input.setTag(field.getKey());
            mFormArea.addView(view);
            inputs.add(input);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                submit();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void submit() {
        for (EditText input : inputs) {
            if (TextUtils.isEmpty(input.getText())) {
                toast("Harap lengkapi formulir terlebih dahulu");
                return;
            }
        }

        Permit permit = new Permit();
        permit.setUser(Session.get().getUser());
        permit.setType(mType);
        for (EditText input : inputs) {
            permit.addFieldValue(input.getTag().toString(), input.getText().toString());
        }

        PermitDB.get().addPermit(permit);

        Toast.makeText(this,
                String.format("Terima kasih. " +
                        "Permohonan anda akan diproses maksimal %d hari kerja.", mType.getMaxDays()),
                Toast.LENGTH_LONG)
                .show();

        finish();
    }
}
