package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.RealistCommentingSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.AbstractKeyboard;
import de.fh_koeln.sgmci.mtwd.customelements.Keyboard;
import de.fh_koeln.sgmci.mtwd.model.Idea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTList;
import org.mt4j.components.visibleComponents.widgets.MTListCell;
import org.mt4j.components.visibleComponents.widgets.MTSvg;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.2.0
 */
public class RealistCommentingScene extends AbstractMTWDScene {

    private final RealistCommentingSceneController controller;
    private MTTextArea problemTextArea;
    private MTTextArea ideaTextArea;
    private MTTextArea problemNorthTextArea;
    private MTTextArea ideaNorthTextArea;
    private MTTextArea problemWestTextArea;
    private MTTextArea ideaWestTextArea;
    private MTTextArea problemEastTextArea;
    private MTTextArea ideaEastTextArea;
    private MTList commentList;
    private MTListCell commentListCell;
    private MTSvgButton helpButton;
    private MTSvgButton settingsButton;
    private MTSvgButton startButton;
    private Idea[] ideas;
    private int ideaIndex = 1;

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
        final IFont problemFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 30);
        final IFont ideaFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 40);
        final IFont commentFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);

        problemTextArea = new MTTextArea(mtApp, problemFont);
        problemTextArea.setNoFill(true);
        problemTextArea.setNoStroke(true);
        problemTextArea.setPickable(false); 
        problemNorthTextArea = new MTTextArea(mtApp, problemFont);
        problemNorthTextArea.setNoFill(true);
        problemNorthTextArea.setNoStroke(true);
        problemNorthTextArea.setPickable(false);
        problemNorthTextArea.rotateZ(Vector3D.ZERO_VECTOR, 180);
        problemWestTextArea = new MTTextArea(mtApp, problemFont);
        problemWestTextArea.setNoFill(true);
        problemWestTextArea.setNoStroke(true);
        problemWestTextArea.setPickable(false);
        problemWestTextArea.rotateZ(Vector3D.ZERO_VECTOR, 90);
        problemEastTextArea = new MTTextArea(mtApp, problemFont);
        problemEastTextArea.setNoFill(true);
        problemEastTextArea.setNoStroke(true);
        problemEastTextArea.setPickable(false);
        problemEastTextArea.rotateZ(Vector3D.ZERO_VECTOR, 270);

        ideaTextArea = new MTTextArea(mtApp, ideaFont);
        ideaTextArea.setNoFill(true);
        ideaTextArea.setNoStroke(true);
        ideaTextArea.setPickable(false);
        ideaNorthTextArea = new MTTextArea(mtApp, ideaFont);
        ideaNorthTextArea.setNoFill(true);
        ideaNorthTextArea.setNoStroke(true);
        ideaNorthTextArea.setPickable(false);
        ideaNorthTextArea.rotateZ(Vector3D.ZERO_VECTOR, 180);
        ideaWestTextArea = new MTTextArea(mtApp, ideaFont);
        ideaWestTextArea.setNoFill(true);
        ideaWestTextArea.setNoStroke(true);
        ideaWestTextArea.setPickable(false);
        ideaWestTextArea.rotateZ(Vector3D.ZERO_VECTOR, 90);
        ideaEastTextArea = new MTTextArea(mtApp, ideaFont);
        ideaEastTextArea.setNoFill(true);
        ideaEastTextArea.setNoStroke(true);
        ideaEastTextArea.setPickable(false);
        ideaEastTextArea.rotateZ(Vector3D.ZERO_VECTOR, 270);

        getCanvas().addChild(problemTextArea);
        getCanvas().addChild(problemNorthTextArea);
        getCanvas().addChild(problemWestTextArea);
        getCanvas().addChild(problemEastTextArea);
        getCanvas().addChild(ideaTextArea);
        getCanvas().addChild(ideaNorthTextArea);
        getCanvas().addChild(ideaWestTextArea);
        getCanvas().addChild(ideaEastTextArea);

        final AbstractKeyboard keyboard = new Keyboard(mtApp);
        float ratio = (mtApp.getWidth() * 0.5f) / keyboard.getWidthXY(TransformSpace.LOCAL);
        keyboard.scale(ratio, ratio, ratio, Vector3D.ZERO_VECTOR);
        keyboard.setPositionRelativeToParent(new Vector3D(mtApp.width / 2, mtApp.height - keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT) / 2));

        getCanvas().addChild(keyboard);

        final MTTextArea commentTextArea = new MTTextArea(mtApp, commentFont);
        commentTextArea.setFillColor(MTColor.YELLOW);
        commentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
        commentTextArea.unregisterAllInputProcessors();
        commentTextArea.setEnableCaret(true);
        keyboard.addChild(commentTextArea);
        commentTextArea.setPositionRelativeToParent(new Vector3D(40, -commentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));

        keyboard.addTextInputListener(commentTextArea);

        final MTRectangle rectangle = new MTRectangle(-100, 0, 30, 30, mtApp);
        rectangle.translate(new Vector3D(0, 15, 0));
        rectangle.registerInputProcessor(new TapProcessor(mtApp));
        rectangle.addGestureListener(TapProcessor.class,
                new IGestureEventListener() {
                    @Override
                    public boolean processGestureEvent(MTGestureEvent ge) {
                        TapEvent te = (TapEvent) ge;
                        if (te.getId() == TapEvent.GESTURE_DETECTED) {
                            final MTTextArea newTextArea = new MTTextArea(mtApp, ideaFont);
                            newTextArea.setText(commentTextArea.getText());
                            getCanvas().addChild(newTextArea);

                            commentTextArea.setText("");
                        }
                        return false;
                    }
                });
        keyboard.addChild(rectangle);

        commentList = new MTList(0, 0, keyboard.getWidthXY(TransformSpace.RELATIVE_TO_PARENT), 150, mtApp);
        commentListCell = new MTListCell(keyboard.getWidthXY(TransformSpace.RELATIVE_TO_PARENT), 150, mtApp);
        commentList.addListElement(commentListCell);
        commentList.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        commentList.translate(new Vector3D(0, 50));

        getCanvas().addChild(commentList);

        helpButton = new MTSvgButton("data/button_help.svg", mtApp);
        helpButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        helpButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 - keyboard.getWidthXY(TransformSpace.LOCAL) * ratio / 2 - 60, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * ratio / 2));

        startButton = new MTSvgButton("data/button_start.svg", mtApp);
        startButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        startButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 + keyboard.getWidthXY(TransformSpace.LOCAL) * ratio / 2 + 120, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * ratio / 2));

        settingsButton = new MTSvgButton("data/button_settings.svg", mtApp);
        settingsButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        settingsButton.setPositionRelativeToParent(new Vector3D(mtApp.getWidth() / 2 - keyboard.getWidthXY(TransformSpace.LOCAL) * ratio / 2 - 180, mtApp.getHeight() - keyboard.getHeightXY(TransformSpace.LOCAL) * ratio / 2));

        getCanvas().addChild(helpButton);
        getCanvas().addChild(startButton);
        getCanvas().addChild(settingsButton);
    }

    @Override
    public void createEventListeners() {
        // displays where the screen is touched
        this.registerGlobalInputProcessor(new CursorTracer(mtApp, this));

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        final MTTextArea textarea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
                        textarea.setText("Problem?");
                        textarea.setNoStroke(true);
                        textarea.setNoFill(true);
                        textarea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));

                        final MTTextArea helpPop = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 25, MTColor.BLUE, MTColor.BLUE));
                        helpPop.setText("Such bei Google nach Hilfe -.-");
                        helpPop.setPositionRelativeToOther(helpButton, new Vector3D(helpPop.getWidthXY(TransformSpace.RELATIVE_TO_PARENT) / 2, -300));
                        helpPop.setPickable(false);
                        getCanvas().addChild(helpPop);

                        final MTSvg svgTest = new MTSvg(mtApp, "data/Zeichnung.svg");
                        getCanvas().addChild(svgTest);

                        mtApp.getCurrentScene().getCanvas().addChild(textarea);
                        break;
                    default:
                        break;
                }
            }
        });

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

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (ae.getID()) {
                    case TapEvent.BUTTON_CLICKED:
                        //wenn Button geklickt wurde
                        MTTextArea textarea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
                        textarea.setText("Loesung!!!");
                        textarea.setNoStroke(true);
                        textarea.setNoFill(true);
                        textarea.setPositionGlobal(new Vector3D(mtApp.width / 2f, mtApp.height / 2f));

                        mtApp.getCurrentScene().getCanvas().addChild(textarea);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void updateScene() {
        problemTextArea.setText(controller.getCurrentProblemDescription());
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextArea.translate(new Vector3D(0, -100));
        
        problemNorthTextArea.setText(controller.getCurrentProblemDescription());
        problemNorthTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, 100, 0));
        problemNorthTextArea.translate(new Vector3D(0, -problemNorthTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
        
        problemWestTextArea.setText(controller.getCurrentProblemDescription());
        problemWestTextArea.setPositionGlobal(new Vector3D(100, mtApp.height / 2, 0));
        problemWestTextArea.translate(new Vector3D(0, -problemWestTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
        
        problemEastTextArea.setText(controller.getCurrentProblemDescription());
        problemEastTextArea.setPositionGlobal(new Vector3D(mtApp.width - 100, mtApp.height / 2, 0));
        problemEastTextArea.translate(new Vector3D(0, -problemEastTextArea.getHeightXY(TransformSpace.LOCAL) / 2));

        ideaTextArea.setText(controller.getCurrentlySelectedIdeaForCurrentProblem().getDescription());
        ideaTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        ideaTextArea.translate(new Vector3D(0, ideaTextArea.getHeightXY(TransformSpace.LOCAL) - 100));
        
        ideaNorthTextArea.setText(controller.getCurrentlySelectedIdeaForCurrentProblem().getDescription());
        ideaNorthTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, 50, 0));
        ideaNorthTextArea.translate(new Vector3D(0, -ideaNorthTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
        
        ideaWestTextArea.setText(controller.getCurrentlySelectedIdeaForCurrentProblem().getDescription());
        ideaWestTextArea.setPositionGlobal(new Vector3D(50, mtApp.height / 2, 0));
        ideaWestTextArea.translate(new Vector3D(0, -ideaWestTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
        
        ideaEastTextArea.setText(controller.getCurrentlySelectedIdeaForCurrentProblem().getDescription());
        ideaEastTextArea.setPositionGlobal(new Vector3D(mtApp.width - 50, mtApp.height / 2, 0));
        ideaEastTextArea.translate(new Vector3D(0, -ideaEastTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
    }
}
