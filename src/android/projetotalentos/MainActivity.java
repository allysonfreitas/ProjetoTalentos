package android.projetotalentos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public int idAfirmacao = 1000; //salva o id da afirmacao que esta na tela
	public int idTalento = 0;
	public String[][] resposta = new String[55][4]; // salva os resultados da resposta do usuario // 0:id_afirmacao / 1:id_talento / 2:likert / 3:tempo
	public int contador = 0; //conta o numero de afirmacoes para terminar o teste
	public String[][] csvMain = new String[56][4];
	
    public String[][] carregaCSV() {
    	//Ler CSV de afirmações
        InputStream inputStream = getResources().openRawResource(R.raw.afirmacoes_);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String[] values = null;
        String[][] total = new String[56][4];
        int i = 0;
        
        try {
        	while ((line = reader.readLine()) != null) {
        		//process the line, stuff to List
    	        values = line.split(";");
    	        total[i][0] = values[0];
    	        total[i][1] = values[1];
    	        total[i][2] = values[2];
    	        total[i][3] = values[3];
    	        
    	        i++;
    	    }
        } catch(Exception e) {} 
        
        return total;
    } 
    
    public String[] randomAfirmacao(String[][] csv) {
    	String[] afirmacao = null;
    	Random random = new Random();
    	
    	 int r = random.nextInt(csv.length);
         
         if(r != idAfirmacao) {
         	idAfirmacao = Integer.parseInt(csv[r][3]);
         	idTalento = Integer.parseInt(csv[r][0]);
         	contador++;
         	return afirmacao = csv[r]; }
         	
         else
         	return randomAfirmacao(csv);
    }
    
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        csvMain = carregaCSV();
        RatingBar nota = (RatingBar) findViewById(R.id.ratingBar1);

          
        // Cria descrição das estrelas
        nota.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
        	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
 
        		if(rating == 1.0)
        			Toast.makeText(getApplicationContext(),"Discordo plenamente",Toast.LENGTH_SHORT).show();
        		else if(rating == 2.0)
        			Toast.makeText(getApplicationContext(),"Discordo em partes",Toast.LENGTH_SHORT).show();
        		else if(rating == 3.0)
        			Toast.makeText(getApplicationContext(),"Neutro",Toast.LENGTH_SHORT).show();
        		else if(rating == 4.0)
        			Toast.makeText(getApplicationContext(),"Concordo",Toast.LENGTH_SHORT).show();
        		else if (rating == 5.0)
        			Toast.makeText(getApplicationContext(),"Concordo plenamente",Toast.LENGTH_SHORT).show();
            }
           });
              
        
        TextView afirmacao = (TextView) findViewById(R.id.textView2);
		afirmacao.setText(randomAfirmacao(csvMain)[2].toString());
        
        
        
        Chronometer cronometro = (Chronometer) findViewById(R.id.chronometer1);
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        RatingBar nota = (RatingBar) findViewById(R.id.ratingBar1);
       
        //Define afirmação
       // Resources res = getResources();
     //   String[] afirmacoes = res.getStringArray(R.array.Afirmacoes);
        
       
        // Cria descrição das estrelas
        nota.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
        	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
 
        		if(rating == 1.0)
        			Toast.makeText(getApplicationContext(),"Discordo plenamente",Toast.LENGTH_SHORT).show();
        		else if(rating == 2.0)
        			Toast.makeText(getApplicationContext(),"Discordo em partes",Toast.LENGTH_SHORT).show();
        		else if(rating == 3.0)
        			Toast.makeText(getApplicationContext(),"Neutro",Toast.LENGTH_SHORT).show();
        		else if(rating == 4.0)
        			Toast.makeText(getApplicationContext(),"Concordo",Toast.LENGTH_SHORT).show();
        		else if (rating == 5.0)
        			Toast.makeText(getApplicationContext(),"Concordo plenamente",Toast.LENGTH_SHORT).show();
            }
           });
        
       
        
        TextView afirmacao = (TextView) findViewById(R.id.textView2);
		afirmacao.setText(defineAfirmacao(idAfirmacao).toString());
		
		
        
        return true;
    } */
    
    public void pularAfirmacao(View view) {
    	
        Resources res = getResources();
    	
        //Para cronômetro
        Chronometer cronometro = (Chronometer) findViewById(R.id.chronometer1);
        cronometro.stop();
        
        RatingBar nota = (RatingBar) findViewById(R.id.ratingBar1);
        
        //Atribui os valores de resposta ao vetor
       resposta[contador][0] = Integer.toString(idAfirmacao);
       resposta[contador][1] = Integer.toString(idTalento);
       resposta[contador][2] = Float.toString(nota.getRating());
       resposta[contador][3] = cronometro.getText().toString();
        
              
 
       
        //if(contador < 5) {
        	TextView afirmacao = (TextView) findViewById(R.id.textView2);
        	afirmacao.setText(randomAfirmacao(csvMain)[2].toString());
        	cronometro.setBase(SystemClock.elapsedRealtime());
        	nota.setRating(0);
        	
        	cronometro.start();
       // } //se for maior, vai pro relatório
       // else
       // 	geraRelatorio();
        
    } 
    
    public void calculaTalento() {
    	
    }
    
    
    public void geraRelatorio() {
    	FileOutputStream fos = null;
    	int cont = 0;
    	
    	try {
    		fos = openFileOutput("relatorio.txt",MODE_PRIVATE);
    		
    		while(cont < resposta.length) {
    			fos.write((resposta[cont][0] + " " + resposta[cont][1] + " " + resposta[cont][2] + " " + resposta[cont][3] + " // ").getBytes());
    			Toast.makeText(getApplicationContext(), "Arquivo criado com sucesso!", Toast.LENGTH_SHORT).show();
    		}
    	} catch(FileNotFoundException e) {
    			Log.e("CreateFile", e.getLocalizedMessage());
    	} catch(IOException e) {
			Log.e("CreateFile", e.getLocalizedMessage());
    	}
    	finally {
    		if(fos!=null) {
    			try {
    				//drain the stream
        			fos.flush();
        			fos.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			
    		}  
    	}
    }
    
    /*public String defineAfirmacao(int n) {
    	String afirmacao = null;
    	Random random = new Random();
    	
    	//Ler CSV de afirmações
        InputStream inputStream = getResources().openRawResource(R.raw.afirmacoes_);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String[] values = null;
        String[][] total = new String[60][4];
        int i = 0;
        
        try {
        	while ((line = reader.readLine()) != null) {
        		//process the line, stuff to List
    	        values = line.split(";");
    	        total[i][0] = values[0];
    	        total[i][1] = values[1];
    	        total[i][2] = values[2];
    	        total[i][3] = values[3];
    	        
    	        i++;
    	    }
        } catch(Exception e) {}
    	
        int r = random.nextInt(total.length);
        
        if(r != n) {
        	idAfirmacao = Integer.parseInt(total[r][3]);
        	idTalento = Integer.parseInt(total[r][0]);
        	contador++;
        	return afirmacao = total[r][2]; }
        	
        else
        	return defineAfirmacao(n); 
    }
    */
    /*
    public void geraResultado() {
    	int cont = 0;
    	
    	while(cont <= resposta.length) {
    		TextView respostashow = (TextView) findViewById(R.id.textView2);
        	respostashow.setText(resposta[cont][0] + " " + resposta[cont][1] + " " + resposta[cont][2] + " " + resposta[cont][3]);
        	cont++;
    	}
    }
    

    */
    
}
