package nyc.c4q.androidtest_unit4final;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ColorAdapter adapter;
    protected HashMap<String, String> colorDict;
    protected List<String> colorsList;
    private LinearLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = findViewById(R.id.fragment_container);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com")
          .addConverterFactory(GsonConverterFactory.create())
          .build();

        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<HashMap<String, String>> colors = service.fetchColors();
        colors.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                colorDict = response.body();
                colorsList = new ArrayList<>();
                colorsList.addAll(colorDict.keySet());

                RecyclerView recyclerView = findViewById(R.id.rv);
                adapter = new ColorAdapter(colorsList, colorDict);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

            }
        });

        //colorDict = new HashMap<>();
        //colorDict.put("indigo", "#4b0082");
        //colorDict.put("green", "#00ff00");
        //colorDict.put("blue", "#0000ff");
        //colorDict.put("red", "#ff0000");
        //// TODO: adding all the colors and their values would be tedious, instead fetch it from the url below
        //// https://raw.githubusercontent.com/operable/cog/master/priv/css-color-names.json

        //colorsList = new ArrayList<>();
        //String[] names = new String[] {"blue", "red", "purple", "indigo", "orange", "brown", "black", "green"};
        //for(String n: names) colorsList.add(n);
        //
        //RecyclerView recyclerView = findViewById(R.id.rv);
        //adapter = new ColorAdapter(colorsList, colorDict);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    // TODO: Add options menu with the item "Info" which is always visible
    // TODO: When "Info" menu item is clicked, display the fragment InfoFragment
    // TODO: If InfoFragment is already visible and I click "Info", remove InfoFragment from the view.
    // Link to creating options menu: https://github.com/C4Q/AC-Android/tree/v2/Android/Lecture-9-Menus-and-Navigation#anatomy-of-menu-xml

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.info:
                if(fragment.getVisibility() == View.VISIBLE) {
                    fragment.setVisibility(View.GONE);
                } else {
                    fragment.setVisibility(View.VISIBLE);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
