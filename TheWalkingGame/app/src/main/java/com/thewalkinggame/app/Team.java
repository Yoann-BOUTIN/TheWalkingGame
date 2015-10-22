package com.thewalkinggame.app;

import android.util.Log;
import android.widget.TextView;

import com.thewalkinggame.app.gestionTeam.RecrutementActivity;

/**
 * Created by Marc on 26/04/2014.
 */
public class Team {
    String joueur1;
    String joueur2;
    String joueur3;
    String joueur4;
    int nbJoueursManquants;
    RecrutementActivity recrutementActivity;

    public Team(String memoirePseudoJoueur1, RecrutementActivity recrutementActivity){
        joueur1 = memoirePseudoJoueur1;
        joueur2 = "";
        joueur3 = "";
        joueur4 = "";
        this.recrutementActivity = recrutementActivity;
        nbJoueursManquants = 3;
    }

    public void addTeamMate(String joueur){
        if (joueur2.isEmpty()) {
            joueur2 = joueur;
            nbJoueursManquants--;
            updateRecrutementText(joueur);
        } else if (joueur3.isEmpty()){
            joueur3 = joueur;
            nbJoueursManquants--;
            updateRecrutementText(joueur);
        } else if (joueur4.isEmpty()){
            joueur4 = joueur;
            nbJoueursManquants--;
            updateRecrutementText(joueur);
        }
        Log.v(">>> " + joueur + " ", "a accepte l'invitation ! :) plus que " + nbJoueursManquants);
    }

    /**
     * Met a jour le textView "En attente de ..." dans la page
     * de recrutement de survivants.
     * Met a jour le nombre de survivants manquants.
     *
     * @param joueur Le pseudo du joueur qui a accepte l'invitation
     */
    public void updateRecrutementText(String joueur) {
        TextView tNbJoueursManquants = (TextView) recrutementActivity.findViewById(R.id.nb_survivants_manquants);
        TextView tPseudoJoueur2 = (TextView) recrutementActivity.findViewById(R.id.pseudo_joueur2);
        TextView tPseudoJoueur3 = (TextView) recrutementActivity.findViewById(R.id.pseudo_joueur3);
        TextView tPseudoJoueur4 = (TextView) recrutementActivity.findViewById(R.id.pseudo_joueur4);
        String[] tPseudoJoueur2Split = ((String) tPseudoJoueur2.getText()).split(" ");
        String[] tPseudoJoueur3Split = ((String) tPseudoJoueur3.getText()).split(" ");
        String[] tPseudoJoueur4Split = ((String) tPseudoJoueur4.getText()).split(" ");
        String pseudoJoueur2 = tPseudoJoueur2Split[tPseudoJoueur2Split.length - 1];
        String pseudoJoueur3 = tPseudoJoueur3Split[tPseudoJoueur3Split.length - 1];
        String pseudoJoueur4 = tPseudoJoueur4Split[tPseudoJoueur4Split.length - 1];
        Log.v("info", tPseudoJoueur2 + " " + tPseudoJoueur2Split + " " + pseudoJoueur2);
        if (joueur.equals(pseudoJoueur2)) {
            Log.v("Dans pseudoJoueur2", "" + pseudoJoueur2);
            ((TextView) recrutementActivity.findViewById(R.id.pseudo_joueur2)).setText(joueur);
        } else if (joueur.equals(pseudoJoueur3)) {
            Log.v("Dans pseudoJoueur3", "" + pseudoJoueur3);
            ((TextView) recrutementActivity.findViewById(R.id.pseudo_joueur3)).setText(joueur);
        } else if (joueur.equals(pseudoJoueur4)) {
            Log.v("Dans pseudoJoueur4", "" + pseudoJoueur4);
            ((TextView) recrutementActivity.findViewById(R.id.pseudo_joueur4)).setText(joueur);
        }
        tNbJoueursManquants.setText(""+nbJoueursManquants);
    }

    public int getNbJoueursManquants(){
        return nbJoueursManquants;
    }
}
