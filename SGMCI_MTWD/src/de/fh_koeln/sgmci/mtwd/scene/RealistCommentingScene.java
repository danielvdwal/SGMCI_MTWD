package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.RealistCommentingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.AbstractKeyboard;
import de.fh_koeln.sgmci.mtwd.customelements.Keyboard;
import de.fh_koeln.sgmci.mtwd.customelements.RealistCommentingModeratorWorkplace;
import de.fh_koeln.sgmci.mtwd.model.Idea;

import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.font.FontManager;
import org.mt4j.util.font.IFont;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.3.0
 */
public class RealistCommentingScene extends AbstractMTWDScene {
	
	private RealistCommentingModeratorWorkplace moderatorWorkplace;

    public RealistCommentingScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new RealistCommentingSceneController(this);
    }

    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/background_wood.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }

    @Override
    public void createComponents() {
    	moderatorWorkplace = new RealistCommentingModeratorWorkplace(mtApp);
    	moderatorWorkplace.scale(componentScaleFactor, componentScaleFactor, componentScaleFactor, Vector3D.ZERO_VECTOR);
    	moderatorWorkplace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - moderatorWorkplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20));
    	getCanvas().addChild(moderatorWorkplace);

        final MTRectangle rectangle = new MTRectangle(mtApp, -100, 0, 30, 30);
        rectangle.translate(new Vector3D(0, 15, 0));
        rectangle.registerInputProcessor(new TapProcessor(mtApp));
        rectangle.addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_STARTED) {
//                            final MTTextArea newTextArea = new MTTextArea(mtApp, ideaFont);
//                            newTextArea.setText(commentTextArea.getText());
//                            getCanvas().addChild(newTextArea);
//
//                            commentTextArea.setText("");
                        }
                        return false;
                    }
                });
        moderatorWorkplace.addChild(rectangle);
        
    }

    @Override
    public void createEventListeners() {
        // displays where the screen is touched
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        moderatorWorkplace.getStartButton().addGestureListener(TapProcessor.class, new IGestureEventListener() {
            
            @Override
            public boolean processGestureEvent(MTGestureEvent mtge) {
                switch (mtge.getId()) {
                    case TapEvent.GESTURE_ENDED:
                        //controller.proceed(problemInputField.getText());
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    
    @Override
    public void startScene() {
    	updateScene();
    	moderatorWorkplace.fillIdeas(controller.getAllVisibleIdeasForCurrentProblem());
    }

    @Override
    public void updateScene() {
    	moderatorWorkplace.setIsActive(AbstractMTWDSceneController.isUserActive(AbstractMTWDSceneController.user1Id));
    }
}
