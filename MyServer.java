import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.io.*;
import java.util.*; 
import java.util.Map.Entry;

//connection

//streams - input / outputstream

//Socket socket = mpa.get(id)
class MyServer{  



    static List<Client> clientList = new ArrayList<>();    
static Map< String  , Socket> map  = new HashMap<>();

    public static final String MESSAGE_FORMAT = "message||{receiver}::{sender}::{message}";
    public static final String LIST_FORMAT = "namelist||{onlinelist}";

public static  void refreashlist() throws Exception{
	  
            //added client obj to online people list 
            String online = "";             
            
			
            for (Client client1 : clientList) {
				
         String name1 = client1.name;
              String uuid = client1.uuid;
			    if (!online.isEmpty()) {
					online = online + "," + name1 + "::" + uuid;
				} else {
					online = online + name1 + "::" + uuid;
				}
                
           } 
            
            System.out.println("client thread started");
            System.out.println(online);
			for (Socket s1 : map.values()){
				    String payload = LIST_FORMAT.replace("{onlinelist}", online);
					Utility.send(s1, payload);	
			}
	
}



public static void main(String args[])throws Exception{ 


    
ServerSocket ss=new ServerSocket(3333);  


while(true) { 
        Socket clientCon = ss.accept();		
        Thread clientThread = new ClientThread(clientCon, clientList , map);
        clientThread.start();
		
		
}

}}  

class ClientThread extends Thread {
	public static final String PIPE_DELIMETER = "\\|\\|";
        Socket clientSocket;
        List<Client> clientList;
		Map< String  , Socket> map;

        public ClientThread(Socket clientSocket , List clientList, Map map) {
                this.clientSocket = clientSocket;
                this.clientList = clientList;
				this.map=map;
        }


       public void run() {
		   try {
           System.out.println("client connected.....");

           String nameWithId = Utility.recieve(clientSocket);
           String[] ClientnamaWithId = nameWithId.split("::");
                String name = ClientnamaWithId[0];
                String id = ClientnamaWithId[1];
				map.put(id , clientSocket);
			
           System.out.println(name + " "+ id);

           //create client obj for server
		   Client client = new Client(name, id);
		   clientList.add(client);
		   MyServer.refreashlist();
          
			while(true){
			String payload=Utility.recieve(clientSocket);
	
			System.out.println("this is msg paylod" + payload);
			   String prefix = payload.split(PIPE_DELIMETER)[0];
                          System.out.println( " this is prefix "+ prefix);
                            String suffix = payload.split(PIPE_DELIMETER)[1];
                           System.out.println("this is suffix"+ suffix);
						   if ("message".equals(prefix)){
							   String[] msgWithId = suffix.split("::");
							   String reciverId = msgWithId[0];
							   String senderId = msgWithId[1];
							   String msg = msgWithId[2];
							   
							   Socket clsocket= map.get(reciverId);
							  
							   
							   for (Entry<String, Socket> entry : map.entrySet()) {
								   System.out.println("key - " + entry.getKey());
								   System.out.println("value = " + entry.getValue());
							   }
							   
							  // System.out.println("destination details - " + Id);
							 //  System.out.println("clscoket " + clsocket);
							   
							   Utility.send(clsocket,payload);
 						   }
                Thread.sleep(1000);
			}
            
            
            
		   } catch(Exception e) {
				System.out.println(e.getMessage());
		   }

       }

}

class Client {
    String name;
    String uuid;

    public Client(String name, String uuid) {
            this.name = name;
            this.uuid = uuid;
    }


}


class Utility {
	
	public static void send(Socket clientSocket, String message) {
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            DataOutputStream dout = new DataOutputStream(outputStream);
            dout.writeUTF(message);
            dout.flush();
        } catch (Exception e) {
            System.out.println("ERROR in send - " + e.getMessage());
        }
    }


    public static String recieve(Socket clientSocket) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            DataInputStream dout = new DataInputStream(inputStream);
            String message = dout.readUTF();
            return message;
        } catch (Exception e) {
            System.out.println("ERROR in send - " + e.getMessage());
        }
        return "";
    }
	
	
}