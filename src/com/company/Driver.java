package com.company;
import java.awt.*;
import java.util.ArrayList;

public class Driver {
    private ArrayList<Kunde> Clients;
    private final Kunde depot;
    public final Color lineColor;

    public Driver(Kunde depot, Color lineColor, ArrayList<Kunde> clients) {
        this.Clients = clients;
        this.depot = depot;
        this.lineColor = lineColor;
    }

    private class NotSupposedExceütion extends Exception {
        public NotSupposedExceütion(String msg) {
            super(msg);
        }
    }

    public void init() throws NotSupposedExceütion {
        // Process the list for the client order
        // -> Get the closest client (depot distance)
        // -> clostest-client = next client

        // Get closest depot-client
        double closestDistane = -1;
        Kunde closestClient = depot;
        for (Kunde client : Clients) {
            double distance = client.getDistance(depot);
            if (client == depot) {
                throw new NotSupposedExceütion("Clients is not supposed to hold the depot!");
            } else if (closestClient == client) {
                throw new NotSupposedExceütion("Doubled client detected!");
            }else if (closestDistane == -1 || distance < closestDistane) {
                closestDistane = distance;
                closestClient = client;
            }
        }
    }

    public void debugListPrint() {
        String out = "D";
        for (Kunde c : Clients) {
            if (!c.dbgOut.equals("0")) {
                out += " - " + c.dbgOut;
            }
        }
        System.out.println(out);

    }

    public Point[] getPoints() {
        Point[] returnValue = new Point[Clients.size()];
        for (int i = 0; i < Clients.size(); i++) {
            returnValue[i] = new Point(Clients.get(i).coords[0], Clients.get(i).coords[1]);
        }
        return returnValue;
    }

    public int getClientCount() {
        return Clients.size();
    }

}