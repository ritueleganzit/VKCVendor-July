package com.eleganzit.vkcvendor.api.uploadMultupleImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by PC10 on 12/22/2015.
 */
public class JSONParser {


    public static JSONObject doGet(HashMap<String, String> param, String url,Context c) throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        JSONObject result = null;
        String response;
        Set keys = param.keySet();

        int count = 0;
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            count++;
            String key = (String) i.next();
            String value = (String) param.get(key);
            if (count == param.size()) {
                Log.e("Key", key + "");
                Log.e("Value", value + "");
                url += key + "=" + URLEncoder.encode(value);

            } else {
                Log.e("Key", key + "");
                Log.e("Value", value + "");

                url += key + "=" + URLEncoder.encode(value) + "&";
            }

        }
        Log.e("URL", url);
        Request request;
        try {
            request = new Request.Builder()
                    .url(url)
                    .build();

        } catch (IllegalArgumentException e) {
            JSONObject jsonObject = new JSONObject();

            return jsonObject;
        }


        Response responseClient = null;
        try {


            responseClient = getUnsafeOkHttpClient().newCall(request).execute();
            response = responseClient.body().string();

            result = new JSONObject(response);
            Log.e("response", response + "==============");
        } catch (Exception e) {
            JSONObject jsonObject = new JSONObject();

            return jsonObject;
        }

        return result;

    }

    public static OkHttpClient createClient(Context context) {

        OkHttpClient client = null;

        CertificateFactory cf = null;
        InputStream cert = null;
        Certificate ca = null;
        SSLContext sslContext = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
         //   cert = context.getResources().openRawResource(R.raw.phramblecom); // Place your 'my_cert.crt' file in `res/raw`

            ca = cf.generateCertificate(cert);
            cert.close();

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();

        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException | KeyManagementException e) {
            e.printStackTrace();
            Log.e("In Cathch0","True");
        }

        return client;
    }





    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static JSONObject doPost(HashMap<String, String> data, String url) {

        Log.e("URL", url);
        try {
            RequestBody requestBody;
            MultipartBody.Builder mBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            if (data != null) {
                for (String key : data.keySet()) {
                    String value = data.get(key);
                    Log.e("Key Values", key + "=" + value);
                    mBuilder.addFormDataPart(key, value);
                }
            } else {
                mBuilder.addFormDataPart("temp", "temp");
            }

            requestBody = mBuilder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = getUnsafeOkHttpClient().newCall(request).execute();
            String responseBody = response.body().string();
            url = "";
            Log.e("User", responseBody);
            return new JSONObject(responseBody);

        } catch (UnknownHostException | UnsupportedEncodingException e) {

            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", e.getLocalizedMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", e.getLocalizedMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }


    public static JSONObject doPostRequestWithFile(HashMap<String, String> data,
                                                   String url,
                                                   ArrayList<String> imageList,
                                                   String fileParamName) {



        try {
            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

            Log.e("Method", "=======");
            RequestBody requestBody;
            MultipartBody.Builder mBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);

            for (String key : data.keySet()) {
                String value = data.get(key);
                Log.e("Key Values", key + "-----------------" + value);

                mBuilder.addFormDataPart(key, value);

            }
            for (int i = 0; i < imageList.size(); i++) {
                File f = new File(imageList.get(i));
               // f = saveBitmapToFile(f);

               // Log.e("File Name 322", f.getName() + "===========");
                if (f.exists()) {
                    Log.e("File Exits", "===================");
                    mBuilder.addFormDataPart(fileParamName, f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
                }
            }

            requestBody = mBuilder.build();


            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization","")
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = getUnsafeOkHttpClient().newCall(request).execute();


            String result = response.body().string();

            Log.e("Response", result + "");
            return new JSONObject(result);

        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("exceptionssssss", "Error: " + e.getLocalizedMessage());
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", e.getLocalizedMessage());
                return jsonObject;

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            Log.e("exceptionssssss", "Other Error: " + e.getMessage());
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("status", "false");
                jsonObject.put("message", e.getLocalizedMessage());
                return jsonObject;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }


    public static  File saveBitmapToFile(File file){
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE=75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }


}