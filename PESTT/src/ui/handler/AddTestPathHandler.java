package ui.handler;

import main.activator.Activator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import ui.constants.Description;
import ui.constants.Messages;
import ui.dialog.InputDialog;
import adt.graph.Graph;
import adt.graph.Path;

public class AddTestPathHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		if(Activator.getDefault().getSourceGraphController().numberOfNodes() >= 1) 
			addNewTestPath(window);
		else 
			MessageDialog.openInformation(window.getShell(), Messages.DRAW_GRAPH_TITLE, Messages.DRAW_GRAPH_MSG);
		return null;
	}
	
	private void addNewTestPath(IWorkbenchWindow window) throws ExecutionException {
		Graph<Integer> sourceGraph = Activator.getDefault().getSourceGraphController().getSourceGraph();
		String message = "Please enter a executed graph:\n(e.g. " + sourceGraph.getInitialNodes().iterator().next() + ", ..., " + sourceGraph.getFinalNodes().iterator().next() + ")";
		InputDialog dialog = new InputDialog(window.getShell(), message);
		dialog.open();
		String input = dialog.getInput();
		if(input != null)
			if(!input.equals(Description.EMPTY)) {
				Path<Integer> newTestPath = Activator.getDefault().getTestPathController().createTestPath(input);
				if(newTestPath != null)
					Activator.getDefault().getTestPathController().addTestPath(newTestPath);
				else {
					MessageDialog.openInformation(window.getShell(), Messages.TEST_PATH_TITLE, Messages.TEST_PATH_INVALID_INPUT_MSG); // message displayed when the inserted graph is not valid.
					addNewTestPath(window);
				}
			} else {
				MessageDialog.openInformation(window.getShell(), Messages.TEST_PATH_TITLE, Messages.TEST_PATH_INPUT_MSG); // message displayed when the inserted graph is empty.
				addNewTestPath(window);
			}
	}	
}
