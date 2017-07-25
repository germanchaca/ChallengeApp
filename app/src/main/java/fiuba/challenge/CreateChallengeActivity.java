package fiuba.challenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import fiuba.challenge.youtube.YoutubeUploadActivity;

public class CreateChallengeActivity extends AppCompatActivity {


    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    private Toolbar toolbar;
    private EditText inputDescruptionEditText;
    private EditText inputTitleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_challenge);

        toolbar = (Toolbar) findViewById(R.id.createChallengeToolbar);
        inputDescruptionEditText = (EditText) findViewById(R.id.input_description);
        inputTitleEditText = (EditText) findViewById(R.id.input_title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void selectVideo(View view){
        Intent intent = new Intent(this,YoutubeUploadActivity.class);
        intent.putExtra(TITLE, inputTitleEditText.getText().toString());
        intent.putExtra(DESCRIPTION, inputDescruptionEditText.getText().toString());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_challenge_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

      }

        return super.onOptionsItemSelected(item);
    }

}
