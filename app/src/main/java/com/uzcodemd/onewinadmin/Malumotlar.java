package com.uzcodemd.onewinadmin;

import static android.util.Log.d;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Malumotlar extends AppCompatActivity {

    private FirebaseFirestore db;
    private DocumentReference documentReference;

    private TextView txtuid,txtkirvaqt,txtonline,txtlink;
    private EditText ediism,edifamilya,editel,edipochta,ediparol,edinetkash,edipromocod,ediuserid,
            ediyoshi,edibugun,edikecha,edihafta,edioy,edijami,edidaraja;
    private ImageView imgfon;
    private Button btntahrirlash;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;

    private Handler handler;
    String nikname, photo;
    int click = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malumotlar);
        Objects.requireNonNull(getSupportActionBar()).hide();

        db = FirebaseFirestore.getInstance();

        txtuid =findViewById(R.id.txtuid);
        txtkirvaqt =findViewById(R.id.txtkirvaqt);
        txtonline =findViewById(R.id.txtonline);
        txtlink =findViewById(R.id.txtlink);

        ediism =findViewById(R.id.ediism);
        edifamilya =findViewById(R.id.edifamilya);
        editel =findViewById(R.id.editel);
        edipochta =findViewById(R.id.edipochta);
        ediparol =findViewById(R.id.ediparol);
        edinetkash =findViewById(R.id.edinetkash);
        edipromocod =findViewById(R.id.edipromocod);
        ediuserid =findViewById(R.id.ediuserid);
        ediyoshi =findViewById(R.id.ediyoshi);
        edibugun =findViewById(R.id.edibugun);
        edikecha =findViewById(R.id.edikecha);
        edihafta =findViewById(R.id.edihafta);
        edioy =findViewById(R.id.edioy);
        edijami =findViewById(R.id.edijami);
        edidaraja =findViewById(R.id.edidaraja);

        btntahrirlash =findViewById(R.id.btntahrirlash);
        imgfon =findViewById(R.id.imgfon);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            nikname = (String) b.get("name");
        }
        txtuid.setText(nikname);
        fun();

        imgfon.setOnClickListener(view -> chooseImage());

        btntahrirlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (click == 0) {
                    ediism.setEnabled(true);
                    edifamilya.setEnabled(true);
                    ediyoshi.setEnabled(true);
                    editel.setEnabled(true);
                    edipromocod.setEnabled(true);
                    ediparol.setEnabled(true);
                    //edimalemail.setEnabled(true);
                    //ediuserid.setEnabled(true);
                    edibugun.setEnabled(true);
                    edikecha.setEnabled(true);
                    edihafta.setEnabled(true);
                    edioy.setEnabled(true);
                    edijami.setEnabled(true);
                    edidaraja.setEnabled(true);
                    edinetkash.setEnabled(true);
                    btntahrirlash.setText("saqlash");
                    click++;
                } else {
                    String ismi = ediism.getText().toString(),
                            familyasi = edifamilya.getText().toString(),
                            tel = editel.getText().toString(),
                            parol = ediparol.getText().toString(),
                            promocode = edipromocod.getText().toString(),
                            pichtas = edipochta.getText().toString(),
                            useriid = ediuserid.getText().toString(),
                            bugunp = edibugun.getText().toString(),
                            kechap = edikecha.getText().toString(),
                            haftap = edihafta.getText().toString(),
                            oyp = edioy.getText().toString(),
                            jamip = edijami.getText().toString(),
                            daraja = edidaraja.getText().toString(),
                            netcashh = edinetkash.getText().toString();

                    Map<String, Object> usermal = new HashMap<>();
                    usermal.put("ISMI", ismi);
                    usermal.put("FAMILYA", familyasi);
                    usermal.put("TEL", tel);
                    usermal.put("PAROL", parol);
                    usermal.put("PROMOCOD", promocode);
                    usermal.put("POCHTA", pichtas);
                    usermal.put("USERID", useriid);
                    usermal.put("BUGUNPUL", bugunp);
                    usermal.put("KECHAPUL", kechap);
                    usermal.put("HAFTAPUL", haftap);
                    usermal.put("OYPUL", oyp);
                    usermal.put("JAMIPUL", jamip);
                    usermal.put("DARAJA", daraja);
                    usermal.put("NETCASH", netcashh);
                    db.collection("user")
                            .document(nikname)
                            .update(usermal).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            ediism.setEnabled(false);
                            edifamilya.setEnabled(false);
                            editel.setEnabled(false);
                            edipromocod.setEnabled(false);
                            ediyoshi.setEnabled(false);
                            ediparol.setEnabled(false);
                            edipochta.setEnabled(false);
                            ediuserid.setEnabled(false);
                            edibugun.setEnabled(false);
                            edikecha.setEnabled(false);
                            edihafta.setEnabled(false);
                            edioy.setEnabled(false);
                            edijami.setEnabled(false);
                            edidaraja.setEnabled(false);
                            edinetkash.setEnabled(false);
                            click--;
                            btntahrirlash.setText("tahrirlash");
                        }
                    });
                }
            }
        });

    }
    private void fun() {
        //progressBarmal.setVisibility(View.VISIBLE);
        documentReference = db.document("user/" + nikname);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String ism = documentSnapshot.getString("ISMI"),
                        familya = documentSnapshot.getString("FAMILYA"),
                        tel = documentSnapshot.getString("TEL"),
                        parol = documentSnapshot.getString("PAROL"),
                        promocod = documentSnapshot.getString("PROMOCOD"),
                        email = documentSnapshot.getString("POCHTA"),
                        id = documentSnapshot.getString("USERID"),
                        bugpul = documentSnapshot.getString("BUGUNPUL"),
                        kechapul = documentSnapshot.getString("KECHAPUL"),
                        hafpul = documentSnapshot.getString("HAFTAPUL"),
                        oypul = documentSnapshot.getString("OYPUL"),
                        jamipul = documentSnapshot.getString("JAMIPUL"),
                        daraja = documentSnapshot.getString("DARAJA"),
                        netcash = documentSnapshot.getString("NETCASH"),
                        kirvaqt = documentSnapshot.getString("KIRGANVAQT"),
                        onlinee = documentSnapshot.getString("SONGIFAOLLIK"),
                        link = documentSnapshot.getString("LINK"),
                        yosh = documentSnapshot.getString("YOSHI"),
                        photo = documentSnapshot.getString("PHOTO");
                if (Objects.equals(photo, "0")) {
                    //progressBarmal.setVisibility(View.GONE);
                    imgfon.setImageResource(android.R.drawable.ic_delete);
                } else {
                    //progressBarmal.setVisibility(View.GONE);
                    Glide.with(getApplicationContext()).load(photo).into(imgfon);
                }
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy:MM:dd", Locale.getDefault());
                String bugunn = sdf1.format(new Date());
                String[] values = kirvaqt.split("_");
                String[] values1 = onlinee.split("_");
                if (Objects.equals(bugunn,values1[0])){
                    txtonline.setText("songi kirgan vaqt"+"\n"
                            +"yaqinda"+"\n"+values1[1]);
                }
                else {
                    txtonline.setText("songi kirgan vaqt"+"\n"+
                            values1[0]+"\n"+values1[1]);
                }
                ediism.setText(ism);
                edifamilya.setText(familya);
                editel.setText(tel);
                edipromocod.setText(promocod);
                ediparol.setText(parol);
                edipochta.setText(email);
                ediuserid.setText(id);
                edibugun.setText(bugpul);
                edikecha.setText(kechapul);
                edihafta.setText(hafpul);
                edioy.setText(oypul);
                edijami.setText(jamipul);
                edidaraja.setText(daraja);
                edinetkash.setText(netcash);
                ediyoshi.setText(yosh);
                txtlink.setText("referal > "+link);
                txtkirvaqt.setText("kirgan vaqt" + "\n" + values[0] + "\n" + values[1]);
                ediism.setEnabled(false);
                edifamilya.setEnabled(false);
                ediyoshi.setEnabled(false);
                editel.setEnabled(false);
                edipromocod.setEnabled(false);
                ediparol.setEnabled(false);
                edipochta.setEnabled(false);
                ediuserid.setEnabled(false);
                edibugun.setEnabled(false);
                edikecha.setEnabled(false);
                edihafta.setEnabled(false);
                edioy.setEnabled(false);
                edijami.setEnabled(false);
                edidaraja.setEnabled(false);
                edinetkash.setEnabled(false);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(Malumotlar.this, "Dasturda xatolik", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imgfon.setImageBitmap(bitmap);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                if (filePath != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(Malumotlar.this);
                    progressDialog.setTitle("Uploading...");
                    progressDialog.show();
                    //txturluserphoto.setText(UUID.randomUUID().toString());
                    StorageReference ref = storageReference.child(nikname
                            + "/" + nikname);
                    ref.putFile(filePath)
                            .addOnSuccessListener(taskSnapshot -> {
                                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();
                                downloadUrl.addOnCompleteListener(task -> {
                                    String downloadURL = "https://" + task.getResult().getEncodedAuthority()
                                            + task.getResult().getEncodedPath()
                                            + "?alt=media&token="
                                            + task.getResult().getQueryParameters("token").get(0);
                                    Map<String, Object> userphoto = new HashMap<>();
                                    userphoto.put("PHOTO", downloadURL);
                                    db.collection("user")
                                            .document(nikname)
                                            .update(userphoto);
                                });
                                progressDialog.dismiss();
                                Toast.makeText(Malumotlar.this, "Uploaded", Toast.LENGTH_LONG).show();
                            }).addOnFailureListener(e -> {
                                d("TAG", e.toString());
                                progressDialog.dismiss();
                                Toast.makeText(Malumotlar.this, "Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
                            })
                            .addOnProgressListener((com.google.firebase.storage.OnProgressListener<? super UploadTask.TaskSnapshot>) snapshot -> {

                                double progress = (100.0 * snapshot.getBytesTransferred() / snapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");

                            });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
}
