<?php
header('Content-Type: application/json');
include_once '../config/database.php';

$json = json_decode(file_get_contents('php://input'), true);

try {
    if (isset($json['username']) && isset($json['password'])) {
        $username = htmlspecialchars($json['username']);
        $password = htmlspecialchars($json['password']);
        
        $getUser = $bdd->prepare("SELECT mdp FROM joueur WHERE nom_utilisateur = ?");
        $getUser->execute(array($username));
        
        // Vérifier si le nom d'utilisateur existe
        if ($getUser->rowCount() > 0) {
            $user = $getUser->fetch();
            
            // Vérifier le mot de passe
            if ($password == $user['mdp']) {
                $result["success"] = true;
            } else {
                $result["success"] = false;
                $result["error"] = "Mot de passe incorrect";
            }        
        } else {
            $result["success"] = false;
            $result["error"] = "Ce nom d'utilisateur n'existe pas";
        }
    } else {
        $result["success"] = false;
        $result["error"] = "Champs non remplis";
    }
} catch (Exception $e) {
    // Ajoutez des informations sur l'exception dans le message d'erreur
    $result["success"] = false;
    $result["error"] = "Erreur lors de la vérification du compte : " . $e->getMessage();
}

echo json_encode($result);
?>
