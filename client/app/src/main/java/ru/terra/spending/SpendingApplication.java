package ru.terra.spending;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import ru.terra.spending.android.R;

/**
 * Created by terranz on 14.07.15.
 */
@ReportsCrashes(formKey = "",
        formUri = "http://terranz.ath.cx/jbrss/errors/do.error.report/spending",
        httpMethod = HttpSender.Method.POST,
        mode = ReportingInteractionMode.TOAST, resToastText = R.string.error_caught)
public class SpendingApplication extends Application {
    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
    }
}
