package id.go.jogjakota.perizinan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.util.concurrent.TimeUnit;

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

public class RegisterActivity extends BaseActivity {

    @Required(order = 1, message = "Email harus diisi.")
    @Email(order = 2, message = "Email harus valid.")
    @InjectView(R.id.email)
    EditText mEmail;

    @Required(order = 3, message = "Username harus diisi.")
    @InjectView(R.id.username)
    EditText mUsername;

    @Required(order = 4, message = "Nama lengkap harus diisi.")
    @InjectView(R.id.fullname)
    EditText mFullName;

    @Password(order = 5, message = "Password harus diisi.")
    @InjectView(R.id.password)
    EditText mPassword;

    @ConfirmPassword(order = 6, message = "Password harus sama.")
    @InjectView(R.id.password_confirm)
    EditText mPasswordConfirm;

    @InjectView(R.id.register)
    Button mRegister;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        progress = new ProgressDialog(this);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.setMessage("Proses pendaftaran");
    }

    @OnClick(R.id.register)
    public void validate() {
        Validator validator = new Validator(this);
        validator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                register();
            }

            @Override
            public void onValidationFailed(View failedView, Rule<?> failedRule) {
                if (failedView instanceof EditText)
                    ((EditText) failedView).setError(failedRule.getFailureMessage());
            }
        });
        validator.validate();
    }

    private void register() {
        final User user = new User();
        user.setFullName(mFullName.getText().toString());
        user.setUsername(mUsername.getText().toString());
        user.setPassword(mPassword.getText().toString());
        user.setEmail(mEmail.getText().toString());

        progress.show();
        Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try {
                    UserDB.get().addUser(user);
                    subscriber.onNext(user);
                    subscriber.onCompleted();
                } catch (UserDB.UserExistException e) {
                    subscriber.onError(e);
                }
            }
        })
                .delay(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onCompleted() {
                        progress.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progress.dismiss();
                        toast(e);
                    }

                    @Override
                    public void onNext(User user) {
                        Session.get().login(user);
                        toast("Pendaftaran sukses, silakan login dengan username dan password anda.");
                        finishWithData(user);
                    }
                });
    }

    private void finishWithData(User user) {
        Intent data = new Intent();
        data.putExtra("username", user.getUsername());
        data.putExtra("password", user.getPassword());
        setResult(RESULT_OK, data);
        finish();
    }
}
