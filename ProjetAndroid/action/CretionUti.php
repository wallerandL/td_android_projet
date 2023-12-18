<?php
header('Content-Type: application/json');
include_once '../config/database.php';
$json = json_decode(file_get_contents('php://input'), true);
if (isset($json['username']) && isset($json['password'])) {
    $username = htmlspecialchars($json['username']);
    $password = htmlspecialchars($json['password']);

    if ($username == "" || $password == "") {
        $result["success"] = false;
        $result["error"] = "Le nom d'utilisateur et/ou le mot de passe est vide";
    } else {
        $checkIfUsernameExists = $bdd->prepare('SELECT * FROM joueur WHERE nom_utilisateur = ?');
        $checkIfUsernameExists->execute(array($username));

        if ($checkIfUsernameExists->rowCount() > 0) {
            $result["success"] = false;
            $result["error"] = "Cet identifiant existe déjà";
        } else {
            try {
                $createAccount = $bdd->prepare("INSERT INTO `joueur` (`id_joueur`, `nom_utilisateur`, `mdp`) VALUES (NULL, ?, ?);");
                $createAccount->execute(array($username, $password));
                $result["success"] = true;
            } catch (Exception $e) {
                $result["success"] = false;
                $result["error"] = "Erreur lors de la création du compte : " . $e->getMessage();
            }
        }
    }
} else {
    $result["success"] = false;
    $result["error"] = "Veuillez compléter tous les champs";
}
echo json_encode($result);
?>
