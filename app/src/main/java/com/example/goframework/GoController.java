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

        //copying the first instance
        GoGameState secondInstance = new GoGameState(firstInstance);


        //creating third instance
        GoGameState thirdInstance = new GoGameState();

        //copying third instance
        GoGameState fourthInstance = new GoGameState(thirdInstance);



        //getting the boolean value for the place stone method
        Boolean bool = firstInstance._placeStone(1, 5, 5 , firstInstance.getGameBoard());

        String boolVal;

        //setting the string depending on the bool value
        if(bool == true) {
            boolVal = "First Instance's move is legal";
        }
        else {
            boolVal = "First Instance's move is illegal";
        }

        String secondString = secondInstance.toString();
        String fourthString = fourthInstance.toString();

        //setting the text of the textView
        String finalString = "Valid Move***: " + boolVal + " Second Instance*** " + secondString + "     Fourth Instance***" + fourthString;
        editText.setText(finalString);
    }
}