package graphvisitors;

import sourcegraph.Edge;
import sourcegraph.Graph;
import sourcegraph.Node;

public class DotGraphVisitor<V> extends DepthFirstGraphVisitor<V> {

	private StringBuilder dotString = null;
	private Graph<V> graph;

	public DotGraphVisitor() {
		dotString = new StringBuilder();
	}
	
	@Override
	public void endVisit(Edge<V> edge) {
		dotString.append(edge.getBeginNode() + " -> " + edge.getEndNode() + "\n");
	}
	
	@Override
	public void endVisit(Node<V> node) {
		if(graph.getNodes().size() == 1)
			dotString.append(node.getValue() + " [style=filled, fillcolor=violet]\n");
		else if(graph.isInitialNode(node)) 
			dotString.append(node.getValue() + " [style=filled, fillcolor=green]\n");
		else if(graph.isFinalNode(node))
			dotString.append(node.getValue() + " [style=filled, fillcolor=red]\n");
	}
	
	@Override
	public boolean visit(Graph<V> graph) {
		this.graph = graph;
		return true;
	}
	
	public String getDotString() {
		return dotString.toString();
	}	
}