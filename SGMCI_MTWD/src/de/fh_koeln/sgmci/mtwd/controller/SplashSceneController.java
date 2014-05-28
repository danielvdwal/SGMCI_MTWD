package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.scene.IScene;

/**
 * This class is used as the controller for the StartScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific to
 * the StartScene.
 *
 * @author Daniel van der Wal, Robert Scherbarth
 * @version 0.3.0
 */
public class SplashSceneController extends AbstractMTWDSceneController {

    public final static String APPLICATION_ABBREVIATION = "MTWD";
    public final static String APPLICATION_NAME = "Multi-Touch-Walt-Disney Methode";
    public final static String LOADING_TEXT = "wird geladen . . .";
    public final static String TECHINQUE_DESCRIPTION = "Beschreibung \n\n"
            + "In der Walt Disney Kreativit\u00e4tstechnik, welche \n"
            + "vom ber\u00fchmten Filmproduzenten Walt Disney \n"
            + "erdacht wurde, versetzt sich eine Gruppe oder \n"
            + "auch Einzelperson nacheinander in drei Rollen \n"
            + "(Tr\u00e4umer, Macher und Kritiker) um somit auf \n"
            + "verschiedene kreative Ideen zu kommen.";
    public final static String DEVELOPERS = "Entwickler: \n\n"
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
