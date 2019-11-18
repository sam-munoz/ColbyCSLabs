/**
* File: BSTMap.java
* Creates a binary tree
* Author: Samuel Munoz
* Course: CS231
* Date: 10-14-2019
*/
import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K,V> implements MapSet<K,V> {

	private TNode root; // holds the root Node of the tree
	private Comparator<String> compare; // this class will allow the program to gain access to the comparative methods that allow Strings to be compared
	private int size; // holds the number of TNodes in the tree
	private int depth; // holds the depth of the tree
	
	// this class represents a single node a binary tree
	private class TNode { // start of the TNode class
		KeyValuePair<K,V> pair; // holds key value pair for the node
		private TNode left; // pointer to the left child node
		private TNode right; // pointer to the right child node

		// creates a Node with a set that points to no other child nodes
		public TNode(K key, V value) {
			this.pair = new KeyValuePair<K,V>(key,value);
			this.left = null;
			this.right = null;
		}

		// returns the k field value
		public K getK() { return this.pair.getKey(); }

		// returns the v field value
		public V getV() { return this.pair.getValue(); }
		
		// returns the KeyValuePair the node holds
		public KeyValuePair<K,V> getEntry() { return this.pair; }

		// set the k value to a new value
		public void setK(K key) { this.pair.setKey(key); }

		// set the v value to a new value
		public void setV(V value) { this.pair.setValue(value); }

		// returns the left the child node
		public TNode getLeft() { return this.left; }

		// returns the right child node
		public TNode getRight() { return this.right; }

		// sets the left child node pointer to a new value
		public void setLeft(TNode left) { this.left = left; }

		// sets the right child node pointer to a new value
		public void setRight(TNode right) { this.right = right; }

		// creates a String representation of the TNode
		public String toString() { return "(" + this.getK() + "," + this.getV() + ")"; }

	} // end of the TNode class

	// creates an empty tree and intializes the comparator
	public BSTMap(Comparator<String> compare) {
		this.compare = compare;
		this.root = null;
		this.size = 0;
		this.depth = 0;
	}
	
	// this method makes the tree held by the object to become empty
	public void clear() {
		this.root = null;
		this.size = 0;
		this.depth = 0;
	}
	
	// this method creates the size of the tree
	public int size() {
		return this.size;
	}

	// this method returns the depth of the tree
	public int depth() {
		return this.depth;
	}

	// adds a new Node in the tree if the set does not exist in the tree, otherwise, this updates an existing node
	public V put(K newKey, V newValue) { 
		
		// node to traverse through tree
		TNode node = this.root;
		
		// check if root is null or not
		if(node == null) {
			this.root = new TNode(newKey,newValue);
			this.depth = 0;
			this.size++;
			return this.root.getV();	
		}
		
		// this holds the value of when we compare keys
		int compareValue;

		// this holds the number of travserals traveled when entering a new node. Starts at -1 to offset the addition done in the while loop.
		int depthTraveled = -1;
			
		// goes through the binary tree and checks if the key in the tree. If the next child is null, a new node is made with the parent class pointing to the new node
		while(true) {
			compareValue = this.compare.compare(newKey.toString(),node.getK().toString());
			if(++depthTraveled > this.depth)
				this.depth = depthTraveled;

			if(compareValue == 0) {
				node.setV(newValue);
				return node.getV();
			}
			else {
				// this.depth++;
				if(compareValue == -1) {
					if(node.getLeft() == null) {
						node.setLeft(new TNode(newKey,newValue));
						this.size++;
						return node.getLeft().getV();
					}
					node = node.getLeft();
					continue;
				}
				else {
					if(node.getRight() == null) {
						node.setRight(new TNode(newKey,newValue));
						this.size++;
						return node.getRight().getV();
					}
					node = node.getRight();
					continue;
				}
			}
		}
	}
	
	// Returns the value associated with the given key. If that key is not in the map, then it returns null.
    public V get( K key ) {
		TNode node = this.root;
		while(node != null) {
			int compareValue = this.compare.compare(key.toString(),node.getK().toString());
			if(compareValue == 0)
				return node.getV();
			else if(compareValue == -1)
				node = node.getLeft();
			else
				node = node.getRight();
		}
		return null;
	}
	
	// returns true if the map already contains a node with the specified key.
	public boolean containsKey( K key ) {
		TNode node = this.root;
		while(node != null) {
			int compareValue = this.compare.compare(key.toString(),node.getK().toString());
			if(compareValue == 0) // 
				return true;
			else if(compareValue == -1)
				node = node.getLeft();
			else
				node = node.getRight();
		}
		return false;
	}
	
	// recursively traverses through the tree in a pre-order fashion.
	private void traversePre(TNode node, ArrayList list, int type) { // https://stackoverflow.com/questions/5683541/how-can-i-pass-a-generic-class-to-a-method-in-java
		if(node != null) {
			if(type == 2) // calls from entrySet
				((ArrayList<KeyValuePair<K,V>>) list).add(node.getEntry());
			else if(type == 1) // calls from values
				((ArrayList<V>) list).add(node.getV());
			else // calls from keySet
				((ArrayList<K>) list).add(node.getK());
			traversePre(node.getLeft(),list,type);
			traversePre(node.getRight(),list,type);
		}
	}
	
	// Returns an ArrayList of all the keys in the map. There is no defined order for the keys.
    public ArrayList<K> keySet() {
		ArrayList<K> list = new ArrayList<K>();
		traversePre(this.root,list,0);
		return list;
	}
	
	// Returns an ArrayList of all the values in the map. These should be in the same order as the keySet.
    public ArrayList<V> values() {
		ArrayList<V> list = new ArrayList<V>();
		traversePre(this.root,list,1);
		return list;
	}

	// this ArrayList holds all the pairs in the nodes. The order of the pairs are in pre-order order
	public ArrayList<KeyValuePair<K,V>> entrySet() {
		ArrayList<KeyValuePair<K,V>> list = new ArrayList<KeyValuePair<K,V>>();
		traversePre(this.root,list,2);
		return list;
	}
	
	// recursively traverses through a tree in a in-order fashion
	private void traverseOrder(TNode node, ArrayList<TNode> list) {
		if(node != null) {
			traverseOrder(node.getLeft(),list);
			list.add(node);
			traverseOrder(node.getRight(),list);
		}
	}
	
	// this method will rebalances the tree to the best of the tree's ability
	public void rebalanceTree() {
		ArrayList<TNode> inOrderList = new ArrayList<TNode>();
		this.traverseOrder(this.root,inOrderList); // this sets the ArrayList the list of the Nodes in order

		if(inOrderList.size() != 0) {
			ArrayList<Integer> indexInTree = new ArrayList<Integer>();

			indexInTree.add(inOrderList.size()/2);
			this.root = inOrderList.get(inOrderList.size()/2); // sets new root node
			this.root.setLeft(null);
			this.root.setRight(null);

			// find depth of balance tree
			double calcDepth = Math.log(inOrderList.size()+1) / Math.log(2); // https://www.khanacademy.org/math/algebra2/x2ec2f6f830c9fb89:logs/x2ec2f6f830c9fb89:change-of-base/v/change-of-base-formula : I did not remember what the change of base formula was
			int depth;
			if((int) calcDepth == calcDepth)
				depth = (int) calcDepth; // perfect square situation
			else
				depth = (int) calcDepth + 1; // non-perfect square situation

			int newIndexNode = 0;
			this.depth = 0;
			for(int layer = 1;layer < depth;layer++) {
				for(int index = 0;index <= indexInTree.size();index++) {
					// System.out.println("Start of loop: index = " + index);
					if(index == 0) { // first element
						newIndexNode = (indexInTree.get(index).intValue() - 0)/2;
						// System.out.println("Start: " + indexInTree.get(index).intValue() + " - 0\t\t" + newIndexNode);
						this.put(inOrderList.get(newIndexNode).getK(),inOrderList.get(newIndexNode).getV());
						indexInTree.add(index,newIndexNode);
						index++;
						// System.out.println(indexInTree + "\tindex: " + index + "\n");
					}
					else if(index == indexInTree.size()) { // last element
						newIndexNode = (inOrderList.size() - indexInTree.get(index-1).intValue())/2 + indexInTree.get(index-1).intValue();
						// System.out.println("End: " + inOrderList.size() + " - " + indexInTree.get(index-1).intValue() + "\t\t " + newIndexNode);
						this.put(inOrderList.get(newIndexNode).getK(),inOrderList.get(newIndexNode).getV());
						indexInTree.add(newIndexNode);
						index++;
						// System.out.println(indexInTree + "\tindex: " + index + "\n");
					}
					else { // elements between the first and last element
						newIndexNode = (indexInTree.get(index).intValue() - indexInTree.get(index-1).intValue())/2 + indexInTree.get(index-1).intValue();
						// System.out.println("Between: " + indexInTree.get(index).intValue() + " - " + indexInTree.get(index-1).intValue() + "\t\t" + newIndexNode);
						this.put(inOrderList.get(newIndexNode).getK(),inOrderList.get(newIndexNode).getV());
						indexInTree.add(index,newIndexNode);
						index++;
						// System.out.println(indexInTree + "\tindex: " + index + "\n");
					}
				}
			}
		}
	}
	
	private void toString(TNode node, String[] returnStringAndTabs) {			
		if(node != null) {
			if(node != this.root)
				returnStringAndTabs[1] += "\t\t";
			returnStringAndTabs[0] += returnStringAndTabs[1] + node + "\n";
			if(node.getLeft() != null)
				returnStringAndTabs[0] += "left: ";
			this.toString(node.getLeft(),returnStringAndTabs);
			if(node.getRight() != null)
				returnStringAndTabs[0] += "right: ";
			this.toString(node.getRight(),returnStringAndTabs);
			if(returnStringAndTabs[1].length() >= 2)
				returnStringAndTabs[1] = returnStringAndTabs[1].substring(0,returnStringAndTabs[1].length()-2);
		}
	}

	public String toString() {
		String[] returnStringAndTabs = {"root: ",""}; // https://stackoverflow.com/questions/1270760/passing-a-string-by-reference-in-java
		this.toString(this.root,returnStringAndTabs);
		return returnStringAndTabs[0];
	}
	
	// debugging main method
	public static void main(String[] args) {
		BSTMap<String,Integer> tree = new BSTMap<String,Integer>(new AscendingString());
		tree.put("a",new Integer(1));
		tree.put("b",new Integer(2));
		tree.put("c",new Integer(3));
		tree.put("d",new Integer(4));
		tree.put("e",new Integer(5));
		tree.put("f",new Integer(6));
		tree.put("g",new Integer(7));
		// tree.put("h",new Integer(8));
		// tree.put("i",new Integer(9));
		// tree.put("j",new Integer(10));
		// tree.put("k",new Integer(11));
		// tree.put("l",new Integer(12));
		// tree.put("m",new Integer(13));
		// tree.put("n",new Integer(14));
		// tree.put("o",new Integer(15));
		// tree.put("p",new Integer(100));
		// tree.put("q",new Integer(100));
		// tree.put("r",new Integer(100));
		// tree.put("s",new Integer(100));
		// tree.put("t",new Integer(100));
		// tree.put("u",new Integer(100));
		// tree.put("v",new Integer(100));
		// tree.put("w",new Integer(100));
		// tree.put("x",new Integer(100));
		// tree.put("y",new Integer(100));
		// tree.put("z",new Integer(100));
		// tree.put("ab",new Integer(100));
		// tree.put("bc",new Integer(100));
		// tree.put("cd",new Integer(100));
		// tree.put("ef",new Integer(100));
		// tree.put("fg",new Integer(100));
		// System.out.println("All pairs: " + tree.entrySet());
		System.out.println(tree);
		System.out.println(tree.depth());

		// System.out.println(tree.keySet());
		// System.out.println(tree.values());
		// System.out.println(tree.entrySet());

		tree.rebalanceTree();
		
		System.out.println(tree);

		System.out.println(tree.depth());
		
		// System.out.println(tree.entrySet());
	}
}