package com.asad.icma;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener  {

    /*Button signup;
    Button selectImage;
    ImageView teacher_picture;
    private int REQUEST_CODE = 1;

    DatabaseHelper helper = new DatabaseHelper(this);

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z._-]+\\.+[a-z]+";*/

    private final AppCompatActivity activity = SignupActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutUsername;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextUsername;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initViews();
        initListeners();
        initObjects();

        /*selectImage = findViewById(R.id.selectImage_btn);
        teacher_picture = findViewById(R.id.teacher_image);

        signup = findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onSignUpClick(view);

            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "Select Picture"), REQUEST_CODE );
                teacher_picture.setVisibility(View.VISIBLE);
            }
        });*/
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutUsername = findViewById(R.id.textInputLayoutUsername);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextUsername = findViewById(R.id.textInputEditTextUsername);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
    }

    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite(){
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            //user.setUsername(textInputEditTextUsername.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextUsername.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

    /*public void onSignUpClick(View v){

        if (v.getId() == R.id.signup_btn)
        {
            EditText name = findViewById(R.id.signup_name);
            EditText email = findViewById(R.id.signup_email);
            EditText uname = findViewById(R.id.signup_username);
            EditText pass1 = findViewById(R.id.signup_password1);
            EditText pass2 = findViewById(R.id.signup_password2);

            String nameStr = name.getText().toString();
            String emailStr = email.getText().toString();
            String unameStr = uname.getText().toString();
            String pass1Str = pass1.getText().toString();
            String pass2Str = pass2.getText().toString();

            //User side validation
            if (TextUtils.isEmpty(nameStr))
            {
                Toast.makeText(SignupActivity.this, "Please enter your Full Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!emailStr.matches(emailPattern))
            {
                Toast.makeText(SignupActivity.this,"Please enter valid email address",Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(unameStr))
            {
                Toast.makeText(SignupActivity.this, "Please enter your User Name", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pass1.length() < 4)
            {
                Toast.makeText(SignupActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass1Str.equals(pass2Str))
            {
                Toast pass = Toast.makeText(SignupActivity.this, "Passwords don't match!", Toast.LENGTH_SHORT);
                pass.show();
            }
            else
            {
                User u = new User();
                u.setName(nameStr);
                u.setEmail(emailStr);
                u.setUname(unameStr);
                u.setPass(pass1Str);

                helper.insertUser(u);

                Toast.makeText(SignupActivity.this, "Welcome Mr. " + unameStr, Toast.LENGTH_SHORT).show();

                Intent in = new Intent(SignupActivity.this, TeacherPanelActivity.class);
                //in.putExtra("email", emailStr);
                in.putExtra("login_username", unameStr);
                startActivity(in);
                finish();
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            Uri uri = data.getData();

            try
            {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                teacher_picture.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }*/
}
