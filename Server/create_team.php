<?php

$response = array();

require_once 'db_connect.php';
$bdd = Connexion::getConnexion();

if (isset($_POST["joueur"]) && ($_POST["joueur"] != "")){
    $joueur = $_POST["joueur"];

    // On créé l'équipe
    $bdd->prepare ("INSERT into team (joueur_1, joueur_2, joueur_3, joueur_4) VALUES (:joueur, :joueur2, :joueur3, :joueur4)");
    $bdd->bind(":joueur", $joueur);
    $bdd->bind(":joueur2", "Joueur manquant");
    $bdd->bind(":joueur3", "Joueur manquant");
    $bdd->bind(":joueur4", "Joueur manquant");
    $bdd->execute();

    // On récupere les joueurs de l'equipe
    $bdd->prepare ( "SELECT joueur_1, joueur_2, joueur_3, joueur_4 FROM team WHERE joueur_1 = :joueur or joueur_2 = :joueur or joueur_3 = :joueur or joueur_4 = :joueur");
    $bdd->bind(":joueur", $joueur);
    $bdd->execute();
    $listeJoueurs = $bdd->fetch();
    $response["listeJoueurs"] = $listeJoueurs;

    echo json_encode($listeJoueurs);
}

?>