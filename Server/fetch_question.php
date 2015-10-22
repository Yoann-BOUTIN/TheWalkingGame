<?php
    $response = array();
    $return["question"] = array();

    require_once 'db_connect.php';
    $db = Connexion::getConnexion();

    $db->prepare("SELECT * FROM QUESTION");
    $db->execute();

    $nbQuestion["rowcount"] = $db->rowCount();

    $random = rand(1,$nbQuestion["rowcount"]);
    $db->prepare("SELECT q.question_id,q.question_text,q.question_multiple_choice FROM QUESTION q WHERE question_id = :id");
    $db->bind(":id",$random);
    $db->execute();
    $results = $db->fetchAll();

    $response['question_id'] = $results[0]["question_id"];
    $response['question_text'] = utf8_encode($results[0]["question_text"]);
    $response['question_multiple_choice'] = $results[0]["question_multiple_choice"];

    array_push($return["question"],$response);
    echo json_encode($response);
?>