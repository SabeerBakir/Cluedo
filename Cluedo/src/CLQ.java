
import java.util.Iterator;

public class CLQ<E extends Comparable<E>> implements Iterable<E> {
	
	// head node
	private Node<E> head;
	
	private int size;
	
	public CLQ() { // standard constructor
		head = new Node<>(null,null);
		size = 0;
	}
	
	public E first() { // first element
		if(isEmpty()) {
			return null;
		}
		return head.subsequent.element;
	}
	
	public E last() {
		if(isEmpty()) {
			return null;
		}
		if(size == 1) {
			return head.subsequent.element;
		}
		
		// Find which node points to the first node in the list
		Node<E> curr = head.subsequent.subsequent;
		while(curr.subsequent != head.subsequent) {
			curr = curr.subsequent;
		}
		return curr.element;
		
	}
	
	public Node<E> lastNode() {
		if(isEmpty()) {
			return null;
		}
		if(size == 1) {
			return head.subsequent;
		}
		
		// Find which node points to the first node in the list
		Node<E> curr = head.subsequent;
		while(curr.subsequent != head.subsequent) { //TODO
			System.out.println("Curr: " + curr.element + "\tNext: " + curr.subsequent.element);
			curr = curr.subsequent;
			//curr.subsequent = head.subsequent;
		}
		return curr;
		
	}
	
	private Node<E> secondLastNode() {
		
		if(size == 1) return head;
		
		// Find which node points to the last node in the list
		Node<E> curr = head.subsequent;
		while(curr.subsequent != lastNode()) {
			curr = curr.subsequent;
		}
		return curr;
		
	}
	
	public void enqueue(E element) { //TODO
		Node<E> newNode = new Node<>(element);
		newNode.subsequent = head;
		if(isEmpty()) {
			head.subsequent = newNode;
			newNode.subsequent = newNode;
		}
		else {
			newNode.subsequent = head.subsequent;
			head.subsequent = newNode;
			lastNode().subsequent = head.subsequent;
		}
		size++;
	}
	
	public E dequeue() { //TODO
		E e = null;
		if(isEmpty()) {
			return null;
		}
		else if(size == 1) {
			e = head.subsequent.element;
			head.subsequent = null;
		}
		else if(size > 1) {
			e = secondLastNode().element;
			secondLastNode().subsequent = head.subsequent;
		}
		size--;
		return e;
	}
	
	public Boolean isEmpty() {
		return size == 0;
	}

	@SuppressWarnings("unused")
	private static class Node<E> { // the node inner class
			
		// the element stored in this node
		private E element;
		
		// the reference to the next node
		private Node<E> subsequent;
		
		// standard constructor
		public Node(E e) {
			element = e;
			subsequent = null;
		}
		
		// subsequent constructor
		public Node(E e, Node<E> s) {
			element = e;
			subsequent = s;
		}
		
		public E getElement() { // returns node's element
			return element;
		}
			
		public Node<E> getSubsequent(){ // returns the next node
			return subsequent;
		}
			
		public void setSubsequent(Node<E> s) { // sets the next node
			subsequent = s;
		}
			
	}
	
	public Iterator<E> iterator() {
		return new ListIterator();
	}

	private class ListIterator implements Iterator<E> {

		Node<E> curr; // current node
		
		public ListIterator() {
			curr = head;
		}
		
		@Override
		public boolean hasNext() {
			return curr != head.subsequent;
		}

		@Override
		public E next() {
			E res = (E) curr.getElement();
			curr = curr.subsequent;
			return res;
		}
	}
	
	public String toString() {
			
			StringBuilder s = new StringBuilder();
			for(E element : this) {
				if(element != null) s.append(element + " ");
			}
			return s.toString();
			
	}
	
	public static void main(String[] args) {
		
		CLQ<Integer> list = new CLQ<Integer>();
		list.enqueue(1);
		System.out.println(list.first());
		System.out.println(list.head.subsequent.subsequent.element);
		list.enqueue(2);
		//list.enqueue(3);
		System.out.println(list.head.subsequent.subsequent.element);
		System.out.println(list.head.subsequent.subsequent.subsequent.element);
		System.out.println(list.dequeue());
		System.out.println(list.size);
		list.enqueue(4);
		System.out.println(list.dequeue());
		System.out.println(list.size);
		System.out.println(list.first());
		
	}

}
