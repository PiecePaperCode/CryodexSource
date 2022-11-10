package cryodex.modules;

import cryodex.Player;
import cryodex.xml.XMLObject;
import cryodex.xml.XMLUtils.Element;
import javafx.scene.control.CheckMenuItem;

public interface Module extends XMLObject {

	public MenuInterface getMenu();

	public RegistrationPanel getRegistration();

	public void setModuleEnabled(Boolean enabled);

	public boolean isModuleEnabled();

	public Tournament loadTournament(Element element);

	public void loadModuleData(Element element);

	public ModulePlayer loadPlayer(Player p, Element element);

	public ModulePlayer getNewModulePlayer(Player player);
	
	public CheckMenuItem getViewMenuItem();
	
	public void setViewMenuItem(CheckMenuItem viewMenuItem);
}
