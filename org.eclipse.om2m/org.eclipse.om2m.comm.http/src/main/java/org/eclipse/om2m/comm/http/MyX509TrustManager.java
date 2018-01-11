package org.eclipse.om2m.comm.http;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MyX509TrustManager implements X509TrustManager {
	
	public static final int width = 300;
	public static final int height = 400;
	JFrame jframe = new JFrame("Connection Target Certificate");
	Button verify_button = new Button("Verify");
	Button reject_button = new Button("Reject");
	
    /*
     * (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[],
     * java.lang.String)
     */
	//確認Client是否信任
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    	System.out.println("11  not work");
    }
    /*
     * (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[],
     * java.lang.String)
     */
    // 確認server是否信任
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
    	Container cp=jframe.getContentPane();
    	JPanel panel=new JPanel();
    	jframe.setSize(width, height);
    	jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    	
    	
    	
    	//jframe.add(verify_button);
    	verify_button.addActionListener(new Verify_ActionHandler());
    	reject_button.addActionListener(new Reject_ActionHandler());
    	panel.add(verify_button);
    	panel.add(reject_button);
    	cp.add(panel,BorderLayout.SOUTH);
    	//jframe.add(reject_button);

    	
    	/*Reference : http://alphax-x.blogspot.tw/2008/11/java.html*/
    	for (X509Certificate cert : arg0) {
    		
        	// Make sure that it hasn't expired.
        	cert.checkValidity();
        	
        	String s_combinCert = "<html>" + "類型 : " + cert.getType() + "<br>"
        						+ "版本 : " + cert.getVersion() + "<br>"
        						+ "標題 : " + cert.getSubjectDN().getName() + "<br>"
        						+ "有效日期 : " + cert.getNotBefore().toString() + "<br>"
                				+ "截止日期 : " + cert.getNotAfter().toString() + "<br>"
                        		+ "序列號 : " + cert.getSerialNumber().toString(16) + "<br>"
                        		+ "發行者 : " + cert.getIssuerDN().getName() + "<br>"
                        		+ "簽名演算法 : " + cert.getSigAlgName() + "<br>"
                        		+ "公鑰演算法 : " + cert.getPublicKey().getAlgorithm() + "<br>"
                        		+ "數位簽章 : " + cert.getSignature() + "<br>"
                        		//+ "序列號 : " + cert. + "<br>"
        						+ "</html>";
        	String s_cert = cert.toString();
        	JLabel cert_label=new JLabel(s_combinCert);
        	//cert_label.setSize(width, 0);
        	
        	JScrollPane jScrollPane = new JScrollPane(cert_label);
        	//jframe.add(jScrollPane);
        	
        	cp.add(jScrollPane);
        	
        	// Verify the certificate's public key chain.
        	//System.out.println(cert);
        }
    	jframe.setVisible(true);
    	
    }
    /*
     * (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    // 接收的發佈者
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
    
    class Verify_ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(jframe,
            "您按了" + e.getActionCommand(),
            "訊息",JOptionPane.INFORMATION_MESSAGE); 
          jframe.dispose();
        }
    }
    
    class Reject_ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          JOptionPane.showMessageDialog(jframe,
            "您按了" + e.getActionCommand(),
            "訊息",JOptionPane.INFORMATION_MESSAGE); 
          jframe.dispose();
        }
    }

}
