package com.himz.databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.himz.entities.Exercise;
import com.himz.entities.Routine;
import com.himz.entities.RoutineDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Simple database access helper class. Defines the basic
 * <p/>
 * Inserting this file as template holder for our database for bite app.
 */
public class LocalDbAdapter {

    /* Global Constants */
    private static final String ROUTINE_TABLE_NAME = "Routine";
    private static final String ROUTINE_DETAIL_TABLE_NAME = "RoutineDetail";
    private static final String PROGRESS_DETAIL_TABLE_NAME = "ProgressDetail";
    private static final String WORKOUT_TABLE_NAME = "Workout";
    private static final String EXERCISE_TABLE_NAME = "Exercise";


    /* Once a row is filled in the phrase table, that is never deleted. Only modified */
    private static final String CREATE_ROUTINE_TABLE = "create table " + ROUTINE_TABLE_NAME +
            "(routineId integer, routineName text, aim text)";

    private static final String CREATE_WORKOUT_TABLE = "create table " + WORKOUT_TABLE_NAME +
            "(workoutId integer, routineId integer, startDate integer)";

    private static final String CREATE_EXERCISE_TABLE = "create table " + EXERCISE_TABLE_NAME +
            "(exerciseId integer, exerciseName text, category text, muscleGroup text)";

    private static final String CREATE_ROUTINE_DETAIL_TABLE = "create table " + ROUTINE_DETAIL_TABLE_NAME +
            "(id integer, routineId integer, day integer, exerciseId integer, " +
            "set1Rep integer," +
            "set2Rep integer," +
            "set3Rep integer," +
            "set4Rep integer," +
            "set5Rep integer," +
            "set6Rep integer" +
            ")";

    private static final String CREATE_PROGRESS_DETAIL_TABLE = "create table " + PROGRESS_DETAIL_TABLE_NAME +
            "(id integer, routineId integer, date integer, exerciseId integer, " +
            "set1Weight integer, set1Rep integer," +
            "set2Weight integer, set2Rep integer," +
            "set3Weight integer, set3Rep integer," +
            "set4Weight integer, set4Rep integer," +
            "set5Weight integer, set5Rep integer," +
            "set6Weight integer, set6Rep integer" +
            ")";


    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;
    private Context mCtx;

	/*Database creation sql statement*/


    private static final String TAG = "DbHelper";
    private static final String DATABASE_NAME = "selfTrainereDb";


    private static final int DATABASE_VERSION = 2;
    private List<RoutineDetail> allRoutineForToday;



    private static class DatabaseHelper extends SQLiteOpenHelper {

        private Context context;

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            System.out.println("In DatabaseHelper constructor");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            System.out.println("In onCreate()");
            db.execSQL(CREATE_EXERCISE_TABLE);
            db.execSQL(CREATE_WORKOUT_TABLE);
            db.execSQL(CREATE_ROUTINE_TABLE);
            db.execSQL(CREATE_ROUTINE_DETAIL_TABLE);
            db.execSQL(CREATE_PROGRESS_DETAIL_TABLE);
            /* Also seed data for default values */
            seedData(db);

        }

        public static Long persistDate(Date date) {
            if (date != null) {
                return date.getTime();
            }
            return null;
        }
        public void seedData(SQLiteDatabase db) {
            //@ToDo add Seed data here
            createExerciseTableRow(db,1,"Squat", "Compound", "Hamstrings, LowerBack");
            createExerciseTableRow(db,2,"DeadLift", "Compound", "Quads, LowerBack");
            createExerciseTableRow(db,3,"PullUp", "Compound", "Lats, Upper Back");
            createExerciseTableRow(db,4,"BenchPress", "Compound", "Chest, Lats");

            createWorkoutTableRow(db,1,1,persistDate(new Date()));

            createRoutineTableRow(db,1,"Personal 1", "Strength Training");

            createRoutineDetailTableRow(db,1,1,1,1,5,5,5,5,5,0);
            createRoutineDetailTableRow(db,2,1,1,2,5,5,5,5,5,0);
            createRoutineDetailTableRow(db,3,1,1,3,5,5,5,5,5,0);
            createRoutineDetailTableRow(db,4,1,1,4,5,5,5,5,5,0);
            createRoutineDetailTableRow(db,5,1,2,3,5,5,5,5,5,0);
            createRoutineDetailTableRow(db,6,1,2,2,5,5,5,5,5,0);

            createProgressDetailTableRow(db, 1, 1, persistDate(new Date()), 1, 50, 5, 50, 5, 45, 5, 45, 5, 45, 5, 0, 0);

        }


