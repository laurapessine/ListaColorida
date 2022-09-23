package com.example.listacolorida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AdicionaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adiciona);

        EditText txtTexto = findViewById(R.id.txtTexto);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button btnInserir = findViewById(R.id.btnInserir);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnInserir.setOnClickListener(view -> {
            String texto = txtTexto.getText().toString();

            if (texto.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Digite algum texto!", Toast.LENGTH_SHORT).show();
            } else {
                int radioSelecionado = radioGroup.getCheckedRadioButtonId();
                if (radioSelecionado == -1) {
                    Toast.makeText(getApplicationContext(), "Obrigatório escolher uma cor!", Toast.LENGTH_SHORT).show();
                } else {
                    int cor = 0;
                    if (radioSelecionado == R.id.radVermelho) {
                        cor = Color.RED;
                    } else if (radioSelecionado == R.id.radVerde) {
                        cor = Color.GREEN;
                    } else if (radioSelecionado == R.id.radAzul) {
                        cor = Color.BLUE;
                    }

                    Intent intent = new Intent();
                    intent.putExtra("texto", texto);
                    intent.putExtra("cor", cor);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btnCancelar.setOnClickListener(view -> finish());
    }
}