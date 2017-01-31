package com.example.hayesdm.quarterbackpassratingcalc;
// import statements
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnFocusChangeListener;

// The various action listeners get implemented here
public class PassRatingCalc extends AppCompatActivity
implements OnClickListener, OnEditorActionListener, OnFocusChangeListener {

    // define variables for the widgets
    private TextView passAttemptsTextView;
    private EditText passAttemptsEditText;
    private TextView passCompletionsTextView;
    private EditText passCompletionsEditText;
    private TextView passYardsTextView;
    private EditText passYardsEditText;
    private TextView passTouchdownsTextView;
    private EditText passTouchdownsEditText;
    private TextView passInterceptionsTextView;
    private EditText passInterceptionsEditText;
    private Button clearAllButton;
    private Button calculateRatingButton;
    private TextView qbRatingScoreTextView;

    // define instance variables
    private double passAttempts = 0;
    private double passCompletions = -1;
    private double passYards = -1;
    private double passTouchdowns =-1;
    private double passInterceptions =-1;
    private double qbScore =-1;

    // define sharedPreferences object
    private SharedPreferences savedValues;

    // This is the Override method for the OnClick Action Listener
    @Override
    public void onClick(View v) {
        // a switch statement to determine which button was clicked
        switch(v.getId())
        {
            // case for if the clear all button was pressed
            case R.id.clearAll:
                // these statements reset the EditText widgets to be empty,
                // The values for all of the instance variables are also reset
                passAttemptsEditText.setText("");
                passAttempts = 0;
                passCompletionsEditText.setText("");
                passCompletions = -1;
                passYardsEditText.setText("");
                passYards = -1;
                passTouchdownsEditText.setText("");
                passTouchdowns = -1;
                passInterceptionsEditText.setText("");
                passInterceptions = -1;
                qbRatingScoreTextView.setText("");
                break;
            // case for if the calculate button was pressed
            case R.id.calculate:
            // this checks to see if a value is entered into each of the fields.
            if(passCompletions < 0 || passInterceptions < 0 || passTouchdowns < 0 || passYardsEditText.getText().toString().trim().length() == 0)
            {
                // a toast that is used as an error message.  each statement has a custom toast for the scenario described in the if statement
                Toast.makeText(this, "All Fields Must Have A Valid Value",
                        Toast.LENGTH_LONG).show();
            }else if(passCompletions > passAttempts)
            {
                Toast.makeText(this, "Can Not Have More Completions Than Attempts",
                        Toast.LENGTH_LONG).show();
            }
            else if(passAttempts <1)
            {
                Toast.makeText(this, "Attempts Must Be Greater Then 0",
                        Toast.LENGTH_LONG).show();
            }
            else if(passTouchdowns > passCompletions)
            {
                Toast.makeText(this, "Can Not Have More Passing TouchDowns Than Completions",
                        Toast.LENGTH_LONG).show();
            }
            else if(passInterceptions > passAttempts)
            {
                Toast.makeText(this, "Can Not Have More Interceptions Than Attempts",
                        Toast.LENGTH_LONG).show();
            }
            else if((passInterceptions + passTouchdowns) > passAttempts)
            {
                Toast.makeText(this, "Can Not Have More Interceptions And Touchdowns Than Attempts",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                // calls the calculate and display method
                calculateAndDisplay();
            }
                break;
        }
    }

    // this is the OnEditorAction listener method
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        // this if statement checks to see which action occured
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO
                || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_UNSPECIFIED
                || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_NONE)
        {
            // this switch checks to see which EditText was edited
            switch(v.getId()){
                // this is the case for the pass attempts edit text
                case R.id.passAttemptsEditText:
                    // these if statements check to see that the edit text is not empty
                    if(passAttemptsEditText.getText().toString().trim().length() != 0)
                    {
                        // these statements get the text from the edit text widget and parse it to a double
                        passAttempts = Double.parseDouble(passAttemptsEditText.getText().toString());
                    }
                    break;
                // this is the case for the pass complete edit text
                case R.id.passCompleteEditText:
                    if(passCompletionsEditText.getText().toString().trim().length() != 0)
                    {
                        passCompletions = Double.parseDouble(passCompletionsEditText.getText().toString());
                    }
                    break;
                // this is the case for the pass yard edit text
                case R.id.passYardEditText:
                    if(passYardsEditText.getText().toString().trim().length() != 0)
                    {
                        passYards = Double.parseDouble(passYardsEditText.getText().toString());
                    }
                    break;
                // this is the case for the pass touchdowns edit text
                case R.id.passTouchdownEditText:
                    if(passTouchdownsEditText.getText().toString().trim().length() != 0)
                    {
                        passTouchdowns = Double.parseDouble(passTouchdownsEditText.getText().toString());
                    }
                    break;
                // this is the case for the pass interceptions edit text
                case R.id.passInterceptionEditText:
                    if(passInterceptionsEditText.getText().toString().trim().length() != 0)
                    {
                        passInterceptions = Double.parseDouble(passInterceptionsEditText.getText().toString());
                    }
                    break;
            }
        }
            return false;
    }

    //  this is the onCreate method for the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_rating_calc);

        // get references to the widgets
        passAttemptsTextView = (TextView) findViewById(R.id.passAttemptsLabel);
        passAttemptsEditText = (EditText) findViewById(R.id.passAttemptsEditText);
        passCompletionsTextView = (TextView) findViewById(R.id.passCompleteLabel);
        passCompletionsEditText = (EditText) findViewById(R.id.passCompleteEditText);
        passYardsTextView = (TextView) findViewById(R.id.passYardLabel);
        passYardsEditText = (EditText) findViewById(R.id.passYardEditText);
        passTouchdownsTextView = (TextView) findViewById(R.id.passTouchdownLabel);
        passTouchdownsEditText = (EditText) findViewById(R.id.passTouchdownEditText);
        passInterceptionsTextView = (TextView) findViewById(R.id.passInterceptionLabel);
        passInterceptionsEditText = (EditText) findViewById(R.id.passInterceptionEditText);
        clearAllButton = (Button) findViewById(R.id.clearAll);
        calculateRatingButton = (Button) findViewById(R.id.calculate);
        qbRatingScoreTextView = (TextView) findViewById(R.id.qbRatingValue);

        // calls the action listener for each widget
        passAttemptsEditText.setOnEditorActionListener(this);
        passCompletionsEditText.setOnEditorActionListener(this);
        passYardsEditText.setOnEditorActionListener(this);
        passTouchdownsEditText.setOnEditorActionListener(this);
        passInterceptionsEditText.setOnEditorActionListener(this);
        clearAllButton.setOnClickListener(this);
        calculateRatingButton.setOnClickListener(this);

        // calls the on focus action listener for the edit text widgets
        passAttemptsEditText.setOnFocusChangeListener(this);
        passCompletionsEditText.setOnFocusChangeListener(this);
        passYardsEditText.setOnFocusChangeListener(this);
        passTouchdownsEditText.setOnFocusChangeListener(this);
        passInterceptionsEditText.setOnFocusChangeListener(this);
    }

    // this is the calculate and display method.  It used an expression found on wikipedia that calculates the quarterback passer rating
    public void calculateAndDisplay() {
        double a,b,c,d;
        a = (passCompletions/passAttempts - .3) * 5;
        if(a > 2.375 )
        {
            a = 2.375;
        }
        else if(a < 0)
        {
            a = 0;
        }
        b = (passYards/passAttempts -3 ) * .25;
        if(b > 2.375 )
        {
            b = 2.375;
        }
        else if(b < 0)
        {
            b = 0;
        }
        c = (passTouchdowns/passAttempts) *20;
        if(c > 2.375 )
        {
            c = 2.375;
        }
        else if(c < 0)
        {
            c = 0;
        }
        d = 2.375 - (passInterceptions/passAttempts * 25);
        if(d > 2.375 )
        {
            d = 2.375;
        }
        else if(d < 0)
        {
            d = 0;
        }
        qbScore = ((a+b+c+d)/6) * 100;

        qbRatingScoreTextView.setText("" + qbScore);
    }

    // this method is called in the onfocus change action listener.  It is used to hide the soft keyboard when the user clicks off of the edit text widget
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // this is the action listener method for On focus Change
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // checks to see if widget has focus
        if (!hasFocus) {
            // these if statements check to see that the edit text is not empty
            if(passAttemptsEditText.getText().toString().trim().length() != 0)
            {
                // these statements get the text from the edit text widget and parse it to a double
                passAttempts = Double.parseDouble(passAttemptsEditText.getText().toString());
            }
            if(passCompletionsEditText.getText().toString().trim().length() != 0)
            {
                passCompletions = Double.parseDouble(passCompletionsEditText.getText().toString());
            }
            if(passYardsEditText.getText().toString().trim().length() != 0)
            {
                passYards = Double.parseDouble(passYardsEditText.getText().toString());
            }
            if(passTouchdownsEditText.getText().toString().trim().length() != 0)
            {
                passTouchdowns = Double.parseDouble(passTouchdownsEditText.getText().toString());
            }
            if(passInterceptionsEditText.getText().toString().trim().length() != 0)
            {
                passInterceptions = Double.parseDouble(passInterceptionsEditText.getText().toString());
            }
            // calls the hideKeyboard method
            hideKeyboard(v);
        }
    }

}
