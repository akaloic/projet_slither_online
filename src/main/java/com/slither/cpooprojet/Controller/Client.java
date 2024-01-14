package com.slither.cpooprojet.Controller;

import com.google.gson.Gson;
import com.slither.cpooprojet.Model.Modele;

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

    private Client(String serverAddress, int serverPort, Modele modele) throws IOException {
        this.socket = new Socket(serverAddress, serverPort);    // Connexion au serveur

        this.out = new PrintWriter(socket.getOutputStream(), true);                   // Flux d'écriture
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));       // Flux de lecture

        this.gson = new Gson();

        this.modele = modele;
    }

    public static Client getInstance(String serverAddress, int serverPort, Modele modele) throws IOException {
        if(instance == null) {
            instance = new Client(serverAddress, serverPort, modele);
        }
        return instance;
    }

    public void sendModele(Modele modele) {         //envoi du modele au serveur
        String json = gson.toJson(modele);
        out.println(json);
    }

    private Modele readModele(){                //lecture du modele du serveur
        try {
            String json = in.readLine();
            return gson.fromJson(json, Modele.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void start() {               //lancement du thread de lecture du modele
        new Thread(() -> {
            while (true) {
                Modele newModele = readModele();
                if (newModele != null) {
                    this.modele = newModele;
                }
            }
        }).start();
    }

    public void close() throws IOException {        //fermeture de la connexion
        in.close();
        out.close();
        socket.close();
    }
}
