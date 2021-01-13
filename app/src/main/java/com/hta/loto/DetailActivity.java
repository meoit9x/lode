package com.hta.loto;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;

public class DetailActivity extends AppCompatActivity {

    private TextView txtContent, txtTitle;
    private ProgressDialog dialog;
    private Thread thread;
    private String key;

    protected void startThread(Consumer consumer) {
        thread = new Thread(() -> consumer.accept(new Object()));
        thread.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        txtTitle = findViewById(R.id.txtTitle);
        txtContent = findViewById(R.id.txtContent);
        dialog = ProgressDialog.show(this, "",
                "Đợi tí đkm", true);
        initData();
    }

    private void initData() {
        dialog.show();
        if (getIntent() != null && getIntent().getExtras() != null) {
            key = getIntent().getExtras().getString("KEY_DETAIL", "");
            txtTitle.setText(key);
            if (key.equals(getString(R.string.dan_de))) {
                key = "de";
            }

            if (key.equals(getString(R.string.bach_thu_lo))) {
                key = "lo";
            }

            if (key.equals(getString(R.string.song_thu_lo))) {
                key = "songthulo";
            }

            if (key.equals(getString(R.string.dan_lo_5_so))) {
                key = "danlo";
            }

            startThread(o -> Controller.getCC(key, o1 -> {
                runOnUiThread(() -> {
                    dialog.dismiss();
                    if (o1 != null) {
                        txtContent.setText(o1.getData().toString());
                    }
                });
            }));

            startThread(o -> Controller.getStaticstic(key, o1 -> {
                runOnUiThread(() -> {
                    dialog.dismiss();
                    if (o1 != null) {
                    }
                });
            }));
        }
    }

}
