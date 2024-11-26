package com.example.duan1_customer;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import android.telephony.PhoneStateListener;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CallDetectionManager implements Application.ActivityLifecycleCallbacks,
        CallDetectionPhoneStateListener.PhoneCallStateUpdate {
    private TelephonyManager telephonyManager;
    private CallDetectionPhoneStateListener callDetectionPhoneStateListener;
    private Context context;
    private boolean isFirstIdleIgnored = true;

    public CallDetectionManager(Context context) {
        this.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }
    public void startListener() {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            callDetectionPhoneStateListener = new CallDetectionPhoneStateListener(this);
            telephonyManager.listen(callDetectionPhoneStateListener,
                    PhoneStateListener.LISTEN_CALL_STATE);
    }
    public void stopListener() {
        Log.d("CallDetectionManager", "stopping");
        if (telephonyManager != null && callDetectionPhoneStateListener != null) {
            telephonyManager.listen(callDetectionPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        telephonyManager = null;
        callDetectionPhoneStateListener = null;
        Log.d("CallDetectionManager", "stopListener");
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    @Override
    public void phoneCallStateUpdated(int state, String incomingNumber) {
        switch (state) {
            //Hangup
            case TelephonyManager.CALL_STATE_IDLE:
                if (!isFirstIdleIgnored) {
                    Log.d("CallDetectionManager", "IDLE " + isFirstIdleIgnored);
                    isFirstIdleIgnored = true;
                    this.stopListener();
                    ((MainActivity)context).reopenApp();
                    break;
                }else {
                    Log.d("CallDetectionManager", "IDLE " + isFirstIdleIgnored);
                    isFirstIdleIgnored = false;
                }

                break;
            //Outgoing
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("CallDetectionManager", "OFFHOOK");
                break;
            //Incoming
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("CallDetectionManager", "RINGING");
                break;
        }
    }


}
