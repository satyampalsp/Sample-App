package sample.daffodil.sample2.Activities;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import sample.daffodil.sample2.Fragments.HomeFragment;
import sample.daffodil.sample2.Fragments.MapsFragment;
import sample.daffodil.sample2.Fragments.ProfileFragment;
import sample.daffodil.sample2.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String url;
    private AdView mAdView;
    ImageView profileImage;
    FirebaseAuth auth;
    private FirebaseAnalytics mFirebaseAnalytics;
    InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5654044967858938/7431275144");
        mAdView = (AdView) findViewById(R.id.adView);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "user");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "test");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        AdRequest adRequest = new AdRequest.Builder().build();
        MobileAds.initialize(this, String.valueOf(R.string.banner_ad_unit_id));
        mAdView.loadAd(adRequest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        auth=FirebaseAuth.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View profileView=navigationView.getHeaderView(0);
        profileImage=(ImageView)profileView.findViewById(R.id.id_profile_image);

        Fragment f=new HomeFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.id_main_fragment_frame,f);
        ft.commit();
        SharedPreferences sp = this.getSharedPreferences("Mypref", 0);
        url=sp.getString("picUrl",null);
        if(url!=null){
            Picasso.with(this).load(url).fit().into(profileImage);
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f=new ProfileFragment();
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.id_main_fragment_frame,f);
                ft.addToBackStack(null);
                ft.commit();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        // TODO: Move this to where you establish a user session
        logUser();

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Fragment f=new HomeFragment();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.id_main_fragment_frame,f);
            ft.addToBackStack(null);
            ft.commit();
        } else if (id == R.id.nav_products) {

        } else if (id == R.id.nav_categories) {

        } else if (id == R.id.nav_aboutUs) {

        }
        else if (id == R.id.nav_maps) {
            Fragment f=new MapsFragment();
            FragmentManager fm=getSupportFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            ft.replace(R.id.id_main_fragment_frame,f);
            ft.addToBackStack(null);
            ft.commit();
        }
        else if(id==R.id.nav_logout){
            SharedPreferences sp=getApplicationContext().getSharedPreferences("Mypref",0);
            SharedPreferences.Editor editor=sp.edit();
            editor.putString("email","");
            editor.putString("pass","");
            editor.putBoolean("flag",false);
            editor.apply();
//            FirebaseAuth auth=FirebaseAuth.getInstance();
//            auth.signOut();
//
//            FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
//                @Override
//                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                    if (user == null) {
//                        // user auth state is changed - user is null
//                        // launch login activity
//                        startActivity(new Intent(MainActivity.this, FirstActivity.class));
//                        finish();
//                    }
//                }
//            };
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
//    public void SetImageView(String Url){
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        View profileView=navigationView.getHeaderView(0);
//        profileImage=(ImageView)profileView.findViewById(R.id.id_profile_image);
//        Picasso.with(this).load(Url).fit().into(profileImage);
//    }
private void logUser() {
    // TODO: Use the current user's information
    // You can call any combination of these three methods
    Crashlytics.setUserIdentifier("12345");
    Crashlytics.setUserEmail("user@fabric.io");
    Crashlytics.setUserName("Test User");
}

}
