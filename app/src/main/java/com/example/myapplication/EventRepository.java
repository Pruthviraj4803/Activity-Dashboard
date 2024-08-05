package com.example.myapplication;
import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventRepository {
    private EventDao eventDao;
    private LiveData<List<Event>> allEvents;
    private ExecutorService executorService;

    public EventRepository(Application application) {
        EventDatabase db = EventDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getAllEvents();
        executorService = Executors.newFixedThreadPool(2);
    }

    public LiveData<List<Event>> getAllEvents() {

        return allEvents;
    }

    public LiveData<List<Event>> getEventsByList(String list) {
        return eventDao.getEventsByList(list);
    }

    public void insert(Event event) {
        executorService.execute(() -> eventDao.insert(event));
    }

    private static class deleteEventAsyncTask extends AsyncTask<Event, Void, Void> {
        private EventDao mAsyncEventDao;
        deleteEventAsyncTask(EventDao dao) {
            mAsyncEventDao = dao;
        }
        @Override
        protected Void doInBackground(final Event... params) {
            mAsyncEventDao.deleteEvent(params[0]);
            return null;
        }
    }
    public void deleteEvent(Event event)  {
        new deleteEventAsyncTask(eventDao).execute(event);
    }
}
