package pdqueue.tools;

import java.sql.Timestamp;

import pdqueue.concurrency.PDSConnection;
import pdqueue.dal.PDItem;
import pdstore.dal.PDSimpleWorkingCopy;

public class PDItemFactory {
	
	private final ItemState initialState = ItemState.PENDING;

	public PDItem createPDItem(String name, String sender, String receiver, String message) {
		PDItem item = new PDItem(PDSConnection.getStore());
		item.setName(name);
		item.setSender(sender);
		item.setReceiver(receiver);
		item.setMessage(message);
		java.util.Date date= new java.util.Date();
		item.setTime(new Timestamp(date.getTime()));
		item.setState(initialState.toString());
		PDSConnection.getStore().commit();
		return item;
	}
}
