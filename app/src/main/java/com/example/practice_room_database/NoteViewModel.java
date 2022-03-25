package com.example.practice_room_database;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(Application application) {
        super(application);
        noteDB = NoteRoomDatabase.getDatabase(application);
        noteDao = noteDB.noteDao();
        mAllNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertAsyncTask(noteDao).execute(note);
    }

    LiveData<List<Note>> getmAllNotes() {
        return mAllNotes;
    }

    public void update(Note note) {
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note) {
        new DeleteAsyncTask(noteDao).execute(note);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel Destroyed");
    }

    public class OperationAsyncTask extends AsyncTask<Note, Void, Void> {

        NoteDao mAsyncTaskDao;

        public OperationAsyncTask(NoteDao noteDao) {
            mAsyncTaskDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            return null;
        }
    }

    public class InsertAsyncTask extends OperationAsyncTask {

        public InsertAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationAsyncTask {

        public UpdateAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }


        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationAsyncTask {

        public DeleteAsyncTask(NoteDao noteDao) {
            super(noteDao);
        }


        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }
}
