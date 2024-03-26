package client.utils;


import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class AdminUtils extends Util{

    private Entity<String> s;

    /**
     * @param userName provided user name of admin
     * @param password the password, which the server will be randomly generating
     * @return whether login was successful or not
     */
    public boolean checkCredentials(String userName, String password) {
        var response = clientBuilder
                .target(serverAddress)
                .path("api/servers/login")
                .request(APPLICATION_JSON)
                .post(Entity.json(password ));

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        }

        return false;
    }
}
