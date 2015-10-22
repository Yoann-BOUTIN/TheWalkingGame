<?php
    require_once 'db_connect.php';
    $db = Connexion::getConnexion();
    if (isset($_GET["id"])) {
        $id = $_GET['id']; $response = array();
        $return['quiz'] = array();
        $db->prepare("SELECT a.answer_text,a.answer_id,a.correct_answer FROM ANSWER a WHERE question_id = :id");
        $db->bind(":id",intval($id)); $db->execute();
        $results = $db->fetchAll();

        $response['answer1_id'] = $results[0]["answer_id"];
        $response['answer1_text'] = utf8_encode($results[0]["answer_text"]);
        $response['answer1_correct'] = $results[0]["correct_answer"];

        $response['answer2_id'] = $results[1]["answer_id"];
        $response['answer2_text'] = utf8_encode($results[1]["answer_text"]);
        $response['answer2_correct'] = $results[1]["correct_answer"];

        $response['answer3_id'] = $results[2]["answer_id"];
        $response['answer3_text'] = utf8_encode($results[2]["answer_text"]);
        $response['answer3_correct'] = $results[2]["correct_answer"];

        $response['answer4_id'] = $results[3]["answer_id"];
        $response['answer4_text'] = utf8_encode($results[3]["answer_text"]);
        $response['answer4_correct'] = $results[3]["correct_answer"];

        array_push($return["quiz"],$response);
        echo json_encode($response);
    }
?>