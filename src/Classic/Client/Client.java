package Classic.Client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;

import Classic.Common.ServeurIntf;
import Classic.Threads.PollingThread;
import Classic.Threads.SenderThread;

public class Client extends Thread {

	private ArrayList<String> listeInput = new ArrayList<String>(); //Liste de messages envoyés par ce client
	private String name;


	public ArrayList<String> getListeInput() {
		return listeInput;
	}

	public void setListeInput(ArrayList<String> listeInput) {
		this.listeInput = listeInput;
	}

	public Client(String name) throws MalformedURLException, RemoteException, NotBoundException {
		this.name = name;
		this.setName(name);
	}

	public static void main(String args[]) throws Exception {
		ServeurIntf serveur = (ServeurIntf)Naming.lookup("//localhost/RmiServer");
		
		//Accueille le client
		System.out.println(serveur.messageBienvenue()); 
		
		//Saisie du nom de l'utilisateur
		System.out.println("Votre nom ?"); 
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		Client chatClient=new Client(name);
		System.out.println("Votre nom est : " + chatClient.getName() + ". Vous pouvez discuter maintenant.");
		
		//Démarre le Thread du polling
		new PollingThread(chatClient, serveur).start(); 
		
		//Démarre le Thread pour envoyer des messages
		new SenderThread(chatClient, serveur).start(); 
	}
}