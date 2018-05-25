package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelpPage extends AnchorPane {

    @FXML
    FlowPane helpFlowPane;

    List<HelpQuestion> questions;

    public HelpPage() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/helpPageElement.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        addQuestions();

        for (HelpQuestion hq : questions) {
            helpFlowPane.getChildren().add(hq);
        }
    }

    private void addQuestions() {
        questions = new ArrayList<HelpQuestion>();

        questions.add(new HelpQuestion("Vad blir 1+1?", "Svaret är 2!"));
        questions.add(new HelpQuestion("Vad blir 8+1?", "Svaret är 9!"));
        questions.add(new HelpQuestion("Vad blir 1-6?", "Svaret är -5!"));
    }
}
