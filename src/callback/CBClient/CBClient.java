package callback.CBClient;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import callback.CBCommon.CBClientIntf;
import callback.CBCommon.CBServeurIntf;

public class CBClient extends UnicastRemoteObject implements CBClientIntf{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String name;
	public CBClient() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public void envoiMessage(String message) throws RemoteException {
		System.out.println(message);
	}


	@Override
	public String getName() throws RemoteException{
		// TODO Auto-generated method stub
		return name;
	}
	
	
	public static void main(String[] args) {
		try {
			CBServeurIntf serveur = (CBServeurIntf)Naming.lookup("//localhost/RmiServer");
			CBClient client = new CBClient();
			
			//Choix du nom et ajout du client au chat
			System.out.println("Bienvenue. Veuillez entrer votre nom.");
			Scanner sc = new Scanner(System.in);
			name = sc.nextLine();
			serveur.ajoutClient(client);
			
			String message = "";
			while(!message.equals("/quit")) {
				message = "";
				message = sc.nextLine();
				if(!message.equals("") && !message.equals("/quit")) {
					serveur.envoiMessage("[" + client.getName() + "] : " + message, client);
				}
			}
			sc.close();
			serveur.retraitClient(client);
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
