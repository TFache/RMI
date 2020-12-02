package callback.CBCommon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CBServeurIntf extends Remote{

	
	public void ajoutClient(CBClientIntf client) throws RemoteException;
	public void retraitClient(CBClientIntf client) throws RemoteException;
	public void envoiMessage(String message, CBClientIntf client) throws RemoteException;
}
