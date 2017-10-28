package guts.carpaltunnel.mobileapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class FacebookDemo extends AppCompatActivity {
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // DEBUG -- prints out the hashkey for the app, which can be registered with facebook
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "guts.carpaltunnel.mobileapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException ignored) {

        } catch (NoSuchAlgorithmException ignored) {

        }

        setContentView(R.layout.facebook_form);
        callbackManager = CallbackManager.Factory.create();

        if (AccessToken.getCurrentAccessToken() != null){
            makeAndFillRequest(AccessToken.getCurrentAccessToken());
        }

        final LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "user_birthday"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                makeAndFillRequest(accessToken);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException error) {

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void makeAndFillRequest(AccessToken token){
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

                        if (response.getError() == null){
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
        parameters.putString("fields","name,birthday,email");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
