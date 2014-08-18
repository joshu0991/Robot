package com.joshu.dnsdynamic.RobotServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RobotServer {

	private int portNum = 35530;
	private Socket clientSocket;
	private ServerSocket serverSocket;
	private PrintWriter out;
	private BufferedReader in;
	private InputProtocol iProt;
	private String dataOut, dataIn;

	{
		try {
			serverSocket = new ServerSocket(portNum);
			clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	RobotServer() {
		System.out.println("Server started");
	}

	RobotServer(int portNum) {
		this.portNum = portNum;
	}

	public void initComm() {
		iProt = new InputProtocol();
		dataOut = iProt.processInput(null);
		out.println(dataOut);
	}

	public void mainLoop(){
		try {
			while((dataIn = in.readLine()) != null){
				dataOut = iProt.processInput(dataIn);
				out.println(dataOut);
				if(dataOut == "Close"){
					out.write("Closing");
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	  //test main 
	public static void main(String [] args){ 
	RobotServer s = new RobotServer(); 
	s.initComm(); 
	s.mainLoop(); 
	} 
}
