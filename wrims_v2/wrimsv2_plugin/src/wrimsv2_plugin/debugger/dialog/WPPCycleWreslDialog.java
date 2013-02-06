package wrimsv2_plugin.debugger.dialog;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import wrimsv2.commondata.wresldata.StudyDataSet;
import wrimsv2.debug.Compile;
import wrimsv2_plugin.debugger.core.DebugCorePlugin;
import wrimsv2_plugin.debugger.exception.WPPException;
import wrimsv2_plugin.debugger.menuitem.EnableMenus;
import wrimsv2_plugin.debugger.model.WPPDebugTarget;
import wrimsv2_plugin.debugger.view.WPPFileIncExploreView;
import wrimsv2_plugin.debugger.view.WPPVarDetailView;
import wrimsv2_plugin.tools.FileProcess;

public class WPPCycleWreslDialog extends PopupDialog {
	
	public WPPCycleWreslDialog(Shell parent, int shellStyle,
			boolean takeFocusOnOpen, boolean persistSize,
			boolean persistLocation, boolean showDialogMenu,
			boolean showPersistActions, String titleText, String infoText) {
		super(parent, shellStyle, takeFocusOnOpen, persistSize, persistLocation,
				showDialogMenu, showPersistActions, titleText, infoText);
		// TODO Auto-generated constructor stub
	}
	
	public void open(int i){
		create();
		getShell().setSize(250, 150);
		open();
	}

	@Override
	 protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		GridLayout layout=new GridLayout(2, false);
		layout.marginWidth=20;
		layout.marginHeight=20;
		dialogArea.setLayout(layout);
		
		GridData gridData=new GridData(GridData.FILL_BOTH);
		gridData.horizontalSpan=1;
		
		Label label1 = new Label(dialogArea, SWT.NONE);
		label1.setText("Cycle:");
		
		final Combo cycleCombo = new Combo(dialogArea, SWT.BORDER);
		for (int i=1; i<=99; i++){
			cycleCombo.add(String.valueOf(i));
		}
		cycleCombo.select(0);
		
		Button ok = new Button(dialogArea, SWT.PUSH);
		ok.setText("OK");
		ok.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				int cycleIndex = Integer.parseInt(cycleCombo.getText())-1;
				procCycleWresl(cycleIndex);
				close();
			}
		});
		
		Button cancel = new Button(dialogArea, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent event){
				close();
			}
		});
		
		dialogArea.getShell().setDefaultButton(ok);
		return dialogArea;
	 }
	
	public void procCycleWresl(final int index){
		WPPDebugTarget target = DebugCorePlugin.target;
		final String path = DebugCorePlugin.cycleWreslMainFilePath;
		
		if (path.equals("")){
			close();
		}else{
			final IWorkbench workbench=PlatformUI.getWorkbench();
			workbench.getDisplay().asyncExec(new Runnable(){
				public void run(){
					final Shell shell=workbench.getActiveWorkbenchWindow().getShell();
					ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);  
					try {
						dialog.run(true,false, new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor) {
								monitor.beginTask("Update explorer for included WRESL files", 100);
								StudyDataSet sds=new StudyDataSet();
								try {
									sds = Compile.checkStudy(path, true);
								} catch (IOException e) {
									WPPException.handleException(e);
								}
								monitor.worked(50);
								if (index>sds.getModelList().size()-1){
									monitor.done();
									displayOutOfRangeMessage();
									return;
								}
								ArrayList<String> fns=FileProcess.getCycleWreslFiles(path, sds, index);
								ArrayList<String> tfns=FileProcess.getTableFiles(path);
								fns.addAll(tfns);
								monitor.worked(20);
								DebugCorePlugin.fileFolderWreslInc=FileProcess.retrieveFileNames(fns);
								monitor.worked(20);
								UpdateWreslIncExplore();
								monitor.done();
							}
						});
					} catch (InvocationTargetException e) {
						WPPException.handleException(e);
					} catch (InterruptedException e) {
						WPPException.handleException(e);
					}
				}
			});
		};
	}
	
	public void UpdateWreslIncExplore(){
		final IWorkbench workbench=PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable(){
			public void run(){
				IWorkbenchPage workBenchPage = workbench.getActiveWorkbenchWindow().getActivePage();
				WPPFileIncExploreView wreslIncExplore;
				try {
					wreslIncExplore = (WPPFileIncExploreView) workBenchPage.showView(DebugCorePlugin.ID_WPP_FILEINCEXPLORE_VIEW);
					wreslIncExplore.update();
				} catch (PartInitException e) {
					WPPException.handleException(e);
				}
			}
		});
	}
	
	public void displayOutOfRangeMessage(){
		final IWorkbench workbench=PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable(){
			public void run(){
				final Shell shell=workbench.getActiveWorkbenchWindow().getShell();
				MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK );  
				messageBox.setText("Warning");
				messageBox.setMessage("Cycle index is out of the range of the model study");
				messageBox.open();
			}
		});
	}
}