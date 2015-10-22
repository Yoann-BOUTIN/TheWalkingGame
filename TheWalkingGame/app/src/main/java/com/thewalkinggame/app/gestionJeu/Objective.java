package com.thewalkinggame.app.gestionJeu;

import android.os.AsyncTask;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thewalkinggame.app.JSONParser;
import com.thewalkinggame.app.MapFragment;
import com.thewalkinggame.app.R;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe Objective permet de créér un objectif, de le placer sur la map avec un marker et
 * de calculer la distance en mètres entre un joueur et cet objectif.
 *
 * Created by Marc on 25/03/2014.
 */
public class Objective {
    /**
     * URL de notre server, sur lequel la requête va être effectuée.
     */
    private static String url_recup_objectives = "http://83.201.30.87:8080/thewalkinggame-0.0.1-SNAPSHOT/objective/allObjective";
    /**
     * Marker de l'objectif sur la map.
     */
    Marker markerObjective = null;
    /**
     * Position en latitude de l'objectif
     * sur la map.
     */
    double latitude;
    /**
     * Position en longitude de l'objectif
     * sur la map.
     */
    double longitude;
    /**
     * Position en latitude de l'objectif
     * precedent sur la map.
     */
    double latitudePreviousObjective;
    /**
     * Position en longitude de l'objectif
     * precedent sur la map.
     */
    double longitudePreviousObjective;
    /**
     * Nombre d'objectifs total.
     */
    int nbObjectives = 10;
    /**
     * Tableau d'objectifs
     */
    double[][] tabOjectives;
    /**
     * Objectifs restants, pas encore
     * utilisés.
     */
    boolean[] objectiveAvailable;
    /**
     * Random
     */
    Random r;
    /**
     * MapFragment
     */
    private MapFragment mapFragment;
    /**
     * ImageView correspondant à l'image
     * qui indique au joueur qu'il a un
     * nouvel objectif.
     */
    private ImageView imagePopUp = null;
    /**
     * L'animation de l'image du nouvel objectif.
     */
    private Animation animPopUp;

    /**
     * Constructeur de la classe Objective
     *
     * @param latitude la latitude de l'objectif sur la map
     * @param longitude la longitude de l'objectif sur la map
     */
    public Objective (double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
        tabOjectives = new double[nbObjectives][2];
        r = new Random();
        new RecupObjectives().execute();
        objectiveAvailable = new boolean[nbObjectives];
        for (int i = 0;i<nbObjectives;i++){
            objectiveAvailable[i] = true;
        }
    }

    /**
     * Ajoute le marquer de l'objectif sur la map
     *
     * @param map la map sur laquelle va être ajouté le marqueur
     */
    public void drawMarker(GoogleMap map){
        // Récupère la latitude et la longitude de la position donnée en paramètre
        LatLng currentPosition = new LatLng(this.latitude, this.longitude);

        if(markerObjective == null) {
            // Ajoute un marker sur la map, indiquant la position courante
            markerObjective = map.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title("Objectif")
                    .snippet("Lat:" + this.latitude + " Lng:" + this.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_objective)));
        } else {
            // Ajoute un marker sur la map, indiquant la position courante
            markerObjective.setPosition(currentPosition);
            markerObjective.setSnippet("Lat:" + this.latitude + " Lng:" + this.longitude);
        }
    }

    /**
     * Donne un nouvel objectif qui n'a pas encore
     * été utilisé.
     */
    public void newObjectiveLocation(){
        int objective;
        do {
            objective = r.nextInt(nbObjectives);

        }
        while(!objectiveAvailable[objective]);
        this.setLatitude(tabOjectives[objective][0]);
        this.setLongitude(tabOjectives[objective][1]);
        objectiveAvailable[objective] = false;
    }


    public double getLattitudePreviousObjective(){ return latitudePreviousObjective;}
    public double getLongitudePreviousObjective(){ return longitudePreviousObjective;}
    public boolean[] getObjectiveAvailable(){ return objectiveAvailable;}
    public double[][] getTabOjectives(){return tabOjectives;}

    /**
     *
     * @return la latitude de l'objectif sur la map
     */
    public double getLatitude(){
        return this.latitude;
    }

    /**
     *
     * @return la longitude de l'objectif sur la map
     */
    public double getLongitude(){
        return this.longitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public  void setLongitude(double longitude){
        this.longitude = longitude;
    }

    /**
     * Animation qui indique au joueur
     * qu'il a un nouvel objectif.
     *
     * @param fragment MapFragment
     */
    public void tellToGoOnObjective(MapFragment fragment) {
        this.mapFragment = fragment;
        new Thread(new Runnable() {
            @Override
            public void run() {
                mapFragment.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imagePopUp = (ImageView) mapFragment.getActivity().findViewById(R.id.info_next_objective);
                        animPopUp = AnimationUtils.loadAnimation(mapFragment.getActivity(),R.anim.anim_info_next_objective);

                        imagePopUp.setVisibility(View.VISIBLE);
                        imagePopUp.startAnimation(animPopUp);
                        imagePopUp.setVisibility(View.INVISIBLE);

                    }
                });
            }
        }).start();
    }

    public int getNbObjectives(){return nbObjectives;}
    /**
     * Permet de récuperer tous les objectifs
     * en BDD.
     */
    class RecupObjectives extends AsyncTask<String, String, String>{
        /**
         * Parametres qui permettent de récuperer
         * les données voulues en BDD.
         */
        List<NameValuePair> params = null;
        /**
         * Json parser
         */
        JSONParser jsonParser = new JSONParser();
        /**
         * Liste de json
         */
        JSONArray objectives = null;

        @Override
        protected String doInBackground(String... args) {

            // Building Parameters
            params = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(url_recup_objectives, "GET", params);

            try {
                objectives = json.getJSONArray("objectives");
                for (int i = 0; i < nbObjectives; i++) {
                    JSONObject obj = objectives.getJSONObject(i);
                    double latitude = obj.getDouble("latitude");
                    double longitude = obj.getDouble("longitude");
                    tabOjectives[i][0] = latitude;
                    tabOjectives[i][1] = longitude;


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