        private long createExerciseTableRow(SQLiteDatabase db, int exerciseId, String exerciseName,
                                            String category, String muscleGroup) {
            ContentValues initialValues = new ContentValues();
            initialValues.put("exerciseId", exerciseId);
            initialValues.put("exerciseName", exerciseName);
            initialValues.put("category", category);
            initialValues.put("muscleGroup", muscleGroup);
            Log.d(TAG, "Inserting values in Exercise Table");
            return db.insert(EXERCISE_TABLE_NAME, null, initialValues);
        }

        private long createRoutineTableRow(SQLiteDatabase db, int routineId, String routineName,
                                           String aim) {
            ContentValues initialValues = new ContentValues();
            initialValues.put("routineId", routineId);
            initialValues.put("exerciseName", routineName);
            initialValues.put("aim", aim);
            Log.d(TAG, "Inserting values in Routine Table");
            return db.insert(ROUTINE_TABLE_NAME, null, initialValues);
        }

        private long createWorkoutTableRow(SQLiteDatabase db, int workoutId, int routineId,
                                           Long startDate) {
            ContentValues initialValues = new ContentValues();
            initialValues.put("workoutId", workoutId);
            initialValues.put("routineId", routineId);
            initialValues.put("startDate", startDate);
            Log.d(TAG, "Inserting values in Workout Table");
            return db.insert(WORKOUT_TABLE_NAME, null, initialValues);
        }

        private long createRoutineDetailTableRow(SQLiteDatabase db, int id, int routineId,
                                                 int day, int exerciseId,
                                                 int set1Rep,
                                                 int set2Rep,
                                                 int set3Rep,
                                                 int set4Rep,
                                                 int set5Rep,
                                                 int set6Rep) {
            ContentValues initialValues = new ContentValues();
            initialValues.put("id", id);
            initialValues.put("routineId", routineId);
            initialValues.put("day", day);
            initialValues.put("exerciseId", exerciseId);
            initialValues.put("set1Rep", set1Rep);
            initialValues.put("set2Rep", set2Rep);
            initialValues.put("set3Rep", set3Rep);
            initialValues.put("set4Rep", set4Rep);
            initialValues.put("set5Rep", set5Rep);
            initialValues.put("set6Rep", set6Rep);
            Log.d(TAG, "Inserting values in RoutineDetail Table");
            return db.insert(ROUTINE_DETAIL_TABLE_NAME, null, initialValues);
        }

        private long createProgressDetailTableRow(SQLiteDatabase db, int id, int routineId,
                                                 Long date, int exerciseId,
                                                 int set1Weight, int set1Rep,
                                                 int set2Weight, int set2Rep,
                                                 int set3Weight, int set3Rep,
                                                 int set4Weight, int set4Rep,
                                                 int set5Weight, int set5Rep,
                                                 int set6Weight, int set6Rep) {
            ContentValues initialValues = new ContentValues();
            initialValues.put("id", id);
            initialValues.put("routineId", routineId);
            initialValues.put("date", date);
            initialValues.put("exerciseId", exerciseId);
            initialValues.put("set1Weight", set1Weight);
            initialValues.put("set1Rep", set1Rep);
            initialValues.put("set2Weight", set2Weight);
            initialValues.put("set2Rep", set2Rep);
            initialValues.put("set3Weight", set3Weight);
            initialValues.put("set3Rep", set3Rep);
            initialValues.put("set4Weight", set4Weight);
            initialValues.put("set4Rep", set4Rep);
            initialValues.put("set5Weight", set5Weight);
            initialValues.put("set5Rep", set5Rep);
            initialValues.put("set6Weight", set6Weight);
            initialValues.put("set6Rep", set6Rep);
            Log.d(TAG, "Inserting values in RoutineDetail Table");
            return db.insert(PROGRESS_DETAIL_TABLE_NAME, null, initialValues);
        }

