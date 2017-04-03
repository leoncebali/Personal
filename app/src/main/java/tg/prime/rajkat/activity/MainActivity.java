package tg.prime.rajkat.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import tg.prime.rajkat.model.Person;
import tg.prime.rajkat.model.dao.DaoMaster;
import tg.prime.rajkat.model.dao.DaoSession;
import tg.prime.rajkat.model.dao.PersonDao;

public class MainActivity extends AppCompatActivity {

    private final String BASE = "leodo_db";

    private Person person;
    private PersonDao personDao;
    private ArrayList<Person> persons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, BASE, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        personDao = daoSession.getPersonDao();

        TextView display = (TextView) findViewById(R.id.display);
        display.setText("Nombre de person enregistré: "+personDao.count());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                startActivity(intent);
            }
        });
    }

}
