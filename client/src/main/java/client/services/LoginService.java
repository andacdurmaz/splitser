package client.services;

import client.scenes.MainCtrl;
import client.utils.AdminUtils;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    private AdminUtils adminUtils;
    @Inject
    public LoginService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.adminUtils = new AdminUtils();
    }

    public void login(){
        mainCtrl.login();
    }

    public void returnToMenu(){
        mainCtrl.showOverview();
    }


    public void setSession() {
        server.setSession();
    }

    public void connect(String username, String password){
        if(adminUtils.checkCredentials(username, password))

    }


}
