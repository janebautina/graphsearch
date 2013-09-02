package graphsearch;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
  
  private void traverse(int a, List<Integer> result, Set<Integer> visited) {
    if (visited.contains(a)) {
      return;
    }
    visited.add(a);
    result.add(a);
    for (int b: getNeighbors(a)) {
      traverse(b, result, visited);
    }
  }
  
  public List<Integer> recursiveDepthFirstSearch(int a) {
    List<Integer> list = new LinkedList<Integer>();
    Set<Integer> visited = new HashSet<Integer>();
    traverse(a, list, visited);
    return list;
  }
  
  public List<Integer> breadthFirstSearch(int a) {
    List<Integer> list = new LinkedList<Integer>();
    Queue<Integer> queue = new LinkedList<Integer>();
    Set<Integer> visited = new HashSet<Integer>();
    queue.add(a);
    while (!queue.isEmpty()) {
      int i = queue.poll();
      if (visited.contains(i)) {
        continue;
      }
      list.add(i);
      visited.add(i);
      for (int j: getNeighbors(i)) {
        queue.add(j);
      }
    }
    return list;
  }
  
  public int distance(int a, int b) {
    Queue<Pair<Integer, Integer>> queue = new LinkedList<Pair<Integer, Integer>>();
    Set<Integer> visited = new HashSet<Integer>();
    queue.add(Pair.createPair(a, 0));
    while (!queue.isEmpty()) {
      Pair<Integer, Integer> iAndDist = queue.poll();
      int i = iAndDist.getElement0();
      final int dist = iAndDist.getElement1();
      if (visited.contains(i)) {
        continue;
      }
      if (i == b) {
        return dist;
      }
      visited.add(i);
      for (int j: getNeighbors(i)) {
        queue.add(Pair.createPair(j, dist + 1));
      }
    }
    return -1;
  }
}