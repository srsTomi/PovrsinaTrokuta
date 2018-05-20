package tomislav.piskur.com.povrsinatrokuta.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import tomislav.piskur.com.povrsinatrokuta.R;
import tomislav.piskur.com.povrsinatrokuta.R;

public class Rezultat extends AppCompatActivity {

    private TextView tvRezultat;
    private Double rezultat;
    private Button btnPovratak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultat);
        getSupportActionBar().hide();

        tvRezultat = findViewById(R.id.tvRezultat);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            rezultat = bundle.getDouble("rezultat" );
        }
        DecimalFormat form = new DecimalFormat("0.00");
        tvRezultat.setText(form.format(rezultat));
        btnPovratak = findViewById(R.id.btnPovratak);
        btnPovratak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });
    }
}
