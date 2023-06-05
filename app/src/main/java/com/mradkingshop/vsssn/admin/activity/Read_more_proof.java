package com.mradkingshop.vsssn.admin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.mradkingshop.vsssn.R;

public class Read_more_proof extends Activity {

    ImageView imageView;
    TextView title,discreption;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proof_read_more);

        imageView=findViewById(R.id.image);
        title=findViewById(R.id.title);
        discreption=findViewById(R.id.discreption);

        data_setUp();

    }

    private void data_setUp() {

      Glide.with(getApplicationContext()).load(getIntent().getExtras().getString("image"))
              .into(imageView);

      title.setText(getIntent().getExtras().getString("titel"));
        discreption.setText(getIntent().getExtras().getString("dis"));

    }
}

