package info.androidhive.navigationdrawer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import info.androidhive.navigationdrawer.R;
import info.androidhive.navigationdrawer.event.LoginEvent;
import info.androidhive.navigationdrawer.service.BroadcastService;
import info.androidhive.navigationdrawer.utils.ApplicationController;

public class LoginActivity extends BaseActivity {
    public static final String MY_PREFS_NAME = "eagle_eye_prefs";
    private static final String TAG = "LoginActivity";
    CatLoadingView mView;


    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.btn_login)
    Button _loginButton;
    private ApplicationController applicationController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerEventBus();
        //Hide title bar
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mView = new CatLoadingView();
        mView.setCancelable(false);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        applicationController = (ApplicationController) getApplicationContext();
    }

    private void registerEventBus() {
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void login() {
        _emailText.setText("rohit@GPP");
        _passwordText.setText("rohit");
        if (validate()) {
            storeLoggedInUser();
            applicationController.showProgressDialog(this);
            startService(new Intent(this, BroadcastService.class));
        } else {
            Toast.makeText(LoginActivity.this, "Invalid Credentials, try again ! ", Toast.LENGTH_LONG).show();
        }
    }
    //Store user details in Pref
    private void storeLoggedInUser() {
        //Store login state & userame in Pref
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("USER", _emailText.getText().toString());
        editor.putString("PASS", _passwordText.getText().toString());
        editor.putBoolean("LOGGED_IN", true);
        editor.apply();
    }
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public boolean validate() {
        boolean valid = true;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        if (email.isEmpty()) {
            _emailText.setError("enter a valid user name");
            valid = false;
        } else {
            _emailText.setError(null);
        }
        if (password.isEmpty() || password.length() < 3 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    public void onEventMainThread (LoginEvent event) {
        //  view.setText(view.getText() + "\n" + event.getData());
        if (mView.isVisible())
            mView.dismiss();
        Log.i("EVENT", "event captured");
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("USER", _emailText.getText().toString());
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterEventBus();
    }

    private void unRegisterEventBus() {
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
