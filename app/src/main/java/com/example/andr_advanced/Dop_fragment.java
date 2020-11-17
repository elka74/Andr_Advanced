package com.example.andr_advanced;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;


public class Dop_fragment extends BottomSheetDialogFragment {

    private OnDialogListener dialogListener;

    public Dop_fragment() {
    }

    public static Dop_fragment newInstance() {
        return new Dop_fragment();
    }

    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dop_fragment, container,
                false);

        Button rectangles = view.findViewById(R.id.rectangles);
        EditText textInputEditText = view.findViewById(R.id.input);
        return view;
    }
}