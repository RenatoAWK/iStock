package bsi.mpoo.istock.services;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public abstract class Mask {

    public static String unmask(String text) {
        return text.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "").replaceAll(" ", "")
                .replaceAll(",", "");
    }

    public static boolean isASign(char c) {
        if (c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' ') {
            return true;
        } else {
            return false;
        }
    }

    public static String mask(String mask, String text) {
        int i = 0;
        StringBuilder result = new StringBuilder();
        for (char character : mask.toCharArray()) {
            if (character != '#') {
                result.append(character);
                continue;
            }
            try {
                result.append(text.charAt(i));
            } catch (Exception e) {
                break;
            }
            i++;
        }
        return result.toString();
    }

    public static TextWatcher insert(final String mask, final EditText editText) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                String unmasked = Mask.unmask(charSequence.toString());
                StringBuilder result = new StringBuilder();
                if (isUpdating) {
                    old = unmasked;
                    isUpdating = false;
                    return;
                }

                int index = 0;
                for (int i = 0; i < mask.length(); i++) {
                    char character = mask.charAt(i);
                    if (character != '#') {
                        if (index == unmasked.length() && unmasked.length() < old.length()) {
                            continue;
                        }
                        result.append(character);
                        continue;
                    }

                    try {
                        result.append(unmasked.charAt(index));
                    } catch (Exception e) {
                        break;
                    }
                    index++;
                }

                if (result.length() > 0) {
                    char last_char = result.charAt(result.length() - 1);
                    boolean hadSign = false;
                    while (isASign(last_char) && unmasked.length() == old.length()) {
                        result = new StringBuilder(result.substring(0, result.length() - 1));
                        last_char = result.charAt(result.length() - 1);
                        hadSign = true;
                    }

                    if (result.length() > 0 && hadSign) {
                        result = new StringBuilder(result.substring(0, result.length() - 1));
                    }
                }
                isUpdating = true;
                editText.setText(result);
                editText.setSelection(result.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void afterTextChanged(Editable s) {}
        };
    }
}
