package com.app.homeycam.CustomeViews.CustomBottomView;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.homeycam.Fragments.LiveFragment;
import com.app.homeycam.LocalizationActivity.LocalizationActivity;
import com.app.homeycam.ModelClass.UserDevices.ProductAccess;
import com.app.homeycam.ModelClass.UserDevices.User;
import com.app.homeycam.ModelClass.UserDevices.UserDevices;
import com.app.homeycam.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomBarHolderActivity extends LocalizationActivity implements BottomNavigationBar.BottomNavigationMenuClickListener, AdapterView.OnItemSelectedListener {

    // helper class for handling UI and click events of bottom-nav-bar
    private BottomNavigationBar mBottomNav;

    Toolbar toolbar;

    TextView toolbar_txt;

    Spinner product_spinner;
    // list of Navigation pages to be shown
    private List<NavigationPage> mNavigationPageList = new ArrayList<>();
    List<ProductAccess> productAccesses;
    List<String> products;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar_holder);

        toolbar = findViewById(R.id.toolbar);
        toolbar_txt = findViewById(R.id.toolbar_title);
        product_spinner = findViewById(R.id.product_spinner);
        toolbar_txt.setText("EVENTS");


    }

    /**
     * initializes the BottomBarHolderActivity with sent list of Navigation pages
     *
     * @param pages
     */
    public void setupBottomBarHolderActivity(List<NavigationPage> pages) {

        // throw error if pages does not have 4 elements
        if (pages.size() != 3) {
            throw new RuntimeException("List of NavigationPage must contain 3 members.");
        } else {
            mNavigationPageList = pages;
            mBottomNav = new BottomNavigationBar(this, pages, this);
            setupFragments();
        }

    }

    /**
     * sets up the fragments with initial view
     */
    private void setupFragments() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, mNavigationPageList.get(1).getFragment());
        fragmentTransaction.commit();
        toolbar_txt.setVisibility(View.GONE);
        product_spinner.setOnItemSelectedListener(BottomBarHolderActivity.this);
        product_spinner.setVisibility(View.VISIBLE);
        toolbar_txt.setText("LIVE");
        getProducts();
    }

    /**
     * handling onclick events of bar items
     *
     * @param menuType
     */
    @Override
    public void onClickedOnBottomNavigationMenu(int menuType) {

        // finding the selected fragment
        Fragment fragment = null;
        switch (menuType) {
            case BottomNavigationBar.MENU_BAR_1:
                toolbar_txt.setVisibility(View.VISIBLE);
                product_spinner.setVisibility(View.GONE);
                toolbar_txt.setText("EVENTS");
                fragment = mNavigationPageList.get(0).getFragment();

                loginPrefManager.setStringValue("live_update", "0");
                break;
            case BottomNavigationBar.MENU_BAR_2:
                product_spinner.setOnItemSelectedListener(BottomBarHolderActivity.this);
                toolbar_txt.setVisibility(View.GONE);
                product_spinner.setVisibility(View.VISIBLE);
                toolbar_txt.setText("LIVE");
                fragment = mNavigationPageList.get(1).getFragment();

                break;
            case BottomNavigationBar.MENU_BAR_3:
                toolbar_txt.setVisibility(View.VISIBLE);
                product_spinner.setVisibility(View.GONE);
                toolbar_txt.setText("SETTINGS");
                loginPrefManager.setStringValue("live_update", "0");
                fragment = mNavigationPageList.get(2).getFragment();
                break;

        }

        // replacing fragment with the current one
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.commit();
        }

    }


    @Override
    public void onResume() {
        super.onResume();


        if (loginPrefManager.getDeviceUpdated()) {
            getProducts();

            loginPrefManager.setDeviceUpdated(false);
        }
    }

    private void getProducts() {
        try {

            show_loader("Loading");

            apiService.getConnectedDevices(loginPrefManager.getUserToken(), loginPrefManager.getUserId()).enqueue(new Callback<UserDevices>() {
                @Override
                public void onResponse(Call<UserDevices> call, Response<UserDevices> response) {

                    dismiss_loader();
                    products = new ArrayList<>();

                    if (response.body().getStatus().equalsIgnoreCase("ok")) {

                        User user = response.body().getData().getUser();

                        if (user != null) {

                            productAccesses = user.getProductAccess();

                            if (productAccesses.size() > 0) {

                                for (int i = 0; i < productAccesses.size(); i++) {
                                    products.add(productAccesses.get(i).getProductId().getProductName());
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(BottomBarHolderActivity.this, android.R.layout.simple_expandable_list_item_1, products);

                            product_spinner.setAdapter(adapter);
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserDevices> call, Throwable t) {

                }
            });


        } catch (Exception e) {

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        Log.e("itemselcted", productAccesses.get(i).getProductId().getId());

        loginPrefManager.setStringValue("product_id", productAccesses.get(i).getProductId().getId());


        LiveFragment fragment = (LiveFragment) mNavigationPageList.get(1).getFragment();


        fragment.emitallfaces();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
