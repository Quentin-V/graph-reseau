package ovh.quinta.reseau.metier;

import org.graphstream.graph.Node;

/**
 * Classe machine représente les machines du réseau, Routeur ou Ordinateur
 */
public class Machine implements Comparable<Machine> {
	/**
	 * Le nom de la machine
	 */
	String name;

	/**
	 * Noeud du graph que représente la machine
	 */
	Node node;

	/**
	 * Constructeur de la machine
	 * @param name le nom de la machine
	 */
	public Machine(String name) {
		this.name = name;
	}

	/**
	 * Constructeur avec nom et noeud en paramètre
	 * @param name le nom de la machine
	 * @param n le noeud que représente la machine
	 */
	public Machine(String name, Node n) {
		this(name);
		node = n;
	}

	/**
	 * Getter du noeud que représente la machine
	 * @return Noeud représenté par la machine
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * Setter du noeud que représente la machine
	 * @param n Le noeud que la machine doit représenter
	 */
	public void setNode(Node n) {
		node = n;
	}

	/**
	 * Getter du nom de la machine
	 * @return Nom de la machine
	 */
	public String getName() {
		return name;
	}

	/**
	 * Tostring renvoyant le nom de la machine
	 * @return le nom de la machine
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * CompareTo qui permet de comparer les machines en fonction de leur nom
	 * @param m la machine à comparer
	 * @return la comparaison du nom des deux machines
	 */
	@Override
	public int compareTo(Machine m) {
		return this.name.compareTo(m.name);
	}
}
