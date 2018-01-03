package it.apuliassicurazioni.mobile;

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

public class C45Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_c45 );

        TextView ageText = findViewById( R.id.TextViewAge );

        long age = Session.getAge();
        ageText.setText( Long.toString( age ) );

        Spinner durata = findViewById( R.id.SpinnerDurata );
        List<String> list = new ArrayList<>();

        long maxDurata = 80 - age;
        if (maxDurata > 30) maxDurata = 30;

        for (int i = 5; i <= maxDurata; i++) {
            list.add( i + " anni" );
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_item, list );
        dataAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        durata.setAdapter( dataAdapter );

        if (maxDurata >= 20) durata.setSelection( 15 );
        else durata.setSelection( (int) maxDurata - 1 );
    }

    public void calcola(View view) {
        DecimalFormat decimalFormat = new DecimalFormat( "#,##0.00" );

        int age = Session.getAge();

        EditText inizialeEnter = findViewById( R.id.iniziale );

        if (TextUtils.isEmpty( inizialeEnter.getText().toString() )) {
            inizialeEnter.setError( "Inserire un valore" );
            return;
        }

        double iniziale = Double.parseDouble( inizialeEnter.getText().toString() );

        EditText aggiuntivoEnter = findViewById( R.id.aggiuntivo );

        if (TextUtils.isEmpty( aggiuntivoEnter.getText().toString() )) {
            aggiuntivoEnter.setError( "Inserire un valore" );
            return;
        }

        double aggiuntivo = Double.parseDouble( aggiuntivoEnter.getText().toString() );

        Spinner durataEnter = findViewById( R.id.SpinnerDurata );

        int durata = durataEnter.getSelectedItemPosition();

        Spinner frazEnter = findViewById( R.id.SpinnerFrazionamento );

        int fraz = frazEnter.getSelectedItemPosition();

        Spinner provvEnter = findViewById( R.id.SpinnerProvvigioni );

        int provv = provvEnter.getSelectedItemPosition();

        double cdiffpa = Session.getTassoC45( age - 18, durata, provv == 1 );

        double diritti = 0;

        double pu = Session.getCaricamentoPu( aggiuntivo, provv == 0 ? 1 : 0.5 );

        double capitaleInizialePremioUnico = (1 - pu) * aggiuntivo;

        double capitaleIniziale = (getPremioAnnualizzatoNettoFraz( iniziale, fraz ) - diritti) / cdiffpa;

        double premio = capitaleInizialePremioUnico + capitaleIniziale;

        ((TextView) findViewById( R.id.premio )).setText( decimalFormat.format( premio ) + " €" );
    }

    private double getPremioAnnualizzatoNettoFraz(double capitale, int fraz) {
        if (fraz == 1) return Utils.arrotonda( capitale * 4 / (1 + 0.025) );
        if (fraz == 2) return Utils.arrotonda( capitale * 2 / (1 + 0.02) );
        return capitale;
    }

    public void goNascita(View view) {
        Intent intent = new Intent( this, NascitaActivity.class );
        intent.putExtra( "NextActivity", 2 );
        startActivity( intent );
    }

    public void stampa(View view) {
        Intent intent = new Intent( this, StampaActivity.class );
        startActivity( intent );
    }
}
