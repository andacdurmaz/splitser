package client.utils;

import jakarta.annotation.Resource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;

import java.net.InetAddress;

public class Util {

    /**
     * @return the current server address
     */
    public static String getAddress() {
        return address;
    }

    /**
     * @param address  setter for the server address parameter
     */
    public static void setAddress(String address) {
        Util.address = address;
    }

    protected static String serverAddress = "http://localhost:8080/";

    protected static String address = "localhost:8080/";
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
     * Setter method for the current ClientBuilder Object.
     * @param clientBuilder The new clientBuilder to change to
     */
    public void setClientBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder.build();
    }

    /**
     * Sets the address of the server
     * @param newAddress Server address
     * @return boolean whether connection was successful
     */
    public boolean setServerAddress(String newAddress){
        if(!isValidServerAddress(newAddress)){
            return false;
        }

        Util.address = newAddress;
        Util.serverAddress = "http://" + address + "/";
        return true;
    }

    /**
     * Checks if a server address is valid
     * @param newAddress Address to check
     * @return Boolean whether the address is valid
     */
    private static boolean isValidServerAddress(String newAddress) {
        try {
            InetAddress targetAddress;

            if(newAddress.equals("localhost:8080")){
                targetAddress = InetAddress.getLocalHost();
            }else{
                targetAddress = InetAddress.getByName("http://" + address + "/");
            }

            return targetAddress.isReachable(1500);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the address of the server
     * @return Returns the server address
     */
    public static String getServerAddress() {
        return Util.serverAddress;
    }

}
