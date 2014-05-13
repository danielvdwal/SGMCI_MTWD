package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.scene.IScene;

/**
 * This class is used as the controller for the StartScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific
 * to the StartScene.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public class StartSceneController extends AbstractMTWDSceneController {
    
    private static int problemIdCounter = 0;
    
    public StartSceneController(IScene observer) {
        super(observer);
    }
    
    /**
     * Creates a new problem with an ongoing identity and given description.<br >
     * After its creation, the scene is ordered to continue to the next one.
     * 
     * @param problemDesc the problem description that should be used
     */
    public void proceed(String problemDesc) {
        currentProblemId = String.format("problem_%03d", problemIdCounter++);
        application.addProblem(currentProblemId, problemDesc);
        observer.gotoNextScene();
    }
}
