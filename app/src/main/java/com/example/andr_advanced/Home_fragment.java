package com.example.andr_advanced;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


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
        TextView textView1 = frgm.findViewById(R.id.textView2);
        TextView textView2 = frgm.findViewById(R.id.textView3);
        TextView textView3 = frgm.findViewById(R.id.textView4);
        TextView textView4 = frgm.findViewById(R.id.textView5);
        TextView textView5 = frgm.findViewById(R.id.textView6);
        TextView textView6 = frgm.findViewById(R.id.textView7);

       /* city.setOnClickListener(v -> {
            Choose_City fragment = new Choose_City();
            assert getFragmentManager() != null;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frg_home, fragment)
                    .commit();
        });
        return frgm;*/
        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dop_fragment dialogFragment =
                        Dop_fragment.newInstance();
                dialogFragment.setOnDialogListener(dialogListener);
                dialogFragment.show(getFragmentManager(),
                        "dop_fragment");
            }
        });
        return frgm;
    }

    private OnDialogListener dialogListener = new OnDialogListener() {
        @Override
        public void onDialogOk() {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.dop, fragment)
                    .commit();
        }
        @Override
        public viod onDialogNo(){
        }
    }

    @SuppressLint("NonConstantResourceId")
    int translateIdToIndex(int id){
        int index = -1;
        switch (id){
            case R.id.btnOk:
                index = 1;
                break;
            case R.id.btnNo:
                index = 2;
                break;
        }
        return index;
    }



    }
