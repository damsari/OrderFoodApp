package com.example.orderfoodapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.orderfoodapp.Common.Common;
import com.example.orderfoodapp.Interface.ItemClickListener;
import com.example.orderfoodapp.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;


public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;


    TextView txtFullName;
    RecyclerView recyler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<category,MenuViewHolder>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //init firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent cartIntent = new Intent(Home.this,Cart.class);
               startActivity(cartIntent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //set name for user
        View headerView = navigationView.getHeaderView(0);
        txtFullName=(TextView)headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        //load menu
        recyler_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recyler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyler_menu.setLayoutManager(layoutManager);

        loadMenu();


    }

    private void loadMenu(){
        adapter = new FirebaseRecyclerAdapter<com.example.orderfoodapp.category, MenuViewHolder>(category.class,R.layout.menu_item,MenuViewHolder.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, com.example.orderfoodapp.category Category, int position) {
                menuViewHolder.txtMenuName.setText(Category.getName());

                Picasso.with(getBaseContext()).load(Category.getImage())
                        .into(menuViewHolder.imageView);
                final category clickItem = Category;

                menuViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick){


                        //get Category id
                        Intent foodList = new Intent (Home.this,FoodList.class);
                        //because category id is key
                        foodList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(foodList);
                    }
                });
            }
        };
        recyler_menu.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {

        } else if (id == R.id.nav_cart) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_log_out) {



        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
