package com.telenor.connect.id;

import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Headers;
import retrofit.http.POST;

public interface ConnectAPI {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/oauth/token")
    void getAccessTokens(
            @Field("grant_type") String grant_type,
            @Field("code") String code,
            @Field("redirect_uri") String redirect_uri,
            @Field("client_id") String client_id,
            Callback<ConnectTokens> tokens);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/oauth/token")
    void refreshAccessTokens(
            @Field("grant_type") String grant_type,
            @Field("refresh_token") String refresh_token,
            @Field("client_id") String client_id,
            Callback<ConnectTokens> tokens);

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("/oauth/revoke")
    void revokeToken(
            @Field("client_id") String client_id,
            @Field("token") String token,
            ResponseCallback callback);
}