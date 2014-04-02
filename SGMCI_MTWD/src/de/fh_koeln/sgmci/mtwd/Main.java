package de.fh_koeln.sgmci.mtwd;

import de.fh_koeln.sgmci.mtwd.scene.HelloWorldScene;
import org.mt4j.MTApplication;

/**
 *
 * @author danielvanderwal
 */
public class Main extends MTApplication {
 
	public static void main(String[] args) {
		initialize();
	}
 
	@Override
	public void startUp() {
		addScene(new HelloWorldScene(this, "Hello World Scene"));
	}
 
}