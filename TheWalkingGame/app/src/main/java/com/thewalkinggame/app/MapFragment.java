package com.thewalkinggame.app;


import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.thewalkinggame.app.gestionUser.Etat;
import com.thewalkinggame.app.gestionUser.GetAllPseudoOfTeam;
import com.thewalkinggame.app.gestionUser.UpdatePosition;
import com.thewalkinggame.app.gestionUser.UserLocation;

import java.text.DecimalFormat;

/**
 * Created by Marc on 21/03/14.
 */
public class MapFragment extends Fragment implements LocationListener{

    private GoogleMap map;
    private double distBetweenObjAndPlayer = 0;
    private TextView textDistBetweenObjAndPlayer = null;
    private Etat etat = Etat.AUCUN_LIEU;
    private boolean defiReussi = true;
    private Location location;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    LocationManager locationManager;
    private TextView gameChrono = null;

    String memoire;
    private String pseudoJ2="";
    private String pseudoJ3="";
    private String pseudoJ4="";

    private UserLocation userLocation = null;
    private UserLocation userLocationJ2 = null;
    private UserLocation userLocationJ3 = null;
    private UserLocation userLocationJ4 = null;
    /**
     * Temps que dure la partie
     * en secondes.
     */
    private int totalTime;
    private int chrono;
    private TextView mTxtViewTimeRemaining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //final Context context = getActivity();
        View v = inflater.inflate(R.layout.activity_map, container, false);

