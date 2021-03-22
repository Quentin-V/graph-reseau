package ovh.quinta.reseau.metier;

import org.graphstream.algorithm.Dijkstra;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.Path;
import org.graphstream.graph.implementations.SingleGraph;
import ovh.quinta.reseau.Main;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Classe qui gère un réseau entre plusieurs machines qui peuvent être des Routeurs ou Ordinateurs
 * Permet de calculer les chemins les plus court entre les machines ainsi que leur tables de routage
 */
public class Reseau {

	/**
	 * Le main lié au réseau (Controler)
	 */
	private final Main main;

	/**
	 * La liste des routeurs du réseau
	 */
	private final ArrayList<Routeur> routeurs;

	/**
	 * La liste des ordinateurs du réseau
	 */
	private final ArrayList<Ordinateur> ordinateurs;

	/**
	 * La liste des routes du réseau
	 */
	private final ArrayList<Route> routes;

	/**
	 * Le graph représentant le réseau
	 */
	Graph graph;

	/**
	 * Constructeur de la classe Réseau, initialise le réseau et affiche le graph
	 * @param main L'instance du main
	 */
	public Reseau(Main main) {
		// Affectation du main
		this.main = main;

		// Instanciation des listes
		routeurs = new ArrayList<>();
		ordinateurs = new ArrayList<>();
		routes = new ArrayList<>();

		// Affichage du graphique
		System.setProperty("org.graphstream.ui", "swing");
		graph = new SingleGraph("Réseau");
		graph.setAttribute("ui.stylesheet", "graph {fill-color: #444;} ");
		graph.display();
	}

