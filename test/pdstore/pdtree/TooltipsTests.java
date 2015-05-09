package pdstore.pdtree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;

import org.junit.BeforeClass;
import org.junit.Test;

import pdstore.GUID;
import pdstore.GUIDGen;
import pdstore.PDStore;
import pdstore.ui.treeview.PDRootNode;
import pdstore.ui.treeview.PDTreeNode;
import pdstore.ui.treeview.PDTreeView;
import book.BookExample;

/**
 * Tests for SoftEng701 Mini-project (Adding mouse-hover tooltips to PDTree
 * windows.
 * 
 * @author mlo450 (Base code from test class PDStoreTest by clut002
 *
 */
public class TooltipsTests {

	static PDTreeNode parentNode;
	static PDTreeView treeView;
	static GUID testType = GUIDGen.generateGUIDs(1).get(0);
	static Object[] array;
	static PDStore store;

	@BeforeClass
	public static void setUpClass() {
		store = new PDStore("MyBookDatabase");

		// I've made these methods, and the static GUID libraryType, visible for
		// testing.
		BookExample.createBookModel(store);

		BookExample.addSomeData(store);

		GUID trans = store.begin();

		Collection<Object> lib = store.getAllInstancesOfType(trans,
				BookExample.libraryType);
		array = lib.toArray();
	}

	@Test
	public final void testReturnsNullWhenMouseOffWindow() {
		// Unfortunately, the treeView created in setUpClass does not persist
		// through to the tests so each test must run this code individually.
		PDTreeView treeView = new PDTreeView(store, array);

		JScrollPane scrollPane = new JScrollPane(treeView);
		JFrame frame = new JFrame("Treeview");
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Register the tree with the ToolTipManager so that tooltips can be
		// displayed on mouse hover.
		ToolTipManager.sharedInstance().registerComponent(treeView);

		parentNode = (PDTreeNode) ((PDRootNode) treeView.getModel().getRoot())
				.getChildAt(0);

		// Create new MouseEvent located off the window.
		MouseEvent e = new MouseEvent(treeView, 0, 0, 0, -1, -1, 0, false);

		assertNull(treeView.getToolTipText(e));
	}

	@Test
	public final void testReturnsNullWhenMouseOnEmptyWindowSpace() {
		// Unfortunately, the treeView created in setUpClass does not persist
		// through to the tests so each test must run this code individually.
		PDTreeView treeView = new PDTreeView(store, array);

		JScrollPane scrollPane = new JScrollPane(treeView);
		JFrame frame = new JFrame("Treeview");
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Register the tree with the ToolTipManager so that tooltips can be
		// displayed on mouse hover.
		ToolTipManager.sharedInstance().registerComponent(treeView);

		parentNode = (PDTreeNode) ((PDRootNode) treeView.getModel().getRoot())
				.getChildAt(0);

		// Create new MouseEvent located off the window.
		MouseEvent e = new MouseEvent(treeView, 0, 0, 0, 200, 200, 0, false);

		assertNull(treeView.getToolTipText(e));
	}

	@Test
	public final void testReturnsChildrenWhenMouseOnNode() {
		// Unfortunately, the treeView created in setUpClass does not persist
		// through to the tests so each test must run this code individually.
		PDTreeView treeView = new PDTreeView(store, array);

		JScrollPane scrollPane = new JScrollPane(treeView);
		JFrame frame = new JFrame("Treeview");
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Register the tree with the ToolTipManager so that tooltips can be
		// displayed on mouse hover.
		ToolTipManager.sharedInstance().registerComponent(treeView);

		parentNode = (PDTreeNode) ((PDRootNode) treeView.getModel().getRoot())
				.getChildAt(0);

		// Create new MouseEvent located off the window.
		MouseEvent e = new MouseEvent(treeView, 0, 0, 0, 55, 20, 0, false);

		assertEquals(treeView.getToolTipText(e),
				"<html>has_book<br>has type<br>name<br></html>");
	}

	@Test
	public final void testCorrectNodeReturnedWhenMouseOnNode() {
		// Unfortunately, the treeView created in setUpClass does not persist
		// through to the tests so each test must run this code individually.
		PDTreeView treeView = new PDTreeView(store, array);

		JScrollPane scrollPane = new JScrollPane(treeView);
		JFrame frame = new JFrame("Treeview");
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Register the tree with the ToolTipManager so that tooltips can be
		// displayed on mouse hover.
		ToolTipManager.sharedInstance().registerComponent(treeView);

		parentNode = (PDTreeNode) ((PDRootNode) treeView.getModel().getRoot())
				.getChildAt(0);

		// Ensure that the method correctly returns the list of the node's
		// children, formatted in html with new lines.
		assertEquals(treeView.getToolTipFromNode(parentNode),
				"<html>has_book<br>has type<br>name<br></html>");
	}

	@Test
	public final void testReturnsNullWhenNodeNull() {
		// Unfortunately, the treeView created in setUpClass does not persist
		// through to the tests so each test must run this code individually.
		PDTreeView treeView = new PDTreeView(store, array);

		JScrollPane scrollPane = new JScrollPane(treeView);
		JFrame frame = new JFrame("Treeview");
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Register the tree with the ToolTipManager so that tooltips can be
		// displayed on mouse hover.
		ToolTipManager.sharedInstance().registerComponent(treeView);

		assertNull(treeView.getToolTipFromNode(null));
	}

	@Test
	public final void testReturnsLeafWhenNodeIsLeaf() {
		// Unfortunately, the treeView created in setUpClass does not persist
		// through to the tests so each test must run this code individually.
		PDTreeView treeView = new PDTreeView(store, array);

		JScrollPane scrollPane = new JScrollPane(treeView);
		JFrame frame = new JFrame("Treeview");
		frame.setLayout(new BorderLayout());
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Register the tree with the ToolTipManager so that tooltips can be
		// displayed on mouse hover.
		ToolTipManager.sharedInstance().registerComponent(treeView);

		parentNode = (PDTreeNode) ((PDRootNode) treeView.getModel().getRoot())
				.getChildAt(0);

		PDTreeNode leafNode;

		// Get reference to a leaf node
		// Path is parentNode -> has_book (0) -> crime (0) -> title (0) -> crime
		// and punishment (0)
		// Each node needs to be expanded with node.doExpand(null) so that
		// its children are created
		parentNode.doExpandNode(null);
		PDTreeNode has_book = (PDTreeNode) parentNode.getChildAt(0);
		has_book.doExpandNode(null);
		PDTreeNode crime = (PDTreeNode) has_book.getChildAt(0);
		crime.doExpandNode(null);
		PDTreeNode title = (PDTreeNode) crime.getChildAt(0);
		title.doExpandNode(null);
		leafNode = (PDTreeNode) title.getChildAt(0);

		assertEquals(treeView.getToolTipFromNode(leafNode), "Leaf node.");
	}
}
