package de.fh_koeln.sgmci.mtwd.customelements;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.components.visibleComponents.widgets.keyboard.ITextInputListener;
import org.mt4j.input.inputProcessors.IGestureEventListener;
import org.mt4j.input.inputProcessors.MTGestureEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapProcessor;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 * This class is used as the super class for new, custom keyboard layouts.<br >
 * Since MTKeyboard's and MTTextKeyboard's layout methods are inaccessible, this
 * class is extending the MTRoundRectangle class to create a new keyboard
 * component, where its layout can be changed by extending this abstract
 * class.<br >
 * Since the layout has its origin from known mobile tablet devices, some
 * constants have been set, to ensure a similarity in the geometry of the
 * different keyboard layouts, such as the height of the keyboard, margin
 * between buttons, the factor to scale a button, etc..<br >
 * These constants can be ignored in case of a complete new implementation of a
 * keyboard layout.
 *
 * @author Daniel van der Wal
 * @version 0.3.0
 */
public abstract class AbstractKeyboard extends MTRoundRectangle {

    /**
     * The distance between the top of the keyboard and the top of the first row
     * of buttons.
     */
    static final int MARGIN_TOP = 20;
    /**
     * The distance between the bottom of the keyboard and the bottom of the
     * last row of buttons.
     */
    static final int MARGIN_BOTTOM = 20;
    /**
     * The arc value for the corners.
     */
    static final int CORNER_RADIUS = 30;
    /**
     * The size of a button itself.
     */
    static final float INNER_BUTTON_SIZE = 42;
    /**
     * The margin value for each side of a button.
     */
    static final float BUTTON_MARGIN = 4;
    /**
     * The computed total size of a button (includes the inner button size plus
     * 2 times the margin).
     */
    static final float BUTTON_SIZE = INNER_BUTTON_SIZE + 2 * BUTTON_MARGIN;
    /**
     * The computed width of a wider button, (e.g. shift, backspace, change to
     * numbers, etc., includes a fixed value plus 2 times the margin).
     */
    static final float WIDE_BUTTON_WIDTH = 102 + 2 * BUTTON_MARGIN;
    /**
     * The factor the scale the button due to the SVG file's size.
     */
    static final float SCALE_FACTOR = 0.1f;
    /**
     * The computed width of a return key (includes a fixed value plus 2 times
     * the margin).
     */
    static final float RETURN_WIDTH = 58 + 2 * BUTTON_MARGIN;
    /**
     * The computed height of the complete keyboard (includes the upper and
     * lower margin).
     */
    static final int KEYBOARD_HEIGHT = 200 + MARGIN_TOP + MARGIN_BOTTOM;
    /**
     * The id to identify the shift key, when it is NOT pressed.
     */
    static final String SHIFT_ID = "shift";
    /**
     * The id to identify the shift key, when it is pressed.
     */
    static final String SHIFT_PRESSED_ID = "shift_pressed";
    /**
     * The id to identify the "change to letters" key.
     */
    static final String LETTERS_ID = "letters";
    /**
     * The id to identify the "change to numbers" key.
     */
    static final String NUMBERS_ID = "numbers";
    /**
     * The id to identify the "change to signs" key.
     */
    static final String SIGNS_ID = "signs";
    /**
     * The id to identify the backspace key.
     */
    static final String BACKSPACE_ID = "backspace";
    /**
     * The PApplet this component is used in.
     */
    final PApplet pApplet;
    /**
     * A map to store all keys as MTSvgButtons and their KeyInfo (includes
     * geometric, id, SVG name information).
     */
    final Map<MTSvgButton, KeyInfo> allKeys;
    /**
     * A map to store only all keys, that don't change their geometry, as
     * MTSvgButtons and their KeyInfo (includes geometric, id, SVG name
     * information).
     */
    final Map<MTSvgButton, KeyInfo> normalKeys;
    /**
     * A map to store only all keys, that are only visible if the "change to
     * letters" key was pressed, as MTSvgButtons and their KeyInfo (includes
     * geometric, id, SVG name information).
     */
    final Map<MTSvgButton, KeyInfo> letterKeys;
    /**
     * A map to store only all keys, that are only visible if the "change to
     * letters" key and the shift key were pressed, as MTSvgButtons and their
     * KeyInfo (includes geometric, id, SVG name information).
     */
    final Map<MTSvgButton, KeyInfo> capitalLetterKeys;
    /**
     * A map to store only all keys, that are only visible if the "change to
     * numbers" key was pressed, as MTSvgButtons and their KeyInfo (includes
     * geometric, id, SVG name information).
     */
    final Map<MTSvgButton, KeyInfo> numberKeys;
    /**
     * A map to store only all keys, that are only visible if the "change to
     * signs" key was pressed, as MTSvgButtons and their KeyInfo (includes
     * geometric, id, SVG name information).
     */
    final Map<MTSvgButton, KeyInfo> signKeys;
    /**
     * The collection of all text input acceptors (the components that receive
     * the text input).
     */
    final Collection<ITextInputListener> textInputAcceptors;
    /**
     * A flag to specify if the shift key was pressed.
     */
    boolean shiftPressed;
    /**
     * The current keyboard visibility mode.<br >
     * It is used to specify which map of keys should be displayed.
     */
    KeyboardVisiblity keyboardVisiblity;

