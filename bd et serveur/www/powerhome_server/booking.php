<?php
header('Content-Type: application/json');
$conn = new mysqli("localhost", "root", "root", "powerhome");
$conn->set_charset("utf8");

// On récupère le booking + le wattage de l'appareil associé
$sql = "SELECT b.*, a.wattage FROM booking b 
        JOIN appliance a ON b.appliance_id = a.id";
$result = $conn->query($sql);

$bookings = [];
while ($row = $result->fetch_assoc()) {
    $bookings[] = [
        "order" => (int)$row['id'],
        "timeslot_id" => (int)$row['timeslot_id'],
        "appliance" => ["wattage" => (int)$row['wattage']]
    ];
}
echo json_encode($bookings);
$conn->close();
?>