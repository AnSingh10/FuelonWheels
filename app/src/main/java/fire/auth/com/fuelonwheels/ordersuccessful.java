package fire.auth.com.fuelonwheels;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ordersuccessful extends AppCompatActivity {

    TextView type,amount,location,dt,price;
    FirebaseFirestore mFire;
    FirebaseAuth mAuth;
    String currid, uid, lat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersuccessful);

        mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();

        currid = mAuth.getCurrentUser().getEmail();

        type = findViewById(R.id.type);
        amount = findViewById(R.id.amount);
        location = findViewById(R.id.location);
        dt = findViewById(R.id.dt);
        price = findViewById(R.id.price);

        final Bundle bundle = getIntent().getExtras();

        dt.setText(bundle.getString("Time"));
        type.setText(bundle.getString("type"));
        amount.setText(bundle.getString("amount"));
        location.setText(bundle.getString("location"));
        price.setText(bundle.getString("price"));

//        mFire.collection("orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot documentSnapshots) {
//                for(DocumentSnapshot doc: documentSnapshots.getDocuments()){
//                   if (doc.getString("uid").equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())
//                           &&
//                           doc.getString("latitude").equals(bundle.getString("latitude"))){
//                            dt.setText(String.valueOf(doc.getString("date")));
//                   }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//
//            }
//        });





    }
}
