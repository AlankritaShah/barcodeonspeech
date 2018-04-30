package munshig.in.kiranamark3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.google.firebase.firestore.DocumentListenOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PhoneAuth";
    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;


    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    //    private ViewGroup mPhoneNumberViews;
//    private ViewGroup mSignedInViews;
//
//    private TextView mStatusText;
//    private TextView mDetailText;
//
    private EditText mPhoneNumberField;
    private EditText mVerificationField;
    SharedPref preff;
    FirebaseFirestore mFirebaseDatabase;
   // DatabaseReference mItemsDatabaseReference;
    private ImageView mStartButton;
    private ImageView mVerifyButton;
   // LocalDatabase db;

//    private Button mResendButton;
//    private Button mSignOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   mFirebaseDatabase = FirebaseFirestore.getInstance();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null && (new SharedPref(getApplicationContext())).getApprovedStatus() == 1){
            Log.i("current user approved", FirebaseAuth.getInstance().getCurrentUser().toString());
            startActivity(new Intent(SignIn.this,BillSummary.class));
            finish();
        }else if(FirebaseAuth.getInstance().getCurrentUser()!=null && (new SharedPref(getApplicationContext())).getApprovedStatus() == 0){
            startActivity(new Intent(SignIn.this,BillSummary.class));
            finish();
        }
        setContentView(R.layout.activity_signin);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }

        preff = new SharedPref(getApplicationContext());
      //  db = new LocalDatabase(getApplicationContext());
        mFirebaseDatabase = FirebaseFirestore.getInstance();
      //  mItemsDatabaseReference = mFirebaseDatabase.getReference().child("data");

        if(preff.getUser()!=null)
        {
            Log.i("user kon hai?", preff.getUser());
            mFirebaseDatabase.collection(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if(task.getResult().isEmpty())
                                {
                                    Log.i("result", "isempty");
                                    generatethreedigitcode(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                                }
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
            Intent intent = new Intent(SignIn.this,BillSummary.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }


        // Assign views
//        mPhoneNumberViews = (ViewGroup) findViewById(R.id.phone_auth_fields);
//        mSignedInViews = (ViewGroup) findViewById(R.id.signed_in_buttons);
//
//        mStatusText = (TextView) findViewById(R.id.status);
//        mDetailText = (TextView) findViewById(R.id.detail);
//
        mPhoneNumberField = (EditText) findViewById(R.id.phone_number);
        mVerificationField = (EditText) findViewById(R.id.otp_code_text);

        mStartButton = (ImageView) findViewById(R.id.send_code);
        mVerifyButton = (ImageView) findViewById(R.id.verify_code);
//        mVerifyButton = (Button) findViewById(R.id.button_verify_phone);
//        mResendButton = (Button) findViewById(R.id.button_resend);
//        mSignOutButton = (Button) findViewById(R.id.sign_out_button);

        // Assign click listeners
        mStartButton.setOnClickListener(this);
        mVerifyButton.setOnClickListener(this);
//        mResendButton.setOnClickListener(this);
//        mSignOutButton.setOnClickListener(this);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        // [END initialize_auth]

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    mStartButton.setEnabled(true);
                    mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
//                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
//                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                ((LinearLayout) findViewById(R.id.otp_layout)).setVisibility(View.VISIBLE);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

        // [START_EXCLUDE]
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification("+91"+mPhoneNumberField.getText().toString());
        }
        // [END_EXCLUDE]
    }
    // [END on_start_check_user]

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_VERIFY_IN_PROGRESS, mVerificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mVerificationInProgress = savedInstanceState.getBoolean(KEY_VERIFY_IN_PROGRESS);
    }


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            preff.setUser(user);
                            new PrefetchData().execute();
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    private void signOut() {
        mAuth.signOut();
        updateUI(STATE_INITIALIZED);
    }

    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                enableViews(mStartButton, mPhoneNumberField);
                disableViews(mVerifyButton, mVerificationField);
                //   mDetailText.setText(null);
                break;
            case STATE_CODE_SENT:

                findViewById(R.id.loadingPane).setVisibility(View.VISIBLE);
                // findViewById(R.id.otp_code).setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    /*
                     * Showing splash screen with a timer. This will be useful when you
                     * want to show case your app logo / company
                     */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        findViewById(R.id.loadingPane).setVisibility(View.GONE);
                    }
                }, 10000);
                // Code sent state, show the verification field, the
                enableViews(mVerifyButton, mVerificationField);
                disableViews(mStartButton, mPhoneNumberField);
                //  mDetailText.setText(R.string.status_code_sent);
                break;
            case STATE_VERIFY_FAILED:
                // Verification has failed, show all options
                //   enableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                //  mVerificationField);
                //         mDetailText.setText(R.string.status_verification_failed);
                break;
            case STATE_VERIFY_SUCCESS:
                // Verification has succeeded, proceed to firebase sign in
                //    disableViews(mStartButton, mVerifyButton, mResendButton, mPhoneNumberField,
                //             mVerificationField);
                //      mDetailText.setText(R.string.status_verification_succeeded);

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        mVerificationField.setText(cred.getSmsCode());

                    } else {
                        //         mVerificationField.setText(R.string.instant_validation);
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                //      mDetailText.setText(R.string.status_sign_in_failed);
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {
            // Signed out
            //     mPhoneNumberViews.setVisibility(View.VISIBLE);
            //     mSignedInViews.setVisibility(View.GONE);

            //      mStatusText.setText(R.string.signed_out);;
        } else {
            // Signed in
            //     mPhoneNumberViews.setVisibility(View.GONE);
            //      mSignedInViews.setVisibility(View.VISIBLE);
//
            //       enableViews(mPhoneNumberField, mVerificationField);
            mPhoneNumberField.setText(null);
            //      mVerificationField.setText(null);

            //      mStatusText.setText(R.string.signed_in);
            //       mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = mPhoneNumberField.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumberField.setError("Invalid phone number.");
            return false;
        }

        return true;
    }

    private void enableViews(View... views) {
        for (View v : views) {
            v.setEnabled(true);
        }
    }

    private void disableViews(View... views) {
        for (View v : views) {
            v.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_code:
                if(Functions.haveNetworkConnection(getApplicationContext())){
                    if (!validatePhoneNumber()) {
                        return;
                    }
                    mStartButton.setEnabled(false);
                    startPhoneNumberVerification("+91"+mPhoneNumberField.getText().toString());
                }
                else{
                    Toast.makeText(this,"Turn on your Internet Connection For Verification!!",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.verify_code:
                if(Functions.haveNetworkConnection(getApplicationContext())){
                    String code = mVerificationField.getText().toString();
                    if (TextUtils.isEmpty(code)) {
                        mVerificationField.setError("Cannot be empty.");
                        return;
                    }

                    verifyPhoneNumberWithCode(mVerificationId, code);
                }else {
                    Toast.makeText(this,"Turn on your Internet Connection For Verification!!",Toast.LENGTH_SHORT).show();
                }

                break;
//
        }
    }
    private class PrefetchData extends AsyncTask<Void, Void, Void> {

        private PrefetchData( ) {

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.v("onPreExecute","i am here");
            findViewById(R.id.waiting).setVisibility(View.VISIBLE);
            Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);

            findViewById(R.id.dotOne).startAnimation(startAnimation);
            findViewById(R.id.dotTwo).startAnimation(startAnimation);
            findViewById(R.id.dotThree).startAnimation(startAnimation);



        }

        @Override
        protected Void doInBackground(Void... arg0) {
            Log.v("onBackground","i am here");

            mFirebaseDatabase.collection(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if(task.getResult().isEmpty())
                                {
                                    Log.i("result", "isempty");
                                    generatethreedigitcode(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                                }
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                }
                                findViewById(R.id.waiting).setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(SignIn.this,BillSummary.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });


//            mFirebaseDatabase.collection(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).get()
//                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                        @Override
//                        public void onSuccess(QuerySnapshot documentSnapshots) {
//                            if (documentSnapshots.isEmpty()) {
//                                Log.d(TAG, "onSuccess: LIST EMPTY");
//                                generatethreedigitcode(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
//                                findViewById(R.id.waiting).setVisibility(View.INVISIBLE);
//                                Intent intent = new Intent(SignIn.this,BillSummary.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//                                return;
//                            } else {
//                                // Convert the whole Query Snapshot to a list
//                                // of objects directly! No need to fetch each
//                                // document.
//                                List<Type> types = documentSnapshots.toObjects(Type.class);
//                                Log.d(TAG, "onSuccess: " + types);
//                                findViewById(R.id.waiting).setVisibility(View.INVISIBLE);
//                                Intent intent = new Intent(SignIn.this,BillSummary.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                startActivity(intent);
//
//                                // Add all to your list
//                                //  mArrayList.addAll(types);
//                              //  Log.d(TAG, "onSuccess: " + types);
//                            }
//                        }
//                        })
//                                .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(getApplicationContext(), "Error getting data!!!", Toast.LENGTH_LONG).show();
//                            }
//                        });


//            DocumentListenOptions options = new DocumentListenOptions()
//                    .includeMetadataChanges();
//
//            DocumentReference docRef = mFirebaseDatabase.collection(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).document("Barcode Items");
//            docRef.addSnapshotListener(options, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot snapshot,
//                                    @Nullable FirebaseFirestoreException e) {
//                    if (e != null) {
//                        Log.w("kyahua?", "Listen failed.", e);
//                        return;
//                    }
//
//                    String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
//                            ? "Server" : "Local";
//
//                    if (snapshot != null && snapshot.exists()) {
//                        Log.d("kyahua?", source + " data: " + snapshot.getData());
//                        preff.setDownload(true);
//                        Log.v("onComplete","i am here");
//                        findViewById(R.id.waiting).setVisibility(View.INVISIBLE);
//
//                        Intent intent = new Intent(SignIn.this,BillSummary.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//
//                        //  collectProductslocal(snapshot.getData());
//                    } else {
//                        Log.d("kyahua?", source + " data: null");
//                        Log.v("onComplete2","i am here");
//                        findViewById(R.id.waiting).setVisibility(View.INVISIBLE);
//                        Intent intent = new Intent(SignIn.this,BillSummary.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                }
//            });

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


        }

    }

    public void generatethreedigitcode(String phone)
    {
        Map<String, Object> city = new HashMap<>();
        city.put("generatedusercode", "001");


        mFirebaseDatabase.collection(phone).document("generatedusercode")
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

}