        // Retourne un MapFragment (qui est un Fragment) construit à partir de la balise fragment
        // du fichier activity_map.xml
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.mapmap)).getMap();
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        editor = settings.edit();
        memoire = settings.getString("pseudo", "");
        totalTime = 1800;
        chrono = 0;
        mTxtViewTimeRemaining = (TextView) v.findViewById(R.id.timeChronoMap);

        // On va chercher les pseudo des membres de l'equipe
        new GetAllPseudoOfTeam(memoire, MapFragment.this).execute();

        userLocation = new UserLocation(memoire);

        etat = etat.obtenirObjectif(MapFragment.this);

        // Retourne un LocationManager object qui va permettre de contrôler les mises à jours
        // de position
        locationManager = (LocationManager)getActivity().getSystemService(Activity.LOCATION_SERVICE);

        // Créé un criteria object nécessaire pour récupérer le fournisseur
        Criteria criteria = new Criteria();

        // Retourne le nom du meilleur fournisseur disponible
        String provider = locationManager.getBestProvider(criteria, true);

        // On peut utiliser le fournisseur tout de suite pour obtenir la dernière position connue
        Location location = locationManager.getLastKnownLocation(provider);

        // Demande au fournisseur de mettre à jour le GPS toutes les 3 secondes
        locationManager.requestLocationUpdates(provider, 3000, 0, this);
        startProgress();
        return v;
    }

    public void setPseudoJ2(String pseudoJ2){
        this.pseudoJ2 = pseudoJ2;
    }

    public void setPseudoJ3(String pseudoJ3){
        this.pseudoJ3 = pseudoJ3;
    }

    public void setPseudoJ4(String pseudoJ4){
        this.pseudoJ4 = pseudoJ4;
    }

    public void setUserLocationJ2(UserLocation userLocation){
        userLocationJ2 = userLocation;
    }

    public void setUserLocationJ3(UserLocation userLocation){
        userLocationJ3 = userLocation;
    }

    public void setUserLocationJ4(UserLocation userLocation){
        userLocationJ4 = userLocation;
    }

    public void setEtat(Etat etat){
        this.etat = etat;
    }

    public Etat getEtat(){
        return etat;
    }

    public UserLocation getUserLocation(){
        return userLocation;
    }

    public void setLocation(Location location){
        this.location = location;
    }

    public MapFragment getMapActivity(){
        return MapFragment.this;
    }

    /**
     * Permet de faire une action lorsque la position a de l'utilisateur change.
     *
     * @param location correspond à la position fournie par le gps toutes les X secondess
     */
    @Override
    public void onLocationChanged(Location location) {
        if(textDistBetweenObjAndPlayer == null) {
            textDistBetweenObjAndPlayer = (TextView) getView().findViewById(R.id.textDistBetweenObjAndPlayer);
        }
        if (map != null) {
            // Peu importe l'etat du joueur, on affiche sa position sur la map.
            // On update aussi sa position dans cette methode.
            userLocation.drawMyLocationWithMarker(map, location);

            if(userLocationJ2 != null && userLocationJ3 != null && userLocationJ4 != null) {
                userLocationJ2.drawPlayer2LocationWithMarker(map);
                userLocationJ3.drawPlayer3LocationWithMarker(map);
                userLocationJ4.drawPlayer4LocationWithMarker(map);
            }

            Log.v(">>>>>", "les pseudo dans onLocationChanged " + pseudoJ2 + " " + pseudoJ3 + " " + pseudoJ4);

            this.setLocation(location);

            // Une même tache peut être lancée une seule fois donc
            // on fait new UpdatePosition
            new UpdatePosition(MapFragment.this).execute();

            // On affiche la fleche correspondant à la position de l'objectif
            // par rapport à la position du joueur.
            userLocation.drawBearingArrowWithMarker(location, map, etat.getObjectif());

            // Evenement aAtteintObjectif
            etat = etat.aAtteintObjectif(userLocation);

            // S'il y a un objectif a atteindre
            // On affiche la distance entre cet objectif et le joueur
            if(etat != Etat.AUCUN_LIEU) {
                // On calcule la distance entre le joueur et l'objectif
                distBetweenObjAndPlayer = userLocation.distTo(etat.getObjectif());
                // On ne veut pas de .0
                DecimalFormat df = new DecimalFormat("#######");
                if (userLocation.distTo(etat.getObjectif()) >= 10) {
                    textDistBetweenObjAndPlayer.setText("Plus que " + df.format(distBetweenObjAndPlayer) +
                            " mètres");
                } else {
                    textDistBetweenObjAndPlayer.setText("");
                }
            }
        }
    }

    /**
     * Fonction chrono qui limite le temps d'une partie
     * Si le temps maximum est atteint la partie est finie
     * Si le joueur a atteint le 10 eme objectif la partie est finie
     */
    public void startProgress() {
        Runnable runnable = new Runnable() {
            @Override
            public void run(){
                while ((chrono <= totalTime) && (etat.obtenirObjectif(MapFragment.this) != Etat.FIN_PARTIE)) {
                    try {
                        if(totalTime - chrono >= 3600){
                            final int heure = (totalTime - chrono)/3600;
                            final int minute = ((totalTime - chrono)/60) % 60;
                            final int seconde = (totalTime - chrono) % 60;
                            mTxtViewTimeRemaining.post(new Runnable() {
                                @Override
                                public void run() {
                                    mTxtViewTimeRemaining.setText("Temps restant : " + heure + " h " + minute + " min " + seconde + " sec");
                                }
                            });
                        }else if((totalTime - chrono) < 3600 && (totalTime - chrono) >= 60){
                            final int minute = (totalTime - chrono)/60;
                            final int seconde = (totalTime - chrono) % 60;
                            mTxtViewTimeRemaining.post(new Runnable() {
                                @Override
                                public void run() {
                                    mTxtViewTimeRemaining.setText("Temps restant : " + minute + " min " + seconde + " sec");
                                }
                            });
                        }else{
                            final int seconde = (totalTime - chrono) % 60;
                            mTxtViewTimeRemaining.post(new Runnable() {
                                @Override
                                public void run() {
                                    mTxtViewTimeRemaining.setText("Temps restants : " + seconde + " sec");
                                }
                            });
                        }
                        Thread.sleep(1000);
                        chrono++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mTxtViewTimeRemaining.post(new Runnable() {
                    @Override
                    public void run() {

                        //mTxtViewTimeRemaining.setText("Temps restant : " + 0 + " sec");
                        Toast.makeText(getActivity(), "Fin de la partie ! ", Toast.LENGTH_SHORT).show();
                        etat = Etat.FIN_PARTIE;
                        etat = etat.partieTermine();
                        locationManager.removeUpdates(getMapActivity());
                    }
                });
            }
        };
        new Thread(runnable).start();
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public GoogleMap getMap(){
        return map;
    }

    public void onDestroyView(){
        locationManager.removeUpdates(this);
        super.onDestroyView();
    }
}


