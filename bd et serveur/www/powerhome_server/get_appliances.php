<?php
header('Content-Type: application/json');
include 'connexion.php';

if (isset($_GET['habitat_id'])) {
    $habitat_id = $_GET['habitat_id'];

    try {
        $sql = "SELECT id, name, reference, wattage, habitat_id 
                FROM appliance 
                WHERE habitat_id = :habitat_id";
        
        $stmt = $pdo->prepare($sql);
        $stmt->execute(['habitat_id' => $habitat_id]);
        $appliances = $stmt->fetchAll(PDO::FETCH_ASSOC);

        // On convertit les types pour Java (int/float)
        foreach ($appliances as &$a) {
            $a['id'] = intval($a['id']);
            $a['wattage'] = intval($a['wattage']);
            $a['habitat_id'] = intval($a['habitat_id']);
        }

        echo json_encode($appliances);
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode([]);
}
?>