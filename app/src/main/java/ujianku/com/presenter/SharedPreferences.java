package ujianku.com.presenter;


import android.content.Context;

public class SharedPreferences {
    public static final String SP_UJIANKU = "ujianku";

    public static final String SP_USERNAME = "spUsername";
    public static final String SP_PASSWORD = "spPassword";
    public static final String SP_ID = "spId";

    public static final String SP_SUDAH_LOGIN = "spSudahLogin";

    android.content.SharedPreferences sp;
    android.content.SharedPreferences.Editor spEditor;

    public SharedPreferences(Context context) {
        sp = context.getSharedPreferences(SP_UJIANKU, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
        spEditor.apply();
    }

    public void saveSPInt(String keySP, int value) {
        spEditor.putInt(keySP, value);
        spEditor.commit();
        spEditor.apply();
    }

    public void saveSPBoolean(String keySP, boolean value) {
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
        spEditor.apply();
    }

    public void remove(String keySP) {
        spEditor.remove(keySP);
        spEditor.commit();
    }

    public void removeAll() {
        spEditor.clear();
        spEditor.commit();
    }

    public String getString(String keySP) {
        return sp.getString(keySP, "");
    }

    public Boolean getBoolean(String keySP) {
        return sp.getBoolean(keySP, false);
    }

    public Boolean getSPSudahLogin() {
        return sp.getBoolean(SP_SUDAH_LOGIN, false);
    }
}
