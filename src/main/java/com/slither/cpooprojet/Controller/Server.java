package com.slither.cpooprojet.Controller;

import com.google.gson.Gson;
import com.slither.cpooprojet.Model.Modele;
import com.slither.cpooprojet.Model.SerializableObject.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    protected ServerSocket serverSocket;
    protected ConcurrentHashMap<Socket, PrintWriter> clients;
    protected Gson gson;
    protected boolean running;
    protected Modele modele;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clients = new ConcurrentHashMap<>();
        this.gson = new Gson();
        this.running = true;
        this.modele = new Modele();
    }

    public void start() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();    // accept est bloquant, il attend qu'un client se connecte
                new Thread(new ClientHandler(this, clientSocket)).start();    // créer un thread pour gérer le client

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);    // nous permet d'envoyer des messages au client
                clients.put(clientSocket, out); // ajouter le client et son flux de sortie à la liste des clients
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendClientIdToClient(int id, Socket socket) {
        PrintWriter writer = clients.get(socket);
        String json = "ID" + id;
        writer.println(json);
    }

    public void updateGameModele(Position position) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clients.keys().nextElement().getInputStream()))) {   // nous permet de lire les messages envoyés par le client
            String response = in.readLine();
            Modele updatedModele = gson.fromJson(response, Modele.class);
            // Mettre à jour l'affichage ou l'état local du jeu en fonction de updatedModele
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Diffuser l'objet Modele mis à jour à tous les clients
     * @param modele
     */
    public void sendToAllClient(Modele modele) {
        String json = gson.toJson(modele);

        for (PrintWriter writer : clients.values()) {
            writer.println(json);
        }
    }

    public void stop() throws IOException {
        running = false;
        serverSocket.close();
    }
}

/**
 * classe interne pour gérer les clients
 */
class ClientHandler implements Runnable {
    private Server server;
    private Socket socket;

    public ClientHandler(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {   // nous permet de lire les messages envoyés par le client
            String inputLine;
            while ((inputLine = in.readLine()) != null) {   // in.readLine() est bloquant, il attend qu'un message soit envoyé par le client

                if (inputLine.equals("ID")) {
                    int clientId = server.modele.getNbPlayers();
                    server.sendClientIdToClient(clientId, socket);  // envoyer l'id du client au client
                } else {
                    Position position = server.gson.fromJson(inputLine, Position.class); // convertir le message en objet Position
                    server.updateGameModele(position); // Mettre à jour l'affichage ou l'état local du jeu en fonction de position
                    server.sendToAllClient(server.modele); // Diffuser l'objet Modele mis à jour
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.clients.remove(socket);
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}