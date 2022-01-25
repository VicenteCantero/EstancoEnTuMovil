package es.proyecto.estancoentumovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class WebTabacaleraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_tabacalera);

        WebView miWeb= (WebView) findViewById(R.id.wvWeb);
        miWeb.loadUrl("https://estanclick.es/tienda/blog/6_precios-actualizados-de-labores-de-tabaco-en-peninsula-y-baleares.html");
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
                Intent intent2= new Intent (WebTabacaleraActivity.this, AlmacenActivity.class);
                startActivity(intent2);
                break;

            default: break;
        }
        return true;
    }
}