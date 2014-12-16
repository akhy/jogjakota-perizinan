package id.go.jogjakota.perizinan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends BaseActivity {

    private static boolean SPLASH_SHOWN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (SPLASH_SHOWN)
            proceed();
        else
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    proceed();
                }
            }, 3000);
    }

    private void proceed() {
        SPLASH_SHOWN = true;

        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
