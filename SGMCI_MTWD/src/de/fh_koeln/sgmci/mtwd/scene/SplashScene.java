package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.widgets.MTBackgroundImage;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.util.MT4jSettings;
import processing.core.PImage;

/**
 *
 * @author Robert Scherbarth
 * @version 0.1.0
 */
public class SplashScene extends AbstractMTWDScene{

    private MTTextArea descriptionTextArea;
    private MTTextArea personsTextArea;
    private MTTextArea profTextArea;
    
    public SplashScene(MTApplication mtApp, String name) {
        super(mtApp, name);
    }
    
    @Override
    public void createBackground() {
        PImage backgroundImage = mtApp.loadImage("data/startBackground.png");
        backgroundImage.resize(MT4jSettings.getInstance().windowWidth, MT4jSettings.getInstance().windowHeight);
        getCanvas().addChild(new MTBackgroundImage(mtApp, backgroundImage, true));
    }
    
    @Override
    public void createComponents() {
        
        final IFont descriptionFont = FontManager.getInstance().createFont(mtApp, "arial.ttf", 18);
        
        descriptionTextArea = new MTTextArea(mtApp, descriptionFont);
        descriptionTextArea.setNoFill(true);
        descriptionTextArea.setNoStroke(true);
        descriptionTextArea.setPickable(false);
        descriptionTextArea.setText("Beschreibung");
        
    }

}
