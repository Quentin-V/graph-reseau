package ovh.quinta.reseau.metier;

/**
 * Classe Ordinateur qui représente un ordinateur dans le réseau, sous classe de Machine
 */
public class Ordinateur extends Machine {

	/**
	 * Style du node de l'Ordinateur dans le graph
	 */
	final static String style = "text-alignment: at-right; text-color: white; fill-color: white;";

	/**
	 * Constructeur similaire à celui de Machine
	 * @param name nom de l'ordinateur
	 */
	public Ordinateur(String name) {
		super(name);
	}

	/**
	 * Applique un style personnalisé au noeuds du graphique
	 */
	public void setStyle() {
		node.setAttribute("ui.label", name); // Affiche le nom de l'ordinateur
		// Définit la place du nom à droite du noeud, et définit la couleur du noeud à blanc
		node.setAttribute("ui.style", style);
	}
}
