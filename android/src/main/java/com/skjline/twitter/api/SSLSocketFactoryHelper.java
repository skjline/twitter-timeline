package com.skjline.twitter.api;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLSocketFactoryHelper {
    public static final X509TrustManager trust;

    static {
        trust = new TwitterTrustManager();
    }

    public static SSLSocketFactory getSocketFactory() {
        SSLSocketFactory sslSocketFactory;
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trust}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            sslSocketFactory = null;
            e.printStackTrace();
        }

        return sslSocketFactory;
    }

    /**
     * For a development, allow all credential
     */
    public static class TwitterTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
