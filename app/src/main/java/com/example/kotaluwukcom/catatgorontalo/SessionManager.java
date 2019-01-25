package com.example.kotaluwukcom.catatgorontalo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN     = "IS_LOGIN";
    public static final String NRP        = "NRP";
    public static final String NAMA       = "NAMA";
    public static final String PANGKAT    = "PANGKAT";
    public static final String JABATAN    = "JABATAN";
    public static final String TELPON     = "TELPON";
    public static final String FOTOPOL    = "FOTOPOL";

 public SessionManager(Context context){
    this.context = context;
    sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    editor = sharedPreferences.edit();
 }

 public void createSession (String nrp, String nama, String pangkat, String jabatan, String telpon, String foto_pol){

     editor.putBoolean(LOGIN, true);
     editor.putString(NRP, nrp);
     editor.putString(NAMA, nama);
     editor.putString(PANGKAT, pangkat);
     editor.putString(JABATAN, jabatan);
     editor.putString(TELPON, telpon);
     editor.putString(FOTOPOL, foto_pol);

     editor.apply();

 }

 public boolean isLoggin(){ return sharedPreferences.getBoolean(LOGIN, false);}

 public void checkLogin(){
     if (!this.isLoggin()){
         Intent i = new Intent(context, Masuk.class);
         context.startActivity(i);
         ((MainActivity)context).finish();
     }
 }

 public HashMap<String, String> getUserDetail(){

     HashMap<String, String> user = new HashMap<>();
     user.put(NRP, sharedPreferences.getString(NRP, null));
     user.put(NAMA, sharedPreferences.getString(NAMA, null));
     user.put(PANGKAT, sharedPreferences.getString(PANGKAT, null));
     user.put(JABATAN, sharedPreferences.getString(JABATAN, null));
     user.put(TELPON, sharedPreferences.getString(TELPON, null));
     user.put(FOTOPOL, sharedPreferences.getString(FOTOPOL, null));

     return user;

 }

 public void logout(){
     editor.clear();
     editor.commit();
     Intent i = new Intent(context, Masuk.class);
     context.startActivity(i);
 }

}
