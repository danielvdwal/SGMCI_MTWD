package de.fh_koeln.sgmci.mtwd.controller.strategy;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;

/**
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class UsersAreReadyContinueStrategy extends AbstractUserContinueStrategy {
    
    @Override
    public boolean canContinue() {
        return AbstractMTWDSceneController.getApplication().getAllActiveUsersWhoAreReady().size() == nrOfUsers;
    }
}
