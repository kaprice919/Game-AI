
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

import processing.core.PApplet;

class Graph {

	Hashtable<String, Node> nodes = new Hashtable<String, Node>();
	LinkedList<Edge> edges = new LinkedList<Edge>();
	LinkedList<Node> nodeList = new LinkedList<Node>();
	Random rand = new Random();

	int nodesProcessed = 0;
	int part4RANGE = 5;

	// Generate whatever you want here
	void generateSimpleGraph() {
		this.createDoubleEdge("bottom middle", "bottom left", 200, 285, 53, 308, 5);
		this.createDoubleEdge("bottom middle", "bottom right", 200, 285, 334, 293, 5);
		this.createDoubleEdge("bottom left", "middle left", 53, 308, 62, 190, 5);
		this.createDoubleEdge("middle left", "top left", 62, 190, 62, 75, 5);
		this.createDoubleEdge("top left", "top middle", 62, 75, 200, 75, 5);
		this.createDoubleEdge("top middle", "top right", 200, 75, 334, 75, 5);
		this.createDoubleEdge("middle right", "top right", 334, 190, 334, 75, 5);
		this.createDoubleEdge("middle right", "bottom right", 334, 190, 334, 75, 5);
		
	}



	void drawPath(PApplet parent, LinkedList<Edge> path) {
		for (Edge edge : path) {
			parent.stroke(255, 0, 0);
			parent.fill(100, 0, 0);
			parent.ellipse(edge.fromNode.x, edge.fromNode.y, 10, 10);
			parent.ellipse(edge.toNode.x, edge.toNode.y, 10, 10);
			parent.line(edge.fromNode.x, edge.fromNode.y, edge.toNode.x, edge.toNode.y);
		}
	}

	LinkedList<Edge> dijkstra(Node start, Node goal) {

		NodeRecord startRecord = new NodeRecord();
		NodeRecord endNodeRecord;
		NodeRecord current = new NodeRecord();
		LinkedList<Edge> path = new LinkedList<Edge>();
		startRecord.node = start;
		startRecord.connection = null;
		startRecord.costSoFar = 0;

		PathfindingList openList = new PathfindingList();
		openList.add(startRecord);
		PathfindingList closedList = new PathfindingList();

		while (openList.list.size() > 0) {
			current = openList.smallestElement();

			if (current.node.equals(goal))
				break;

			LinkedList<Edge> connections = getNeighbors(current.node);

			for (Edge connection : connections) {
				nodesProcessed++;
				Node endNode = connection.toNode;
				int endNodeCost = current.costSoFar + connection.weight;

				if (closedList.contains(endNode)) {
					continue;
				} else if (openList.contains(endNode)) {
					endNodeRecord = openList.find(endNode);
					if (endNodeRecord.costSoFar <= endNodeCost)
						continue;
				} else {
					endNodeRecord = new NodeRecord();
					endNodeRecord.node = endNode;
				}

				endNodeRecord.costSoFar = endNodeCost;
				endNodeRecord.connection = connection;

				if (!openList.contains(endNode))
					openList.add(endNodeRecord);
			}
			openList.remove(current);
			closedList.add(current);
		}

		if (!current.node.equals(goal))
			System.out.println("No sol'n :( sorry ");
		else {
			if(current.node.equals(start)){
				path.add(current.connection);
			}
			while (!current.node.equals(start)) {
				path.add(current.connection);

				current = closedList.find(current.connection.fromNode);
			}
		}
		path = reverse(path);

		return path;
	}

	class Heuristic {
		Node goal;

		Heuristic(Node goal) {
			this.goal = goal;
		}

		int constantEstimate(Node node) {
			return 5;
		}

		int euclidianDistance(Node node) {
			// Euclidian
			return (int) Math.sqrt(Math.pow(goal.x - node.x, 2) + Math.pow(goal.y - node.y, 2));
			// Constant
			// return 1;
		}
	}

