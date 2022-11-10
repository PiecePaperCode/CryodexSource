package cryodex.modules.xwing;

import cryodex.CryodexController;
import cryodex.CryodexController.Modules;
import cryodex.MenuBarPane;
import cryodex.Player;
import cryodex.modules.MenuInterface;
import cryodex.modules.Module;
import cryodex.modules.ModulePlayer;
import cryodex.modules.RegistrationPanel;
import cryodex.modules.Tournament;
import cryodex.modules.xwing.XWingTournamentCreationWizard.WizardOptions;
import cryodex.xml.XMLUtils;
import cryodex.xml.XMLUtils.Element;
import javafx.scene.control.CheckMenuItem;

public class XWingModule implements Module {

	private static XWingModule module;

	public static XWingModule getInstance() {
		if (module == null) {
			module = new XWingModule();
		}

		return module;
	}

	private CheckMenuItem viewMenuItem;
	private XWingRegistrationPanel registrationPanel;
	private XWingMenuInterface menu;
	private XWingOptions options;

	private boolean isEnabled = true;

	private XWingModule() {

	}

	@Override
	public MenuInterface getMenu() {
		if (menu == null) {
			menu = new XWingMenuInterface();
		}
		return menu;
	}

	@Override
	public RegistrationPanel getRegistration() {
		if (registrationPanel == null) {
			registrationPanel = new XWingRegistrationPanel();
		}
		return registrationPanel;
	}

	@Override
	public void setModuleEnabled(Boolean enabled) {
		isEnabled = enabled;

		getRegistration().getPanel().setVisible(enabled);
		getMenu().getMenu().setVisible(enabled);
	}

	@Override
	public boolean isModuleEnabled() {
		return isEnabled;
	}

	public static void createTournament() {
		// Dialog wizard = new XWingTournamentCreationWizard();
		// wizard.setVisible(true);

	}

	public static void makeTournament(WizardOptions wizardOptions) {

		XWingTournament tournament = new XWingTournament(
				wizardOptions.getName(), wizardOptions.getPlayerList(),
				wizardOptions.getInitialSeedingEnum(),
				wizardOptions.getPoints(), wizardOptions.getEscalationPoints(),
				wizardOptions.isSingleElimination());

		CryodexController.registerTournament(tournament);

		tournament.startTournament();

		MenuBarPane.getInstance().getMenus().removeAll();

		CryodexController.saveData();
	}

	public XWingOptions getOptions() {
		if (options == null) {
			options = new XWingOptions();
		}
		return options;
	}

	@Override
	public StringBuilder appendXML(StringBuilder sb) {
		XMLUtils.appendXMLObject(sb, "OPTIONS", getOptions());
		XMLUtils.appendObject(sb, "NAME", Modules.XWING.getName());
		return sb;
	}

	@Override
	public ModulePlayer loadPlayer(Player p, Element element) {
		return new XWingPlayer(p, element);
	}

	@Override
	public Tournament loadTournament(Element element) {
		return new XWingTournament(element);
	}

	@Override
	public void loadModuleData(Element element) {
		options = new XWingOptions(element.getChild("OPTIONS"));
	}

	@Override
	public ModulePlayer getNewModulePlayer(Player player) {
		return new XWingPlayer(player);
	}

	@Override
	public CheckMenuItem getViewMenuItem() {
		return viewMenuItem;
	}

    @Override
    public void setViewMenuItem(CheckMenuItem viewMenuItem) {
        this.viewMenuItem = viewMenuItem;
    }
}
