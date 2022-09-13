package com.uzcodemd.onewinadmin;

import static com.uzcodemd.onewinadmin.Constants.TOPIC;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.uzcodemd.onewinadmin.api.ApiUtilities;
import com.uzcodemd.onewinadmin.model.NatificationData;
import com.uzcodemd.onewinadmin.model.PushNotification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin extends AppCompatActivity {

    private FirebaseFirestore db;
    private DocumentReference documentReference;

    String xabar, yangilik, message, dollar,
            euro, rubl, sum, foiz11, foiz22, foiz33, foiz44,name1,name2,name3,name4,promocode;

    private EditText edixabar,ediyangilik,edidollar,edieuro,edirubl,edisom,ediname1,ediname2,
            ediname3,ediname4,edifoiz1,edifoiz2,edifoiz3,edifoiz4,edipromocode;

    private CheckBox checkBox;
    private Button btnsavejonat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        setTitle("\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\n");
        Objects.requireNonNull(getSupportActionBar()).hide();
        db = FirebaseFirestore.getInstance();
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        edixabar = findViewById(R.id.edixabar);
        ediyangilik = findViewById(R.id.ediyangilik);
        edidollar = findViewById(R.id.edidollar);
        edieuro = findViewById(R.id.edieuro);
        edirubl = findViewById(R.id.edirubl);
        edisom = findViewById(R.id.edisom);
        ediname1 = findViewById(R.id.ediname1);
        ediname2 = findViewById(R.id.ediname2);
        ediname3 = findViewById(R.id.ediname3);
        ediname4 = findViewById(R.id.ediname4);
        edifoiz1 = findViewById(R.id.edifoiz1);
        edifoiz2 = findViewById(R.id.edifoiz2);
        edifoiz3 = findViewById(R.id.edifoiz3);
        edifoiz4 = findViewById(R.id.edifoiz4);
        edipromocode = findViewById(R.id.edipromocode);
        checkBox = findViewById(R.id.checkBox);
        btnsavejonat = findViewById(R.id.btnsavejonat);
        firebaseread();

        btnsavejonat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()){
                    funupdatefirebase();
                    PushNotification notification = new PushNotification(new NatificationData("xabar", message), TOPIC);
                    sendNotification(notification);
                }else {
                    funupdatefirebase();
                }
            }
        });

    }
    private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "ishladi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "xato", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void firebaseread() {
        documentReference = db.document("Admin/netcash");
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            sum = documentSnapshot.getString("som");
            rubl = documentSnapshot.getString("rubl");
            dollar = documentSnapshot.getString("dollar");
            euro = documentSnapshot.getString("euro");
            yangilik = documentSnapshot.getString("YANAGILIKLAR");
            xabar = documentSnapshot.getString("XABARLAR");

            edidollar.setText(dollar);
            edieuro.setText(euro);
            edirubl.setText(rubl);
            edisom.setText(sum);
            edixabar.setText(xabar);
            ediyangilik.setText(yangilik);
            //progressBaradmin.setVisibility(View.GONE);

        });
        documentReference = db.document("Admin/admin");
        documentReference.get().addOnSuccessListener(documentSnapshot -> {
            foiz11 = documentSnapshot.getString("foiz");
            foiz22 = documentSnapshot.getString("foiz1");
            foiz33 = documentSnapshot.getString("foiz2");
            foiz44 = documentSnapshot.getString("foiz3");
            name1 = documentSnapshot.getString("text1");
            name2 = documentSnapshot.getString("text2");
            name3 = documentSnapshot.getString("text3");
            name4 = documentSnapshot.getString("text4");
            promocode = documentSnapshot.getString("POMOCODE");
            edipromocode.setText(promocode);
            edifoiz1.setText(foiz11);
            edifoiz2.setText(foiz22);
            edifoiz3.setText(foiz33);
            edifoiz4.setText(foiz44);

            ediname1.setText(name1);
            ediname2.setText(name2);
            ediname3.setText(name3);
            ediname4.setText(name4);
            //progressBaradmin.setVisibility(View.GONE);
        });
    }
    private void funupdatefirebase(){
        String xabarr = edixabar.getText().toString().trim(),
                yangilikk = ediyangilik.getText().toString().trim(),
                dollarr = edidollar.getText().toString().trim(),
                euroo = edieuro.getText().toString().trim(),
                rubll = edirubl.getText().toString().trim(),
                somm = edisom.getText().toString().trim(),

                namee1 = ediname1.getText().toString().trim(),
                namee2 = ediname2.getText().toString().trim(),
                namee3 = ediname3.getText().toString().trim(),
                namee4 = ediname4.getText().toString().trim(),
                foizz1 = edifoiz1.getText().toString().trim(),
                foizz2 = edifoiz2.getText().toString().trim(),
                foizz3 = edifoiz3.getText().toString().trim(),
                foizz4 = edifoiz4.getText().toString().trim(),
                promocodd = edipromocode.getText().toString().trim();
        message = xabarr;
        //progressBaradmin.setVisibility(View.VISIBLE);
        Map<String, Object> updatecash = new HashMap<>();
        updatecash.put("XABARLAR", xabarr);
        updatecash.put("YANAGILIKLAR", yangilikk);
        updatecash.put("dollar", dollarr);
        updatecash.put("euro", euroo);
        updatecash.put("rubl", rubll);
        updatecash.put("som", somm);
        Map<String, Object> updatecash1 = new HashMap<>();
        updatecash1.put("text1", namee1);
        updatecash1.put("text2", namee2);
        updatecash1.put("text3", namee3);
        updatecash1.put("text4", namee4);
        updatecash1.put("foiz", foizz1);
        updatecash1.put("foiz1", foizz2);
        updatecash1.put("foiz2", foizz3);
        updatecash1.put("foiz3", foizz4);
        updatecash1.put("POMOCODE", promocodd);

        db.collection("Admin")
                .document("netcash")
                .update(updatecash);
        db.collection("Admin")
                .document("admin")
                .update(updatecash1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                firebaseread();
            }
        });
    }
}
