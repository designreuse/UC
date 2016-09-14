package com.yealink.uc.platform.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import com.google.common.io.ByteStreams;
import com.yealink.uc.platform.rest.exception.RemoteServiceErrorException;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ChNan
 */
public class HttpClient {
    private final Logger logger = LoggerFactory.getLogger(HttpClient.class);
    private static long timeOut = TimeUnit.MINUTES.toMillis(2);
    private long slowRequestThreshold = TimeUnit.SECONDS.toMillis(5);

    private static CloseableHttpClient httpClient;

    private static HttpClient INSTANCE = new HttpClient();

    private HttpClient() {
        httpClient = build();
    }

    public static HttpClient get() {
        return INSTANCE;
    }

    private CloseableHttpClient build() {
        try {
            HttpClientBuilder builder = HttpClients.custom();
            builder.setHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                .setSslcontext(new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build());
            // builder will use PoolingHttpClientConnectionManager by default
            builder.setDefaultSocketConfig(SocketConfig.custom()
                .setSoKeepAlive(true).build());
            builder.setDefaultRequestConfig(RequestConfig.custom()
                .setSocketTimeout((int) timeOut)
                .setConnectTimeout((int) timeOut).build());
            return builder.build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            throw new IllegalStateException(e);
        }
    }

    //todo
    public HttpResponse execute(HttpRequest request) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try (CloseableHttpResponse response = httpClient.execute(request.build())) {
            String charset = responseCharset(response.getEntity());
            String responseContent = new String(ByteStreams.toByteArray(response.getEntity().getContent()), charset);
            return new HttpResponse(responseContent, response.getStatusLine().getStatusCode(), response.getAllHeaders());
        } catch (IOException e) {
            throw new RemoteServiceErrorException("Remote service connection error!");
        } finally {
            stopWatch.stop();
            long elapsedTime = stopWatch.getTime();
            logger.info("execute, elapsedTime={}", stopWatch.getTime());
            if (elapsedTime > slowRequestThreshold) logger.warn("slow http request detected");
        }
    }

    private String responseCharset(HttpEntity responseEntity) {
        Header header = responseEntity.getContentType();
        if (header == null || header.getValue() == null) return "UTF-8";
        ContentType contentType = ContentType.parse(header.getValue());
        Charset charset = contentType.getCharset();
        if (charset == null) return "UTF-8";
        return charset.name();
    }

}
