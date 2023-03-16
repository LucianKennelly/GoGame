package com.example.goframework;

import android.view.View;
import android.widget.EditText;

public class GoController implements View.OnClickListener {

    private EditText editText;

    public GoController(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onClick(View view) {
        //setting the text to blank
        editText.setText("");

        //creating a first instance
        GoGameState firstInstance = new GoGameState();
    }
}