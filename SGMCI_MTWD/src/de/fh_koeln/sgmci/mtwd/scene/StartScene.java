package de.fh_koeln.sgmci.mtwd.scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.mt4j.MTApplication;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.widgets.MTTextArea;
import org.mt4j.components.visibleComponents.widgets.buttons.MTImageButton;
import org.mt4j.components.visibleComponents.widgets.keyboard.MTKeyboard;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.globalProcessors.CursorTracer;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.sceneManagement.Iscene;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;

import processing.core.PImage;

/**
 *
 * @author Nadim Khan, Ramon Victor - Fachhochschule Köln Campus Gummersbach 2014
 * 
 * Diese Klasse repräsentiert den Startbildschirm.
 * 
 */
public class StartScene extends AbstractScene implements IScene{

	private MTApplication mtApp;
	private Iscene dreamScene;

	public StartScene(final MTApplication mtApplication, String name) {
		super(mtApplication, name);
		this.mtApp = mtApplication;

		//Zeigt die Touch-Stellen auf dem Bildschirm an.
		this.registerGlobalInputProcessor(new CursorTracer(mtApplication, this));

		this.getCanvas().setFrustumCulling(false);
		this.setClearColor(new MTColor(146, 150, 188, 255));

		//Erstellt das Texfeld sowie das zugehörige Inputfeld.
		final MTTextArea textField = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApplication, "arial.ttf", 50,
				new MTColor(255, 255, 255, 255),
				new MTColor(255, 255, 255, 255)));
		textField.setNoFill(true);
		textField.setNoStroke(true);
		textField.setText("Bitte geben Sie Ihr Problem ein:");

		final MTTextArea textInput = new MTTextArea(mtApplication, FontManager.getInstance().createFont(mtApplication, "arial.ttf", 50));
		textInput.setNoFill(true);

		//Stellt die korrekte Position des Inputfeldes sicher.
		new Thread() {
			@Override
			public void run() {
				while(true) {
					textInput.setPositionRelativeToOther(textField, new Vector3D(textField.getWidthXY(TransformSpace.RELATIVE_TO_PARENT)/2, 100+textInput.getHeightXY(TransformSpace.RELATIVE_TO_PARENT)/2));
					try {
						sleep(50);
					} catch (InterruptedException ex) {
						Logger.getLogger(StartScene.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
			}
		}.start();

		MTKeyboard keyboard = new MTKeyboard(mtApplication);
		keyboard.addTextInputListener(textInput);

		//Fügt die Komponenten dem Canvas hinzu.
		this.getCanvas().addChild(textField);
		this.getCanvas().addChild(keyboard);
		this.getCanvas().addChild(textInput);

		textField.setPositionGlobal(new Vector3D(mtApplication.width/2f, mtApplication.height/2-150));
		textInput.setEnableCaret(true);
		textInput.setPickable(false);
		textField.setPickable(false);
		keyboard.setPositionRelativeToParent(new Vector3D(mtApplication.width/2, mtApplication.height-keyboard.getHeightXY(TransformSpace.RELATIVE_TO_PARENT)/2));

		PImage helpImage = null;
		PImage startImage = null;
		PImage settingsImage = null;
		try {
			helpImage = mtApplication.loadImage("/data/helpButton.png");
			startImage = mtApplication.loadImage("/data/startButton.png");
			settingsImage = mtApplication.loadImage("/data/settingsButton.png");
		} catch (Exception e) {
			System.out.println("Error: Bilder konnte nicht geladen werden!");;
		}
		
		MTImageButton helpButton = new MTImageButton(helpImage, mtApplication);
		helpButton.setNoStroke(true);
		helpButton.setDrawSmooth(true);

		if (MT4jSettings.getInstance().isOpenGlMode())
			helpButton.setUseDirectGL(true);

		helpButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				switch (ae.getID()) {
				case TapEvent.BUTTON_CLICKED:
					//wenn Button geklickt wurde
					MTTextArea textarea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
					textarea.setText("Problem?");
					textarea.setNoStroke(true);
					textarea.setNoFill(true);
					textarea.setPositionGlobal(new Vector3D(mtApp.width/2f, mtApp.height/2f));

					mtApp.getCurrentScene().getCanvas().addChild(textarea);
					break;
				default:
					break;
				}
			}
		});

		helpButton.setPositionRelativeToParent(new Vector3D(mtApplication.getWidth()/6, mtApplication.getHeight() - mtApplication.getHeight()/14));
		helpButton.setSizeXYGlobal(mtApplication.getWidth()/16, mtApplication.getHeight()/9);

		this.getCanvas().addChild(helpButton);

		
		MTImageButton settingsButton = new MTImageButton(settingsImage, mtApplication);
		settingsButton.setNoStroke(true);
		settingsButton.setDrawSmooth(true);

		if (MT4jSettings.getInstance().isOpenGlMode())
			settingsButton.setUseDirectGL(true);

		settingsButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				switch (ae.getID()) {
				case TapEvent.BUTTON_CLICKED:
					//wenn Button geklickt wurde
					MTTextArea textarea = new MTTextArea(mtApp, FontManager.getInstance().createFont(mtApp, "arial.ttf", 50, MTColor.BLUE, MTColor.BLUE));
					textarea.setText("Loesung!!!");
					textarea.setNoStroke(true);
					textarea.setNoFill(true);
					textarea.setPositionGlobal(new Vector3D(mtApp.width/2f, mtApp.height/2f));

					mtApp.getCurrentScene().getCanvas().addChild(textarea);
					break;
				default:
					break;
				}
			}
		});

		settingsButton.setPositionRelativeToParent(new Vector3D(mtApplication.getWidth()/12, mtApplication.getHeight() - mtApplication.getHeight()/14));
		settingsButton.setSizeXYGlobal(mtApplication.getWidth()/16, mtApplication.getHeight()/9);

		this.getCanvas().addChild(settingsButton);

		
		MTImageButton startButton = new MTImageButton(startImage, mtApplication);
		startButton.setNoStroke(true);
		startButton.setDrawSmooth(true);

		if (MT4jSettings.getInstance().isOpenGlMode())
			startButton.setUseDirectGL(true);

		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ae) {
				switch (ae.getID()) {
				case TapEvent.BUTTON_CLICKED:
					//wenn Button geklickt wurde
					//Save the current scene on the scene stack before changing
					mtApp.pushScene();
					if (dreamScene == null){
						dreamScene = new DreamScene(mtApp, "Scene 2");
						//Add the scene to the mt application
						mtApp.addScene(dreamScene);
					}
					//Do the scene change
					mtApp.changeScene(dreamScene);
					break;
				default:
					break;
				}
			}
		});

		startButton.setPositionRelativeToParent(new Vector3D(mtApplication.getWidth()/1.2f, mtApplication.getHeight() - mtApplication.getHeight()/14));
		startButton.setSizeXYGlobal(mtApplication.getWidth()/16, mtApplication.getHeight()/9);

		this.getCanvas().addChild(startButton);
	}

	@Override
	public void init() { }

	@Override
	public void shutDown() {}
}
