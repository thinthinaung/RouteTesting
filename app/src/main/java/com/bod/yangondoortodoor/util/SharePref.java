package com.bod.yangondoortodoor.util;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ezdir on 8/28/14.
 */
public class SharePref {

    private static final String PREF_FIRST_TIME_CHECK = "pref_first_time_check";
    public static String PREF_USER = "pref_user";
    public static String PREF_GCM = "pref_gcm";
    public static String PREF_GCMFLAG = "pref_gcm_flag";
    public static String PREF_USERID = "pref_userid";
    private static SharePref pref;
    protected SharedPreferences mSharedPreferences;
    protected SharedPreferences.Editor mEditor;
    protected Context mContext;

    public SharePref(Context context)
    {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
        this.mContext = context;
    }

    public static synchronized SharePref getInstance(Context mContext) {
        if (pref == null) {
            pref = new SharePref(mContext);
        }
        return pref;
    }

    public void saveUserSession(String UserSession) {
        mEditor.putString(PREF_USER, UserSession).commit();
    }

    public String getUserSession() {
        return mSharedPreferences.getString(PREF_USER, null);
    }

    public boolean isFirstTime() {
        return mSharedPreferences.getBoolean(PREF_FIRST_TIME_CHECK, true);
    }

    public void noLongerFirstTime()
    {
        mEditor.putBoolean(PREF_FIRST_TIME_CHECK, false);
    }

    public void saveUSERID(String UserID) {
        mEditor.putString(PREF_USERID, UserID).commit();
    }

    public String getUSERID()
    {
        return mSharedPreferences.getString(PREF_USERID, null);
    }

    public void saveGCM_REGID(String UserSession) {
        mEditor.putString(PREF_GCM, UserSession).commit();
    }

    public String getGCM_REGID()
    {
        return mSharedPreferences.getString(PREF_GCM, null);
    }

    public void saveGCMFlag(String Flag)
    {
        mEditor.putString(PREF_GCMFLAG,Flag).commit();
    }

    public String getGCMFlag()
    {
       return  mSharedPreferences.getString(PREF_GCMFLAG,null);
    }

}
