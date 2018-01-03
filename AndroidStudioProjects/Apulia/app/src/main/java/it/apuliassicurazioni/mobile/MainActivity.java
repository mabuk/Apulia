package it.apuliassicurazioni.mobile;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    public void goTM11(View view) {
        if (Session.getDataNascita() == null) {
            goNascita( view, 1 );
        } else {
            Intent intent = new Intent( this, Tm11Activity.class );
            startActivity( intent );
        }
    }

    public void goC45(View view) {
        if (Session.getDataNascita() == null) {
            goNascita( view, 2 );
        } else {
            Intent intent = new Intent( this, C45Activity.class );
            startActivity( intent );
        }
    }

    public void goM50(View view) {
        if (Session.getDataNascita() == null) {
            goNascita( view, 3 );
        } else {
            Intent intent = new Intent( this, M50Activity.class );
            startActivity( intent );
        }
    }

    public void goNascita(View view, int nextAct) {
        Intent intent = new Intent( this, NascitaActivity.class );
        intent.putExtra("NextActivity", nextAct);
        startActivity( intent );
    }
}
