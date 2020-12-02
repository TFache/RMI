package Classic.Threads;

import java.rmi.RemoteException;
import java.util.Scanner;

import Classic.Client.Client;
import Classic.Common.ServeurIntf;

public class SenderThread extends Thread {

	private Client client;
	private ServeurIntf serveur;
	private Scanner sc;
	public SenderThread(Client client, ServeurIntf serveur) {
		this.client = client;
		this.serveur = serveur;
		this.sc = new Scanner(System.in);
	}
	
	@Override
	public void run() {
		while(true) {
			String message = sc.nextLine();
			try {
				client.getListeInput().add("[" + client.getName() + "] : " + message);
				serveur.envoiMessage(message,client.getName());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
