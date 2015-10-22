package com.thewalkinggame.app.utilitaire;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Cette classe permet de construire et d'afficher une boite de dialogue avec un ou deux
 * boutons simplement en lui assignant un titre, un message, une icone, le texte d'un ou
 * des boutons, le ou les listeners, et le context dans lequel la boite de dialogue est appelée.
 *
 * Created by Marc on 27/03/2014.
 */
public class MyAlertDialog{
    /**
     * Le context dans lequel afficher
     * la boite de dialogue.
     */
    private Context context = null;
    /**
     * Le titre de la boite de dialogue
     */
    private String title = "";
    /**
     * Le message de la boite de dialogue
     */
    private String message = "";
    /**
     * Le listener associé au bouton de droite
     */
    private DialogInterface.OnClickListener listenerPositive = null;
    /**
     * Le listener associé au bouton du milieu
     */
    private DialogInterface.OnClickListener listenerNegative = null;
    /**
     * Le listener associé au bouton de gauche
     */
    private DialogInterface.OnClickListener listenerNeutral = null;
    /**
     * Le text associé au bouton de droite
     */
    private CharSequence textPositiveButton = "";
    /**
     * Le text associé au bouton de gauche
     */
    private CharSequence textNegativeButton = "";
    /**
     * Le text associé au bouton du milieu
     */
    private CharSequence textNeutralButton = "";
    /**
     * l'icone correspondant à la boite de
     * dialogue
     */
    private int inconId = 0;
    /**
     * Le nombre de boutons de la boite
     * de dialogue (1? 2? 3?)
     */
    private int nbButton = 0;
    /**
     * Si la boite de dialogue
     * est ouverte
     */
    private boolean isShown;

    /**
     * Constructeur de la classe MyAlertDialog qui permet de créér une boite de dialogue
     * qui contient 1 bouton.
     * @param title Titre de la boite de dialogue
     * @param message Message de la boite de dialoge
     * @param iconId Icone de la boite de dialogue
     * @param textNeutralButton Texte compris dans le bouton
     * @param listenerNeutral Listener correspondant au bouton
     * @param context Le context dans lequel la boite de dialogue est appelée
     */
    public MyAlertDialog (String title,
                          String message,
                          int iconId,
                          CharSequence textNeutralButton,
                          DialogInterface.OnClickListener listenerNeutral,
                          Context context){
        this.title = title;
        this.message = message;
        this.inconId = iconId;
        this.textNeutralButton = textNeutralButton;
        this.listenerNeutral = listenerNeutral;
        this.context = context;
        this.nbButton = 1;
        this.isShown = false;
    }

    /**
     * Constructeur de la classe MyAlertDialog qui permet de créér une boite de dialogue
     * qui contient 2 boutons.
     * @param title Titre de la boite de dialogue
     * @param message Message de la boite de dialoge
     * @param iconId Icone de la boite de dialogue
     * @param textPositiveButton Texte compris dans le bouton positif
     * @param textNegativeButton Texte compris dans le bouton negatif
     * @param listenerPositive Listener correspondant au bouton positif
     * @param listenerNegative Listener correspondant au bouton negatif
     * @param context Le context dans lequel la boite de dialogue est appelée
     */
    public MyAlertDialog (String title,
                          String message,
                          int iconId,
                          CharSequence textPositiveButton,
                          CharSequence textNegativeButton,
                          DialogInterface.OnClickListener listenerPositive,
                          DialogInterface.OnClickListener listenerNegative,
                          Context context){
        this.title = title;
        this.message = message;
        this.inconId = iconId;
        this.textPositiveButton = textPositiveButton;
        this.textNegativeButton = textNegativeButton;
        this.listenerPositive = listenerPositive;
        this.listenerNegative = listenerNegative;
        this.context = context;
        this.nbButton = 2;
        this.isShown = false;
    }
    /**
     * Affiche la boite de dialogue
     */


    public void show(){
        // Si la boite de dialogue contient 1 bouton
        if(this.nbButton == 1 && (!this.getIsShown())) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.context);

            // Modifie le titre de la boite de dialogue
            alertDialogBuilder.setTitle(this.title);

            // Modifie l'icone de la boite de dialogue
            alertDialogBuilder.setIcon(this.inconId);

            alertDialogBuilder
                    // Modifie le message de la boite de dialogue
                    .setMessage(this.message)
                    // Ne peut pas quitter la boite de dialogue avant d'avoir cliqué sur le bouton
                    .setCancelable(false)
                    .setNeutralButton(this.textNeutralButton, this.listenerNeutral);

            // Créé la boite de dialogue
            alertDialogBuilder.create();

            // Affiche la boite de dialogue
            alertDialogBuilder.show();

            // La boite de dialogue est affichée donc on passe isShown à true
            this.isShown = true;
        // Si la boite de dialogue contient 2 boutons
        } else if (this.nbButton == 2 && (!this.getIsShown())){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.context);

            // Modifie le titre de la boite de dialogue
            alertDialogBuilder.setTitle(this.title);

            // Modifie l'icone de la boite de dialogue
            alertDialogBuilder.setIcon(this.inconId);

            alertDialogBuilder
                    // Modifie le message de la boite de dialogue
                    .setMessage(this.message)
                    // Ne peut pas quitter la boite de dialogue avant d'avoir cliqué sur un bouton
                    .setCancelable(false)
                    .setNegativeButton(this.textNegativeButton, this.listenerNegative)
                    .setPositiveButton(this.textPositiveButton, this.listenerPositive);

            // Créé la boite de dialogue
            alertDialogBuilder.create();

            // Affiche la boite de dialogue
            alertDialogBuilder.show();

            // La boite de dialogue est affichée donc on passe isShown à true
            this.isShown = true;
        }
    }

    /**
     * Permet de savoir si la boite de dialogue est affichée ou non.
     *
     * @return true si la boite de dialogue est affichée, false sinon
     */
    public boolean getIsShown(){
        return this.isShown;
    }

    /**
     * Permet de modifier "l'état" de la boite de dialogue,
     * boite de dialogue affichée ou non.
     *
     * @param isShown modifie la variable isShown avec la valeur passée en paramètre,
     *                true pour dire que la boite de dialogue est affichée et
     *                false sinon.
     */
    public void setIsShown(boolean isShown){
        this.isShown = isShown;
    }
}
