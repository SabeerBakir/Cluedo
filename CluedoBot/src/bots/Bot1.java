package bots;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.HashMap;
import java.util.Arrays;

import gameengine.*;

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
    private int moves;
    private Graph g = new Graph();;
    private Notes notes = new Notes();
    private boolean markCards = true; // for marking the cards into the notes
    private boolean askedQuestion = false; // for checking if a question has been asked already
    private int questionsAsked = 0; // keeping track of how many questions this bot asks
    private Random rand = new Random();
    private String confirmedSuspect = null;
    private String confirmedWeapon = null;
    private String confirmedRoom = null;
    private ArrayList<Card> revealedCards = new ArrayList<>();
    private int logLineCounter = 0;
    private ArrayList<Integer> cardInfoSetsNumber = new ArrayList<>(); 
    private int exitDoor;
    private boolean rolled = false;
    
    private static class Graph{
    	
    	public static class Node{
    		
    		int index;
    		int x;
    		int y;
    		int type;	// 0 = hallway, 1 = door, 2 = room, 3 = trap door, 4 = other, 5 = room centre
    		int links;	// number of vertices from this node
    		ArrayList<Node> neighbours = new ArrayList<Node>();	// ArrayList of neighbouring nodes
    		
    		Node(int x, int y, int type){
    			this.index = this.index++;
    			this.x = x;
    			this.y = y;
    			if(type >= 0 && type < 6) this.type = type;
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
            {2, 2, 2, 2, 2, 3, 2, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 2, 2, 5, 2, 2, 2, 2},
            {2, 5, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 5, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 2, 2, 2, 3, 2},
            {2, 2, 2, 2, 1, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 2, 1, 2, 2, 2, 2, 1, 2, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 5, 2, 2, 2},
            {2, 2, 5, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 2, 1, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 2, 2, 2, 2, 1, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 5, 2, 2, 0, 0, 0, 2, 2, 1, 2, 2, 2},
            {2, 2, 2, 2, 2, 2, 1, 2, 0, 0, 2, 2, 2, 2, 2, 0, 0, 2, 5, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 2},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 1, 1, 2, 2, 0, 0, 0, 2, 2, 2, 2, 2, 2},
            {3, 2, 2, 2, 2, 2, 1, 0, 0, 2, 5, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 1, 2, 2, 2, 2, 2, 3},
            {2, 2, 5, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 5, 2, 2, 2, 2},
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
        	
        	// Kitchen
        	addMutualLinks(1+24*2,4+24*6);
        	addMutualLinks(1+24*2,5+24*1);		// Trap Door <-----> Kitchen
        	// Ballroom
        	addMutualLinks(10+3*24,8+24*5);
        	addMutualLinks(10+3*24,9+24*7);
        	addMutualLinks(10+3*24,14+24*7);
        	addMutualLinks(10+3*24,15+24*5);
        	// Conservatory
        	addMutualLinks(19+24*1,18+24*4);
        	addMutualLinks(19+24*1,22+24*5);	// Trap Door <------> Conservatory
        	// Billiard Room
        	addMutualLinks(20+24*9,18+24*9);
        	addMutualLinks(20+24*9,22+24*12);
        	// Library
        	addMutualLinks(18+24*15,17+24*16);
        	addMutualLinks(18+24*15,20+24*14);
        	// Study
        	addMutualLinks(19+24*22,17+24*21);
        	addMutualLinks(19+24*22,23+24*21);	// Trap Door <-----> Study
        	// Hall
        	addMutualLinks(10+24*19,11+24*18);
        	addMutualLinks(10+24*19,12+24*18);
        	addMutualLinks(10+24*19,14+24*20);
        	// Lounge
        	addMutualLinks(2+24*22,6+24*19);
        	addMutualLinks(2+24*22,0+24*19);	// Trap Door <-----> Lounge
        	// Dining Room
        	addMutualLinks(2+24*10,6+24*15);
        	addMutualLinks(2+24*10,7+24*12);
        	// Basement
        	addMutualLinks(12+24*14,12+24*16);      
        	
        	
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
    				//if(length(curr,n) == 4) System.out.println("[" + curr + "," + curr.type + "] <--> [" + n + "," + n.type + "]"); 
    				if(distScore >= gScore[n.index]) continue;
    				
    				//System.out.println(curr + " --> " + n);
    				
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
    		if((u.type == 5 && v.type == 3) || (u.type == 3 && v.type == 5)) {
    			return 4;
    		}
    		if(u.type == 3 && v.type == 3) {
    			return 7;
    		}
    		if((u.type == 1 && v.type == 5) || (u.type == 5 && v.type == 1)) {
    			return 0;
    		}
    		else {
    			return 7;
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
        			else sb.append("P\t");
        		}
        		else if(curr.links>0) sb.append("#\t");
        		else sb.append("\t");
        		if((curr.index+1)%24==0) sb.append("\n");
        	}
    		return sb.toString();
    	}
    	
    }
    
    private class Notes{
    	String[][] suspects = new String[6][4];
    	String[][] weapons = new String[6][4];
    	String[][] rooms = new String[9][4];
    	
    	
    	
    	Notes(){
	    	int i = 0; // counter
	    	
	    	for(String s : Names.SUSPECT_NAMES){
	    		suspects[i][0] = s;
	    		i++;
	    	}
	    	i=0;
	    	for(String s : Names.WEAPON_NAMES){
	    		weapons[i][0] = s;
	    		i++;
	    	}
	    	i=0;
	    	for(String s : Names.ROOM_CARD_NAMES){
	    		rooms[i][0] = s;
	    		i++;
	    	}
    	}
    	
    	
    	void add(String mark, String name){ // for cards you know 100%
    		
    		if(Names.isSuspect(name)){
    			for(int i = 0; i < Names.SUSPECT_NAMES.length; i++){
    				if(suspects[i][0].toLowerCase().equals(name.toLowerCase().trim())){
    					for(int j = 1; j < suspects[i].length; j++){
    						suspects[i][j] = mark;
    					}
    				}
    			}
    		}
    		else if(Names.isWeapon(name)){
    			for(int i = 0; i < Names.WEAPON_NAMES.length; i++){
    				if(weapons[i][0].toLowerCase().equals(name.toLowerCase().trim())){
    					for(int j = 1; j < weapons[i].length; j++){
    						weapons[i][j] = mark;
    					}
    				}
    			}
    		}
    		else if(Names.isRoomCard(name)){
    			for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++){
    				//System.out.println(rooms[i][0].toLowerCase() + "\t" + name.toLowerCase().trim());
    				if(rooms[i][0].toLowerCase().equals(name.toLowerCase().trim())){
    					for(int j = 1; j < rooms[i].length; j++){
    						rooms[i][j] = mark;
    					}
    				}
    			}
    		}
    	}
    	
    	public void noCardShown(String suspect, String weapon, String room, int botNum) {
    		for(int i = 0; i < Names.SUSPECT_NAMES.length; i++) {
				if(notes.suspects[i][0].equals(suspect)) {
					notes.suspects[i][botNum] = "X";
				}
			}
    		
    		for(int i = 0; i < Names.WEAPON_NAMES.length; i++) {
				if(notes.weapons[i][0].equals(weapon)) {
					notes.weapons[i][botNum] = "X";
				}
			}
    		
    		for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++) {
				if(notes.rooms[i][0].equals(room)) {
					notes.rooms[i][botNum] = "X";
				}
			}
    		
    		// Perform Checks
    		checkForConfirmedSuspect();
    		checkForConfirmedWeapon();
    		checkForConfirmedRoom();
    	}
    	
    	public void unknownCardShown(String suspect, String weapon, String room, int botNum, int mark) {
    		
    		String strMark = String.valueOf(mark);
    		
    		for(int i = 0; i < Names.SUSPECT_NAMES.length; i++) {
    			if(suspects[i][0].toLowerCase().equals(suspect.toLowerCase().trim())) {
    				for(int j = 1; j < suspects[i].length; j++) {
    					if(j == botNum) {
    			    		StringBuffer buf = new StringBuffer();
    			    		if(suspects[i][j] == null) {
    			    			buf.append(strMark);
    			    		}
    			    		else if(suspects[i][j] == "X"){
    			    			buf.append("X");
    			    		}
    			    		else if(suspects[i][j] == "tick"){
    			    			buf.append("tick");
    			    		}
    			    		else {
    			    			buf.append(suspects[i][j] + " " + strMark);
    			    		}
    						suspects[i][j] = buf.toString();
    					}
    				}
    			}
    			break;
			}
    		
    		for(int i = 0; i < Names.WEAPON_NAMES.length; i++) {
    			if(weapons[i][0].toLowerCase().equals(weapon.toLowerCase().trim())) {
    				for(int j = 1; j < weapons[i].length; j++) {
    					if(j == botNum) {
    			    		StringBuffer buf = new StringBuffer();
    			    		if(weapons[i][j] == null) {
    			    			buf.append(strMark);
    			    		}
    			    		else if(weapons[i][j] == "X"){
    			    			buf.append("X");
    			    		}
    			    		else if(suspects[i][j] == "tick"){
    			    			buf.append("tick");
    			    		}
    			    		else {
    			    			buf.append(weapons[i][j] + " " + strMark);
    			    		}
    						weapons[i][j] = buf.toString();
    					}
    				}
    			}
    			break;
			}
    		
    		for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++) {
    			if(rooms[i][0].toLowerCase().equals(room.toLowerCase().trim())) {
    				for(int j = 1; j < rooms[i].length; j++) {
    					if(j == botNum) {
    			    		StringBuffer buf = new StringBuffer();
    			    		if(rooms[i][j] == null) {
    			    			buf.append(strMark);
    			    		}
    			    		else if(rooms[i][j] == "X"){
    			    			buf.append("X");
    			    		}
    			    		else if(suspects[i][j] == "tick"){
    			    			buf.append("tick");
    			    		}
    			    		else {
    			    			buf.append(rooms[i][j] + " " + strMark);
    			    		}
    						rooms[i][j] = buf.toString();    						
    					}
    				}
    			}
    			break;
			}
    		
    		//System.out.println(this);
    	}
    	
    	public void cardShown(String card, int botNum) {
    		for(int i = 0; i < Names.SUSPECT_NAMES.length; i++) {
    			if(suspects[i][0].equals(card)) {
    				for(int j = 1; j < suspects[i].length; j++) {
    					if(j == botNum) {
    						suspects[i][j] = "tick";
    					}
    					else {
    						suspects[i][j] = "X";
    					}
    				}
    			}
			}
    		
    		for(int i = 0; i < Names.WEAPON_NAMES.length; i++) {
    			if(weapons[i][0].equals(card)) {
    				for(int j = 1; j < weapons[i].length; j++) {
    					if(j == botNum) {
    						weapons[i][j] = "tick";
    					}
    					else {
    						weapons[i][j] = "X";
    					}
    				}
    			}
			}
    		
    		for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++) {
    			if(rooms[i][0].equals(card)) {
    				for(int j = 1; j < rooms[i].length; j++) {
    					if(j == botNum) {
    						rooms[i][j] = "tick";
    					}
    					else {
    						rooms[i][j] = "X";
    					}
    				}
    			}
			}
    		
    		// Perform Checks
    		checkForConfirmedSuspect();
    		checkForConfirmedWeapon();
    		checkForConfirmedRoom();
    	}
    	
    	void checkForConfirmedSuspect() {
    		int rowsFullyKnown = 0;
    		for(int i = 0; i < Names.SUSPECT_NAMES.length; i++) {
    			int cardsKnownInRow = 0;
    			for(int j = 1; j < suspects[i].length; j++) {
    				if(suspects[i][j] != null) {
    					if (suspects[i][j].equals("X") || suspects[i][j].equals("tick")) {
    				}
    					cardsKnownInRow++;
    					if(cardsKnownInRow == 3)
    						rowsFullyKnown++;
    				}
    			}
    			if(rowsFullyKnown == 5) {
    				for(int k = 0; k < Names.SUSPECT_NAMES.length; k++) {
    					if(suspects[k][1] != null && suspects[k][2] != null && suspects[k][3] != null) {
    						// do nothing
    					}
    					else {
    						confirmedSuspect = suspects[k][0];
    						System.out.println("SUSPECT FOUND");
    						System.out.println(confirmedSuspect);
    					}
    				}
    			}
    		}
    	}

    	
    	void checkForConfirmedWeapon() {
    		int rowsFullyKnown = 0;
    		for(int i = 0; i < Names.WEAPON_NAMES.length; i++) {
    			int cardsKnownInRow = 0;
    			for(int j = 1; j < weapons[i].length; j++) {
    				if(weapons[i][j] != null) {
    					if (weapons[i][j].equals("X") || weapons[i][j].equals("tick")) {
    				}
    					cardsKnownInRow++;
    					if(cardsKnownInRow == 3)
    						rowsFullyKnown++;
    				}
    			}
    			if(rowsFullyKnown == 5) {
    				for(int k = 0; k < Names.WEAPON_NAMES.length; k++) {
    					if(weapons[k][1] != null && weapons[k][2] != null && weapons[k][3] != null) {
    						// do nothing
    					}
    					else {
    						confirmedWeapon = weapons[k][0];
    						System.out.println("WEAPON FOUND");
    						System.out.println(confirmedWeapon);
    					}
    				}
    			}
    		}
    	}
    	
    	void checkForConfirmedRoom() {
    		int rowsFullyKnown = 0;
    		for(int i = 0; i < Names.ROOM_NAMES.length - 1; i++) {
    			int cardsKnownInRow = 0;
    			for(int j = 1; j < rooms[i].length; j++) {
    				if(rooms[i][j] != null) {
    					if (rooms[i][j].equals("X") || rooms[i][j].equals("tick")) {
    				}
    					cardsKnownInRow++;
    					if(cardsKnownInRow == 3)
    						rowsFullyKnown++;
    				}
    			}
    			if(rowsFullyKnown == 8) {
    				for(int k = 0; k < Names.ROOM_NAMES.length - 1; k++) {
    					if(rooms[k][1] != null && rooms[k][2] != null && rooms[k][3] != null) {
    						// do nothing
    					}
    					else {
    						confirmedRoom = rooms[k][0];
    						System.out.println("ROOM FOUND");
    						System.out.println(confirmedRoom);
    					}
    				}
    			}
    		}
    	}
    	
    	public String toString(){
    		StringBuffer buf = new StringBuffer();
    		
    		buf.append("Suspects\n---------------------\n");
    		for(int i = 0; i < Names.SUSPECT_NAMES.length; i++){
    			for(int j = 0; j < suspects[i].length; j++){
    				if(suspects[i][j] == null){
    					buf.append("\t");
    				}
    				else{
    					buf.append(suspects[i][j] + "\t");
    				}
    			}
    			buf.append("\n");
    		}
    		buf.append("\n");
    		buf.append("Weeapons\n---------------------\n");
    		for(int i = 0; i < Names.WEAPON_NAMES.length; i++){
    			for(int j = 0; j < weapons[i].length; j++){
    				if(weapons[i][j] == null){
    					buf.append("\t");
    				}
    				else{
    					buf.append(weapons[i][j] + "\t");
    				}
    			}
    			buf.append("\n");
    		}
    		buf.append("\n");
    		buf.append("Rooms\n---------------------\n");
    		for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++){
    			for(int j = 0; j < rooms[i].length; j++){
    				if(rooms[i][j] == null){
    					buf.append("\t");
    				}
    				else{
    					buf.append(rooms[i][j] + "\t");
    				}
    			}
    			buf.append("\n");
    		}
    		buf.append("\n");
    		
			return buf.toString();
    	}
    	
    }
    
    public Bot1 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
        this.moves = 0;
    }

    public String getName() {
        return "Bot1"; // must match the class name
    }

    public String getCommand() {
    		if(markCards){ // Mark cards on notes
    		markCards = false;
	    	for(Card x : player.getCards()){
	    		notes.add("X", x.toString());
	    	}
    	}	
    	
    	parseLog();    	
    	if(!rolled) {
    		if(player.getToken().isInRoom()){
    			if(player.getToken().getRoom().hasPassage()){
    				if(player.getCards().contains(player.getToken().getRoom().getPassageDestination().toString())){
    					for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++){
    						int count = 0;
    						for(int j = 1; j < notes.rooms[i].length; j++){
    							if(notes.rooms[i][0].equals(player.getToken().getRoom().getPassageDestination().toString())){
	    							if(notes.rooms[i][j] == "X"){
	    								count++;
	    							}
    							}
    						}
    						if(count <= 1){
		    					rolled = true;
		    					return "passage";
    						}
    					}
    				}
    			}
    		}
    		rolled = true;
    		return "roll";
    	}

    	
    	if(moves == dice.getTotal() || player.getToken().isInRoom()){
    		moves = 0;
    	}
    	if(player.getToken().isInRoom() && !askedQuestion){ // Ask Question or accuse
			if(confirmedSuspect != null && confirmedWeapon != null && confirmedRoom != null && player.getToken().getRoom().hasName("Cellar")){
				return "accuse";
			}
			else if(!askedQuestion){
    			askedQuestion = true;
    			questionsAsked++;
    			return "question";
			}
		}
    	askedQuestion = false;
    	rolled = false;
    	return "done";
    }

    public String getMove() {
    	Coordinates pos = player.getToken().getPosition();
    	Coordinates dest = null;
    	exitDoor = 0;
		int across = 0;
		int down = 0;
		
    	if(confirmedSuspect != null && confirmedWeapon != null && confirmedRoom != null) {
    		shortestRouteToRoom(across, down, pos, map.getRoom("Cellar"));
    	}
    	else {
			// Nearest room
			if(questionsAsked == 0) {
		    	dest = closestRoom(pos);
			}
			else {
				
		    	dest = closestRoomWithLeastInfo(pos);
		    	
				if(player.getToken().isInRoom()) {
					 findClosestDoorToDest(dest, player.getToken().getRoom());
				}
			}
			
			// Bug fix
			if(player.getToken().isInRoom()) {
				Room room = player.getToken().getRoom();
				if(room.toString().equals(Names.ROOM_CARD_NAMES[0])) { // Kitchen
					pos = new Coordinates(1, 2);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[1])) { // Ballroom
					pos = new Coordinates(10, 3);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[2])) { // Conservatory
					pos = new Coordinates(19, 1);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[3])) { // Billiard Room
					pos = new Coordinates(20, 9);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[4])) { // Library
					pos = new Coordinates(18, 15);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[5])) { // Study
					pos = new Coordinates(19, 22);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[6])) { // Hall
					pos = new Coordinates(10, 19);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[7])) { // Lounge
					pos = new Coordinates(2, 22);
				}
				else if(room.toString().equals(Names.ROOM_CARD_NAMES[8])) { // Dining Room
					pos = new Coordinates(2, 10);
				}							
			}
			
			if(dest.getCol() == 0 && dest.getRow() == 0) { // No where to prioritise going, go to next closest room
				dest = closestRoom(pos);
				if(player.getToken().isInRoom()) {
					findClosestDoorToDest(dest, player.getToken().getRoom());
				}
				System.out.println("No rooms left");
			}
			
			// Bug testing
			if(g.minPath(pos.getCol() + 24*pos.getRow(), dest.getCol() + 24*dest.getRow()).size() < 2) {
				System.out.println(g.getNode(pos.getCol() + 24*pos.getRow()).neighbours);
				System.out.println(g.minPath(pos.getCol() + 24*pos.getRow(), dest.getCol() + 24*dest.getRow()));
				System.out.println(dest);
				for(Card c : player.getCards()) {
					System.out.println(c.toString());
				}
				System.out.println("");
				System.out.println("");
				System.exit(-1);
			}

		    across = g.minPath(pos.getCol() + 24*pos.getRow(), dest.getCol() + 24*dest.getRow()).get(1).y;
		    down = g.minPath(pos.getCol() + 24*pos.getRow(), dest.getCol() + 24*dest.getRow()).get(1).x;
		    
		    // Edge cases
		    if(across == 2 && down == 10){
		    	across = 8;
		    	down = 12;
		    }
		    else if(across == 10 && down == 19){
		    	across = 11;
		    	down = 17;
		    }
		    else if(across == 20 && down == 9){
		    	across = 22;
		    	down = 13;
		    }
		    
    	}

    	//System.out.println(pos + "\t" + dest + "\t" + across + "\t" + down);
    	//System.out.println(g.minPath(pos.getCol() + 24*pos.getRow(), dest.getCol() + 24*dest.getRow()));
    	if(player.getToken().getPosition().equals(new Coordinates(8,5))) { // prevent player getting stuck in doors
    		moves++;
    		return "l";
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(7,12))) {
    		moves++;
    		return "r";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(18,9))) {
    		moves++;
    		return "l";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(6,19))) {
    		moves++;
    		return "u";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(17,16))) {
    		moves++;
    		return "l";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(20,14))) {
    		moves++;
    		return "u";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(22,12))) {
    		moves++;
    		return "d";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(4,6))) {
    		moves++;
    		return "d";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(9,7))) {
    		moves++;
    		return "d";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(14,7))) {
    		moves++;
    		return "d";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(15,5))) {
    		moves++;
    		return "r";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(18,4))) {
    		moves++;
    		return "d";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(6,15))) {
    		moves++;
    		return "d";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(11,18))) {
    		moves++;
    		return "u";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(12,18))) {
    		moves++;
    		return "u";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(14,20))) {
    		moves++;
    		return "r";    		
    	}
    	else if(player.getToken().getPosition().equals(new Coordinates(17,21))) {
    		moves++;
    		return "u";    		
    	}
    	else if(down > pos.getRow()){
    		moves++;
    		return "d";
    	}
    	else if(across > pos.getCol()){
    		moves++;
    		return "r";
    	}
    	else if(across < pos.getCol()){
    		moves++;
    		return "l";
    	}
    	else if(down < pos.getRow()){
    		moves++;
    		return "u";
    	}
    	else{
    		return "error";
        }
    }

    public String getSuspect() {
		if(confirmedSuspect != null){
			return confirmedSuspect;
		}
    	if(questionsAsked == 0){ // On the first question ...
	    	int chance = rand.nextInt(100) + 1;
	    	if(chance < 80){ //80% of the time
	    		for(Card c : player.getCards()){
	    			if(Names.isSuspect(c.toString())){
	    				return c.toString();
	    			}
	    		}
	    	}
	    	else{// 20% of the time ...
	    		
	    	}
    	}
    	else{ 
    		for(int i = 0; i < Names.SUSPECT_NAMES.length; i++){
    	   		int count = 0;
    			for(int j = 1; j < notes.suspects[i].length; j++){
    				if(notes.suspects[i][j] == "tick"){
    					count = 0;
    					break; // Go next line
    				}
    				else if(notes.suspects[i][j] != "X"){
    					count++;
    				}
    			}
    			if(count > 1){ // This number can be tweaked
    				System.out.println(notes.suspects[i][0]);
    				return notes.suspects[i][0];
    			}
    		}
    	}
    	return Names.SUSPECT_NAMES[rand.nextInt(Names.SUSPECT_NAMES.length - 1)]; // if none of the above apply, we give back a random name
    }

    public String getWeapon() {
		if(confirmedWeapon != null){
			return confirmedWeapon;
		}
		int count = 0;
		for(int i = 0; i < Names.WEAPON_NAMES.length; i++){
			for(int j = 1; j < notes.weapons[i].length; j++){
				if(notes.weapons[i][j] == "tick"){
					break; // Go next line
				}
				else if(notes.weapons[i][j] != "X"){
					count++;
				}
			}
			if(count > 1){ // This number can be tweaked
				System.out.println(notes.weapons[i][0]);
				return notes.weapons[i][0];
			}
		}
	
	return Names.WEAPON_NAMES[rand.nextInt(Names.WEAPON_NAMES.length - 1)]; // if none of the above apply, we give back a random name
    }

    public String getRoom() {
    	if(confirmedRoom != null){
    		return confirmedRoom;
    	}
        return player.getToken().getRoom().toString();
    }

    public String getDoor() {
    	getMove();
        return Integer.toString(exitDoor + 1);
    }

    public String getCard(Cards matchingCards) {
    	// Show the least amount of cards
    	if(revealedCards.isEmpty()) {
    		revealedCards.add(matchingCards.get());
    		return matchingCards.get().toString();
    	}
    	if(revealedCards.size() == 1) {
    		for(Card c : matchingCards) {
    			if(c.hasName(revealedCards.get(0).toString())) {
    				return c.toString();
    			}
    		}
    	}
    	if(revealedCards.size() > 1) {
    		for(Card c : revealedCards) {
    			for(Card d : matchingCards) {
    				if(c.hasName(d.toString())) {
    					return d.toString();
    				}
    			}
    		}
    	}
        return matchingCards.get().toString();
    }

    public void notifyResponse(Log response) {
    	String suspect = null;
    	String weapon = null;
    	String room = null;
    	
    	for(String s : response) {
    		if(s.contains("questioned")) {
    			for(String x : Names.SUSPECT_NAMES) {
    				if(s.substring(s.lastIndexOf(")")).contains(x)) {
    					suspect = x;
    					break;
    				}
    			}
    			for(String y : Names.WEAPON_NAMES) {
    				if(s.contains(y)) {
    					weapon = y;
    					break;
    				}
    			}
    			for(String z : Names.ROOM_CARD_NAMES) {
    				if(s.contains(z)) {
    					room = z;
    					break;
    				}
    			}
    		}
    	}
    	for(String s : response) {
    		System.out.println(s);
    		if(s.contains("did not show any cards")) {
    			int botNum = getBotNum(s.substring(0, s.indexOf(" ")));
    			notes.noCardShown(suspect, weapon, room, botNum);
    		}
    		if(s.contains("showed")) {
    			String card = s.substring(s.indexOf(": ") + 2, s.length() - 1);
    			int botNum = getBotNum(s.substring(0, s.indexOf(" ")));
    			//System.out.println(botNum);
    			notes.cardShown(card, botNum);
    			System.out.println(notes);
    		}
    	}
    }
    
    public int getBotNum(String playerName) {
    	String[] temp = playersInfo.getPlayersNames();
    	for(int i = 0; i < temp.length; i++) {
    		if(temp[i].equals(playerName)) {
    			return (i + 1);
    		}
    	}
    	return -1; // error
    }

    public void parseLog() {
    	// Parse the log
    	int counter = 0; //temporary counter
		String suspect = null;
    	String weapon = null;
    	String room = null;
    	for(String s : log) {
    		if(counter > logLineCounter) { //this way we don't parse old information
	    		//System.out.println(s);
	    		if(!s.substring(0, s.indexOf(" ")).equals(player.getName())){
		    		if(s.contains("questioned")) {
		    			for(String x : Names.SUSPECT_NAMES) {
		    				if(s.substring(s.lastIndexOf(")")).contains(x)) {
		    					suspect = x;
		    					break;
		    				}
		    			}
		    			for(String y : Names.WEAPON_NAMES) {
		    				if(s.contains(y)) {
		    					weapon = y;
		    					break;
		    				}
		    			}
		    			for(String z : Names.ROOM_CARD_NAMES) {
		    				if(s.contains(z)) {
		    					room = z;
		    					break;
		    				}
		    			}
		    		}
	    		}
	    		if(suspect != null && weapon != null && room != null){
		    		if(s.contains("did not show any cards") && s.substring(0, s.indexOf(" ")).equals(player.getName())) {
		    			int botNum = getBotNum(s.substring(0, s.indexOf(" ")));
		    			notes.noCardShown(suspect, weapon, room, botNum);
		    		}
		    		if(s.contains("showed")) {
		    			int botNum = getBotNum(s.substring(0, s.indexOf(" ")));
		    			cardInfoSetsNumber.add(botNum);
		    			int count = 0;
		    			for(int x : cardInfoSetsNumber) {
		    				if(botNum == x) {
		    					count++;
		    				}
		    			}
		    			notes.unknownCardShown(suspect, weapon, room, botNum, count);
		    		}
	    		}
	    		counter++;
	    		logLineCounter++;
    		}
    		else {
    			counter++;
    		}
    	}
    	//System.out.println(notes);
    }
    
    public Coordinates closestRoom(Coordinates pos) { //From a normal tile or room
    	int minIndex = 0;
    	int minVal = Integer.MAX_VALUE;
    	for(bots.Bot1.Graph.Node n : g.nodes){
    		if(n.type == 1 && n.index != (pos.getCol() + 24*pos.getRow())){
    			if(player.getToken().isInRoom()) {
    				ArrayList<Integer> doorCoords = new ArrayList<>();
    				for(int i = 0; i < player.getToken().getRoom().getNumberOfDoors(); i++) {
    					doorCoords.add(player.getToken().getRoom().getDoorCoordinates(i).getCol() + player.getToken().getRoom().getDoorCoordinates(i).getRow()*24);
    				}
    				
    				if(!doorCoords.contains(n.index)) {
    					if(g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n) < minVal){
    						minVal= g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n);
    						minIndex = n.index;
    					}
    				}
    			}
    			else {
					if(g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n) < minVal){
						minVal= g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n);
						minIndex = n.index;
					}
    			}
    		}
    	}
    	return new Coordinates(minIndex%24, minIndex/24);
    }

    public void shortestRouteToRoom(int across, int down, Coordinates pos, Room room) {
    	int minIndex = 0;
    	int minVal = Integer.MAX_VALUE;
    	Coordinates temp;
    	
    	if(player.getToken().isInRoom()) {
    		findClosestDoorToDest(room.getDoorCoordinates(0), player.getToken().getRoom());
    	}
    	else {
	    	temp = room.getDoorCoordinates(0);
	    	
	    	bots.Bot1.Graph.Node n = g.getNode(temp.getCol() + 24*temp.getRow());
			if(g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n) < minVal){
				minVal= g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n);
				minIndex = n.index;
	    	}
			
			Coordinates dest = new Coordinates(minIndex%24, minIndex/24);
			
	        across = g.minPath(pos.getCol() + 24*pos.getRow(), dest.getCol() + 24*dest.getRow()).get(1).y;
	        down = g.minPath(pos.getCol() + 24*pos.getRow(), dest.getCol() + 24*dest.getRow()).get(1).x;
    	}
    }
    
