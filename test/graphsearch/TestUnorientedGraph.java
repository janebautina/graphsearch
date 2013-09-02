package graphsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class TestUnorientedGraph {
  @Test 
  public void testAddEdge() {
    UnorientedGraph gr = new UnorientedGraph();
    gr.addEdge(1, 2);
    gr.addEdge(2, 3);
    gr.addEdge(3, 1);
    assertTrue(gr.isNeighbor(1, 2));
    assertTrue(gr.isNeighbor(2, 1));
    assertTrue(gr.isNeighbor(2, 3));
    assertTrue(gr.isNeighbor(3, 2));
    assertTrue(gr.isNeighbor(1, 3));
    assertTrue(gr.isNeighbor(3, 1));
    assertFalse(gr.isNeighbor(1, 4));
  }
  
  private UnorientedGraph createTestGraph() {
    UnorientedGraph gr = new UnorientedGraph();
    gr.addEdge(1, 2);
    gr.addEdge(3, 4);
    gr.addEdge(1, 3);
    gr.addEdge(3, 5);
    gr.addEdge(5, 1);
    return gr;
  }
  
  @Test
  public void testToString() {
    UnorientedGraph gr = createTestGraph();
    assertEquals("1: 2 3 5\n2: 1\n3: 1 4 5\n4: 3\n5: 1 3\n", gr.toString());
  }
  
  @Test
  public void testGetNeighbors() {
    UnorientedGraph gr = createTestGraph();
    Set<Integer> neighborsA = gr.getNeighbors(1);
    Set<Integer> expected = new TreeSet<Integer>();
    expected.add(2);
    expected.add(3);
    expected.add(5);
    assertEquals(expected, neighborsA);
  }
  
  @Test
  public void testDepthFirstSearch() {
    UnorientedGraph gr = createTestGraph();
    Integer[] expected = new Integer[] { 1, 2, 3, 4, 5 };
    assertEquals(Arrays.asList(expected), gr.depthFirstSearch(1));
    assertEquals(Arrays.asList(expected), gr.recursiveDepthFirstSearch(1));
    
    expected = new Integer[] { 3, 1, 2, 5, 4 };
    assertEquals(Arrays.asList(expected), gr.depthFirstSearch(3));
    assertEquals(Arrays.asList(expected), gr.recursiveDepthFirstSearch(3));
  }

  @Test
  public void testBreadthFirstSearch() {
    UnorientedGraph gr = createTestGraph();
    List<Integer> result = gr.breadthFirstSearch(1);
    Integer[] expected = new Integer[] { 1, 2, 3, 5, 4 };
    assertEquals(Arrays.asList(expected), result);

    result = gr.breadthFirstSearch(3);
    expected = new Integer[] { 3, 1, 4, 5, 2 };
    assertEquals(Arrays.asList(expected), result);

  }

  @Test
  public void testDistance() {
    UnorientedGraph gr = createTestGraph();
    assertEquals(0, gr.distance(1, 1));
    assertEquals(1, gr.distance(1, 2));
    assertEquals(2, gr.distance(1, 4));  
  }
}
