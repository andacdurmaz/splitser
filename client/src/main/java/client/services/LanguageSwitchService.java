package client.services;

import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class LanguageSwitchService {
    private final ServerUtils server;
    private final MainCtrl mainCtrl;

    /**
     * @param server server
     * @param mainCtrl mainctrl
     */
    @Inject
    public LanguageSwitchService(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    /**
     *
     */
    public void showStartPage(){
        mainCtrl.showStartPage();
    }


    /**
     *
     */
    public void setLocale(String s){
        mainCtrl.setLocale(s);
    }


    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }
}
