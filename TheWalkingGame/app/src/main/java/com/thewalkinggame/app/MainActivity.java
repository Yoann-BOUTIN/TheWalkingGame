package com.thewalkinggame.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.thewalkinggame.app.admin.MainManageActivity;
import com.thewalkinggame.app.gestionTeam.InvitationActivity;
import com.thewalkinggame.app.gestionTeam.RecrutementActivity;
import com.thewalkinggame.app.gestionUser.ProfilActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Menu de l'application TheWalkingGame.
 */
public class MainActivity extends Activity {
    /**
     * Bouton pour lancer le test
     * 1 joueur.
     */
    Button boutonToGame = null;
    /**
     * Bouton pour lancer l'activity
     * de recrutement de survivants.
     */
    Button boutonToRecrute = null;
    /**
     * Bouton pour accéder au journal
     * d'invitations.
     */
    Button boutonToInvitation = null;
    /**
     * Bouton pour lancer l'activity
     * du profil du joueur.
     */
    Button boutonToProfil = null;
    /**
     * Bouton qui fait apparaitre les
     * boutons pour gérer le son des
     * clicks et la musique du jeu.
     */
    private Button buttonOption = null;
    /**
     * Bouton qui permet de couper la
     * musique du menu ou de la réactiver.
     */
    private Button buttonSoundMusic = null;
    /**
     * Bouton qui permet de couper le son
     * des clicks ou de les réactiver.
     */
    private Button buttonSoundClick = null;

    /**
     * Les animations correspondantes à l'apparition  **
     * * des boutons de gestion de musique/sons         *
     */

    private Animation animGetVisibleButtonMusic = null;
    private Animation animGetVisibleButtonGenerique = null;
    private Animation animGetVisibleButtonClick = null;
    private Animation animGetInvisibleButtonMusic = null;
    private Animation animGetInvisibleButtonGenerique = null;
    private Animation animGetInvisibleButtonClick = null;

    /**
     * True si les boutons permettant
     * la gestion du son, sont cachés
     * ou visible.
     */
    private boolean optionIsHidden = true;

    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static String url_delete_team = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/deleteByPseudo";
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static String url_create_team = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/team/new";


    /**
     * Le bouton qui permet d'accéder
     * à la partie ADMIN du jeu TheWalkingGame.
     */
    private ImageButton manage = null;
    /**
     * Preferences settings.
     */
    SharedPreferences settings;
    /**
     * Gestion de la musique du menu.
     */
    private MediaPlayer mpMusicMainMenu;
    /**
     * Gestion du son lors des clicks.
     */
    private MediaPlayer mpSongButtonClick;
    /**
     * True si les sons lors des clicks
     * sont activés, false sinon.
     */
    private static boolean songClickActivated = true;
    /**
     * True si la musique du menu est
     * activée, false sinon.
     */
    private static boolean songMenuActivated = true;
    /**
     * Les utilisateurs qui ont le
     * droit d'accés à la partie ADMIN
     * du jeu The Walking Game.
     */
    private final String yo = "yoann", marc = "Marc", zizou = "zizou", kev = "kevin";
    /**
     * Bouton pour lancer le générique
     * de l'application TheWalkingGame
     */
    private Button buttonToGenerique;

    /**
     * Pseudo en memoire du User
     */
    String memoire;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Recup des views et ajout des listeners correspondant **/

        boutonToGame = (Button) findViewById(R.id.boutonToGame);
        boutonToGame.setOnClickListener(listenerGoToMap);

        boutonToRecrute = (Button) findViewById(R.id.boutonToRecrute);
        boutonToRecrute.setOnClickListener(listenerGoToRecrute);

        boutonToInvitation = (Button) findViewById(R.id.boutonToInvitation);
        boutonToInvitation.setOnClickListener(listenerGoToInvitation);

        boutonToProfil = (Button) findViewById(R.id.boutonToProfil);
        boutonToProfil.setOnClickListener(listenerGoToProfil);

        buttonOption = (Button) findViewById(R.id.buttonOption);
        buttonOption.setOnClickListener(listenerOption);

        buttonSoundClick = (Button) findViewById(R.id.buttonSoundClick);
        buttonSoundClick.setOnClickListener(listenerSoundClick);
        buttonSoundClick.setVisibility(View.INVISIBLE);

        buttonSoundMusic = (Button) findViewById(R.id.buttonSoundMusic);
        buttonSoundMusic.setOnClickListener(listenerSoundMusic);
        buttonSoundMusic.setVisibility(View.INVISIBLE);

