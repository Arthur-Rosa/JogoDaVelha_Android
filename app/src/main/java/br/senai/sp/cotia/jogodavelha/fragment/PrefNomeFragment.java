package br.senai.sp.cotia.jogodavelha.fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import br.senai.sp.cotia.jogodavelha.R;

public class PrefNomeFragment extends PrefFragment{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_name);
    }

    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().show();
        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
