package com.slither.cpooprojet.Model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Gson gson;

    public Client(String serverAddress, int serverPort) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);

        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.gson = new Gson();
    }

    public void sendModele(Modele modele) {
        String json = gson.toJson(modele);
        out.println(json);
    }

    public Modele receiveModele() throws IOException {
        String response = in.readLine();
        return gson.fromJson(response, Modele.class);
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
