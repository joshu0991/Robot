package com.joshu.dnsdynamic.ClientGUI.Logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientLogics {

	private String hostName;
	private int portNum;
	private Socket sock;
	private PrintWriter out;
	private BufferedReader in;
	private String dataIn;
	
	public ClientLogics(String hostName, String port){
		this.hostName = hostName;
		this.portNum = Integer.parseInt(port);
		System.out.println("Host name " + hostName);
		System.out.println("Port " + port);
		try {
			sock = new Socket(hostName, portNum);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void mainLoop(String request){
		try {
			while((dataIn = in.readLine()) != null){
				System.out.println("Data In " + dataIn);
				if(dataIn == "Close"){
					break;
				}
                 System.out.println("Client: " + request);
                 out.println(request);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//test main
	public static void main(String [] args){
		//spawn a thread with a mainLoop for each thing required from the server 
		ClientLogics l = new ClientLogics("localhost", "35530"); 
		l.mainLoop("Temp");
	}
}
