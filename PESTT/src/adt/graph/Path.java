package adt.graph;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Path<V extends Comparable<V>> extends AbstractPath<V> {
		
	private List<Node<V>> nodes;

	// pre: nodes.size() > 0
	public Path(Collection<Node<V>> nodes) {
		this.nodes = new LinkedList<Node<V>>(nodes);
	}
	
	@Override
	public Iterator<Node<V>> iterator() {
		return nodes.iterator();
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("[");
		Iterator<Node<V>> it = iterator();
		s.append(it.next()); // a path has always one node, at least!
		while(it.hasNext())
			s.append(", " + it.next());
		s.append("]");
		return s.toString();
	}
	

	@Override
	public boolean isSubPath(AbstractPath<V> other) {
		if (other instanceof InfinitePath)
			return false;
		Path<V> path = (Path<V>) other; 
		int i = 0;
		for (Iterator<Node<V>> it = nodes.iterator(); it.hasNext(); i++, it.next()) 
			if (isConsecutive(i, path))
				return true;
		return false;
	}
	
	private boolean isConsecutive(int i, Path<V> path) {
		Iterator<Node<V>> it = path.iterator();
		int size = path.nodes.size();
		if(nodes.size() >= size) {
			while(i < nodes.size() && it.hasNext()) {
				if(nodes.get(i) != it.next())
					return false;
				i++;
			}
			return it.hasNext() ? false : true;	
		}
		return false;
	}

	
	@Override
	public boolean toursWithSideTrip(AbstractPath<V> other) {
		if (other instanceof InfinitePath)
			return false;
		Path<V> path = (Path<V>) other; 
		int i = 0;
		for (Iterator<Node<V>> it = nodes.iterator(); it.hasNext(); i++, it.next()) 
			if (isConsecutiveSideTrip(i, path))
				return true;
		return false;
	}

	private boolean isConsecutiveSideTrip(int i, Path<V> path) {
		Iterator<Node<V>> it = path.iterator();
		while (i < nodes.size() && it.hasNext()) {
			Node<V> node = it.next();
			if (nodes.get(i) != node) {
				// try advance loop
				Node<V> currentNode = nodes.get(i);
				while (i < nodes.size() & nodes.get(i) != currentNode)
					i++;
				if (i == nodes.size())
					return false;
				// if found loop, compare the next node in the path
				i++;
				if (i < nodes.size() && nodes.get(i) != nodes)
					return false;
			}
			i++;
		}
		return true;
	}

	
	@Override
	public boolean toursWithDetour(AbstractPath<V> other) {
		if (other instanceof InfinitePath)
			return false;
		Path<V> path = (Path<V>) other; 
		int index = 0;
		for(Node<V> node : path) {
			while(index < nodes.size() && nodes.get(index) != node)
				index++;
			if(index == nodes.size())
				return false;
		}
		return true;
	}
}