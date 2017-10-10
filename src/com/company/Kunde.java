package com.company;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Single client serving as "Point" to travel to.
 */
public class Kunde {
    /**
     * The client's raw coordinates
     */
    public final int[] coords;
    private final Kunde depot;
    public String dbgOut;
    public final double X_rad;
    /**
     * Link to the arrayList housing this client.
     *
     * ->> The targetID is the ID of the targetted client within this list!
     */
    private final ArrayList<Kunde> accordingList;
    /**
     * ownID does not include the +1 Offset (get(0)=Depot!)
     */
    private int ownID;

    /**
     * Client-Constructor
     * @param arrayList The list housing the client
     * @param x X-Coordinate (NO OFFSETS IN ACCOUNT!)
     * @param y Y-Coordinate (NO OFFSETS IN ACCOUNT!)
     */
    public Kunde(Kunde depot, ArrayList<Kunde> arrayList, int x, int y) {
        this.depot = depot;
        this.dbgOut = String.valueOf(arrayList.size());
        coords = new int[]{x, y};
        this.accordingList = arrayList;
        this.X_rad = Math.toDegrees(calcRad());
        try {
            File dbgFile = new File("dbg" + this.dbgOut + ".txt");
            if (dbgFile.exists()) dbgFile.delete();
            FileWriter dbgOut = new FileWriter(dbgFile);
            dbgOut.write(String.valueOf(diff0(depot.coords[0] - this.coords[0])) + ";" + String.valueOf(diff0(depot.coords[1] - this.coords[1])) + ";" + String.valueOf(this.X_rad));
            dbgOut.close();
        } catch (IOException ex) {
            System.err.println("Could not write debug file - " + this.dbgOut + " - " +
                    String.valueOf(this.coords[0]) + ";" + String.valueOf(this.coords[1]) + ";" + String.valueOf(this.X_rad));
        }
    }

    public Kunde(int x, int y) {
        this.depot = null;
        this.dbgOut = "Depot";
        coords = new int[]{x, y};
        this.accordingList = null;
        X_rad = 0;
    }

    /**
     * Initialize the client (do not do so before all clients were created inside the list, and do so for every client!)
     * @param XOffset The X-Offset to take in the calculation
     * @param YOffset The Y-Offset to take in the calculation
     * @deprecated Not in use anymore!
     */
    public void init(int XOffset, int YOffset) {

    }

    public Kunde getClosestClient() {
        Kunde closest = this;
        for (int i =0; i < accordingList.size(); i++) {
            if ((this.calcDistance(closest) > this.calcDistance(accordingList.get(i)) || closest == this)) {
                closest = accordingList.get(i);
            }
        }

        return closest;
    }

    private int diff0 (int i) {
        if (i < 0) i = i * -1;
        return i;
    }

    private double diff0 (double i) {
        if (i < 0) i = i * -1;
        return i;
    }

    private double calcRad() {
        int x = coords[0];
        int y = depot.coords[1];
        //int depotDistance = diff0(depot.coords[0] - x);
        int clientDistance = diff0(coords[1] - y);

        double sinValue = clientDistance / getDistance(depot);
        double rad = Math.asin(sinValue);
        double result;
        if (x < depot.coords[0]) { // Left of depot
            if (y < coords[1]) { // Above Client
                result = 1 - rad;
            } else if (y > coords[1]) { // Below Client
                result = rad;
            } else { // Same y -> directly next to client --> client next to depot!
                result = 0;
            }
        } else if (x > depot.coords[0]) { // Right of depot
            if (y < coords[1]) { // Above client
                result = rad;
            } else if (y > coords[1]) { // Below client
                result = 1 - rad;
            } else { // Same y -> directly next to client
                result = 0.5;
            }
        } else { // Same x -> above depot
            return 0;
        }
        return diff0(result);
    }

    /**
     * Get the distance to the target client
     * @param target The client to target
     * @return The distance to the target client, -1 if not found.
     */
    public double getDistance(Kunde target) {
        return this.calcDistance(target);
    }

    /**
     * Calculation of the depot distance
     */
    public double calcDepotDistance() {
        return calcDistance(depot);
    }

    /**
     * Calculates the distance between this and the specified client
     */
    private double calcDistance(Kunde client) {
        if (client == this) {
            return 0;
        }

        int x1, x2, y1, y2;
        x1 = this.coords[0];
        x2 = client.coords[0];
        y1 = this.coords[1];
        y2 = client.coords[1];
        int diffx, diffy;
        diffx = x2 - x1;
        diffy = y2 - y1;
        return Math.sqrt((diffx * diffx) + (diffy * diffy));
    }
}