package it.distributedsystems.model.ejb;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;


@MessageDriven(name = "LoggingBean", activationConfig = {
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/LoggingQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class LoggingMessageBean implements MessageListener {

    private static final Logger LOGGER = Logger.getLogger(LoggingMessageBean.class.getName());

    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            LOGGER.info("Message received: " + textMessage.getText());
        } catch (JMSException e) {
            System.out.println("Error while trying to consume messages: " + e.getMessage());
        }
    }
}
