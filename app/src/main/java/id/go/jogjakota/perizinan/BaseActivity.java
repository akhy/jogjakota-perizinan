package id.go.jogjakota.perizinan;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import id.go.jogjakota.perizinan.data.Session;

public class BaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        Toolbar toolbar = ButterKnife.findById(this, R.id.toolbar);
        mToolbar = toolbar;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void toast(Throwable error) {
        if (error == null)
            toast("Unknown error");
        else
            toast(error.getMessage());
    }

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        if (Session.get().isLoggedIn())
            getMenuInflater().inflate(R.menu.logged_in, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Session.get().logout();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            new AlertDialog.Builder(this)
                    .setView(getLayoutInflater().inflate(R.layout.fragment_about, null))
                    .setTitle("Tentang")
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        Transition.goForward(this);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        Transition.goForward(this);
    }

    @Override
    public void finish() {
        super.finish();
        Transition.goBack(this);
    }
}
