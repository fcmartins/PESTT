package ui.display.views.structural;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

import main.activator.Activator;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchPartSite;

import ui.constants.Description;
import ui.constants.TableViewers;
import adt.graph.Graph;
import adt.graph.Path;
import domain.events.TestPathChangedEvent;

public class TestPathsViewer extends AbstractTableViewer implements ITableViewer, Observer {

	private Composite parent;
	private TableViewer testPathhsViewer;
	private Control testPathsControl; // control of executedGraphViewer.
	private IWorkbenchPartSite site;
	
	public TestPathsViewer(Composite parent, IWorkbenchPartSite site) {
		this.parent = parent;
		this.site = site;
		Activator.getDefault().getTestPathController().addObserverTestPath(this);
	}
	
	public TableViewer create() {
		testPathhsViewer = createViewTable(parent, site, TableViewers.TESTPATHSVIEWER);
		testPathsControl = testPathhsViewer.getControl();
		createColumnsToExecutedGraphViewer();
		setSelections(); // associate path to the ViewGraph elements.
		return testPathhsViewer;
	}

	@Override
	public void update(Observable obs, Object data) {
		if(data instanceof TestPathChangedEvent) {
			List<Object> testPaths = new ArrayList<Object>();
			for(Path<Integer> path : ((TestPathChangedEvent) data).testPathSet)
				testPaths.add(path);
			if(testPaths.size() > 1)
				testPaths.add(Description.TOTAL);
			testPathhsViewer.setInput(testPaths);
		}
	}

	public void dispose() {
		testPathsControl.dispose();
	}
	
	private void createColumnsToExecutedGraphViewer() {
		String columnNames = TableViewers.TEST_PATTHS; // the names of column.
		int columnWidths = 500; // the width of column.
		TableViewerColumn col = createColumnsHeaders(testPathhsViewer, columnNames, columnWidths, 0);
		col.setLabelProvider(new StyledCellLabelProvider() {

			@Override
			public void update(ViewerCell cell) {
				if(cell.getElement() instanceof Graph<?>) {
					Graph<?> graph = (Graph<?>) cell.getElement();
					cell.setText(graph.toString());
				} else if(cell.getElement() instanceof Path<?>) {
					Path<?> path = (Path<?>) cell.getElement();
					cell.setText(path.toString());
				} else {
					String str = (String) cell.getElement();
					cell.setText(str);
				}
			}
		});
	}	
	
	private void setSelections() {
		testPathhsViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			@SuppressWarnings("unchecked")
			public void selectionChanged(final SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection(); // get the selection.
				boolean hasTotal = false;
				Set<Path<Integer>> testPaths = new TreeSet<Path<Integer>>();
				for(Object obj : selection.toList())
					if(obj instanceof String) {
						hasTotal = true;
						break;
					}
					else
						testPaths.add((Path<Integer>) obj);
		
				if(hasTotal) {
					testPaths.clear();
					for(Path<Integer> path : Activator.getDefault().getTestPathController().getTestPaths())
						testPaths.add(path);
				}
					
				Activator.getDefault().getTestPathController().selectTestPath(testPaths);
		    }
		});
	}
}