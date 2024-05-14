/*
 * This source file was generated by the Gradle 'init' task
 */
package org.loadtest.app;

import java.util.*;

import static org.example.utilities.StringUtils.join;
import static org.example.utilities.StringUtils.split;

import org.apache.commons.text.WordUtils;

public class App {
    public static void main(String[] args) {
        LinkedList tokens;
        tokens = split(MessageUtils.getMessage());
        String result = join(tokens);
        System.out.println(WordUtils.capitalize(result));
    }
}
