package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.exception.NoIdeaTextException;
import de.fh_koeln.sgmci.mtwd.exception.NoIdeasException;
import de.fh_koeln.sgmci.mtwd.scene.IScene;

/**
 * This class is used as the controller for the DreamerScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific to
 * the DreamerScene.
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class DreamerSceneController extends AbstractMTWDSceneController {

    private static final String NO_IDEA_TEXT = "Es wurde keine Idee eingegeben.";
    private static final String NO_IDEAS_TEXT = "Es wurden keine Ideen angegeben.";
    private static final String HELP_TEXT = "Auf diesem Screen k\u00F6nnen Sie ihre Ideen\n"
            + "um das Problem zu l\u00F6sen eingeben.\n"
            + "Benutzen Sie dazu einfach die Tastatur vor Ihnen\n"
            + "und wischen Sie, wenn Sie fertig sind, mit dem Finger nach oben,\n"
            + "um diese an das System zu senden.";
    private static int ideaIdCounter = 0;

    public DreamerSceneController(IScene observer) {
        super(observer);
    }
    
    public String getHelpText() {
        return HELP_TEXT;
    }

    public void createIdea(String ideaDescription) throws NoIdeaTextException {
        ideaDescription = ideaDescription.trim();
        if (ideaDescription.isEmpty()) {
            throw new NoIdeaTextException(NO_IDEA_TEXT);
        }
        application.getProblem(currentProblemId).addIdea(String.format("idea_%03d", ideaIdCounter++), ideaDescription);
        observer.updateScene();
    }

    public void proceed() throws NoIdeasException {
        if (userContinueStrategy.canContinue() && !getAllVisibleIdeasForCurrentProblem().isEmpty()) {
            observer.gotoNextScene();
        } else if (userContinueStrategy.canContinue() && getAllVisibleIdeasForCurrentProblem().isEmpty()) {
            throw new NoIdeasException(NO_IDEAS_TEXT);
        }
    }
}
