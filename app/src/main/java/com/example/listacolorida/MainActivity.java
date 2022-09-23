/* Laura e Evandro */

package com.example.listacolorida;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase bd;
    AdapterTextos adapterTextos;
    Cursor cursorTextos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnInserirTexto = findViewById(R.id.btnInserirTexto);
        ListView listaTextos = findViewById(R.id.listaTextos);

        bd = openOrCreateDatabase("textos", MODE_PRIVATE, null);
        bd.execSQL("CREATE TABLE IF NOT EXISTS textos(texto VARCHAR, cor INTEGER)");

        cursorTextos = bd.rawQuery("SELECT _rowid_ _id, texto, cor FROM textos", null);
        adapterTextos = new AdapterTextos(this, cursorTextos);
        listaTextos.setAdapter(adapterTextos);

        btnInserirTexto.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AdicionaActivity.class);
            startActivityForResult(intent, 1);
        });

        listaTextos.setOnItemClickListener((adapterView, view, i, l) -> {
            cursorTextos = (Cursor) adapterTextos.getItem(i);
            String texto = cursorTextos.getString(cursorTextos.getColumnIndexOrThrow("texto"));
            int cor = cursorTextos.getInt(cursorTextos.getColumnIndexOrThrow("cor"));
            String nomeCor;
            if (cor == Color.RED) {
                nomeCor = "Vermelho";
            } else if (cor == Color.GREEN) {
                nomeCor = "Verde";
            } else {
                nomeCor = "Azul";
            }
            Toast.makeText(getApplicationContext(), texto + "\n" + nomeCor, Toast.LENGTH_SHORT).show();
        });

        listaTextos.setOnItemLongClickListener((adapterView, view, i, l) -> {
            cursorTextos = (Cursor) adapterTextos.getItem(i);
            int rowid = cursorTextos.getInt(cursorTextos.getColumnIndexOrThrow("_id"));
            bd.execSQL("DELETE FROM textos WHERE _rowid_ = " + rowid);
            Cursor cursorTextos = bd.rawQuery("SELECT _rowid_ _id, texto, cor FROM textos", null);
            adapterTextos.changeCursor(cursorTextos);
            return true;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String texto = data.getStringExtra("texto");
                int cor = data.getIntExtra("cor", 0);

                bd.execSQL("INSERT INTO textos(texto, cor) VALUES('" + texto + "', " + cor + ")");
                Cursor cursorTextos = bd.rawQuery("SELECT _rowid_ _id, texto, cor FROM textos", null);
                adapterTextos.changeCursor(cursorTextos);
            }
        }
    }
}