        private long createProgressDetailTableRow(SQLiteDatabase db, int exerciseId, String exerciseName,
                                                  String category, String muscleGroup) {
            ContentValues initialValues = new ContentValues();
            initialValues.put("exerciseId", exerciseId);
            initialValues.put("exerciseName", exerciseName);
            initialValues.put("category", category);
            initialValues.put("muscleGroup", muscleGroup);
            Log.d(TAG, "Inserting values in Exercise Table");
            return db.insert(EXERCISE_TABLE_NAME, null, initialValues);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS TripIt");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    public LocalDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the bite database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     * initialization call)
     * @throws android.database.SQLException if the database could be neither opened or created
     */
    public LocalDbAdapter open() throws SQLException {

        System.out.println("In open()");

        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();

        return this;
    }



    /**
     * @brief Close the adapter
     */
    public void close() {
        mDbHelper.close();
    }

    public Exercise getExerciseForId(int exerciseId) {
        Cursor c = null;
        c = mDb.rawQuery("select * from " + EXERCISE_TABLE_NAME + " where exerciseId = " + "\"" + exerciseId + "\"", null);
        Exercise exercise = new Exercise();
        try {
            if (c.moveToFirst()) {
                do {
                    /* Assuming only one restaurant of same name for now. Else only the last restaurant will be shown */
                    exercise.setExerciseId(Integer.parseInt(c.getString(0)));
                    exercise.setExerciseName(c.getString(1));
                    exercise.setCategory(c.getString(2));
                    exercise.setMuscleGroup(c.getString(3));
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exercise;
    }

    public Routine getRoutineForId(int routineId) {
        Cursor c = null;
        c = mDb.rawQuery("select * from " + ROUTINE_TABLE_NAME + " where routineId = " + "\"" + routineId + "\"", null);
        Routine routine = new Routine();
        try {
            if (c.moveToFirst()) {
                do {
                    /* Assuming only one restaurant of same name for now. Else only the last restaurant will be shown */
                    routine.setRoutineId(Integer.parseInt(c.getString(0)));
                    routine.setRoutineName(c.getString(1));
                    routine.setAim(c.getString(2));
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routine;
    }


    public List<RoutineDetail> getAllRoutineForToday() {
        RoutineDetail routineDetail;
        List<RoutineDetail> allRoutineForToday = new ArrayList<RoutineDetail>();
        Cursor c = null;
        c = mDb.rawQuery("select * from " + ROUTINE_DETAIL_TABLE_NAME+ " where day = 1", null);

        try {
            if (c.moveToFirst()) {
                do {
                    routineDetail = new RoutineDetail();
                    /* Assuming only one restaurant of same name for now. Else only the last restaurant will be shown */
                    routineDetail.setId(Integer.parseInt(c.getString(0)));
                    routineDetail.setRoutineId(Integer.parseInt(c.getString(1)));
                    routineDetail.setDay(Integer.parseInt(c.getString(2)));
                    routineDetail.setExerciseId(Integer.parseInt(c.getString(3)));
                    routineDetail.setSet1Rep(Integer.parseInt(c.getString(4)));
                    routineDetail.setSet2Rep(Integer.parseInt(c.getString(5)));
                    routineDetail.setSet3Rep(Integer.parseInt(c.getString(6)));
                    routineDetail.setSet4Rep(Integer.parseInt(c.getString(7)));
                    routineDetail.setSet5Rep(Integer.parseInt(c.getString(8)));
                    routineDetail.setSet6Rep(Integer.parseInt(c.getString(9)));
                    allRoutineForToday.add(routineDetail);
                } while (c.moveToNext());
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allRoutineForToday;
    }


    /**
     * Remove all users and groups from database.
     */
    public void deleteAllRowsFromTable(String tableName) {
        mDb.delete(tableName, null, null);
    }

    /**
     * Function to update the description of the activity
     *
     * @param description
     * @param activityID
     * @return true, if updated and false if not updated
     */
    public boolean updateDescriptionOfActivity(String description, int activityID) {
        String strSQL = "UPDATE ActivityTable SET description = " + "\"" + description + "\"" + "WHERE activityID = " + "\"" + activityID + "\"";
        if (strSQL != null) {
            try {
                mDb.execSQL(strSQL);
                return true;
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Function to return the last activityID from the table
     *
     * @return
     */
    public int fetchRowCountOfTable(String tableName) {
        Cursor c = null;
        c = mDb.rawQuery("select count(*) from " + tableName, null);
        c.moveToFirst();
        int rowCount = c.getInt(0);
        return rowCount;
    }
} 