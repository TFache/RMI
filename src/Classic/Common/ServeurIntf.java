package Classic.Common;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServeurIntf extends Remote{

	public String messageBienvenue() throws RemoteException;
	public void envoiMessage(String message, String name) throws RemoteException;
	public List<String> getListeMessage() throws RemoteException;

}