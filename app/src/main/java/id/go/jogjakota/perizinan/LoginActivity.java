package id.go.jogjakota.perizinan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import id.go.jogjakota.perizinan.data.Session;
import id.go.jogjakota.perizinan.data.UserDB;
import id.go.jogjakota.perizinan.domain.User;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private static final int REQUEST_REGISTER = 3691;
    @InjectView(R.id.username)
    EditText mUsername;
    @InjectView(R.id.password)
    EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        getToolbar().setBackgroundColor(0x00ffFFff);

        if (Session.get().isLoggedIn())
            proceedToMain();
    }

    @OnClick(R.id.login)
    public void doLogin() {
        final String username = mUsername.getText().toString().trim();
        final String password = mPassword.getText().toString();
        Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    User user = UserDB.get().getUser(username, password);
                    subscriber.onNext(user);
                    subscriber.onCompleted();
                } catch (UserDB.UserNotFoundException e) {
                    subscriber.onError(e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {
                        proceedToMain();
                    }

                    @Override
                    public void onError(Throwable e) {
                        toast(e);
                    }

                    @Override
                    public void onNext(User user) {
                        Log.d(Tag.D, "Logging in as " + user.getFullName());
                        Session.get().login(user);
                    }
                });
    }

    @OnClick(R.id.register)
    public void register() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, REQUEST_REGISTER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_REGISTER && resultCode == RESULT_OK) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");

            mUsername.setText(username);
            mPassword.setText(password);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void proceedToMain() {
        toast("Berhasil login.");
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }
}
