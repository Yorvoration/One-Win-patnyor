package com.uzcodemd.onewinadmin;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class Sample extends AppCompatActivity {


    ListView listmal;
    private FirebaseFirestore db;
    private SearchView search_view;
    Intent intent;
    String email,parol1;
    String type = "POCHTA";
    ArrayAdapter<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample);
        Objects.requireNonNull(getSupportActionBar()).hide();
        db = FirebaseFirestore.getInstance();
        listmal = findViewById(R.id.listmal);
        search_view = findViewById(R.id.search_view);
        Button btnadmin = findViewById(R.id.btnadmin);
        Button btnfilter = findViewById(R.id.btnfilter);

        setTitle("\uD83D\uDE01\uD83D\uDE01\uD83D\uDE01\n");
        search();

        btnfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);
                popupMenu.inflate(R.menu.menupopup);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.email:
                                type = "POCHTA";
                                btnfilter.setText("pochta");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.daraja:
                                type = "POCHTA";
                                btnfilter.setText("daraja");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.yoshi:
                                type = "POCHTA";
                                btnfilter.setText("yoshi");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.bugun:
                                type = "POCHTA";
                                btnfilter.setText("bugungi pul");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.kecha:
                                type = "POCHTA";
                                btnfilter.setText("kechagi pul");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.hafta:
                                type = "POCHTA";
                                btnfilter.setText("haftalik pul");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.oyyyy:
                                type = "POCHTA";
                                btnfilter.setText("oylik pul");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.jami:
                                type = "POCHTA";
                                btnfilter.setText("jami puli");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.tefon:
                                type = "TEL";
                                btnfilter.setText("telefon raqam");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.userid:
                                type = "USERID";
                                btnfilter.setText("foydalanuvchi idsi");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.famili:
                                type = "FAMILYA";
                                btnfilter.setText("familya");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.issmi:
                                type = "ISMI";
                                btnfilter.setText("ismi");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.promokod:
                                type = "PROMOCOD";
                                btnfilter.setText("promocod");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.UiD:
                                type = "UID";
                                btnfilter.setText("uid");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.netkash:
                                type = "NETCASH";
                                btnfilter.setText("netcash");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.llink:
                                type = "LINK";
                                btnfilter.setText("referal");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.kirvaqt:
                                type = "KIRGANVAQT";
                                btnfilter.setText("kirgan vaqti");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                            case R.id.songivaqt:
                                type = "SONGIFAOLLIK";
                                btnfilter.setText("songi faollik");
                                //progressBarqidir.setVisibility(View.VISIBLE);
                                search();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        btnadmin.setOnClickListener(view -> {
            intent = new Intent(Sample.this, Admin.class);
            startActivity(intent);
        });

        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.getFilter().filter(newText);
                return false;
            }
        });
        listmal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedFromList = (String) (listmal.getItemAtPosition(position));
                String[] values = selectedFromList.split("_");
                String a = values[1];
                Intent ii = new Intent(Sample.this, Malumotlar.class);
                ii.putExtra("name", a);
                startActivity(ii);
            }
        });
        listmal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v, int index, long arg3) {

                String selectedFromList = (String) (listmal.getItemAtPosition(index));
                String[] values = selectedFromList.split("_");
                String a = values[1];
                DocumentReference documentReference = db.document("user/"+a);
                documentReference.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                         email = documentSnapshot.getString("POCHTA");
                         parol1 = documentSnapshot.getString("PAROL");
                    }
                });

                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                popupMenu.inflate(R.menu.ochirish);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete:
                                new AlertDialog.Builder(Sample.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("sdfghj").setMessage("qwertyu")
                                        .setPositiveButton("ha", (dialog, which) -> {

                                        })
                                        .setNegativeButton("yoq", null)
                                        .show();
                                search();
                                return true;
                            case R.id.transfer:
                                String selectedFromList = (String) (listmal.getItemAtPosition(index));
                                String[] values = selectedFromList.split("_");
                                String a = values[1];
                                Intent ii = new Intent(Sample.this, Transfer.class);
                                ii.putExtra("name", a);
                                startActivity(ii);
                                search();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();

                return true;
            }
        });
    }

    private void search() {
        db.collection("user").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            list = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add("    " + document.getString(type) + "\n\n    -------------------_" + document.getId());
                                listmal.setAdapter(list);
                                //progressBarqidir.setVisibility(View.GONE);
                            }
                        } else {
                            //progressBarqidir.setVisibility(View.GONE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //progressBarqidir.setVisibility(View.GONE);
            }
        });
    }
}
