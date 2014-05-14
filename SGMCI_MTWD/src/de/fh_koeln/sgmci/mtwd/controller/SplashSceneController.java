package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.scene.IScene;

/**
 * This class is used as the controller for the StartScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific
 * to the StartScene.
 * 
 * @author Daniel van der Wal, Robert Scherbarth
 * @version 0.1.0
 */
public class SplashSceneController extends AbstractMTWDSceneController {
    
    public final static String APPLICATION_ABBREVIATION = "MTWD";
    public final static String APPLICATION_NAME = "Multi-Touch-Walt-Disney Methode";
    public final static String LOADING_TEXT = "wird geladen . . .";
    public final static String TECHINQUE_DESCRIPTION = "Beschreibung \n"
            + "kurze Beschreibung der Technik";
    public final static String DEVELOPERS = "Entwickler: \n"
                + "Daniel van der Wal \n"
                + "Robert Scherbarth \n"
                + "Nadim Khan \n"
                + "Ramon Victor";
    public final static String MODULE = "spezielle Gebiete der MCI / SS14";
    public final static String PROF = "Prof. Dr. Heiner Klocke";
    
    public SplashSceneController(IScene observer) {
        super(observer);
    }
    
    /**
     * Order the scene to continue to the next one.
     */
    public void proceed() {
        observer.gotoNextScene();
    }
}
