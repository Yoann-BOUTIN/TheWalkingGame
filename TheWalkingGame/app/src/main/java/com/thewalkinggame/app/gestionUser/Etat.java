package com.thewalkinggame.app.gestionUser;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.thewalkinggame.app.MapFragment;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.endGame.EndOfGame;
import com.thewalkinggame.app.gestionJeu.GameChoice;
import com.thewalkinggame.app.gestionJeu.Objective;
import com.thewalkinggame.app.gestionJeu.Penality;
import com.thewalkinggame.app.utilitaire.MyAlertDialog;

/**
 * Created by Marc on 02/04/2014.
 *
 * Gere les différents états d'un joueur au cours de la partie.
 */
public enum Etat {
    // Tous les états possible du systeme
    AUCUN_LIEU, EN_RECHERCHE_DU_LIEU, LIEU_TROUVE, EN_DEFI, EN_PENALITE, REPOND_ALERT_DIALOG, FIN_PARTIE;

    private static Objective objective = null;
    private Penality penality = null;
    private static int defi_ok = 0;
    private static int defi_fail = 0;
    private static MapFragment mapFragment = null;
    private int nb_objectif = 0;

    public void setMapActivity(MapFragment mapFragment){
        Etat.mapFragment = mapFragment;
    }

    /**
     * Correspond a l'evenement dans lequel le joueur n'a aucun
     * objectif qui lui est attribué.
     * On obtient cet evenement quand le joueur vient de débuter la partie
     * ou quand le joueur a réussi un defi ou a terminé son temps de pénalité
     * due à un défi     loupé.
     *
     * @return le nouvel état du joueur
     */
    public Etat obtenirObjectif(final MapFragment mapFragment){
        // On met dans une variable l'état courant
        Etat result = this;
        switch (this){
            case AUCUN_LIEU:
                setMapActivity(mapFragment);
                    // Si l'objectif n'a pas été créé
                    if (objective == null) {
                        // On créé le premier objectif
                        objective = new Objective(43.7160892d, 7.2635992d);
                        objective.tellToGoOnObjective(mapFragment);
                        // Dessine le marqueur correspondant à l'objectif sur la map
                        objective.drawMarker(mapFragment.getMap());
                        // On change l'etat du joueur
                        mapFragment.setEtat(Etat.EN_RECHERCHE_DU_LIEU);
                        result = Etat.EN_RECHERCHE_DU_LIEU;
                        nb_objectif += 1;
                        break;
                    } else {
                        if (nb_objectif == objective.getNbObjectives()){
                            result = Etat.FIN_PARTIE;
                            break;
                        }else {
                            // Change la position du marqueur correspondant à l'objectif sur la map,
                            // on lui donne la position du prochain objectif
                            objective.newObjectiveLocation();
                            objective.tellToGoOnObjective(mapFragment);
                            // Dessine le marqueur correspondant à l'objectif sur la map
                            objective.drawMarker(mapFragment.getMap());
                            // On change l'etat du joueur
                            mapFragment.setEtat(Etat.EN_RECHERCHE_DU_LIEU);
                            result = Etat.EN_RECHERCHE_DU_LIEU;
                            nb_objectif += 1;
                            break;
                    }
                }
            case EN_RECHERCHE_DU_LIEU:
                // rien
                break;
            case LIEU_TROUVE:
                // rien
                break;
            case EN_DEFI:
                // rien
                break;
            case EN_PENALITE:
                // rien
                break;
            case REPOND_ALERT_DIALOG:
                // rien
                break;
            case FIN_PARTIE:
                break;
        }
        // On renvoie l'état
        return result;
    }


