package com.gmtk.game.dialog;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestionAnswer {

    private HashMap<String, String> answers;

    /**
     *
     * @param answerSet An array set of the form [ ['Yes', 'abc123'], ['No', 'def456'] ] which constitutes the decisions.
     */
    public QuestionAnswer(String[][] answerSet) {
        for (int i = 0; i < answerSet.length; i++) {
            this.answers.put(answerSet[i][0], answerSet[i][1]);
        }
    }

    public ArrayList<String> getAnswers() {
        return (ArrayList<String>) this.answers.keySet();
    }

    public String getIDForAnswer(String answer) {
        return this.answers.get(answer);
    }
}
