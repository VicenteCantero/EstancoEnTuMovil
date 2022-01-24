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
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import es.proyecto.estancoentumovil.model.Productos;
import es.proyecto.estancoentumovil.model.Usuarios;

public class AlmacenActivity extends AppCompatActivity {

    private Button btVerCarrito, btAgregarProducto;
    ListView lvProductos;
    private TextView nombreUsuario, emailUsuario;
    Usuarios miUsuario;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    ArrayList<Productos> listaProductos;
    ArrayAdapter<Productos> adaptador;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almacen);

        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();


        btVerCarrito= (Button) findViewById(R.id.btVerCarrito);
        btAgregarProducto= (Button) findViewById(R.id.btAgregarProducto);
        nombreUsuario= (TextView) findViewById(R.id.nombreUsuario);
        emailUsuario= (TextView) findViewById(R.id.emailUsuario);
        lvProductos= (ListView) findViewById(R.id.lvProductos);

        listaProductos= new ArrayList<>();

        adaptador= new ArrayAdapter<Productos>(AlmacenActivity.this, android.R.layout.simple_list_item_1,listaProductos);

        lvProductos.setAdapter(adaptador);

        lvProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(AlmacenActivity.this, DetalleProductoActivity.class);
                intent.putExtra("Productos", listaProductos.get(i).getUid());
                intent.putExtra("Usuarios", miUsuario.uid);
                startActivity(intent);
            }
        });

        mDatabase.child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    listaProductos.clear();
                    for(DataSnapshot dato: snapshot.getChildren()){
                        Productos unProducto= dato.getValue(Productos.class);
                        listaProductos.add(unProducto);
                        adaptador.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btVerCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AlmacenActivity.this, CarritoActivity.class);
                intent.putExtra("Usuarios", miUsuario.uid);
                startActivity(intent);
            }
        });

        btAgregarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(AlmacenActivity.this, AgregarProductosActivity.class);
                startActivity(intent2);
            }
        });

        getUserInfo();
    }

    private void getUserInfo(){
        String id= mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    miUsuario=snapshot.getValue(Usuarios.class);
                    String name= snapshot.child("name").getValue().toString();
                    String email= snapshot.child("email").getValue().toString();

                    nombreUsuario.setText(name);
                    emailUsuario.setText(email);

                    if(miUsuario.carrito != null){
                        btVerCarrito.setText("Ver Carrito ( "+miUsuario.carrito.size()+")");
                    }else{
                        btVerCarrito.setText("Ver Carrito");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                Intent intent2= new Intent (AlmacenActivity.this, PrimerMActivity.class);
                startActivity(intent2);
                break;

            default: break;
        }
        return true;
    }
}