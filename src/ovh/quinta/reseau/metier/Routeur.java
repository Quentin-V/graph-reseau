package ovh.quinta.reseau.metier;

/**
 * Classe Routeur qui représente un routeur dans le réseau
 */
public class Routeur extends Machine {

	/**
	 * Style du node du Routeur dans le graph
	 */
	static final String style = "size: 20px; shape: triangle; text-alignment: at-left; text-color: white; fill-color: white;";

	/**
	 * Constructeur similaire à celui de Machine
	 * @param name le nom du routeur
	 */
	public Routeur(String name) {
		super(name);
	}

	/**
	 * Applique le style personnalisé au noeuds du graph
	 */
	public void setStyle() {
		node.setAttribute("ui.label", name);
		node.setAttribute("ui.style", style);
	}
}
