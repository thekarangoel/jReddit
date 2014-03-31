package com.github.jreddit.utils.restclient;

import com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder;
import com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.util.List;

public class BetterRestClient implements MethodBuilderRestClient {
    private final HttpClient httpClient;
    private final ResponseHandler<BasicResponse> responseHandler;
    private String userAgent = "Omer's Reddit API Java Wrapper";

    public BetterRestClient(HttpClient httpClient, ResponseHandler<BasicResponse> responseHandler) {
        this.httpClient = httpClient;
        this.responseHandler = responseHandler;
    }

    @Override
    public BasicResponse get(HttpGetMethodBuilder getMethodBuilder) throws IOException {
        getMethodBuilder.withUserAgent(userAgent);
        HttpGet request = getMethodBuilder.build();
        return httpClient.execute(request, responseHandler);
    }

    @Override
    public BasicResponse post(HttpPostMethodBuilder postMethodBuilder, List<NameValuePair> params) throws IOException{
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, Consts.UTF_8);

        postMethodBuilder.withUserAgent(userAgent);
        HttpPost request = postMethodBuilder.build();
        request.setEntity(entity);
        return httpClient.execute(request, responseHandler);
    }

    @Override
    public void setUserAgent(String agent) {
        this.userAgent = agent;
    }
}