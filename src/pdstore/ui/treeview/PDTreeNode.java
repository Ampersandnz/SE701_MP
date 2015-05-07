package pdstore.ui.treeview;

import java.util.Collections;
import java.util.List;

import javax.swing.tree.TreeNode;

import nz.ac.auckland.se.genoupe.tools.DynamicNode;
import pdstore.Blob;

public abstract class PDTreeNode extends DynamicNode implements TreeNode {

	PDTreeView treeView;

	public PDTreeNode() {
		super();
	}

	public PDTreeNode(Object userObject) {
		super(userObject);
	}

	public PDTreeNode(Object userObject, boolean allowsChildren) {
		super(userObject, allowsChildren);
	}

	public abstract void paste(PDTreeModel pdTreeModel, TreeNode node);

	public abstract void contextMenu(PDTreeView pdTreeView, int x, int y);

	public abstract void copy(PDTreeModel pdTreeModel);
	
	public abstract void setIcon(Blob image);

	// Iterate through tree, moving nodes up one level if they are the only
	// child.
	public void promoteNodes() {
		List<PDTreeNode> children = Collections.list(children());

		System.out.println("promoteNodes called in node "
				+ this.getClass().getSimpleName());

		for (PDTreeNode child : children) {
			List<PDTreeNode> grandchildren = Collections.list(child.children());
			if (grandchildren.size() == 1) {
				System.out.println("Found a node with only one child!");
			}
			// If child itself only has one child, move that node (this node's
			// grandchild) up a level and delete child from the list.

		}

		// Refresh the list of children (some may have been deleted in the last
		// step and replaced by their own children.
		children = Collections.list(children());
	}
}