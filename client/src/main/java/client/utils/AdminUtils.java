package client.utils;


import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.RequestBody;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.http.RequestEntity.post;

public class AdminUtils extends Util{

    private Entity<String> s;

    public boolean checkCredentials(String userName, String password) {
        Response response = clientBuilder
                .target(serverAddress)
                .path("api/servers/login")
                .request(APPLICATION_JSON)
                .post(Entity.json("{\"email\": \"" + userName + "\", \"password\": \"" + password + "\"}"));

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return true;
        }

        return false;
    }
}
