package pdstore.pdtree;

import java.util.Collection;

import javax.swing.ToolTipManager;

import nz.ac.auckland.se.genoupe.tools.Debug;

import org.junit.BeforeClass;
import org.junit.Test;

import pdstore.GUID;
import pdstore.PDStore;
import pdstore.ui.treeview.PDTreeView;

/**
 * Tests for SoftEng701 Mini-project (Adding mouse-hover tooltips to PDTree
 * windows.
 * <p>
 * Content of test PDStore instance taken from BookExample for ease of manual
 * result validation and author familiarity.
 * 
 * @author mlo450 (Base code from test class PDStoreTest by clut002
 *
 */
public class PDStoreTest {

	static PDStore store;

	// the GUID that identifies our book model
	static GUID model = new GUID("77760e908da911e098a9842b2b9af4fd");

	// the GUIDs for the types and roles of our book model
	static GUID bookType = new GUID("77760e918da911e098a9842b2b9af4fd");
	static GUID bookTitleRole = new GUID("77760e928da911e098a9842b2b9af4fd");
	static GUID bookYearRole = new GUID("77760e938da911e098a9842b2b9af4fd");
	static GUID bookAuthorRole = new GUID("77760e988da911e098a9842b2b9af4fd");
	static GUID authorType = new GUID("77760e948da911e098a9842b2b9af4fd");
	static GUID authorFirstNameRole = new GUID(
			"77760e958da911e098a9842b2b9af4fd");
	static GUID authorLastNameRole = new GUID(
			"77760e968da911e098a9842b2b9af4fd");
	static GUID libraryType = new GUID("77760e978da911e098a9842b2b9af4fd");
	static GUID libraryBookRole = new GUID("77760e998da911e098a9842b2b9af4fd");

	// GUIDs to identify a book and an author in our database
	static GUID library = new GUID("77760e978da911e198a9842b2b9af4fd");
	static GUID bookLOTR = new GUID("77760e918da911e198a9842b2b9af4fd");
	static GUID bookSilmarillion = new GUID("77760e918da911e298a9842b2b9af4fd");
	static GUID bookCrime = new GUID("77760e918da911e398a9842b2b9af4fd");
	static GUID bookCatcher = new GUID("77760e918da911e498a9842b2b9af4fd");
	static GUID authorTolkien = new GUID("77760e948da911e198a9842b2b9af4fd");
	static GUID authorDostoyevsky = new GUID("77760e948da911e298a9842b2b9af4fd");
	static GUID authorSalinger = new GUID("77760e948da911e398a9842b2b9af4fd");

	static final String EXAMPLE_TREE_VIEWER = "ExampleTreeViewer";

	static PDTreeView treeView;

	@BeforeClass
	public static void setUpClass() {
		// create a brand new store (including the metamodel) to prevent
		// interference with earlier test runs
		String fileName = "PDStoreTest-" + System.nanoTime();
		store = new PDStore(fileName);

		// this method creates our example model
		createBookModel(store);

		// let's add some book data into the store
		addSomeData(store);

		Debug.addDebugTopic(EXAMPLE_TREE_VIEWER);
		PDStore store = new PDStore("MyBookDatabase");

		GUID trans = store.begin();
		Collection<Object> lib = store
				.getAllInstancesOfType(trans, libraryType);
		Object[] array = lib.toArray();
		treeView = new PDTreeView(store, array);

		// Register the tree with the ToolTipManager so that tooltips can be
		// displayed on mouse hover.
		ToolTipManager.sharedInstance().registerComponent(treeView);
	}

	@Test
	public final void testReturnsNullWhenMouseOffWindow() {
		// TODO: call getToolTipText with MouseEvent off window
	}

	@Test
	public final void testReturnsNullWhenMouseOnEmptyWindowSpace() {
		// TODO: call getToolTipText with MouseEvent on window but not on a node
	}

	@Test
	public final void testCorrectNodeReturnedWhenMouseOnNode() {
		// TODO: call getToolTipFromNode with a node
	}

	@Test
	public final void testReturnsNullWhenNodeNull() {
		// TODO: call getToolTipFromNode with null as arg, check that null is
		// returned
	}