    /**
     * The constructor of the AbstractKeyboard class.<br >
     * It initializes all final fields.<br >
     * In the beginning the shift key is not pressed and the uncapitalized
     * letters are shown.
     *
     * @param x the x geometry of the keyboard
     * @param y the y geometry of the keyboard
     * @param z the z geometry of the keyboard
     * @param width the width of the keyboard
     * @param height the height of the keyboard (the constant value only is
     * provided, in case the constant should be used, its usage needs to be
     * declared explicitly)
     * @param widthArc the width of the arc used for the corners of the keyboard
     * @param heightArc the height of the arc used for the corners of the
     * keyboard
     * @param pApplet the PApplet this component is used in
     */
    public AbstractKeyboard(PApplet pApplet, float x, float y, float z, float width, float height, float widthArc, float heightArc) {
        super(pApplet, x, y, z, width, height, widthArc, heightArc);
        this.pApplet = pApplet;

        this.shiftPressed = false;
        this.keyboardVisiblity = KeyboardVisiblity.LETTERS;

        this.allKeys = new HashMap<MTSvgButton, KeyInfo>();
        this.normalKeys = new HashMap<MTSvgButton, KeyInfo>();
        this.letterKeys = new HashMap<MTSvgButton, KeyInfo>();
        this.capitalLetterKeys = new HashMap<MTSvgButton, KeyInfo>();
        this.numberKeys = new HashMap<MTSvgButton, KeyInfo>();
        this.signKeys = new HashMap<MTSvgButton, KeyInfo>();

        this.textInputAcceptors = new LinkedList<ITextInputListener>();

        this.setDrawSmooth(true);
        if (MT4jSettings.getInstance().isOpenGlMode()) {
            this.setUseDirectGL(true);
        }

        this.setDepthBufferDisabled(true);

        this.setBoundsBehaviour(AbstractShape.BOUNDS_ONLY_CHECK);
    }

    /**
     * Get the height of the keyboard.
     *
     * @return the height of the keyboard
     */
    public float getHeight() {
        return KEYBOARD_HEIGHT;
    }

    /**
     * Get the complete width of the keyboard.
     *
     * @return the complete width of the keyboard
     */
    public abstract float getWidth();

    /**
     * Changes the visibility of the buttons on the keyboard in regard to the
     * current keyboard visibility mode.
     */
    void changeVisibility() {
        switch (keyboardVisiblity) {
            case LETTERS:
                for (MTSvgButton key : letterKeys.keySet()) {
                    key.setVisible(true);
                }
                for (MTSvgButton key : capitalLetterKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : numberKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : signKeys.keySet()) {
                    key.setVisible(false);
                }
                break;
            case LETTERS_WITH_SHIFT_PRESSED:
                for (MTSvgButton key : letterKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : capitalLetterKeys.keySet()) {
                    key.setVisible(true);
                }
                for (MTSvgButton key : numberKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : signKeys.keySet()) {
                    key.setVisible(false);
                }
                break;
            case NUMBERS:
                for (MTSvgButton key : letterKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : capitalLetterKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : numberKeys.keySet()) {
                    key.setVisible(true);
                }
                for (MTSvgButton key : signKeys.keySet()) {
                    key.setVisible(false);
                }
                break;
            case SIGNS:
                for (MTSvgButton key : letterKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : capitalLetterKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : numberKeys.keySet()) {
                    key.setVisible(false);
                }
                for (MTSvgButton key : signKeys.keySet()) {
                    key.setVisible(true);
                }
                break;
        }
    }

