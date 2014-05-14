package de.fh_koeln.sgmci.mtwd.scene;

import de.fh_koeln.sgmci.mtwd.controller.DreamerSceneController;
import de.fh_koeln.sgmci.mtwd.customelements.SplitKeyboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTPolygon;
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
import org.mt4j.util.math.Vertex;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth, Daniel van der Wal
 * @version 0.1.0
 */
public class DreamerScene extends AbstractMTWDScene {
    
    private final DreamerSceneController controller;
    private MTTextArea problemTextArea;
    private MTTextArea problemTextAreaInverted;
    
    private MTSvgButton startButton;
    
    public DreamerScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        controller = new DreamerSceneController(this);
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
        final IFont ideaFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);
        
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
        
        MTPolygon polygon = new MTPolygon(mtApp, new Vertex[]{
            new Vertex(0, mtApp.getHeight(), 0, 1),
            new Vertex(250, mtApp.getHeight() - 200, 0, 1),
            new Vertex(mtApp.getWidth() - 250, mtApp.getHeight() - 200, 0, 1),
            new Vertex(mtApp.getWidth(), mtApp.getHeight(), 0, 1)});
        polygon.setFillColor(new MTColor(255, 255, 255, 64));
        polygon.setStrokeColor(MTColor.BLACK);
        getCanvas().addChild(polygon);
        
        MTPolygon polygon2 = new MTPolygon(mtApp, new Vertex[]{
            new Vertex(0, 0, 0, 1),
            new Vertex(250, 200, 0, 1),
            new Vertex(mtApp.getWidth() - 250, 200, 0, 1),
            new Vertex(mtApp.getWidth(), 0, 0, 1)});
        polygon2.setFillColor(new MTColor(0, 0, 0, 64));
        polygon2.setStrokeColor(MTColor.BLACK);
        getCanvas().addChild(polygon2);
        
        MTPolygon polygon3 = new MTPolygon(mtApp, new Vertex[]{
            new Vertex(0, 0, 0, 1),
            new Vertex(250, 200, 0, 1),
            new Vertex(250, mtApp.getHeight() - 200, 0, 1),
            new Vertex(0, mtApp.getHeight(), 0, 1)});
        polygon3.setFillColor(new MTColor(0, 0, 0, 64));
        polygon3.setStrokeColor(MTColor.BLACK);
        getCanvas().addChild(polygon3);
        
        MTPolygon polygon4 = new MTPolygon(mtApp, new Vertex[]{
            new Vertex(mtApp.getWidth(), 0, 0, 1),
            new Vertex(mtApp.getWidth() - 250, 200, 0, 1),
            new Vertex(mtApp.getWidth() - 250, mtApp.getHeight() - 200, 0, 1),
            new Vertex(mtApp.getWidth(), mtApp.getHeight(), 0, 1)});
        polygon4.setFillColor(new MTColor(255, 255, 255, 64));
        polygon4.setStrokeColor(MTColor.BLACK);
        getCanvas().addChild(polygon4);
        
        for (int i = 0; i < 4; i++) {
            final SplitKeyboard keyboard = new SplitKeyboard(mtApp);
            float width = keyboard.getWidth();
            float height = keyboard.getHeight();
            float ratio = (mtApp.getWidth() * 0.5f) / width;
            keyboard.setSpaceBetweenKeyboards(200);
            keyboard.scale(ratio, ratio, ratio, Vector3D.ZERO_VECTOR);
            
            width = width * ratio;
            height = height * ratio;
            
            final MTTextArea currentTextArea = new MTTextArea(mtApp, ideaFont);
            currentTextArea.setExpandDirection(MTTextArea.ExpandDirection.UP);
            currentTextArea.setStrokeColor(new MTColor(0, 0, 0, 255));
            currentTextArea.setFillColor(new MTColor(205, 200, 177, 255));
            currentTextArea.setEnableCaret(true);
            keyboard.getLeftKeyboard().addChild(currentTextArea);
            currentTextArea.setPositionRelativeToParent(new Vector3D(40, -currentTextArea.getHeightXY(TransformSpace.LOCAL) * 0.5f));
            keyboard.addTextInputListener(currentTextArea);
            
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
                                newTextArea.setText(currentTextArea.getText());
                                getCanvas().addChild(newTextArea);
                                
                                currentTextArea.setText("");
                            }
                            return false;
                        }
                    });
            keyboard.addChild(rectangle);
            
            switch (i) {
                case 0:
                    keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height - height / 2, 0));
                    break;
                case 1:
                    keyboard.rotateZ(new Vector3D(width / 2, height / 2), 90);
                    keyboard.setPositionGlobal(new Vector3D(height / 2, mtApp.height / 2, 0));
                    break;
                case 2:
                    keyboard.rotateZ(new Vector3D(width / 2, height / 2), 180);
                    keyboard.setPositionGlobal(new Vector3D(mtApp.width / 2, height / 2, 0));
                    break;
                case 3:
                    keyboard.rotateZ(new Vector3D(width / 2, height / 2), -90);
                    keyboard.setPositionGlobal(new Vector3D(mtApp.width - height / 2, mtApp.height / 2, 0));
                    break;
            }
            
            keyboard.setPickable(false);
            keyboard.removeAllGestureEventListeners();
            keyboard.unregisterAllInputProcessors();
            this.getCanvas().addChild(keyboard);
        }
        
        startButton = new MTSvgButton("data/button_start.svg", mtApp);
        startButton.scale(0.2f, 0.2f, 0.2f, Vector3D.ZERO_VECTOR);
        getCanvas().addChild(startButton);
        startButton.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
    }
    
    @Override
    public void updateScene() {
        problemTextArea.setText(controller.getCurrentProblemDescription());
        problemTextArea.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextArea.translate(new Vector3D(0, problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
        
        problemTextAreaInverted.setText(controller.getCurrentProblemDescription());
        problemTextAreaInverted.setPositionGlobal(new Vector3D(mtApp.width / 2, mtApp.height / 2, 0));
        problemTextAreaInverted.translate(new Vector3D(0, -problemTextArea.getHeightXY(TransformSpace.LOCAL) / 2));
    }
    
    @Override
    public void init() {
        mtApp.registerKeyEvent(this);
    }
    
    @Override
    public void shutDown() {
        mtApp.unregisterKeyEvent(this);
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
