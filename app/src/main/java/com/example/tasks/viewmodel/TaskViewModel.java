package com.example.tasks.viewmodel;

import android.app.Application;

import com.example.tasks.service.listener.Feedback;
import com.example.tasks.service.model.PriorityModel;
import com.example.tasks.service.repository.PriorityRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class TaskViewModel extends AndroidViewModel {

    private PriorityRepository mPriorityRepository;

    private MutableLiveData<List<PriorityModel>> mListPriority = new MutableLiveData<>();
    public LiveData<List<PriorityModel>> listPriority = this.mListPriority;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        this.mPriorityRepository = new PriorityRepository(application);
    }

    public void getList(){
        List<PriorityModel> lst = mPriorityRepository.getList();
        this.mListPriority.setValue(lst);
    }

}
