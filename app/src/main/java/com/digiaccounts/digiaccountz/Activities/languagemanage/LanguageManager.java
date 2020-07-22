package com.digiaccounts.digiaccountz.Activities.languagemanage;

public class LanguageManager {

   static LanguageHandler loginLanghandler = new LanguageHandler();

   public static LanguageHandler getInstance(){
       return loginLanghandler;
   }

}
