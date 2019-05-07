package com.example.lab04;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity {
    private ArrayList<String> target;
    private SimpleCursorAdapter adapter;
    MySQLite db = new MySQLite(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        String[] values = new String[]{"Pies",
                        "Kot", "Koń", "Gołąb", "Kruk", "Dzik", "Karp",
                        "Osioł", "Chomik", "Mysz", "Jeż", "Kraluch"
        };
        this.target = new ArrayList<String>();
        this.target.addAll(Arrays.asList(values));
        this.adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                db.lista(),
                new String[] {"_id", "species"},
                new int[] {android.R.id.text1,
                        android.R.id.text2},

                SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE

        );


        ListView listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(this.adapter);
        listview.setOnItemClickListener(new
                AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?>
                                                    adapter, View view, int pos, long id)
                    {
                        TextView name = (TextView) view.findViewById(android.R.id.text1);
                        Animal zwierz = db.pobierz(Integer.parseInt (name.getText().toString()));
                        Intent intencja = new
                                Intent(getApplicationContext(),
                                DodajWpis.class);
                        intencja.putExtra("element", zwierz);
                        startActivityForResult(intencja, 2);

                    }
                });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView name = (TextView) view.findViewById(android.R.id.text1);
                Animal zwierz = db.pobierz(Integer.parseInt (name.getText().toString()));
                int id_ob = zwierz.get_id();
                db.usun(String.valueOf(id_ob));
                adapter.changeCursor(db.lista());
                adapter.notifyDataSetChanged();
                return true;
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public void nowyWpis(MenuItem mi)
    {
        Intent intencja = new Intent(this,
                DodajWpis.class);
        startActivityForResult(intencja, 1);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            //String nowy = (String)extras.get("wpis");
            Animal nowy = (Animal)
                    extras.getSerializable("nowy");
            //target.add(nowy);
            this.db.dodaj(nowy);
            adapter.changeCursor(db.lista());
            adapter.notifyDataSetChanged();
        }
        if(requestCode==2 && resultCode==RESULT_OK)
        {
            Bundle extras = data.getExtras();
            //String nowy = (String)extras.get("wpis");
            Animal nowy = (Animal)
                    extras.getSerializable("nowy");
            //target.add(nowy);
            this.db.aktualizuj(nowy);
            adapter.changeCursor(db.lista());
            adapter.notifyDataSetChanged();
        }
    }
}
