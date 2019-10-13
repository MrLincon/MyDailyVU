package com.example.mydailyvu.Profile_Teacher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydailyvu.Profile_CR.CrProfileActivity;
import com.example.mydailyvu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditTeacherProfileActivity extends AppCompatActivity {

    private static final String KEY_USER_IMAGE = "imageUrl";

    Toolbar toolbar;

    ProgressBar progressBar;

    private EditText teacher_name, teacher_dept, teacher_designation, teacher_room, teacher_contact, teacher_email,teacher_code;
    private TextView add_image;

    CircularImageView imageViewT;

    private FirebaseAuth mAuth;
    private String userID;
    private String image_link;


    private FirebaseFirestore db;

    private StorageReference mStorageRef;
    private DocumentReference document_reference;
    private DocumentReference document_ref;
    private DocumentReference doc_ref;

    private static final int PICK_IMAGE_REQUEST = 11;

    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher_profile);

        add_image = findViewById(R.id.add_teacher_image);
        progressBar = findViewById(R.id.progress_loading_t);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        imageViewT = findViewById(R.id.circularImageView_add_teacher);

        imageViewT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userImage();
            }
        });

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userImage();
            }
        });


        mStorageRef = FirebaseStorage.getInstance().getReference().child("uploadsT");


        teacher_name = findViewById(R.id.teacher_name);
        teacher_dept = findViewById(R.id.teacher_dept);
        teacher_designation = findViewById(R.id.teacher_designation);
        teacher_code = findViewById(R.id.teacher_code);
        teacher_room = findViewById(R.id.teacher_room);
        teacher_email = findViewById(R.id.teacher_email);
        teacher_contact = findViewById(R.id.teacher_contact);


        Intent intent = getIntent();
        String name_teacher = intent.getStringExtra(TeacherProfileActivity.EXTRA_T_NAME);
        String department_teacher = intent.getStringExtra(TeacherProfileActivity.EXTRA_T_DEPARTMENT);
        String designation_teacher = intent.getStringExtra(TeacherProfileActivity.EXTRA_T_DESIGNATION);
        String code_teacher = intent.getStringExtra(TeacherProfileActivity.EXTRA_T_CODE);
        String room_teacher = intent.getStringExtra(TeacherProfileActivity.EXTRA_T_ROOM);
        String email_teacher = intent.getStringExtra(TeacherProfileActivity.EXTRA_T_EMAIL);
        String contact_teacher = intent.getStringExtra(TeacherProfileActivity.EXTRA_T_CONTACT);

        teacher_name.setText(name_teacher);
        teacher_dept.setText(department_teacher);
        teacher_designation.setText(designation_teacher);
        teacher_code.setText(code_teacher);
        teacher_room.setText(room_teacher);
        teacher_email.setText(email_teacher);
        teacher_contact.setText(contact_teacher);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getUid();

        progressBar.setVisibility(View.GONE);

