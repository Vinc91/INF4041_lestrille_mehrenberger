package org.esiea.mehrenberger_lestrille.myapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.graphics.BitmapFactory;
import android.app.Notification;

/**
 * Created by pierremehrenberger on 07/11/2016.
 */
public class RegisterActivity extends AppCompatActivity {
    Button bRegister;
    Button btnNotif;
    //EditText name, age, uName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnNotif = (Button) findViewById(R.id.bRegister);
        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNotification();
            }
        });

        Toolbar toolbar=(Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //name =(EditText)findViewById(R.id.editName);
        //age =(EditText)findViewById(R.id.editAge);
        //uName =(EditText)findViewById(R.id.editUname);
        //password=(EditText)findViewById(R.id.editPass);
        //name.setTextColor(0xff000000);
        //age.setTextColor(0xff000000);
        //uName.setTextColor(0xff000000);
        //password.setTextColor(0xff000000);
        //bRegister =(Button)findViewById(R.id.bRegister);
      /*
      GetBiersServices.startActionGet_All_Biers(this);
      IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
      LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(), intentFilter);
      */
    }
    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_beer)
                        .setContentTitle("Successful !")
                        .setContentText("Votre commande est en chemin / Your order is on the way");

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

