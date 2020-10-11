// Author: Lomar Ungerer Conradie | DQ61ZP3G5 | ITJA321 | Project
package com.app.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the main implementation of the Home activity and all its related functions
 */
public class Home extends AppCompatActivity {
    //These are the global variables used in the Home class and they are instantiated here
    private Button viewAcc_btn, transferAcc_btn,logout;
    EditText l_email;
    TextView welcome_view;
    String name;

    @Override
    /**
     * This is the onCreate method that runs when the activity is built
     * The components on the Home page are assigned to a instanced variable and
     * used to perform functions related to the Home page, such as implementing
     * intents to load other activities and display the logged in users' name.
     * There are three onClick methods within this main method that allow functionality
     * when the buttons on the Home page are clicked. The Intents used also pass the
     * users' email to other activities to allow sufficient identification of the
     * specific user that has logged in.
     * The Logout button has a toast message that notifies the user that they have logged out.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        welcome_view = findViewById(R.id.welcome_lbl);
        l_email = findViewById(R.id.Email_edt);
        SisonkeDBHelper sisonkeDBHelper = new SisonkeDBHelper(Home.this);
        final String email_result = getIntent().getExtras().getString("result");
        BankUser bankUser = sisonkeDBHelper.getUserDetails(email_result);
        name = bankUser.getName();
        welcome_view.setText("Welcome " + name);

        viewAcc_btn = findViewById(R.id.viewbalance_btn);
        transferAcc_btn = findViewById(R.id.transfer_btn);
        logout = findViewById(R.id.logout_btn);

        viewAcc_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int1 = new Intent(Home.this, ViewAccount.class);
                int1.putExtra("result",email_result);
                startActivity(int1);
            }
        });

        transferAcc_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int2 = new Intent(Home.this, Transfer.class);
                int2.putExtra("result",email_result);
                startActivity(int2);
            }
        });

        logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int3 = new Intent(Home.this, Login.class);
                int3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(int3);
                Toast.makeText(getApplicationContext(),"You have logged out.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}