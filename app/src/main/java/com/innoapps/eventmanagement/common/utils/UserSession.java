package com.innoapps.eventmanagement.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by Braintech on 7/22/2015.
 */
public class UserSession {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "user_pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "is_user_logged_in";

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_MOBILE = "mobile";//
    public static final String KEY_USER_TOKENID = "token_id";
    public static final String KEY_USER_IMAGE = "user_image";
    public static final String KEY_REFRESH = "refresh";
    public static final String KEY_PROREFRESH = "prorefresh";


    public UserSession(Context _context) {
        this.context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createUserSession(String user_id, String user_name, String email, String mobile, int token_id, String userImage) {

        editor.putString(KEY_USER_ID, user_id);
        editor.putString(KEY_USER_NAME, user_name);
        editor.putString(KEY_USER_EMAIL, email);
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USER_MOBILE, mobile);
        editor.putInt(KEY_USER_TOKENID, token_id);
        editor.putString(KEY_USER_IMAGE, userImage);

        // commit changes
        editor.commit();
    }

    public void createUserID(String user_id) {

        editor.putString(KEY_USER_ID, user_id);
        editor.putBoolean(IS_LOGIN, true);
        // commit changes
        editor.commit();
    }

    public void refresh(boolean value) {
        editor.putBoolean(KEY_REFRESH, value);
        editor.commit();
    }

    public void profileRefresh(boolean value) {
        editor.putBoolean(KEY_PROREFRESH, value);
        editor.commit();
    }

    /* Get KEY_USER_ID */
    public String getUserID() {
        return pref.getString(KEY_USER_ID, "");
    }

    /* Get KEY_USER_NAME */
    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "");
    }

    /* Get KEY_USER_EMAIL */
    public String getUserEmail() {
        return pref.getString(KEY_USER_EMAIL, "");
    }

    /* Get KEY_USER_MOBILE */
    public String getUserMobile() {
        return pref.getString(KEY_USER_MOBILE, "");
    }

    /* Get KEY_USER_TOKENID */
    public int getUserTokenid() {
        return pref.getInt(KEY_USER_TOKENID, 0);
    }


    /* Get KEY_USER_TOKENID */
    public String getUserImage() {
        return pref.getString(KEY_USER_IMAGE, "");
    }

    /**
     * Clear session details
     */

    public void clearUserSession() { // Clearing all data from Shared
        editor.clear();
        editor.commit();
    }


    // Get Login State
    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isRefresh() {
        return pref.getBoolean(KEY_REFRESH, false);
    }

    public boolean isProRefresh() {
        return pref.getBoolean(KEY_PROREFRESH, false);
    }

}
