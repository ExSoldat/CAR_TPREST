|*******************************************|
|				SAAB Mathieu				|
|  TP de Conception d'Application Réparties	|
|  Application de gestion de fichiers REST  |
|*******************************************|		

|------- Lancer l'application : 
Afin de lancer l'application (server&frontend), il faut le faire à l'aide de maven en se positionnant sur le dossier racine "CAR_TPREST" et executer la commande suivante :

mvn clean install

Puis se positionner dans le sous dossier "backend" et executer la commande suivante :

mvn spring-boot:run

Enfin, il faut ouvrir un navigateur internet et taper l'url http://localhost:8080

|------- Utiliser l'application :

	|--- Se connecter à l'application : un compte avec l'username seinturierl et le mot de passe azerty aura été créé au préalable auquel vous pourrez vous connecter en appuyant sur le mais vous pourrez en créer un nouveau en cliquant sur le bouton 'Sign in'
		/ Erreurs gérées :
			- Le renseignement d'un mauvais mot de passe
			- Le renseignement d'un mauvais d'utilisateur/utilisateur inconnu

	|--- Créer un dossier : en cliquant sur le bouton représenté par un dossier avec un signe plus dessus, vous aurez accès à une modale de création de dossier qui vous demandera de renseigner un nom de dossier qui sera crée dans le dossier dans lequel on navigue actuellement. En confirmant le nom la modale se ferme et l'arborescence recharge. 
		/ Erreurs gérées :
			- Le cas où un dossier n'a pu être créé

	|--- Naviguer dans l'arborescence : On peut se déplacer dans deux sens sur l'application, la première, descendante est possible en cliquant sur le nom d'un dossier, en cliquant dessus on 'entre' dans ce dossier. On peut alors demonter l'arborescence en cliquant sur les liens présents dans la toolbar
		/ Erreurs gérées :
			- Déplacement dans un fichier qui n'existe pas

	|--- Uploader un fichier : Afin d'uploader un fichier il faut cliquer sur l'icône d'upload à gauche de l'icône de création de fichier. Vous aurez alors accès à une modale vous permettant de choisir un fichier à envoyer sur le serveur. Attention, la taille maximale d'un fichier est de 128kb


	|--- Supprimer un dossier/fichier : Pour supprimer un dossier vide ou un fichier, il faut cliquer sur la croix rouge située à droite d'un dossier ou d'un fichier, si la suppression est possible, le fichier sera donc supprimé sur le serveur
		/ Erreurs gérées :
			- Cas où l'on essaie de supprimer un dossier non vide
			- Cas où l'on essaie de supprimer un dossier n'existant pas

	|--- Déplacer un fichier : Pour déplacer un fichier, il faut faire un drag'n'drop du fichier/dossier qu'on veut déplacer vers le dossier dans lequel on le veut pour un déplacement descendant. Pour un déplacement ascendant, il faut déposer le fichier/dossier dans la barre de navigation (la toolbar)
		/ Erreurs gérées :
			- Cas où le déplacement est impossible

	|--- Télécharger un fichier : Pour télécharger un fichier, il faut cliquer sur l'icone download située à gauche de l'icône de suppression.

	|--- Se déconnecter de l'application : Pour se déconnecter de l'application, il faut cliquer sur l'icone power on/off tout en haut à droite.