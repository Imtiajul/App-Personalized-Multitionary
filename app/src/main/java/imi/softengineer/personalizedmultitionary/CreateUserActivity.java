package imi.softengineer.personalizedmultitionary;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by ImtiajulIslam on 2/24/2018.
 */

public class CreateUserActivity extends AppCompatActivity {

    private final String TAG = "CreateUser";

    EditText word, description, link;
    Button btSave;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        toolbar = findViewById(R.id.creat_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        word = findViewById(R.id.edWord);
        description = findViewById(R.id.edDescription);
        link = findViewById(R.id.edLink);
        btSave = findViewById(R.id.btSave);

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "production")
                .allowMainThreadQueries()
                .build();


        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 3/13/2018 Save to database
                Log.d(TAG, "onClick: " + word.getText().toString());

                String woord = word.getText().toString().trim();

                if (woord.isEmpty()) {
                    word.setError("You must assign word");
                } else {
                    User user = new User(woord, description.getText().toString(), link.getText().toString());
                    db.userDao().insertAll(user);

                    startActivity(new Intent(CreateUserActivity.this, HomeActivity.class));
                }

                //List<String> list = db.userDao().getAllWord();
                /////////////////////////////////////////////////////////////////////////////////////


            }
        });

    }
}