//    public void findLeastInfo(ArrayList<String> leastInfo){
//    	
//		for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++) {
//			int amount = 0;
//			for(int j = 1; j < notes.rooms[i].length; j++) {
//				if(notes.rooms[i][j] != "X") {
//					amount++;
//				}
//			}
//			if(amount >= 3) { // this number can be changed
//				if(player.getToken().isInRoom()) {
//					if(notes.rooms[i][0] == player.getToken().getRoom().toString()) {
//						// Do nothing
//					}
//    				else {
//    					leastInfo.add(notes.rooms[i][0]);
//    					//System.out.println(notes.rooms[i][0]);
//    				}
//				}
//				else {
//					leastInfo.add(notes.rooms[i][0]);
//				}
//			}
//		}
//    }
    
    public Coordinates closestRoomWithLeastInfo(/*ArrayList<String> leastInfo, */Coordinates pos) {
    	int minIndex = 0;
    	int minVal = Integer.MAX_VALUE;
    	
    	ArrayList<String> leastInfo = new ArrayList<>();
    	int minCount = 2;
    	for(int i = 0; i < Names.ROOM_CARD_NAMES.length; i++){
    		int count = 0;
    		for(int j = 1; j < notes.rooms[i].length; j++){
    			if(notes.rooms[i][j] == "X" || notes.rooms[i][j] == "tick"){
    				count++;
    			}
    		}
    		if(count <= minCount){
    			minCount = count;
    			leastInfo.add(notes.rooms[i][0]);
    		}
    	}
    	
    	// find the closest room with the least info
    	for(String s : leastInfo) {
    		if(map.getRoom(s).getNumberOfDoors() > 1) {
    			for(int i = 0; i < map.getRoom(s).getNumberOfDoors(); i++) {
    				Coordinates temp = map.getRoom(s).getDoorCoordinates(i);
    				bots.Bot1.Graph.Node n = g.getNode(temp.getCol() + 24*temp.getRow());
    	    		if(n.type == 1 && n.index != pos.getCol() + 24*pos.getRow()){
    	    			if(g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n) < minVal){
    	    				minVal= g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n);
    	    				minIndex = n.index;
    	    			}
    	    		}
    			}
    		}
    		else { // only has 1 door
    			Coordinates temp = map.getRoom(s).getDoorCoordinates(0);
    			bots.Bot1.Graph.Node n = g.getNode(temp.getCol() + 24*temp.getRow());
	    		if(n.type == 1 && n.index != pos.getCol() + 24*pos.getRow()){
	    			if(g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n) < minVal){
	    				minVal= g.heuristic(g.getNode(pos.getCol() + 24*pos.getRow()), n);
	    				minIndex = n.index;
	    			}
	    		}
    		}
    	}
    	
    	return new Coordinates(minIndex%24, minIndex/24);	
    }
    
    public void findClosestDoorToDest(Coordinates dest, Room currentRoom) {
    	int minIndex = 0;
    	int minVal = Integer.MAX_VALUE;
    	exitDoor = 0;
		if(currentRoom.getNumberOfDoors() > 1) {
			for(int i = 0; i < currentRoom.getNumberOfDoors(); i++) {
				Coordinates temp = currentRoom.getDoorCoordinates(i);
				bots.Bot1.Graph.Node n = g.getNode(temp.getCol() + 24*temp.getRow());
				
				if(g.heuristic(n, g.getNode(dest.getCol() + 24*dest.getRow())) < minVal){
    				minVal= g.heuristic(n, g.getNode(dest.getCol() + 24*dest.getRow()));
    				minIndex = n.index;
    				exitDoor = i;
    				System.out.println("");
    				//System.out.println("exitDoor: " + exitDoor);
    			}
			}
		}
		else {
			exitDoor = 0;
			//System.out.println("exitDoor: " + exitDoor);
		}
    }
    
    //    public static void main(String[] args) {
//    	Graph g = new Graph();
////    	for(bots.Bot1.Graph.Node curr : g.minPath(4+24*7,17+24*20)) {
////    		System.out.print(curr.index + "\t");
////    	}
////    	System.out.print("\n"+g.toStringPath(g.minPath(4+24*7,17+24*20))+"\n");
//    	
//    	ArrayList<bots.Bot1.Graph.Node> temp = new ArrayList<>();
//    	temp = g.minPath(0 + 24*7, 7 + 24*9);
////    	System.out.println(g.toStringPath(g.minPath(0 + 24*7, 7 + 24*9)));
//    	
////    	for(bots.Bot1.Graph.Node x : temp){
////    		System.out.println(x.index%24 + ", " + x.index/24);
////    	}
//    	for(bots.Bot1.Graph.Node x : temp){
//    		System.out.println(x.y + ", " + x.x);
//    	}
//    }

}
