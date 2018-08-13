/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.utilities;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
                                  
/**
 *
 * @author pc
 */
public class Mail {
    private final Properties properties = new Properties();	
    private String password;
    private Session session;
 
    public Mail() {
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port",587);
        properties.put("mail.smtp.user", "elsalvadorprueba4@gmail.com");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);
    }
 
    public boolean sendEmail(String email, String msg){
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress((String)properties.get("mail.smtp.user")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Bienvenido a la cuponera");
            message.setText(msg);
            Transport t = session.getTransport("smtp");
            t.connect((String)properties.get("mail.smtp.user"), "mekaku12");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
            return true;
        } catch (MessagingException error) {
            error.printStackTrace();
            return false;
        }
    }
}
