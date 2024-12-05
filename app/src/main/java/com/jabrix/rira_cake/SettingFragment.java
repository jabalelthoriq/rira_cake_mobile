package com.jabrix.rira_cake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SettingFragment extends Fragment {

    private ImageView profilePicture;
    private TextView usernameText;
    private Button keluarAkun, btnUbahProfil, ulasAplikasi, btnKeamananAkun;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Inisialisasi Views
        profilePicture = view.findViewById(R.id.profilePicture);
        usernameText = view.findViewById(R.id.usernameText);
        keluarAkun = view.findViewById(R.id.keluarAkun);
        btnUbahProfil = view.findViewById(R.id.btnubahProfil);
        ulasAplikasi = view.findViewById(R.id.btnulasAplikasi);
        btnKeamananAkun = view.findViewById(R.id.btnkeamananAkun);
        // Muat data awal
        loadUserData();

        // Tombol Logout dengan Konfirmasi
        keluarAkun.setOnClickListener(v -> {
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Konfirmasi Logout")
                    .setMessage("Apakah Anda yakin ingin keluar dari akun?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        Intent intent = new Intent(requireContext(), Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        });

        // Tombol untuk membuka Fragment Ubah Profil
        btnUbahProfil.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, new ubah_profil());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Tombol untuk membuka Fragment Keamanan Akun
        btnKeamananAkun.setOnClickListener(v -> {
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.flFragment, new KeamananAkun());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        // Tombol untuk menampilkan Dialog Ulas Aplikasi
        ulasAplikasi.setOnClickListener(v -> showReviewDialog());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadUserData(); // Muat ulang data saat fragment aktif kembali
    }

    private void loadUserData() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");
        String imageUriString = sharedPreferences.getString("profileImage", null);

        if (username == null || username.isEmpty()) {
            username = "Guest";
        }
        usernameText.setText(username);

        if (imageUriString != null) {
            try {
                Uri imageUri = Uri.parse(imageUriString);
                profilePicture.setImageURI(imageUri);
            } catch (Exception e) {
                profilePicture.setImageResource(R.drawable.ic_baseline_person_24); // Gambar default
            }
        } else {
            profilePicture.setImageResource(R.drawable.ic_baseline_person_24); // Gambar default
        }
    }

    private void showReviewDialog() {
        android.app.Dialog dialog = new android.app.Dialog(requireContext());
        dialog.setContentView(R.layout.dialog_ulas_aplikasi);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.findViewById(R.id.btnSubmitReview).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Terima kasih atas ulasan Anda!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
