package fire.auth.com.fuelonwheels;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.*;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Myorder extends AppCompatActivity {


    RecyclerView mRecyclerView;

    FirebaseFirestore mFire;
    FirebaseAuth mAuth;

    List<Orders> mList;
    OrderAdapter mOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        mList = new ArrayList<>();
        mOrderAdapter = new OrderAdapter(mList);

        mRecyclerView = findViewById(R.id.mOrderList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mOrderAdapter);


                mAuth = FirebaseAuth.getInstance();
        mFire = FirebaseFirestore.getInstance();

        mFire.collection("orders").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for(DocumentChange documentChange: documentSnapshots.getDocumentChanges()){
                    if(documentChange.getType() == DocumentChange.Type.ADDED){
                        if(documentChange.getDocument().getString("uid").equals(mAuth.getCurrentUser().getUid())) {
                            mList.add(documentChange.getDocument().toObject(Orders.class));
                            mOrderAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Myorder.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });










    }
}
