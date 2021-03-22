package ovh.quinta.reseau.metier;

import org.graphstream.graph.Edge;

/**
 * Classe représentant une route dans le réseau
 */
public class Route {

	/**
	 * Style du edge dans le graph
	 */
	static final String style = "text-alignment: under; fill-color: white; text-color: white;";

	/**
	 * La première machine de la route
	 */
	Machine a;

	/**
	 * La deuxième machine de la route
	 */
	Machine b;

	/**
	 * La cout de la route
	 */
	int cost;

	/**
	 * Le edge du graph représenté par la route
	 */
	private Edge edge;

	/**
	 * Constructeur avec deux machines et un entier (cout)
	 * @param a la première machine
	 * @param b la deuxième machine
	 * @param cost le coût de la route
	 */
	public Route(Machine a, Machine b, int cost) {
		this.a = a;
		this.b = b;
		this.cost = cost;
		this.edge = null;
	}

	/**
	 * Constructeur de route avec deux machines, un cout et un edege du graph
	 * @param a la première machine
	 * @param b la deuxième machine
	 * @param cost le cout
	 * @param edge un edge du graph
	 */
	public Route(Machine a, Machine b, int cost, Edge edge) {
		this(a, b, cost);
		this.edge = edge;
		edge.setAttribute("length", cost);
		setStyle();
	}

	/**
	 * retourne la première machine de la route
	 * @return la machine A de la route
	 */
	public Machine getA() {
		return a;
	}

	/**
	 * retourne la deuxième machine de la route
	 * @return la machine B de la route
	 */
	public Machine getB() {
		return b;
	}

	/**
	 * Accesseur de l'edge représenté par le route
	 * @return l'edge représenté par la route
	 */
	public Edge getEdge() {
		return edge;
	}

	/**
	 * Accesseur du coût de la route
	 * @return le coût de la route
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Setter du edge représenté par la route
	 * @param edge un edge
	 */
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

	/**
	 * Recharge le style de l'edge représenté par la route
	 */
	private void setStyle() {
		edge.setAttribute("ui.label", cost);
		edge.setAttribute("ui.style", style);
	}
}
