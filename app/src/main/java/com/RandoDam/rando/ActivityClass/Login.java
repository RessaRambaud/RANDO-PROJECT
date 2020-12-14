package com.RandoDam.rando.ActivityClass;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.RandoDam.rando.Fragments.Hamburger_fragment;
import com.RandoDam.rando.Fragments.MainFragment_Acceuil;
import com.RandoDam.rando.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    public static final int GOOGLE_SIGN_IN_CODE = 1000;
    SignInButton signIn;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;

    EditText l_email,l_password;
    Button login;
    ProgressBar progressbar_login;
    FirebaseAuth fAuth;
    TextView mdp_Oublie;
    private static final String TAG = "Reset";

    FirebaseFirestore fstore;


//code for google sign in

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==GOOGLE_SIGN_IN_CODE){
    Task<GoogleSignInAccount> signInTask= GoogleSignIn.getSignedInAccountFromIntent(data);

    try {
    GoogleSignInAccount signInAcc=signInTask.getResult(ApiException.class);
    // Toast.makeText(this,"Compte est Connecté sur Google Account ",Toast.LENGTH_SHORT).show();
    startActivity(new Intent(this,MainFragment_Acceuil.class));
    } catch (ApiException e) {
    e.printStackTrace();
    }
    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       //code for google sign in

        signIn=findViewById(R.id.signIn);

                 gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
                 signInClient= GoogleSignIn.getClient(this,gso);
                 GoogleSignInAccount signInAccount=GoogleSignIn.getLastSignedInAccount(this);
                 if(signInAccount!=null)
                 {
                 Toast.makeText(this,"User is logged in Already",Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(this,MainFragment_Acceuil.class));

                 }
                 signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent sign=signInClient.getSignInIntent();
                startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
                }
                });


        l_email=findViewById(R.id.et_Login_emailID);
        l_password=findViewById(R.id.et_Login_motDePasse);
        progressbar_login=findViewById(R.id.pb_progressBarLogin);
        login=findViewById(R.id.btn_Login);
        mdp_Oublie=findViewById(R.id.tv_MDP_Oublie);

        //to get current instance from the database in the firebase
        fAuth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String email=l_email.getText().toString();
            Log.i(TAG, "onClick: "+email);
            String password=l_password.getText().toString();
            Log.i(TAG, "onClick: "+password);

            if(TextUtils.isEmpty(email))
            {
                l_email.setError("Verifiez le email Id");
                return;
            }
            if(TextUtils.isEmpty(password))
            {
                l_password.setError("Verifiez le Mot de passe");
                return;
            }
            if(password.length()<6)
            {
                l_password.setError("Verifiez votre mot de passe , c'est moins de 6 charactres");
                return;
            }
            progressbar_login.setVisibility(View.VISIBLE);

            //Authenticate the user

            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful())
                {
                    Toast.makeText(Login.this,"User Logged In",Toast.LENGTH_SHORT).show();
                    Log.i("debug", " is successful");
                    startActivity(new Intent(getApplicationContext(), Hamburger_fragment.class));

                }else
                {
                    // Toast.makeText(Login.this,"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(Login.this,"Verifiez le Email ID et Mot de passe",Toast.LENGTH_SHORT).show();

                }
                progressbar_login.setVisibility(View.GONE);

            });

        }
    });


        //reset Password

        mdp_Oublie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetMail=new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog=new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Mot De Passe ?");
                passwordResetDialog.setMessage(" Votre Email ici..");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("debug", "Reset");

                        //extract the email
                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Reset Link is send to your email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Error ! Reset Link is not Sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });


    }



}