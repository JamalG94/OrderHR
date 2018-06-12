package com.example.jamal.orderhr_noninstant.Activities.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jamal.orderhr_noninstant.Activities.Main.MainActivity;
import com.example.jamal.orderhr_noninstant.Datastructures.Admin;
import com.example.jamal.orderhr_noninstant.Datastructures.Staff;
import com.example.jamal.orderhr_noninstant.Datastructures.Student;
import com.example.jamal.orderhr_noninstant.Datastructures.SuperUser;
import com.example.jamal.orderhr_noninstant.Datastructures.UnauthenticatedUser;
import com.example.jamal.orderhr_noninstant.API.IO;
import com.example.jamal.orderhr_noninstant.R;
import com.example.jamal.orderhr_noninstant.Session.Session;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class GoogleLoginActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private Session session;
    IO _IO;
    private static int RC_SING_IN = 1;

    SignInButton signInButton;
    TextView greetText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(onClickSignIn);

        session = new Session(this);

        greetText = findViewById(R.id.greetText);
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        UpDateUI(account);
    }

    private void UpDateUI(GoogleSignInAccount account){
        if(account!= null){
            SuperUser user = checkUserAuthentication(account.getEmail());
            if(user.TypeOfUser() == "UnauthenticatedUser"){
                user = CreateNewUser(account.getEmail(), account.getDisplayName(), account.getGivenName(), account.getFamilyName());
                session.setUser(user);
            }
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    Button.OnClickListener onClickSignIn = new Button.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
            }
        }
    };


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SING_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SING_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            //TODO Parse this to the database as student account;
            CreateNewUser(account.getEmail(), account.getDisplayName(), account.getGivenName(), account.getFamilyName());
            UpDateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("HandleSignIn", "signInResult:failed code=" + e.getStatusCode());
            UpDateUI(null);
        }
    }


    private SuperUser CreateNewUser(String email, String username, String firstname, String lastname){
        _IO = IO.GetInstance();
        String json = String.format("{\"username\":\"%s\", \"email\":\"%s\", \"firstname\":\"%s\", \"lastname\":\"%s\"}", username, email, firstname, lastname);
        _IO.DoPostRequestToAPIServer(json, "http://markb.pythonanywhere.com/loginauth/", this);
        return new Student();
    }

    private SuperUser checkUserAuthentication(String email) {
        SuperUser returneduser = null;
        _IO = IO.GetInstance();
        String json = String.format("{\"username\":\"%s\"}", email);
        String result = _IO.DoPostRequestToAPIServer(json, "http://markb.pythonanywhere.com/loginauth/", this);
        try{
            if(result.equals("This user could not be found")){
                returneduser = new UnauthenticatedUser();
            }
            else{
                JSONObject convertedresult = new JSONObject(result);
                boolean resultingequation = convertedresult.getBoolean("isauthenticated");
                if(resultingequation ){
                    if(convertedresult.getBoolean("issuperuser")){
                        returneduser=new Admin();
                    }
                    else if(convertedresult.getBoolean("isstaff")){
                        returneduser=new Staff();
                    }
                    else{
                        returneduser = new Student();
                    }
                }
            }

        }catch(JSONException jsonexception){
            Toast test = Toast.makeText(this,"SERVER RETURN ERROR",Toast.LENGTH_LONG);
            test.show();
        }
        return returneduser;
    }

}
