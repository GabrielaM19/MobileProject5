package com.example.projekt_5;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private TextView tvNumerTelefonu;
    private TextView tvWyswietlaTelefon;
    private EditText etTekstDoWyszukania;
    private TextView tvTekstDoWyszukania;
    private Button bWyszukajTekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvNumerTelefonu = (TextView) findViewById(R.id.tvNumerTelefonu);
        tvWyswietlaTelefon = (TextView) findViewById(R.id.tvWyswietlaTelefon);
        etTekstDoWyszukania  = (EditText) findViewById(R.id.etTekstDoWyszukania);
        bWyszukajTekst  = (Button) findViewById(R.id.bWyszukajTekst);

        bWyszukajTekst.setOnClickListener(this);


        FloatingActionButton fabKontakty = (FloatingActionButton) findViewById(R.id.fabKontakty);
        fabKontakty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wybierzKontakt();
            }
        });
    }

    private void wybierzKontakt() {
        Intent wybKontakt = new Intent(Intent.ACTION_PICK);
        wybKontakt.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

        startActivityForResult(wybKontakt,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri dane = data.getData();
            String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

            Cursor c = getContentResolver().query(dane, projection,null, null, null);
            c.moveToFirst();
            int numberColumnIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = c.getString(numberColumnIndex);

            int nameColumnIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String name = c.getString(nameColumnIndex);
            tvWyswietlaTelefon.setText(name.toString() + ": " + number.toString());

        }
    }


    @Override
    public void onClick(View v) {
        String tekstDoWyszukania = etTekstDoWyszukania.getText().toString();
        Intent iSzukaj = new Intent();

        iSzukaj.setAction(
                Intent.ACTION_WEB_SEARCH);

        iSzukaj.putExtra(SearchManager.QUERY,
                tekstDoWyszukania);

        startActivity(iSzukaj);

    }
}
