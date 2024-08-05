package com.example.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;



@Dao
public interface EventDao {
    @Insert
    void insert(Event event);

    @Query("SELECT * FROM event_table")
    LiveData<List<Event>> getAllEvents();

    @Delete
    void deleteEvent(Event event);

    @Query("SELECT * FROM event_table WHERE list = :list")
    LiveData<List<Event>> getEventsByList(String list);
}
