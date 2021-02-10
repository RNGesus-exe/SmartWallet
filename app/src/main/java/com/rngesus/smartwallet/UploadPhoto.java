package com.rngesus.smartwallet;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadPhoto {
    StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();;
    void onStart(Context context, CircleImageView profileimg)
    {
        StorageReference profile=storageReference.child("user/"+firebaseAuth.getCurrentUser().getUid()+"profile.jpg");
        profile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(profileimg);

            }
        });
    }
    void Uploadimage(Uri selectedImage,Context context,CircleImageView profileimg)
    {
        StorageReference fileref = storageReference.child("user/" + firebaseAuth.getCurrentUser().getUid() + "profile.jpg");
        fileref.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(context)
                                .load(uri)
                                .into(profileimg);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void deleteimage(Context context)
    {
        StorageReference profile=storageReference.child("user/"+firebaseAuth.getCurrentUser().getUid()+"profile.jpg");
        StorageReference Delete=FirebaseStorage.getInstance().getReferenceFromUrl(profile.toString());
        Delete.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Image is deleted", Toast.LENGTH_SHORT).show();
                Glide.with(context)
                        .load((Bitmap) null)
                        .into(SettingActivity.profileimgsetting);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Image is  not deleted", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
