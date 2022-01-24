package es.proyecto.estancoentumovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.HashMap;
import java.util.UUID;

import es.proyecto.estancoentumovil.model.Pedido;
import es.proyecto.estancoentumovil.model.Productos;

public class CarritoActivity extends AppCompatActivity {

    private ListView lvCarrito;
    private Button btContinuar;
    private TextView tvTotal;

    private String fecha, hora, fechaPedido;

    private double PrecioTotalId= 0.0;

    ArrayList<Productos> listaCarrito;
    ArrayAdapter<Productos> adaptador;

    String idUsuario;

    FirebaseDatabase miBase;
    DatabaseReference miReferencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        lvCarrito= (ListView) findViewById(R.id.lvCarrito);
        tvTotal=(TextView) findViewById(R.id.tvTotal);
        btContinuar=(Button)findViewById(R.id.btContinuar) ;
        setTitle("Carrito");


        idUsuario= getIntent().getExtras().getString("Usuarios");
        listaCarrito= new ArrayList<>();
        adaptador= new ArrayAdapter<>(CarritoActivity.this, android.R.layout.simple_list_item_1, listaCarrito);

        lvCarrito.setAdapter(adaptador);

        lvCarrito.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                miReferencia.child("Usuarios").child(idUsuario).child("carrito").child(listaCarrito.get(i).getUid()).removeValue();
                return true;
            }
        });

        miBase= FirebaseDatabase.getInstance();
        miReferencia= miBase.getReference();


        miReferencia.child("Usuarios").child(idUsuario).child("carrito").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    listaCarrito.clear();
                    for(DataSnapshot item: snapshot.getChildren()) {
                        String idProducto= item.getKey();
                        miReferencia.child("Productos").child(idProducto).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Productos miProducto= snapshot.getValue(Productos.class);
                                listaCarrito.add(miProducto);
                                adaptador.notifyDataSetChanged();
                                calcularTotal();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }else{
                    tvTotal.setText("Total: 0");
                    adaptador.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat curreDateFormat = new SimpleDateFormat("MM-dd-yyyy");
                fecha = curreDateFormat.format(calendar.getTime());

                SimpleDateFormat curreTimeFormat = new SimpleDateFormat("HH:mm:ss");
                hora = curreTimeFormat.format(calendar.getTime());

                fechaPedido = fecha +" "+ hora;

                String idUsuario = getIntent().getExtras().getString("Usuarios");
                String total = tvTotal.getText().toString() ;
                String fecha= fechaPedido.toString();


                Pedido pedido = new Pedido();
                pedido.setUid(UUID.randomUUID().toString());
                pedido.setIdUsuario(idUsuario);
                pedido.setTotal(total);
                pedido.setFecha(fecha);
                miReferencia.child("Pedidos").child(pedido.getUid()).setValue(pedido);
                Toast.makeText(CarritoActivity.this, "Añadido", Toast.LENGTH_SHORT).show();

                Intent intent= new Intent (CarritoActivity.this, PedidosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void calcularTotal () {
            double total = 0;

            for (Productos miProducto : listaCarrito) {
                total = total + Double.parseDouble(miProducto.getPrecio());
            }

            tvTotal.setText("Total: " + total);
    }



}