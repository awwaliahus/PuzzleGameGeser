package com.kel7.puzzlegamegeser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GridLayout gridLayout;
    private Button[][] buttons;
    private List<String> puzzlePieces;
    private int emptyRow, emptyCol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        buttons = new Button[3][3];
        puzzlePieces = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", ""));

        Collections.shuffle(puzzlePieces); // Acak puzzle

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new Button(this);
                buttons[row][col].setText(puzzlePieces.get(row * 3 + col));
                buttons[row][col].setOnClickListener(this);
                gridLayout.addView(buttons[row][col]);

                if (puzzlePieces.get(row * 3 + col).isEmpty()) {
                    emptyRow = row;
                    emptyCol = col;
                }
            }
        }

        Button shuffleButton = findViewById(R.id.shuffleButton);
        shuffleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shufflePuzzle();
            }
        });
        Button cheatButton = findViewById(R.id.cheatButton);
        cheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                cheat();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;
        String buttonText = clickedButton.getText().toString();
        int row = -1, col = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j] == clickedButton) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if (isAdjacent(emptyRow, emptyCol, row, col)) {
            buttons[emptyRow][emptyCol].setText(buttonText);
            buttons[row][col].setText("");
            emptyRow = row;
            emptyCol = col;
        }

        checkWin();
    }

    private boolean isAdjacent(int emptyRow, int emptyCol, int row, int col) {
        return (Math.abs(emptyRow - row) == 1 && emptyCol == col) ||
                (Math.abs(emptyCol - col) == 1 && emptyRow == row);
    }

    private void shufflePuzzle() {
        Collections.shuffle(puzzlePieces);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(puzzlePieces.get(row * 3 + col));
                if (puzzlePieces.get(row * 3 + col).isEmpty()) {
                    emptyRow = row;
                    emptyCol = col;
                }
            }
        }
    }

    private void checkWin() {
        List<String> currentOrder = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                currentOrder.add(buttons[row][col].getText().toString());
            }
        }

        List<String> winOrder = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "");

        if (currentOrder.equals(winOrder)) {
            showWinDialog();
        }
    }

    private void showWinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("YOU WON!");
        builder.setMessage("Congratulation...");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
    private void cheat() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(String.valueOf((char) ('A' + row * 3 + col)));
            }
        }
        buttons[2][2].setText("");
        checkWin();
    }
}
