package de.fh_koeln.sgmci.mtwd.controller.strategy;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;

/**
 * This class is used to check if four users are ready to continue to the next
 * scene.
 *
 * @author Daniel van der Wal
 * @version 0.2.0
 */
public class FourUsersContinueStrategy extends AbstractUserContinueStrategy {

    public FourUsersContinueStrategy(AbstractMTWDSceneController controller) {
        super(controller);
    }

    @Override
    public boolean canContinue() {
        return controller.isUser1ReadyToContinue()
                && controller.isUser2ReadyToContinue()
                && controller.isUser3ReadyToContinue()
                && controller.isUser4ReadyToContinue();
    }
}
