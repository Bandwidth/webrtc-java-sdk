package com.bandwidth.webrtc.authorization;

import com.bandwidth.webrtc.exceptions.HttpException;
import com.bandwidth.webrtc.exceptions.UnAuthorizedException;
import com.bandwidth.webrtc.helpers.Helper;
import okhttp3.*;

import java.io.IOException;
import java.util.Base64;

public class WebRtcAuthorizer {
    private static OkHttpClient httpClient = new OkHttpClient();

    public static OauthToken getClientCredentials(WebRtcCredentials creds) throws IOException, HttpException {

        String url = "https://id.bandwidth.com/api/v1/oauth2/token";

        String mediaType = "application/x-www-form-urlencoded";

        String auth = creds.getUsername() + ":" + creds.getPassword();

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
            throw new UnAuthorizedException("Not Authorized getting token");
        } else if (!response.isSuccessful()) {
            throw new HttpException("Request to get toke unsuccessful, status code: " + response.code());
        }

        return Helper.deserialize(response.body().string(), OauthToken.class);
    }
}
