package com.example.xisd_5th_attempt.ui.UnitInfo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UnitInfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UnitInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
