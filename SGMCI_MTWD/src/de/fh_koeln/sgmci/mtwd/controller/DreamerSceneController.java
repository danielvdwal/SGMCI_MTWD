package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.exception.NoIdeaTextException;
import de.fh_koeln.sgmci.mtwd.scene.IScene;

/**
 * This class is used as the controller for the DreamerScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific to
 * the DreamerScene.
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class DreamerSceneController extends AbstractMTWDSceneController {

    private static final String NO_IDEA_TEXT = "Es wurde keine Idee eingegeben.";
    private static int ideaIdCounter = 0;

    public DreamerSceneController(IScene observer) {
        super(observer);
    }

    public void createIdea(String ideaDescription) throws NoIdeaTextException {
        ideaDescription = ideaDescription.trim();
        if (ideaDescription.isEmpty()) {
            throw new NoIdeaTextException(NO_IDEA_TEXT);
        }
        application.getProblem(currentProblemId).addIdea(String.format("idea_%03d", ideaIdCounter++), ideaDescription);
        observer.updateScene();
    }
}
