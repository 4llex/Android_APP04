package com.example.tasks.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tasks.R;
import com.example.tasks.service.listener.Feedback;
import com.example.tasks.service.model.PriorityModel;
import com.example.tasks.service.model.TaskModel;
import com.example.tasks.viewmodel.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private SimpleDateFormat mFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private ViewHolder mViewHolder = new ViewHolder();
    private TaskViewModel mViewModel;
    private List<Integer> mListPriority = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        // Botão de voltar nativo
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        this.mViewHolder.editDescription = findViewById(R.id.edit_description);
        this.mViewHolder.spinnerPriority = findViewById(R.id.spinner_priority);
        this.mViewHolder.checkComplete = findViewById(R.id.check_completed);
        this.mViewHolder.buttonDate = findViewById(R.id.button_date);
        this.mViewHolder.buttonSave = findViewById(R.id.button_save);

        // ViewModel
        this.mViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        this.createEvents();

        // Cria observadores
        this.loadObservers();
        this.mViewModel.getList();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);

        String date = this.mFormat.format(c.getTime());
        this.mViewHolder.buttonDate.setText(date);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_date) {
            this.showDatePicker();
        } else if (id == R.id.button_save) {
            this.handleSave();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    private void handleSave() {
        TaskModel task = new TaskModel();

        task.setDescription(this.mViewHolder.editDescription.getText().toString());
        task.setComplete(this.mViewHolder.checkComplete.isChecked());
        task.setDueData(this.mViewHolder.buttonDate.getText().toString());
        task.setPriorityId(this.mListPriority.get(this.mViewHolder.spinnerPriority.getSelectedItemPosition()));

        this.mViewModel.save(task);
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, this, year, month, day).show();
    }

    private void createEvents() {
        this.mViewHolder.buttonSave.setOnClickListener(this);
        this.mViewHolder.buttonDate.setOnClickListener(this);
    }

    /**
     * Observadores
     */
    private void loadObservers() {
        this.mViewModel.listPriority.observe(this, new Observer<List<PriorityModel>>() {
            @Override
            public void onChanged(List<PriorityModel> list) {
                loadSpinner(list);
            }
        });

        this.mViewModel.taskSave.observe(this, new Observer<Feedback>() {
            @Override
            public void onChanged(Feedback feedback) {
                String s = "";
            }
        });
    }

    public void loadSpinner(List<PriorityModel> list){
        List<String> lstPriority = new ArrayList<>();
        for (PriorityModel p : list){
            lstPriority.add(p.getDescription());
            this.mListPriority.add(p.getId());
        }

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, lstPriority);
        this.mViewHolder.spinnerPriority.setAdapter(adapter);
    }

    /**
     * ViewHolder
     */
    private static class ViewHolder {

        EditText editDescription;
        Spinner spinnerPriority;
        CheckBox checkComplete;
        Button buttonDate;
        Button buttonSave;

    }
}