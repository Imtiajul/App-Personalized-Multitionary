package imi.softengineer.personalizedmultitionary;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private static final String FILE_NAME = "CustomDictionary.dic";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    Toolbar toolbar;
    AppDatabase db;
    //ArrayList <User> users;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycleView);
        toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        /*users= new ArrayList<>();

        for (int i = 0; i <100 ; i++) {
            User user = new User("Quran # "+ i , "It was written by Allah", "Allah.com");

            users.add(user);
       }*/

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();
        List<User> users = db.userDao().getAllUsers();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionBar_help:
                startActivity(new Intent(this, HelpActivity.class));
                break;

            case R.id.actionBar_Setting:
                startActivity(new Intent(this, SettingsActivity.class));
                break;

            case R.id.actionBar_developer_info:
                startActivity(new Intent(this, Developer_InfoActivity.class));
                break;

            case R.id.actionBar_licence_info:
                startActivity(new Intent(this, Licence_infoActivity.class));
                break;

            case R.id.actionBar_signOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this, SignInActivity.class));
                break;

            default:
                Toast.makeText(getApplicationContext(), "Unexpected Error Occured", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void fBtonCreate(View view) {
        startActivity(new Intent(HomeActivity.this, CreateUserActivity.class));
    }

    public void fBtonDelete(View view) {
        db.userDao().deleteAll();
        startActivity(new Intent(HomeActivity.this, HomeActivity.class));
    }

    public void generateDicFile(View view) {
        //startActivity(new Intent(this, DicFile.class));

        String state;
        state = Environment.getExternalStorageState();

        String data = String.valueOf(db.userDao().getAllWord());
        //Filtering
        data = data.replace("[", "");
        data = data.replace("]", "");
        data = data.replace(", ", "\n");


        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/MyAppFiles");
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            File file = new File(Dir, FILE_NAME);

            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data.getBytes());
                fos.close();
                //list.clear();
                Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "SD card Not Found", Toast.LENGTH_SHORT).show();
        }

    }
}
