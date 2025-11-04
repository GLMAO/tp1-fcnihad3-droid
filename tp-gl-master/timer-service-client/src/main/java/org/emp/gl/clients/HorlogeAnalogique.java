package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.time.LocalTime;

public class HorlogeAnalogique extends JFrame implements TimerChangeListener {

    private TimerService timerService;
    private ClockPanel clockPanel;
    private String name;

    public HorlogeAnalogique(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;
        
        setupUI();
        
        // S'inscrire comme écouteur du TimerService
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("Horloge Analogique " + name + " initialisée!");
        setVisible(true);
    }

    private void setupUI() {
        setTitle("Horloge Analogique - " + name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        
        clockPanel = new ClockPanel();
        add(clockPanel);
        
        // Mettre à jour l'affichage initial
        updateClock();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Mettre à jour l'affichage à chaque changement de temps
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName()) ||
            TimerChangeListener.MINUTE_PROP.equals(evt.getPropertyName()) ||
            TimerChangeListener.HEURE_PROP.equals(evt.getPropertyName())) {
            updateClock();
        }
    }

    private void updateClock() {
        if (clockPanel != null) {
            clockPanel.repaint();
        }
    }

    // Panel personnalisé pour dessiner l'horloge
    class ClockPanel extends JPanel {
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Activer l'antialiasing pour des rendus plus lisses
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Dimensions
            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;
            int radius = Math.min(width, height) / 2 - 20;
            
            // Dessiner le cadran de l'horloge
            drawClockFace(g2d, centerX, centerY, radius);
            
            // Dessiner les aiguilles
            drawHands(g2d, centerX, centerY, radius);
        }
        
        private void drawClockFace(Graphics2D g2d, int centerX, int centerY, int radius) {
            // Fond du cadran
            GradientPaint gradient = new GradientPaint(
                centerX - radius, centerY - radius, new Color(240, 240, 240),
                centerX + radius, centerY + radius, new Color(200, 200, 200)
            );
            g2d.setPaint(gradient);
            g2d.fill(new Ellipse2D.Double(centerX - radius, centerY - radius, radius * 2, radius * 2));
            
            // Bordure du cadran
            g2d.setColor(new Color(100, 100, 100));
            g2d.setStroke(new BasicStroke(3));
            g2d.draw(new Ellipse2D.Double(centerX - radius, centerY - radius, radius * 2, radius * 2));
            
            // Centre de l'horloge
            g2d.setColor(new Color(50, 50, 50));
            g2d.fillOval(centerX - 5, centerY - 5, 10, 10);
            
            // Marques des heures
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            
            for (int i = 1; i <= 12; i++) {
                // CORRECTION : Angle dans le sens horaire
                double angle = Math.toRadians((i * 30) - 90); // 30 degrés par heure, -90 pour commencer à 12h
                
                int numberRadius = radius - 20;
                
                // Position du chiffre
                int x = centerX + (int) (numberRadius * Math.cos(angle)) - 5;
                int y = centerY + (int) (numberRadius * Math.sin(angle)) + 5;
                
                g2d.drawString(String.valueOf(i), x, y);
                
                // Marques plus épaisses pour les heures
                int markLength = 15;
                int innerX1 = centerX + (int) ((radius - 5) * Math.cos(angle));
                int innerY1 = centerY + (int) ((radius - 5) * Math.sin(angle));
                int outerX1 = centerX + (int) ((radius - markLength) * Math.cos(angle));
                int outerY1 = centerY + (int) ((radius - markLength) * Math.sin(angle));
                
                g2d.setStroke(new BasicStroke(3));
                g2d.drawLine(innerX1, innerY1, outerX1, outerY1);
            }
            
            // Marques des minutes (plus fines)
            g2d.setColor(new Color(100, 100, 100));
            g2d.setStroke(new BasicStroke(1));
            for (int i = 0; i < 60; i++) {
                if (i % 5 != 0) { // Ne pas redessiner les marques des heures
                    // CORRECTION : Angle dans le sens horaire
                    double angle = Math.toRadians((i * 6) - 90); // 6 degrés par minute
                    
                    int markLength = 8;
                    int innerX = centerX + (int) ((radius - 5) * Math.cos(angle));
                    int innerY = centerY + (int) ((radius - 5) * Math.sin(angle));
                    int outerX = centerX + (int) ((radius - markLength) * Math.cos(angle));
                    int outerY = centerY + (int) ((radius - markLength) * Math.sin(angle));
                    
                    g2d.drawLine(innerX, innerY, outerX, outerY);
                }
            }
        }
        
        private void drawHands(Graphics2D g2d, int centerX, int centerY, int radius) {
            if (timerService == null) return;
            
            int hours = timerService.getHeures() % 12;
            int minutes = timerService.getMinutes();
            int seconds = timerService.getSecondes();
            
            // CORRECTION : Calcul des angles dans le sens horaire
            // 0° à 12h, 90° à 3h, 180° à 6h, 270° à 9h
            double secondAngle = Math.toRadians((seconds * 6) - 90); // 6° par seconde
            double minuteAngle = Math.toRadians((minutes * 6 + seconds * 0.1) - 90); // 6° par minute + mouvement fluide
            double hourAngle = Math.toRadians((hours * 30 + minutes * 0.5) - 90); // 30° par heure + 0.5° par minute
            
            // Aiguille des secondes (fine et rouge)
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int secondHandLength = radius - 30;
            int secondX = centerX + (int) (secondHandLength * Math.cos(secondAngle));
            int secondY = centerY + (int) (secondHandLength * Math.sin(secondAngle));
            g2d.drawLine(centerX, centerY, secondX, secondY);
            
            // Aiguille des minutes (moyenne et bleue)
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int minuteHandLength = radius - 40;
            int minuteX = centerX + (int) (minuteHandLength * Math.cos(minuteAngle));
            int minuteY = centerY + (int) (minuteHandLength * Math.sin(minuteAngle));
            g2d.drawLine(centerX, centerY, minuteX, minuteY);
            
            // Aiguille des heures (épaisse et noire)
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int hourHandLength = radius - 60;
            int hourX = centerX + (int) (hourHandLength * Math.cos(hourAngle));
            int hourY = centerY + (int) (hourHandLength * Math.sin(hourAngle));
            g2d.drawLine(centerX, centerY, hourX, hourY);
            
            // Centre de l'horloge (cercle)
            g2d.setColor(Color.RED);
            g2d.fillOval(centerX - 6, centerY - 6, 12, 12);
            g2d.setColor(Color.WHITE);
            g2d.fillOval(centerX - 3, centerY - 3, 6, 6);
        }
    }
}