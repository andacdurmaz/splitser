package client.utils;

import jakarta.annotation.Resource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;

public class Util {
    @Resource
    protected Client clientBuilder;

    /**
     * Constructor for BaseUtil and all of ServerUtils
     */
    public Util() {
        this.clientBuilder = ClientBuilder.newClient(new ClientConfig());
    }

    /**
     * Getter method for the current ClientReturner object
     * @return a current ClientReturner Object
     */
    public Client getClient() {
        return clientBuilder;
    }
}
