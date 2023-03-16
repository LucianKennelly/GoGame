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
        // Call the method to clear the text field
        clearTextField();
    }

    private void clearTextField() {
        editText.setText("");
    }
}