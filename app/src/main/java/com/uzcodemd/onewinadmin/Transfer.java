package com.uzcodemd.onewinadmin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Transfer extends AppCompatActivity {

    private FirebaseFirestore db;
    private DocumentReference documentReference;
    ListView listtransfer;
    String nikname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer);

        setTitle("\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\n");
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            nikname = (String) b.get("name");
        }
        db = FirebaseFirestore.getInstance();
        listtransfer = findViewById(R.id.listtransfer);
        readFirebase();

    }
    private void readFirebase(){
        documentReference = db.document("otkazma/" + nikname);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String history = documentSnapshot.getString("NET_KASH");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String[] values = history.split(",");
                            List<String> list = new ArrayList<>();
                            list.addAll(Arrays.asList(values));
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Transfer.this,
                                    android.R.layout.simple_list_item_1, list);
                            listtransfer.setAdapter(adapter);
                            //progressBarotkazma.setVisibility(View.GONE);
                        }
                    }, 2000);
                }
            }
        });
    }
}
