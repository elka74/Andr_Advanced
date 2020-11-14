package com.example.andr_advanced;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class Dop_fragment extends BottomSheetDialogFragment {

        private OnDialogListener dialogListener;

        public static Dop_fragment newInstance() {
            return new Dop_fragment();
        }

        public void setOnDialogListener(OnDialogListener dialogListener){
            this.dialogListener = dialogListener;
        }



    @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater,
                                 @Nullable ViewGroup container,
                                 @Nullable Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_dop_fragment, container,
                    false);

            Button button = view.findViewById(R.id.btnNo);
            Button button1 = view.findViewById(R.id.btnOk);
            //button.setOnClickListener((View.OnClickListener) this);
            //button1.setOnClickListener((View.OnClickListener) this);   return view;
            return view;
        }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            setCancelable(false);


            view.findViewById(R.id.btnOk).setOnClickListener(view1 -> {
                dismiss();
                if (dialogListener != null) dialogListener.onDialogOk();
                Fragment fragment = new Home_fragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dop, fragment)
                        .commit();
            });

            view.findViewById(R.id.btnNo).setOnClickListener(view12 -> {
                dismiss();
                if (dialogListener != null) dialogListener.onDialogNo();
            });



        }
    }