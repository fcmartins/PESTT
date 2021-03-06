package coverage;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.eclipse.jdt.core.dom.ASTNode;

import sourcegraph.Graph;
import sourcegraph.Node;
import sourcegraph.Path;
import view.GraphsCreator;
import constants.Colors_ID;
import constants.Graph_ID;
import constants.Layer_ID;
import editor.Line;

public class FakeCoverageData implements ICoverageData {

	private HashMap<Integer, String> lineStatus;
	
	public FakeCoverageData(Path<Integer> fakeExecutedPath) {
		lineStatus = new LinkedHashMap<Integer, String>();
		setLineStatus(fakeExecutedPath);
	}
	
	public HashMap<Integer, String> getLineStatus() {
		return lineStatus;
	}
	
	public String getLineStatus(int line) {
		return lineStatus.get(line);
	}
	
	@SuppressWarnings("unchecked")
	private void setLineStatus(Path<Integer> fakeExecutedPath) {
		Graph<Integer> sourceGraph = (Graph<Integer>) GraphsCreator.INSTANCE.getGraphs().get(Graph_ID.SOURCE_GRAPH_NUM);
		sourceGraph.selectMetadataLayer(Layer_ID.INSTRUCTIONS); // select the layer to get the information.
		for(Node<Integer> node : sourceGraph.getNodes()) {
			LinkedHashMap<ASTNode, Line> map = (LinkedHashMap<ASTNode, Line>) sourceGraph.getMetadata(node); // get the information in this layer to this node.
			if(map != null) 
				for(Entry<ASTNode, Line> entry : map.entrySet()) {
					int line = entry.getValue().getStartLine();
					if(fakeExecutedPath.containsNode(node)) 
						lineStatus.put(line, Colors_ID.GRENN_ID);
					else 
						lineStatus.put(line, Colors_ID.RED_ID);
				}
		}
	}
}
