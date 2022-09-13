package com.uzcodemd.onewinadmin.api;

import static com.uzcodemd.onewinadmin.Constants.CONTENT_TYPE;
import static com.uzcodemd.onewinadmin.Constants.SERVER_KEY;

import com.uzcodemd.onewinadmin.model.PushNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Authorization: key="+SERVER_KEY,"Content-Type:"+CONTENT_TYPE})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification notification);
}
