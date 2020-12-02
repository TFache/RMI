package Classic.Threads;

import java.rmi.RemoteException;
import java.util.ArrayList;

import Classic.Client.Client;
import Classic.Common.ServeurIntf;

public class PollingThread extends Thread {

	private Client client;
	private ServeurIntf serveur;
	public PollingThread(Client client, ServeurIntf serveur) {
		this.client = client;
		this.serveur = serveur;
	}
	
	@Override
	public void run() {
		try {
			//Mise à jour de la liste des messages envoyés précédemment par les autres clients
			client.setListeInput((ArrayList<String>) serveur.getListeMessage()); 
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		while(true) {
			try {
				/* Si la liste de messages du serveur a une taille différente de la liste de messages du client,
				 * alors on affiche les messages manquants et on les ajoute à la liste de messages du serveur */
				if(serveur.getListeMessage().size() > client.getListeInput().size()) {
					for (int i = client.getListeInput().size(); i < serveur.getListeMessage().size(); i++) {
						System.out.println(serveur.getListeMessage().get(i));
						client.getListeInput().add(serveur.getListeMessage().get(i));
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
