package com.RandoDam.rando.ActivityClass;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.RandoDam.rando.Fragments.Hamburger_fragment;
import com.RandoDam.rando.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    /**
     * declarations of the variables in the register page
     **/

    EditText Pseudo, EmailID, MotDePasse, NumeroDePortable;
    Button Registre;
    ProgressBar progressBar;
    String userId;
    /**
     * to store in the fire store
     **/
    FirebaseFirestore fstore;

    FirebaseAuth fAuth;

    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Pseudo = findViewById(R.id.et_Pseudo);
        EmailID = findViewById(R.id.et_EmailID);
        MotDePasse = findViewById(R.id.et_MotDePasse);
        NumeroDePortable = findViewById(R.id.et_NumeroDePortable);
        Registre = findViewById(R.id.btn_Registre);
        progressBar = findViewById(R.id.pb_progressBar);


        /**to get current instance from the database in the firebase**/
        fAuth = FirebaseAuth.getInstance();
      


        /** to check whether the user is already logged in or not
         if the user is logged in it will go to the acceuil page of the application **/
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Hamburger_fragment.class));
            finish();

        }

        fstore = FirebaseFirestore.getInstance();
        Registre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "BTN click: ");

                String pseudo = Pseudo.getText().toString();
                String email = EmailID.getText().toString();
                String password = MotDePasse.getText().toString();
                String phone = NumeroDePortable.getText().toString();

                Log.i(TAG, "pseudo: " +pseudo);
                Log.i(TAG, "emailid: " +email);
                
                saveToFireStore(pseudo,email,password,phone);


                /**Empty is user for validation **/

                if (TextUtils.isEmpty(pseudo)) {
                    Pseudo.setError("Pseudo est Obligatoire");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    EmailID.setError("Email est Obligatoire");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    MotDePasse.setError("Mot de Passe est Obligatoire");
                    return;
                }

                if (password.length() < 6) {
                    MotDePasse.setError("Le Mot de passe doit etre minimum de 6 lettres");
                    return;
                }
                if (phone.length() < 9) {
                    NumeroDePortable.setError("Le numero de portable doit etre minimum de 6 lettres");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);


                /**Register the user in the firebase FireStore **/


            }
        });
    }


 private void saveToFireStore(String pseudo, String email,String password, String phone) {

 fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {


     if (!pseudo.isEmpty() && !email.isEmpty()) {
         Log.i(TAG, "saveToFireStore: next to hash map" + pseudo);
         /**Hash Map **/

         Map<String, Object> user = new HashMap<>();
         user.put("Pseudo", pseudo);
         user.put("Email", email);
         user.put("Password", password);
         user.put("NumeroDePortable", phone);

         Log.i(TAG, "saveToFireStore: next");

         fstore.collection("Users").document(email).set(user)
                 .addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()) {
                             Toast.makeText(Register.this, "Data Saved", Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(), Hamburger_fragment.class));

                         }
                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();
             }
         });


     } else {
         Toast.makeText(this, "Empty Fields are not allowed", Toast.LENGTH_SHORT).show();
         progressBar.setVisibility(View.GONE);
     }
 });
    }

}

