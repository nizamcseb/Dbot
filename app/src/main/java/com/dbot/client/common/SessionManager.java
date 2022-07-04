package com.dbot.client.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.dbot.client.LandingActivity;
import com.dbot.client.R;
import com.dbot.client.login.LoginActivity;
import com.dbot.client.login.SignupActivity;
import com.dbot.client.main.MainActivity;


public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "dbotPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogedInClient(Intent intent, String clientId, String fullName, String phone, String emailId, String companyName, String companyPhone, String companyEmail, String city, String freelancer) {
        createLoginSession(true);
        setClientId(clientId);
        setClientFullName(fullName);
        setClientPhone(phone);
        setClientEmailId(emailId);
        setClientCompanyName(companyName);
        setClientCompanyPhone(companyPhone);
        setClientCompanyEmail(companyEmail);
        setCity(city);
        setFreeLancer(freelancer);

        _context.startActivity(intent);

    }

    public void updateClient(String clientId, String fullName, String phone, String emailId, String companyName, String companyPhone, String companyEmail, String city, String freelancer) {
        createLoginSession(true);
        setClientId(clientId);
        setClientFullName(fullName);
        setClientPhone(phone);
        setClientEmailId(emailId);
        setClientCompanyName(companyName);
        setClientCompanyPhone(companyPhone);
        setClientCompanyEmail(companyEmail);
        setCity(city);
        setFreeLancer(freelancer);
    }


    /**
     * Create login session
     *
     * @param b
     */
    public void createLoginSession(boolean b) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, b);

        editor.commit();
    }

    public void setClientId(String clientId) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_ID), clientId);

        editor.commit();
    }

    public String getClientId() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_ID), null);
    }

    public void setClientFullName(String fullName) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_FULLNAME), fullName);

        editor.commit();
    }

    public String getClientFullName() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_FULLNAME), null);
    }

    public void setClientPhone(String phone) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_PHONE), phone);

        editor.commit();
    }

    public String getClientPhone() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_PHONE), null);
    }

    public void setClientEmailId(String emailId) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_EMAIL), emailId);

        editor.commit();
    }

    public String getClientEmailId() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_EMAIL), null);
    }

    public void setClientCompanyName(String companyName) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_COMPANY_NAME), companyName);

        editor.commit();
    }

    public String getClientCompanyName() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_COMPANY_NAME), null);
    }

    public void setClientCompanyPhone(String companyPhone) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_COMPANY_PHONE), companyPhone);

        editor.commit();
    }

    public String getClientCompanyPhone() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_COMPANY_PHONE), null);
    }

    public void setClientCompanyEmail(String companyEmail) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_COMPANY_EMAIL), companyEmail);

        editor.commit();
    }

    public String getClientCompanyEmail() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_COMPANY_EMAIL), null);
    }

    public void setCity(String city) {

        editor.putString(_context.getString(R.string.TAG_CLIENT_CITY), city);

        editor.commit();
    }

    public String getCity() {

        return pref.getString(_context.getString(R.string.TAG_CLIENT_CITY), null);
    }

    private void setFreeLancer(String freelancer) {
        editor.putString(_context.getString(R.string.TAG_FREELANCER), freelancer);

        editor.commit();
    }

    public int getFreeLancer() {

        return Integer.parseInt(pref.getString(_context.getString(R.string.TAG_FREELANCER), "0"));
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     *
     * @param token
     */
    public void checkLogin(String token) {
        // Check login status
        if (this.isLoggedIn()) {
            System.out.println("Login success");
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        } else {
            System.out.println("Login failure");

            Intent i = new Intent(_context, LoginActivity.class);
            i.putExtra(_context.getString(R.string.TAG_NOTIFICATION_TOKEN), token);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LandingActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


}
