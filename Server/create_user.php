<?php
 require_once 'db_connect.php';

// array for JSON response
$response = array();

if (isset($_POST['pseudo'])) {
	$pseudo = $_POST['pseudo'];


	$bdd = Connexion::getConnexion();

	$bdd->prepare ( "select * from user WHERE user_pseudo = :pseudo" );
    $bdd->bind(":pseudo",$pseudo);
    $bdd->execute();
    $result = $bdd->fetch();
    if(empty($result)){

		$bdd->prepare ( "INSERT into user(user_pseudo) VALUES (:pseudo)" );
		$bdd->bind(':pseudo',$pseudo);
		$bdd->execute();
		$response["success"] = 1;
		echo json_encode($response);
	}
	else{
		$response["success"] = 0;
		echo json_encode($response);
	}
}
?>