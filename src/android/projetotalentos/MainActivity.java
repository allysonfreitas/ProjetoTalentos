package android.projetotalentos;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        RatingBar nota = (RatingBar) findViewById(R.id.ratingBar1);
        
        
        // Cria descrição das estrelas
        nota.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
        	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
 
        		/* Não consegui transformar rating em int
        		switch(rating.intValue()) {
        			case 1: 
        				Toast.makeText(getApplicationContext(),"Discordo plenamente",Toast.LENGTH_LONG).show();
        				break;
        			case 2:
        				Toast.makeText(getApplicationContext(),"Discordo em partes",Toast.LENGTH_LONG).show();
        				break;
        			case 3:
        				Toast.makeText(getApplicationContext(),"Neutro",Toast.LENGTH_LONG).show();
        				break;
        			case 4:
        				Toast.makeText(getApplicationContext(),"Concordo",Toast.LENGTH_LONG).show();
        				break;
        			case 5:
        				Toast.makeText(getApplicationContext(),"Concordo plenamente",Toast.LENGTH_LONG).show();
        				break;
        		} */
        		
        		if(rating == 1.0)
        			Toast.makeText(getApplicationContext(),"Discordo plenamente",Toast.LENGTH_LONG).show();
        		else if(rating == 2.0)
        			Toast.makeText(getApplicationContext(),"Discordo em partes",Toast.LENGTH_LONG).show();
        		else if(rating == 3.0)
        			Toast.makeText(getApplicationContext(),"Neutro",Toast.LENGTH_LONG).show();
        		else if(rating == 4.0)
        			Toast.makeText(getApplicationContext(),"Concordo",Toast.LENGTH_LONG).show();
        		else if (rating == 5.0)
        			Toast.makeText(getApplicationContext(),"Concordo plenamente",Toast.LENGTH_LONG).show();
        		
        		//Toast.makeText(getApplicationContext(),"Your Selected Ratings  : " + String.valueOf(rating),Toast.LENGTH_LONG).show();
        		//Continuar amanha
            }
           });
        
        
        return true;
    }
    
    public void mostrarDescricao(View view) {
    	RatingBar nota = (RatingBar) findViewById(R.id.ratingBar1);
    	TextView texto = (TextView) findViewById(R.id.textView1);
    	
    	String strNota = nota.getRating() + "";
    	
    
    	texto.setText(strNota);
    	
    }
    
    
    
}
