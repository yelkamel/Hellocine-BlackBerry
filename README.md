# JAVA_BlackBerry_Hellocine
BlackBerry Project talking with an API (xml parsing)

Hellocine is a JAVA application on BlackBerry OS (compatible with version 1.0) which interact with a web service (I parse XML). 
The user can have the GPS, phone and mail contact cinema. 
It can also add favorite cinemas and film book via the website. 
The application can be used without internet.

# TODO traduction
-- FRENCH PART --
INTRODUCTION

HelloCiné est le leader parisien dans le domaine des réservations de place de cinéma. Nous disposons de 15 cinémas partenaires sur Paris. Suite à la récente explosion des Smartphones et Tablettes, le groupe HelloCiné a décidé de se lancer dans l’acquisition d’applications mobiles pour développer sa notoriété et générer du trafic.
Principales demandes : 
➢	Une application pour l’une des plateformes suivantes devra être développée :
o	IPhone / iPad (iOS 5 minimum)
o	Android (Android 4.0)
o	Windows Phone 7.5 / Windows 8 RT
➢	Chaque application devra respecter et s’adapter aux contraintes ergonomiques de la plateforme cible.
➢	Le contenu consulté en étant connecté devra être consultable en étant hors ligne. 
STRUCTURE DE L’APPLICATION MOBILES

Chaque application mobile devra proposer à l’utilisateur les fonctionnalités décrites dans cette partie.
LES CINEMAS

L’utilisateur pourra consulter l’ensemble des cinémas partenaires et aura accès aux informations suivantes pour chaque cinéma :
•	Adresse
•	Téléphone
•	Liste des films proposés par le cinéma
•	Email
•	Horaires
En cliquant sur l’adresse d’un cinéma, une carte s’affichera et proposera à l’utilisateur un itinéraire pour s’y rendre. De plus il aura la possibilité de créer une liste de cinémas favoris. Cette liste sera stockée sur le téléphone. Enfin, l’utilisateur pourra lancer un appel ou la rédaction d’un email afin de contacter le cinéma directement depuis l’application
LES FILMS

L’utilisateur pourra consulter l’ensemble des films proposés par les partenaires d’HelloCine et aura accès aux informations suivantes pour chaque film :
•	Nom du film
•	Durée
•	Liste des cinémas proposant ce film
DETAILS D’UN FILM
L’utilisateur pourra consulter les détails d’un film. Il aura accès aux informations suivantes :
•	Les séances du film
•	L’heure de début de la séance (timestamp)
•	L’heure de fin de la séance (timestamp)
•	Le nombre de places déjà réservées pour la séance
•	Le nombre de places disponibles au maximum pour la séance
•	Le nom de la salle dans laquelle se déroule la séance
•	Le nom du cinéma dans lequel se trouve la séance
•	Nom du film
•	Durée

LES RESERVATIONS

L’utilisateur pourra réserver une place de cinéma pour une séance donnée. 
Il devra être connecté pour pouvoir réserver une séance. (cf webservices)
Un utilisateur ne pourra pas réserver plusieurs places. De plus, il n’existe sur le serveur aucune gestion du dépassement de réservations par rapport au nombre de places disponibles pour la séance. Cette vérification devra donc être effectuée par l’application.
LE PLANNING

L’utilisateur pourra accéder à un planning listant toutes ses réservations.
LE PROFIL

L’utilisateur pourra se créer un profil en renseignant les informations suivantes :
•	Login
•	Mot de passe
Il pourra partager sa réservation pour une séance via les réseaux sociaux (Twitter, Facebook) s’il le souhaite.
 
CHARTE GRAPHIQUE
Les applications mobiles devront respecter une charte graphique qui vous sera envoyée par mail.

WEBSERVICES

Les contenus d’HelloCine sont récupérés de façon dynamique en utilisant des requêtes Http et retournant des flux XML. HelloCine met à disposition des URLs spécifiques pour les différentes fonctionnalités des applications mobiles. Les paramètres sont transmis via la méthode GET (directement injectée dans l’url).

LES CINEMAS

Adresse : http://hellocine.3ie.fr/Webservices/get_cinemas
Format du XML :
<?xml version="1.0" encoding="utf8"?>
<cinemas>
  <cinema>
    <cinema_id>1</cinema_id>
    <cinema_name><![CDATA[UGCGobelins]]></cinema_name>
    <longitude>0</longitude>
    <latitude>0</latitude>
    <hours><![CDATA[8h-18h]]></hours>
    <mail><![CDATA[adresse@serveur.com]]></mail>
    <phone>0101010101</phone>
    <address><![CDATA[Les Gobelins Paris]]></address>
    <films>
      <film>
        <film_id>1</film_id>
        <film_name><![CDATA[La classe américaine]]></film_name>
        <duration><![CDATA[2h00]]></duration>
      </film>
    </films>
  </cinema>
</cinemas> 

