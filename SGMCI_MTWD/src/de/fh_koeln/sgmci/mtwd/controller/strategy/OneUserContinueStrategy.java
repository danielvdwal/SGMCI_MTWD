package de.fh_koeln.sgmci.mtwd.controller.strategy;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;

/**
 * This class is used to check if one user is ready to continue to the next scene.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public class OneUserContinueStrategy extends AbstractUserContinueStrategy {

    public OneUserContinueStrategy(AbstractMTWDSceneController controller) {
        super(controller);
    }

    @Override
    public boolean canContinue() {
        return controller.isUser1ReadyToContinue()
                || controller.isUser2ReadyToContinue()
                || controller.isUser3ReadyToContinue()
                || controller.isUser4ReadyToContinue();
    }
}
