package com.example.andr_advanced;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;


public class Home_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frgm = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        TextView text = frgm.findViewById(R.id.text);
        TextView tx_city = frgm.findViewById(R.id.tx_mos);
        ImageView image = frgm.findViewById(R.id.image);
        TextView textView = frgm.findViewById(R.id.textView);
        TableLayout dop_fun = frgm.findViewById(R.id.dop_fun);
        TextView city = frgm.findViewById(R.id.city);

        city.setOnClickListener(v -> {
            Choose_City fragment = new Choose_City();
            assert getFragmentManager() != null;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frg_home, fragment)
                    .commit();
        });
        return frgm;

    }
}