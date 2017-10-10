package com.company;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.util.Random;
import java.util.ArrayList;

public class test extends javax.swing.JFrame {
    private int offset;
    private ArrayList<Kunde>[] clients;
    public JLabel depot;


    private class ContentPane extends JPanel {
        private final Driver[] drivers;
        private final int yOffset;

        public ContentPane(Driver[] drivers, int yOffset) {
            this.drivers = drivers;
            this.yOffset = yOffset;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(2,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
            for (Driver d : drivers) {
                g2d.setColor(d.lineColor);
                for (int i = 0; i < d.getPoints().length - 1; i++) {
                    g2d.drawLine(d.getPoints()[i].x, d.getPoints()[i].y + yOffset, d.getPoints()[i + 1].x, d.getPoints()[i + 1].y + yOffset);
                }

            }
        }
    }


    public test(int[] dimensions, int targets, int driverCount) {
        clients = new ArrayList[driverCount + 1];
        for (int i = 0; i < clients.length; i++) {
            clients[i] = new ArrayList<>();
        }

        if (dimensions.length != 2) {
            System.err.println("Dimension not specified!");
            return;
        }
        Kunde depotClient = new Kunde(dimensions[0] / 2, 0); // Add depot "Client"
        this.offset = dimensions[1] / 2;
        this.setLayout(new SpringLayout());
        Random rand = new Random();
        for (int a = 0; a < targets; a++) {
            clients[0].add(new Kunde(depotClient, clients[0],
                    rand.nextInt(dimensions[0] - 100) + 50,
                    (offset - 50) - rand.nextInt(dimensions[1] - 100)));
        }
        Driver[] drivers = new Driver[driverCount];;
        for (Kunde e : clients[0]) {
            e.init(0, dimensions[1] / 2);
            for (int i = 0; i < drivers.length; i++) {
                drivers[i] = new Driver(depotClient, new Color(255 * i / drivers.length, 255 * i / drivers.length, 255 * i / drivers.length), clients[i+1]);
            }
        }

        // Distribute clients to drivers
        double perDriverCut = (double) 360 / (double) driverCount;
        for (Kunde k : clients[0]) {
            System.out.println("DEBUG X_rad for " + String.valueOf(k.coords[0]) + ";" + String.valueOf(k.coords[1]) + ":" + String.valueOf(k.X_rad));
            int corrDriver = (int) Math.floor(k.X_rad / perDriverCut);
            clients[corrDriver + 1].add(k);
            System.out.println("DEBUG added for driver" + String.valueOf(corrDriver));
        }


        ContentPane mainFrame = new ContentPane(drivers, offset);
        mainFrame.setBorder(new TitledBorder("Test"));
        mainFrame.setPreferredSize(new Dimension(dimensions[0], dimensions[1]));
        mainFrame.setLayout(null);
        this.add(mainFrame);
        mainFrame.setMinimumSize(new Dimension(dimensions[0], dimensions[1]));
        mainFrame.setSize(dimensions[0], dimensions[1]);
        this.depot = new JLabel(" +Depot");
        mainFrame.add(this.depot);
        this.depot.setBounds(mainFrame.getBounds());
        this.depot.setLocation(dimensions[0] / 2, 0);
        this.depot.setVisible(true);
        JLabel[] points = new JLabel[targets];

        for (int a = 0; a < clients[0].size(); a++) {
            points[a] = new JLabel((char) 33 + " " + String.valueOf(a));
            mainFrame.add(points[a]);
            points[a].setBounds(mainFrame.getBounds());
            points[a].setLocation(clients[0].get(a).coords[0], clients[0].get(a).coords[1]);
            points[a].setVisible(true);
        }
    }
}