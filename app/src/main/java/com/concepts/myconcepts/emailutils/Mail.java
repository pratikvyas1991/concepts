package com.concepts.myconcepts.emailutils;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by tasol on 7/5/18.
 */

public class Mail extends javax.mail.Authenticator {
    private String user;
    private String pass;
    private String[] to;
    private String from;
    private String port;
    private String sport;
    private String host;
    private String subject;
    private String body;
    private boolean auth;
    private boolean debuggable;
    private Multipart multipart;

    public Mail(String un, String pass) {
        this.user = un;
        this.pass = pass;
        host = "smtp.gmail.com"; // default smtp server
        port = "465"; // default smtp port
        sport = "465"; // default socketfactory port
        user = ""; // username
        pass = ""; // password
        from = ""; // email sent from
        subject = ""; // email subject
        body = ""; // email body
        debuggable = false; // debug mode on or off - default off
        auth = true; // smtp authentication - default on
        multipart = new MimeMultipart();
// There is something wrong with MailCap, javamail can not find a handler for the multipart
// /mixed part, so this bit needs to be added.
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
    }

    private Properties _setProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        if (debuggable) {
            props.put("mail.debug", "true");
        }
        if (auth) {
            props.put("mail.smtp.auth", "true");
        }
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.enable", true);
        return props;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, pass);
    }

    public boolean send() throws Exception {
        Properties props = _setProperties();

        if (!user.equals("") && !pass.equals("") && to.length > 0 && !from.equals("") &&
                !subject.equals("") && !body.equals("")) {

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });

            SMTPAuthenticator authentication = new SMTPAuthenticator(user,pass);

            javax.mail.Message msg = new MimeMessage(Session
                    .getDefaultInstance(props, authentication));

            msg.setFrom(new InternetAddress(from));


            InternetAddress[] addressTo = new InternetAddress[to.length];

            for (int i = 0; i < to.length; i++) {
                addressTo[i] = new InternetAddress(to[i]);
            }
            msg.setRecipients(MimeMessage.RecipientType.TO, addressTo);

            msg.setSubject(subject);
            msg.setSentDate(new Date());

// setup message body
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);

// Put parts in message
            msg.setContent(multipart);

// send email
            String protocol = "smtp";
            props.put("mail." + protocol + ".auth", "true");
            Transport t = session.getTransport(protocol);
            try {
                t.connect("smtp.gmail.com", user, pass);
                t.sendMessage(msg, msg.getAllRecipients());
            } finally {
                t.close();
            }

            return true;
        } else {
            return false;
        }
    }
}
