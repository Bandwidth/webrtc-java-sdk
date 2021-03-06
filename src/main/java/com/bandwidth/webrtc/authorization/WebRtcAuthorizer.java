package com.bandwidth.webrtc.authorization;

import com.bandwidth.webrtc.exceptions.HttpException;
import com.bandwidth.webrtc.exceptions.UnauthorizedException;
import com.bandwidth.webrtc.helpers.Helper;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.Base64;

public class WebRtcAuthorizer {

    private static OkHttpClient httpClient = new OkHttpClient();

    private static String url = "https://id.bandwidth.com/api/v1/oauth2/token";

    public static OauthToken getClientCredentials(WebRtcCredentials credentials) throws IOException, HttpException {

        String mediaType = "application/x-www-form-urlencoded";

        String auth = credentials.getUsername() + ":" + credentials.getPassword();

        String encodedBasicCredential = Base64.getEncoder().encodeToString(auth.getBytes());

        Headers headers = new Headers.Builder()
                .add("Content-Type", mediaType)
                .add("Authorization", "Basic " + encodedBasicCredential)
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.get(mediaType), "grant_type=client_credentials&scope=");

        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();

        Response response = httpClient.newCall(request).execute();

        if (response.code() == 401) {
            throw new UnauthorizedException("Not Authorized getting token");
        } else if (!response.isSuccessful()) {
            throw new HttpException("Request to get token unsuccessful, status code: " + response.code());
        }

        return Helper.deserialize(response.body().string(), OauthToken.class);
    }

    public void setUrl(String url) {
        WebRtcAuthorizer.url = url;
    }

    public static void setHttpClient(OkHttpClient httpClient) {
        WebRtcAuthorizer.httpClient = httpClient;
    }

}
