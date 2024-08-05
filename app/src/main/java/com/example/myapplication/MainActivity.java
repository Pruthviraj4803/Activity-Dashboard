package com.example.myapplication;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.app.databinding.MainActivtyBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


import java.util.Set;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MainActivtyBinding binding;
    private EventViewModel eventViewModel;
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private Spinner spinnerList;
    public EditText e1,e2;

    private static final List<String> DEFAULT_OPTION =
            Arrays.asList(new String[]{"All"});

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ImageView iv_mic,addlist;


    //private String[] lists = {"All", "Project", "Shopping", "Important","Add List"};
  //  List<String> lists = new ArrayList<>();
  //  SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
   // SharedPreferences.Editor editor = pref.edit();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = MainActivtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        recyclerView = binding.recycler;
        spinnerList = binding.listspinner;
        FloatingActionButton fab = binding.fabAddTask;

        adapter = new EventAdapter(eventViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        e1 = binding.taskname;
        e2=binding.listname;
        iv_mic = binding.mike;
        addlist=binding.addlist;
        iv_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");

                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast
                            .makeText(MainActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

     List<String> spinnerOptions=getOptionsFromSharedPreferences(this);
        spinnerList.setOnItemSelectedListener(this);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerOptions);
      listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnerList.setAdapter(listAdapter);
        addlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newOption = e2.getText().toString();
                if (!newOption.isEmpty()) {
                    listAdapter.add(newOption);
                    listAdapter.notifyDataSetChanged();

                    addOptionToSharedPreferences(MainActivity.this,spinnerOptions);
                    e2.setText(""); // Clear the input field
                }
            }
        });


        fab.setOnClickListener(v -> {

            String taskname = e1.getText().toString();

            Intent intent = new Intent(MainActivity.this, AddNewTaskActivity.class);

            if (!taskname.isEmpty()) {
                intent.putExtra("key", taskname);
            }
            List<String> allOptions = getOptionsFromSharedPreferences(MainActivity.this);

            intent.putStringArrayListExtra("allOptions", (ArrayList<String>) allOptions);

            startActivity(intent);
        });

    }

    public void saveOptionsToSharedPreferences(Context context, List<String> options) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<>(options);
        editor.putStringSet("spinnerOptions", set);
        editor.apply();
    }

    @SuppressLint("MutatingSharedPrefs")
    public void addOptionToSharedPreferences(Context context, List<String> option) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//        Set<String> set = sharedPreferences.getStringSet("spinnerOptions", new HashSet<>());
//        set.add(option);

        Gson gson = new Gson();
        String json = gson.toJson(option);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("spinnerOptions", json);
        editor.apply();
    }

    @SuppressLint("MutatingSharedPrefs")
    public List<String> getOptionsFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
//        Set<String> set = sharedPreferences.getStringSet("spinnerOptions", new HashSet<>());
        String set = sharedPreferences.getString("spinnerOptions", new Gson().toJson(DEFAULT_OPTION));
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
//        if (set.isEmpty()) {
//            // Add default option if no options are present
//            set.add(DEFAULT_OPTION);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putStringSet("spinnerOptions", set);
//            editor.apply();
//        }
        return gson.fromJson(set,type);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

      //  String selectedList = lists.get(position);
      if (Objects.equals(selectedItem, "All")) {
          eventViewModel.getAllEvents().observe(this, events -> adapter.setEvents(events));
       }

      else {
           eventViewModel.getEventsByList(selectedItem).observe(this, events -> adapter.setEvents(events));
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                e1.setText(
                        Objects.requireNonNull(result).get(0));
            }
        }
    }
}
