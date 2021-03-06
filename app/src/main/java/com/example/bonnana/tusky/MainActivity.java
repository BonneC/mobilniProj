package com.example.bonnana.tusky;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.bonnana.tusky.adapter.CardViewAdapter;
import com.example.bonnana.tusky.adapter.TabsAdapter;
import com.example.bonnana.tusky.model.Topic;
import com.example.bonnana.tusky.model.TopicList;
import com.example.bonnana.tusky.network.RetrofitInstance;
import com.example.bonnana.tusky.services.TopicServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private TopicList topicList = new TopicList();
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    UserTasksActivity ut;


//    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
//            int position = viewHolder.getAdapterPosition();
//
//            Topic topic = topicList.getTopicArrayList().get(position);
//            String id = topic.getId();
//
//            callExplicitIntent(id);
//        }
//    };
//
//    public void callExplicitIntent(String id) {
//        Intent intent = new Intent(this, TopicTasksActivity.class);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, id);
//        startActivity(intent);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_logo_ver12);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String token = sharedPref.getString("idToken", "none");

        if (token.equals("none")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        } else {
            tabLayout = findViewById(R.id.tabs);
            viewPager = findViewById(R.id.viewpager);
            TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());
            ut = new UserTasksActivity();

            adapter.addFragment(ut, "Tasks");
            adapter.addFragment(new TopicsActivity(), "Topics");


            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        }


//
//        layoutManager = new GridLayoutManager(this, 3);
//        recyclerView.setLayoutManager(layoutManager);

//        toolbar = (Toolbar) findViewById(R.id.nav_bar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            sharedPref.edit().remove("idToken").apply();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    //    public void getTopics(){
//        Intent intent = new Intent(this, TopicsActivity.class);
//
//        startActivity(intent);
//    }
//
//    public void getTasks(View view) {
//        Intent intent = new Intent(this, UserTasksActivity.class);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_TEXT, "1");
//        startActivity(intent);
//    }
//
//    public void getTopics(View view) {
//        getTopics();
//    }
}
