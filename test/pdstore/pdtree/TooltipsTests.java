package pdstore.pdtree;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for SoftEng701 Mini-project (Adding mouse-hover tooltips to PDTree
 * windows.
 * 
 * @author mlo450 (Base code from test class PDStoreTest by clut002
 *
 */
public class TooltipsTests {

	// TODO: Any static fields required

	@BeforeClass
	public static void setUpClass() {
		// TODO: Any setup required
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
		// TODO call getToolTipFromNode with null
	}
}
