package com.slither.cpooprojet.Model;

import com.google.gson.Gson;

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
    private boolean running;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clients = new ConcurrentHashMap<>();
        this.gson = new Gson();
        this.running = true;
    }

    public void start() {
        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();    // accept est bloquant, il attend qu'un client se connecte
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);    // nous permet d'envoyer des messages au client
                clients.put(clientSocket, out); // ajouter le client et son flux de sortie à la liste des clients

                new Thread(new ClientHandler(this, clientSocket)).start();    // créer un thread pour gérer le client
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                Modele modele = server.gson.fromJson(inputLine, Modele.class);
                server.sendToAllClient(modele); // Diffuser l'objet Modele mis à jour
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