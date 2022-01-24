package es.proyecto.estancoentumovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.proyecto.estancoentumovil.model.Productos;

public class DetalleProductoActivity extends AppCompatActivity {

    private TextView nombreDetalle, precioDetalle, otroDetalle;
    private Button btDetalle;

    Productos miProducto;
    String idUsuario;

    FirebaseDatabase miBase;
    DatabaseReference miReferencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        nombreDetalle=(TextView) findViewById(R.id.nombreDetalle);
        precioDetalle=(TextView) findViewById(R.id.precioDetalle);
        otroDetalle=(TextView) findViewById(R.id.otroDetalle);
        btDetalle=(Button) findViewById(R.id.btDetalle);

        miBase= FirebaseDatabase.getInstance();
        miReferencia= miBase.getReference();

        String idProducto= getIntent().getExtras().getString("Productos");
        idUsuario= getIntent().getExtras().getString("Usuarios");

        miReferencia.child("Productos").child(idProducto).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                miProducto= snapshot.getValue(Productos.class);
                nombreDetalle.setText(miProducto.getNombre());
                precioDetalle.setText(miProducto.getPrecio());
                otroDetalle.setText(miProducto.getCategoria());

                setTitle("Detalle: "+miProducto.getNombre());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miReferencia.child("Usuarios").child(idUsuario).child("carrito").child(miProducto.getUid()).setValue(true);
                finish();
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
                Intent intent2= new Intent (DetalleProductoActivity.this, AlmacenActivity.class);
                startActivity(intent2);
                break;

            default: break;
        }
        return true;
    }
}