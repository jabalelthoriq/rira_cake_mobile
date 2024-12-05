package com.jabrix.rira_cake;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class KeamananAkun extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keamanan_akun, container, false);

        // Deklarasi View
        Button backButton = view.findViewById(R.id.back_button);
        EditText newPassword = view.findViewById(R.id.new_password);
        EditText confirmPassword = view.findViewById(R.id.confirm_password);
        Button submitButton = view.findViewById(R.id.submit_button);

        // Fungsi Tombol Kembali
        backButton.setOnClickListener(v -> {
            // Kembali ke fragment sebelumnya di back stack
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        // Fungsi Tombol Submit
        submitButton.setOnClickListener(v -> {
            String newPasswordText = newPassword.getText().toString().trim();
            String confirmPasswordText = confirmPassword.getText().toString().trim();

            // Validasi input
            if (newPasswordText.isEmpty() || confirmPasswordText.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
            } else if (newPasswordText.length() < 8) {
                Toast.makeText(requireContext(), "Password harus minimal 8 karakter", Toast.LENGTH_SHORT).show();
            } else if (!newPasswordText.equals(confirmPasswordText)) {
                Toast.makeText(requireContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show();
            } else {
                // Lakukan sesuatu saat password berhasil diubah
                Toast.makeText(requireContext(), "Password berhasil diubah!", Toast.LENGTH_SHORT).show();
                // Kirim ke server atau backend
                updatePassword(newPasswordText);
            }
        });

        return view;
    }

    private void updatePassword(String newPassword) {
        // TODO: Kirim password ke server
        Toast.makeText(requireContext(), "Password Anda telah diperbarui di server.", Toast.LENGTH_SHORT).show();
    }
}
