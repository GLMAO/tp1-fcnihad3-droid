package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.clients.HorlogeGUI;
import org.emp.gl.clients.HorlogeAnalogique;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;
import javax.swing.SwingUtilities;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            testHorlogeAnalogique();
        });
    }

    private static void testHorlogeAnalogique() {
        // 1. Instancier le TimerService
        TimerService timerService = new DummyTimeServiceImpl();
        
        // 2. Créer l'horloge analogique réaliste
        HorlogeAnalogique horlogeAnalogique = new HorlogeAnalogique("Horloge Salon", timerService);
        
        // 3. (Optionnel) Créer aussi l'horloge numérique
        HorlogeGUI horlogeNumerique = new HorlogeGUI("Horloge Bureau", timerService);
        horlogeNumerique.setLocation(400, 0); // Déplacer la fenêtre numérique
        
        // 4. (Optionnel) Garder l'horloge console
        Horloge horlogeConsole = new Horloge("Console", timerService);
        
        System.out.println("=== HORLOGE ANALOGIQUE RÉALISTE ===");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}