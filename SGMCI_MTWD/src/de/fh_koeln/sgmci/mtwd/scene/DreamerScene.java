package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.DreamerSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.SplitKeyboard;
import de.fh_koeln.sgmci.mtwd.exception.NoIdeaTextException;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.2.0
 */
public class DreamerScene extends AbstractMTWDScene {

    private final IFont ideaFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);
    private final DreamerSceneController controller;
    private final Map<String, MTTextArea> displayedIdeas;
    private MTTextArea problemTextArea;
    private MTTextArea problemTextAreaInverted;
    private SplitKeyboard user1Keyboard;
    private SplitKeyboard user2Keyboard;
    private SplitKeyboard user3Keyboard;
    private SplitKeyboard user4Keyboard;
    private MTSvgButton addUser1KeyboardButton;
    private MTSvgButton addUser2KeyboardButton;
    private MTSvgButton addUser3KeyboardButton;
    private MTSvgButton addUser4KeyboardButton;

    private MTSvgButton startButton;

    public DreamerScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new DreamerSceneController(this);
        this.displayedIdeas = new HashMap<String, MTTextArea>();
    }

    @Override
    public void createBackground() {
        // 2400 x 1600
        PImage backgroundImage = mtApp.loadImage("data/background_sky.jpg");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }

    @Override
    public void createComponents() {
        final IFont problemFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 30);

        problemTextArea = new MTTextArea(mtApp, problemFont);
        problemTextArea.setNoFill(true);
        problemTextArea.setNoStroke(true);
        problemTextArea.setPickable(false);

        problemTextAreaInverted = new MTTextArea(mtApp, problemFont);
        problemTextAreaInverted.setNoFill(true);
        problemTextAreaInverted.setNoStroke(true);
        problemTextAreaInverted.setPickable(false);
        problemTextAreaInverted.rotateZ(Vector3D.ZERO_VECTOR, 180);

        getCanvas().addChild(problemTextArea);
        getCanvas().addChild(problemTextAreaInverted);

        user1Keyboard = new SplitKeyboard(mtApp);
        float width = user1Keyboard.getWidth();
        float height = user1Keyboard.getHeight();
        user1Keyboard.setSpaceBetweenKeyboards(200);
        user1Keyboard.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);

        width = width * keyboardScaleFactor;
        height = height * keyboardScaleFactor;

        final MTTextArea user1CurrentTextArea = new MTTextArea(mtApp, ideaFont);
        user1CurrentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
        user1CurrentTextArea.setStrokeColor(new MTColor(0, 0, 0, 255));
        user1CurrentTextArea.setFillColor(new MTColor(205, 200, 177, 255));
        user1CurrentTextArea.setEnableCaret(true);
        user1Keyboard.getLeftKeyboard().addChild(user1CurrentTextArea);
        user1CurrentTextArea.setPositionRelativeToParent(new Vector3D(40, -user1CurrentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));
        user1Keyboard.addTextInputListener(user1CurrentTextArea);

        final MTRectangle user1Rectangle = new MTRectangle(-100, 0, 30, 30, mtApp);
        user1Rectangle.translate(new Vector3D(0, 15, 0));
        user1Rectangle.registerInputProcessor(new TapProcessor(mtApp));
        user1Rectangle.addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user1CurrentTextArea.getText());
                                user1CurrentTextArea.setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });
        user1Keyboard.addChild(user1Rectangle);

        user1Keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - height / 2, 0));

        user2Keyboard = new SplitKeyboard(mtApp);
        user2Keyboard.setSpaceBetweenKeyboards(200);
        user2Keyboard.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);

        final MTTextArea user2CurrentTextArea = new MTTextArea(mtApp, ideaFont);
        user2CurrentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
        user2CurrentTextArea.setStrokeColor(new MTColor(0, 0, 0, 255));
        user2CurrentTextArea.setFillColor(new MTColor(205, 200, 177, 255));
        user2CurrentTextArea.setEnableCaret(true);
        user2Keyboard.getLeftKeyboard().addChild(user2CurrentTextArea);
        user2CurrentTextArea.setPositionRelativeToParent(new Vector3D(40, -user2CurrentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));
        user2Keyboard.addTextInputListener(user2CurrentTextArea);

        final MTRectangle user2Rectangle = new MTRectangle(-100, 0, 30, 30, mtApp);
        user2Rectangle.translate(new Vector3D(0, 15, 0));
        user2Rectangle.registerInputProcessor(new TapProcessor(mtApp));
        user2Rectangle.addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user2CurrentTextArea.getText());
                                user2CurrentTextArea.setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });
        user2Keyboard.addChild(user2Rectangle);

        user2Keyboard.rotateZ(new Vector3D(width / 2, height / 2), 180);
        user2Keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2, height / 2, 0));

        user3Keyboard = new SplitKeyboard(mtApp);
        user3Keyboard.setSpaceBetweenKeyboards(200);
        user3Keyboard.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);

        final MTTextArea user3CurrentTextArea = new MTTextArea(mtApp, ideaFont);
        user3CurrentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
        user3CurrentTextArea.setStrokeColor(new MTColor(0, 0, 0, 255));
        user3CurrentTextArea.setFillColor(new MTColor(205, 200, 177, 255));
        user3CurrentTextArea.setEnableCaret(true);
        user3Keyboard.getLeftKeyboard().addChild(user3CurrentTextArea);
        user3CurrentTextArea.setPositionRelativeToParent(new Vector3D(40, -user3CurrentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));
        user3Keyboard.addTextInputListener(user3CurrentTextArea);

        final MTRectangle user3Rectangle = new MTRectangle(-100, 0, 30, 30, mtApp);
        user3Rectangle.translate(new Vector3D(0, 15, 0));
        user3Rectangle.registerInputProcessor(new TapProcessor(mtApp));
        user3Rectangle.addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user3CurrentTextArea.getText());
                                user3CurrentTextArea.setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });
        user3Keyboard.addChild(user3Rectangle);

        user3Keyboard.rotateZ(new Vector3D(width / 2, height / 2), 90);
        user3Keyboard.setPositionGlobal(new Vector3D(height / 2, mtApp.height / 2, 0));

        user4Keyboard = new SplitKeyboard(mtApp);
        user4Keyboard.setSpaceBetweenKeyboards(200);
        user4Keyboard.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);

        final MTTextArea user4CurrentTextArea = new MTTextArea(mtApp, ideaFont);
        user4CurrentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
        user4CurrentTextArea.setStrokeColor(new MTColor(0, 0, 0, 255));
        user4CurrentTextArea.setFillColor(new MTColor(205, 200, 177, 255));
        user4CurrentTextArea.setEnableCaret(true);
        user4Keyboard.getLeftKeyboard().addChild(user4CurrentTextArea);
        user4CurrentTextArea.setPositionRelativeToParent(new Vector3D(40, -user4CurrentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));
        user4Keyboard.addTextInputListener(user4CurrentTextArea);

        final MTRectangle user4Rectangle = new MTRectangle(-100, 0, 30, 30, mtApp);
        user4Rectangle.translate(new Vector3D(0, 15, 0));
        user4Rectangle.registerInputProcessor(new TapProcessor(mtApp));
        user4Rectangle.addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user4CurrentTextArea.getText());
                                user4CurrentTextArea.setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });
        user4Keyboard.addChild(user4Rectangle);

        user4Keyboard.rotateZ(new Vector3D(width / 2, height / 2), -90);
        user4Keyboard.setPositionGlobal(new Vector3D(mtApp.width - height / 2, mtApp.height / 2, 0));

        user1Keyboard.setPickable(false);
        user1Keyboard.removeAllGestureEventListeners();
        user1Keyboard.unregisterAllInputProcessors();
        this.getCanvas().addChild(user1Keyboard);

        user2Keyboard.setPickable(false);
        user2Keyboard.removeAllGestureEventListeners();
        user2Keyboard.unregisterAllInputProcessors();
        this.getCanvas().addChild(user2Keyboard);

        user3Keyboard.setPickable(false);
        user3Keyboard.removeAllGestureEventListeners();
        user3Keyboard.unregisterAllInputProcessors();
        this.getCanvas().addChild(user3Keyboard);

        user4Keyboard.setPickable(false);
        user4Keyboard.removeAllGestureEventListeners();
        user4Keyboard.unregisterAllInputProcessors();
        this.getCanvas().addChild(user4Keyboard);

        user1Keyboard.setVisible(false);
        user2Keyboard.setVisible(false);
        user3Keyboard.setVisible(false);
        user4Keyboard.setVisible(false);

        addUser1KeyboardButton = new MTSvgButton("data/plusButton.svg", mtApp);
        addUser1KeyboardButton.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - height / 2, 0));
        addUser1KeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                            controller.setUser1Activate(true);
                        break;
                    default:
                        break;
                }
            }
        });
        this.getCanvas().addChild(addUser1KeyboardButton);

        addUser2KeyboardButton = new MTSvgButton("data/plusButton.svg", mtApp);
        addUser2KeyboardButton.rotateZ(new Vector3D(width / 2, height / 2), 180);
        addUser2KeyboardButton.setPositionGlobal(new Vector3D(mtApp.width / 2, height / 2, 0));
        addUser2KeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                            controller.setUser2Activate(true);
                        break;
                    default:
                        break;
                }
            }
        });
        this.getCanvas().addChild(addUser2KeyboardButton);

        addUser3KeyboardButton = new MTSvgButton("data/plusButton.svg", mtApp);
        addUser3KeyboardButton.rotateZ(new Vector3D(width / 2, height / 2), 90);
        addUser3KeyboardButton.setPositionGlobal(new Vector3D(height / 2, mtApp.height / 2, 0));
        addUser3KeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                            controller.setUser3Activate(true);
                        break;
                    default:
                        break;
                }
            }
        });
        this.getCanvas().addChild(addUser3KeyboardButton);

        addUser4KeyboardButton = new MTSvgButton("data/plusButton.svg", mtApp);
        addUser4KeyboardButton.rotateZ(new Vector3D(width / 2, height / 2), -90);
        addUser4KeyboardButton.setPositionGlobal(new Vector3D(mtApp.width - height / 2, mtApp.height / 2, 0));
        addUser4KeyboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                            controller.setUser4Activate(true);
                        break;
                    default:
                        break;
                }
            }
        });
        this.getCanvas().addChild(addUser4KeyboardButton);

        startButton = new MTSvgButton("data/startButton.svg", mtApp);
        getCanvas().addChild(startButton);
        startButton.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
    }

    @Override
    public void startScene() {
        Collection<Idea> ideas = controller.getAllVisibleIdeasForCurrentProblem();
        for (Idea idea : ideas) {
            final MTTextArea newTextArea = new MTTextArea(mtApp, ideaFont);
            newTextArea.setText(idea.getDescription());
            displayedIdeas.put(idea.getId(), newTextArea);
            getCanvas().addChild(newTextArea);
        }

        if (AbstractMTWDSceneController.isUser1Activate()) {
            addUser1KeyboardButton.setVisible(false);
            user1Keyboard.setVisible(true);
        } else {
            user1Keyboard.setVisible(false);
            addUser1KeyboardButton.setVisible(true);
        }
        if (AbstractMTWDSceneController.isUser2Activate()) {
            addUser2KeyboardButton.setVisible(false);
            user2Keyboard.setVisible(true);
        } else {
            user2Keyboard.setVisible(false);
            addUser2KeyboardButton.setVisible(true);
        }
        if (AbstractMTWDSceneController.isUser3Activate()) {
            addUser3KeyboardButton.setVisible(false);
            user3Keyboard.setVisible(true);
        } else {
            user3Keyboard.setVisible(false);
            addUser3KeyboardButton.setVisible(true);
        }
        if (AbstractMTWDSceneController.isUser4Activate()) {
            addUser4KeyboardButton.setVisible(false);
            user4Keyboard.setVisible(true);
        } else {
            user4Keyboard.setVisible(false);
            addUser4KeyboardButton.setVisible(true);
        }

        // needs to be removed and set for each user
        problemTextArea.setText(controller.getCurrentProblemDescription());
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextArea.translate(new Vector3D(0, problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));

        problemTextAreaInverted.setText(controller.getCurrentProblemDescription());
        problemTextAreaInverted.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextAreaInverted.translate(new Vector3D(0, -problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
    }

    @Override
    public void updateScene() {
        Collection<Idea> ideas = controller.getAllVisibleIdeasForCurrentProblem();
        for (Idea idea : ideas) {
            if (!displayedIdeas.containsKey(idea.getId())) {
                final MTTextArea newTextArea = new MTTextArea(mtApp, ideaFont);
                newTextArea.setText(idea.getDescription());
                displayedIdeas.put(idea.getId(), newTextArea);
                getCanvas().addChild(newTextArea);
            }
        }

        if (AbstractMTWDSceneController.isUser1Activate()) {
            addUser1KeyboardButton.setVisible(false);
            user1Keyboard.setVisible(true);
        } else {
            user1Keyboard.setVisible(false);
            addUser1KeyboardButton.setVisible(true);
        }
        if (AbstractMTWDSceneController.isUser2Activate()) {
            addUser2KeyboardButton.setVisible(false);
            user2Keyboard.setVisible(true);
        } else {
            user2Keyboard.setVisible(false);
            addUser2KeyboardButton.setVisible(true);
        }
        if (AbstractMTWDSceneController.isUser3Activate()) {
            addUser3KeyboardButton.setVisible(false);
            user3Keyboard.setVisible(true);
        } else {
            user3Keyboard.setVisible(false);
            addUser3KeyboardButton.setVisible(true);
        }
        if (AbstractMTWDSceneController.isUser4Activate()) {
            addUser4KeyboardButton.setVisible(false);
            user4Keyboard.setVisible(true);
        } else {
            user4Keyboard.setVisible(false);
            addUser4KeyboardButton.setVisible(true);
        }
    }

    @Override
    public void init() {
        mtApp.registerKeyEvent(this);
    }

    @Override
    public void shutDown() {
        mtApp.unregisterKeyEvent(this);
        for (MTTextArea textArea : displayedIdeas.values()) {
            textArea.destroy();
        }
        displayedIdeas.clear();
    }

    public void keyEvent(KeyEvent e) {
        int evtID = e.getID();
        if (evtID != KeyEvent.KEY_PRESSED) {
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_F:
                System.out.println("FPS: " + mtApp.frameRate);
                break;
            default:
                break;
        }
    }

    @Override
    public void createEventListeners() {

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        //controller.proceed(problemInputField.getText());
                        gotoNextScene();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