	LinkedList<Edge> Astar(Node start, Node goal) {
		NodeRecord startRecord = new NodeRecord();
		NodeRecord endNodeRecord;
		NodeRecord current = new NodeRecord();
		LinkedList<Edge> path = new LinkedList<Edge>();
		Heuristic heuristic = new Heuristic(goal);
		int endNodeHeuristic = 0;
		startRecord.node = start;
		startRecord.connection = null;
		startRecord.costSoFar = 0;
		startRecord.estimatedTotalCost = heuristic.euclidianDistance(start);

		PathfindingList openList = new PathfindingList();
		openList.add(startRecord);
		PathfindingList closedList = new PathfindingList();

		while (openList.list.size() > 0) {
			current = openList.smallestElement();

			if (current.node.equals(goal))
				break;

			LinkedList<Edge> connections = getNeighbors(current.node);

			for (Edge connection : connections) {
				nodesProcessed++;
				Node endNode = connection.toNode;
				int endNodeCost = current.costSoFar + connection.weight;

				if (closedList.contains(endNode)) {
					endNodeRecord = closedList.find(endNode);
					if (endNodeRecord.costSoFar <= endNodeCost)
						continue;
					closedList.remove(endNodeRecord);
					endNodeHeuristic = endNodeRecord.cost - endNodeRecord.costSoFar;
				} else if (openList.contains(endNode)) {
					endNodeRecord = openList.find(endNode);
					if (endNodeRecord.costSoFar <= endNodeCost)
						continue;
					endNodeHeuristic = endNodeRecord.cost - endNodeRecord.costSoFar;
				} else {
					endNodeRecord = new NodeRecord();
					endNodeRecord.node = endNode;
					endNodeHeuristic = heuristic.euclidianDistance(endNode);
				}

				endNodeRecord.costSoFar = endNodeCost;
				endNodeRecord.connection = connection;
				endNodeRecord.estimatedTotalCost = endNodeCost + endNodeHeuristic;

				if (!openList.contains(endNode))
					openList.add(endNodeRecord);
			}
			openList.remove(current);
			closedList.add(current);
		}

		if (!current.node.equals(goal))
			System.out.println("No sol'n :( sorry ");
		else {
			while (!current.node.equals(start)) {
				path.add(current.connection);

				current = closedList.find(current.connection.fromNode);
			}
		}
		path = reverse(path);

		return path;
	}

	void printPath(LinkedList<Edge> path) {
		for (Edge edge : path)
			System.out.println("From: " + edge.fromNode.name + "  To: " + edge.toNode.name);
	}

	LinkedList<Edge> reverse(LinkedList<Edge> path) {
		Stack<Edge> stack = new Stack<Edge>();
		LinkedList<Edge> soln = new LinkedList<Edge>();
		for (Edge edge : path) {
			stack.push(edge);
		}
		while (stack.size() > 0)
			soln.add(stack.pop());
		return soln;
	}

	Node search(String name) {
		return nodes.get(name);
	}

	void createDoubleEdge(String node1name, String node2name, int x1, int y1, int x2, int y2, int weight) {
		Node node1, node2;
		if (!nodes.containsKey(node1name)) {
			node1 = new Node(x1, y1, node1name);
			nodes.put(node1name, node1);
			nodeList.add(node1);
		} else {
			node1 = nodes.get(node1name);
		}
		if (!nodes.containsKey(node2name)) {
			node2 = new Node(x2, y2, node2name);
			nodes.put(node2name, node2);
			nodeList.add(node2);
		} else {
			node2 = nodes.get(node2name);
		}

		// Create the edge with your 2 nodes
		Edge edge = new Edge();
		edge.fromNode = node1;
		edge.toNode = node2;
		edge.weight = weight;

		node1.neighbors.add(edge);
		edges.add(edge);

		// Create a double in the reverse
		Edge edge2 = new Edge();
		edge2.fromNode = node2;
		edge2.toNode = node1;
		edge2.weight = weight;

		node2.neighbors.add(edge2);
		edges.add(edge2);
	}

	class NodeRecord {
		Node node;
		Edge connection;
		int cost = 0;
		int costSoFar = Integer.MAX_VALUE;
		int estimatedTotalCost = 0;
	}

	LinkedList<Edge> getNeighbors(Node node) {
		return node.neighbors;
	}

	class Node {
		int x, y;
		String name;
		LinkedList<Edge> neighbors = new LinkedList<Edge>();

		public Node(int x, int y, String name) {
			this.name = name;
			this.x = x;
			this.y = y;
		}
	}

	static class Edge {
		Node toNode, fromNode;
		int weight;
	}

	class PathfindingList {
		// Implementation of open and closed data structures here will result in
		// different performance
		LinkedList<NodeRecord> list = new LinkedList<NodeRecord>();

		void add(NodeRecord nodeRecord) {
			list.add(nodeRecord);
		}

		void remove(NodeRecord nodeRecord) {
			list.remove(nodeRecord);
		}

		NodeRecord smallestElement() {
			NodeRecord smallest = new NodeRecord();
			smallest.costSoFar = Integer.MAX_VALUE;
			for (NodeRecord node : list) {
				if (node.costSoFar < smallest.costSoFar)
					smallest = node;
			}
			return smallest;
		}

		boolean contains(Node node) {
			for (NodeRecord nodeRecord : list) {
				if (nodeRecord.node.equals(node))
					return true;
			}
			return false;
		}

		NodeRecord find(Node node) {
			for (NodeRecord nodeRecord : list) {
				if (nodeRecord.node.equals(node))
					return nodeRecord;
			}
			return null;
		}
	}

	public void drawGraph(PApplet parent) {
		for (Edge edge : edges) {
			parent.stroke(0);
			parent.line(edge.toNode.x, edge.toNode.y, edge.fromNode.x, edge.fromNode.y);
		}
		for (Node node : nodeList) {
			parent.fill(0);
			parent.ellipse(node.x, node.y, 10, 10);
		}
	}
}