    /**
     * Creates the MTSvgButton objects by using the given collection of key
     * information and adds them to the given container, as well as the
     * corresponding map.
     *
     * @param keyInfos the collection of key information to be used to create
     * the MTSvgButton objects
     * @param keyboard the component on which the keys should be displayed
     */
    void createKeyButtonsOutOfKeyInfos(Collection<KeyInfo> keyInfos, MTRoundRectangle keyboard) {
        for (KeyInfo keyInfo : keyInfos) {
            MTSvgButton key = new MTSvgButton(pApplet, keyInfo.svgFileName);

            //Transform
            key.scale(SCALE_FACTOR, SCALE_FACTOR, 1, new Vector3D(0, 0, 0));
            key.translate(keyInfo.geometry);
            key.translate(new Vector3D(BUTTON_MARGIN, BUTTON_MARGIN));

            switch (keyInfo.visibilityInfo) {
                case NORMAL_KEY:
                    normalKeys.put(key, keyInfo);
                    break;
                case LETTERS_IS_SELECTED:
                    letterKeys.put(key, keyInfo);
                    break;
                case LETTERS_IS_SELECTED_AND_SHIFT_PRESSED:
                    capitalLetterKeys.put(key, keyInfo);
                    break;
                case NUMBERS_IS_SELECTED:
                    numberKeys.put(key, keyInfo);
                    break;
                case SIGNS_IS_SELECTED:
                    signKeys.put(key, keyInfo);
                    break;
            }
            allKeys.put(key, keyInfo);

            if (keyInfo.id.equals(SHIFT_ID)) {
                key.addGestureListener(TapProcessor.class, new ShiftButtonPressedListener());
            } else if (keyInfo.id.equals(SHIFT_PRESSED_ID)) {
                key.addGestureListener(TapProcessor.class, new ShiftPressedButtonPressedListener());
            } else if (keyInfo.id.equals(LETTERS_ID)) {
                key.addGestureListener(TapProcessor.class, new LettersButtonPressedListener());
            } else if (keyInfo.id.equals(NUMBERS_ID)) {
                key.addGestureListener(TapProcessor.class, new NumbersButtonPressedListener());
            } else if (keyInfo.id.equals(SIGNS_ID)) {
                key.addGestureListener(TapProcessor.class, new SignsButtonPressedListener());
            } else {
                key.addGestureListener(TapProcessor.class, new KeyButtonPressedListener());
            }
            keyboard.addChild(key);
        }
    }

    /**
     * Adds an ITextInputListener object to the textListener collection.
     *
     * @param textListener the listener to be added
     */
    public synchronized void addTextInputListener(ITextInputListener textListener) {
        if (!this.textInputAcceptors.contains(textListener)) {
            this.textInputAcceptors.add(textListener);
        }
    }

    /**
     * Retrieve an array of all ITextInputListener objects in the textListener
     * collection.
     *
     * @return an array of all ITextInputListener objects
     */
    public synchronized ITextInputListener[] getTextInputListeners() {
        return this.textInputAcceptors.toArray(new ITextInputListener[this.textInputAcceptors.size()]);
    }

    /**
     * Removes the given ITextInputListener object from the textListener
     * collection.
     *
     * @param textListener the listener to be removed
     */
    public synchronized void removeTextInputListener(ITextInputListener textListener) {
        if (this.textInputAcceptors.contains(textListener)) {
            this.textInputAcceptors.remove(textListener);
        }
    }

    @Override
    protected void destroyComponent() {
        super.destroyComponent();
        allKeys.clear();
        normalKeys.clear();
        letterKeys.clear();
        capitalLetterKeys.clear();
        numberKeys.clear();
        signKeys.clear();
        textInputAcceptors.clear();
    }

    /**
     * This enum is used as a visibility mode for the keyboard.<br >
     * The mode is used to display a specific set of keys.
     */
    enum KeyboardVisiblity {

        /**
         * Show uncapitalized letters-layout.
         */
        LETTERS,
        /**
         * Show capitalized letters-layout.
         */
        LETTERS_WITH_SHIFT_PRESSED,
        /**
         * Show the numbers-layout.
         */
        NUMBERS,
        /**
         * Show the signs-layout.
         */
        SIGNS;
    }

    /**
     * This enum is used to specify in which case a key is visible.<br >
     * The values are used to add the keys to the specific sets of keys.
     */
    enum KeyVisibility {

        /**
         * The key is visible all the time and does not change its geometric
         * information.
         */
        NORMAL_KEY,
        /**
         * The key is only visible, if the uncapitalized letters-layout is
         * active.
         */
        LETTERS_IS_SELECTED,
        /**
         * The key is only visible, if the capitalized letters-layout is active.
         */
        LETTERS_IS_SELECTED_AND_SHIFT_PRESSED,
        /**
         * The key is only visible, if the numbers-layout is active.
         */
        NUMBERS_IS_SELECTED,
        /**
         * The key is only visible, if the signs-layout is active.
         */
        SIGNS_IS_SELECTED;
    }

