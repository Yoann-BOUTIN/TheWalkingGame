<?php
 require_once 'db_connect.php';

// array for JSON response
$response = array();

$bdd = Connexion::getConnexion();

$newScore = intval($_POST["newScore"]);

// La requête qui permet de mettre à jour le nouveau score a atteindre
$bdd->prepare ("UPDATE reanimer SET score_target = :newScore WHERE prototype = 1");
$bdd->bind(':newScore',$newScore);
$bdd->execute();
$response["success"] = 1;
echo json_encode($response);
?>