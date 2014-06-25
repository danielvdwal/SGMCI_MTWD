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
 * This class is used as the controller for the CriticerVotingScene.<br >
 * It extends the AbstractMTWDSceneController and controls actions specific to
 * the CriticerVotingScene.
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public class CriticerVotingSceneController extends AbstractMTWDSceneController {

    private final Map<String, Collection<VotedIdea>> votedIdeasByUsers;
    private static final String HELP_TEXT = "Auf diesem Screen k\u00F6nnen Sie\n"
            + "zum letzten Mal die restlichen Ideen bewerten.\n"
            + "Durch den \"Daumen hoch\" k\u00F6nnen Sie eine Idee positiv bewerten.\n"
            + "Durch den \"Daumen runter\" stellen Sie sich gegen die Idee.";

    public CriticerVotingSceneController(IScene observer) {
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
                        votedIdea.getIdea().setTotalCriticerLikes(votedIdea.getIdea().getTotalCriticerLikes()+ 1);
                    } else if (votedIdea.isDisliked()) {
                        votedIdea.getIdea().setTotalCriticerDislikes(votedIdea.getIdea().getTotalCriticerDislikes()+ 1);
                    }
                }
            }
            Collection<Idea> ideas = getAllIdeasForCurrentProblem();
            for (Idea idea : ideas) {
                if(((double)idea.getTotalCriticerDislikes()/ application.getAllActiveUsers().size()) > filterValue) {
                    idea.setStillDisplayed(false);
                }
            }
            observer.gotoNextScene();
        }
    }
}
