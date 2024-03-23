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

    /**
     * @param server constructor
     * @param mainCtrl DI components
     */
    @Inject
    public LoginService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.adminUtils = new AdminUtils();
    }

    /**
     * runs the login method
     */
    public void login(){
        mainCtrl.login();
    }

    /**
     * method to exit the login page
     * and return to main menu
     */
    public void returnToMenu(){
        mainCtrl.showOverview();
    }


    /**
     * used for refreshing
     */
    public void setSession() {
        server.setSession();
    }

    /**
     * @param username provided user name of admin
     * @param password the password, which the server will be randomly generating
     */
    public void connect(String username, String password){
        if(adminUtils.checkCredentials(username, password))
            mainCtrl.showAdminOverview();
    }


}
