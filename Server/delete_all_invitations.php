<?php
 require_once 'db_connect.php';

// array for JSON response
$response = array();

if (isset($_GET["invite"])){
    $pseudoInvite = $_GET["invite"];

	$bdd = Connexion::getConnexion();

    $bdd->prepare ( "delete from invitation WHERE invite = :invite");
    $bdd->bind(":invite",$pseudoInvite);
    $bdd->execute();

	$response["success"] = 1;
	echo json_encode($response);
}

?>