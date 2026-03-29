<?php
header('Content-Type: application/json');
include 'connexion.php'; // Assure-toi que ce fichier contient ta connexion PDO

if (isset($_GET['id'])) {
    $id = $_GET['id'];

    try {
        // On récupère le résident et les infos de son habitat lié
        $sql = "SELECT r.id, r.firstname, r.lastname, r.email, 
                       h.id as habitat_id, h.floor, h.area 
                FROM resident r 
                LEFT JOIN habitat h ON r.id = h.user_id 
                WHERE r.id = :id";
        
        $stmt = $pdo->prepare($sql);
        $stmt->execute(['id' => $id]);
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($row) {
            // On structure le tableau pour correspondre aux entités Java
            $response = [
                "id" => intval($row['id']),
                "firstname" => $row['firstname'],
                "lastname" => $row['lastname'],
                "email" => $row['email'],
                "habitat" => [
                    "id" => intval($row['habitat_id']),
                    "floor" => intval($row['floor']),
                    "area" => floatval($row['area'])
                ]
            ];
            echo json_encode($response);
        } else {
            echo json_encode(null);
        }
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
}
?>