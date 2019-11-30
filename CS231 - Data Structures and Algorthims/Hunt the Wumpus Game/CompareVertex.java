/**
* File: CompareVertex.java
* Class to create comparator for Vertices
* Author: Samuel Munoz
* Course: CS231
* Date: 11-26-2019
*/
import java.util.Comparator;

public class CompareVertex implements Comparator<Vertex> {
	public int compare(Vertex v1, Vertex v2) {
		if(v1.cost() == v2.cost())
			return 0;
		else if(v1.cost() < v2.cost())
			return 1;
		else
			return -1;
	}
}