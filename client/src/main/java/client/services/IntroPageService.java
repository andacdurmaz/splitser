package client.services;

import client.scenes.MainCtrl;


import javax.inject.Inject;

public class IntroPageService {
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
     * @param mainCtrl  mainctrl instance
     */
    @Inject
    public IntroPageService(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * @param s String
     * @return string
     */
    public String getString(String s){
        return mainCtrl.getBundle().getString(s);
    }


}