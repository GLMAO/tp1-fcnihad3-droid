package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.awt.*;

public class HorlogeGUI extends JFrame implements TimerChangeListener {

    private TimerService timerService;
    private JLabel timeLabel;
    private JLabel dateLabel;
    private String name;

    public HorlogeGUI(String name, TimerService timerService) {
        this.name = name;
        this.timerService = timerService;
        
        setupUI();
        
        // S'inscrire comme écouteur du TimerService
        this.timerService.addTimeChangeListener(this);
        
        System.out.println("Horloge GUI " + name + " initialisée!");
        setVisible(true);
    }

    private void setupUI() {
        setTitle("Horloge - " + name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Création du panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        
        // Label pour l'heure
        timeLabel = new JLabel("", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Digital-7", Font.BOLD, 48));
        timeLabel.setForeground(Color.GREEN);
        timeLabel.setBackground(Color.BLACK);
        timeLabel.setOpaque(true);
        
        // Label pour la date (optionnel)
        dateLabel = new JLabel("Horloge Numérique", SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBackground(Color.BLACK);
        dateLabel.setOpaque(true);
        
        // Ajouter les composants
        mainPanel.add(timeLabel, BorderLayout.CENTER);
        mainPanel.add(dateLabel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Mettre à jour l'affichage initial
        updateTimeDisplay();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Mettre à jour l'affichage à chaque changement de seconde
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            updateTimeDisplay();
        }
    }

    private void updateTimeDisplay() {
        if (timerService != null) {
            String time = String.format("%02d:%02d:%02d", 
                timerService.getHeures(),
                timerService.getMinutes(), 
                timerService.getSecondes());
            
            timeLabel.setText(time);
        }
    }
}