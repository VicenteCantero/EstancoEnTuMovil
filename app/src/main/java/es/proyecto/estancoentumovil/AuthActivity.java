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

import java.util.HashMap;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private EditText etNombre,etEmail, etPassword;
    private Button btRegistro, btAcceder;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    private String name="";
    private String email="";
    private String password="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        etNombre= (EditText) findViewById(R.id.etNombre);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        btRegistro= (Button) findViewById(R.id.btRegistro);
        btAcceder= (Button) findViewById(R.id.btAcceder);

        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=etNombre.getText().toString();
                email=etEmail.getText().toString();
                password=etPassword.getText().toString();


                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){

                    if(password.length()>=6){
                        registerUser();
                    }else{
                        Toast.makeText(AuthActivity.this, "Más de 6", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AuthActivity.this, "Rellena", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AuthActivity.this, LoginUsuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String,Object> map= new HashMap<>();

                    String id= mAuth.getCurrentUser().getUid();

                    map.put("name", name);
                    map.put("email", email);
                    map.put("password", password);
                    map.put("uid", mAuth.getCurrentUser().getUid());


                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()) {
                                Intent intent= new Intent(AuthActivity.this, PrimerMActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(AuthActivity.this, "No se pudo guardar los datos correctamente", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(AuthActivity.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            Intent intent= new Intent(AuthActivity.this, PrimerMActivity.class);
            startActivity(intent);
            finish();
        }
    }

}