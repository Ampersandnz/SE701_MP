package angil.dal;

import java.util.*;
import pdstore.*;
import pdstore.dal.PDInstance;
import pdstore.dal.PDWorkingCopy;

/**
 * Data access class to represent instances of type "Page" in memory.
 * Note that this class needs to be registered with PDCache by calling:
 *    Class.forName("angil.dal.PDPage");
 * @author PDGen
 */
public class PDPage implements PDInstance {

	public static final GUID typeId = new GUID("fbc29377374c11e0bfe4001e8c7f9d82"); 

	public static final GUID roleNextActionId = new GUID("005319b036ed11e08144001e8c7f9d82");

	static {
		register();
	}

	/**
	 * Registers this DAL class with the PDStore DAL layer.
	 */
	public static void register() {
		PDStore.addDataClass(typeId, PDPage.class);
	}

	private PDStore store;
	private GUID id;

	public String toString() {
		String name = getName();
		if(name!=null)
			return "PDPage:" + name;
		else
			return "PDPage:" + id;
	}

	/**
	 * Creates an PDPage object representing a PDStore instance of type Page.
	 * @param store the store the instance should be in
	 */
	public PDPage(PDStore store) {
		this(store, new GUID());
	}

	/**
	 * Creates an PDPage object representing the instance with the given ID. 
	 * The ID must be of an instance of type Page.
	 * @param store the store the instance should be in
	 * @param id GUID of the instance
	 */
	public PDPage(PDStore store, GUID id) {
		this.store = store;
		this.id = id;
		// set the has-type link for this instance
		store.setType(id, typeId);
	}

	/**
	 * Loads an object for the instance of PDStore type Page with the given ID.
	 * If an object for the instance is already available in the given PDStore object, it is returned.
	 * @param store store to load the instance into
	 * @param id GUID of the instance
	 */
	public static PDPage load(PDStore store, GUID id) {
		PDInstance instance = store.load(typeId, id);
		return (PDPage)instance;
	}

	/**
	 * Gets the PDStore this object is stored in.
	 */
	public PDStore getStore() {
		return store;
	}

	/**
	 * Gets the GUID of the instance represented by this object.
	 */
	public GUID getId() {
		return id;
	}

	/**
	 * Gets the GUID of the type of the instance represented by this object.
	 * This method isn't static so that it can be part of the PDInstance interface.
	 */
	public GUID getTypeId() {
		return typeId;
	}

	/**
	 * Gets a textual label for this instance, for use in UIs.
	 * @return a textual label for the instance
	 */
	public String getLabel() {
		return store.getLabel(id);
	}

	/**
	 * Gets the name of this instance.
	 * In PDStore every instance can be given a name.
	 * @return name the instance name
	 */
	public String getName() {
		return store.getName(id);
	}

	/**
	 * Sets the name of this instance.
	 * In PDStore every instance can be given a name.
	 * If the instance already has a name, the name will be overwritten.
	 * If the given name is null, an existing name will be removed.
	 * @return name the new instance name
	 */
	public void setName(String name) {
		store.setName(id, name);
	}

	/**
	 * Removes the name of this instance.
	 * In PDStore every instance can be given a name.
	 * If the instance does not have a name, nothing happens.
	 */
	public void removeName() {
		store.removeName(id);
	}

	/**
	 * Gets the icon of this instance.
	 * In PDStore every instance can be given an icon.
	 * @return icon the instance icon
	 */
	public Blob getIcon() {
		return store.getIcon(id);
	}

	/**
	 * Sets the icon of this instance.
	 * In PDStore every instance can be given an icon.
	 * If the instance already has an icon, the icon will be overwritten.
	 * If the given icon is null, an existing icon will be removed.
	 * @return icon the new instance icon
	 */
	public void setIcon(Blob icon) {
		store.setIcon(id, icon);
	}

	/**
	 * Removes the icon of this instance.
	 * In PDStore every instance can be given an icon.
	 * If the instance does not have an icon, nothing happens.
	 */
	public void removeIcon() {
		store.removeIcon(id);
	}
	/**
	 * Returns the instance connected to this instance through the role nextAction.
	 * @return the connected instance
	 */
	public PDAction getNextAction() {
		GUID instanceId = (GUID) store.getInstance(this.id, roleNextActionId);
	 	return (PDAction) store.load(PDAction.typeId, instanceId);
	}

	/**
	 * Returns the instance(s) connected to this instance through the role nextAction.
	 * @return the connected instance(s)
	 */
	 public Collection<PDAction> getNextActions() {
		return (Collection<PDAction>)(Object)store.getAndLoadInstances(this.id, roleNextActionId, PDAction.typeId);
	 }

   /**
	 * Connects this instance to the given instance using role "nextAction".
	 * If the given instance is null, nothing happens.
	 * @param nextAction the instance to connect
	 */
	public void addNextAction(GUID nextAction) {
		if (nextAction != null) {
			store.addLink(this.id, roleNextActionId, nextAction);
		}
	}
	/**
	 * Connects this instance to the given instance using role "nextAction".
	 * If the given instance is null, nothing happens.
	 * @param nextAction the instance to connect
	 */
	public void addNextAction(PDAction nextAction) {
		if (nextAction != null)
			addNextAction(nextAction.getId());
	}

	/**
	 * Connects this instance to the given instance using role "nextAction".
	 * If the given collection of instances is null, nothing happens.
	 * @param nextAction the Collection of instances to connect
	 */
	public void addNextActions(Collection<PDAction> nextActions) {
		if (nextActions == null)
			return;
		for (PDAction instance : nextActions)
			addNextAction(instance);
	}

	/**
	 * Removes the link from this instance through role "nextAction".
	 */
	public void removeNextAction() {
		store.removeLink(this.id, roleNextActionId,
			store.getInstance(this.id, roleNextActionId));
	}

	/**
	 * Removes the link from this instance through role "nextAction" to the given instance, if the link exists.
	 * If there is no such link, nothing happens.
	 * If the given instance is null, nothing happens.
	 */
	public void removeNextAction(Object nextAction) {
		if (nextAction == null)
			return;
		store.removeLink(this.id, roleNextActionId, nextAction);
	}

	/**
	 * Removes the links from this instance through role "nextAction" to the instances
	 * in the given Collection, if the links exist.
	 * If there are no such links or the collection argument is null, nothing happens.
	 */
	public void removeNextActions(Collection<PDAction> nextActions) {
		if (nextActions == null)
			return;
		for (PDAction instance : nextActions)
			store.removeLink(this.id, roleNextActionId, instance);
	}

	/**
	 * Connects this instance to the given instance using role "nextAction".
	 * If there is already an instance connected to this instance through role "nextAction", the link will be overwritten.
	 * If the given instance is null, an existing link is removed.
	 * @param nextAction the instance to connect
	 */
	public void setNextAction(GUID nextAction) {
		store.setLink(this.id,  roleNextActionId, nextAction);
	}
	/**
	 * Connects this instance to the given instance using role "nextAction".
	 * If there is already an instance connected to this instance through role "nextAction", the link will be overwritten.
	 * If the given instance is null, an existing link is removed.
	 * @param nextAction the instance to connect
	 */
	public void setNextAction(PDAction nextAction) {
		setNextAction(nextAction.getId());
	}

	@Override
	public PDWorkingCopy getPDWorkingCopy() {
		// TODO Auto-generated method stub
		return null;
	}
}
