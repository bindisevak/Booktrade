package sjsu.com.booktrade;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.InjectView;

import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_READ_CONTACTS = 0;
    private Location presentLocation;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;

    // UI references.
//    private EditText uEmail;
//    private EditText uPassword;
//    private View mProgressView;
//    private View mLoginFormView;
//    private Button mBtnLogin,mBtnRegister;
    @InjectView(R.id.input_email) EditText uEmail;
    @InjectView(R.id.input_password) EditText uPassword;
    @InjectView(R.id.btn_login) Button mBtnLogin;
    @InjectView(R.id.link_signup) TextView mBtnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_login);
        // Set up the login form.
        setContentView(R.layout.activity_loginreg);
        ButterKnife.inject(this);
//        uEmail = (EditText) findViewById(R.id.email);
//

//        uPassword = (EditText) findViewById(R.id.password);
//
//
//        mBtnLogin = (Button) findViewById(R.id.btn_login);
//        mBtnRegister = (Button) findViewById(R.id.btn_register);
//        mBtnLogin.setOnClickListener(this);
//        mBtnRegister.setOnClickListener(this);
//
//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();


            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                redirectToRegistration();
            }
        });
    }


    public void login() {

        String uName = uEmail.getText().toString();
        String uPass = uPassword.getText().toString();


        UserAction uAction = new UserAction(this);
        uAction.execute(uName, uPass);

    }


    UserTO uInfo;
    private class UserAction extends AsyncTask<String,String,String> {
        Context context;

        private UserAction(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            BookTradeHttpConnection conn = new BookTradeHttpConnection();
            uInfo = conn.loginUser(params[0],params[1]);
            Log.d("User login ","User login : Uemail" + params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            System.out.println("User login : Uemail" + uInfo.getEmailId());
//            System.out.println("User login : Upass" + uInfo.getPassword());
            if(uInfo!=null) {
                Intent mainAct = new Intent(context, LandingPage.class);
                mainAct.putExtra("UserInfo",uInfo);
                Log.d("String", "I am here");
                mainAct.putExtra("firstName", uInfo.getFirstName());
                mainAct.putExtra("lastName", uInfo.getLastName());
                mainAct.putExtra("userId", uInfo.getUserId());
                startActivity(mainAct);

                //Toast.makeText(getApplicationContext(), " Login Credentials are valid !!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Invalid Login Credentials!!", Toast.LENGTH_LONG).show();
            }

        }


    }

    private void redirectToRegistration(){

        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);

    }



}

