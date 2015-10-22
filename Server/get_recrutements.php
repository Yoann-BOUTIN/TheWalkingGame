<?php

$response = array();

require_once 'db_connect.php';
$bdd = Connexion::getConnexion();

if (isset($_GET["joueur"])){
    $joueur = $_GET["joueur"];

    // On récupere les joueurs de l'equipe
    $bdd->prepare ( "SELECT joueur_1, joueur_2, joueur_3, joueur_4 FROM team WHERE joueur_1 = :joueur or joueur_2 = :joueur or joueur_3 = :joueur or joueur_4 = :joueur");
    $bdd->bind(":joueur", $joueur);
    $bdd->execute();
    $listeJoueurs = $bdd->fetch();

    $nbJoueurs = 0;
    // On incremente l'entier correspondant au nombre de joueurs dans l'equipe,
    // si le pseudo n'est pas "Joueur manquant" et n'est pas "En attente"...
    // C'est alors un joueur donc on incrémente la variable $nbJoueurs.
    if($listeJoueurs['joueur_1'] != "Joueur manquant" && substr($listeJoueurs["joueur_1"], 0, 10) != "En attente") $nbJoueurs++;
    if($listeJoueurs['joueur_2'] != "Joueur manquant" && substr($listeJoueurs["joueur_2"], 0, 10) != "En attente") $nbJoueurs++;
    if($listeJoueurs['joueur_3'] != "Joueur manquant" && substr($listeJoueurs["joueur_3"], 0, 10) != "En attente") $nbJoueurs++;
    if($listeJoueurs['joueur_4'] != "Joueur manquant" && substr($listeJoueurs["joueur_4"], 0, 10) != "En attente") $nbJoueurs++;

    $response["listeJoueurs"] = $listeJoueurs;
    $response["nbJoueurs"] = $nbJoueurs;

    echo json_encode($response);
}

?>