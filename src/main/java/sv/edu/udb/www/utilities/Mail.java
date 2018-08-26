/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.utilities;

import java.util.Properties;
import javafx.util.Pair;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author pc
 */
public class Mail {

    private final Properties properties = new Properties();
    private final Session session;
    private Pair<String, String> filePath;
    private String Addressee;
    private String message;
    private String affair;

    public Mail() {
        properties.put("mail.smtp.host", "smtp.mailtrap.io");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 25);
        properties.put("mail.smtp.user", "f3b304e742ba85");
        properties.put("mail.smtp.password", "6ca405416af632");
        properties.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(properties);
    }

    public boolean sendEmail() {
        try {
            MimeMultipart message = new MimeMultipart();
            BodyPart body = new MimeBodyPart();

            body.setContent(this.message, "text/html");
            message.addBodyPart(body);

            if (this.filePath != null) {
                BodyPart file = new MimeBodyPart();
                file.setDataHandler(new DataHandler(new FileDataSource(this.getFilePath())));
                file.setFileName(this.getFileName());
                message.addBodyPart(file);
            }
            
            MimeMessage gmail = new MimeMessage(this.session);
            
            gmail.setFrom(new InternetAddress((String) properties.get("mail.smtp.user")));
            gmail.addRecipient(Message.RecipientType.TO, new InternetAddress(this.Addressee));
            gmail.setSubject(this.affair);
            gmail.setContent(message);

            Transport t = session.getTransport("smtp");
            t.connect((String) properties.get("mail.smtp.user"), (String) properties.get("mail.smtp.password"));
            
            t.sendMessage(gmail, gmail.getAllRecipients());
            t.close();
            
            return true;
        } catch (MessagingException error) {
            error.printStackTrace();
            return false;
        }
    }

    public String getFileName() {
        return filePath.getKey();
    }
    
    public String getFilePath() {
        return filePath.getValue();
    }

    public void setFilePath(String fileName, String filePath) {
        this.filePath = new Pair<>(fileName, filePath);
    }

    public String getAddressee() {
        return Addressee;
    }

    public void setAddressee(String Addressee) {
        this.Addressee = Addressee;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }
    
    
}
