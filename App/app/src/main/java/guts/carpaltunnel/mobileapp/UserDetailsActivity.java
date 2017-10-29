package guts.carpaltunnel.mobileapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import guts.carpaltunnel.mobileapp.util.FormManager;

public class UserDetailsActivity extends AppCompatActivity{

    Activity ctx = this;
    CallbackManager callbackManager;

    EditText full_name;
    EditText date_of_birth;
    EditText email;
    Button submit_form;
    LoginButton facebook_login_button;

    private Toolbar mTopToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_user_details);

        full_name = findViewById(R.id.full_name);
        date_of_birth = findViewById(R.id.date_of_birth);
        email = findViewById(R.id.email);
        facebook_login_button = findViewById(R.id.facebook_login_button);
        submit_form = findViewById(R.id.submit_form);




        facebook_login_button.setReadPermissions(Arrays.asList("email", "user_birthday"));
        facebook_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                facebookAutofill(accessToken);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }

        });

        if (AccessToken.getCurrentAccessToken() != null) {
            facebookAutofill(AccessToken.getCurrentAccessToken());
        }


        submit_form.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                FormManager formManager = ((HSApplication)getApplicationContext()).formManager;
                formManager.setField("name",full_name.getText());
                formManager.setField("birthday",date_of_birth.getText());
                formManager.setField("email",email.getText());

                Intent myIntent = new Intent(view.getContext(), LocationActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void facebookAutofill(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        EditText full_name = findViewById(R.id.full_name);
                        EditText date_of_birth = findViewById(R.id.date_of_birth);
                        EditText email = findViewById(R.id.email);

                        if (response.getError() == null) {
                            try {
                                full_name.setText(response.getJSONObject().getString("name"));
                                date_of_birth.setText(response.getJSONObject().getString("birthday"));
                                email.setText(response.getJSONObject().getString("email"));
                            } catch (JSONException ignored) {
                            }
                        } else {
                            full_name.setText(response.getError().toString());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,birthday,email");
        request.setParameters(parameters);
        request.executeAsync();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            Intent myIntent = new Intent(ctx, ChatbotActivity.class);
            startActivityForResult(myIntent, 0);
//            Toast.makeText(WelcomeActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

