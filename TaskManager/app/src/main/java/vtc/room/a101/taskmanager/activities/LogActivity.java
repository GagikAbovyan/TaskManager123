package vtc.room.a101.taskmanager.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import vtc.room.a101.taskmanager.R;
import vtc.room.a101.taskmanager.fragmens.LoginFragment;

public class LogActivity extends AppCompatActivity {


    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        fragmentManager = getSupportFragmentManager();
        openFragment(new LoginFragment());
    }


    private void openFragment(final Fragment fragment){
        fragmentManager.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }
}
