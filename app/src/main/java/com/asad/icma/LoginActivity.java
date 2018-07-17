package com.asad.icma;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String emailToRemember, passwordToRemember;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;

    /*ScrollView rootview;

    DatabaseHelper helper = new DatabaseHelper(this);

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+";

    TextView goToSignup, forgotPassword;
    Button login;
    EditText email_view, password_view;*/

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;
    private AppCompatTextView textViewLinkForgotPassword;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    SimpleDateFormat df = new SimpleDateFormat("HH:mm a");

    SimpleDateFormat mts = new SimpleDateFormat("15:42");

    long currentTime;
    long TimeStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            textInputEditTextEmail.setText(loginPreferences.getString("email", ""));
            textInputEditTextPassword.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        Calendar c = Calendar.getInstance();

        String formattedTime = df.format(c.getTime());

        String StartTime = mts.format(c.getTime());

        /*currentTime = parseTime(formattedTime).getTime();
        TimeStart = parseTime(StartTime).getTime();

        if (TimeStart < currentTime )
        {
            nestedScrollView.setBackgroundColor(Color.CYAN);
        }
        else
        {
            nestedScrollView.setBackgroundColor(Color.WHITE);
        }*/

        //nestedScrollView.setBackgroundColor(Color.parseColor("#01ff90"));

        /*Thread myThread = new Thread(){

            @Override
            public void run(){
                try {
                    sleep(9000);
                    nestedScrollView.setBackgroundColor(Color.parseColor("#01ff90"));
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
        myThread.*/

        /*new CountDownTimer(5000, 50) {

            @Override
            public void onTick(long arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFinish() {
                nestedScrollView.setBackgroundColor(Color.CYAN);
            }
        }.start();*/
    }

    private void initViews(){

        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        nestedScrollView = findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = findViewById(R.id.email_textinput_layout);
        textInputLayoutPassword = findViewById(R.id.password_textinput_layout);

        textInputEditTextEmail = findViewById(R.id.login_email_textinput);
        textInputEditTextPassword = findViewById(R.id.login_password_textinput);

        appCompatButtonLogin = findViewById(R.id.login_btn);

        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);
        textViewLinkForgotPassword = findViewById(R.id.forgotPassword);
    }

    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
        textViewLinkForgotPassword.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login_btn:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.forgotPassword:
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(intent);
                break;
        }
    }

    private void verifyFromSQLite(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textInputEditTextEmail.getWindowToken(), 0);

            emailToRemember = textInputEditTextEmail.getText().toString();
            passwordToRemember = textInputEditTextPassword.getText().toString();

            if (saveLoginCheckBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("email", emailToRemember);
                loginPrefsEditor.putString("password", passwordToRemember);
                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }

            Intent in = new Intent(LoginActivity.this, TeacherPanelActivity.class);
            //in.putExtra("login_username", uname);
            //in.putExtra("email", em);
            startActivity(in);
            finish();


        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }


    }

    private void emptyInputEditText(){
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }

    /*private void showPasswordResetDialog() {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        dialog.setTitle("RESET PASSWORD");
        final LayoutInflater inflater = LayoutInflater.from(this);
        View forgetPassword_layout = inflater.inflate(R.layout.activity_forgetpassword1, null);
        dialog.setView(forgetPassword_layout);
        //final MaterialEditText gmkEmail= (MaterialEditText) forgetPassword_layout.findViewById(R.id.edtEmail);

        dialog.setPositiveButton("Reset Password",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                *//*FirebaseAuth.getInstance().sendPasswordResetEmail(gmkEmail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(PassengerLoginActivity.this, "Password Reset Successfully", Toast.LENGTH_LONG).show();
                                }

                                else{
                                    Toast.makeText(PassengerLoginActivity.this, "Password Reset Error", Toast.LENGTH_LONG).show();
                                }
                            }
                        });*//*
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }

        });
        dialog.show();
    }*/

    private Date parseTime(String time) {

        try {
            return df.parse(time);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }


    }
}
