package com.example.delivereat.ui.activities.decomercio;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.delivereat.R;
import com.example.delivereat.control.IControl;
import com.example.delivereat.ui.abstracts.BaseActivity;

public class ProximamenteActivity extends BaseActivity {

    @Nullable
    @Override
    protected IControl getControl() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_proximamente;
    }

    @Override
    protected void iniciarViews() {
        setAnimacionVolver(R.anim.from_up, R.anim.to_down);
    }

}