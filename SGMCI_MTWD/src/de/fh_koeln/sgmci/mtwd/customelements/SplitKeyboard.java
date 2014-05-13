package de.fh_koeln.sgmci.mtwd.customelements;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import org.mt4j.components.MTComponent;
import org.mt4j.components.visibleComponents.shapes.AbstractShape;
import org.mt4j.components.visibleComponents.shapes.MTRoundRectangle;
import org.mt4j.components.visibleComponents.widgets.buttons.MTSvgButton;
import org.mt4j.input.inputProcessors.componentProcessors.tapProcessor.TapEvent;
import org.mt4j.util.MT4jSettings;
import org.mt4j.util.MTColor;
import org.mt4j.util.math.Vector3D;
import processing.core.PApplet;

/**
 * This class is used for a new, custom keyboard layout.<br >
 * It represents a split keyboard.
 * 
 * @author Daniel van der Wal
 * @version 0.1.0
 */
public final class SplitKeyboard extends AbstractKeyboard {

    private static final int MARGIN_KEYBOARD_INSIDE = 20;
    private static final int MARGIN_KEYBOARD_OUTSIDE = 20;
    private static final float SPACE_WIDTH = 199 + 2 * BUTTON_MARGIN;
    private static final int CORNER_RADIUS = 30;
    private static final int KEYBOARD_WIDTH = 317 + MARGIN_KEYBOARD_INSIDE + MARGIN_KEYBOARD_OUTSIDE;
    private final MTRoundRectangle leftKeyboard;
    private final MTRoundRectangle rightKeyboard;
    private float spaceBetweenKeyboards = 200;

    public SplitKeyboard(PApplet pApplet) {
        super(0, 0, 0, KEYBOARD_WIDTH* 2, KEYBOARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS, pApplet);

        this.leftKeyboard = new MTRoundRectangle(0, 0, 0, KEYBOARD_WIDTH, KEYBOARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS, pApplet);
        this.rightKeyboard = new MTRoundRectangle(0, 0, 0, KEYBOARD_WIDTH, KEYBOARD_HEIGHT, CORNER_RADIUS, CORNER_RADIUS, pApplet);
        this.setFillColor(MTColor.BLACK);
        this.setNoFill(true);
        this.setNoStroke(true);
        this.removeAllGestureEventListeners();
        
        this.createKeyboard();
    }

    /**
     * Get the left part of the keyboard.
     * 
     * @return the left part of the keyboard
     */
    public MTComponent getLeftKeyboard() {
        return leftKeyboard;
    }

    /**
     * Get the right part of the keyboard.
     * 
     * @return the right part of the keyboard
     */
    public MTComponent getRightKeyboard() {
        return rightKeyboard;
    }

