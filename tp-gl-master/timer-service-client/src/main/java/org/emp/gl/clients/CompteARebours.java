package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;
import java.beans.PropertyChangeEvent;

public class CompteARebours implements TimerChangeListener {

    private int compteur;
    private TimerService timerService;
    private String name;

    public CompteARebours(String name, int initial, TimerService timerService) {
        this.name = name;
        this.compteur = initial;
        this.timerService = timerService;
        
        // S'inscrire comme écouteur du TimerService
        this.timerService.addTimeChangeListener(this);
        
        System.out.println(name + " initialisé avec " + initial + " secondes");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Décrémenter seulement quand les secondes changent
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            if (compteur > 0) {
                System.out.println(name + " : " + compteur);
                compteur--;
                
                // Se désinscrire quand le compte arrive à 0
                if (compteur == 0) {
                    System.out.println(name + " : TERMINÉ !");
                    timerService.removeTimeChangeListener(this);
                }
            }
        }
    }
}