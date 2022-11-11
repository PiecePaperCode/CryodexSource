package cryodex.widget;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeView;


public abstract class FilteredTree  {

	private static final long serialVersionUID = 1L;

	private String filteredText = "";
	private ScrollPane scrollpane = new ScrollPane();
	private TreeView tree = new TreeView();
	private TreeView originalRoot;

	public FilteredTree(TreeView originalRoot) {
		this.originalRoot = originalRoot;
	}

	protected abstract boolean matches(TreeView node,
			String testToMatch);

	/**
	 * Renders bold any tree nodes who's toString() value starts with the
	 * filtered text we are filtering on.
	 * 
	 * @author Oliver.Watkins
	 */

	public TreeView getTree() {
		return tree;
	}

}