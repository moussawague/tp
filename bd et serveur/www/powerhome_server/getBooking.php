<?php
header('Content-Type: application/json');

// 1. Connexion à la base de données
$host = "localhost";
$user = "root";
$pass = "root";
$dbname = "powerhome";

$conn = new mysqli($host, $user, $pass, $dbname);

if ($conn->connect_error) {
    die(json_encode(["error" => "Connexion échouée"]));
}

// 2. Récupération de l'ID utilisateur (ex: booking.php?user_id=1)
$userId = isset($_GET['user_id']) ? intval($_GET['user_id']) : 0;

if ($userId > 0) {
    // 3. Requête SQL corrigée selon ton fichier .sql
    // Jointure entre 'booking' et 'appliance' sur 'appliance_id'
    $sql = "SELECT 
                b.id AS booking_id, 
                b.bookedAt, 
                b.timeslot_id, 
                a.id AS appliance_id, 
                a.name AS appliance_name, 
                a.wattage 
            FROM booking b
            INNER JOIN appliance a ON b.appliance_id = a.id
            WHERE b.user_id = $userId";

    $result = $conn->query($sql);
    $bookings = array();

    if ($result && $result->num_rows > 0) {
        while($row = $result->fetch_assoc()) {
            // On structure le JSON pour qu'il corresponde à tes objets Java
            $item = [
                "bookedAt" => $row['bookedAt'],
                "timeslot_id" => intval($row['timeslot_id']),
                "appliance" => [
                    "id" => intval($row['appliance_id']),
                    "name" => $row['appliance_name'],
                    "wattage" => intval($row['wattage'])
                ]
            ];
            $bookings[] = $item;
        }
    }

    echo json_encode($bookings);

} else {
    echo json_encode(["error" => "ID utilisateur manquant ou invalide"]);
}

$conn->close();
?>