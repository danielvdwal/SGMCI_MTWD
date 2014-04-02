package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

/**
 *
 * @author danielvanderwal
 */
public class HelloWorldScene extends AbstractScene {
 
	public HelloWorldScene(MTApplication mtApplication, String name) {
		super(mtApplication, name);
 
		//Disable frustum culling for this scene - optional
		this.getCanvas().setFrustumCulling(false);
		//Set the background color
		this.setClearColor(new MTColor(146, 150, 188, 255));
 
		//Create a textfield
		MTTextArea textField = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApplication, "arial.ttf", 
				50, 				  //Font size
				new MTColor(255, 255, 255, 255),   //Font fill color
				new MTColor(255, 255, 255, 255))); //Font outline color
		textField.setNoFill(true);
		textField.setNoStroke(true);
		textField.setText("Hello World!");
		//Add the textfield to our canvas
		this.getCanvas().addChild(textField);
 
		//Center the textfield on the screen
		textField.setPositionGlobal(new Vector3D(mtApplication.width/2f, mtApplication.height/2f));
	}
 
        @Override
	public void init() { }
 
        @Override
	public void shutDown() {}
}
