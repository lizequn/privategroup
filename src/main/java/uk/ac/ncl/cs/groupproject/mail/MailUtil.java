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
    public static void sendMail(String address,String Information){
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
                    InternetAddress.parse("ttpttd@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Test,"
                    + "\n\n You have received a file!");


            Transport.send(message);


            System.out.println("Done");


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
