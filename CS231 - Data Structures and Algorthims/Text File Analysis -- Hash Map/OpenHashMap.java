/**
* File: OpenHashMap.java
* Creates an open hash map
* Author: Samuel Munoz
* Course: CS231
* Date: 10-28-2019
*/
import java.util.ArrayList;

public class OpenHashMap<K,V> implements MapSet<K,V> {

	// array of BSTMap that becomes my hash map
	private BSTMap<K,V>[] map;
	
	// holds the size of the array
	private int size;
	
	// may need to raise to higher power of 2 and/or change this into a prime number
	private final int DEFAULT_CAPACITY = 1024;

	// holds the number of collusions that occur in this hash map
	private int numOfCollusions; 

	// constructor: creates an empty hash map
	public OpenHashMap() {
		this.map = new BSTMap[DEFAULT_CAPACITY]; // https://learning.oreilly.com/library/view/learning-java-4th/9781449372477/ch08s10.html
		this.size = 0;
		this.numOfCollusions = 0;
	}

	// constructor: creates an empty hash map with a given array size
	public OpenHashMap(int capacity) {
		this.map = new BSTMap[capacity];
		this.size = 0;
		this.numOfCollusions = 0;
	}

	// generates a hash code 
	public int hashCode(String str) {
		return Math.abs(str.hashCode()) % this.map.length; // may need to modify
	}

	// adds or updates a key-value pair
    public V put( K new_key, V new_value ) { 
    	int hash = this.hashCode(new_key.toString());
    	if(this.map[hash] == null) {
    		this.map[hash] = new BSTMap<K,V>(new AscendingString());
            this.size++;
    	}
    	else if(!this.map[hash].containsKey(new_key))
    		this.numOfCollusions++;
		
    	V returnV = this.map[hash].put(new_key,new_value);

        if(this.map[hash].size() == 50000)
            this.map[hash].rebalanceTree();

    	return returnV;
    } 
    
    // Returns true if the map contains a key-value pair with the given key
    public boolean containsKey( K key ) {
    	int hash = this.hashCode(key.toString());
    	if(this.map[hash] != null)
    		return this.map[hash].containsKey(key);
    	return false;
    }
    
    // Returns the value associated with the given key.
    public V get( K key ) { 
    	 int hash = this.hashCode(key.toString());
    	 if(this.map[hash] != null)
    	 	return this.map[hash].get(key);
    	 return null;
    }
    
    // Returns an ArrayList of all the keys in the map. There is no
    public ArrayList<K> keySet() { 
    	ArrayList list = new ArrayList<K>();
    	for(BSTMap m : this.map)
    		if(m != null)
    			list.addAll(m.keySet()); // https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html#addAll-java.util.Collection-
    	return list;
    }

    // Returns an ArrayList of all the values in the map. These should
    public ArrayList<V> values() { 
    	ArrayList list = new ArrayList<V>();
    	for(BSTMap m : this.map)
    		if(m != null)
    			list.addAll(m.values());
    	return list;
    }
    
    // return an ArrayList of pairs.
    public ArrayList<KeyValuePair<K,V>> entrySet() { 
    	ArrayList<KeyValuePair<K,V>> list = new ArrayList<KeyValuePair<K,V>>();
    	for(BSTMap<K,V> m : this.map) {
    		if(m != null) {
    			list.addAll(m.entrySet());
    			// ArrayList<KeyValuePair<K,V>> newElements = m.entrySet();
    			// for(KeyValuePair<K,V> pair : newElements)
    			// 	list.add(pair);
    		}
    	}
    	return list; 
    }

    // Returns the capacity of the array
    public int capacity() { 
    	return this.map.length;
    }

    // Returns the number of key-value pairs in the map.
    public int size() {
    	return this.size;
    }
        
    // removes all mappings from this MapSet
    public void clear() {
    	this.map = new BSTMap[this.map.length];
    	this.size = 0;
    }

    // returns the number of collusions recorded in this hash table
    public int collusions() {
        return this.numOfCollusions;
    }

    public String toString() {
    	String returnString = "";
    	for(int index = 0;index < this.map.length;index++)
    		if(this.map[index] != null)
    			returnString += index + ":\n" + this.map[index] + "\n";
    	return returnString;
    }

    public static void main(String[] args) {
    	OpenHashMap<String,Integer> m = new OpenHashMap<String,Integer>(1000000);
    	m.put("a",new Integer(1));
		m.put("b",new Integer(2));
		m.put("c",new Integer(3));
		m.put("d",new Integer(4));
		m.put("e",new Integer(5));
		m.put("f",new Integer(6));
		m.put("g",new Integer(7));
		m.put("h",new Integer(8));
		// m.put("Aa",new Integer(8));
		// m.put("BB",new Integer(8));
		// m.put("i",new Integer(9));
		// m.put("j",new Integer(10));
		// m.put("k",new Integer(11));
		// m.put("l",new Integer(12));
		// m.put("m",new Integer(13));
		// m.put("n",new Integer(14));
		// m.put("o",new Integer(15));

    	
    	System.out.println(m.map.length + "\n");

    	System.out.println(m);

    	System.out.println("number of collusions: " + m.numOfCollusions);
    	System.out.println("containsKey: " + m.containsKey("a"));
    	System.out.println("get: " + m.get("b"));
    	System.out.println("keySet: " + m.keySet());
    	System.out.println("values: " + m.values());
    	System.out.println("EntrySet: " + m.entrySet());
    	System.out.println("size: " + m.size());

    	System.out.println("-----------------------------------------------------------------------------------------------------------");

    	m.clear();

		System.out.println("size: " + m.size());    	
    }
}