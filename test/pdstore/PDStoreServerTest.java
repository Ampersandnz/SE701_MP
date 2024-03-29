package pdstore;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.concurrent.Semaphore;

import org.junit.Before;
import org.junit.Test;

import pdstore.util.Starter;

public class PDStoreServerTest extends PDStoreTest {

	PDStore getFreshStore() {
		// URL for Christof's desktop: "clut002-01.cs.auckland.ac.nz"
		return PDStore.connectToServer(null);
	}

	/**
	 * This constant is the delay used to make sure that local and remote
	 * objects do steps in the intended order. If the tests using this constant
	 * fail, consider increasing it.
	 */
	public static final int LISTENER_DELAY = 100;

	PDStore serverForThread;
	Semaphore lock;
	public int callCount = 0;

	public final void testReadWriteConcurrently() {
		GUID guid1 = new GUID();
		GUID guid2 = new GUID();
		GUID guid3 = new GUID();
		GUID roleA = new GUID();

		PDStore store1 = getFreshStore();
		PDStore store2 = getFreshStore();
		GUID transaction1 = store1.begin();
		GUID transaction2 = store2.begin();
		Collection<Object> result;

		// connection 1
		store1.addLink(transaction1, guid1, roleA, guid2);
		store1.removeLink(transaction1, guid1, roleA, guid3);
		result = store1.getInstances(transaction1, guid1, roleA);
		assertTrue(result.contains(guid2));
		assertTrue(!result.contains(guid3));

		// connection 2
		store2.addLink(transaction2, guid1, roleA, guid3);
		store2.removeLink(transaction2, guid1, roleA, guid2);
		result = store2.getInstances(transaction2, guid1, roleA);
		assertTrue(result.contains(guid3));
		assertTrue(!result.contains(guid2));
		store2.commit(transaction2);

		// check that transaction1 still cannot see transaction2
		result = store1.getInstances(transaction1, guid1, roleA);
		assertTrue(result.contains(guid2));
		assertTrue(!result.contains(guid3));

		store1.commit(transaction1);

		// a new transaction should see committed transaction1 as current
		// database state
		transaction2 = store2.begin();
		result = store2.getInstances(transaction2, guid1, roleA);
		assertTrue(result.contains(guid2));
		store2.commit(transaction2);
	}

	public void runMe() {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// no action needed
		}
		System.err.println("Hello! runMe is running:");
		serverForThread.nextTransaction();
		callCount++;
		lock.release();
		System.err.println("runMe was notified:");
	}

	public final void testStarterReverseListener() {
		GUID guid1 = new GUID();
		GUID guid2 = new GUID();
		GUID roleA = new GUID();

		PDStore store = getFreshStore();

		// remote listener:
		Semaphore waitLock = new Semaphore(1, true);
		serverForThread = store;
		lock = waitLock;
		Starter starter = new Starter(this, "runMe");

		try {
			Thread.sleep(LISTENER_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GUID transaction = store.begin();
		store.addLink(transaction, guid1, roleA, guid2);
		assertTrue(callCount == 0);
		store.commit(transaction);
		// TODO is this code needed?
		// Currently difficult to create delay
		// try {
		// waitLock.acquire();
		// } catch (InterruptedException e) {
		// // no action required
		// }
		try {
			Thread.sleep(LISTENER_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(callCount == 1);
	}

	public final void testAdHocReverseListener() {
		GUID guid1 = new GUID();
		GUID guid2 = new GUID();
		GUID roleA = new GUID();

		PDStore store = getFreshStore();

		// remote listener:
		Semaphore waitLock = new Semaphore(1, true);
		ReverseListenerThread r = new ReverseListenerThread(
				(pdstore.rmi.PDStore) store, waitLock);
		Thread thread = new Thread(r);
		thread.start();

		try {
			Thread.sleep(LISTENER_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GUID transaction = store.begin();
		store.addLink(transaction, guid1, roleA, guid2);
		assertTrue(r.callCount == 0);
		store.commit(transaction);
		// currenltly difficult to create delay
		// try {
		// waitLock.acquire();
		// } catch (InterruptedException e) {
		// // no action required
		// }
		try {
			Thread.sleep(LISTENER_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(r.callCount == 1);
	}

	public final void testSingleClientListener() {
		GUID guid1 = new GUID();
		GUID guid2 = new GUID();
		GUID roleA = new GUID();

		PDStore store = getFreshStore();

		// remote listener:
		Semaphore waitLock = new Semaphore(1, true);
		ReverseListenerThread r = new SingleClientListenerThread(
				(pdstore.rmi.PDStore) store, waitLock);
		Thread thread = new Thread(r);
		thread.start();

		try {
			Thread.sleep(LISTENER_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GUID transaction = store.begin();
		store.addLink(transaction, guid1, roleA, guid2);
		assertTrue(r.callCount == 0);
		store.commit(transaction);
		// currenltly difficult to create delay
		// try {
		// waitLock.acquire();
		// } catch (InterruptedException e) {
		// // no action required
		// }
		try {
			Thread.sleep(LISTENER_DELAY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertTrue(r.callCount == 1);
	}

}
