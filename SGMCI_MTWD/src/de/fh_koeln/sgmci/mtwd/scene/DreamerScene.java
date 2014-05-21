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
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
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

        /*final MTTextArea user1CurrentTextArea = new MTTextArea(mtApp, ideaFont);
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

         user1Keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - height / 2 - 20, 0));
         */
        /*user2Keyboard = new SplitKeyboard(mtApp);
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
         user2Keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2, height / 2  + 20, 0));

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
         user3Keyboard.setPositionGlobal(new Vector3D(height / 2 + 20, mtApp.height / 2, 0));

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
         user4Keyboard.setPositionGlobal(new Vector3D(mtApp.width - height / 2 - 20, mtApp.height / 2, 0));

         user1Keyboard.setPickable(false);
         user1Keyboard.removeAllGestureEventListeners();
         user1Keyboard.unregisterAllInputProcessors();
         //this.getCanvas().addChild(user1Keyboard);
         */
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

        /*user2Keyboard.setPickable(false);
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

         user2Keyboard.setVisible(false);
         user3Keyboard.setVisible(false);
         user4Keyboard.setVisible(false);

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
         this.getCanvas().addChild(addUser4KeyboardButton);*/
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
                    if(te.isHoldComplete()) {
                        controller.setUser1Activate(false);
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
                    if(te.isHoldComplete()) {
                        controller.setUser2Activate(false);
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
                    if(te.isHoldComplete()) {
                        controller.setUser3Activate(false);
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
                    if(te.isHoldComplete()) {
                        controller.setUser4Activate(false);
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
