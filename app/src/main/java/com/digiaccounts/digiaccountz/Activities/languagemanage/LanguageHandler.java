package com.digiaccounts.digiaccountz.Activities.languagemanage;

public class LanguageHandler {

    boolean handling ;
    public String currentlanguage ="en";

    public LanguageHandler() {
        this.handling = false;
    }

    public boolean isHandling() {
        return handling;
    }

    public void setHandling(boolean handling) {
        this.handling = handling;
    }

    public void setCurrentlanguage(String currentlanguage) {
        this.currentlanguage = currentlanguage;
    }

    public String getCurrentlanguage() {
        return currentlanguage;
    }
}
