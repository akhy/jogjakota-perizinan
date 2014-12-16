package id.go.jogjakota.perizinan;

import android.app.Activity;

public class Transition {

    public static void goForward(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
    }

    public static void goBack(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    public static void goSlideUp(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_bottom,
                R.anim.slide_out_top);
    }

    public static void goSlideDown(Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in_top,
                R.anim.slide_out_bottom);
    }

}
