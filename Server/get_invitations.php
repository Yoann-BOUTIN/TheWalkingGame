<?php

$response = array();

require_once 'db_connect.php';
$bdd = Connexion::getConnexion();

if (isset($_GET["invite"])){
    $joueurInvite = $_GET["invite"];

    // On récupere le nombre d'invitations reçues par le joueur
    $bdd->prepare ( "SELECT COUNT(*) FROM invitation WHERE invite = :invite");
    $bdd->bind(":invite", $joueurInvite);
    $bdd->execute();
    $nbInvitations = $bdd->fetch();
    // On récupere l'entier correspondant au nombre d'invitations
    $nbInvitations = ($nbInvitations['COUNT(*)']);
    // echo $nbInvitations;
    $response["nb_invitations"] = $nbInvitations;

    $bdd->prepare ( "select * from invitation WHERE invite = :invite");
    $bdd->bind(":invite",$joueurInvite);
    $bdd->execute();
    //$result = $bdd->fetch();
    $result = $bdd->fetchAll();

    if($nbInvitations > 0){
        // On remplit la variable $response avec toutes les invitations
        while($nbInvitations > 0){
            $nbInvitations--;
            $response[$nbInvitations]["inviteur"] = $result[$nbInvitations]["inviteur"];
            $response[$nbInvitations]["date_invitation"] = $result[$nbInvitations]["date_invitation"];
            //$response[$nbInvitations]["success"] = 1;
        }
    }

    /**$response["success"] = 1;
    $response["inviteur"] = $result["inviteur"];
    $response["date_invitation"] = $result["date_invitation"];*/

    echo json_encode($response);
}

?>