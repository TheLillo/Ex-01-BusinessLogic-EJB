package it.distributedsystems.profilers;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Profiler {

    @AroundInvoke
    public Object profiler(InvocationContext ctx){
        Object obj = null;
        Context ic = null;
        ConnectionFactory cf = null;

        try{
            ic = new InitialContext();
            cf = (ConnectionFactory) ic.lookup("/ConnectionFactory");
        }catch (NamingException e){
            e.printStackTrace();
        }

        try(
                Connection connection = cf.createConnection();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                )
        {
            Queue queue = (Queue) ic.lookup("queue/LoggingQueue");
            MessageProducer publisher = session.createProducer(queue);
            connection.start();
            TextMessage message = session.createTextMessage(ctx.getMethod().getName());
            publisher.send(message);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        }

        try{
            obj = ctx.proceed();
        }catch (Exception e){
            e.printStackTrace();
        }

        return obj;
    }

}
