<?php
header('Content-Type: application/json');

$conn = new mysqli("localhost", "root", "root", "powerhome");

if ($conn->connect_error) {
    echo json_encode(["error" => "Database connection failed"]);
    exit();
}

$conn->set_charset("utf8");

if (isset($_GET['id'])) {

    $id = intval($_GET['id']);

    $stmt = $conn->prepare("SELECT * FROM resident WHERE id = ?");
    $stmt->bind_param("i", $id);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result && $result->num_rows > 0) {
        echo json_encode($result->fetch_assoc());
    } else {
        echo json_encode(["error" => "User not found"]);
    }

} else {

    $result = $conn->query("SELECT * FROM resident");

    if ($result) {
        echo json_encode($result->fetch_all(MYSQLI_ASSOC));
    } else {
        echo json_encode(["error" => "Query failed"]);
    }
}

$conn->close();
?>