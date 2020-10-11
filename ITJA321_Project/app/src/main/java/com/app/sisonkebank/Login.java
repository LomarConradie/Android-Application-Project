// Author: Lomar Ungerer Conradie | DQ61ZP3G5 | ITJA321 | Project
package com.app.sisonkebank;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

/**
 * This is the main implementation of the Login activity and all its related functions
 */
public class Login extends AppCompatActivity {
    //These are the global variables used in the Login class and they are instantiated here
    Button log_btn;
    TextView reg_txt;
    EditText email_edt, password_edt;
    String email, password;

    /**
     * This is the onCreate method that runs when the activity is called and run.
     * The Login button onClick method allows the program to check the email
     * and password editText boxes and allow validation of the inserted data.
     * Additionally, a LoginCheck variable is then used to determine whether the
     * inserted values are correct. The SQLite database is called and values are
     * assigned to check if the user has inserted the correct login credentials.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_edt = findViewById(R.id.Email_edt);
        password_edt = findViewById(R.id.Password_edt);

        log_btn = findViewById(R.id.Login_btn);
        reg_txt = findViewById(R.id.register_txt);

        log_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                email = email_edt.getText().toString();
                password = password_edt.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(email.matches("")) {
                    Toast.makeText(Login.this, "Please fill in both the email and password fields", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!(email.matches(emailPattern))){
                    Toast.makeText(Login.this, "Incorrect email format", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.matches("")){
                    Toast.makeText(Login.this, "Please fill in both the email and password fields", Toast.LENGTH_SHORT).show();
                    return;
                }else if(password.trim().length()<5){
                    Toast.makeText(Login.this, "Password is too short", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    try{
                        SisonkeDBHelper sisonkeHelper = new SisonkeDBHelper(Login.this);
                        boolean loginCheck = sisonkeHelper.authUserLogin(email,password);
                        if(loginCheck == true){
                            Toast.makeText(Login.this, "You have successfully logged in", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Home.class);
                            intent.putExtra("result",email);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Login.this, "The email or password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception e){
                        Toast.makeText(Login.this, "Login error occurred", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        reg_txt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent int1 = new Intent(Login.this, Registration.class);
                startActivity(int1);
            }
        });
    }
}