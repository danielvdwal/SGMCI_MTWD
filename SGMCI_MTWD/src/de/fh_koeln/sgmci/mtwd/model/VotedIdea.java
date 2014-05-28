package de.fh_koeln.sgmci.mtwd.model;

/**
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class VotedIdea extends AbstractModel {

    private final Idea idea;
    private VoteType voteType;

    public VotedIdea(Idea idea) {
        super(idea.id);
        this.idea = idea;
        this.voteType = VoteType.NONE;
    }
    
    public Idea getIdea() {
        return idea;
    }
    
    public String getDescription() {
        return idea.getDescription();
    }
    
    public void like() {
        voteType = VoteType.LIKE;
    }
    
    public void dislike() {
        voteType = VoteType.DISLIKE;
    }
    
    public boolean isLiked() {
        return voteType == VoteType.LIKE;
    }
    
    public boolean isDisliked() {
        return voteType == VoteType.DISLIKE;
    }

    enum VoteType {

        NONE,
        LIKE,
        DISLIKE
    }
}
