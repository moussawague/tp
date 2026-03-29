<?php
header('Content-Type: application/json');

$conn = new mysqli("localhost", "root", "root", "powerhome");

if ($conn->connect_error) {
    echo json_encode(["error" => "Database connection failed"]);
    exit();
}

$conn->set_charset("utf8");

if (isset($_POST['id'], $_POST['firstname'], $_POST['lastname'], $_POST['email'])) {

    $id = intval($_POST['id']);
    $firstname = $_POST['firstname'];
    $lastname = $_POST['lastname'];
    $email = $_POST['email'];

    $stmt = $conn->prepare("UPDATE resident SET firstname = ?, lastname = ?, email = ? WHERE id = ?");
    $stmt->bind_param("sssi", $firstname, $lastname, $email, $id);

    if ($stmt->execute()) {
        echo json_encode(["success" => true]);
    } else {
        echo json_encode(["error" => "Update failed"]);
    }

} else {
    echo json_encode(["error" => "Missing parameters"]);
}

$conn->close();
?>