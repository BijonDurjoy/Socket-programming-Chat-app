package Multiple_client_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread implements Runnable{

    Socket clientSocket;
    Thread t;

    ServerThread(Socket clientSocket){
        this.clientSocket = clientSocket;
        t = new Thread(this);
        t.start();
    }


    @Override
    public void run() {

        try {
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());

            while(true) {
                //read from client...
                Object cMsg = ois.readObject();
                if(cMsg == null){
                    break;
                }
                System.out.println("From Client: " + (String) cMsg);

                String serverMsg = (String) cMsg;
                serverMsg = serverMsg.toUpperCase();

                //send to client..
                oos.writeObject(serverMsg);
            }

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
