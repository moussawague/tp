<?php
header('Content-Type: application/json');

$conn = new mysqli("localhost", "root", "root", "powerhome");

if ($conn->connect_error) {
    echo json_encode(["error" => "Database connection failed"]);
    exit();
}

// ✅ IMPORTANT POUR LES ACCENTS
$conn->set_charset("utf8");

$sql = "SELECT * FROM timeslot";
$result = $conn->query($sql);

if ($result) {
    $resident = $result->fetch_all(MYSQLI_ASSOC);
    echo json_encode($resident);
} else {
    echo json_encode(["error" => "Query failed"]);
}

$conn->close();
?>