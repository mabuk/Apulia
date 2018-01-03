package it.apuliassicurazioni.mobile;

import android.content.Intent;

import java.util.Calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

public class NascitaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_nascita );

        DatePicker dp = findViewById( R.id.simpleDatePicker );

        Calendar cal = Session.getDataNascita();

        Calendar maxDate = Calendar.getInstance();
        maxDate.add( Calendar.YEAR, -18 );

        Calendar minDate = Calendar.getInstance();
        minDate.add( Calendar.YEAR, -getMaxAnni() );

        if (cal == null) {
            cal = maxDate;
        }

        dp.setMaxDate( maxDate.getTimeInMillis() );
        dp.setMinDate( minDate.getTimeInMillis() );

        dp.init( cal.get( Calendar.YEAR ), cal.get( Calendar.MONTH ), cal.get( Calendar.DAY_OF_MONTH ), new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                Calendar c = Calendar.getInstance();
                c.set( year, month, day );
                Session.setDataNascita( c );
            }
        } );
    }

    private Class getNextAvtivity() {
        int n = getIntent().getExtras().getInt( "NextActivity" );
        switch (n) {
            case 1:
            default:
                return Tm11Activity.class;
            case 2:
                return C45Activity.class;
            case 3:
                return M50Activity.class;
        }
    }

    private int getMaxAnni() {
        int n = getIntent().getExtras().getInt( "NextActivity" );
        switch (n) {
            case 1:
                return 74;
            case 2:
            case 3:
            default:
                return 75;
        }
    }

    public void goNext(View view) {
        Intent intent = new Intent( this, getNextAvtivity() );
        startActivity( intent );
    }
}
