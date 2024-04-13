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
        mainCtrl.showStartPage();
    }


    /**
     * used for refreshing
     */
    public void setSession() {
        server.setSession();
    }


    /**
     * @param password the password that was submitted
     * @return the boolean of the success of the login attempt
     */
    public boolean connect(String password){
        int code = server.checkCredentials(password);
        if(code == 200) {
            mainCtrl.showAdminOverview();
            return true;
        }
        return false;

    }

    /**
     * gets the main controller
     * @return main controller
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }


}
