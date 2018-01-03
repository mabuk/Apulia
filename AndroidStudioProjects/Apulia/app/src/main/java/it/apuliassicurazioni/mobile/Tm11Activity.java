package it.apuliassicurazioni.mobile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Tm11Activity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_tm11 );

        TextView ageText = findViewById( R.id.TextViewAge );

        long age = Session.getAge();
        ageText.setText( Long.toString( age ) );

        Spinner durata = findViewById( R.id.SpinnerDurata );
        List<String> list = new ArrayList<>();

        long maxDurata = 75 - age;
        if (maxDurata > 30) maxDurata = 30;

        list.add( "1 anno" );

        for (int i = 2; i < maxDurata + 1; i++) {
            list.add( i + " anni" );
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_item, list );
        dataAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        durata.setAdapter( dataAdapter );

        if (maxDurata >= 20) durata.setSelection( 19 );
        else durata.setSelection( (int) maxDurata - 1 );
    }

    public void calcola(View view) {
        DecimalFormat decimalFormat = new DecimalFormat( "#,##0.00" );

        int age = Session.getAge();

        EditText capitaleEnter = findViewById( R.id.capitale );

        if (TextUtils.isEmpty( capitaleEnter.getText().toString() )) {
            capitaleEnter.setError( "Inserire un valore" );
            return;
        }

        double capitale = Double.parseDouble( capitaleEnter.getText().toString() );

        Spinner durataEnter = findViewById( R.id.SpinnerDurata );

        int durata = durataEnter.getSelectedItemPosition() + 1;

        Spinner frazEnter = findViewById( R.id.SpinnerFrazionamento );

        int fraz = frazEnter.getSelectedItemPosition();

        double puro = Session.getTassoTm11( age - 18, durata - 1 );

        double coeff = Utils.arrotonda( puro / 0.75 ) / 1000.0;
        double diritti = 15;

        Garanzia infortuni1 = new Garanzia( capitale * 0.001, fraz );
        Garanzia infortuni2 = new Garanzia( capitale * 0.001, fraz );
        Garanzia tariffa = new Garanzia( capitale * coeff, fraz, 0 );
        Garanzia esonero = new Garanzia( tariffa.imponibile * Session.getTassoEsonero( age, durata ), fraz );

        double premio = infortuni1.premio() + infortuni2.premio() + esonero.premio() + tariffa.premio() + diritti;

        ((TextView) findViewById( R.id.premio )).setText( decimalFormat.format( premio ) + " €" );

        double rata = infortuni1.rata() + infortuni2.rata() + esonero.rata() + tariffa.rata();

        ((TextView) findViewById( R.id.premio_rate )).setText( decimalFormat.format( rata ) + " €" );
    }

    public void stampa(View view) {
        Intent intent = new Intent( this, StampaActivity.class );
        startActivity( intent );
    }

    public void goNascita(View view) {
        Intent intent = new Intent( this, NascitaActivity.class );
        intent.putExtra("NextActivity", 1);
        startActivity( intent );
    }
}
