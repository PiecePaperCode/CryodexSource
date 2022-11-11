package cryodex.widget;

import cryodex.MenuBarPane;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

public class TournamentTabbedPane {
	private final TabPane tabPane;

	public TournamentTabbedPane() {
		this.tabPane = new TabPane();
	}

	public TabPane getTabPane() {
		return this.tabPane;
	}

	public void addTab(String title, Node node) {
		Tab tab = new Tab();
		tab.setText(title);
		tab.setContent(node);
		tabPane.getTabs().add(tab);
		tabPane.addEventFilter(
			MouseEvent.MOUSE_CLICKED,
			mouseEvent -> MenuBarPane.getInstance().getMenus().removeAll()
		);
	}
}
