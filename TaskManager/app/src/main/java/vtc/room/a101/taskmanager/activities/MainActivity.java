package vtc.room.a101.taskmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import vtc.room.a101.taskmanager.R;
import vtc.room.a101.taskmanager.adapters.PagesAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showImages();
        startNewActivity();
    }


    private void startNewActivity(){
        final ImageButton close = (ImageButton) findViewById(R.id.close_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
            }
        });
    }


    private void showImages(){
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final int[] images = {R.drawable.task, R.drawable.sport, R.drawable.work, R.drawable.lesson};
        PagerAdapter pagerAdapter = new PagesAdapter(this, images);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
    }
}
