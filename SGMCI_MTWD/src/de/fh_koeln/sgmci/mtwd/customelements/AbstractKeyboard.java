package de.fh_koeln.sgmci.mtwd.customelements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.mt4j.components.TransformSpace;
import static org.mt4j.components.visibleComponents.shapes.AbstractShape.BOUNDS_ONLY_CHECK;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.components.visibleComponents.widgets.keyboard.ITextInputListener;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.animation.Animation;
import org.mt4j.util.animation.AnimationEvent;
import org.mt4j.util.animation.IAnimationListener;
import org.mt4j.util.animation.MultiPurposeInterpolator;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public abstract class AbstractKeyboard extends MTRoundRectangle {
    
    static final int MARGIN_TOP = 20;
    static final int MARGIN_BOTTOM = 20;
    static final float INNER_BUTTON_WIDTH = 42;
    static final float BUTTON_MARGIN = 4;
    static final float BUTTON_SIZE = INNER_BUTTON_WIDTH + 2 * BUTTON_MARGIN;
    static final float WIDE_BUTTON_WIDTH = 102 + 2 * BUTTON_MARGIN;
    static final float SCALE_FACTOR = 0.1f;
    static final float RETURN_WIDTH = 58 + 2 * BUTTON_MARGIN;
    static final int KEYBOARD_HEIGHT = 200 + MARGIN_TOP + MARGIN_BOTTOM;
    static final String SHIFT_ID = "shift";
    static final String SHIFT_PRESSED_ID = "shift_pressed";
    static final String LETTERS_ID = "letters";
    static final String NUMBERS_ID = "numbers";
    static final String SIGNS_ID = "signs";
    static final String BACKSPACE_ID = "backspace";
    final PApplet pApplet;
    final Map<MTSvgButton, KeyInfo> allKeys;
    final Map<MTSvgButton, KeyInfo> normalKeys;
    final Map<MTSvgButton, KeyInfo> letterKeys;
    final Map<MTSvgButton, KeyInfo> capitalLetterKeys;
    final Map<MTSvgButton, KeyInfo> numberKeys;
    final Map<MTSvgButton, KeyInfo> signKeys;
    final Collection<ITextInputListener> textInputAcceptors;
    boolean shiftPressed;
    KeyboardVisiblity keyboardVisiblity;
    
    public AbstractKeyboard(float x, float y, float z, float width, float height, float widthArc, float heightArc, PApplet pApplet) {
        super(x, y, z, width, height, widthArc, heightArc, pApplet);
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

        this.setBoundsBehaviour(BOUNDS_ONLY_CHECK);
    }
    
    void changeVisibility(){
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
    
    void closeKeyboard() {
        float width = this.getWidthXY(TransformSpace.RELATIVE_TO_PARENT);
        Animation keybCloseAnim = new Animation("Keyboard Fade", new MultiPurposeInterpolator(width, 1, 300, 0.2f, 0.5f, 1), this);
        keybCloseAnim.addAnimationListener(new IAnimationListener() {
            @Override
            public void processAnimationEvent(AnimationEvent ae) {
                switch (ae.getId()) {
                    case AnimationEvent.ANIMATION_STARTED:
                    case AnimationEvent.ANIMATION_UPDATED:
                        float currentVal = ae.getAnimation().getInterpolator().getCurrentValue();
                        setWidthRelativeToParent(currentVal);
                        break;
                    case AnimationEvent.ANIMATION_ENDED:
                        setVisible(false);
                        destroy();
                        break;
                    default:
                        break;
                }
            }
        });
        keybCloseAnim.start();
    }
    
    void createKeyButtonsOutOfKeyInfos(Collection<KeyInfo> keyInfos, MTRoundRectangle keyboard) {
        for (KeyInfo keyInfo : keyInfos) {
            MTSvgButton key = new MTSvgButton(keyInfo.fileName, pApplet);

            //Transform
            key.scale(SCALE_FACTOR, SCALE_FACTOR, 1, new Vector3D(0, 0, 0));
            key.translate(keyInfo.position);
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
                key.addActionListener(new ShiftButtonPressedListener());
            } else if (keyInfo.id.equals(SHIFT_PRESSED_ID)) {
                key.addActionListener(new ShiftPressedButtonPressedListener());
            } else if (keyInfo.id.equals(LETTERS_ID)) {
                key.addActionListener(new LettersButtonPressedListener());
            } else if (keyInfo.id.equals(NUMBERS_ID)) {
                key.addActionListener(new NumbersButtonPressedListener());
            } else if (keyInfo.id.equals(SIGNS_ID)) {
                key.addActionListener(new SignsButtonPressedListener());
            } else {
                key.addActionListener(new KeyButtonPressedListener());
            }

            keyboard.addChild(key);
        }
    }
    
    private boolean setWidthRelativeToParent(float width) {
        if (width > 0) {
            Vector3D centerPoint;
            if (this.hasBounds()) {
                centerPoint = this.getBounds().getCenterPointLocal();
                centerPoint.transform(this.getLocalMatrix());
            } else {
                centerPoint = this.getCenterPointGlobal();
                centerPoint.transform(this.getGlobalInverseMatrix());
            }
            this.scale(1 / this.getWidthXY(TransformSpace.RELATIVE_TO_PARENT), 1 / this.getWidthXY(TransformSpace.RELATIVE_TO_PARENT), 1, centerPoint);
            this.scale(width, width, 1, centerPoint);
            return true;
        } else {
            return false;
        }
    }
    
    public synchronized void addTextInputListener(ITextInputListener textListener) {
        if (!this.textInputAcceptors.contains(textListener)) {
            this.textInputAcceptors.add(textListener);
        }
    }

    public synchronized ITextInputListener[] getTextInputListeners() {
        return this.textInputAcceptors.toArray(new ITextInputListener[this.textInputAcceptors.size()]);
    }

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
    
    enum KeyboardVisiblity {

        LETTERS,
        LETTERS_WITH_SHIFT_PRESSED,
        NUMBERS,
        SIGNS;
    }

    enum KeyVisibility {

        NORMAL_KEY,
        LETTERS_IS_SELECTED,
        LETTERS_IS_SELECTED_AND_SHIFT_PRESSED,
        NUMBERS_IS_SELECTED,
        SIGNS_IS_SELECTED;
    }
    
    class KeyInfo {

        String id;
        String charUnicodeToWrite;
        String fileName;
        Vector3D position;
        KeyVisibility visibilityInfo;

        public KeyInfo(String id, String charUnicodeToWrite, String fileName,
                Vector3D position, KeyVisibility visibilityInfo) {
            this.id = id;
            this.charUnicodeToWrite = charUnicodeToWrite;
            this.fileName = fileName;
            this.position = position;
            this.visibilityInfo = visibilityInfo;
        }
    }

    class ShiftButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                shiftPressed = true;
                keyboardVisiblity = KeyboardVisiblity.LETTERS_WITH_SHIFT_PRESSED;
                changeVisibility();
            }
        }
    }

    class KeyButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                if (e.getSource() instanceof MTSvgButton) {
                    MTSvgButton key = (MTSvgButton) e.getSource();
                    KeyInfo value = allKeys.get(key);
                    if (value == null) {
                        return;
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
        }
    }

    class ShiftPressedButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                shiftPressed = false;
                keyboardVisiblity = KeyboardVisiblity.LETTERS;
                changeVisibility();
            }
        }
    }

    class LettersButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                if (shiftPressed) {
                    keyboardVisiblity = KeyboardVisiblity.LETTERS_WITH_SHIFT_PRESSED;
                } else {
                    keyboardVisiblity = KeyboardVisiblity.LETTERS;
                }
                changeVisibility();
            }
        }
    }

    class NumbersButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                keyboardVisiblity = KeyboardVisiblity.NUMBERS;
                changeVisibility();
            }
        }
    }

    class SignsButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                keyboardVisiblity = KeyboardVisiblity.SIGNS;
                changeVisibility();
            }
        }
    }
}
