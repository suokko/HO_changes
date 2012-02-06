package ho.core.module;

import javax.swing.JMenu;
import javax.swing.JPanel;

public abstract class DefaultModule implements IModule {

	private boolean isStartUp = false;
	private boolean isActive = false;
	public abstract int getModuleId();
	public abstract String getDescription();
	public abstract JPanel createTabPanel();

	protected DefaultModule(){
		
	}
	
	protected DefaultModule(boolean isActive){
		this.isActive = isActive;
	}
	
	public boolean hasMainTab() {
		return true;
	}

	public boolean hasConfigPanel() {
		return false;
	}

	public JPanel createConfigPanel() {
		return null;
	}

	public boolean isStartup() {
		return isStartUp;
	}

	public void setStartup(boolean value) {
		isStartUp = value;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean value) {
		isActive = value;
	}
	
	@Override
	public String toString(){
		return getDescription();
	}
	
	public boolean hasMenu(){
		return false;
	}
	public JMenu getMenu(){
		return null;
	}
	
}
