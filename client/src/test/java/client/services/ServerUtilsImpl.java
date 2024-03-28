package client.services;

import client.utils.ServerUtils;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.function.Consumer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class ServerUtilsImpl extends ServerUtils {
    public void setSession(){

    }
    private StompSession connect(String targetUrl){
        return new StompSession() {
            @Override
            public String getSessionId() {
                return null;
            }

            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public void setAutoReceipt(boolean enabled) {

            }

            @Override
            public Receiptable send(String destination, Object payload) {
                return null;
            }

            @Override
            public Receiptable send(StompHeaders headers, Object payload) {
                return null;
            }

            @Override
            public Subscription subscribe(String destination, StompFrameHandler handler) {
                return null;
            }

            @Override
            public Subscription subscribe(StompHeaders headers, StompFrameHandler handler) {
                return null;
            }

            @Override
            public Receiptable acknowledge(String messageId, boolean consumed) {
                return null;
            }

            @Override
            public Receiptable acknowledge(StompHeaders headers, boolean consumed) {
                return null;
            }

            @Override
            public void disconnect() {

            }

            @Override
            public void disconnect(StompHeaders headers) {

            }
        };
    }

    public <T> void registerForSocketMessages(String destination,
                                              Class<T> packetType, Consumer<T> packetConsumer){

    }

    public String send(String destinationAddress, Object o) {
        return destinationAddress + o;
    }

    /**
     * @param password the password that was submitted
     * @return the boolean of the success of the login attempt
     */
    public int checkCredentials(String password) {
        try {
            if (Long.valueOf(password) == 777)
                return 200;
            return 401;
        }catch (NumberFormatException e){

            return 400;
        }

    }

}