//
        document_reference = db.collection("UsersT").document(userID);

        document_reference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {

                    progressBar.setVisibility(View.VISIBLE);


                    String url = documentSnapshot.getString(KEY_USER_IMAGE);

                    Picasso.get().load(url).error(R.drawable.add_user_pic).into(imageViewT);


                } else {
                    Toast.makeText(EditTeacherProfileActivity.this, "Problem loading user image!", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        progressBar.setVisibility(View.GONE);


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {

        Intent save = new Intent(EditTeacherProfileActivity.this, TeacherProfileActivity.class);
        startActivity(save);
        finish();
        super.onBackPressed();
    }

    private void userImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.get().load(mImageUri).error(R.drawable.add_user_pic).into(imageViewT);

            final StorageReference imageName = mStorageRef.child(userID);

            final ProgressDialog progressDialogT = new ProgressDialog(EditTeacherProfileActivity.this);
            progressDialogT.setTitle("Uploading Photo");
            progressDialogT.setMessage("Please wait a few seconds!");
            progressDialogT.show();

            imageName.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(EditTeacherProfileActivity.this, "Uploaded", Toast.LENGTH_LONG).show();

                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            image_link = uri.toString();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            doc_ref = db.collection("UsersT").document(userID);

                            doc_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        if (documentSnapshot != null && documentSnapshot.exists()) {
                                            doc_ref.update("imageUrl", image_link).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    progressDialogT.cancel();
                                                    Toast.makeText(EditTeacherProfileActivity.this, "Profile picture uploaded!", Toast.LENGTH_SHORT).show();
                                                    Intent save = new Intent(EditTeacherProfileActivity.this, CrProfileActivity.class);
                                                    startActivity(save);
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialogT.cancel();
                                                    Toast.makeText(EditTeacherProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });

                                        } else {
                                            Map<String, String> userMapImg = new HashMap<>();

                                            userMapImg.put("imageUrl", image_link);

                                            doc_ref.set(userMapImg).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialogT.cancel();
                                                    Toast.makeText(EditTeacherProfileActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialogT.cancel();
                                                    Toast.makeText(EditTeacherProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }
                                }
                            });

                        }
                    });

                }

            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_details:
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveData() {

        updateDetails();

    }

    private void updateDetails() {

        final String nameT = teacher_name.getText().toString().trim();
        final String departmentT = teacher_dept.getText().toString().toUpperCase().trim();
        final String designationT = teacher_designation.getText().toString().trim();
        final String codeT = teacher_code.getText().toString().toUpperCase().trim();
        final String roomT = teacher_room.getText().toString().trim();
        final String emailT = teacher_email.getText().toString().trim();
        final String contactT = teacher_contact.getText().toString().trim();

//        if (mImageUri == null){
//            Toast.makeText(this, "No image selected!", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (nameT.isEmpty()) {
            teacher_name.setError("Field must be filled");
            return;
        }
        if (departmentT.isEmpty()) {
            teacher_dept.setError("Field must be filled");
            return;
        }
        if (designationT.isEmpty()) {
            teacher_designation.setError("Field must be filled");
            return;
        }
        if (codeT.isEmpty()) {
            teacher_code.setError("Field must be filled");
            return;
        }
        if (roomT.isEmpty()) {
            teacher_room.setError("Field must be filled");
            return;
        }
        if (emailT.isEmpty()) {
            teacher_email.setError("Field must be filled");
            return;
        }
        if (contactT.isEmpty()) {
            teacher_contact.setError("Field must be filled");
            return;
        } else {

            ProgressDialog progressDialogT = new ProgressDialog(EditTeacherProfileActivity.this);
            progressDialogT.setTitle("Updating");
            progressDialogT.setMessage("Please wait a few seconds!");
            progressDialogT.show();


            document_ref = db.collection("UsersT").document(userID);

            document_ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null && documentSnapshot.exists()) {

                            document_ref.update("name", nameT,
                                    "dept", departmentT,
                                    "desg", designationT,
                                    "code", codeT,
                                    "room", roomT,
                                    "email", emailT,
                                    "contact",contactT).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
//                                    cr_name.setText("");
//                                    cr_id.setText("");
//                                    cr_semester.setText("");
//                                    cr_section.setText("");
//                                    cr_contact.setText("");
//                                    cr_email.setText("");

                                    Toast.makeText(EditTeacherProfileActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditTeacherProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                            Intent save = new Intent(EditTeacherProfileActivity.this, TeacherProfileActivity.class);
                            startActivity(save);
                            finish();
                        } else {
                            Map<String, String> userMap = new HashMap<>();

                            userMap.put("imageUrl", image_link);
                            userMap.put("name", nameT);
                            userMap.put("dept", departmentT);
                            userMap.put("desg", designationT);
                            userMap.put("code", codeT);
                            userMap.put("room", roomT);
                            userMap.put("email", emailT);
                            userMap.put("contact", contactT);

                            document_ref.set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    cr_name.setText("");
//                                    cr_id.setText("");
//                                    cr_semester.setText("");
//                                    cr_section.setText("");
//                                    cr_contact.setText("");
//                                    cr_email.setText("");

                                    Toast.makeText(EditTeacherProfileActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditTeacherProfileActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                            Intent save = new Intent(EditTeacherProfileActivity.this, CrProfileActivity.class);
                            startActivity(save);
                            finish();
                        }
                    }


                }
            });


        }
    }
}