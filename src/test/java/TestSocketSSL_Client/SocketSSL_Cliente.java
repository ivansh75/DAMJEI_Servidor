/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestSocketSSL_Client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 *
 * @author Ivimar
 */
public class SocketSSL_Cliente {

    private SSLSocket client;

    public SocketSSL_Cliente() {
    }

    public Socket connect(String host, int port) throws KeyStoreException, FileNotFoundException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException, NoSuchAlgorithmException {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream("src/main/certs/client/clientKey.jks"),
                "clientpass".toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, "clientpass".toCharArray());

        KeyStore trustedStore = KeyStore.getInstance("JKS");
        trustedStore.load(new FileInputStream(
                "src/main/certs/client/clientTrustedCerts.jks"), "clientpass"
                .toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(trustedStore);

        SSLContext sc = SSLContext.getInstance("TLS");
        TrustManager[] trustManagers = tmf.getTrustManagers();
        KeyManager[] keyManagers = kmf.getKeyManagers();
        sc.init(keyManagers, trustManagers, null);

        SSLSocketFactory ssf = sc.getSocketFactory();
        client = (SSLSocket) ssf.createSocket(host, port);
        client.startHandshake();
        return client;

    }
}
