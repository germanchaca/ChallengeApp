package fiuba.challenge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {
    private Button signOutButton;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_profile, container, false);

        signOutButton = (Button) rootView.findViewById(R.id.buttonSignOut);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        return rootView;
    }

    public void aboutUs(){

    }
    public void signOut(){
        SharedPreferences settings = getContext().getSharedPreferences(LoginActivity.APP, Context.MODE_PRIVATE);

        if (settings.contains(LoginActivity.PREF_ACCOUNT_NAME)) {
            SharedPreferences.Editor editor = settings.edit();
            editor.remove(LoginActivity.PREF_ACCOUNT_NAME);
            editor.apply();
        }

        Intent intent = new Intent(getContext(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
