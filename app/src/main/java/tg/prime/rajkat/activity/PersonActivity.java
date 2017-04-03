package tg.prime.rajkat.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import tg.prime.rajkat.model.Person;
import tg.prime.rajkat.model.dao.DaoMaster;
import tg.prime.rajkat.model.dao.DaoSession;
import tg.prime.rajkat.model.dao.PersonDao;

public class PersonActivity extends AppCompatActivity {

    private final String BASE = "leodo_db";

    private ImageView picture;
    private EditText name, firstname, age;
    private Button valid;
    private Person person;
    private PersonDao personDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, BASE, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        personDao = daoSession.getPersonDao();

        picture = (ImageView) findViewById(R.id.picture);
        name = (EditText) findViewById(R.id.name);
        firstname = (EditText) findViewById(R.id.firstname);
        age = (EditText) findViewById(R.id.age);
        valid = (Button) findViewById(R.id.btn_valid);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/**");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            }
        });

        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!verification()){
                    //action to valid and save on sqlite
                    person = new Person();
                    person.setName(name.getText().toString());
                    person.setFirstname(firstname.getText().toString());
                    person.setAge(Integer.parseInt(age.getText().toString()));
                    personDao.insert(person);
                    Toast.makeText(PersonActivity.this, "Enregistrement effectué", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Check if it's not empty
     * @return boolean
     */
    private boolean verification(){
        boolean empty = false;
        if (name.getText().toString().trim().isEmpty() || firstname.getText().toString().trim().isEmpty()
                || age.getText().toString().trim().isEmpty()){
            empty = true;
            Toast.makeText(this, "Remplissez tous les champs", Toast.LENGTH_SHORT).show();
        }
        return empty;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_person_list){
            Intent intent = new Intent(PersonActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
