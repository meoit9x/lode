package com.hta.loto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.txtDanDe).setOnClickListener(this);
        findViewById(R.id.txtBachThuLo).setOnClickListener(this);
        findViewById(R.id.txtSongThuLo).setOnClickListener(this);
        findViewById(R.id.txtDanLo5So).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        if (v.getId() == R.id.txtDanDe) {
            bundle.putString("KEY_DETAIL", getString(R.string.dan_de));
        }
        if (v.getId() == R.id.txtBachThuLo) {
            bundle.putString("KEY_DETAIL", getString(R.string.bach_thu_lo));
        }
        if (v.getId() == R.id.txtSongThuLo) {
            bundle.putString("KEY_DETAIL", getString(R.string.song_thu_lo));
        }
        if (v.getId() == R.id.txtDanLo5So) {
            bundle.putString("KEY_DETAIL", getString(R.string.dan_lo_5_so));
        }
        intent.putExtras(bundle);
        startActivity(intent);
    }
}