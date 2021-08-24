package com.example.tasks.viewmodel;

import android.app.Application;

import com.example.tasks.service.listener.APIListener;
import com.example.tasks.service.listener.Feedback;
import com.example.tasks.service.model.PersonModel;
import com.example.tasks.service.repository.PersonRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RegisterViewModel extends AndroidViewModel {

    private PersonRepository mPersonRepository;

    private MutableLiveData<Feedback> mCreate = new MutableLiveData<>();
    public LiveData<Feedback> create = this.mCreate;

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        this.mPersonRepository = new PersonRepository(application);
    }

    public void create(String name, String email, String password){
        this.mPersonRepository.create(name, email, password, new APIListener<PersonModel>() {
            @Override
            public void onSuccess(PersonModel result) {
                // salva dados do user no sharedPreferences
                mPersonRepository.saveUserData(result);
                mCreate.setValue(new Feedback());
            }

            @Override
            public void onFailure(String message) {
                mCreate.setValue(new Feedback(message));
            }
        });
    }

}
