package info.androidhive.navigationdrawer.utils;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;

import com.roger.catloadinglibrary.CatLoadingView;

import backend.gppmon.handler.RTMProvider;
import backend.gppmon.handler.RTMProviderImpl;
import backend.gppmon.test.SimpleMessageHandler;
import info.androidhive.navigationdrawer.activity.BaseActivity;

/**
 * Created by Rohit Pawar on 05-04-2018.
 */

public class ApplicationController extends MultiDexApplication {
    private RTMProvider provider;
    private SimpleMessageHandler handler;
    private static  CatLoadingView mView;
    @Override
    public void onCreate() {
        super.onCreate();

        mView = new CatLoadingView();
    }

  public RTMProvider getRTMProviderInstance() {
        try {
            provider = RTMProviderImpl.getInstance();
            provider.setLoginURL(AppConstants.LOGIN_URL);
            provider.setMessageHandler(handler);
        } catch (Exception e) {
            return null;
        }
      return provider;
    }

    public void LoginToRTM(){


    }

    public void showProgressDialog( AppCompatActivity baseActivity){
        mView.setCancelable(true);
        mView.show(baseActivity.getSupportFragmentManager(), "");
    }

    public void dismissProgressDialog(){
        try {
            if (mView != null) {
                mView.dismiss();
            }
        }catch (Exception e){e.printStackTrace();}
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
