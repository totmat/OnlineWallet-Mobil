<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['cardNumber']) && isset($_POST['date']) && isset($_POST['securityCode']) && isset($_POST['firstName']) && isset($_POST['lastName'])) {
    if ($db->dbConnect()) {
        if ($db->bankkartyafeltoltes("bankkartya", $_POST['cardNumber'], $_POST['date'], $_POST['securityCode'], $_POST['firstName'], $_POST['lastName'])) {
            echo "Card upload Success";
        } else echo "Card upload Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
