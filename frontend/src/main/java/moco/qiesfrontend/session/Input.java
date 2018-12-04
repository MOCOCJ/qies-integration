package moco.qiesfrontend.session;

import java.util.Scanner;

/**
 * Prompt
 */
public class Input {

    private String prompt;
    private Scanner inputReader;

    public Input(String prompt) {
        setPrompt(prompt);
        inputReader = new Scanner(System.in);
    }

    public void setPrompt(String prompt) {
        this.prompt = "[" + prompt + "]  ";
    }

    public String takeInput(String message) {
        System.out.println(message);
        System.out.print(prompt);

        String in = inputReader.nextLine();

        return in;
    }
}