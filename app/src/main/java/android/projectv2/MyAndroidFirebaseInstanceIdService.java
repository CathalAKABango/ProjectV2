package android.projectv2;

/**
 * Created by cathal on 13/04/2017.
 */

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";

    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);
     sendRegistrationToServer(refreshedToken); 
        //Implement this method if you want to store the token on your server
    }

    private void sendRegistrationToServer(String token) {
    }
}
