package com.example.tasks.service.repository.local;

import com.example.tasks.service.model.PriorityModel;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PriorityDAO {

    @Insert
    void save(List<PriorityModel> list);

    @Query("SELECT * FROM priority")
    List<PriorityModel> list();

    @Query("SELECT description FROM priority WHERE id = :id")
    String getDescription(int id);

}
