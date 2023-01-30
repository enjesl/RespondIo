/*
 * Copyright 2004 ThoughtWorks, Inc. Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.automation.util;

/*
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class KeyCodes. Holds the key codes which will be used across many
 * command in the runtime. Key codes are saved within a hash map and the key
 * represents the actual key combination of the keyboard. The hash map will be
 * initialized at the constructor of the key codes.
 */
public class KeyCodes {

	/** The key codes hash map. */
	private Map<String, int[]> codes;

	/**
	 * Instantiates a new key codes.
	 */
	public KeyCodes() {
		init();
	}

	/**
	 * Inits the key codes which will be used across many command in the runtime.
	 * Key codes are saved within a hash map and the key represents the actual key
	 * combination of the keyboard. The hash map will be initialized at the
	 * constructor of the key codes.
	 * 
	 * For key code for simulating key 'a' pass the String "a" For key code for
	 * simulating key 'b' pass the String "a" For key code for simulating key 'c'
	 * pass the String "a" For key code for simulating key 'D' pass the String "D"
	 * For key code for simulating key 'E' pass the String "E" For key code for
	 * simulating key 'F' pass the String "F" For key code for simulating key
	 * 'alt+F4' pass the String "alt+F4 For key code for simulating key 'shift+\t'
	 * pass the String "shift+\t" For key code for simulating key 'ctrl+o' pass the
	 * String "ctrl+o"
	 * 
	 */
	private void init() {
		codes = new HashMap<>();
		// key code for key event "a"
		codes.put("a", new int[] { KeyEvent.VK_A });
		// key code for key event "b"
		codes.put("b", new int[] { KeyEvent.VK_B });
		// key code for key event "c"
		codes.put("c", new int[] { KeyEvent.VK_C });
		// key code for key event "d"
		codes.put("d", new int[] { KeyEvent.VK_D });
		// key code for key event "e"
		codes.put("e", new int[] { KeyEvent.VK_E });
		// key code for key event "f"
		codes.put("f", new int[] { KeyEvent.VK_F });
		// key code for key event "g"
		codes.put("g", new int[] { KeyEvent.VK_G });
		// key code for key event "h"
		codes.put("h", new int[] { KeyEvent.VK_H });
		// key code for key event "i"
		codes.put("i", new int[] { KeyEvent.VK_I });
		// key code for key event "j"
		codes.put("j", new int[] { KeyEvent.VK_J });
		// key code for key event "k"
		codes.put("k", new int[] { KeyEvent.VK_K });
		// key code for key event "l"
		codes.put("l", new int[] { KeyEvent.VK_L });
		// key code for key event "m"
		codes.put("m", new int[] { KeyEvent.VK_M });
		// key code for key event "n"
		codes.put("n", new int[] { KeyEvent.VK_N });
		// key code for key event "o"
		codes.put("o", new int[] { KeyEvent.VK_O });
		// key code for key event "p"
		codes.put("p", new int[] { KeyEvent.VK_P });
		// key code for key event "q"
		codes.put("q", new int[] { KeyEvent.VK_Q });
		// key code for key event "r"
		codes.put("r", new int[] { KeyEvent.VK_R });
		codes.put("s", new int[] { KeyEvent.VK_S });
		codes.put("t", new int[] { KeyEvent.VK_T });
		codes.put("u", new int[] { KeyEvent.VK_U });
		codes.put("v", new int[] { KeyEvent.VK_V });
		codes.put("w", new int[] { KeyEvent.VK_W });
		codes.put("x", new int[] { KeyEvent.VK_X });
		codes.put("y", new int[] { KeyEvent.VK_Y });
		codes.put("z", new int[] { KeyEvent.VK_Z });
		codes.put("A", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_A });
		codes.put("B", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_B });
		codes.put("C", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_C });
		codes.put("D", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_D });
		codes.put("E", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_E });
		codes.put("F", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_F });
		codes.put("G", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_G });
		codes.put("H", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_H });
		codes.put("I", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_I });
		codes.put("J", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_J });
		codes.put("K", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_K });
		codes.put("L", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_L });
		codes.put("M", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_M });
		codes.put("N", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_N });
		codes.put("O", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_O });
		codes.put("P", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_P });
		codes.put("Q", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_Q });
		codes.put("R", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_R });
		codes.put("S", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_S });
		codes.put("T", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_T });
		codes.put("U", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_U });
		codes.put("V", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_V });
		codes.put("W", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_W });
		codes.put("X", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_X });
		codes.put("Y", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_Y });
		codes.put("Z", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_Z });
		codes.put("`", new int[] { KeyEvent.VK_BACK_QUOTE });
		codes.put("0", new int[] { KeyEvent.VK_0 });
		codes.put("1", new int[] { KeyEvent.VK_1 });
		codes.put("2", new int[] { KeyEvent.VK_2 });
		codes.put("3", new int[] { KeyEvent.VK_3 });
		codes.put("4", new int[] { KeyEvent.VK_4 });
		codes.put("5", new int[] { KeyEvent.VK_5 });
		codes.put("6", new int[] { KeyEvent.VK_6 });
		codes.put("7", new int[] { KeyEvent.VK_7 });
		codes.put("8", new int[] { KeyEvent.VK_8 });
		codes.put("9", new int[] { KeyEvent.VK_9 });
		codes.put("-", new int[] { KeyEvent.VK_MINUS });
		codes.put("=", new int[] { KeyEvent.VK_EQUALS });
		codes.put("~", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE });
		codes.put("!", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_1 });
		codes.put("@", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_2 });
		codes.put("#", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_3 });
		codes.put("$", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_4 });
		codes.put("%", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_5 });
		codes.put("^", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_6 });
		codes.put("&", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_7 });
		codes.put("*", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_8 });
		codes.put("(", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_9 });
		codes.put(")", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_0 });
		codes.put("_", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_MINUS });
		codes.put("+", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_EQUALS });
		codes.put("\\t", new int[] { KeyEvent.VK_TAB });
		codes.put("\\n", new int[] { KeyEvent.VK_ENTER });
		codes.put("[", new int[] { KeyEvent.VK_OPEN_BRACKET });
		codes.put("]", new int[] { KeyEvent.VK_CLOSE_BRACKET });
		codes.put("\\", new int[] { KeyEvent.VK_BACK_SLASH });
		codes.put("{", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET });
		codes.put("}", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET });
		codes.put("|", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH });
		codes.put(";", new int[] { KeyEvent.VK_SEMICOLON });
		codes.put(":", new int[] { KeyEvent.VK_COLON });
		codes.put("'", new int[] { KeyEvent.VK_QUOTE });
		codes.put("\"", new int[] { KeyEvent.VK_QUOTEDBL });
		codes.put(",", new int[] { KeyEvent.VK_COMMA });
		codes.put("<", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_COMMA });
		codes.put(".", new int[] { KeyEvent.VK_PERIOD });
		codes.put(">", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_PERIOD });
		codes.put("/", new int[] { KeyEvent.VK_SLASH });
		codes.put("?", new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH });
		codes.put(" ", new int[] { KeyEvent.VK_SPACE });
		codes.put("alt", new int[] { KeyEvent.VK_ALT });
		codes.put("ctrl", new int[] { KeyEvent.VK_CONTROL });
		codes.put("esc", new int[] { KeyEvent.VK_ESCAPE });
		codes.put("down", new int[] { KeyEvent.VK_DOWN });
		codes.put("up", new int[] { KeyEvent.VK_UP });
		codes.put("left", new int[] { KeyEvent.VK_LEFT });
		codes.put("right", new int[] { KeyEvent.VK_RIGHT });
		codes.put("F1", new int[] { KeyEvent.VK_F1 });
		codes.put("F2", new int[] { KeyEvent.VK_F2 });
		codes.put("F3", new int[] { KeyEvent.VK_F3 });
		codes.put("F4", new int[] { KeyEvent.VK_F4 });
		codes.put("F5", new int[] { KeyEvent.VK_F5 });
		codes.put("F6", new int[] { KeyEvent.VK_F6 });
		codes.put("F7", new int[] { KeyEvent.VK_F7 });
		codes.put("F8", new int[] { KeyEvent.VK_F8 });
		codes.put("F9", new int[] { KeyEvent.VK_F9 });
		codes.put("F10", new int[] { KeyEvent.VK_F10 });
		codes.put("F11", new int[] { KeyEvent.VK_F11 });
		codes.put("F12", new int[] { KeyEvent.VK_F12 });
		codes.put("insert", new int[] { KeyEvent.VK_INSERT });
		codes.put("home", new int[] { KeyEvent.VK_HOME });
		codes.put("pageup", new int[] { KeyEvent.VK_PAGE_UP });
		codes.put("backspace", new int[] { KeyEvent.VK_BACK_SPACE });
		codes.put("delete", new int[] { KeyEvent.VK_DELETE });
		codes.put("end", new int[] { KeyEvent.VK_END });
		codes.put("pagedown", new int[] { KeyEvent.VK_PAGE_DOWN });

	}

	/**
	 * Gets the key codes for the given character. Returns the key code array for
	 * the given key combination.
	 * 
	 * @param character the character
	 * @return the key codes
	 */
	public final int[] getKeyCodes(final String character) {
		return codes.get(character);
	}

}
