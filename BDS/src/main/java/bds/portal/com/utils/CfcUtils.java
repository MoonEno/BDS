package bds.portal.com.utils;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CFC 연동
 *
 * @author ihmoon
 * @created 2018. 02. 01.
 */
public final class CfcUtils {

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CfcUtils.class);

    private static String host = null;

    private static Configuration config = getConfig();

    private static Configuration getConfig() {
        Configuration config = null;
        try {
            config = new PropertiesConfiguration("portal.properties");
            host = config.getString("cfc.host");
        } catch (Exception e) {
        }
        return config;
    }

    private static CloseableHttpClient httpclient = getHttpClient();

    private static CloseableHttpClient getHttpClient() {

        CloseableHttpClient httpclient = null;
        try {
            SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();
            HostnameVerifier allowAllHosts = new NoopHostnameVerifier();
            SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
            httpclient = HttpClients.custom().setSSLSocketFactory(connectionFactory).build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return httpclient;
    }

    private static List<NameValuePair> getNvps(String ifId) {

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("compcd", config.getString("cfc.compcd")));
        nvps.add(new BasicNameValuePair("bizgrpcd", config.getString("cfc.bizgrpcd")));
        nvps.add(new BasicNameValuePair("wrkPlccd", config.getString("cfc.wrkPlccd")));
        nvps.add(new BasicNameValuePair("ip", config.getString("cfc.ip")));
        nvps.add(new BasicNameValuePair("macAddress", config.getString("cfc.macAddress")));
        nvps.add(new BasicNameValuePair("mchCode", config.getString("mchCode")));
        nvps.add(new BasicNameValuePair("ifId", ifId));

        return nvps;
    }

    public static List<Object> getUserList() {

        List<Object> rList = null;

        String ifId = config.getString("cfc.getAllUserInfo");
        try {
            HttpPost httpPost = new HttpPost(host + ifId);
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.addHeader("Accept", "application/json");
            List<NameValuePair> nvps = getNvps(ifId);

            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse response = httpclient.execute(httpPost);

            int status = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                if (200 == status) {

                    Map<String, Object> map = new ObjectMapper().readValue(entity.getContent(),
                                                                           new TypeReference<Map<String, Object>>() {
                    });

                    if ("true".equals(map.get("isResult"))) {
                        rList = (List<Object>)map.get("data");
                    } else {
                        logger.error("errorMsg : {}", map.get("errorMsg"));
                    }
                } else {
                    logger.error("getUserList : {}, {}", status, entity);
                }
            }

            response.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rList;
    }
}
