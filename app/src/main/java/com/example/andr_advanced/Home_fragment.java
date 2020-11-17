package com.example.andr_advanced;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class Home_fragment extends Fragment {
    public static OnDialogListener dialogListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frgm = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        TextView text = frgm.findViewById(R.id.text);
        EditText tx_city = frgm.findViewById(R.id.tx_mos);
        ImageView image = frgm.findViewById(R.id.image);
        EditText temp = frgm.findViewById(R.id.textView);
        TableLayout dop_fun = frgm.findViewById(R.id.dop_fun);
        Button refresh = frgm.findViewById(R.id.city);
        TextView textView1 = frgm.findViewById(R.id.textView2);
        EditText wind = frgm.findViewById(R.id.textView3);
        TextView textView3 = frgm.findViewById(R.id.textView4);
        EditText humidity = frgm.findViewById(R.id.textView5);
        TextView textView5 = frgm.findViewById(R.id.textView6);
        EditText pressure = frgm.findViewById(R.id.textView7);
        EditText editText = frgm.findViewById(R.id.input );


        /*city.setOnClickListener(view -> {
            Dop_fragment dialogFragment =
                    Dop_fragment.newInstance();
            dialogFragment.setOnDialogListener(dialogListener);
            assert getFragmentManager() != null;
            dialogFragment.show(getFragmentManager(),
                    "dop_fragment");
        });*/
        return frgm;
    }


}






