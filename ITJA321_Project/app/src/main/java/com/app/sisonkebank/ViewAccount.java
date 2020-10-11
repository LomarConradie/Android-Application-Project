// Author: Lomar Ungerer Conradie | DQ61ZP3G5 | ITJA321 | Project
package com.app.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This is the main implementation of the ViewAccount activity and all its related functions
 */
public class ViewAccount extends AppCompatActivity {
    //These are the global variables used in the ViewAccount class and they are instantiated here
    TextView H_name,H_surname,H_balance,H_savings;
    String HolderName, HolderSurname, HolderBalance, HolderSavings;

    /**
     * This is the onCreate method for the ViewAccount activity and runs once the
     * activity is called. This method displays all user data in TextViews and
     * uses the database methods to display the user data. An email variable is also
     * passed from the Home activity which allows the correct user data to be displayed.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        H_name = findViewById(R.id.HolderName_lbl);
        H_surname = findViewById(R.id.HolderSurname_lbl);
        H_balance = findViewById(R.id.HolderBalance_lbl);
        H_savings = findViewById(R.id.HolderSavings_lbl);

        SisonkeDBHelper sisonkeDBHelper = new SisonkeDBHelper(ViewAccount.this);
        final String email_result = getIntent().getExtras().getString("result");
        BankUser bankUser = sisonkeDBHelper.getUserDetails(email_result);
        HolderName = bankUser.getName();
        HolderSurname = bankUser.getSurname();
        HolderBalance = Integer.toString(bankUser.getBalance());
        HolderSavings = Integer.toString(bankUser.getSavings());

        H_name.setText("Account Holder Name: " + HolderName);
        H_surname.setText("Account Holder Surname: " + HolderSurname);
        H_balance.setText("Current Account Balance: R" + HolderBalance);
        H_savings.setText("Savings Account Balance: R" + HolderSavings);
    }
}