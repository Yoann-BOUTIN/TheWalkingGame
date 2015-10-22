<?php
 
$response = array();

require_once 'db_connect.php';
$bdd = Connexion::getConnexion();

// check for post data
if (isset($_GET["user_pseudo"])) {
    $pseudo = $_GET['user_pseudo'];

    // get a pseudo from products table
    $bdd->prepare ( "select * from user WHERE user_pseudo = :pseudo" );
    $bdd->bind(":pseudo",$pseudo);
    $bdd->execute();
    $result = $bdd->fetch();

    $response["success"] = 1;
    $response["user_pseudo"] = $result["user_pseudo"];
    $response["user_score"] = $result["user_score"];
    $response["user_pos_longitude"] = $result["user_pos_longitude"];
    $response["user_pos_latitude"] = $result["user_pos_latitude"];

    echo json_encode($response);
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
 
    // echoing JSON response
    echo json_encode($response);
}


?>