// Author: Lomar Ungerer Conradie | DQ61ZP3G5 | ITJA321 | Project
package com.app.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the main implementation of the Registration activity and all its related functions
 */
public class Registration extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{
    //These are the global variables used in the Registration class and they are instantiated here
    TextView exist_txt;
    Button createAcc_btn;
    EditText reg_name, reg_surname, reg_email, reg_password, reg_mobile;
    String gender="",r_name, r_surname, r_email, r_pass, r_mobile;
    RadioGroup radioGroup;

    /**
     * This is the onCreate method for the Registration class.
     * The goal of this class is to allow users to create and register a
     * new user account with the related details required by the database.
     * There are 2 onclicklisteners which are used to register when the
     * button and textview are clicked. The button implements functionality
     * that checks the TextEdit boxes inserted data to ensure that validation
     * occurs and that all inserted data is correct. If the data is correct, the
     * program will link to the database, add the new data and check if the insert
     * query was successful.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        reg_name = findViewById(R.id.RegName_edt);
        reg_surname = findViewById(R.id.RegSurname_edt);
        reg_email = findViewById(R.id.RegEmail_edt);
        reg_password = findViewById(R.id.RegPassword_edt);
        reg_mobile = findViewById(R.id.RegMobile_edt);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        exist_txt = findViewById(R.id.ExistingAccount_txt);
        createAcc_btn = findViewById(R.id.createaccount_btn);

        exist_txt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int1 = new Intent(Registration.this, Login.class);
                startActivity(int1);
            }
        });

        createAcc_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                r_name = reg_name.getText().toString();
                r_surname = reg_surname.getText().toString();
                r_email = reg_email.getText().toString();
                r_pass = reg_password.getText().toString();
                r_mobile = reg_mobile.getText().toString();
                //validation of the registration starts here
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(r_name.matches("")){
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else if(r_surname.matches("")){
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else if(r_email.matches("")) {
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!(r_email.matches(emailPattern))){
                    Toast.makeText(Registration.this, "Incorrect email format", Toast.LENGTH_SHORT).show();
                    return;
                }else if(r_pass.matches("")) {
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else if(r_pass.trim().length()<5){
                    Toast.makeText(Registration.this, "Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }else if(r_mobile.matches("")){
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else if(r_mobile.trim().length()<10){
                    Toast.makeText(Registration.this, "Mobile number is too short", Toast.LENGTH_SHORT).show();
                    return;
                }else if(gender.matches("")){
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    BankUser bankUser;
                    try {
                        bankUser = new BankUser(-1,r_name,r_surname, r_email, r_pass, Integer.parseInt(r_mobile),gender,2500,0);
                        SisonkeDBHelper sisonkeHelper = new SisonkeDBHelper(Registration.this);
                        boolean check = sisonkeHelper.checkEmail(r_email);
                        if(check == true){
                            Toast.makeText(Registration.this, "This email already exists. Please enter another.", Toast.LENGTH_SHORT).show();
                        }else{
                            sisonkeHelper.addUser(bankUser);
                            Toast.makeText(Registration.this, "Account creation was successfull", Toast.LENGTH_SHORT).show();
                            Intent int1 = new Intent(Registration.this, Login.class);
                            startActivity(int1);
                        }
                    }catch(Exception e){
                        Toast.makeText(Registration.this,"Account creation was not successfull",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }

    /**
     * This method is used to determine which radio button is checked and assigns a string value
     * dependant on the checked radio button.
     * @param radioGroup - this is the radiogroup component used to group and host the two gender radio buttons
     * @param checkedId - the int checked id is used in a switch to determine which radio button is selected
     *                  if the button is male_rbtn, then the String called gender is assigned male. If the
     *                  button selected is the female_rbtn, then the gender is female.
     */
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId){
            case R.id.male_rbtn:
                gender="male";
                break;
            case R.id.female_rbtn:
                gender="female";
                break;
        }
    }
}