    /**
     * This class is used to store id, character, SVG file, visibility and
     * geometry information of a key.<br >
     * The objects of this class are later used to create the MTSvgButtons.
     */
    class KeyInfo {

        /**
         * The unique id of the key.
         */
        String id;
        /**
         * The character to be written, once the button is pressed.
         */
        String charUnicodeToWrite;
        /**
         * The location and name of the SVG file.
         */
        String svgFileName;
        /**
         * The geometric information for the button (including x and y
         * positions, width and height).
         */
        Vector3D geometry;
        /**
         * The layout setup in which the button should be visible.
         */
        KeyVisibility visibilityInfo;

        /**
         * The constructor of the KeyInfo class.
         *
         * @param id the unique id of the key
         * @param charUnicodeToWrite the character(s) to be written, once the
         * button is pressed
         * @param svgFileName the location and name of the SVG file
         * @param geometry the geometric information for the button
         * @param visibilityInfo the layout setup in which the button should be
         * visible
         */
        public KeyInfo(String id, String charUnicodeToWrite, String svgFileName,
                Vector3D geometry, KeyVisibility visibilityInfo) {
            this.id = id;
            this.charUnicodeToWrite = charUnicodeToWrite;
            this.svgFileName = svgFileName;
            this.geometry = geometry;
            this.visibilityInfo = visibilityInfo;
        }
    }

    /**
     * This class is used to implement an ActionListener, that, once the shift
     * button has been pressed, performs the necessary actions that take place.
     */
    class ShiftButtonPressedListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_STARTED) {
                shiftPressed = true;
                keyboardVisiblity = KeyboardVisiblity.LETTERS_WITH_SHIFT_PRESSED;
                changeVisibility();
            }
            return false;
        }
    }

    /**
     * This class is used to implement an ActionListener, that, once a key has
     * been pressed, performs the necessary actions that take place.
     */
    class KeyButtonPressedListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_STARTED) {
                if (mtge.getTarget() instanceof MTSvgButton) {
                    MTSvgButton key = (MTSvgButton) mtge.getTarget();
                    KeyInfo value = allKeys.get(key);
                    if (value == null) {
                        return false;
                    } else if (value.id.equals(BACKSPACE_ID)) {
                        ITextInputListener[] listeners = getTextInputListeners();
                        for (ITextInputListener listener : listeners) {
                            listener.removeLastCharacter();
                        }
                    } else {
                        ITextInputListener[] listeners = getTextInputListeners();
                        for (ITextInputListener listener : listeners) {
                            listener.appendCharByUnicode(value.charUnicodeToWrite);
                        }
                    }
                    if (keyboardVisiblity == KeyboardVisiblity.LETTERS_WITH_SHIFT_PRESSED) {
                        keyboardVisiblity = KeyboardVisiblity.LETTERS;
                        changeVisibility();
                    }
                }
            }
            return false;
        }
    }

    /**
     * This class is used to implement an ActionListener, that, once the shift
     * button has been pressed, in case the button was already in the pressed
     * state, performs the necessary actions that take place.
     */
    class ShiftPressedButtonPressedListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_STARTED) {
                shiftPressed = false;
                keyboardVisiblity = KeyboardVisiblity.LETTERS;
                changeVisibility();
            }
            return false;
        }
    }

    /**
     * This class is used to implement an ActionListener, that, once the "change
     * to letters" button has been pressed, performs the necessary actions that
     * take place.
     */
    class LettersButtonPressedListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_STARTED) {
                if (shiftPressed) {
                    keyboardVisiblity = KeyboardVisiblity.LETTERS_WITH_SHIFT_PRESSED;
                } else {
                    keyboardVisiblity = KeyboardVisiblity.LETTERS;
                }
                changeVisibility();
            }
            return false;
        }
    }

    /**
     * This class is used to implement an ActionListener, that, once the "change
     * to numbers" button has been pressed, performs the necessary actions that
     * take place.
     */
    class NumbersButtonPressedListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_STARTED) {
                keyboardVisiblity = KeyboardVisiblity.NUMBERS;
                changeVisibility();
            }
            return false;
        }
    }

    /**
     * This class is used to implement an ActionListener, that, once the "change
     * to signs" button has been pressed, performs the necessary actions that
     * take place.
     */
    class SignsButtonPressedListener implements IGestureEventListener {

        @Override
        public boolean processGestureEvent(MTGestureEvent mtge) {
            if (mtge.getId() == TapEvent.GESTURE_STARTED) {
                keyboardVisiblity = KeyboardVisiblity.SIGNS;
                changeVisibility();
            }
            return false;
        }
    }
}
