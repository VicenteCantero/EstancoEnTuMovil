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

import es.proyecto.estancoentumovil.model.Productos;

public class AgregarProductosActivity extends AppCompatActivity {

    private EditText nombrepro, categoriapro, preciopro;
    private Button agregarpro;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_productos);

        nombrepro = (EditText) findViewById(R.id.nombrepro);
        categoriapro = (EditText) findViewById(R.id.categoriapro);
        preciopro = (EditText) findViewById(R.id.preciopro);
        agregarpro = (Button) findViewById(R.id.agregarpro);


        initFirebase();

        agregarpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombrepro.getText().toString();
                String categoria = categoriapro.getText().toString();
                String precio = preciopro.getText().toString();

                if(nombre.equals("") || categoria.equals("") || precio.equals("") ){
                    validateData();
                } else{
                    Productos productos = new Productos();
                    productos.setUid(UUID.randomUUID().toString());
                    productos.setNombre(nombre);
                    productos.setCategoria(categoria);
                    productos.setPrecio(precio);
                    databaseReference.child("Productos").child(productos.getUid()).setValue(productos);
                    Toast.makeText(AgregarProductosActivity.this, "Añadido", Toast.LENGTH_SHORT).show();
                    cleanData();
                    Intent intent= new Intent (AgregarProductosActivity.this, AlmacenActivity.class);
                    startActivity(intent);}
            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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
        String nombre = nombrepro.getText().toString();
        String descripcion = categoriapro.getText().toString();
        String precio = preciopro.getText().toString();


        switch(item.getItemId()){

            case R.id.atras:
                Intent intent2= new Intent (AgregarProductosActivity.this, AlmacenActivity.class);
                startActivity(intent2);
                break;

            default: break;
        }
        return true;
    }

    private void cleanData() {
        nombrepro.setText("");
        categoriapro.setText("");
        preciopro.setText("");
    }

    private void validateData() {
        String nombre = nombrepro.getText().toString();
        String categoria = categoriapro.getText().toString();
        String precio = preciopro.getText().toString();


        if(nombre.equals("")){
            nombrepro.setError("Requerido");
        } else if(categoria.equals("")){
            categoriapro.setError("Requerido");
        } else if(precio.equals("")){
            preciopro.setError("Requerido");
        }
    }

}
