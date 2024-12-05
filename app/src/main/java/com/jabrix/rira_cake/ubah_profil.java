package com.jabrix.rira_cake;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class ubah_profil extends Fragment {

    private ImageView profileImage;
    private EditText edtFullName, edtEmail, edtPhone, edtAddress;
    private Uri imageUri;

    // Daftarkan launcher untuk memilih gambar dari galeri
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    imageUri = result.getData().getData();
                    profileImage.setImageURI(imageUri);  // Tampilkan gambar di ImageView
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ubah_profil, container, false);

        // Inisialisasi elemen UI
        profileImage = view.findViewById(R.id.profileImage);
        edtFullName = view.findViewById(R.id.edtFullName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPhone = view.findViewById(R.id.edtPhone);
        edtAddress = view.findViewById(R.id.edtAddress);
        Button btnSubmit = view.findViewById(R.id.btnSubmit);
        Button btnBack = view.findViewById(R.id.backButton);

        // Load data dari SharedPreferences
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        edtFullName.setText(sharedPreferences.getString("username", ""));
        edtEmail.setText(sharedPreferences.getString("email", ""));
        edtPhone.setText(sharedPreferences.getString("phone", ""));
        edtAddress.setText(sharedPreferences.getString("address", ""));
        String savedImageUri = sharedPreferences.getString("profileImage", null);

        if (savedImageUri != null) {
            imageUri = Uri.parse(savedImageUri);
            profileImage.setImageURI(imageUri);
        }

        // Event untuk memilih gambar dari galeri
        profileImage.setOnClickListener(v -> openGallery());

        // Tombol untuk menyimpan data
        btnSubmit.setOnClickListener(v -> {
            if (edtFullName.getText().toString().isEmpty() ||
                    edtEmail.getText().toString().isEmpty() ||
                    edtPhone.getText().toString().isEmpty() ||
                    edtAddress.getText().toString().isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", edtFullName.getText().toString());
            editor.putString("email", edtEmail.getText().toString());
            editor.putString("phone", edtPhone.getText().toString());
            editor.putString("address", edtAddress.getText().toString());
            if (imageUri != null) {
                editor.putString("profileImage", imageUri.toString());
            }
            editor.apply();

            Toast.makeText(requireContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Tombol kembali
        btnBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void openGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            resultLauncher.launch(galleryIntent);
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(requireContext(), "Izin diperlukan untuk mengakses galeri", Toast.LENGTH_SHORT).show();
        }
    }

    private static final int PICK_IMAGE = 100;
}
