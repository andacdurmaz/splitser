package client.utils;

import jakarta.annotation.Resource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;

import java.net.InetAddress;

public class Util {
    protected static String address;

    protected static String server;
    @Resource
    protected Client clientBuilder;

    /**
     * Constructor for Util and all of ServerUtils
     */
    public Util() {
        this.clientBuilder = ClientBuilder.newClient(new ClientConfig());
    }

    /**
     * Getter method for the current ClientBuilder object
     * @return a current ClientBuilder Object
     */
    public Client getClient() {
        return clientBuilder;
    }


    /**
     * Getter method for the current ClientBuilder object
     *
     * @return a current ClientBuilder Object
     */
    public Client getClientBuilder() {
        return clientBuilder;
    }

    /**
     * Setter method for the current ClientReturn Object. This is here for testing
     * purposes to allow overriding the ClientBuilder class. However, normally the
     * default ClientBuilder is fine and should *not* be changed.
     * @param clientBuilder The new clientBuilder to change to
     */
    public void setClientBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder.build();
    }

    /**
     * Sets the address of the server
     * @param address Server address
     * @return boolean whether connection was successful
     */
    public boolean setServer(String address){
        if(!isValidServerAddress(address)){
            return false;
        }

        Util.address = address;
        Util.server = "http://" + address + "/";
        return true;
    }

    /**
     * Checks if a server address is valid
     * @param address Address to check
     * @return Boolean whether the address is valid
     */
    private static boolean isValidServerAddress(String address) {
        try {
            InetAddress testAddress;

            if(address.equals("localhost:8080")){
                testAddress = InetAddress.getLocalHost();
            }else{
                testAddress = InetAddress.getByName("http://" + address + "/");
            }

            return testAddress.isReachable(1500);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the address of the server
     * @return Returns the server address
     */
    public static String getServer() {
        return Util.server;
    }

}
