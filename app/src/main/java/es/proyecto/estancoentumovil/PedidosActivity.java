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
import es.proyecto.estancoentumovil.model.Pedido;

public class PedidosActivity extends AppCompatActivity {

    EditText pedidoFecha, pedidoIDUsuario, pedidoTotal;
    ListView listaPedidos;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Pedido> pedidos = new ArrayList<Pedido>();
    ArrayAdapter<Pedido> adapterPedido;
    Pedido pedidoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        listaPedidos = findViewById(R.id.lvPedidos);

        initFirebase();
        obtenerPedidos();

        listaPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pedidoSeleccionado = (Pedido) adapterView.getItemAtPosition(i);
                pedidoIDUsuario.setText(pedidoSeleccionado.getIdUsuario());
                pedidoFecha.setText(pedidoSeleccionado.getFecha());
                pedidoTotal.setText(pedidoSeleccionado.getTotal());

            }
        });

    }

    private void obtenerPedidos() {
        databaseReference.child("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pedidos.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Pedido pedido = data.getValue(Pedido.class);
                    PedidosActivity.this.pedidos.add(pedido);
                    adapterPedido = new ArrayAdapter<Pedido>(PedidosActivity.this, android.R.layout.simple_list_item_1, PedidosActivity.this.pedidos);
                    listaPedidos.setAdapter(adapterPedido);
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
                Intent intent2= new Intent (PedidosActivity.this, PrimerMActivity.class);
                startActivity(intent2);
                break;

            default: break;
        }
        return true;
    }

}

