package com.himz.helpers;

import android.app.Application;
import android.content.Context;

import com.himz.databases.LocalDbAdapter;


/**
 * Class to store all the global variables.
 * Mainly handling database connections for now
 * As a general practice, start moving shared state among actiities to this
 * class.
 *
 * @author himanshu
 *
 */


public class App extends Application {
	/* List of global variables, to be shared across activities */
	public LocalDbAdapter db;


	@Override
	public void onCreate() {
		super.onCreate();
		Context ctx = getApplicationContext();
		this.db = new LocalDbAdapter(ctx);
		this.db.open();

	}
	@Override
	public void onTerminate() {
		this.db.close();
		super.onTerminate();
	}
}