package com.brightwheel.core.service;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("httpClientService")
public class HttpClientService {

    Logger logger = LoggerFactory.getLogger(HttpClientService.class);
    /**
     *
     * @param url the POST endpoint
     * @return the HTTP response status code
     *
     * @throws IOException
     */
    public HttpResponse post( String url, Header[] headers, HttpEntity entity) throws IOException {
        logger.trace("Start post method");
        HttpPost httpPost = new HttpPost(url);
        HttpResponse response = null;
        HttpClient httpClient = null;
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClient = httpClientBuilder.build();
            if(headers.length > 0) {
                httpPost.setHeaders(headers);
            }
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.info("Response Status code :"+statusCode);
            logger.info("Response status line Reason :"+response.getStatusLine().getReasonPhrase());
            return response;
        } catch (IOException e){
            logger.error("Exception occured post() ", e);
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }
        logger.trace("Exiting post method");
        return response;
    }

    public static HttpClientService getInstance(){
        return new HttpClientService();
    }
}
