package android.projectv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button resetbtn, bckbtn;

    private EditText resetEmail;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();

        bckbtn = (Button)findViewById(R.id.backbutton);
        resetbtn = (Button)findViewById(R.id.resetbutton);
        resetEmail = (EditText)findViewById(R.id.resetemail);

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = resetEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPasswordActivity.this, "Plese enter a vaild email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ResetPasswordActivity.this, "We have sent an email with instructions to reset password", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(ResetPasswordActivity.this, "Oooops something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
