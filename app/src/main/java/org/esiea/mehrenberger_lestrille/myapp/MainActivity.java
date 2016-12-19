package org.esiea.mehrenberger_lestrille.myapp;

import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.SearchView;
import android.support.v4.view.MenuItemCompat;

import android.widget.TextView;
import android.widget.FrameLayout;
import android.widget.Toast;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.GestureDetector;
import android.view.MotionEvent;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.DialogInterface;

import android.util.Log;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private static final String TAG= "BeersServices";
    private JSONArray jsonBeer;
    private RecyclerView recycler= null;
    private AlertDialog.Builder alertDBuild= null;
    private AlertDialog alertD= null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Défini la bar d'outil (en haut de l'appli)
        toolbar=(Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //Défini le recycleView (liste de produits)
        recycler = (RecyclerView) findViewById(R.id.rev_biere);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //Défini le button "Order Beer/Commander Biere"
        TextView buttonOrder = (TextView) findViewById(R.id.button_order);
        getString(R.string.dispo);

        //Défini le NavigationDrawerFragment (menu lateral gauche coulissant)
        NavigationDrawerFragment menulateral = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        menulateral.setUp(R.id.fragment_navigation_drawer,(DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        //Défini le recycleView (liste de produits)
        recycler = (RecyclerView) findViewById(R.id.rev_biere);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        BeersServices.startActionGet_All_Biers(this);

        recycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recycler, new ClickListener() {

            //Afin de simuler un vrai stock de produit, nous avons decider de mettre en indisponible certain produit
            @Override
            public void onClick(View view, int position) {
                if(position==1|| position==4|| position== 5|| position== 8|| position== 11|| position== 13|| position== 14
                        || position== 17|| position== 20|| position== 25|| position== 28|| position==33|| position== 36){
                    Toast.makeText(MainActivity.this, "n°"+position+ " "+getString(R.string.noSupply), Toast.LENGTH_SHORT).show();
                }
                else {Toast.makeText(MainActivity.this, "n°"+position+ " "+getString(R.string.supply), Toast.LENGTH_SHORT).show();}
            }

            //Affichage d'un AlertDialog pour "l'ajout du produit" dans le panier
            @Override
            public void onLongClick(View view, final int  position) {
                if (position==1|| position==4|| position== 5|| position== 8|| position== 11|| position== 13|| position== 14
                        || position== 17|| position== 20|| position== 25|| position== 28|| position==33|| position== 36) {
                    Toast.makeText(MainActivity.this, "n°" + position + " " + getString(R.string.noSupply_lg_click), Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Validation")
                            .setMessage(getString(R.string.addMessage))
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "n°" + position + " " + getString(R.string.addedMessage), Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "" + getString(R.string.msg_cancel), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show();
                }
            }
        }));

        jsonBeer = getBiersFromFile();
        recycler.setAdapter(new BiersAdapter(jsonBeer));

        IntentFilter intentFilter = new IntentFilter(BIERS_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BierUpdate(), intentFilter);

        alertDBuild = new AlertDialog.Builder(this);
        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.show();
            }
        });

        alertDBuild = new AlertDialog.Builder(this).setTitle("Validation").setMessage("Souhaitez-vous valider cette action ?").setPositiveButton("yes", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),getString(R.string.msg_pay),Toast.LENGTH_LONG).show();
                intentFonction();
            }
            //Si l'on reponds "non"
        }).setNegativeButton("No",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(), getString(R.string.msg_cancel), Toast.LENGTH_LONG).show();
            }
        }).setIcon(android.R.drawable.ic_dialog_alert);
        final FrameLayout frameView = new FrameLayout(this);
        alertDBuild.setView(frameView);
        alertD = alertDBuild.create();
        LayoutInflater inflater = alertD.getLayoutInflater();
    }


    void intentFonction() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement the filter logic
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, ""+item.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        }
        if(id ==R.id.web){
            startActivity(new Intent(this, WebActivity.class));
        }
        if(id ==R.id.profile){
            startActivity(new Intent(this, ProfileActivity.class));
        }
        if(id==android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    public static final String BIERS_UPDATE = "com.octip.inf4042_11.BIERS_UPDATE";

    public class BierUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, getIntent().getAction());
            // Mettre une notification ici
            recycler.setAdapter(new BiersAdapter(getBiersFromFile()));
        }
    }

    public JSONArray getBiersFromFile() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private class BiersAdapter extends RecyclerView.Adapter<BiersAdapter.BierHolder> {
        private JSONArray biers;

        public BiersAdapter(JSONArray jsonarray) {
            this.biers = jsonarray;
        }

        @Override
        public BierHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            LayoutInflater li = LayoutInflater.from(viewGroup.getContext());

            View v = li.inflate(R.layout.rv_bier_element, viewGroup, false);

            return new BierHolder(v);
        }

        @Override
        public void onBindViewHolder(BierHolder bierHolder, int i) {

            try{
                JSONObject jObj= biers.getJSONObject(i);
                String jS= jObj.getString("name"); //PREND LE NOM SUR LE SITE
                bierHolder.name.setText(jS);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return biers.length();
        }

        class BierHolder extends RecyclerView.ViewHolder {
            TextView name;
            public BierHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.rv_bier_element_name);
            }
        }
    }
    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{


        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            Log.d("BLA","constructor invoked");
            this.clickListener=clickListener;
            gestureDetector= new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("BLA","onSimpleTapUp");
                    return true;

                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child =recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener!=null){

                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                    Log.d("BLA","onLongPress");
                    // super.onLongPress(e);
                }
            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("BLA","onInterceptTouchEvent"+gestureDetector.onTouchEvent(e)+" " +e);
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if (child!=null && clickListener!=null && gestureDetector.onTouchEvent(e)){
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.d("BLA","onTouchEvent" +e);

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }

}