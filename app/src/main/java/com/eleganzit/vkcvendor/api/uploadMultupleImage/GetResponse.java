package com.eleganzit.vkcvendor.api.uploadMultupleImage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by PC10 on 8/3/2016.
 */
public interface GetResponse {
    void onSuccesResult(JSONObject result) throws JSONException;
    void onFailureResult(JSONObject result) throws JSONException;
    void onException(String message) throws JSONException;

}
