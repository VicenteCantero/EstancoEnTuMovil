package es.proyecto.estancoentumovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PrimerMActivity extends AppCompatActivity {

    private Button btAlmacen, btClientes, btPedidos, btCerrar, btWeb;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primer_m);

        mAuth=FirebaseAuth.getInstance();

        btAlmacen= (Button) findViewById(R.id.btAlmacen);
        btClientes= (Button) findViewById(R.id.btClientes);
        btPedidos= (Button) findViewById(R.id.btPedidos);
        btCerrar= (Button) findViewById(R.id.btCerrar);
        btWeb= (Button) findViewById(R.id.btWeb);


        btAlmacen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (PrimerMActivity.this, AlmacenActivity.class);
                startActivity(intent);
            }
        });

        btClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (PrimerMActivity.this, ListaClientesActivity.class);
                startActivity(intent);
            }
        });

        btPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (PrimerMActivity.this, PedidosActivity.class);
                startActivity(intent);
            }
        });


        btWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent (PrimerMActivity.this, WebTabacaleraActivity.class);
                startActivity(intent);
            }
        });

        btCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent= new Intent(PrimerMActivity.this, AuthActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}