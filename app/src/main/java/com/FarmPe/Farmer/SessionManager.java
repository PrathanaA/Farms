package com.FarmPe.Farmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.FarmPe.Farmer.Activity.LandingPageActivity;
import com.FarmPe.Farmer.Activity.LoginActivity;


/**
 * Created by vinod on 11/12/17.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences pref1;
     SharedPreferences.Editor loginPrefsEditor;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
   // private static final String PREF_NAME = "ParentCraft";
    private static final String PREF_NAME = "Xohri";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_PASSWORD = "pass";
    public static final String KEY_NAME = "name";
    public static final String KEY_LANGUAGE_DETAILS= "langdetails";
    public static final String KEY_DEFAULT_ADDRESS= "default_address";
    public static final String KEY_DEFAULT_ADDRESS_ID= "default_address_id";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_LNG_SELECTED = "lng";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_LANGUAGE_NAME = "language_name";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref1 = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        loginPrefsEditor=pref1.edit();
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String pass, String phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_PASSWORD, pass);

        // Storing email in pref
        editor.putString(KEY_PHONE, phone);


        // commit changes
        editor.commit();
    }



/////

    /**
     * Create remenberme session
     * */
    public void saveUserId(String userId){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_USER_ID, userId);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void saveLanguage(String language){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_LANGUAGE, language);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void save_name(String name , String phone){

        System.out.println("111111111111aaaa"+name);
        // Storing name in pref
        loginPrefsEditor.putString(KEY_NAME, name);
        loginPrefsEditor.putString(KEY_PHONE, phone);



        // commit changes
        loginPrefsEditor.commit();
    }

    public void saveLangDetails(String details){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_LANGUAGE_DETAILS, details);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void saveDefaultAddress(String address,String address_id){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_DEFAULT_ADDRESS, address);
        loginPrefsEditor.putString(KEY_DEFAULT_ADDRESS_ID, address_id);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void save_Language(int code){

        // Storing name in pref
        loginPrefsEditor.putInt(KEY_LNG_SELECTED, code);


        // commit changes
        loginPrefsEditor.commit();
    }



    public void saveLanguage_name(String code){

        // Storing name in pref
        loginPrefsEditor.putString(KEY_LANGUAGE_NAME, code);


        // commit changes
        loginPrefsEditor.commit();
    }


    public int getLanguage(){


        //if language code not stored will return 1(English)
        int s1=pref.getInt(KEY_LNG_SELECTED, 1); // getting String

        return s1;
    }



    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LandingPageActivity.class);
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
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);// for clearing
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);


    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String checkRememberMe(String phone, String password){
        String rememberMe="uncheck";

        String userId=pref1.getString("phone1", null); // getting String
        String userpassword=pref1.getString("pass1", null); // getting String
        System.out.println("llllllllllllllllllll"+userId);
        System.out.println("llllllllllllllllllll"+userpassword);
        if (phone.equals(userId)){
            rememberMe=userpassword;
        }


        return rememberMe;
    }

    public String getRegId(String s2){

        String s1=pref.getString(s2, ""); // getting String

        return s1;
    }


}
