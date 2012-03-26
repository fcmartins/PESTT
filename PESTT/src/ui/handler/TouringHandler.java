package ui.handler;

import main.activator.Activator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

import domain.constants.TourType;

import ui.constants.Description;
import ui.constants.Messages;

public class TouringHandler extends AbstractHandler {

	private String option = Description.EMPTY;
	private String old = TourType.TOUR.toString(); 
	private boolean flag = false;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if(!flag)
			if(Activator.getDefault().getSourceGraphController().numberOfNodes() >= 1) {
				option = event.getParameter(RadioState.PARAMETER_ID); // get the current selected state.
				if(!option.equals(old)) {
					if(option != null && !option.equals(Description.NONE)) {
						HandlerUtil.updateRadioState(event.getCommand(), option); // update the current state.
						old = option;
					}
					Activator.getDefault().getTestPathController().selectTourType(old);
				}
			} else {
				flag = true;
				IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
				MessageDialog.openInformation(window.getShell(), Messages.DRAW_GRAPH_TITLE, Messages.DRAW_GRAPH_MSG); // message displayed when the graph is not designed.
			}
		return null;
	}
}