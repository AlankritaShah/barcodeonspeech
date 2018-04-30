package munshig.in.kiranamark3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


public class BillSummary extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener

        {

    LinearLayout linearLayout;
   // ItemFragment ifrag;
            private static final int CONTENT_VIEW_ID = 10101010;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_bill_summary);
//        FrameLayout frame = new FrameLayout(this);
//        frame.setId(CONTENT_VIEW_ID);
        setContentView(R.layout.activity_bill_summary);
//        Fragment fragment = new ItemFragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

//        addFragment(android.R.id.content,
//                new ItemFragment(),
//                ItemFragment.FRAGMENT_TAG);

//        if (savedInstanceState == null) {
////            Fragment newFragment = new ItemFragment();
////            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////            ft.add(CONTENT_VIEW_ID, newFragment).commit();
//        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

       // ifrag = new ItemFragment();

        Toolbar fab = (Toolbar) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Bill saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView.getHeaderView(0);
        linearLayout = (LinearLayout)header.findViewById(R.id.linearlayout);
        EditText ed = (EditText) header.findViewById(R.id.searcharea);

//        DummyContent.DummyItem item = new DummyContent.DummyItem("0","1","2");
//        onListFragmentInteraction(item);

    }

//            protected void addFragment(@IdRes int containerViewId,
//                                       @NonNull Fragment fragment,
//                                       @NonNull String fragmentTag) {
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(containerViewId, fragment, fragmentTag)
//                        .disallowAddToBackStack()
//                        .commit();
//            }

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
        getMenuInflater().inflate(R.menu.bill_summary, menu);
        MenuItem item=menu.getItem(0);
       // MenuItem item = (MenuItem) menu.findViewById(R.id.action_settings);
        onOptionsItemSelected(item);
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
            Log.i("settings?", "clicked");
            item.setTitle("Items: " + getnumberofitems("billname"));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public int getnumberofitems(String billname)
    {
        return 0;
    }
//            @Override
//            public void onListFragmentInteraction(DummyContent.DummyItem item)
//            {
//                ItemFragment articleFrag = (ItemFragment)
//                        getSupportFragmentManager().findFragmentById(R.id.frame);
//
//                if (articleFrag != null) {
//                    // If article frag is available, we're in two-pane layout...
//                    Log.i("what?", "is this?");
//                    // Call a method in the ArticleFragment to update its content
//                  //  articleFrag.updateArticleView(item);
//                } else {
//                    // Otherwise, we're in the one-pane layout and must swap frags...
//
//                    Log.i("what?", "is this2");
//                    // Create fragment and give it an argument for the selected article
//                    ItemFragment newFragment = new ItemFragment();
//                    Bundle args = new Bundle();
//                    args.putInt(ItemFragment.ARG_COLUMN_COUNT, 25);
//                    newFragment.setArguments(args);
//
//                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//                    // Replace whatever is in the fragment_container view with this fragment,
//                    // and add the transaction to the back stack so the user can navigate back
//                    transaction.replace(R.id., newFragment);
//                    transaction.addToBackStack(null);
//
//                    // Commit the transaction
//                    transaction.commit();
//                }
//            }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
