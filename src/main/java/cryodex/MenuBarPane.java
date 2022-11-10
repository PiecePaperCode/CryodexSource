package cryodex;

import java.io.File;
import cryodex.CryodexController.Modules;
import cryodex.modules.Module;
import cryodex.widget.AboutPanel;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class MenuBarPane {
	private Menu fileMenu;
	private Menu viewMenu;
	private Menu helpMenu;

	private CheckMenuItem showTableNumbers;
	private CheckMenuItem showQuickFind;

	private static MenuBar instance;

	public static MenuBar getInstance() {
		if (instance == null) {
			instance = new MenuBar();
		}
		return instance;
	}

	private MenuBarPane() {

		instance.getMenus().add(getFileMenu());
		instance.getMenus().add(getViewMenu());

		for (final Module m : CryodexController.getModules()) {
            instance.getMenus().add(m.getMenu().getMenu());
		}

        instance.getMenus().add(getHelpMenu());
	}

	public Menu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new Menu("File");
			// fileMenu.setMnemonic('F');

			MenuItem importPlayers = new MenuItem("Import Players");
			importPlayers.addEventHandler(
                    MouseEvent.MOUSE_CLICKED,
                    MouseEvent -> PlayerImport.importPlayers()
			);
			
			MenuItem exit = new MenuItem("Exit");
			exit.addEventHandler(
                    MouseEvent.MOUSE_CLICKED,
                    MouseEvent -> Main.getInstance().dispose()
			);

			fileMenu.getItems().add(importPlayers);
			fileMenu.getItems().add(exit);
		}

		return fileMenu;
	}

	public Menu getViewMenu() {
		if (viewMenu == null) {
			viewMenu = new Menu("View");
			// viewMenu.setMnemonic('V');

			showTableNumbers = new CheckMenuItem("Show Table Numbers");
			showTableNumbers.setSelected(true);
			showTableNumbers.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                MouseEvent -> showTableNumbers.isSelected()
			);

			showQuickFind = new CheckMenuItem("Show Quick Table Search");
			showQuickFind.setSelected(false);
			showQuickFind.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                MouseEvent -> CryodexController.
                    getOptions()
                    .setShowQuickFind(
                        showQuickFind.isSelected()
                    )
			);

			final CheckMenuItem showRegistrationPanel = new CheckMenuItem(
                "Show Registration Panel"
            );
			showRegistrationPanel.setSelected(true);
			showRegistrationPanel.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                MouseEvent -> {
					Main.getInstance().getRegisterPane()
                        .getChildren()
                        .remove(Main.getInstance().getRegisterPanel());
					if (showRegistrationPanel.isSelected()) {
						Main.getInstance().getRegisterPane()
                            .getChildren()
                            .add(Main.getInstance().getRegisterPanel().getPlayerPanel());
					}

					Main.getInstance().validate();
					Main.getInstance().repaint();
				}
			);

			viewMenu.getItems().add(showQuickFind);
			viewMenu.getItems().add(showTableNumbers);
			viewMenu.getItems().add(showRegistrationPanel);

			for (final Module m : CryodexController.getModules()) {
				final CheckMenuItem moduleItem = new CheckMenuItem(
						Modules.getNameByModule(m));
				moduleItem.setSelected(m.isModuleEnabled());
				moduleItem.addEventHandler(
                    MouseEvent.MOUSE_CLICKED,
                    MouseEvent -> {
						m.setModuleEnabled(moduleItem.isSelected());
						Modules moduleEnum = Modules.getEnumByName(Modules
								.getNameByModule(m));
						if (moduleItem.isSelected()) {
							CryodexController.getOptions()
									.getNonVisibleModules().remove(moduleEnum);
						} else {
							CryodexController.getOptions()
									.getNonVisibleModules().add(moduleEnum);
						}
						CryodexController.saveData();
					}
				);
				
				m.setViewMenuItem(moduleItem);

				viewMenu.getItems().add(moduleItem);
			}
		}

		return viewMenu;
	}

	public Menu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new Menu("Help");
			// helpMenu.setMnemonic('H');

			MenuItem about = new MenuItem("About");
			about.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                MouseEvent -> AboutPanel.showAboutPanel()
			);
			MenuItem whereIsSave = new MenuItem("Where is my save file?");
			whereIsSave.addEventHandler(
                MouseEvent.MOUSE_CLICKED,
                MouseEvent -> {
					File path = new File(System.getProperty("user.dir"));
					if (!path.exists()) {
						System.out.println("Error with user directory");
					}
					File file = new File(path, CryodexController.CRYODEX_SAVE);

					if (file.exists()) {
                        new Alert(
                            Alert.AlertType.INFORMATION,
                            "Save file can be found at"
                            + file.getAbsolutePath(),
                            ButtonType.OK
                        );
					} else if (!path.exists()) {
                        new Alert(
                            Alert.AlertType.WARNING,
                            "Save location could not be determined. " +
                                "Check permissions to allow a Java " +
                                "application to save a file."
                            + file.getAbsolutePath(),
                            ButtonType.OK
                        );
					} else if (!file.exists()) {
                        new Alert(
                            Alert.AlertType.WARNING,
                            "A save file could not be found. " +
                            "It should be called"
                            +CryodexController.CRYODEX_SAVE +
                            "</b> and should be located in folder"
                            + file.getAbsolutePath(),
                            ButtonType.OK
                        );
					}
				}
			);

			helpMenu.getItems().add(about);
			helpMenu.getItems().add(whereIsSave);
		}
		return helpMenu;
	}
}