    /**
     * Correspond à l'evenement dans lequel le joueur se trouve sur l'objectif.
     *
     * @return le nouvel état du joueur
     */
    public Etat aAtteintObjectif(UserLocation userLocation){
        Etat result = this;
        switch (this){
            case AUCUN_LIEU:
                // rien
                break;
            case EN_RECHERCHE_DU_LIEU:

                if(userLocation.isOnObjective(objective)) {
                    //Action
                    // Affiche la boite de dialogue "Objectif atteint !"
                    new MyAlertDialog(
                            "Info",
                            "Objectif atteint ! Le défi va se lancer",
                            R.drawable.icon_objective,
                            "OK",
                            new DialogInterface.OnClickListener() {
                                // Lors d'un click sur le bouton "OK" de la boite de dialogue
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    // Ferme la boite de dialogue
                                    dialog.cancel();
                                    // On change l'etat du joueur
                                    mapFragment.setEtat(Etat.LIEU_TROUVE);
                                    mapFragment.getEtat().lancerDefi();
                                }
                            },
                            mapFragment.getActivity()).show();

                    // Si le joueur n'a pas repondu a la boite de dialogue
                    result = Etat.REPOND_ALERT_DIALOG;
                } // sinon l'état du joueur ne change pas
                break;

            case LIEU_TROUVE:

                if(!userLocation.isOnObjective(objective)) {

                    // Affiche la boite de dialogue "Objectif perdu !"
                    new MyAlertDialog(
                            "Info",
                            "Vous êtes trop eloigné de l'objectif",
                            R.drawable.icon_objective,
                            "OK",
                            new DialogInterface.OnClickListener() {
                                // Lors d'un click sur le bouton "OK" de la boite de dialogue
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    // Ferme la boite de dialogue
                                    dialog.cancel();
                                    // On change l'etat du joueur
                                    mapFragment.setEtat(Etat.EN_RECHERCHE_DU_LIEU);
                                }
                            },
                            mapFragment.getActivity()).show();

                    // Si le joueur n'a pas repondu a la boite de dialogue
                    result = Etat.REPOND_ALERT_DIALOG;
                } // sinon l'état du joueur ne change pas
                break;
            case EN_DEFI:
                // rien
                break;
            case EN_PENALITE:
                // rien
                break;
            case REPOND_ALERT_DIALOG:
                // rien
                break;
            case FIN_PARTIE:
                // rien
                break;
        }
        return result;
    }


    /**
     * Correspond à l'evenement dans lequel un défi debute pour le joueur.
     *
     * @return le nouvel état du joueur
     */
    public Etat lancerDefi(){
        Etat result = this;
        switch (this){
            case AUCUN_LIEU:
                // rien
                break;
            case EN_RECHERCHE_DU_LIEU:
                // rien
                break;
            case LIEU_TROUVE:
                //Action
                // On lance l'activity du Quiz
                //Intent intent = new Intent(mapActivity.getActivity(),QuizActivity.class);
                // On lance l'activity de demarrage du jeu Reanimer
                GameChoice gameChoice = new GameChoice(mapFragment.getActivity());
                gameChoice.launchGame();
                // On change l'etat du joueur
                mapFragment.setEtat(Etat.EN_DEFI);
                result = Etat.EN_DEFI;

                break;
            case EN_DEFI:
                // rien
                break;
            case EN_PENALITE:
                // rien
                break;
            case REPOND_ALERT_DIALOG:
                // rien
                break;
            case FIN_PARTIE:
                // rien
                break;
        }
        return result;
    }

    /**
     * Si le joueur a reussi le défi, il lui en sera
     * attribué un autre, sinon il devra rester un
     * moment en pénalité avant d'avoir un autre défi.
     *
     * @param defiReussi si le defi est reussi ou loupé
     * @return le nouvel état du joueur
     */
    public Etat defiTermine(boolean defiReussi){
        Etat result = this;
        switch (this){
            case AUCUN_LIEU:
                // rien
                break;
            case EN_RECHERCHE_DU_LIEU:
                // rien
                break;
            case LIEU_TROUVE:
                // rien
                break;
            case EN_DEFI:
                if(defiReussi){
                    defi_ok+=1;
                    Log.d("OK PUIS NOK", String.valueOf(defi_ok));
                    //Action
                    // Supprime le marker de la map
                    //objective.deleteMarker(map);
                    // On change l'etat du joueur
                    mapFragment.setEtat(Etat.AUCUN_LIEU);
                    result = Etat.AUCUN_LIEU;
                    mapFragment.getEtat().obtenirObjectif(mapFragment);
                    // defi non réussi
                } else {
                    defi_fail+=1;
                    Log.d("DEFI FAIL : ", String.valueOf(defi_fail));
                            //Action
                    // Affiche le timer de la pénalité avec sa progress bar,
                    // la pénalité dure ici 10 * 1000 ms = 10 secondes.
                    penality = new Penality(mapFragment);
                    penality.runThread();
                    //penality.startCountdownTimer(10 * 1000);
                    // On change l'etat du joueur
                    mapFragment.setEtat(Etat.EN_PENALITE);
                    result = Etat.EN_PENALITE;
                }
                break;

            case EN_PENALITE:
                // rien
                break;
            case REPOND_ALERT_DIALOG:
                // rien
                break;
            case FIN_PARTIE:
                // rien
                break;
        }
        return result;
    }


    /**
     * penaliteTerminee correspond à l'evenement dans lequel le temps de
     * penalité attribué au joueur qui n'a pas réussi son défi est
     * terminé.
     *
     * @return le nouvel état du joueur
     */
    public Etat penaliteTerminee(){
        Etat result = this;
        switch (this){
            case AUCUN_LIEU:
                // rien
                break;
            case EN_RECHERCHE_DU_LIEU:
                // rien
                break;
            case LIEU_TROUVE:
                // rien
                break;
            case EN_DEFI:
                // rien
                break;
            case EN_PENALITE:
                //Action
                // Supprime le marker de la map
                //objective.deleteMarker(map);
                // On change l'etat du joueur
                mapFragment.setEtat(Etat.AUCUN_LIEU);
                result = Etat.AUCUN_LIEU;
                mapFragment.getEtat().obtenirObjectif(mapFragment);
                break;

            case REPOND_ALERT_DIALOG:
                // rien
                break;
            case FIN_PARTIE:
                // rien
                break;
        }
        return result;
    }

    /**
     * Correspond au à la fin de jeu
     */
    public Etat partieTermine(){
        Etat result = this;
        switch (this){
            case AUCUN_LIEU:
                // rien
                break;
            case EN_RECHERCHE_DU_LIEU:
                // rien
                break;
            case LIEU_TROUVE:
                // rien
                break;
            case EN_DEFI:
                // rien
                break;
            case EN_PENALITE:
                //rien
                break;
            case REPOND_ALERT_DIALOG:
                // rien
                break;
            case FIN_PARTIE:
                //Activity end game
                Intent intent = new Intent(mapFragment.getActivity(), EndOfGame.class);
                intent.putExtra("defi_ok",defi_ok);
                intent.putExtra("defi_fail",defi_fail);
                mapFragment.getActivity().startActivity(intent);
                break;
        }
        return result;
    }

    /**
     * Permet d'obtenir l'objet objectif.
     *
     * @return l'objet objectif
     */
    public Objective getObjectif(){
        return objective;
    }
}
