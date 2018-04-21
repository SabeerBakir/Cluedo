package bots;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Arrays;

import gameengine.*;
import gameengine.Map;

@SuppressWarnings("unused")
public class Bot1 implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the board or the player objects
    // It may only inspect the state of the board and the player objects

    private Player player;
    private PlayersInfo playersInfo;
    private Map map;
    private Dice dice;
    private Log log;
    private Deck deck;
    
    private static class Graph{
    	
    	public static class Node{
    		
    		int index;
    		int x;
    		int y;
    		int type;	// 0 = hallway, 1 = door, 2 = room, 3 = trap door, 4 = other
    		int links;	// number of vertices from this node
    		ArrayList<Node> neighbours = new ArrayList<Node>();	// ArrayList of neighbouring nodes
    		
    		Node(int x, int y, int type){
    			this.index = this.index++;
    			this.x = x;
    			this.y = y;
    			if(type >= 0 && type < 5) this.type = type;
    			else this.type = 4;
    			this.links = 0;
    		}
    		
    		public void addNeighbour(Node curr) {
    			neighbours.add(curr);
    			links++;
    		}
    		
    		public Coordinates getColRow() {
    			return new Coordinates(y,x);
    		}
    		
    		public int getX() {
    			return x;
    		}
    		
    		public int getY() {
    			return y;
    		}
    		
    		public int getType() {
    			return type;
    		}
    		
    		public int getLinks() {
    			return links;
    		}
    		
    		public String toString() {
    			return String.valueOf(index);
    		}
    		
    	}
    	
    	private static int [][] tileType = new int[][] { // where 0 is a regular tile, 1 is a door tile, 2 are tiles which are not movable to, 3 are trap doors
            {2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 3, 2, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 2, 2, 2, 3, 2},
            {2, 2, 2, 2, 1, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 2, 2, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 1, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 1, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 1, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {3, 2, 2, 2, 2, 2, 1, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 3},
            {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2},
        };
        
        public Graph() {
        	// Add all nodes
        	for(int i = 0; i < tileType.length; i++) {
        		for(int j = 0; j < tileType[0].length; j++) {
        			addNode(new bots.Bot1.Graph.Node(i, j, tileType[i][j]));
        		}
        	}
        	// Add links to corridor tiles
        	for(bots.Bot1.Graph.Node curr : nodes) {
        		if(curr.index-24 > 0 && curr.type == 0) {	// up
        			if(nodes.get(curr.index-24).type==0) {
        				curr.addNeighbour(nodes.get(curr.index-24));
        			}
        		}
        		if(curr.index+24 < 600 && curr.type == 0) {	// down
        			if(nodes.get(curr.index+24).type==0) {
        				curr.addNeighbour(nodes.get(curr.index+24));
        			}
        		}
        		if(curr.index-1 > 0 && curr.type == 0) {	// left
        			if(nodes.get(curr.index-1).type==0) {
        				curr.addNeighbour(nodes.get(curr.index-1));
        			}
        		}
        		if(curr.index+1 < 600 && curr.type == 0) {	// right
        			if(nodes.get(curr.index+1).type==0) {
        				curr.addNeighbour(nodes.get(curr.index+1));
        			}
        		}
        	}
        	// Special Links
        	//1
        	addMutualLinks(4+24*6,4+24*7);
        	//2
        	addMutualLinks(8+24*5,7+24*5);
        	//3
        	addMutualLinks(9+24*7,9+24*8);
        	//4
        	addMutualLinks(14+24*7,14+24*8);
        	//5
        	addMutualLinks(18+24*4,18+24*5);
        	//6
        	addMutualLinks(15+24*5,16+24*5);
        	//7
        	addMutualLinks(18+24*9,17+24*9);
        	//8
        	addMutualLinks(22+24*12,22+24*13);
        	//9
        	addMutualLinks(20+24*14,20+24*13);
        	//10
        	addMutualLinks(17+24*16,16+24*16);
        	//11
        	addMutualLinks(17+24*21,17+24*20);
        	//12
        	addMutualLinks(14+24*20,15+24*20);
        	//13
        	addMutualLinks(12+24*18,12+24*17);
        	//14
        	addMutualLinks(11+24*18,11+24*17);
        	//15
        	addMutualLinks(12+24*17,12+24*16);	// Basement Door
        	//16
        	addMutualLinks(6+24*19,6+24*18);
        	//17
        	addMutualLinks(6+24*15,6+24*16);
        	//18
        	addMutualLinks(7+24*12,8+24*12);
        	//19
        	addMutualLinks(5+24*1,23+24*21);	// Trap Door: Kitchen <-----> Study
        	//20
        	addMutualLinks(22+24*5,0+24*19);	// Trap Door: Conservatory <-----> Lounge
        	//21
        	addMutualLinks(5+24*1,4+24*6);		// Trap Door <-----> Kitchen
        	//22
        	addMutualLinks(22+24*5,18+24*4);	// Trap Door <-----> Conservatory
        	//23
        	addMutualLinks(23+24*21,17+24*21);	// Trap Door <-----> Study
        	//24
        	addMutualLinks(0+24*19,6+24*19);	// Trap Door <-----> Lounge
        	
        }
    	
    	int size;
    	private ArrayList<Node> nodes = new ArrayList<Node>();
    	
    	public Node getNode(int index) {
    		return nodes.get(index);
    	}
    	
    	public Node getUp(Node curr) {
    		if(curr.index-24 > 0) return getNode(curr.index-24);
    		else return null;
    	}
    	
    	public Node getDown(Node curr) {
    		if(curr.index+24 < 600) return getNode(curr.index+24);
    		else return null;
    	}
    	
    	public Node getLeft(Node curr) {
    		if(curr.index-1 > 0) return getNode(curr.index-1);
    		else return null;
    	}
    	
    	public Node getRight(Node curr) {
    		if(curr.index+1 < 600) return getNode(curr.index+1);
    		else return null;
    	}
    	
    	private void addMutualLinks(int indexA, int indexB) {
    		nodes.get(indexA).addNeighbour(nodes.get(indexB));
    		nodes.get(indexB).addNeighbour(nodes.get(indexA));
    	}
    	
    	private int addNode(Node n) {
    		nodes.add(n);
    		nodes.get(size).index = size;
    		int temp = size;
    		size++;
    		return temp;
    	}
    	
    	private ArrayList<Node> getLinkedNodes(){
    		ArrayList<Node> temp = new ArrayList<Node>();
    		for(Node curr : nodes) {
    			if(curr.links > 0) {
    				temp.add(curr);
    			}
    		}
    		return temp;
    	}
    	
    	class NodePriority implements Comparable<NodePriority>{

    		Node element;
    		int dist;
    		
    		public NodePriority(Node element, int dist) {
    			this.element = element;
    			this.dist = dist;
    		}
    		
			@Override
			public int compareTo(NodePriority arg) {
				return Integer.compare(this.dist, arg.dist);
			}
    		
    	}
    	
    	public ArrayList<Node> minPath(int indexA, int indexB) {
    		return a_star(getNode(indexA),getNode(indexB));
    	}
    	
    	public ArrayList<Node> minPath(Node A, Node B) {
    		return a_star(A,B);
    	}
    	
    	private ArrayList<Node> dijkstra(Node source, Node target) {
    		
    		int[] dist = new int[nodes.size()];
    		Node[] prev = new Node[nodes.size()];
    		dist[source.index] = 0;
    		PriorityQueue<NodePriority> qSet = new PriorityQueue<NodePriority>();
    		
    		for(Node curr : getLinkedNodes()) {
    			if(curr != source) {
    				dist[curr.index] = Integer.MAX_VALUE;
    				prev[curr.index] = null;
    			}
    			qSet.add(new NodePriority(curr,dist[curr.index]));
    		}
    		
    		while(!qSet.isEmpty()) {
    			
    			Node temp = qSet.remove().element;
    			if(temp == target) break;
    			for(Node n : temp.neighbours) {
    				int alt = dist[temp.index] + length(temp,n);
    				if(alt < dist[n.index]) {
    					dist[n.index] = alt;
    					prev[n.index] = temp;
    					qSet.add(new NodePriority(n,alt));
    					
    				}
    			}
    			
    		}
    		
    		ArrayList<Node> path = new ArrayList<Node>();
    		while(prev[target.index] != null) {
    			path.add(0, target);
    			target = prev[target.index];
    		}
    		path.add(0,source);
    		return path;
    		
    	}
    	
    	private ArrayList<Node> a_star(Node source, Node target){
    		
    		ArrayList<Node> closedSet = new ArrayList<Node>();
    		ArrayList<Node> openSet = new ArrayList<Node>();
    		openSet.add(source);
    		Node[] cameFrom = new Node[nodes.size()];
    		int[] gScore = new int[nodes.size()];
    		int[] fScore = new int[nodes.size()];
    		Arrays.fill(fScore, Integer.MAX_VALUE);
    		Arrays.fill(gScore, Integer.MAX_VALUE);
    		fScore[source.index] = heuristic(source,target);
    		gScore[source.index] = 0;
    		
    		while(!openSet.isEmpty()) {
    			
    			Node curr = findMinIndex(toArray(openSet), fScore);
    			if(curr == target) break;
    			
    			openSet.remove(curr);
    			closedSet.add(curr);
    			
    			for(Node n : curr.neighbours) {
    				
    				if(closedSet.contains(n)) continue;
    				if(!openSet.contains(n)) openSet.add(n);
    				
    				int distScore = gScore[curr.index] + length(curr,n);
    				if(distScore >= gScore[n.index]) continue;
    				
    				cameFrom[n.index] = curr;
    				gScore[n.index] = distScore;
    				fScore[n.index] = gScore[n.index] + heuristic(n,target);
    				
    			}
    			
    		}
    		
    		ArrayList<Node> path = new ArrayList<Node>();
    		while(cameFrom[target.index] != null) {
    			path.add(0,target);
    			target = cameFrom[target.index];
    		}
    		path.add(0,source);
    		return path;
    	}
    	
    	private Node[] toArray(ArrayList<Node> temp) {
    		Node[] curr = new Node[temp.size()];
    		for(int i = 0; i < temp.size(); i++) {
    			curr[i] = temp.get(i);
    		}
    		return curr;
    	}
    	
    	private Node findMinIndex(Node[] array, int[] fScore) {
    		int minVal = fScore[array[0].index];
    		int minIndex = 0;
    		for(int idx = 1; idx < array.length; idx++) {
    			if(fScore[array[idx].index] < minVal) {
    				minVal = fScore[array[idx].index];
    				minIndex = idx;
    			}
    		}
    		return array[minIndex];
    	}
    	
    	private int heuristic(Node u, Node v) {
    		return Math.abs(u.x-v.x) + Math.abs(u.y-v.y);
    	}
    	
    	private int length(Node u, Node v) {
    		if(u.type == 0 && v.type == 0) {
    			return 1;
    		}
    		if((u.type == 0 && v.type == 1) || (u.type == 1 && v.type == 0)) {
    			return 7;
    		}
    		if((u.type == 1 && v.type == 3) || (u.type == 3 && v.type == 1)) {
    			return 7;
    		}
    		if(u.type == 3 && v.type == 3) {
    			return 7;
    		}
    		else {
    			return Integer.MAX_VALUE;
    		}
    	}
    	
    	public String toString() {
    		StringBuilder sb = new StringBuilder();
    		Iterator<Node> nodesI = nodes.iterator();
    		int i = 0;
    		while(nodesI.hasNext()) {
    			sb.append("Node " + i + ": ");
    			for(Node n : nodes.get(i).neighbours) {
    				sb.append(n.index + " ");
    			}
    			sb.append("\n");
    			i++;
    			nodesI.next();
    		}
    		return sb.toString();
    	}
    	
    	public String toStringConnected() {
    		StringBuilder sb = new StringBuilder();
    		for(Node curr : nodes) {
    			if(curr.links>0) {
        			sb.append("Node " + curr.index + ": ");
        			for(Node n : curr.neighbours) {
        				sb.append(n.index + " ");
        			}
        			sb.append("\n");
    			}
    		}
    		return sb.toString();
    	}
    	
    	public String toStringMap() {
    		StringBuilder sb = new StringBuilder();
    		for(Node curr : nodes) {
        		if(curr.type==0) sb.append("#\t");
        		else sb.append("\t");
        		if((curr.index+1)%24==0) sb.append("\n");
        	}
    		return sb.toString();
    	}
    	
    	public String toStringLinks() {
    		StringBuilder sb = new StringBuilder();
    		for(Node curr : nodes) {
        		if(curr.links>0) sb.append("L\t");
        		else sb.append("\t");
        		if((curr.index+1)%24==0) sb.append("\n");
        	}
    		return sb.toString();
    	}
    	
    	public String toStringPath(ArrayList<Node> path) {
    		StringBuilder sb = new StringBuilder();
    		for(Node curr : nodes) {
        		if(path.contains(curr)) {
        			if(curr == path.get(0)) sb.append("S\t");
        			else if(curr == path.get(path.size()-1)) sb.append("T\t");
        			else if(curr.index == 173) sb.append('!');
        			else sb.append("P\t");
        		}
        		else if(curr.links>0) sb.append("#\t");
        		else sb.append("\t");
        		if((curr.index+1)%24==0) sb.append("\n");
        	}
    		return sb.toString();
    	}
    	
    }

    public Bot1 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
    }

    public String getName() {
        return "Bot1"; // must match the class name
    }

    public String getCommand() {
        // Add your code here
        return "done";
    }

    public String getMove() {
        // Add your code here
        return "r";
    }

    public String getSuspect() {
        // Add your code here
        return Names.SUSPECT_NAMES[0];
    }

    public String getWeapon() {
        // Add your code here
        return Names.WEAPON_NAMES[0];
    }

    public String getRoom() {
        // Add your code here
        return Names.ROOM_NAMES[0];
    }

    public String getDoor() {
        // Add your code here
        return "1";
    }

    public String getCard(Cards matchingCards) {
        // Add your code here
        return matchingCards.get().toString();
    }

    public void notifyResponse(Log response) {
        // Add your code here
    }
    
    public static void main(String[] args) {
    	Graph g = new Graph();
//    	for(bots.Bot1.Graph.Node curr : g.minPath(4+24*7,17+24*20)) {
//    		System.out.print(curr.index + "\t");
//    	}
//    	System.out.print("\n"+g.toStringPath(g.minPath(4+24*7,17+24*20))+"\n");
    }

}
