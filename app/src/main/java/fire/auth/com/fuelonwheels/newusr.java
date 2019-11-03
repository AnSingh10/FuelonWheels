package fire.auth.com.fuelonwheels;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class newusr extends AppCompatActivity {

    Button signupBtn, signupAccBtn;
    EditText Name, email, password,mobile;
    FirebaseAuth sAuth;
    FirebaseFirestore mFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newusr);

       Name = findViewById(R.id.name);
       email =findViewById(R.id.email);
       password =findViewById(R.id.password);
       signupBtn =findViewById(R.id.signupBtn);
       signupAccBtn =findViewById(R.id.signupAccBtn);
       mobile =findViewById(R.id.mobile);

       sAuth =FirebaseAuth.getInstance();
       mFire = FirebaseFirestore.getInstance();


    }
    void signin(View v){
        final String Email =email.getText().toString();
        String Pass= password.getText().toString();
        String phone = mobile.getText().toString();

        sAuth.createUserWithEmailAndPassword(Email,Pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Map<String, String> userData = new HashMap<>();
                userData.put("mobile",mobile.getText().toString());
                userData.put("name", Name.getText().toString());
                userData.put("email", Email);

                mFire.collection("UserData")
                .add(userData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        startActivity(new Intent(newusr.this, MainActivity.class));
                        Toast.makeText(newusr.this, "User Created Successfully..", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(newusr.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(newusr.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    void acc(View v){
        startActivity(new Intent(newusr.this,MainActivity.class));
    }
}
