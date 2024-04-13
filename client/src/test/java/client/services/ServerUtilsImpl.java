package client.services;

import client.utils.ServerUtils;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ServerUtilsImpl extends ServerUtils {
    private List<String> calls;

    /**
     *
     */
    public ServerUtilsImpl() {
        calls = new ArrayList<>();
    }

    /**
     *
     */
    public void setSession(){
        calls.add("setSession");
    }

    /**
     * @param targetUrl
     * @return
     */
    private StompSession connect(String targetUrl){
        calls.add("connect");
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

    /**
     * @param destination    server address we want to subscribe to
     * @param packetType     the type of object we will be receiving
     * @param packetConsumer a class to store ant iterate over objects
     *                       received from the server
     * @param <T>
     */
    public <T> void registerForSocketMessages(String destination,
                                              Class<T> packetType, Consumer<T> packetConsumer){
        calls.add("regSocket");
    }

    /**
     * @param destinationAddress server address to which we want
     *                           to send an updated object
     * @param o                  Object to be sent
     * @return test string
     */
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
