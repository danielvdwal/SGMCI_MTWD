package de.fh_koeln.sgmci.mtwd.customelements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import org.mt4j.components.TransformSpace;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.components.visibleComponents.widgets.keyboard.ITextInputListener;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.animation.Animation;
import org.mt4j.util.animation.AnimationEvent;
import org.mt4j.util.animation.IAnimationListener;
import org.mt4j.util.animation.MultiPurposeInterpolator;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 *
 * @author danielvanderwal
 */
public final class Keyboard extends MTRoundRectangle {

    private static final int MARGIN_LEFT = 20;
    private static final int MARGIN_RIGHT = 20;
    private static final int MARGIN_TOP = 20;
    private static final int MARGIN_BOTTOM = 20;
    private static final float INNER_BUTTON_WIDTH = 42;
    private static final float BUTTON_MARGIN = 4;
    private static final float BUTTON_SIZE = INNER_BUTTON_WIDTH + 2 * BUTTON_MARGIN;
    private static final float BUTTON_MARGIN_NUMBERS_AND_SIGNS = 5.5f;
    private static final float WIDE_BUTTON_WIDTH = 102 + 2 * BUTTON_MARGIN;
    private static final float RETURN_WIDTH = 58 + 2 * BUTTON_MARGIN;
    private static final float BACKSPACE_AND_RIGHT_SHIFT_WIDTH = 74 + 2 * BUTTON_MARGIN;
    private static final float SCALE_FACTOR = 0.1f;
    private static final int CORNER_RADIUS = 30;
    private static final int KEYBOARD_WIDTH = (int) (34.0 / 3 * BUTTON_SIZE + RETURN_WIDTH) + MARGIN_LEFT + MARGIN_RIGHT;
    private static final int KEYBOARD_HEIGHT = 200 + MARGIN_TOP + MARGIN_BOTTOM;
    private static final String SHIFT_ID = "shift";
    private static final String SHIFT_PRESSED_ID = "shift_pressed";
    private static final String LETTERS_ID = "letters";
    private static final String NUMBERS_ID = "numbers";
    private static final String SIGNS_ID = "signs";
    private static final String BACKSPACE_ID = "backspace";
    private final PApplet pApplet;
    private final Map<MTSvgButton, KeyInfo> allKeys;
    private final Map<MTSvgButton, KeyInfo> normalKeys;
    private final Map<MTSvgButton, KeyInfo> letterKeys;
    private final Map<MTSvgButton, KeyInfo> capitalLetterKeys;
    private final Map<MTSvgButton, KeyInfo> numberKeys;
    private final Map<MTSvgButton, KeyInfo> signKeys;
    private final Collection<ITextInputListener> textInputAcceptors;
    private boolean shiftPressed;
    private KeyboardVisiblity keyboardVisiblity;

    public Keyboard(PApplet pApplet) {
        super(0, 0, 0, KEYBOARD_WIDTH, KEYBOARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS, pApplet);

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

        this.setFillColor(MTColor.BLACK);

        this.createKeyboard();

        this.setDepthBufferDisabled(true);
        
        this.setBoundsBehaviour(BOUNDS_ONLY_CHECK);
    }

