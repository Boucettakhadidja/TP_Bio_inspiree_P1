package Astar;

//import com.mxgraph.model.mxCell;
//import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import javax.swing.JFrame;


public class GraphVisualisation extends JFrame{
	 private static final long serialVersionUID = -8123406571694511514L;
	 private Object[] vertices;
	 
	   public GraphVisualisation(Node result) {
	     super("Visualisation du graphe Ex1");
	 
	     mxGraph graph = new mxGraph();
	     Object parent = graph.getDefaultParent();
	 
	     graph.getModel().beginUpdate();
	     try {
	       Object v1 = graph.insertVertex(parent, null, "S", 20, 100, 20, 20);
	       Object v2 = graph.insertVertex(parent, null, "A", 100, 20, 20, 20);
	       Object v3 = graph.insertVertex(parent, null, "B", 100, 180, 20, 20);
	       Object v4 = graph.insertVertex(parent, null, "C", 180, 100, 20, 20);
	       Object v5 = graph.insertVertex(parent, null, "G", 290, 100, 20, 20);
	       
	       graph.insertEdge(parent, null, "1", v1, v2);
	       graph.insertEdge(parent, null, "2", v1, v3);
	       graph.insertEdge(parent, null, "1", v2, v4);
	       graph.insertEdge(parent, null, "1", v3, v4);
	       graph.insertEdge(parent, null, "2", v4, v5);
	    // Mise à jour de la variable vertices
	       vertices = new Object[]{v1, v2, v3, v4, v5};
	     } finally {
	       graph.getModel().endUpdate();
	     }
	 
	     mxGraphComponent graphComponent = new mxGraphComponent(graph);
	     getContentPane().add(graphComponent);
	     if (result != null) {
	         highlightPath(graph, result);
	     }
	     System.out.println("\n");
	  // Appel de la méthode pour afficher la matrice d'adjacence
	     printAdjacencyMatrix(graph);
	   }
	   private void printAdjacencyMatrix(mxGraph graph) {
	       int[][] adjacencyMatrix = new int[6][6];

	       // Parcourt toutes les arêtes du graphe
	       for (Object edge : graph.getChildEdges(graph.getDefaultParent())) {
	           // Récupère les cellules source et target de l'arête
	           Object source = graph.getModel().getTerminal(edge, true);
	           Object target = graph.getModel().getTerminal(edge, false);

	           // Obtient les indices associés aux cellules source et target
	           int sourceIndex = getIndexFromVertex(source);
	           int targetIndex = getIndexFromVertex(target);

	           // Met à jour la matrice d'adjacence
	           adjacencyMatrix[sourceIndex][targetIndex] = 1;
	           adjacencyMatrix[targetIndex][sourceIndex] = 1; // Si le graphe est non orienté
	       }

	       // Affiche la matrice d'adjacence dans la console
	       System.out.println("Matrice d'adjacence :");
	       for (int[] row : adjacencyMatrix) {
	           for (int value : row) {
	               System.out.print(value + " ");
	           }
	           System.out.println();
	       }
	   }

	   private int getIndexFromVertex(Object vertex) {
	       // Recherche l'index de la cellule dans le tableau vertices
	       for (int i = 0; i < vertices.length; i++) {
	           if (vertices[i].equals(vertex)) {
	               return i;
	           }
	       }
	       return -1;
	   }
	   private void highlightPath(mxGraph graph, Node result) {
	       // Highlight the path in red
	       for (Object cell : graph.getChildVertices(graph.getDefaultParent())) {
	           String nodeName = graph.getModel().getValue(cell).toString();
	           if (result.pathContains(nodeName)) {
	               graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red", new Object[]{cell});
	               graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "red", new Object[]{cell});
	           }
	       }
	   }

	   public static void main(String[] args) {
	  GraphVisualisation frame = new GraphVisualisation(null);
	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   frame.setSize(400, 320);
	   frame.setVisible(true);
	}
	}
