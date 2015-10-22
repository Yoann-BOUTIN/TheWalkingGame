<?php
 
$response = array();

require_once 'db_connect.php';
$bdd = Connexion::getConnexion();

$prototype = intval($_GET["prototype"]);

$bdd->prepare ( "select * from reanimer WHERE prototype = :prototype" );
$bdd->bind(":prototype", $prototype);
$bdd->execute();
$result = $bdd->fetch();

$response["success"] = 1;
$response["timer"] = $result["temps_autorise"];
$response["score"] = $result["score_target"];

echo json_encode($response);

?>