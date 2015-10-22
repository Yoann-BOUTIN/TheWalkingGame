<?php
 require_once 'db_connect.php';

// array for JSON response
$response = array();

$bdd = Connexion::getConnexion();

$newTimer = intval($_POST["newTimer"]);

// La requête qui permet de mettre à jour le nouveau temps autorise
$bdd->prepare ("UPDATE reanimer SET temps_autorise = :newTimer WHERE prototype = 1");
$bdd->bind(':newTimer',$newTimer);
$bdd->execute();
$response["success"] = 1;
echo json_encode($response);
?>