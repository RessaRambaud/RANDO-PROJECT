package com.RandoDam.rando.ActivityClass;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.RandoDam.rando.Fragments.Hamburger_fragment;
import com.RandoDam.rando.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {
    EditText profile_Pseudo, profile_EmailId,profile_NumeroDePortable;
    Button modifier;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String UserId,EmailID;
   ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_Pseudo=findViewById(R.id.et_Profile_Pseudo);
        profile_EmailId=findViewById(R.id.et_Profile_EmailId);
        profile_NumeroDePortable=findViewById(R.id.et_Profile_NumeroDePortable);
        modifier=findViewById(R.id.btn_ModifyDetails);
        home=findViewById(R.id.img_home);


        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        UserId=fAuth.getCurrentUser().getUid();
        EmailID=fAuth.getCurrentUser().getEmail();

        DocumentReference documentReference=fstore.collection("Users").document(EmailID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                profile_Pseudo.setText(value.getString("Pseudo"));
                profile_EmailId.setText(value.getString("Email"));
                profile_NumeroDePortable.setText(value.getString("NumeroDePortable"));
            }
        });

    modifier.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          profile_Pseudo.getText().toString();
            FirebaseUser firebaseUser=fAuth.getCurrentUser();


        }
    });
        home.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Hamburger_fragment.class));
                finish();
            }
        });
    }
}