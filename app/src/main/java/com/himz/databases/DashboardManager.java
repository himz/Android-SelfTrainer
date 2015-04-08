package com.himz.databases;

import android.app.Application;
import android.content.Context;

import com.himz.entities.Exercise;
import com.himz.entities.Routine;
import com.himz.entities.RoutineDataForAdapter;
import com.himz.entities.RoutineDetail;
import com.himz.helpers.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Example file to take care of calling the database
 *
 */
public class DashboardManager {
	static App app;
    static Random rand;

    public static List<RoutineDetail> getAllRoutineForToday(Context ctx) {
        app = ((App)ctx.getApplicationContext());
        return app.db.getAllRoutineForToday();
    }


    public static List<RoutineDataForAdapter> getRoutineDataForAdapter(Context ctx, List<RoutineDetail> routineDetailList) {
        app = ((App)ctx.getApplicationContext());
        List<RoutineDataForAdapter> routineDataForAdapterList = new ArrayList<RoutineDataForAdapter>();
        RoutineDataForAdapter routineDataForAdapter  =  new RoutineDataForAdapter();

        for(RoutineDetail routineDetail:routineDetailList){
            routineDataForAdapter = new RoutineDataForAdapter();
            routineDataForAdapter.setRoutineDetail(routineDetail);
            Exercise ex = app.db.getExerciseForId(routineDetail.getExerciseId());
            routineDataForAdapter.setExercise(ex);
            Routine routineData =  app.db.getRoutineForId(routineDetail.getRoutineId());
            routineDataForAdapter.setRoutineData(routineData);
            routineDataForAdapterList.add(routineDataForAdapter);
        }
        return routineDataForAdapterList;
    }

/*	public static Phrase getPhraseFromID(Context ctx, int phraseID) {
		app = ((App)ctx.getApplicationContext());
		return app.db.getPhraseFromID(phraseID);
	}

    public static List<Phrase> getAllPhrase(Context ctx) {
        app = ((App)ctx.getApplicationContext());
        return app.db.getAllPhrase();
    }

    public static int getPhraseCount(Context ctx) {
        app = ((App)ctx.getApplicationContext());
        return app.db.getPhraseCount();
    }*/

/*    public static Phrase getRandomPhrase(Context ctx) {
        // Get row count, generate random number and then send that phrase out.
        int noOfRows = getPhraseCount(ctx);
        // Generate random phraseID
        int randomPhraseID = randInt(1, noOfRows, rand);
        return getPhraseFromID(ctx, randomPhraseID);
    }*/

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @param rand Random variable to be passed to function
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static int randInt(int min, int max, Random rand) {
        if(rand == null)
            rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


}