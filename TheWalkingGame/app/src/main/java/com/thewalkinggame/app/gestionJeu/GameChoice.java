package com.thewalkinggame.app.gestionJeu;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.thewalkinggame.app.gestionJeu.FindTheKey.SplashFindTheKey;
import com.thewalkinggame.app.gestionJeu.HeadUpsideDown.SplashHeadUpsideDown;
import com.thewalkinggame.app.gestionJeu.JeuSimon.SimonGameActivity;
import com.thewalkinggame.app.gestionJeu.QuizHandler.QuizActivity;
import com.thewalkinggame.app.gestionJeu.Reanimer.SplashReanimer;

import java.util.Random;

/**
 * Created by law on 09/05/2014.
 *
 * Permet de lancer aléatoirement un jeu.
 */
public class GameChoice {

    /**
     * Fragment de MapFragment
     */
    FragmentActivity mapActivity;

    /**
     * Constructeur de la class GameChoice
     *
     * @param mapActivity Fragment de MapFragment
     */
    public GameChoice(FragmentActivity mapActivity){
        this.mapActivity = mapActivity;
    }

    /**
     * Lance l'activity d'un jeu aléatoirement.
     */
    public void launchGame()
    {
        int numberOfGame    = KindOfGames.values().length;
        Random random       = new Random();
        int idGame          = random.nextInt(numberOfGame);

        Intent intent = null;
        switch (idGame)
        {
            case 0:
                intent = new Intent(mapActivity, QuizActivity.class);
                break;
            case 1:
                intent = new Intent(mapActivity, SplashReanimer.class);
                break;
            case 2:
                intent = new Intent(mapActivity, SimonGameActivity.class);
                break;
            case 3:
                intent = new Intent(mapActivity, SplashHeadUpsideDown.class);
                break;
            case 4:
                intent = new Intent(mapActivity, SplashFindTheKey.class);
                break;
        }
        mapActivity.startActivity(intent);
    }
}
