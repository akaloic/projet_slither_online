package com.slither.cpooprojet.Model;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    
    public Server(int port) throws Exception{
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public void send(Object o) throws Exception{
        out.writeObject(o);
    }

    public Object receive() throws Exception{
        return in.readObject();
    }

    public void close() throws Exception{
        in.close();
        out.close();
        socket.close();
        serverSocket.close();
    }
}
