package com.eleganzit.vkcvendor.api.uploadMultupleImage;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.HashMap;


public class CallAPiActivity extends AppCompatActivity {
    public Context context;
    public Activity baseActivity;
    AppDialogs appDialogs;


    public CallAPiActivity(Context context) {
        this.context = context;
        baseActivity = (Activity) context;
        appDialogs = new AppDialogs(context);
    }


    public void doRequestCall(final Activity activity, final HashMap<String, String> map, final String url, final GetResponse getApiResult) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                     JSONObject result = null;
                    try {
                        result = JSONParser.doGet(map, url,context);
                    } catch (KeyManagementException e) {
                        e.printStackTrace();
                    } catch (UnrecoverableKeyException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (KeyStoreException e) {
                        e.printStackTrace();
                    }
                    final JSONObject finalResult = result;
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                if (finalResult.getString("status").equalsIgnoreCase("true")) {
                                    getApiResult.onSuccesResult(finalResult);
                                } else {
                                    getApiResult.onFailureResult(finalResult);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                try {
                                    getApiResult.onException(e.getLocalizedMessage());
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                            }


                        }
                    });
                }
            }).start();

    }

   public void doPostWithFiles(final Activity activity, final HashMap<String, String> map,final String url, final ArrayList<String> mFiles, final String fileParamName, final GetResponse getApiResult) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject result = JSONParser.doPostRequestWithFile(map, url, mFiles, fileParamName);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("Result Do Get", result.toString() + "");

                        try {
                            if (result.getString("status").equalsIgnoreCase("1")) {
                                getApiResult.onSuccesResult(result);
                            } else {

                                getApiResult.onFailureResult(result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                getApiResult.onException(e.getLocalizedMessage());
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }


                    }
                });
            }
        }).start();
    }

public void doPost(final Activity activity, final HashMap<String, String> map,final String url, final GetResponse getApiResult) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                final JSONObject result = JSONParser.doPost(map, url);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            if (result.getString("status").equalsIgnoreCase("1")) {
                                getApiResult.onSuccesResult(result);
                            } else {

                                getApiResult.onFailureResult(result);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            try {
                                getApiResult.onException(e.getLocalizedMessage());
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                        }


                    }
                });
            }
        }).start();
    }


}
