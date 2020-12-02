package Classic.Serveur;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import Classic.Common.ServeurIntf;

public class Serveur extends UnicastRemoteObject implements ServeurIntf{
	private static final long serialVersionUID = 1L;

	private List<String> listeMessage = new ArrayList<String>(); //Liste des messages reçus par le serveur

	public Serveur() throws RemoteException{
		super(0);
	}

	public List<String> getListeMessage() throws RemoteException {
		return listeMessage;
	}

	public void setListeMessage(List<String> listeMessage) {
		this.listeMessage = listeMessage;
	}

	@Override
	public String messageBienvenue() throws RemoteException{
		return "Bienvenue!";
	}

	@Override
	public void envoiMessage(String message, String name) throws RemoteException {
		//Permet l'enregistrement du message dans la liste des messages du serveur (et donc son renvoi par la suite)
		this.listeMessage.add("[" + name + "] : " + message);
	}

	public static void main(String args[]) throws Exception {
		try { 
			LocateRegistry.createRegistry(1099); 
		} catch (RemoteException e) {
		}
		Serveur chatServeur = new Serveur();
		Naming.rebind("//localhost/RmiServer", chatServeur);
		System.out.println("Serveur prêt!");
	}



}