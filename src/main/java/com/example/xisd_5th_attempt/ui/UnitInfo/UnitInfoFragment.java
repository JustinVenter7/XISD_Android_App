package com.example.xisd_5th_attempt.ui.UnitInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.xisd_5th_attempt.R;

public class UnitInfoFragment extends Fragment {

    private UnitInfoViewModel UnitInfoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UnitInfoViewModel =
                new ViewModelProvider(this).get(UnitInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_unitinfo, container, false);

        return root;
    }
}
