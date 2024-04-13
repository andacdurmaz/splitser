package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;


import javax.inject.Inject;

public class UserDebtService {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @return mainctrl instance
     */
    public MainCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * Constructor
     *
     * @param server server connection
     * @param mainCtrl  mainctrl instance
     */
    @Inject
    public UserDebtService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * @return get server instance
     */
    public ServerUtils getServer(){
        return this.server;
    }

    /**
     * starts a new websocket session
     */
    public void setSession(){
        server.setSession();
    }

    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }


}