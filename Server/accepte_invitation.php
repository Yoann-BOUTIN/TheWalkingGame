<?php

$response = array();

require_once 'db_connect.php';
$bdd = Connexion::getConnexion();

    $invite = $_POST["invite"];
    $inviteur = $_POST["inviteur"];

    // On récupere les joueurs de l'equipe
    $bdd->prepare ( "SELECT joueur_1, joueur_2, joueur_3, joueur_4 FROM team WHERE joueur_1 = :joueur or joueur_2 = :joueur or joueur_3 = :joueur or joueur_4 = :joueur");
    $bdd->bind(":joueur", $inviteur);
    $bdd->execute();
    $listeJoueurs = $bdd->fetch();

    // On update le pseudo du joueur invité dans la BDD pour valider son invitation dans l'équipe
    if($listeJoueurs["joueur_2"] == "Joueur manquant" || substr($listeJoueurs["joueur_2"], 0, 10) == "En attente" && substr($listeJoueurs["joueur_2"], 14, strlen($listeJoueurs["joueur_2"])) == $invite) {
        $bdd->prepare ( "UPDATE team SET joueur_2 = :joueur2 WHERE joueur_1 = :joueur1");
        $bdd->bind(":joueur1", $inviteur);
        $bdd->bind(":joueur2", $invite);
        $bdd->execute();
    } else if($listeJoueurs["joueur_3"] == "Joueur manquant" || substr($listeJoueurs["joueur_3"], 0, 10) == "En attente" && substr($listeJoueurs["joueur_3"], 14, strlen($listeJoueurs["joueur_3"])) == $invite){
        $bdd->prepare ( "UPDATE team SET joueur_3 = :joueur3 WHERE joueur_1 = :joueur1");
        $bdd->bind(":joueur1", $inviteur);
        $bdd->bind(":joueur3", $invite);
        $bdd->execute();
    } else if($listeJoueurs["joueur_4"] == "Joueur manquant" || substr($listeJoueurs["joueur_4"], 0, 10) == "En attente" && substr($listeJoueurs["joueur_4"], 14, strlen($listeJoueurs["joueur_4"])) == $invite){
        $bdd->prepare ( "UPDATE team SET joueur_4 = :joueur4 WHERE joueur_1 = :joueur1");
        $bdd->bind(":joueur1", $inviteur);
        $bdd->bind(":joueur4", $invite);
        $bdd->execute();
    }


?>