    private void createKeyboard() {
        Collection<KeyInfo> keyInfos = new LinkedList<KeyInfo>();

        keyInfos.addAll(createKeyboardLetterKeyInfos());
        keyInfos.addAll(createKeyboardShiftedLetterKeyInfos());
        keyInfos.addAll(createKeyboardNumberKeyInfos());
        keyInfos.addAll(createKeyboardSignKeyInfos());
        keyInfos.addAll(createKeyboardNormalKeyInfos());

        createKeyButtonsOutOfKeyInfos(keyInfos);

        // 64px * 64px 
        MTSvgButton keybCloseSvg = new MTSvgButton(MT4jSettings.getInstance().getDefaultSVGPath() + "keybClose.svg", pApplet);
        keybCloseSvg.scale(0.8f, 0.8f, 1, new Vector3D(0, 0, 0));
        keybCloseSvg.translate(new Vector3D(KEYBOARD_WIDTH - BUTTON_SIZE - BUTTON_MARGIN, BUTTON_MARGIN, 0));
        keybCloseSvg.translate(new Vector3D(BUTTON_SIZE / 2, -BUTTON_SIZE / 2, 0));
        keybCloseSvg.setBoundsPickingBehaviour(AbstractShape.BOUNDS_ONLY_CHECK);
        keybCloseSvg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getID() == TapEvent.BUTTON_CLICKED) {
                    closeKeyboard();
                }
            }
        });
        addChild(keybCloseSvg);

        changeVisibility();
    }

    private Collection<KeyInfo> createKeyboardLetterKeyInfos() {
        Collection<KeyInfo> keyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo("q_key", "q", "data/key_q.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("w_key", "w", "data/key_w.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("e_key", "e", "data/key_e.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("r_key", "r", "data/key_r.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("t_key", "t", "data/key_t.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("z_key", "z", "data/key_z.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("u_key", "u", "data/key_u.svg", new Vector3D(startX + 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("i_key", "i", "data/key_i.svg", new Vector3D(startX + 7 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("o_key", "o", "data/key_o.svg", new Vector3D(startX + 8 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("p_key", "p", "data/key_p.svg", new Vector3D(startX + 9 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("u_umlaut_key", "\u00fc", "data/key_u_umlaut.svg", new Vector3D(startX + 10 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT + BUTTON_SIZE / 3;

        keyInfos.add(new KeyInfo("a_key", "a", "data/key_a.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("s_key", "s", "data/key_s.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("d_key", "d", "data/key_d.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("f_key", "f", "data/key_f.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("g_key", "g", "data/key_g.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("h_key", "h", "data/key_h.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("j_key", "j", "data/key_j.svg", new Vector3D(startX + 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("k_key", "k", "data/key_k.svg", new Vector3D(startX + 7 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("l_key", "l", "data/key_l.svg", new Vector3D(startX + 8 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("o_umlaut_key", "\u00f6", "data/key_o_umlaut.svg", new Vector3D(startX + 9 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("a_umlaut_key", "\u00e4", "data/key_a_umlaut.svg", new Vector3D(startX + 10 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(SHIFT_ID, "", "data/key_shift.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("y_key", "y", "data/key_y.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("x_key", "x", "data/key_x.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("c_key", "c", "data/key_c.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("v_key", "v", "data/key_v.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("b_key", "b", "data/key_b.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("n_key", "n", "data/key_n.svg", new Vector3D(startX + 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("m_key", "m", "data/key_m.svg", new Vector3D(startX + 7 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("comma", ",", "data/key_comma.svg", new Vector3D(startX + 8 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("dot", ".", "data/key_dot.svg", new Vector3D(startX + 9 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("sz_key", "\u00df", "data/key_sz.svg", new Vector3D(startX + 10 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - BACKSPACE_AND_RIGHT_SHIFT_WIDTH;

        keyInfos.add(new KeyInfo(SHIFT_ID, "", "data/key_shift_large.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - WIDE_BUTTON_WIDTH;

        keyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));

        return keyInfos;
    }

    private Collection<KeyInfo> createKeyboardShiftedLetterKeyInfos() {
        Collection<KeyInfo> keyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo("q_capital_key", "Q", "data/key_capital_q.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("w_capital_key", "W", "data/key_capital_w.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("e_capital_key", "E", "data/key_capital_e.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("r_capital_key", "R", "data/key_capital_r.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("t_capital_key", "T", "data/key_capital_t.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("z_capital_key", "Z", "data/key_capital_z.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("u_capital_key", "U", "data/key_capital_u.svg", new Vector3D(startX + 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("i_capital_key", "I", "data/key_capital_i.svg", new Vector3D(startX + 7 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("o_capital_key", "O", "data/key_capital_o.svg", new Vector3D(startX + 8 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("p_capital_key", "P", "data/key_capital_p.svg", new Vector3D(startX + 9 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("u_umlaut_capital_key", "\u00dc", "data/key_capital_u_umlaut.svg", new Vector3D(startX + 10 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT + BUTTON_SIZE / 3;

        keyInfos.add(new KeyInfo("a_capital_key", "A", "data/key_capital_a.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("s_capital_key", "S", "data/key_capital_s.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("d_capital_key", "D", "data/key_capital_d.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("f_capital_key", "F", "data/key_capital_f.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("g_capital_key", "G", "data/key_capital_g.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("h_capital_key", "H", "data/key_capital_h.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("j_capital_key", "J", "data/key_capital_j.svg", new Vector3D(startX + 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("k_capital_key", "K", "data/key_capital_k.svg", new Vector3D(startX + 7 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("l_capital_key", "L", "data/key_capital_l.svg", new Vector3D(startX + 8 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("o_umlaut_capital_key", "\u00d6", "data/key_capital_o_umlaut.svg", new Vector3D(startX + 9 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("a_umlaut_capital_key", "\u00c4", "data/key_capital_a_umlaut.svg", new Vector3D(startX + 10 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(SHIFT_PRESSED_ID, "", "data/key_shift_pressed.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("y_capital_key", "Y", "data/key_capital_y.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("x_capital_key", "X", "data/key_capital_x.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("c_capital_key", "C", "data/key_capital_c.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("v_capital_key", "V", "data/key_capital_v.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("b_capital_key", "B", "data/key_capital_b.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("n_capital_key", "N", "data/key_capital_n.svg", new Vector3D(startX + 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("m_capital_key", "M", "data/key_capital_m.svg", new Vector3D(startX + 7 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("exclamation_mark", "!", "data/key_exclamation_mark.svg", new Vector3D(startX + 8 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("question_mark", "?", "data/key_question_mark.svg", new Vector3D(startX + 9 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        keyInfos.add(new KeyInfo("sz_key", "\u00df", "data/key_sz.svg", new Vector3D(startX + 10 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        
        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - BACKSPACE_AND_RIGHT_SHIFT_WIDTH;

        keyInfos.add(new KeyInfo(SHIFT_PRESSED_ID, "", "data/key_shift_pressed_large.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - WIDE_BUTTON_WIDTH;

        keyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        return keyInfos;
    }

    private Collection<KeyInfo> createKeyboardNumberKeyInfos() {
        Collection<KeyInfo> keyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo("1_key", "1", "data/key_1.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("2_key", "2", "data/key_2.svg", new Vector3D(startX + 1 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("3_key", "3", "data/key_3.svg", new Vector3D(startX + 2 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("4_key", "4", "data/key_4.svg", new Vector3D(startX + 3 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("5_key", "5", "data/key_5.svg", new Vector3D(startX + 4 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("6_key", "6", "data/key_6.svg", new Vector3D(startX + 5 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("7_key", "7", "data/key_7.svg", new Vector3D(startX + 6 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("8_key", "8", "data/key_8.svg", new Vector3D(startX + 7 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("9_key", "9", "data/key_9.svg", new Vector3D(startX + 8 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("0_key", "0", "data/key_0.svg", new Vector3D(startX + 9 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT + BUTTON_SIZE * 2 / 3;

        keyInfos.add(new KeyInfo("minus", "-", "data/key_minus.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("slash", "/", "data/key_slash.svg", new Vector3D(startX + 1 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("colon", ":", "data/key_colon.svg", new Vector3D(startX + 2 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("semicolon", ";", "data/key_semicolon.svg", new Vector3D(startX + 3 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("bracket_open", "(", "data/key_bracket_open.svg", new Vector3D(startX + 4 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("bracket_close", ")", "data/key_bracket_close.svg", new Vector3D(startX + 5 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("dollar", "$", "data/key_dollar.svg", new Vector3D(startX + 6 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("ampersand", "&", "data/key_ampersand.svg", new Vector3D(startX + 7 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("at", "@", "data/key_at.svg", new Vector3D(startX + 8 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(SIGNS_ID, "", "data/key_switch_to_signs.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startX += WIDE_BUTTON_WIDTH + BUTTON_SIZE * 4 / 5;

        keyInfos.add(new KeyInfo("dot", ".", "data/key_dot.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("comma", ",", "data/key_comma.svg", new Vector3D(startX + 1 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("question_mark", "?", "data/key_question_mark.svg", new Vector3D(startX + 2 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("exclamation_mark", "!", "data/key_exclamation_mark.svg", new Vector3D(startX + 3 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("single_quote", "'", "data/key_quote_single.svg", new Vector3D(startX + 4 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));
        keyInfos.add(new KeyInfo("double_quote", "\"", "data/key_quote_double.svg", new Vector3D(startX + 5 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - WIDE_BUTTON_WIDTH;

        keyInfos.add(new KeyInfo(SIGNS_ID, "", "data/key_switch_to_signs.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - WIDE_BUTTON_WIDTH;

        keyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        return keyInfos;
    }

    private Collection<KeyInfo> createKeyboardSignKeyInfos() {
        Collection<KeyInfo> keyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo("square_bracket_open", "[", "data/key_square_bracket_open.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("square_bracket_close", "]", "data/key_square_bracket_close.svg", new Vector3D(startX + 1 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("curly_bracket_open", "{", "data/key_curly_bracket_open.svg", new Vector3D(startX + 2 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("curly_bracket_close", "}", "data/key_curly_bracket_close.svg", new Vector3D(startX + 3 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("hash_mark", "#", "data/key_hash_mark.svg", new Vector3D(startX + 4 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("percent", "%", "data/key_percent.svg", new Vector3D(startX + 5 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("caret", "^", "data/key_caret.svg", new Vector3D(startX + 6 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("asterisk", "*", "data/key_asterisk.svg", new Vector3D(startX + 7 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("plus", "+", "data/key_plus.svg", new Vector3D(startX + 8 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("equals", "=", "data/key_equals.svg", new Vector3D(startX + 9 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT + BUTTON_SIZE * 2 / 3;

        keyInfos.add(new KeyInfo("underscore", "_", "data/key_underscore.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("backslash", "\\", "data/key_backslash.svg", new Vector3D(startX + 1 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("pipe", "|", "data/key_pipe.svg", new Vector3D(startX + 2 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("tilde", "~", "data/key_tilde.svg", new Vector3D(startX + 3 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("angle_bracket_open", "<", "data/key_angle_bracket_open.svg", new Vector3D(startX + 4 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("angle_bracket_close", ">", "data/key_angle_bracket_close.svg", new Vector3D(startX + 5 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("euro", "€", "data/key_euro.svg", new Vector3D(startX + 6 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("pound", "£", "data/key_pound.svg", new Vector3D(startX + 7 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("yen", "¥", "data/key_yen.svg", new Vector3D(startX + 8 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startX += WIDE_BUTTON_WIDTH + BUTTON_SIZE * 4 / 5;

        keyInfos.add(new KeyInfo("dot", ".", "data/key_dot.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("comma", ",", "data/key_comma.svg", new Vector3D(startX + 1 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("question_mark", "?", "data/key_question_mark.svg", new Vector3D(startX + 2 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("exclamation_mark", "!", "data/key_exclamation_mark.svg", new Vector3D(startX + 3 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("single_quote", "'", "data/key_quote_single.svg", new Vector3D(startX + 4 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));
        keyInfos.add(new KeyInfo("double_quote", "\"", "data/key_quote_double.svg", new Vector3D(startX + 5 * (BUTTON_SIZE + BUTTON_MARGIN_NUMBERS_AND_SIGNS), startY), KeyVisibility.SIGNS_IS_SELECTED));

        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - WIDE_BUTTON_WIDTH;

        keyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_LEFT;

        keyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startX = KEYBOARD_WIDTH - MARGIN_RIGHT - WIDE_BUTTON_WIDTH;

        keyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));

        return keyInfos;
    }

    private Collection<KeyInfo> createKeyboardNormalKeyInfos() {
        Collection<KeyInfo> keyInfos = new ArrayList<KeyInfo>();

        keyInfos.add(new KeyInfo("space", " ", "data/key_space_large.svg", new Vector3D(MARGIN_LEFT + WIDE_BUTTON_WIDTH, MARGIN_TOP + 3 * BUTTON_SIZE), KeyVisibility.NORMAL_KEY));

        float startY = MARGIN_TOP;
        float startX = KEYBOARD_WIDTH - MARGIN_RIGHT;

        keyInfos.add(new KeyInfo(BACKSPACE_ID, "", "data/key_backspace_large.svg", new Vector3D(startX - BACKSPACE_AND_RIGHT_SHIFT_WIDTH, startY), KeyVisibility.NORMAL_KEY));
        keyInfos.add(new KeyInfo("return", "\n", "data/key_return.svg", new Vector3D(startX - RETURN_WIDTH, startY + BUTTON_SIZE), KeyVisibility.NORMAL_KEY));

        return keyInfos;
    }

    private void changeVisibility() {
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

    private void closeKeyboard() {
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

    private void createKeyButtonsOutOfKeyInfos(Collection<KeyInfo> keyInfos) {
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

            addChild(key);
        }
    }

    private enum KeyboardVisiblity {

        LETTERS,
        LETTERS_WITH_SHIFT_PRESSED,
        NUMBERS,
        SIGNS;
    }

    private enum KeyVisibility {

        NORMAL_KEY,
        LETTERS_IS_SELECTED,
        LETTERS_IS_SELECTED_AND_SHIFT_PRESSED,
        NUMBERS_IS_SELECTED,
        SIGNS_IS_SELECTED;
    }

    private class KeyInfo {

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

    private class ShiftButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                shiftPressed = true;
                keyboardVisiblity = KeyboardVisiblity.LETTERS_WITH_SHIFT_PRESSED;
                changeVisibility();
            }
        }
    }

    private class KeyButtonPressedListener implements ActionListener {

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

    private class ShiftPressedButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                shiftPressed = false;
                keyboardVisiblity = KeyboardVisiblity.LETTERS;
                changeVisibility();
            }
        }
    }

    private class LettersButtonPressedListener implements ActionListener {

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

    private class NumbersButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                keyboardVisiblity = KeyboardVisiblity.NUMBERS;
                changeVisibility();
            }
        }
    }

    private class SignsButtonPressedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getID() == TapEvent.BUTTON_DOWN) {
                keyboardVisiblity = KeyboardVisiblity.SIGNS;
                changeVisibility();
            }
        }
    }
}