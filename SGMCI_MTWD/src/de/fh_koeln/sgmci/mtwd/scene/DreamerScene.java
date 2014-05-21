package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.AbstractMTWDSceneController;
import de.fh_koeln.sgmci.mtwd.controller.DreamerSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.DreamerUserWorkplace;
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
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.input.gestureAction.TapAndHoldVisualizer;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapAndHoldProcessor.TapAndHoldProcessor;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MT4jSettings;
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
    private DreamerUserWorkplace user1Workplace;
    private DreamerUserWorkplace user2Workplace;
    private DreamerUserWorkplace user3Workplace;
    private DreamerUserWorkplace user4Workplace;

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

        user1Workplace = new DreamerUserWorkplace(mtApp);
        user1Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user2Workplace = new DreamerUserWorkplace(mtApp);
        user2Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user3Workplace = new DreamerUserWorkplace(mtApp);
        user3Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);
        user4Workplace = new DreamerUserWorkplace(mtApp);
        user4Workplace.scale(keyboardScaleFactor, keyboardScaleFactor, keyboardScaleFactor, Vector3D.ZERO_VECTOR);

        user1Workplace.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - user1Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, 0));
        this.getCanvas().addChild(user1Workplace);

        user2Workplace.rotateZ(new Vector3D(user2Workplace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user2Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 180);
        user2Workplace.setPositionGlobal(new Vector3D(mtApp.width / 2, user2Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, 0));
        this.getCanvas().addChild(user2Workplace);

        user3Workplace.rotateZ(new Vector3D(user3Workplace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user3Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), 90);
        user3Workplace.setPositionGlobal(new Vector3D(user3Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 + 20, mtApp.height / 2, 0));
        this.getCanvas().addChild(user3Workplace);

        user4Workplace.rotateZ(new Vector3D(user4Workplace.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, user4Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2), -90);
        user4Workplace.setPositionGlobal(new Vector3D(mtApp.width - user4Workplace.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2 - 20, mtApp.height / 2, 0));
        this.getCanvas().addChild(user4Workplace);
    }

    @Override
    public void createEventListeners() {
        user1Workplace.getAddWorkspaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser1Activate(true);
                }
            }
        });
        user1Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user1Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user1Workplace.getCloseButton()));
        user1Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent ge) {
                TapAndHoldEvent te = (TapAndHoldEvent) ge;
                if (te.getId() == TapAndHoldEvent.GESTURE_ENDED) {
                    if (te.isHoldComplete()) {
                        controller.setUser1Activate(false);
                        controller.proceed();
                    }
                }
                return false;
            }
        });
        user1Workplace.getReadyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser1ReadyToContinue(true);
                    controller.proceed();
                }
            }
        });
        user1Workplace.getReadyButtonDone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser1ReadyToContinue(false);
                    controller.proceed();
                }
            }
        });
        user1Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user1Workplace.getSendButton().addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user1Workplace.getTextArea().getText());
                                user1Workplace.getTextArea().setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });

        user2Workplace.getAddWorkspaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser2Activate(true);
                }
            }
        });
        user2Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user2Workplace.getCloseButton()));
        user2Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent ge) {
                TapAndHoldEvent te = (TapAndHoldEvent) ge;
                if (te.getId() == TapAndHoldEvent.GESTURE_ENDED) {
                    if (te.isHoldComplete()) {
                        controller.setUser2Activate(false);
                        controller.proceed();
                    }
                }
                return false;
            }
        });
        user2Workplace.getReadyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser2ReadyToContinue(true);
                    controller.proceed();
                }
            }
        });
        user2Workplace.getReadyButtonDone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser2ReadyToContinue(false);
                    controller.proceed();
                }
            }
        });
        user2Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user2Workplace.getSendButton().addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user2Workplace.getTextArea().getText());
                                user2Workplace.getTextArea().setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });

        user3Workplace.getAddWorkspaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser3Activate(true);
                }
            }
        });
        user3Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user3Workplace.getCloseButton()));
        user3Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent ge) {
                TapAndHoldEvent te = (TapAndHoldEvent) ge;
                if (te.getId() == TapAndHoldEvent.GESTURE_ENDED) {
                    if (te.isHoldComplete()) {
                        controller.setUser3Activate(false);
                        controller.proceed();
                    }
                }
                return false;
            }
        });
        user3Workplace.getReadyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser3ReadyToContinue(true);
                    controller.proceed();
                }
            }
        });
        user3Workplace.getReadyButtonDone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser3ReadyToContinue(false);
                    controller.proceed();
                }
            }
        });
        user3Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user3Workplace.getSendButton().addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user3Workplace.getTextArea().getText());
                                user3Workplace.getTextArea().setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });

        user4Workplace.getAddWorkspaceButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser4Activate(true);
                }
            }
        });
        user4Workplace.getCloseButton().registerInputProcessor(new TapAndHoldProcessor(mtApp, 1000));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new TapAndHoldVisualizer(mtApp, user4Workplace.getCloseButton()));
        user4Workplace.getCloseButton().addGestureListener(TapAndHoldProcessor.class, new IGestureEventListener() {
            @Override
            public boolean processGestureEvent(MTGestureEvent ge) {
                TapAndHoldEvent te = (TapAndHoldEvent) ge;
                if (te.getId() == TapAndHoldEvent.GESTURE_ENDED) {
                    if (te.isHoldComplete()) {
                        controller.setUser4Activate(false);
                        controller.proceed();
                    }
                }
                return false;
            }
        });
        user4Workplace.getReadyButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser4ReadyToContinue(true);
                    controller.proceed();
                }
            }
        });
        user4Workplace.getReadyButtonDone().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getID() == TapEvent.BUTTON_DOWN) {
                    controller.setUser4ReadyToContinue(false);
                    controller.proceed();
                }
            }
        });
        user4Workplace.getSendButton().registerInputProcessor(new TapProcessor(mtApp));
        user4Workplace.getSendButton().addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            try {
                                controller.createIdea(user4Workplace.getTextArea().getText());
                                user4Workplace.getTextArea().setText("");
                            } catch (NoIdeaTextException ex) {
                                // do nothing
                            }
                        }
                        return false;
                    }
                });
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

        user1Workplace.setIsActive(AbstractMTWDSceneController.isUser1Activate());
        user2Workplace.setIsActive(AbstractMTWDSceneController.isUser2Activate());
        user3Workplace.setIsActive(AbstractMTWDSceneController.isUser3Activate());
        user4Workplace.setIsActive(AbstractMTWDSceneController.isUser4Activate());

        user1Workplace.setIsReady(controller.isUser1ReadyToContinue());
        user2Workplace.setIsReady(controller.isUser2ReadyToContinue());
        user3Workplace.setIsReady(controller.isUser3ReadyToContinue());
        user4Workplace.setIsReady(controller.isUser4ReadyToContinue());

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

        user1Workplace.setIsActive(AbstractMTWDSceneController.isUser1Activate());
        user2Workplace.setIsActive(AbstractMTWDSceneController.isUser2Activate());
        user3Workplace.setIsActive(AbstractMTWDSceneController.isUser3Activate());
        user4Workplace.setIsActive(AbstractMTWDSceneController.isUser4Activate());

        user1Workplace.setIsReady(controller.isUser1ReadyToContinue());
        user2Workplace.setIsReady(controller.isUser2ReadyToContinue());
        user3Workplace.setIsReady(controller.isUser3ReadyToContinue());
        user4Workplace.setIsReady(controller.isUser4ReadyToContinue());
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
}