        buttonToGenerique = (Button) findViewById(R.id.buttonGoGenerique);
        buttonToGenerique.setOnClickListener(listenerGoToGenerique);
        buttonToGenerique.setVisibility(View.INVISIBLE);

        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        memoire = settings.getString("pseudo","");


        /** Gestion du bouton pour avoir accés à la partie ADMIN de l'appli **/

        if (memoire.equalsIgnoreCase(yo) || memoire.equalsIgnoreCase(zizou) || memoire.equalsIgnoreCase(marc) || memoire.equalsIgnoreCase(kev)) {
            manage = (ImageButton) findViewById(R.id.manage_button);
            manage.setVisibility(View.VISIBLE);
            manage.setOnClickListener(goToManage);
        }

        /** Gestion de l'animation du logo The Waling Game **/

        ImageView img = (ImageView) findViewById(R.id.simple_anim);
        img.setBackgroundResource(R.drawable.anim_acceuil);
        MyAnimationRoutine mar = new MyAnimationRoutine();
        Timer t = new Timer(false);
        t.schedule(mar, 100);

        /*ImageView fumee = (ImageView)findViewById(R.id.fumee_anim);
        fumee.setImageResource(R.drawable.anim_acceuil_fumee);
        MyAnimation marou = new MyAnimation();
        Timer tim = new Timer(false);
        tim.schedule(marou, 100);*/
        /** On associe les animations aux vues correspondantes **/

        animGetVisibleButtonMusic = AnimationUtils.loadAnimation(this, R.anim.anim_visible_button_sound_music);
        animGetVisibleButtonClick = AnimationUtils.loadAnimation(this, R.anim.anim_visible_button_sound_click);
        animGetVisibleButtonGenerique = AnimationUtils.loadAnimation(this, R.anim.anim_visible_button_generique);
        animGetInvisibleButtonMusic = AnimationUtils.loadAnimation(this, R.anim.anim_invisible_button_sound_music);
        animGetInvisibleButtonClick = AnimationUtils.loadAnimation(this, R.anim.anim_invisible_button_sound_click);
        animGetInvisibleButtonGenerique = AnimationUtils.loadAnimation(this, R.anim.anim_invisible_button_generique);

        /** Ajout de la musique du menu si elle n'est pas désactivée **/

        mpMusicMainMenu = MediaPlayer.create(MainActivity.this, R.raw.instru_main_menu_twg);
        if (songMenuActivated) {
            mpMusicMainMenu.start();
        }
        mpMusicMainMenu.setLooping(true);

        /** Son du click **/

