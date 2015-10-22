<?php
 require_once 'db_connect.php';

// array for JSON response
$response = array();

// Si ce pseudo existe
if (isset($_POST['pseudo'])) {
	$pseudo = $_POST['pseudo'];
	$posLongitude = $_POST['posLongitude'];
	$posLatitude = $_POST['posLatitude'];


	$bdd = Connexion::getConnexion();

	$bdd->prepare ( "select * from user WHERE user_pseudo = :pseudo" );
    $bdd->bind(":pseudo",$pseudo);
    $bdd->execute();
    $result = $bdd->fetch();
    // Le joueur existe dans la bdd
    if(!empty($result)){
        // La requête qui permet de mettre à jour la position du joueur
        $bdd->prepare ("UPDATE user SET user_pos_longitude = :posLongitude, user_pos_latitude = :posLatitude WHERE user_pseudo = :pseudo");
		$bdd->bind(':pseudo',$pseudo);
		$bdd->bind(':posLongitude', $posLongitude);
		$bdd->bind(':posLatitude', $posLatitude);
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