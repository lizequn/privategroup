package uk.ac.ncl.cs.groupproject.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Auther: Li Zequn
 * Date: 24/03/14
 */
public class MailUtil {
    public static void sendMailtoReceiver(String address,String from,String name,String link){
        final String username = "ttpttd@gmail.com";
        final String password = "groupproject1";


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        try {


            Message message = new MimeMessage(session);
            //message.setFrom(new InternetAddress("ttpttd@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject("Receive File From TTP");
            message.setText("you should receive a file from TTP from: " + from +" file name :"+name+" id to receive file(use by command line) "+link);


            Transport.send(message);


            System.out.println("Done");


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendMailToSender(String address,String uid,String link){
        final String username = "ttpttd@gmail.com";
        final String password = "groupproject1";


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });


        try {


            Message message = new MimeMessage(session);
            //message.setFrom(new InternetAddress("ttpttd@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(address));
            message.setSubject("Receive File From TTP");
            message.setText("you should receive a receipt from TTP with uid:" + uid +" id to receive file(use by command line) "+link);


            Transport.send(message);


            System.out.println("Done");


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
