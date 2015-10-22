package com.thewalkinggame.app.gestionUser;

import android.location.Location;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.thewalkinggame.app.R;
import com.thewalkinggame.app.gestionJeu.Objective;

/**
 * Created by Marc on 25/03/2014.
 */
public class UserLocation {
    /**
     * La position en latitude du joueur
     */
    private double latitude;
    /**
     * La position en longitude du joueur
     */
    private double longitude;

    /** Markers correspondants aux joueurs **/

    private Marker markerPlayer1;
    private Marker markerPlayer2;
    private Marker markerPlayer3;
    private Marker markerPlayer4;

    /**
     * Marker correspondant à la fleche
     * qui donne la direction vers le
     * prochain objectif.
     */
    private Marker markerArrow1;
    /**
     * Le pseudo du joueur courant
     */
    private String pseudo;

    /**
     * Constructeur de la classe UserLocation, initialise les identificateurs latitude
     * et longitude à 0.
     */
    public UserLocation(String pseudo){
        this.latitude = 0;
        this.longitude = 0;
        this.pseudo = pseudo;
    }

    /**
     * Pose un marker sur la map avec pour coordonnée la latitude et la longitude de
     * qui est fournie grace au location passé en paramètre, et zoom sur cette position.
     *
     * @param map Correspond à la map sur laquelle on affiche le marqueur.
     */
    public void drawMyLocationWithMarker(GoogleMap map, Location location){

        // On met à jour la position
        this.updateMyLocation(location);

        // Récupère la latitude et la longitude de la position donnée en paramètre
        LatLng currentPosition = new LatLng(this.latitude,this.longitude);

        // Zoom sur la position courante
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 18));

        // Si le marker n'est pas déjà créé, alors on le créé et on le place à la position
        // courant de l'utilisateur (Joueur 1)
        if(markerPlayer1 == null) {
            // Ajoute un marker sur la map, indiquant la position courante
            markerPlayer1 = map.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title(pseudo)
                    .snippet("Lat:" + this.latitude + " Lng:" + this.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_j1)));
        } else {
            // Sinon on modifie seulement la position du marker qui est déjà sur la map
            markerPlayer1.setPosition(currentPosition);
            // Ainsi que la position du marker affichée dans le snippet
            markerPlayer1.setSnippet("Lat:" + this.latitude + " Lng:" + this.longitude);
        }
    }

    /**
     * Pose un marker sur la map qui représente la position
     * à suivre, via une fleche.
     *
     * @param location Represente une position geographique.
     * @param map Correspond à la map sur laquelle on affiche le marqueur.
     */
    public void drawBearingArrowWithMarker(Location location, GoogleMap map, Objective objective){

        // On met à jour la position
        this.updateMyLocation(location);

        // Récupère la latitude et la longitude de la position donnée en paramètre
        // auquel on ajoute de la latitue afin que la fleche soit positionnée
        // au dessus du marker du joueur
        double lat = this.latitude + 0.0003;
        LatLng arrowPosition = new LatLng(lat,this.longitude);

        float bearingValue = getBearingValue(location, objective);

        // Si le marker n'est pas déjà créé, alors on le créé
        if(markerArrow1 == null) {
            // Ajoute un marker sur la map, indiquant la position courante
            markerArrow1 = map.addMarker(new MarkerOptions()
                    .position(arrowPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow_red))
                    .title("Objectif")
                    .snippet("Lat:" + objective.getLatitude() + " Lng:" + objective.getLongitude())
                    .anchor(0.5f,0.5f)
                    .alpha(0.9f)
                    .rotation(bearingValue));
        } else {
            // Sinon on modifie la position du marker qui est déjà sur la map,
            // sa trajectoire, son snippet.
            markerArrow1.setSnippet("Lat:" + objective.getLatitude() + " Lng:" + objective.getLongitude());
            markerArrow1.setPosition(arrowPosition);
            markerArrow1.setRotation(bearingValue);
        }
    }

    /**
     * Retourne la trajectoire en degrés entre le joueur et l'objectif
     * qu'il doit atteindre.
     *
     * @param location Represente une position geographique, celle du joueur.
     * @param objective Correspond à l'objectif du joueur.
     * @return la trajectoire de la position du joueur à la position
     * de l'objectif
     */
    public float getBearingValue(Location location, Objective objective){
        Location endingLocation = new Location("ending point");
        endingLocation.setLatitude(objective.getLatitude());
        endingLocation.setLongitude(objective.getLongitude());

        return location.bearingTo(endingLocation);
    }

    /**
     * Pose un marker sur la map avec pour coordonnée la latitude et la longitude de
     * qui est fournie grace au location passé en paramètre.
     *
     * @param map Correspond à la map sur laquelle on affiche le marqueur.
     */
    public void drawPlayer2LocationWithMarker(GoogleMap map){

        // On met à jour la position
        this.updateLocation();

        // Récupère la latitude et la longitude de la position donnée en paramètre
        LatLng currentPosition = new LatLng(this.latitude,this.longitude);

        // Si le marker n'est pas déjà créé, alors on le créé et on le place à la position
        // courant de l'utilisateur (Joueur 2)
        if(markerPlayer2 == null) {
            // Ajoute un marker sur la map, indiquant la position courante
            markerPlayer2 = map.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title(pseudo)
                    .snippet("Lat:" + this.latitude + " Lng:" + this.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_j2)));
        } else {
            // Sinon on modifie seulement la position du marker qui est déjà sur la map
            markerPlayer2.setPosition(currentPosition);
            // Ainsi que la position du marker affichée dans le snippet
            markerPlayer2.setSnippet("Lat:" + this.latitude + " Lng:" + this.longitude);
        }
    }

    /**
     * Pose un marker sur la map avec pour coordonnée la latitude et la longitude de
     * qui est fournie grace au location passé en paramètre.
     *
     * @param map Correspond à la map sur laquelle on affiche le marqueur.
     */
    public void drawPlayer3LocationWithMarker(GoogleMap map){

        // On met à jour la position
        this.updateLocation();

        // Récupère la latitude et la longitude de la position donnée en paramètre
        LatLng currentPosition = new LatLng(this.latitude,this.longitude);

        // Si le marker n'est pas déjà créé, alors on le créé et on le place à la position
        // courant de l'utilisateur (Joueur 3)
        if(markerPlayer3 == null) {
            // Ajoute un marker sur la map, indiquant la position courante
            markerPlayer3 = map.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title(pseudo)
                    .snippet("Lat:" + this.latitude + " Lng:" + this.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_j3)));
        } else {
            // Sinon on modifie seulement la position du marker qui est déjà sur la map
            markerPlayer3.setPosition(currentPosition);
            // Ainsi que la position du marker affichée dans le snippet
            markerPlayer3.setSnippet("Lat:" + this.latitude + " Lng:" + this.longitude);
        }
    }

    /**
     * Pose un marker sur la map avec pour coordonnée la latitude et la longitude de
     * qui est fournie grace au location passé en paramètre.
     *
     * @param map Correspond à la map sur laquelle on affiche le marqueur.
     */
    public void drawPlayer4LocationWithMarker(GoogleMap map){

        // On met à jour la position
        this.updateLocation();

        // Récupère la latitude et la longitude de la position donnée en paramètre
        LatLng currentPosition = new LatLng(this.latitude,this.longitude);

        // Si le marker n'est pas déjà créé, alors on le créé et on le place à la position
        // courant de l'utilisateur (Joueur 4)
        if(markerPlayer4 == null) {
            // Ajoute un marker sur la map, indiquant la position courante
            markerPlayer4 = map.addMarker(new MarkerOptions()
                    .position(currentPosition)
                    .title(pseudo)
                    .snippet("Lat:" + this.latitude + " Lng:" + this.longitude)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_j4)));
        } else {
            // Sinon on modifie seulement la position du marker qui est déjà sur la map
            markerPlayer4.setPosition(currentPosition);
            // Ainsi que la position du marker affichée dans le snippet
            markerPlayer4.setSnippet("Lat:" + this.latitude + " Lng:" + this.longitude);
        }
    }


    public void updateLocation(){
        new GetPosition(this).execute();
    }

    /**
     * Met à jour les identificateurs latitude et longitude avec les nouvelles positions
     * fournies grace aux services qui lui sont liés.
     *
     */
    public void updateMyLocation(Location location){
        this.setLatitude(location.getLatitude());
        this.setLongitude(location.getLongitude());
    }

    /**
     * Calcule la distance entre le joueur et l'objectif
     *
     * @param objective Correspond à l'objectif du joueur
     * @return La distance en mètres entre l'objectif et le joueur
     */
    public int distTo(Objective objective) {
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(this.getLatitude()-objective.getLatitude());
        double dLng = Math.toRadians(this.getLongitude()-objective.getLongitude());
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(objective.getLatitude())) *
                Math.cos(Math.toRadians(this.getLatitude())) *Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return (int)(dist * meterConversion);
    }

    /**
     * Permet de savoir si le joueur est arrivé à son objectif.
     *
     * @param objective L'objectif à atteindre pour le joueur.
     * @return True si la distance entre le joueur et l'objectif est infèrieure à 10 mètres,
     * False sinon.
     */
    public Boolean isOnObjective(Objective objective){
        return (this.distTo(objective) < 10);
    }

    /**
     * Permet de retourner la latitude correspondant à celle du joueur.
     *
     * @return La latitude correspondant à celle du joueur.
     */
    public double getLatitude(){
        return latitude;
    }

    /**
     * Permet de retourner la longitude correspondant à celle du joueur.
     *
     * @return La longitude correspondant à celle joueur.
     */
    public double getLongitude(){
        return longitude;
    }

    /**
     * Permet de modifier la latitude correspondant à celle du joueur.
     *
     * @param latitude valeur par laquelle on veut modifier la latitude
     *                 correspondant à celle du joueur.
     */
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    /**
     * Permet de modifier la longitude correspondant à celle du joueur.
     *
     * @param longitude Valeur par laquelle on veut modifier la longitude
     *                  correspondant à celle du joueur.
     */
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public String getUserPseudo(){
        return pseudo;
    }
}
