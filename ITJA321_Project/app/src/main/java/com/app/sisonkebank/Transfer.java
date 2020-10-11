// Author: Lomar Ungerer Conradie | DQ61ZP3G5 | ITJA321 | Project
package com.app.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * This is the main implementation of the Transfer activity and all its related functions
 */
public class Transfer extends AppCompatActivity {
    //These are the global variables used in the Transfer class and they are instantiated here
    TextView T_balance,T_savings;
    String TransferBalance, TransferSavings, TransferChoice, email_result;
    EditText amount;
    Spinner spin;
    Button TransferButton;
    Integer Int_amount, Int_balance, Int_Savings;
    BankUser bankUser;
    SisonkeDBHelper sisonkeDBHelper;

    /**
     * This is the onCreate method that runs once the Transfer activity is called.
     * The method allows the activity to obtain and assign the components and their
     * values from the layout xml file (This occurs for each activity).
     * The values contained within the spinner are also instantiated here, as well as
     * a itemselectedlistener that determines which spinner value has been selected and
     * an arrayadapter to insert the values in the spinner. The transfer buttons' functionality
     * is also added within the onCreate method.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        T_balance = findViewById(R.id.CurrentBalace_lbl);
        T_savings = findViewById(R.id.TransferSavings_lbl);
        amount = findViewById(R.id.amount_edt);
        TransferButton = findViewById(R.id.transfer_btn);
        spin = findViewById(R.id.transferFrom_cmbx);

        sisonkeDBHelper = new SisonkeDBHelper(Transfer.this);
        email_result = getIntent().getExtras().getString("result");
        bankUser = sisonkeDBHelper.getUserDetails(email_result);

        TransferBalance = Integer.toString(bankUser.getBalance());
        T_balance.setText("Current Account Balance: R" + TransferBalance);

        TransferSavings = Integer.toString(bankUser.getSavings());
        T_savings.setText("Savings Account Balance: R" + TransferSavings);

        ArrayList<String> spinnerList = new ArrayList<>();
        spinnerList.add("Current to Savings");
        spinnerList.add("Savings to Current");
        ArrayAdapter<String> arrAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerList);
        arrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(arrAdapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransferChoice = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TransferButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String empty = amount.getText().toString();
                if(empty.matches("")){
                    Toast.makeText(Transfer.this, "Please enter the amount you wish to transfer", Toast.LENGTH_SHORT).show();
                }else{
                    transferMethod();
                    TransferBalance = Integer.toString(bankUser.getBalance());
                    T_balance.setText("Current Account Balance: R" + TransferBalance);
                    TransferSavings = Integer.toString(bankUser.getSavings());
                    T_savings.setText("Savings Account Balance: R" + TransferSavings);
                }
            }
        });
    }

    /**
     * As mentioned in the project pdf, a method is required for the transaction functionality and the
     * validation of these transactions.
     * The transferMethod() implements validation, links to the database method called updateBalance
     * and checks if the selected values are correct. Integers are used to obtain the user balance and
     * savings account balance and these updated values to the database to be updated once a
     * successful transaction has taken place.
     */
    public void transferMethod(){
        Int_amount = Integer.parseInt(amount.getText().toString());
        Int_balance = bankUser.getBalance();
        Int_Savings = bankUser.getSavings();
        if(TransferChoice == "Current to Savings"){
            if(Int_balance < Int_amount){
                Toast.makeText(Transfer.this, "You do not have the available funds to make this transaction", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Int_balance = Int_balance - Int_amount;
                Int_Savings = Int_Savings + Int_amount;
                boolean result = sisonkeDBHelper.updateBalance(Int_balance, Int_Savings, email_result);
                if(result = true){
                    Toast.makeText(Transfer.this, "Transfer was successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Transfer.this, "Transfer was not successful", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(TransferChoice == "Savings to Current"){
            if(Int_Savings < Int_amount){
                Toast.makeText(Transfer.this, "You do not have the available funds to make this transaction", Toast.LENGTH_SHORT).show();
                return;
            }else{
                Int_balance = Int_balance + Int_amount;
                Int_Savings = Int_Savings - Int_amount;
                boolean result2 = sisonkeDBHelper.updateBalance(Int_balance, Int_Savings, email_result);
                if(result2 = true){
                    Toast.makeText(Transfer.this, "Transfer was successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Transfer.this, "Transfer was not successful", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}