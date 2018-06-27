package info.androidhive.navigationdrawer.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class AppPreferences {

   /* private static final String KEY_PREFS_IS_EULA_ACCEPTED = "is_eula_accepted";
    private static final String KEY_PREFS_IS_EULA_ENABLED = "is_eula_enabled";
    private static final String KEY_PREFS_IS_USER_REGISTERED = "is_user_registered";
    private static final String KEY_PREFS_IS_USER_VERIFIED = "is_user_verified";
    private static final String KEY_PIN_ATTEMPTS_EXCEEDED = "pin_attempts_exceeded";
    private static final String KEY_PREFS_SHOULD_PROMPT_FINGERPRINT = "should_prompt_fingerprint";
    private static final String KEY_PREFS_IS_ENABLING_FINGERPRINT = "is_enabling_fingerprint";
    private static final String KEY_PREFS_FINGERPRINT_AUTH_ENABLED = "fingerprint_auth_enabled";
    private static final String KEY_PREFS_REMEMBER_USER_ID = "remember_user_id";
    private static final String KEY_MAP_UID = "map_uid";
    private static final String KEY_MOBILE_TOKEN_CODE = "mobile_token_code";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_DEVICE_TYPE = "device_type";
    private static final String KEY_CERTIFICATE_EXPIRY_DATE = "cert_expiry_date";
    private static final String KEY_CERTIFICATE_RENEWAL_MARGIN = "cert_renewal_margin";
    private static final String KEY_CERTIFICATE_ENABLE = "cert_enable";
    private static final String KEY_USE_TOUCHID = "usertouchid";
    private static final String KEY_MFA_USER_DISABLE = "user_disable";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PRIVATE = "private";
    private static final String KEY_PUBLIC = "public";
    private static final String KEY_LICENSE_URL = "license_url";
    private static final String KEY_CONTACTUS_URL = "contactus_url";
    private static final String KEY_HELP_URL = "help_url";
    private static final String KEY_IS_FIRST_TIME_LOGIN = "is_first_login";


    private Context context;
    private EncryptionHelper encryptionHelper;

    public AppPreferences(Context context, EncryptionHelper encryptionHelper) {
        this.context = context;
        this.encryptionHelper = encryptionHelper;
    }


    public AppPreferences(Context context) {
        this.context = context;

    }

    public void resetPreferences() {
        getSharedPreferences().edit().clear().apply();
    }

    public boolean isEulaAccepted() {
        return getSharedPreferences().getBoolean(KEY_PREFS_IS_EULA_ACCEPTED, false);
    }

    public void setEulaAccepted(boolean accepted) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_IS_EULA_ACCEPTED, accepted).apply();
    }

    public boolean isEulaEnable() {
        return getSharedPreferences().getBoolean(KEY_PREFS_IS_EULA_ENABLED, false);
    }

    public void setEulaEnable(boolean enabled) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_IS_EULA_ENABLED, enabled).apply();
    }

    public boolean isUserRegistered() {
        return getSharedPreferences().getBoolean(KEY_PREFS_IS_USER_REGISTERED, false);
    }

    public void setUserRegistered(boolean registered) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_IS_USER_REGISTERED, registered).apply();
    }

    public boolean isUserVerified() {
        return getSharedPreferences().getBoolean(KEY_PREFS_IS_USER_VERIFIED, false);
    }

    public void setUserVerified(boolean verified) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_IS_USER_VERIFIED, verified).apply();
    }

    public boolean isPinAttemptsExceeded() {
        return getSharedPreferences().getBoolean(KEY_PIN_ATTEMPTS_EXCEEDED, false);
    }

    public void setPinAttemptsExceeded(boolean exceeded) {
        getSharedPreferences().edit().putBoolean(KEY_PIN_ATTEMPTS_EXCEEDED, exceeded).apply();
    }

    public boolean shouldPromptFingerprintSetup() {
        return getSharedPreferences().getBoolean(KEY_PREFS_SHOULD_PROMPT_FINGERPRINT, true);
    }

    public void setShouldPromptFingerprintSetup(boolean shouldPrompt) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_SHOULD_PROMPT_FINGERPRINT, shouldPrompt).apply();
    }

    public boolean isEnablingFingerprint() {
        return getSharedPreferences().getBoolean(KEY_PREFS_IS_ENABLING_FINGERPRINT, false);
    }

    public void setIsEnablingFingerprint(boolean isEnabling) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_IS_ENABLING_FINGERPRINT, isEnabling).apply();
    }

    public boolean isFingerprintAuthEnabled() {
        return getSharedPreferences().getBoolean(KEY_PREFS_FINGERPRINT_AUTH_ENABLED, false);
    }

    public void setFingerprintAuthEnabled(boolean enabled) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_FINGERPRINT_AUTH_ENABLED, enabled).apply();
    }

    public boolean shouldRememberUserId() {
        return getSharedPreferences().getBoolean(KEY_PREFS_REMEMBER_USER_ID, false);
    }

    public void setRememberUserId(boolean remember) {
        getSharedPreferences().edit().putBoolean(KEY_PREFS_REMEMBER_USER_ID, remember).apply();
    }

    public String getDeviceType() {
        return getSharedPreferences().getString(KEY_DEVICE_TYPE, "");
    }

    public void setDeviceType(String deviceType) {
        getSharedPreferences().edit().putString(KEY_DEVICE_TYPE, deviceType).apply();
    }

    // begin sensitive fields that are encrypted/decrypted upon read and write

    public String getUserId() {
        return readSensitiveStringFromSharedPreferences(KEY_USER_ID);
    }

    public void setUserId(String userId) {
        writeSensitiveStringToSharedPreferences(KEY_USER_ID, userId);
    }

    // begin sensitive fields that are encrypted/decrypted upon read and write

    public String getPassword() {
        return readSensitiveStringFromSharedPreferences(KEY_PASSWORD);
    }

    public void setPassword(String password) {
        writeSensitiveStringToSharedPreferences(KEY_PASSWORD, password);
    }

    public String getMapUid() {
        return readSensitiveStringFromSharedPreferences(KEY_MAP_UID);
    }

    public void setMapUid(String mapUid) {
        writeSensitiveStringToSharedPreferences(KEY_MAP_UID, mapUid);
    }

    public String getMobileTokenCode() {
        return readSensitiveStringFromSharedPreferences(KEY_MOBILE_TOKEN_CODE);
    }

    public void setMobileTokenCode(String mobileTokenCode) {
        writeSensitiveStringToSharedPreferences(KEY_MOBILE_TOKEN_CODE, mobileTokenCode);
    }


    public long getCertificateExpiryDate() {
        return getSharedPreferences().getLong(KEY_CERTIFICATE_EXPIRY_DATE, 0L);
    }

    public void setCertificateExpiryDate(long expiryDate) {
        getSharedPreferences().edit().putLong(KEY_CERTIFICATE_EXPIRY_DATE, expiryDate).commit();
    }

    public int getCertificateRenewalMargin() {
        return getSharedPreferences().getInt(KEY_CERTIFICATE_RENEWAL_MARGIN, 0);
    }

    public void setCertificateRenewalMargin(int margin) {
        getSharedPreferences().edit().putInt(KEY_CERTIFICATE_RENEWAL_MARGIN, margin).commit();
    }


    public boolean getCertificateEnable() {
        return getSharedPreferences().getBoolean(KEY_CERTIFICATE_ENABLE, false);
    }

    public void setCertificateEnable(boolean enable) {
        getSharedPreferences().edit().putBoolean(KEY_CERTIFICATE_ENABLE, enable).commit();
    }

    public boolean getUseTouchId() {
        return getSharedPreferences().getBoolean(KEY_USE_TOUCHID, false);
    }

    public void setUseTouchId(boolean enable) {
        getSharedPreferences().edit().putBoolean(KEY_USE_TOUCHID, enable).commit();
    }

    public boolean getMFAUserDisable() {
        return getSharedPreferences().getBoolean(KEY_MFA_USER_DISABLE, false);
    }

    public void setMFAUserDisable(boolean disable) {
        getSharedPreferences().edit().putBoolean(KEY_MFA_USER_DISABLE, disable).commit();
    }

    public void setLicenseUrl(String url) {
        getSharedPreferences().edit().putString(KEY_LICENSE_URL, url).apply();
    }

    public String getLicenseUrl() {
            return getSharedPreferences().getString(KEY_LICENSE_URL, "");
    }

    public void setContactUsUrl(String url) {
        getSharedPreferences().edit().putString(KEY_CONTACTUS_URL, url).apply();
    }

    public String getContactUsUrl() {
        return getSharedPreferences().getString(KEY_CONTACTUS_URL, "");

    }
    public void setHelpUrl(String url) {
        getSharedPreferences().edit().putString(KEY_HELP_URL, url).apply();
    }

    public String getHelpUrl() {
        return getSharedPreferences().getString(KEY_HELP_URL, "");

    }

    // end sensitive fields that are encrypted/decrypted upon read and write

    private String readSensitiveStringFromSharedPreferences(String keyOfStringToDecrypt) {
        String encryptedString = getSharedPreferences().getString(keyOfStringToDecrypt, "");
        return encryptionHelper.decryptString(encryptedString);
    }

    private void writeSensitiveStringToSharedPreferences(String key, String stringToEncrypt) {
        String encryptedString = encryptionHelper.encryptString(stringToEncrypt);
        getSharedPreferences().edit().putString(key, encryptedString).apply();
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setKeyPrivate(String privateKey){
        getSharedPreferences().edit().putString(KEY_PRIVATE, privateKey).commit();
    }

    public String getKeyPrivate(){
       return getSharedPreferences().getString(KEY_PRIVATE, null);
    }

    public void setKeyPublic(String publicKey){
        getSharedPreferences().edit().putString(KEY_PUBLIC, publicKey).commit();
    }

    public String getKeyPublic(){
        return getSharedPreferences().getString(KEY_PUBLIC, null);
    }

    public boolean isFirstTimeLogin() {
        return getSharedPreferences().getBoolean(KEY_IS_FIRST_TIME_LOGIN, false);
    }

    public void setFirstTimeLogin(boolean flag) {
        getSharedPreferences().edit().putBoolean(KEY_IS_FIRST_TIME_LOGIN, flag).apply();
    }*/
}
