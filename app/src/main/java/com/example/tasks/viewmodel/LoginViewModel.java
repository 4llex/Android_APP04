package com.example.tasks.viewmodel;

import android.app.Application;

import com.example.tasks.service.listener.APIListener;
import com.example.tasks.service.model.PersonModel;
import com.example.tasks.service.repository.PersonRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LoginViewModel extends AndroidViewModel {

    private PersonRepository mPersonRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.mPersonRepository = new PersonRepository(application);
    }

    public void login(String email, String password){
        this.mPersonRepository.login(email, password, new APIListener<PersonModel>() {
            @Override
            public void onSuccess(PersonModel result) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

}
