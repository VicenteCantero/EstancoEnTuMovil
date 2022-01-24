package es.proyecto.estancoentumovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import es.proyecto.estancoentumovil.model.Clientes;

public class AgregarClientesActivity extends AppCompatActivity {

    EditText txtNombre, txtPersona, txtTelefono;
    Button guardarCP;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_clientes);

        txtNombre = findViewById(R.id.txtNombre);
        txtPersona = findViewById(R.id.txtPersona);
        txtTelefono = findViewById(R.id.txtTelefono);

        guardarCP= (Button)findViewById(R.id.guardarCP);

        initFirebase();

        guardarCP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombre = txtNombre.getText().toString();
                String persona = txtPersona.getText().toString();
                String telefono = txtTelefono.getText().toString();

                if(nombre.equals("") || persona.equals("") || telefono.equals("") ){
                    validateData();
                } else{
                    Clientes clientes = new Clientes();
                    clientes.setUid(UUID.randomUUID().toString());
                    clientes.setNombre(nombre);
                    clientes.setPersona(persona);
                    clientes.setTelefono(telefono);
                    databaseReference.child("Clientes").child(clientes.getUid()).setValue(clientes);
                    Toast.makeText(AgregarClientesActivity.this, "Añadido", Toast.LENGTH_SHORT).show();
                    cleanData();
                    Intent intent= new Intent (AgregarClientesActivity.this, ListaClientesActivity.class);
                    startActivity(intent);}
            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }



    private void cleanData() {
        txtNombre.setText("");
        txtPersona.setText("");
        txtTelefono.setText("");
    }

    private void validateData() {
        String nombre = txtNombre.getText().toString();
        String persona = txtPersona.getText().toString();
        String telefono = txtTelefono.getText().toString();


        if(nombre.equals("")){
            txtNombre.setError("Requerido");
        } else if(persona.equals("")){
            txtPersona.setError("Requerido");
        } else if(telefono.equals("")){
            txtTelefono.setError("Requerido");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.atras_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()){

            case R.id.atras:
                Intent intent2= new Intent (AgregarClientesActivity.this, ListaClientesActivity.class);
                startActivity(intent2);
                break;

            default: break;
        }
        return true;
    }
}