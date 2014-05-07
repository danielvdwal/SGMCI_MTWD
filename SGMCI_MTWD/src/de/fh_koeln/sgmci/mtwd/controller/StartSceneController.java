package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.scene.IScene;

/**
 *
 * @author danielvanderwal
 */
public class StartSceneController extends AbstractMTWDSceneController {
    
    private static int problemIdCounter = 0;
    
    public StartSceneController(IScene observer) {
        super(observer);
    }
    
    public void proceed(String problemDesc) {
        currentProblemId = String.format("problem_%03d", problemIdCounter++);
        application.addProblem(currentProblemId, problemDesc);
        observer.gotoNextScene();
    }
}
