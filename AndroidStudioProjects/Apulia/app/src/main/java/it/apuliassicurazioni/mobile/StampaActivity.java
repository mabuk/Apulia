package it.apuliassicurazioni.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StampaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_stampa );
    }

    public void buildPdf(View view) {
        Intent intent = new Intent( this, StampaActivity.class );
        startActivity( intent );
    }
}
