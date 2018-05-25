package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class HelpQuestion extends AnchorPane {

    private String question;
    private String answer;

    @FXML
    Label questionElement, answerElement;

    public HelpQuestion(String question, String answer) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/helpPageSingleQuestionElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.question = question;
        this.answer = answer;

        questionElement.setText(question);
        answerElement.setText(answer);
    }
}
