package de.fh_koeln.sgmci.mtwd.controller;

import de.fh_koeln.sgmci.mtwd.model.Idea;
import de.fh_koeln.sgmci.mtwd.model.User;
import de.fh_koeln.sgmci.mtwd.model.VotedIdea;
import de.fh_koeln.sgmci.mtwd.scene.IScene;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * This class is used as the controller for the RealistVotingScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific to
 * the RealistVotingScene.
 *
 * @author Nadim Khan, Ramon Victor
 * @version 0.3.0
 */
public class RealistVotingSceneController extends AbstractMTWDSceneController {

    private final Map<String, Collection<VotedIdea>> votedIdeasByUsers;
    private static final String HELP_TEXT = "Auf diesem Screen k\u00F6nnen Sie alle Ideen bewerten.\n"
            + "Durch die Gesamtwertung k\u00F6nnen Ideen aus dem weiteren Verfahren entfernt werden.\n"
            + "Durch den \"Daumen hoch\" k\u00F6nnen Sie eine Idee positiv bewerten.\n"
            + "Durch den \"Daumen runter\" stellen Sie sich gegen die Idee.";

    public RealistVotingSceneController(IScene observer) {
        super(observer);
        votedIdeasByUsers = new HashMap<String, Collection<VotedIdea>>();
    }
    
    public String getHelpText() {
        return HELP_TEXT;
    }

    @Override
    public void update() {
        votedIdeasByUsers.clear();
        for (User user : getApplication().getAllUsers()) {
            Collection<VotedIdea> ideas = new LinkedList<VotedIdea>();
            for (Idea idea : getAllVisibleIdeasForCurrentProblem()) {
                ideas.add(new VotedIdea(idea));
            }
            votedIdeasByUsers.put(user.getId(), ideas);
        }
    }

    public Collection<VotedIdea> getAllVotedIdeasForUser(String id) {
        return votedIdeasByUsers.get(id);
    }

    public void like(VotedIdea idea) {
        idea.like();
    }

    public void dislike(VotedIdea idea) {
        idea.dislike();
    }

    public void proceed() {
        if (userContinueStrategy.canContinue()) {
            for (Collection<VotedIdea> votedIdeas : votedIdeasByUsers.values()) {
                for (VotedIdea votedIdea : votedIdeas) {
                    if (votedIdea.isLiked()) {
                        votedIdea.getIdea().setTotalRealistLikes(votedIdea.getIdea().getTotalRealistLikes() + 1);
                    } else if (votedIdea.isDisliked()) {
                        votedIdea.getIdea().setTotalRealistDislikes(votedIdea.getIdea().getTotalRealistDislikes() + 1);
                    }
                }
            }
            observer.gotoNextScene();
        }
    }
}
