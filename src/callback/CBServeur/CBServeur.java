package callback.CBServeur;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import callback.CBCommon.CBClientIntf;
import callback.CBCommon.CBServeurIntf;

public class CBServeur extends UnicastRemoteObject implements CBServeurIntf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CBClientIntf> listeClients = new ArrayList<CBClientIntf>();

	protected CBServeur() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public List<CBClientIntf> getListeClients() {
		return listeClients;
	}



	public void setListeClients(List<CBClientIntf> listeClients) {
		this.listeClients = listeClients;
	}



	@Override
	public void ajoutClient(CBClientIntf client) throws RemoteException {
		envoiTous("[" + client.getName() + "] a rejoint le chat." );
		this.getListeClients().add(client);
	}

	@Override
	public void retraitClient(CBClientIntf client) throws RemoteException {
		this.getListeClients().remove(client);
		envoiTous("[" + client.getName() + "] a quitté le chat." );
	}

	@Override
	public void envoiMessage(String message, CBClientIntf clientEnvoyeur) throws RemoteException {
		for (CBClientIntf cbClientIntf : listeClients) {
			if(!cbClientIntf.equals(clientEnvoyeur)) {
				cbClientIntf.envoiMessage(message);
			}
		}
		
	}
	
	public void envoiTous(String message) {
		for (CBClientIntf cbClientIntf : this.getListeClients()) {

				try {
					cbClientIntf.envoiMessage(message);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						this.retraitClient(cbClientIntf);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}


		}
	}
	
	public static void main(String args[]) throws Exception {
		try { 
			LocateRegistry.createRegistry(1099); 
		} catch (RemoteException e) {
		}
		CBServeur chatServeur = new CBServeur();
		Naming.rebind("//localhost/RmiServer", chatServeur);
		System.out.println("Serveur prêt!");
	}

	
}
