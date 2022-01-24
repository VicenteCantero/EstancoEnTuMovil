package es.proyecto.estancoentumovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.proyecto.estancoentumovil.model.Clientes;

public class ListaClientesActivity extends AppCompatActivity {

    EditText clienteNombre, clientePersona, clienteTelefono;
    Button btAgregarCliente;
    ListView listaClientes;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Clientes> clientes = new ArrayList<Clientes>();
    ArrayAdapter<Clientes> adapterClientes;
    Clientes clientesSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        listaClientes = findViewById(R.id.lvClientes);

        btAgregarCliente=(Button)findViewById(R.id.btAgregarCliente);

        initFirebase();
        listUserData();

        listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clientesSelected = (Clientes) adapterView.getItemAtPosition(i);
                clienteNombre.setText(clientesSelected.getNombre());
                clientePersona.setText(clientesSelected.getPersona());
                clienteTelefono.setText(clientesSelected.getTelefono());

            }
        });

        btAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (ListaClientesActivity.this, AgregarClientesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void listUserData() {
        databaseReference.child("Clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientes.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Clientes clientes = data.getValue(Clientes.class);
                    ListaClientesActivity.this.clientes.add(clientes);
                    adapterClientes = new ArrayAdapter<Clientes>(ListaClientesActivity.this, android.R.layout.simple_list_item_1, ListaClientesActivity.this.clientes);
                    listaClientes.setAdapter(adapterClientes);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        switch(item.getItemId()){

            case R.id.atras:
                Intent intent2= new Intent (ListaClientesActivity.this, PrimerMActivity.class);
                startActivity(intent2);
                break;

            default: break;
        }
        return true;
    }

}

