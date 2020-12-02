package callback.CBCommon;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CBClientIntf extends Remote {

	public String getName() throws RemoteException;
	public void envoiMessage(String message) throws RemoteException;
}
