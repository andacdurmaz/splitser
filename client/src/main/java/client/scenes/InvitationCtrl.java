
package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class InvitationCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private Event event;

    @FXML
    private Label eventTitle;
    @FXML
    private Label eventCode;


    /**
     * Adding javadoc for checkstyle
     *
     * @param server   server
     * @param mainCtrl mainCtrl
     * @param event event
     */
    @Inject
    public InvitationCtrl(ServerUtils server, MainCtrl mainCtrl, Event event) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.event = event;
    }

    public void sendEmail()
    {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.abv.bg");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.transport.protocol", "smtp");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("user", "password");
            }
        });


        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from"));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress("to"));
            message.setSubject("Ping");
            message.setText("Hello, this is example of sending email  ");

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");

        }catch (MessagingException mex) {mex.printStackTrace();}

    }

    /**
     * set the event title label
     * @param event
     */
    public void updateEventTitle(Event event) {
        if (event != null || event.getTitle().length() != 0)
            eventTitle.setText(event.getTitle());
    }

    /**
     * set the event code label
     * @param event
     */
    public void updateEventCode(Event event) {
        if (event != null || event.getTitle().length() != 0)
        {
            String code = Long.toString(event.getEventCode());
            eventCode.setText(code);
        }

    }

    /**
     * set the event title and code
     * @param event
     */
    public void setData(Event event) {
        updateEventTitle(event);
        updateEventCode(event);
    }


    /**
     * set the event
     * @param event
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     *  return back to event info
     */
    public void cancel() {
//        clearFields();
        mainCtrl.showEventInfo(event);
    }


}