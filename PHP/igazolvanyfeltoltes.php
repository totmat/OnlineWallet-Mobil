<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['documentId']) && isset($_POST['fullname']) && isset($_POST['gender']) && isset($_POST['year']) && isset($_POST['month']) && isset($_POST['day'])) {
    if ($db->dbConnect()) {
        if ($db->igazolvanyfeltoltes("igazolvany", $_POST['documentId'], $_POST['fullname'], $_POST['gender'], $_POST['year'], $_POST['month'], $_POST['day'])) {
            echo "ID upload Success";
        } else echo "ID upload Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
