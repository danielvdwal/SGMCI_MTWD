package de.fh_koeln.sgmci.mtwd;

import de.fh_koeln.sgmci.mtwd.example.HelloWorldScene;
import de.fh_koeln.sgmci.mtwd.example.MT4JBasicsScene;
import de.fh_koeln.sgmci.mtwd.example.MT4JGestruresScene;
import de.fh_koeln.sgmci.mtwd.example.PianoScene;
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
        //addScene(new PianoScene(this, "Piano Scene"));
        //addScene(new MT4JBasicsScene(this, "MT4J Basics"));
        //addScene(new MT4JGestruresScene(this, "MT4J Gestures"));
    }
}