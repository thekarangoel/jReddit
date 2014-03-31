package com.github.jreddit.utils.restclient;

public interface BasicResponse {

    /**
     * @return <code>int</code> the Http response code
     */
    public int getStatusCode();

    /**
     * @return <code>String</code> the response body
     */
    public String getResponseBody();
}
