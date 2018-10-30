package ch.FOW_Collection;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

/**
 * The MyApplication class is instantiated once for the whole application and can (but should not) be used as a
 * singleton to store application wide data. The manifest contains a reference to this class, apart from that it is
 * not referenced in this application.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /*
         * This will log whenever we reveice data from firestore. This is useful for debugging and to get a feeling
         * of how much and when new data is received from the database.
         * */
        FirebaseFirestore.setLoggingEnabled(true);

        /*
         * Apply settings
         */
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        FirebaseFirestore.getInstance().setFirestoreSettings(settings);
    }
}