    /**
     * Get the complete width of the keyboard, including the space between.
     * 
     * @return the complete width of the keyboard, including the space between
     */
    public float getWidth() {
        return spaceBetweenKeyboards + 2 * KEYBOARD_WIDTH;
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
     * Changes the space between the keyboards by moving the left side and right side further apart.
     * 
     * @param space the space that the keyboard parts should have between them
     */
    public void setSpaceBetweenKeyboards(float space) {
        leftKeyboard.translate(new Vector3D(spaceBetweenKeyboards / 2, 0));
        rightKeyboard.translate(new Vector3D(-spaceBetweenKeyboards / 2, 0));
        leftKeyboard.translate(new Vector3D(-space/2, 0));
        rightKeyboard.translate(new Vector3D(space/2, 0));
        spaceBetweenKeyboards = space;
    } 

    @Override
    public void setFillColor(MTColor fillColor) {
        leftKeyboard.setFillColor(fillColor);
        rightKeyboard.setFillColor(fillColor);
    }
    
    @Override
    public void setPickable(boolean pickable) {
        super.setPickable(pickable);
        leftKeyboard.setPickable(pickable);
        rightKeyboard.setPickable(pickable);
    }

    @Override
    public void unregisterAllInputProcessors() {
        super.unregisterAllInputProcessors();
        leftKeyboard.unregisterAllInputProcessors();
        rightKeyboard.unregisterAllInputProcessors();
    }

    @Override
    public void removeAllGestureEventListeners() {
        super.removeAllGestureEventListeners();
        leftKeyboard.removeAllGestureEventListeners();
        rightKeyboard.removeAllGestureEventListeners();
    }

    private void createKeyboard() {
        Collection<KeyInfo> leftKeyInfos = new LinkedList<KeyInfo>();
        Collection<KeyInfo> rightKeyInfos = new LinkedList<KeyInfo>();

        leftKeyInfos.addAll(createLeftKeyboardLetterKeyInfos());
        leftKeyInfos.addAll(createLeftKeyboardShiftedLetterKeyInfos());
        leftKeyInfos.addAll(createLeftKeyboardNumberKeyInfos());
        leftKeyInfos.addAll(createLeftKeyboardSignKeyInfos());
        leftKeyInfos.addAll(createLeftKeyboardNormalKeyInfos());

        rightKeyInfos.addAll(createRightKeyboardLetterKeyInfos());
        rightKeyInfos.addAll(createRightKeyboardShiftedLetterKeyInfos());
        rightKeyInfos.addAll(createRightKeyboardNumberKeyInfos());
        rightKeyInfos.addAll(createRightKeyboardSignKeyInfos());
        rightKeyInfos.addAll(createRightKeyboardNormalKeyInfos());

        createKeyButtonsOutOfKeyInfos(leftKeyInfos, leftKeyboard);
        createKeyButtonsOutOfKeyInfos(rightKeyInfos, rightKeyboard);

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
        rightKeyboard.addChild(keybCloseSvg);

        // set start position of right keyboard
        rightKeyboard.translate(new Vector3D(KEYBOARD_WIDTH, 0));
        
        leftKeyboard.translate(new Vector3D(-spaceBetweenKeyboards/2, 0));
        rightKeyboard.translate(new Vector3D(spaceBetweenKeyboards/2, 0));

        this.addChild(leftKeyboard);
        this.addChild(rightKeyboard);

        this.changeVisibility();
    }

    private Collection<KeyInfo> createLeftKeyboardLetterKeyInfos() {
        Collection<KeyInfo> leftKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo("q_key", "q", "data/key_q.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("w_key", "w", "data/key_w.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("e_key", "e", "data/key_e.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("r_key", "r", "data/key_r.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("t_key", "t", "data/key_t.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("z_key", "z", "data/key_z.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE + BUTTON_SIZE / 3;

        leftKeyInfos.add(new KeyInfo("a_key", "a", "data/key_a.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("s_key", "s", "data/key_s.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("d_key", "d", "data/key_d.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("f_key", "f", "data/key_f.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("g_key", "g", "data/key_g.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("h_key", "h", "data/key_h.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(SHIFT_ID, "", "data/key_shift.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("y_key", "y", "data/key_y.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("x_key", "x", "data/key_x.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("c_key", "c", "data/key_c.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("v_key", "v", "data/key_v.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("b_key", "b", "data/key_b.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));

        return leftKeyInfos;
    }

    private Collection<KeyInfo> createLeftKeyboardShiftedLetterKeyInfos() {
        Collection<KeyInfo> leftKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo("q_capital_key", "Q", "data/key_capital_q.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("w_capital_key", "W", "data/key_capital_w.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("e_capital_key", "E", "data/key_capital_e.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("r_capital_key", "R", "data/key_capital_r.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("t_capital_key", "T", "data/key_capital_t.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("z_capital_key", "Z", "data/key_capital_z.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE + BUTTON_SIZE / 3;

        leftKeyInfos.add(new KeyInfo("a_capital_key", "A", "data/key_capital_a.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("s_capital_key", "S", "data/key_capital_s.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("d_capital_key", "D", "data/key_capital_d.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("f_capital_key", "F", "data/key_capital_f.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("g_capital_key", "G", "data/key_capital_g.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("h_capital_key", "H", "data/key_capital_h.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(SHIFT_PRESSED_ID, "", "data/key_shift_pressed.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("y_capital_key", "Y", "data/key_capital_y.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("x_capital_key", "X", "data/key_capital_x.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("c_capital_key", "C", "data/key_capital_c.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("v_capital_key", "V", "data/key_capital_v.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        leftKeyInfos.add(new KeyInfo("b_capital_key", "B", "data/key_capital_b.svg", new Vector3D(startX + 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        return leftKeyInfos;
    }

    private Collection<KeyInfo> createLeftKeyboardNumberKeyInfos() {
        Collection<KeyInfo> leftKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo("1_key", "1", "data/key_1.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("2_key", "2", "data/key_2.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("3_key", "3", "data/key_3.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("4_key", "4", "data/key_4.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("5_key", "5", "data/key_5.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE + BUTTON_SIZE / 3;

        leftKeyInfos.add(new KeyInfo("minus", "-", "data/key_minus.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("slash", "/", "data/key_slash.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("colon", ":", "data/key_colon.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("semicolon", ";", "data/key_semicolon.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("bracket_open", "(", "data/key_bracket_open.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(SIGNS_ID, "", "data/key_switch_to_signs.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startX += WIDE_BUTTON_WIDTH;

        leftKeyInfos.add(new KeyInfo("dot", ".", "data/key_dot.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("comma", ",", "data/key_comma.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("question_mark", "?", "data/key_question_mark.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        return leftKeyInfos;
    }

    private Collection<KeyInfo> createLeftKeyboardSignKeyInfos() {
        Collection<KeyInfo> leftKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo("square_bracket_open", "[", "data/key_square_bracket_open.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("square_bracket_close", "]", "data/key_square_bracket_close.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("curly_bracket_open", "{", "data/key_curly_bracket_open.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("curly_bracket_close", "}", "data/key_curly_bracket_close.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("hash_mark", "#", "data/key_hash_mark.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE + BUTTON_SIZE / 3;

        leftKeyInfos.add(new KeyInfo("underscore", "_", "data/key_underscore.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("backslash", "\\", "data/key_backslash.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("pipe", "|", "data/key_pipe.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("tilde", "~", "data/key_tilde.svg", new Vector3D(startX + 3 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("angle_bracket_open", "<", "data/key_angle_bracket_open.svg", new Vector3D(startX + 4 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startX += WIDE_BUTTON_WIDTH;

        leftKeyInfos.add(new KeyInfo("dot", ".", "data/key_dot.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("comma", ",", "data/key_comma.svg", new Vector3D(startX + 1 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        leftKeyInfos.add(new KeyInfo("question_mark", "?", "data/key_question_mark.svg", new Vector3D(startX + 2 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = MARGIN_KEYBOARD_OUTSIDE;

        leftKeyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));

        return leftKeyInfos;
    }

    private Collection<KeyInfo> createLeftKeyboardNormalKeyInfos() {
        Collection<KeyInfo> leftKeyInfos = new ArrayList<KeyInfo>();

        leftKeyInfos.add(new KeyInfo("space", " ", "data/key_space.svg", new Vector3D(MARGIN_KEYBOARD_OUTSIDE + WIDE_BUTTON_WIDTH, MARGIN_TOP + 3 * BUTTON_SIZE), KeyVisibility.NORMAL_KEY));

        return leftKeyInfos;
    }

    private Collection<KeyInfo> createRightKeyboardLetterKeyInfos() {
        Collection<KeyInfo> rightKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE;

        rightKeyInfos.add(new KeyInfo("u_umlaut_key", "\u00fc", "data/key_u_umlaut.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("p_key", "p", "data/key_p.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("o_key", "o", "data/key_o.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("i_key", "i", "data/key_i.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("u_key", "u", "data/key_u.svg", new Vector3D(startX - 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - RETURN_WIDTH;

        rightKeyInfos.add(new KeyInfo("a_umlaut_key", "\u00e4", "data/key_a_umlaut.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("o_umlaut_key", "\u00f6", "data/key_o_umlaut.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("l_key", "l", "data/key_l.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("k_key", "k", "data/key_k.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("j_key", "j", "data/key_j.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE;

        rightKeyInfos.add(new KeyInfo(SHIFT_ID, "", "data/key_shift.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("sz_key", "\u00df", "data/key_sz.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("dot", ".", "data/key_dot.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("comma", ",", "data/key_comma.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("m_key", "m", "data/key_m.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("n_key", "n", "data/key_n.svg", new Vector3D(startX - 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - WIDE_BUTTON_WIDTH;

        rightKeyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED));

        return rightKeyInfos;
    }

    private Collection<KeyInfo> createRightKeyboardShiftedLetterKeyInfos() {
        Collection<KeyInfo> rightKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE;

        rightKeyInfos.add(new KeyInfo("u_umlaut_capital_key", "\u00dc", "data/key_capital_u_umlaut.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("p_capital_key", "P", "data/key_capital_p.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("o_capital_key", "O", "data/key_capital_o.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("i_capital_key", "I", "data/key_capital_i.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("u_capital_key", "U", "data/key_capital_u.svg", new Vector3D(startX - 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - RETURN_WIDTH;

        rightKeyInfos.add(new KeyInfo("a_umlaut_capital_key", "\u00c4", "data/key_capital_a_umlaut.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("o_umlaut_capital_key", "\u00d6", "data/key_capital_o_umlaut.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("l_capital_key", "L", "data/key_capital_l.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("k_capital_key", "K", "data/key_capital_k.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("j_capital_key", "J", "data/key_capital_j.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE;

        rightKeyInfos.add(new KeyInfo(SHIFT_PRESSED_ID, "", "data/key_shift_pressed.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("sz_key", "\u00df", "data/key_sz.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("question_mark", "?", "data/key_question_mark.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("exclamation_mark", "!", "data/key_exclamation_mark.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("m_capital_key", "M", "data/key_capital_m.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));
        rightKeyInfos.add(new KeyInfo("n_capital_key", "N", "data/key_capital_n.svg", new Vector3D(startX - 6 * BUTTON_SIZE, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - WIDE_BUTTON_WIDTH;

        rightKeyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers_and_signs.svg", new Vector3D(startX, startY), KeyVisibility.LETTERS_IS_SELECTED_AND_SHIFT_PRESSED));

        return rightKeyInfos;
    }

    private Collection<KeyInfo> createRightKeyboardNumberKeyInfos() {
        Collection<KeyInfo> rightKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE;

        rightKeyInfos.add(new KeyInfo("0_key", "0", "data/key_0.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("9_key", "9", "data/key_9.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("8_key", "8", "data/key_8.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("7_key", "7", "data/key_7.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("6_key", "6", "data/key_6.svg", new Vector3D(startX - 6 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - RETURN_WIDTH;

        rightKeyInfos.add(new KeyInfo("at", "@", "data/key_at.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("ampersand", "&", "data/key_ampersand.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("dollar", "$", "data/key_dollar.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("bracket_close", ")", "data/key_bracket_close.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - WIDE_BUTTON_WIDTH;

        rightKeyInfos.add(new KeyInfo(SIGNS_ID, "", "data/key_switch_to_signs.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("double_quote", "\"", "data/key_quote_double.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("single_quote", "'", "data/key_quote_single.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("exclamation_mark", "!", "data/key_exclamation_mark.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - WIDE_BUTTON_WIDTH;

        rightKeyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.NUMBERS_IS_SELECTED));

        return rightKeyInfos;
    }

    private Collection<KeyInfo> createRightKeyboardSignKeyInfos() {
        Collection<KeyInfo> rightKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE;

        rightKeyInfos.add(new KeyInfo("equals", "=", "data/key_equals.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("plus", "+", "data/key_plus.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("asterisk", "*", "data/key_asterisk.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("caret", "^", "data/key_caret.svg", new Vector3D(startX - 5 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("percent", "%", "data/key_percent.svg", new Vector3D(startX - 6 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - RETURN_WIDTH;

        rightKeyInfos.add(new KeyInfo("yen", "¥", "data/key_yen.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("pound", "£", "data/key_pound.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("euro", "€", "data/key_euro.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("angle_bracket_close", ">", "data/key_angle_bracket_close.svg", new Vector3D(startX - 4 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - WIDE_BUTTON_WIDTH;

        rightKeyInfos.add(new KeyInfo(NUMBERS_ID, "", "data/key_switch_to_numbers.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("double_quote", "\"", "data/key_quote_double.svg", new Vector3D(startX - 1 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("single_quote", "'", "data/key_quote_single.svg", new Vector3D(startX - 2 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));
        rightKeyInfos.add(new KeyInfo("exclamation_mark", "!", "data/key_exclamation_mark.svg", new Vector3D(startX - 3 * BUTTON_SIZE, startY), KeyVisibility.SIGNS_IS_SELECTED));

        startY += BUTTON_SIZE;
        startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE - WIDE_BUTTON_WIDTH;

        rightKeyInfos.add(new KeyInfo(LETTERS_ID, "", "data/key_switch_to_letters.svg", new Vector3D(startX, startY), KeyVisibility.SIGNS_IS_SELECTED));

        return rightKeyInfos;
    }

    private Collection<KeyInfo> createRightKeyboardNormalKeyInfos() {
        Collection<KeyInfo> rightKeyInfos = new ArrayList<KeyInfo>();

        float startY = MARGIN_TOP;
        float startX = KEYBOARD_WIDTH - MARGIN_KEYBOARD_OUTSIDE;

        rightKeyInfos.add(new KeyInfo(BACKSPACE_ID, "", "data/key_backspace.svg", new Vector3D(startX - BUTTON_SIZE, startY), KeyVisibility.NORMAL_KEY));
        rightKeyInfos.add(new KeyInfo("return", "\n", "data/key_return.svg", new Vector3D(startX - RETURN_WIDTH, startY + BUTTON_SIZE), KeyVisibility.NORMAL_KEY));
        rightKeyInfos.add(new KeyInfo("space", " ", "data/key_space.svg", new Vector3D(startX - WIDE_BUTTON_WIDTH - SPACE_WIDTH, startY + 3 * BUTTON_SIZE), KeyVisibility.NORMAL_KEY));

        return rightKeyInfos;
    }
}