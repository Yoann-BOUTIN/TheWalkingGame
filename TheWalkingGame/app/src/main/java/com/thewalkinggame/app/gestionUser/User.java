package com.thewalkinggame.app.gestionUser;

import java.util.regex.Pattern;

/**
 * Created by Zizou on 25/03/2014.
 */
public class User {
    /**
     * Le pseudo du joueur
     */
    String pseudo = null;

    /**
     *
     * @param pseudo : pseudo de l'utilisateur
     */
    public User(String pseudo){

        this.pseudo = pseudo;
    }

    /**
     * methode permettant de recuperer le pseudo de l'utilisateur
     * @return: le pseudo de l'utilisateur
     */
    public String getPseudo()
    {
        return pseudo;
    }

    /**
     * methode qui verifie si le pseudo est bien conforme a la regex
     * @return : boolean
     */
    public boolean isValid() {
        if (Pattern.matches("[[A-Z][a-z]]{4,10}", pseudo)) {
            return true;
        }
        return false;
    }
}
