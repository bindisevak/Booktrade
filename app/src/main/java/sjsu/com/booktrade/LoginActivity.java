package sjsu.com.booktrade;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sjsu.com.booktrade.beans.UserTO;
import sjsu.com.booktrade.util.BookTradeHttpConnection;
import sjsu.com.booktrade.util.BookTradeJSONParser;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private static final int REQUEST_READ_CONTACTS = 0;

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
    private EditText uEmail;
    private EditText uPassword;
    private View mProgressView;
    private View mLoginFormView;
    private Button mBtnLogin,mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        uEmail = (EditText) findViewById(R.id.email);


        uPassword = (EditText) findViewById(R.id.password);


        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }




    @Override
    public void onClick(View v) {
        if(v == mBtnLogin) {
            String uName = uEmail.getText().toString();
            String uPass = uPassword.getText().toString();


            UserAction uAction = new UserAction(this);
            uAction.execute(uName, uPass);
        } else if (v == mBtnRegister){
            redirectToRegistration();
        }
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
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(uInfo!=null) {
                //Intent mainAct = new Intent(context, LandingPageActivity.class);
                //mainAct.putExtra("UserInfo",uInfo);
                //startActivity(mainAct);
                Toast.makeText(getApplicationContext(), " Login Credentials are valid !!", Toast.LENGTH_LONG).show();
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

    /*private void startBarcodeScanner(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult intentScanningResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if(intentScanningResult != null){
            String scanContent = intentScanningResult.getContents();
            String scanFormat = intentScanningResult.getFormatName();
            Toast.makeText(this, "Format: "+scanFormat + "Result: "+ scanFormat, Toast.LENGTH_LONG).show();
        }

    }*/


}

