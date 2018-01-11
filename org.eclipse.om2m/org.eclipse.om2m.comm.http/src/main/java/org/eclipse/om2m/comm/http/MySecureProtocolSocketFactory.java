package org.eclipse.om2m.comm.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManager;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

public class MySecureProtocolSocketFactory implements SecureProtocolSocketFactory {
    private SSLContext sslContext = null;
    public static final String KEYSTORE_DIR = System.getProperty("org.eclipse.equinox.http.jetty.ssl.keystore","NSCL");
    public static final String SCL_TYPE = System.getProperty("org.eclipse.om2m.sclType","NSCL");
    public static final String CERT_PASSWORD = System.getProperty("org.eclipse.equinox.http.jetty.ssl.password","123456");
    
    /**
     * Constructor for MySecureProtocolSocketFactory.
     * @throws InterruptedException 
     */
    public MySecureProtocolSocketFactory(){
    }
    /**
     * @return
     */
    private static SSLContext createEasySSLContext() {
        try {

            KeyStore ks = KeyStore.getInstance("JKS");    //PKCS12
            /*File file_buf = new File(KEYSTORE_DIR);
            FileInputStream fis = new FileInputStream(file_buf.getParent()+"\\"+SCL_TYPE.toLowerCase()+".crt");
            System.out.println(file_buf.getParent()+"\\"+SCL_TYPE.toLowerCase()+".crt");
            */
            File file_buf = new File(KEYSTORE_DIR);
            FileInputStream fis = new FileInputStream(file_buf.getAbsolutePath());
            ks.load(fis, CERT_PASSWORD.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");   // X509
            kmf.init(ks, CERT_PASSWORD.toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers(); 
            
            
            //System.out.println(ks.getCertificate(SCL_TYPE.toLowerCase()));
            
            
            
            //context.init(null, new TrustManager[] {new MyX509TrustManager()}, null);
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(keyManagers, new TrustManager[] {new MyX509TrustManager()}, null);
            /*
            // 對象的協議名稱  
            System.out.println("安全套接字使用的協議: " + context.getProtocol());  
            // 獲取SSLContext實例相關的SSLEngine  
            SSLEngine e = context.createSSLEngine();  
            System.out  
                    .println("支持的協議: " + Arrays.asList(e.getSupportedProtocols()));  
            System.out.println("啟用的協議: " + Arrays.asList(e.getEnabledProtocols()));  
            System.out.println("支持的加密套件: "  
                    + Arrays.asList(e.getSupportedCipherSuites()));  
            System.out.println("啟用的加密套件: "  
                    + Arrays.asList(e.getEnabledCipherSuites()));  
            */

            return context;
        } catch (Exception e) {
            throw new HttpClientError(e.toString());
        }
    }
    /**
     * @return
     */
    private SSLContext getSSLContext() {
        if (this.sslContext == null) {
            this.sslContext = createEasySSLContext();
        }
        
        return this.sslContext;
    }
    /*
     * (non-Javadoc)
     * @see
     * org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String,
     * int, java.net.InetAddress, int)
     */
    public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException,
        UnknownHostException {	  	
        return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
    }
    /*
     * (non-Javadoc)
     * @see
     * org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String,
     * int, java.net.InetAddress, int, org.apache.commons.httpclient.params.HttpConnectionParams)
     */
    public Socket createSocket(final String host, final int port, final InetAddress localAddress, final int localPort,
            final HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {

        if (params == null) {
            throw new IllegalArgumentException("Parameters may not be null");
        }
        int timeout = params.getConnectionTimeout();
        
        
        if (timeout == 0) {
            return createSocket(host, port, localAddress, localPort);
        } else {
            return ControllerThreadSocketFactory.createSocket(this, host, port, localAddress, localPort, timeout);
        }
        
    }
    /*
     * (non-Javadoc)
     * @see SecureProtocolSocketFactory#createSocket(java.lang.String,int)
     */
    public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(host, port);
    }
    /*
     * (non-Javadoc)
     * @see SecureProtocolSocketFactory#createSocket(java.net.Socket,java.lang.String,int,boolean)
     */
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
        UnknownHostException {
        return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
    }
}