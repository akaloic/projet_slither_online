package com.slither.cpooprojet.Controller;

import com.google.gson.Gson;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.SerializableObject.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static Client instance = null;  // Singleton
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Gson gson;
    private Modele modele;
    private int id;

    private Client(String serverAddress, int serverPort, Modele modele, int id) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);

        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        this.gson = new Gson();

        this.modele = modele;
        this.id = id;
    }

    public static Client getInstance(String serverAddress, int serverPort, Modele modele, int id) throws IOException {
        if(instance == null) {
            instance = new Client(serverAddress, serverPort, modele, id);
        }
        return instance;
    }

    // public void sendModele(Modele modele) {
    //     String json = gson.toJson(modele);
    //     out.println(json);
    // }

    public void requestClientID() {
        out.println("ID");
    }

    public void sendPosition(Position position) {
        String json = gson.toJson(position);
        out.println(json);
    }

    public void start() {
        new Thread(() -> {
            String json;
            while (true) {
                try {
                    json = in.readLine();
                    
                    if (json.startsWith("ID")) {
                        this.id = Integer.parseInt(json.substring(2));
                    } else {
                        Modele updatedModele = gson.fromJson(json, Modele.class);
                        updatedModele.setMainSnake(id);
                        this.modele = updatedModele;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