	static void createBookModel(PDStore store) {

		// begin a new database transaction
		GUID transaction = store.begin();

		// create a new model, with a new GUID
		store.createModel(transaction, model, "BookModel");

		// create the new complex type Library
		store.createType(transaction, model, libraryType, "Library");

		// create the new complex type Book
		store.createType(transaction, model, bookType, "Book");

		// create relation from Book to String with a role "title"
		// the role on the other end is unnamed, hence the 3rd parameter null
		store.createRelation(transaction, bookType, null, "title",
				bookTitleRole, PDStore.STRING_TYPEID);

		// create relation from Book to Integer with a role "year"
		// the role on the other end is unnamed, hence the 3rd parameter null
		store.createRelation(transaction, bookType, null, "year", bookYearRole,
				PDStore.INTEGER_TYPEID);

		// create the new complex type Author
		store.createType(transaction, model, authorType, "Author");

		// create relation from Author to String with a role "first name"
		store.createRelation(transaction, authorType, null, "first name",
				authorFirstNameRole, PDStore.STRING_TYPEID);

		// create relation from Author to String with a role "last name"
		store.createRelation(transaction, authorType, null, "last name",
				authorLastNameRole, PDStore.STRING_TYPEID);

		// create relation from Library to Book
		store.createRelation(transaction, libraryType, "shelved_in",
				"has_book", libraryBookRole, bookType);

		// create a relation from Book to Author
		// note this relation has names for both roles, so it can be navigated
		// in both directions;
		// i.e. you can get the authors of a book and the books of an author
		store.createRelation(transaction, bookType, "book", "author",
				bookAuthorRole, authorType);

		// commit the transaction
		store.commit(transaction);
	}

	static void addSomeData(PDStore store) {

		// begin a new database transaction
		GUID transaction = store.begin();

		// let's put "Lord of the Rings" into our database
		store.setName(transaction, bookLOTR, "LOTR");

		// tell PDStore that bookLOTR is of type Book
		store.setType(transaction, bookLOTR, bookType);

		// add links for our bookLOTR instance
		store.addLink(transaction, bookLOTR, bookTitleRole, "Lord of the Rings");
		store.addLink(transaction, bookLOTR, bookYearRole, 1954);

		// and we add the author...
		store.setName(transaction, authorTolkien, "Tolkien");
		store.setType(transaction, authorTolkien, authorType);
		store.addLink(transaction, authorTolkien, authorFirstNameRole, "John");
		store.addLink(transaction, authorTolkien, authorLastNameRole, "Tolkien");

		// we link the author to the book
		store.addLink(transaction, bookLOTR, bookAuthorRole, authorTolkien);

		// Another book with the same author:
		store.setName(transaction, bookSilmarillion, "Sil");
		store.setType(transaction, bookSilmarillion, bookType);
		store.addLink(transaction, bookSilmarillion, bookTitleRole,
				"The Silmarillion");
		store.addLink(transaction, bookSilmarillion, bookYearRole, 1977);

		// we link the author to the book
		store.addLink(transaction, bookSilmarillion, bookAuthorRole,
				authorTolkien);

		// Another book with a new author:
		store.setName(transaction, bookCatcher, "Catcher");
		store.setType(transaction, bookCatcher, bookType);
		store.addLink(transaction, bookCatcher, bookTitleRole,
				"The Catcher in the Rye");
		store.addLink(transaction, bookCatcher, bookYearRole, 1951);

		// new author...
		store.setName(transaction, authorSalinger, "Salinger");
		store.setType(transaction, authorSalinger, authorType);
		store.addLink(transaction, authorSalinger, authorFirstNameRole, "J. D.");
		store.addLink(transaction, authorSalinger, authorLastNameRole,
				"Salinger");

		// we link the author to the book
		store.addLink(transaction, bookCatcher, bookAuthorRole, authorSalinger);

		// Yet another book with a new author:
		store.setName(transaction, bookCrime, "Crime");
		store.setType(transaction, bookCrime, bookType);
		store.addLink(transaction, bookCrime, bookTitleRole,
				"Crime and Punishment");
		store.addLink(transaction, bookCrime, bookYearRole, 1866);

		// new author...
		store.setName(transaction, authorDostoyevsky, "Dostoyevsky");
		store.setType(transaction, authorDostoyevsky, authorType);
		store.addLink(transaction, authorDostoyevsky, authorFirstNameRole,
				"Fyodor");
		store.addLink(transaction, authorDostoyevsky, authorLastNameRole,
				"Dostoyevsky");

		// we link the author to the book
		store.addLink(transaction, bookCrime, bookAuthorRole, authorDostoyevsky);

		// put the library instance into the database
		store.setName(transaction, library, "My book library");
		store.setType(transaction, library, libraryType);

		// link the books to the library
		store.addLink(transaction, library, libraryBookRole, bookLOTR);
		store.addLink(transaction, library, libraryBookRole, bookSilmarillion);
		store.addLink(transaction, library, libraryBookRole, bookCatcher);
		store.addLink(transaction, library, libraryBookRole, bookCrime);

		// commit the transaction
		store.commit(transaction);
	}
}
