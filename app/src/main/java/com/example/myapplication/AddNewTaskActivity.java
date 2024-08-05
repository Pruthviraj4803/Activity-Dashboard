package com.example.myapplication;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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
import com.example.app.databinding.AddTaskBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Calendar;
import java.util.Locale;

public class AddNewTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int REQUEST_CODE_SPEECH_INPUT2 = 1;
    AddTaskBinding binding;
    String[] lists = {};
    String[] lists1 = {"Repeat Once", "Daily", "Weekly", "Monthly"};

    private EditText editTextName;
    private EditText editTextTime;
    private EditText editTextDate;
    private Spinner spinnerRepeat;
    private Spinner spinnerList;
    private FloatingActionButton buttonSave;

    ImageView mike2;

    private EventViewModel eventViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        editTextName = binding.taskInput;
        editTextDate = binding.Date;
        editTextTime = binding.Time;
        spinnerRepeat = binding.repeatSpinner;
        spinnerList = binding.addToListSpinner;
        buttonSave = binding.saveTask;
        mike2=binding.mike2;
        Intent intent = getIntent();
        lists = getIntent().getStringArrayListExtra("allOptions").toArray(new String[0]);
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey("key")) {
                String receivedTaskName = extras.getString("key");
                if (receivedTaskName != null) {
                    editTextName.setText(receivedTaskName);
                }
            }
        }

        mike2.setOnClickListener(new View.OnClickListener() {
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
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT2);
                } catch (Exception e) {
                    Toast
                            .makeText(AddNewTaskActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });



        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String time = editTextTime.getText().toString().trim();
            String date = editTextDate.getText().toString().trim();
            String repeat = spinnerRepeat.getSelectedItem().toString();
            String list = spinnerList.getSelectedItem().toString();

            if (name.isEmpty() || time.isEmpty() || date.isEmpty() || repeat.isEmpty() || list.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Event event = new Event(name, time, date, repeat, list);

            eventViewModel.insert(event);
            finish();
        });

        binding.back.setOnClickListener(v -> finish());
        binding.editDate.setOnClickListener(v -> showDatePickerDialog());
        binding.editTime.setOnClickListener(v -> showTimePickerDialog());

        setupSpinners();
    }

    private void setupSpinners() {
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lists);
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.addToListSpinner.setAdapter(listAdapter);
        binding.addToListSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<String> repeatAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lists1);
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.repeatSpinner.setAdapter(repeatAdapter);
        binding.repeatSpinner.setOnItemSelectedListener(this);
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
            binding.Date.setText(date);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            String time = hourOfDay + ":" + minute1;
            binding.Time.setText(time);
        }, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Handle item selection if needed
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle no item selected if needed
    }
}
