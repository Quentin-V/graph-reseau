package ovh.quinta.reseau;

import java.io.*;
import java.util.ArrayList;

import org.json.*;
import ovh.quinta.reseau.metier.Machine;
import ovh.quinta.reseau.metier.Ordinateur;
import ovh.quinta.reseau.metier.Reseau;
import ovh.quinta.reseau.metier.Routeur;
import ovh.quinta.reseau.ui.ControlFrame;
import ovh.quinta.reseau.ui.TableRoutage;

/**
 * Classe principale qui lance le programme
 */
public class Main {
	/**
	 * Un attribut de la classe Réseau qui représente le réseau avec les Machines et les Routes
	 */
	private final Reseau reseau;
	/**
	 * Une fenêtre permettant de réaliser les actions de l'utilisateur
	 */
	private final ControlFrame controlFrame;

	/**
	 * Main du programme, crée une isntance de Main en utilisant le constructeur avec un fichier en paramètres
	 * @param args arguments du programme, non utilisés
	 */
	public static void main(String[] args) {
		new Main("/res/data.json");
	}

	/**
	 * Constructeur principal de la classe Main
	 * crée un Réseau et une ControlFrame
	 */
	public Main() {
		reseau = new Reseau(this);
		controlFrame = new ControlFrame(this);
	}

	/**
	 * Constructeur à partir d'un fichier Json
	 * @param fichier Le chemin du fichier
	 */
	public Main(String fichier) {
		this(); // Appel du constructeur de base pour instancier le Réseau et la ControlFrame
		InputStream is = getClass().getResourceAsStream(fichier); // Récupération de la ressource du fichier
		// Lecture du fichier data
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try {
		    while ((line = reader.readLine()) != null) {
		        stringBuilder.append(line);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}

		// Traitement du fichier en JSON
		JSONObject obj = null;
		try {
			obj = new JSONObject(stringBuilder.toString());
		}catch(JSONException jse) {
			jse.printStackTrace();
			System.exit(1);
		}
		JSONArray routeursArray = obj.getJSONArray("Routeurs");
		JSONArray ordinateursArray = obj.getJSONArray("Ordinateurs");
		JSONArray routesArray = obj.getJSONArray("Routes");

		// Création des machines et des routes présentes dans le fichier data
		// Lance une exception si il y a une erreur de syntaxe dans la fichier mais
		// effectuera tous les ajouts présents avant l'erreur de syntaxe
		for(Object s : routeursArray) {
			if(s instanceof String) {
				reseau.addRouteur(((String)s));
			}else throw new IllegalArgumentException("Le fichier n'a pas une syntaxe valide (Routeurs)");
		}
		for(Object s : ordinateursArray) {
			if(s instanceof String) {
				reseau.addOrdinateur(((String)s));
			}else throw new IllegalArgumentException("Le fichier n'a pas une syntaxe valide (Ordinateurs)");
		}
		for(Object route : routesArray) {
			if(!(route instanceof JSONArray)) throw new IllegalArgumentException("Le fichier n'a pas une syntaxe valide (Routes)");
			try {
				JSONArray jRoute = (JSONArray) route;
				String idA = (String)jRoute.get(0);
				String idB = (String)jRoute.get(1);
				Machine a,b;
				a = reseau.getMachine(idA);
				b = reseau.getMachine(idB);
				// Empêche la création de route entre deux ordinateurs
				if(a instanceof Ordinateur && b instanceof Ordinateur) throw new IllegalArgumentException("Impossible de créer une route entre 2 ordinateurs");
				assert a != null && b != null;
				int cost = jRoute.length() == 3 ? (int) jRoute.get(2) : 1;
				reseau.addRoute(a, b, cost);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		controlFrame.reload();
	}

	/**
	 * Retourne une copie de la liste des macihnes du réseau
	 * @return Une copie de la liste des macihnes dans le réseau
	 */
	public ArrayList<Machine> getMachines() {
		return reseau.getMachinesCopy();
	}

	/**
	 * Demande au réseau d'ajouter un routeur
	 * @param name le nom du routeur à ajouter
	 */
	public void addRouteur(String name) {
		reseau.addRouteur(name);
	}

	/**
	 * Demande au réseau d'ajouter un ordinateur
	 * @param name le nom de l'ordinateur à ajouter
	 */
	public void addOrdi(String name) {
		reseau.addOrdinateur(name);
	}

	/**
	 * Demande au réseau de supprimer une machine
	 * @param machineName le nom de la machine à supprimer
	 */
	public void delMachine(String machineName) {
		reseau.delMachine(machineName);
	}

	/**
	 * Demande au réseau de créer ou supprimer une route
	 * @param a Nom de la machine A
	 * @param b Nom de la machine B
	 * @param cost String du cout de la route pour l'ajout
	 */
	public void addDelRoute(String a, String b, String cost) {
		reseau.addDelRoute(a, b, cost);
	}

	/**
	 * Demande au réseau d'afficher le plus court chemin entre les deux machines
	 * @param a le nom de la machine A
	 * @param b le nom de la machine B
	 */
	public void plusCourtChemin(String a, String b) {
		reseau.afficherPlusCourtChemin(a, b);
	}

	/**
	 * Crée la table de routage de la machine `machine`
	 * @param machine le nom de la machine dont on veut créer la table
	 */
	public void tableRoutage(String machine) {
		new TableRoutage(reseau.tableRoutage(machine), machine);
	}

	/**
	 * Affiche un message d'erreur
	 * @param message le texte d'erreur à afficher
	 */
	public void errorMessage(String message) {
		controlFrame.errorMessage(message);
	}
}