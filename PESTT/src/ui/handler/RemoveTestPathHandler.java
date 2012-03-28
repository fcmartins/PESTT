package ui.handler;

import main.activator.Activator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import ui.constants.Messages;
import ui.dialog.RemoveDialog;
import adt.graph.Path;

public class RemoveTestPathHandler extends AbstractHandler {
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		if(Activator.getDefault().getEditorController().isEverythingMatching())
			if(Activator.getDefault().getSourceGraphController().numberOfNodes() >= 1)
				removeTestPath(window);
			else 
				MessageDialog.openInformation(window.getShell(), Messages.DRAW_GRAPH_TITLE, Messages.DRAW_GRAPH_MSG);
		else
			MessageDialog.openInformation(window.getShell(), Messages.DRAW_GRAPH_TITLE, Messages.GRAPH_UPDATE_MSG);
		return null;
	}
	
	private void removeTestPath(IWorkbenchWindow window) throws ExecutionException {
		if(Activator.getDefault().getTestPathController().isTestPathSelected()) {
			String pathMessage = "";
			for(Path<Integer> path : Activator.getDefault().getTestPathController().getSelectedTestPaths())
				pathMessage += path.toString() +"\n";
			String message = "Are you sure that you want to delete this graph:\n" + pathMessage;
			RemoveDialog dialog = new RemoveDialog(window.getShell(), message);
			dialog.open();
			String input = dialog.getInput();
			if(input != null) {
				Activator.getDefault().getTestPathController().removeCoverageData();
				Activator.getDefault().getTestPathController().removeTestPath();
				MessageDialog.openInformation(window.getShell(), Messages.TEST_PATH_TITLE, Messages.TEST_PATH_SUCCESS_REMOVE_MSG); // message displayed when the graph is successfully remove.
			}
		} else
			MessageDialog.openInformation(window.getShell(), Messages.TEST_PATH_TITLE, Messages.TEST_PATH_SELECT_TO_REMOVE_MSG); // message displayed when there is no graph selected to be removed.
	}		
}