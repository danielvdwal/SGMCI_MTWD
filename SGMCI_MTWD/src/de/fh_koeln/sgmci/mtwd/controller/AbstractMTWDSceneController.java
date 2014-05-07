package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.model.MultitouchWaltDisneyApplication;
import de.fh_koeln.sgmci.mtwd.scene.IScene;

/**
 *
 * @author danielvanderwal
 */
public abstract class AbstractMTWDSceneController {
    
    final static MultitouchWaltDisneyApplication application = new MultitouchWaltDisneyApplication();
    static String currentProblemId;
    IScene observer;

    public AbstractMTWDSceneController(IScene observer) {
        this.observer = observer;
    }
    
    public String getCurrentProblemDescription() {
        return application.getProblem(currentProblemId).getDescription();
    }
}
