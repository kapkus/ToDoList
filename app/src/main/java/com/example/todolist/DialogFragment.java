package com.example.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DialogFragment extends androidx.fragment.app.DialogFragment {

    public interface DialogInterface {
        void onDelete(int position);
    }

    private DialogInterface mListener;

    public static String TAG = "DeleteListItem";
    private final int position;

    public DialogFragment(int position) {
        this.position = position;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage("Do you want to delete this item from the list?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    mListener.onDelete(position);
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                })
                .create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (DialogInterface) getActivity();
        }
        catch (ClassCastException e) {
            Log.e(TAG, "ERROR: " + e.getMessage());
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

}