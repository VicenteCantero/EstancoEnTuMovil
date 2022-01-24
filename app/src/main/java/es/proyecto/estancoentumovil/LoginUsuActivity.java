package es.proyecto.estancoentumovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginUsuActivity extends AppCompatActivity {

    private EditText etEmailLogin, etPasswordLogin;
    private Button btIniciar;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usu);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        etEmailLogin= (EditText) findViewById(R.id.etEmailLogin);
        etPasswordLogin= (EditText) findViewById(R.id.etPasswordLogin);
        btIniciar= (Button) findViewById(R.id.btIniciar);

        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email= etEmailLogin.getText().toString();
                password= etPasswordLogin.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    LoginUser();

                }else{
                    Toast.makeText(LoginUsuActivity.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LoginUser() {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent= new Intent(LoginUsuActivity.this, PrimerMActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(LoginUsuActivity.this, "No se pudo iniciar sesión, compruebe los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}