	/**
	 * Retourne un routeur en fonction d'un id
	 * @param id L'id voulu
	 * @return Le routeur associé à l'id voulu ou null s'il nexiste pas
	 */
	public Routeur getRouteur(String id) {
		return routeurs.stream().filter(r -> r.name.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Accesseur de routeurs
	 * @return l'arraylist des routeurs
	 */
	public ArrayList<Routeur> getRouteurs() {
		return routeurs;
	}

	/**
	 * Retourne un ordinateur en fonction d'un id
	 * @param id L'id voulu
	 * @return L'ordinateur associé à l'id voulu ou null s'il nexiste pas
	 */
	public Ordinateur getOrdinateur(String id) {
		return ordinateurs.stream().filter(o -> o.name.equals(id)).findFirst().orElse(null);
	}

	/**
	 * Accesseur de ordinateurs
	 * @return l'arraylist des ordinateurs
	 */
	public ArrayList<Ordinateur> getOrdinateurs() {
		return ordinateurs;
	}

	/**
	 * Trouve une route entre 2 machines
	 * @param a Une machine
	 * @param b Une autre machine
	 * @return Une Route entre a et b
	 */
	public Route getRoute(Machine a, Machine b) {
		return routes.stream().filter(r -> r.getA().equals(a) && r.getB().equals(b) || r.getA().equals(b) && r.getB().equals(a)).findFirst().orElse(null);
	}

	/**
	 * Retourne une route qui passe par deux noeuds
	 * @param a Un noeud de la route voulue
	 * @param b L'autre noeud de la route voulue
	 * @return Une Route entre a et b
	 */
	public Route getRoute(Node a, Node b) {
		return routes.stream().filter(r -> r.getA().node.equals(a) && r.getB().node.equals(b) || r.getA().node.equals(b) && r.getB().node.equals(a)).findFirst().orElse(null);
	}

	/**
	 * Retourne la liste de toutes les routes du réseau
	 * @return une liste comportant toutes les routes du réseau
	 */
	public ArrayList<Route> getRoutes() {
		return routes;
	}

	/**
	 * Retourne toutes les routes qui partent d'une machine
	 * @param machine La machine à partir de laquelle on veut les routes
	 * @return Une liste de routes partant de la machine
	 */
	public ArrayList<Route> getRoutesFrom(Machine machine) {
		return this.routes.stream().filter(r -> r.getA().equals(machine) || r.getB().equals(machine)).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Retourne une machine à partir de son id
	 * @param id L'id de la machine voulue
	 * @return La machine qui a comme id l'id voulu ou null
	 */
	public Machine getMachine(String id) {
		Machine machine;

		machine = getRouteur(id);
		machine = machine == null ? getOrdinateur(id) : machine;

		return machine;
	}

	/**
	 * Retourne une machine à partir d'un Node
	 * @param node Le Node associé à la machine voulue
	 * @return La machine associée au Node ou null si elle n'existe pas
	 */
	public Machine getMachineFromNode(Node node) {
		ArrayList<Machine> machines = new ArrayList<>();
		machines.addAll(routeurs);
		machines.addAll(ordinateurs);
		return machines.stream().filter(m -> m.node.equals(node)).findFirst().orElse(null);
	}

	/**
	 * Retourne une liste comportant toutes les machines
	 * @return Une arraylist comportant toutes les machines du réseau
	 */
	public ArrayList<Machine> getMachines() {
		ArrayList<Machine> machines = new ArrayList<>();
		machines.addAll(routeurs);
		machines.addAll(ordinateurs);
		return machines;
	}

	/**
	 * Retourne une copie de la liste des machines du réseau
	 * @return une copie de la liste des machines du réseau
	 */
	public ArrayList<Machine> getMachinesCopy() {
		return new ArrayList<>(getMachines());
	}

	/**
	 * Ajoute un routeur dans le réseau
	 * @param r Le routeur à ajouter
	 */
	public void addRouteur(Routeur r) {
		Node rNode = graph.addNode(r.name);
		routeurs.add(r);
		r.setNode(rNode);
		r.setStyle();
	}

	/**
	 * Ajoute un routeur au réseau à partir d'un nom
	 * @param name le nom du routeur à ajouter
	 */
	public void addRouteur(String name) {
		if(getRouteurs().stream().map(Routeur::toString).anyMatch(s -> s.equals(name))) {
			main.errorMessage("Un routeur avec ce nom existe déjà");
			return;
		}
		addRouteur(new Routeur(name));
	}

	/**
	 * Ajoute un ordinateur dans le réseau
	 * @param o L'ordinateur à ajouter
	 */
	public void addOrdinateur(Ordinateur o) {
		ordinateurs.add(o);
		Node oNode = graph.addNode(o.name);
		o.setNode(oNode);
		o.setStyle();
	}

	/**
	 * Ajoute un ordinateur au réseau à partir d'un nom
	 * @param name le nom de l'ordinateur à ajouter
	 */
	public void addOrdinateur(String name) {
		if(getOrdinateurs().stream().map(Ordinateur::toString).anyMatch(s -> s.equals(name))) {
			main.errorMessage("Un ordinateur avec ce nom existe déjà");
			return;
		}
		addOrdinateur(new Ordinateur(name));
	}

	/**
	 * Ajoute une route entre deux machines avec un certain cout
	 * @param a    La machine source
	 * @param b    La machine destination
	 * @param cost Le cout de la route entre les deux machines
	 */
	public void addRoute(Machine a, Machine b, int cost) {
		if(a instanceof Ordinateur && b instanceof Ordinateur) return; // A et B tous 2 ordinateur
		if(a instanceof Ordinateur && !getRoutesFrom(a).isEmpty() || b instanceof Ordinateur && !getRoutesFrom(b).isEmpty())
			return; // A ou B est un ordinateur déjà connecté
		Edge e = graph.addEdge(a.name + "-" + b.name, a.name, b.name);
		routes.add(new Route(a, b, cost, e));
	}

	/**
	 * Ajoute ou supprime une route à partir du nom de deux machines et d'un coût si l'action est l'ajout d'une route
	 * @param a le nom de la première machine
	 * @param b le nom de la deuxième machine
	 * @param cost le coût de la route à ajouter peut être vide et sera 1 par défaut
	 */
	public void addDelRoute(String a, String b, String cost) {
		Machine mA, mB;
		mA = getMachine(a);
		mB = getMachine(b);
		if(mA instanceof Ordinateur && mB instanceof Ordinateur) {
			main.errorMessage("Connexion impossible entre deux ordinateurs");
			return;
		}
		Route r = getRoute(mA, mB);
		if(r != null) {
			graph.removeEdge(r.getEdge());
			removeRoute(r);
		}else {
			if(mA instanceof Ordinateur && !getRoutesFrom(mA).isEmpty() || mB instanceof Ordinateur && !getRoutesFrom(mB).isEmpty()) {
				main.errorMessage("Une connexion est déjà présente sur l'ordinateur, supprimez la avant d'en recréer une");
			}
			addRoute(mA, mB, cost.length() > 0 ? Integer.parseInt(cost) : 1);
		}
	}

	/**
	 * Supprime une route de la liste des routes
	 * @param r la route à supprimer
	 */
	public void removeRoute(Route r) {
		routes.remove(r);
	}

	/**
	 * Supprime une machine dans le réseau à partir de son nom
	 * @param machineName le nom de la machine à supprimer
	 */
	public void delMachine(String machineName) {
		Machine m = getMachine(machineName);
		if(m != null) {
			if(m instanceof Routeur) {
				getRouteurs().remove(m);
				getRoutesFrom(m).forEach(r -> {
					graph.removeEdge(r.getEdge());
					getRoutes().remove(r);
				});
			}else if(m instanceof Ordinateur) {
				getOrdinateurs().remove(m);
				getRoutesFrom(m).forEach(r -> {
					graph.removeEdge(r.getEdge());
					getRoutes().remove(r);
				});
			}
			graph.removeNode(m.getNode());
		}else {
			main.errorMessage("Impossible de trouver une machine avec ce nom");
		}
	}

	/**
	 * Colore des Edge du graphique en vert
	 * @param edges Liste des Edge à colorer
	 */
	public void colorerRoutes(ArrayList<Edge> edges) {
		for(Edge e : edges) {
			e.setAttribute("ui.style", "size: 2px; fill-color: green;");
		}
	}

	/**
	 * Colore des Edge du graphique en vert
	 * @param routes Liste des routes dont les Edge sont à colorer
	 */
	public void colorerRoute(ArrayList<Route> routes) {
		for(Route r : routes) {
			r.getEdge().setAttribute("ui.style", "size: 2px; fill-color: green;");
		}
	}

	/**
	 * Retourne le chemin le plus court entre deux machines
	 * @param a La machine source
	 * @param b La machine destination
	 * @return Le chemin le plus court entre les deux machines
	 */
	public Path shortestPath(Machine a, Machine b) {
		assert a != null && b != null;
		Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
		dijkstra.init(graph);
		dijkstra.setSource(a.getNode());
		dijkstra.compute();
		return dijkstra.getPath(b.getNode());
	}

	/**
	 * Retourne le chemin le plus court entre deux noeuds du graphique
	 * @param a Le noeud source
	 * @param b Le noeud de destination
	 * @return Le chemin le plus court entre les deux noeuds
	 */
	public Path shortestPath(Node a, Node b) {
		assert a != null && b != null;
		Dijkstra dijkstra = new Dijkstra(Dijkstra.Element.EDGE, null, "length");
		dijkstra.init(graph);
		dijkstra.setSource(a);
		dijkstra.compute();
		return dijkstra.getPath(b);
	}

	/**
	 * Calcule le plus court chemin entre deux machines et le colore en vert sur le graphique
	 * @param a La machine source
	 * @param b La machine destination
	 */
	public void afficherPlusCourtChemin(String a, String b) {
		resetStyle(); // Remet le style des edges du graph à celui de base
		Machine mA, mB;
		mA = getMachine(a);
		mB = getMachine(b);
		// Récupère le chemin le plus court entre les deux machines et colore les edges du chemin en vert
		Path shortest = shortestPath(mA, mB);
		ArrayList<Edge> shortestEdges = shortest.edges().collect(Collectors.toCollection(ArrayList<Edge>::new));
		colorerRoutes(shortestEdges);
	}

	/**
	 * Calcule la table de routage d'une machine sous forme
	 *
	 * @param machineName Le nom de la machine dont on veut la table de routage
	 * @return La table de routage de la machine demandée sous forme d'une HashMap
	 */
	public Map<Machine, ArrayList<HashMap<Machine, Integer>>> tableRoutage(String machineName) {
		// Crée la structure qui représente la table de routage
		HashMap<Machine, ArrayList<HashMap<Machine, Integer>>> table = new HashMap<>();
		// On récupère la machine à partir de laquelle on veut faire la table de routage
		Machine machine = getMachine(machineName);
		// On récupère les voisins de la machine
		Stream<Node> neighbors = machine.node.neighborNodes();
		// Pour chaque machine
		neighbors.forEach(neigh -> {
			// On récupère le coût entre la machine et le voisin
			int neighToMachineCost = getRoute(machine.node, neigh).getCost();
			// Pour chaque machine du graphique
			for(Machine m : getMachines()) {
				// On passe la boucle si on est sur la machine de la table
				if(m.equals(machine)) continue;
				// On récupère le chemin le plus court entre le voisin et chaque machine
				Path shortest = shortestPath(neigh, m.node);
				// On passe si le chemin repasse par la machine de la table
				if(shortest.contains(machine.node)) continue;
				// On calcule le coût de la route totale entre le voisin et la machine du réseau
				int costFromNeigh = shortest.edges().mapToInt(e -> (int) e.getAttribute("length")).sum();
				// On ajoute la valeur trouvée à la table de routage, ou on crée la ligne
				if(table.containsKey(m)) {
					ArrayList<HashMap<Machine, Integer>> costs = table.get(m);
					HashMap<Machine, Integer> cost = new HashMap<>();
					cost.put(getMachineFromNode(neigh), neighToMachineCost + costFromNeigh);
					costs.add(cost);
				}else {
					table.put(m, new ArrayList<>());
					HashMap<Machine, Integer> cost = new HashMap<>();
					cost.put(getMachineFromNode(neigh), neighToMachineCost + costFromNeigh);
					table.get(m).add(cost);
				}
			}
		});
		// On trie chaque ligne de la table pour afficher les couts dans l'ordre croissant
		table.forEach((k, v) -> { // Pour trier les coûts du moins élevé au plus élevé
			table.get(k).sort(Comparator.comparingInt(h2 -> h2.values().stream().findFirst().get()));
		});
		// On retourne une treemap pour trier alphabétiquement les lignes de la table
		return new TreeMap<>(table); // Pour trier les clés par ordre alphabétique
	}

	/**
	 * Redéfinit le style des liens entre les machines au style de base
	 */
	private void resetStyle() {
		graph.edges().forEach(e -> e.setAttribute("ui.style", Route.style));
	}
}