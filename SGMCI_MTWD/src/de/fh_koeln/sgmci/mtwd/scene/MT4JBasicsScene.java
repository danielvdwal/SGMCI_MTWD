package de.fh_koeln.sgmci.mtwd.scene;

import org.mt4j.MTApplication;
import org.mt4j.components.visibleComponents.font.FontManager;
import org.mt4j.components.visibleComponents.font.IFont;
import org.mt4j.components.visibleComponents.shapes.MTEllipse;
import org.mt4j.components.visibleComponents.shapes.MTRectangle;
import org.mt4j.components.visibleComponents.widgets.MTTextField;
import org.mt4j.sceneManagement.AbstractScene;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PImage;

/**
 *
 * @author danielvanderwal
 */
public class MT4JBasicsScene extends AbstractScene {

    public MT4JBasicsScene(MTApplication mtApp, String name) {
        super(mtApp, name);
        this.setClearColor(new MTColor(200, 0, 255, 255));
        IFont font = FontManager.getInstance().createFont(mtApp, "arial.ttf", 20);

        MTTextField textField1 = new MTTextField(0, 0, 200, 40, font, mtApp);
        MTTextField textField2 = new MTTextField(50, 50, 200, 40, font, mtApp);
        MTRectangle rectangle1 = new MTRectangle(500, 300, 50, 100, mtApp);
        MTRectangle rectangle2 = new MTRectangle(100, 350, 100, 40, mtApp);
        MTEllipse circle = new MTEllipse(mtApp, new Vector3D(70, 90), 100, 100);
        PImage image = mtApp.createImage(300, 300, MTApplication.RGB);
        MTRectangle imageRect = new MTRectangle(image, mtApp);

        textField1.setText("Hello, World!");
        textField2.setText("Testing 1 2 3!");

        rectangle1.setFillColor(MTColor.RED);
        rectangle2.setFillColor(MTColor.BLUE);
        circle.setFillColor(new MTColor(0, 255, 0, 125));

        rectangle1.setStrokeColor(MTColor.YELLOW);
        rectangle2.setStrokeColor(MTColor.BLACK);

        rectangle1.setStrokeWeight(5);
        rectangle2.setStrokeWeight(5);

        rectangle1.setNoStroke(true);
        rectangle2.setNoFill(true);

        image.loadPixels();
        for (int y = 0; y < image.height; y++) {
            for (int x = 0; x < image.width; x++) {
                int pixelPos = x + y * image.width;
                image.pixels[pixelPos] = mtApp.color((int)(Math.random()*255), 
                        (int)(Math.random()*255),(int)(Math.random()*255));
            }
        }
        image.updatePixels();

        this.getCanvas().addChild(textField1);
        this.getCanvas().addChild(textField2);
        this.getCanvas().addChild(rectangle1);
        this.getCanvas().addChild(rectangle2);
        this.getCanvas().addChild(circle);
        this.getCanvas().addChild(imageRect);
    }

    @Override
    public void init() {
    }

    @Override
    public void shutDown() {
    }
}
