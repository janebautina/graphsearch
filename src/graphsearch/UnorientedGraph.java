package graphsearch;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.NavigableSet;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;


public class UnorientedGraph {
  private Map<Integer, NavigableSet<Integer>> neighbors = 
      new TreeMap<Integer, NavigableSet<Integer>>();
  
  private static final NavigableSet<Integer> EMPTY_SORTED_SET = 
      new TreeSet<Integer>();
      
  public void addEdge(int a, int b) {
    addNeighbor(a, b);
    addNeighbor(b, a);
  }
  
  private void addNeighbor(int a, int b) {
    NavigableSet<Integer> neighborsA = neighbors.get(a);
    if (neighborsA == null) {
      neighborsA = new TreeSet<Integer>();
      neighbors.put(a, neighborsA);
    }
    neighborsA.add(b); 
  }
  
  @Override
  public String toString() {
    StringBuilder stb = new StringBuilder();
    for (int x: neighbors.keySet()) {
      stb.append(x);
      stb.append(":");
      for (int y: neighbors.get(x)) {
        stb.append(" ");
        stb.append(y);
      }
      stb.append("\n");
    }
    return stb.toString();
  }
  
  public boolean isNeighbor(int a, int b) {
    Set<Integer> neighborsA = neighbors.get(a);
    if (neighborsA == null) {
      return false;
    }
    return neighborsA.contains(b);
  }
  
  public NavigableSet<Integer> getNeighbors(int a) {
    NavigableSet<Integer> neighborsA = neighbors.get(a); 
    if (neighborsA != null) {
      return neighborsA;
    }
    return EMPTY_SORTED_SET;
  }
  
  public List<Integer> depthFirstSearch(int a) {
    List<Integer> list = new LinkedList<Integer>();
    Stack<Integer> stack = new Stack<Integer>();
    Set<Integer> visited = new HashSet<Integer>();
    stack.push(a);
    while (!stack.isEmpty()) {
      int i = stack.pop();
      if (visited.contains(i)) {
        continue;
      }
      list.add(i);
      visited.add(i);
      for (int j: getNeighbors(i).descendingSet()) {
        stack.push(j);
      }
    }
    return list;
  }
}