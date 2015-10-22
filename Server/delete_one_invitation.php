<?php
 require_once 'db_connect.php';

// array for JSON response
$response = array();

if (isset($_GET["invite"])){
    $pseudoInvite = $_GET["invite"];
    $pseudoInviteur = $_GET["inviteur"];
    $date = $_GET["date"];

	$bdd = Connexion::getConnexion();

    $bdd->prepare ( "delete from invitation WHERE (invite = :invite and inviteur = :inviteur and date_invitation = :date)");
    $bdd->bind(":invite",$pseudoInvite);
    $bdd->bind(":inviteur",$pseudoInviteur);
    $bdd->bind(":date",$date);
    $bdd->execute();

	$response["success"] = 1;
	echo json_encode($response);
}

?>