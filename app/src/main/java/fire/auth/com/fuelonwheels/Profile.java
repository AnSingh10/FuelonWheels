package fire.auth.com.fuelonwheels;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {

    TextView name,mail,phone;
    FirebaseAuth mAuth;
    FirebaseFirestore mFire;
    ProgressBar mProgress;
    int count = 0;
    String currid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();

        currid = mAuth.getCurrentUser().getEmail();

        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        phone = findViewById(R.id.phone);

        mFire.collection("UserData").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot doc : documentSnapshots.getDocuments()) {
                    if (doc.getString("email").equals(currid)) {
                        String cname = doc.getString("name");
                        String cmail = doc.getString("email");
                        String cphone = doc.getString("mobile");

                        name.setText(cname);
                        mail.setText(cmail);
                        phone.setText(cphone);

                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mFire.collection("Post").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for(DocumentSnapshot doc: documentSnapshots.getDocuments()){
                    if(doc.getString("email").toString().equals(currid)) {
                        count++;
                    }
                }
            }
        });

    }
}