        mpSongButtonClick = MediaPlayer.create(MainActivity.this, R.raw.sound_click_twg);

    }

    /**
     * Gere l'animation du logo TheWAlkingGame
     */
    class MyAnimationRoutine extends TimerTask {
        MyAnimationRoutine() {
        }

        public void run() {
            ImageView img = (ImageView) findViewById(R.id.simple_anim);
            // Get the background, which has been compiled to an AnimationDrawable object.
            AnimationDrawable frameAnimation = (AnimationDrawable) img.getBackground();

            // Start the animation (looped playback by default).
            frameAnimation.start();
        }
    }
    /*class MyAnimation extends TimerTask
    {
        MyAnimation()
        {
        }

        public void run()
        {
            ImageView fumee = (ImageView)findViewById(R.id.fumee_anim);
// Get the background, which has been compiled to an AnimationDrawable object.
            AnimationDrawable frameAnim = (AnimationDrawable) fumee.getBackground();

// Start the animation (looped playback by default).
            frameAnim.start();
        }
    }*/

    /**
     * Listener qui permet de lancer le TabActionBar
     */
    View.OnClickListener listenerGoToMap = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            runSongClick();
            new DeleteTeamByPseudo().execute();
        }
    };

    /**
     * Listener qui permet de lancer l'activity
     * de recrutements de survivants.
     */
    View.OnClickListener listenerGoToRecrute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runSongClick();
            Intent intent = new Intent(MainActivity.this, RecrutementActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
        }
    };

    /**
     * Listener qui permet de lancer l'activity
     * du journal d'invitations.
     */
    View.OnClickListener listenerGoToInvitation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runSongClick();
            Intent intent = new Intent(MainActivity.this, InvitationActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
        }
    };

    /**
     * Listener qui permet de lancer l'activity
     * du profil du joueur.
     */
    View.OnClickListener listenerGoToProfil = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runSongClick();
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
        }
    };

    /**
     * Listener qui permet de lancer l'activity
     * du générique de l'application The Walking Game.
     */
    View.OnClickListener listenerGoToGenerique = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runSongClick();
            Intent intent = new Intent(MainActivity.this, GeneriqueActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
        }
    };

    /**
     * Listener qui permet d'afficher, ou de cacher
     * les boutons de gestion du son, et de lancer
     * les animations correspondantes.
     */
    View.OnClickListener listenerOption = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (optionIsHidden) {
                runSongClick();
                buttonSoundMusic.setVisibility(View.VISIBLE);
                buttonSoundClick.setVisibility(View.VISIBLE);
                buttonToGenerique.setVisibility(View.VISIBLE);
                buttonSoundClick.startAnimation(animGetVisibleButtonClick);
                buttonSoundMusic.startAnimation(animGetVisibleButtonMusic);
                buttonToGenerique.startAnimation(animGetVisibleButtonGenerique);
                optionIsHidden = false;
            } else {
                runSongClick();
                buttonSoundClick.startAnimation(animGetInvisibleButtonClick);
                buttonSoundMusic.startAnimation(animGetInvisibleButtonMusic);
                buttonToGenerique.startAnimation(animGetInvisibleButtonGenerique);
                buttonSoundClick.setVisibility(View.INVISIBLE);
                buttonSoundMusic.setVisibility(View.INVISIBLE);
                buttonToGenerique.setVisibility(View.INVISIBLE);
                optionIsHidden = true;
            }
        }
    };

    /**
     * Listener qui permet de couper le son lors
     * des clicks ou de le réactiver.
     */
    View.OnClickListener listenerSoundClick = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            if (songClickActivated) {
                songClickActivated = false;
                buttonSoundClick.setBackground(getResources().getDrawable(R.drawable.sono_off_silver));
            } else {
                songClickActivated = true;
                buttonSoundClick.setBackground(getResources().getDrawable(R.drawable.sono_on_silver));
            }
            runSongClick();
        }
    };

    /**
     * Listener qui permet de couper la musique du
     * menu ou de la réactiver.
     */
    View.OnClickListener listenerSoundMusic = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            if (songMenuActivated) {
                songMenuActivated = false;
                mpMusicMainMenu.pause();
                buttonSoundMusic.setBackground(getResources().getDrawable(R.drawable.musique_off_silver));
            } else {
                songMenuActivated = true;
                mpMusicMainMenu.start();
                buttonSoundMusic.setBackground(getResources().getDrawable(R.drawable.musique_on_silver));
            }
            runSongClick();
        }
    };

    /**
     * Listener qui permet de lancer l'activity
     * correspondante à la partie ADMIN de l'application
     * The Walking Game.
     */
    View.OnClickListener goToManage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, MainManageActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
        }
    };

    /**
     * Lance le son du click de bouton si
     * le son des clicks est activé.
     * Sachant que le son est activé
     * par défaut.
     */
    public void runSongClick() {
        if (songClickActivated) {
            mpSongButtonClick.start();
        }
    }

    /**
     * On relance la musique si le
     * son est activé.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (songMenuActivated) {
            mpMusicMainMenu.start();
        }
    }

    /**
     * Lorsque l'application est en pause,
     * on met en pause la musique.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mpMusicMainMenu.pause();
    }

    class DeleteTeamByPseudo extends AsyncTask<String, String, String> {

        /**
         * Json parser.
         */
        JSONParser jsonParser = new JSONParser();
        /**
         * Les parametres qui permettent d'obtenir
         * les données voulue.
         */
        List<NameValuePair> params = null;

        @Override
        protected String doInBackground(String... args) {

            params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("joueur1", "testA"));

            JSONObject json = jsonParser.makeHttpRequest(url_delete_team, "DELETE", params);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            new CreateTeamForTest().execute();
        }
    }

    class CreateTeamForTest extends AsyncTask<String, String, String> {

        /**
         * Json parser.
         */
        JSONParser jsonParser = new JSONParser();
        /**
         * Les parametres qui permettent d'obtenir
         * les données voulue.
         */
        JSONObject params = new JSONObject();

        @Override
        protected String doInBackground(String... args) {

            try {
                params.put("joueur1", memoire);
                params.put("joueur2", "testA");
                params.put("joueur3", "testB");
                params.put("joueur4", "testC");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonParser.makeHttpPostRequest(url_create_team, "POST", params);
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            Intent intent = new Intent(MainActivity.this, TabActionBar.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in_opacity, R.anim.fade_out_opacity);
        }
    }
}


