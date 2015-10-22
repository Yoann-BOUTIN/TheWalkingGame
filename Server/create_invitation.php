<?php
 require_once 'db_connect.php';

// array for JSON response
$response = array();

if (isset($_POST['pseudoInviteur']) && isset($_POST[('pseudoInvite')])){
	$pseudoInviteur = $_POST['pseudoInviteur'];
	$pseudoInvite = $_POST['pseudoInvite'];

	$bdd = Connexion::getConnexion();

    $bdd->prepare ( "INSERT into invitation(inviteur, invite) VALUES (:pseudoInviteur, :pseudoInvite)" );
	$bdd->bind(':pseudoInviteur', $pseudoInviteur);
	$bdd->bind(':pseudoInvite', $pseudoInvite);
	$bdd->execute();

	// On récupere les joueurs de l'equipe
    $bdd->prepare ( "SELECT joueur_1, joueur_2, joueur_3, joueur_4 FROM team WHERE joueur_1 = :joueur or joueur_2 = :joueur or joueur_3 = :joueur or joueur_4 = :joueur");
    $bdd->bind(":joueur", $pseudoInviteur);
    $bdd->execute();
    $listeJoueurs = $bdd->fetch();

	if($listeJoueurs["joueur_2"] == "Joueur manquant") {
        $bdd->prepare ( "UPDATE team SET joueur_2 = :joueur2 WHERE joueur_1 = :joueur1");
        $bdd->bind(":joueur1", $pseudoInviteur);
        $bdd->bind(":joueur2", "En attente de " . $pseudoInvite);
        $bdd->execute();
    } else if($listeJoueurs["joueur_3"] == "Joueur manquant") {
        $bdd->prepare ( "UPDATE team SET joueur_3 = :joueur3 WHERE joueur_1 = :joueur1");
        $bdd->bind(":joueur1", $pseudoInviteur);
        $bdd->bind(":joueur3", "En attente de " . $pseudoInvite);
        $bdd->execute();
    } else if($listeJoueurs["joueur_4"] == "Joueur manquant") {
        $bdd->prepare ( "UPDATE team SET joueur_4 = :joueur4 WHERE joueur_1 = :joueur1");
        $bdd->bind(":joueur1", $pseudoInviteur);
        $bdd->bind(":joueur4", "En attente de " . $pseudoInvite);
        $bdd->execute();
    }

	$response["success"] = 1;
	echo json_encode($response);
}

?>