package android.projetotalentos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Obtém a média aritmética de um Array
 * 
 * @param
 * @return
 */
public class MainActivity extends Activity {
	
	public int idAfirmacao = 1000; //salva o id da afirmacao que esta na tela
	public int idTalento = 0;
	public String[][] resposta = new String[55][4]; // salva os resultados da resposta do usuario // 0:id_afirmacao / 1:id_talento / 2:likert / 3:tempo
	public int contador = 0; //conta o numero de afirmacoes para terminar o teste
	public String[][] csvMain = new String[56][4]; 
	public float[] somaTalento = new float[34];
	public float[][] respostas = new float[55][4];
	
	/**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */
    public String[][] carregaCSV() {
    	//Ler CSV de afirmações
        InputStream inputStream = getResources().openRawResource(R.raw.afirmacoes_);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha;
        String[] valores = null;
        String[][] vetorCSV = new String[56][4];
        int i = 0;
        
        try {
        	while ((linha = reader.readLine()) != null) {
        		//process the line, stuff to List
    	        valores = linha.split(";");
    	        vetorCSV[i][0] = valores[0];
    	        vetorCSV[i][1] = valores[1];
    	        vetorCSV[i][2] = valores[2];
    	        vetorCSV[i][3] = valores[3];
    	        
    	        i++;
    	    }
        } catch(Exception e) {} 
        
        return vetorCSV;
    } 
    
    /**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */
    public String[] randomAfirmacao(String[][] csv) {
    	String[] afirmacao = null;
    	Random random = new Random();
    	
    	 int r = random.nextInt(csv.length - 1);
         
         if(r != idAfirmacao) {
         	idAfirmacao = Integer.parseInt(csv[r][3]);
         	idTalento = Integer.parseInt(csv[r][0]);
     
         	return afirmacao = csv[r]; }
         	
         else
         	return randomAfirmacao(csv);
    }
    
    /**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
               
        csvMain = carregaCSV();
        RatingBar nota = (RatingBar) findViewById(R.id.ratingBar1);

        /*
         * Cria descrição das estrelas
         */
        nota.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
        	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        		Toast toast = new Toast(getApplicationContext());
    			switch((int) rating) {
        		case 1:
        			toast = Toast.makeText(getApplicationContext(),"Discordo plenamente",Toast.LENGTH_SHORT);
        			toast.setGravity(Gravity.CENTER, 0, 0);
        			toast.show();
        			break;
        		case 2:
        			toast = Toast.makeText(getApplicationContext(),"Discordo em partes",Toast.LENGTH_SHORT);
        			toast.setGravity(Gravity.CENTER, 0, 0);
        			toast.show();
        			break;
        		case 3:
        			toast = Toast.makeText(getApplicationContext(),"Neutro",Toast.LENGTH_SHORT);
        			toast.setGravity(Gravity.CENTER, 0, 0);
        			toast.show();
        			break;
        		case 4:
        			toast = Toast.makeText(getApplicationContext(),"Concordo",Toast.LENGTH_SHORT);
        			toast.setGravity(Gravity.CENTER, 0, 0);
        			toast.show();
        			break;
        		case 5:
        			toast = Toast.makeText(getApplicationContext(),"Concordo plenamente",Toast.LENGTH_SHORT);
        			toast.setGravity(Gravity.CENTER, 0, 0);
        			toast.show();
        			break;
        		}
            }
           });
              
        
        /*
         * Carrega a TextView com a afirmação
         */
        TextView afirmacao = (TextView) findViewById(R.id.textView2);
		afirmacao.setText(randomAfirmacao(csvMain)[2].toString());
        
        
        /*
         * Inicia cronômetro
         */
        Chronometer cronometro = (Chronometer) findViewById(R.id.chronometer1);
        cronometro.setBase(SystemClock.elapsedRealtime());
        cronometro.start();

    }
    
    /**
     * Transforma o valor do cronômetro de texto para número (em segundos)
     * 
     * @param view
     */
    public int emSegundos(String texto) {
    	int tempo = 0;
    	String[] valores = null;
    	
    	valores = texto.split(":");
    	tempo = Integer.parseInt(valores[0])*60 + Integer.parseInt(valores[1]);  	
    	
    	return tempo;
    }
    
    /**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */
    public void pularAfirmacao(View view) {
    	RatingBar nota = (RatingBar) findViewById(R.id.ratingBar1);
    	
    	if(nota.getRating() != 0) {
	        Resources res = getResources();
	        int cont = contador; // teste
	    	float[] resp = respostas[contador]; //teste
	    	
	    	
	        //Para cronômetro
	        Chronometer cronometro = (Chronometer) findViewById(R.id.chronometer1);
	        cronometro.stop();
	        
	        
	        
	        //Atribui os valores de resposta ao vetor
	       resposta[contador][0] = Integer.toString(idAfirmacao);
	       resposta[contador][1] = Integer.toString(idTalento);
	       resposta[contador][2] = Float.toString(nota.getRating());
	       resposta[contador][3] = cronometro.getText().toString();
	        
	       respostas[contador] = calculaTalentoAntes();
	       resp = respostas[contador];
	       
	       
	       contador++;
	       
	        //if(contador < 5) {
	        if(contador < csvMain.length) {
	        	TextView afirmacao = (TextView) findViewById(R.id.textView2);
	        	afirmacao.setText(randomAfirmacao(csvMain)[2].toString());
	        	cronometro.setBase(SystemClock.elapsedRealtime());
	        	nota.setRating(0);
	        	cronometro.start();
	        	
	        } else {
	        	calculaTalentos();
	        }
    	} else {
    		Toast toast = Toast.makeText(getApplicationContext(),"Escolha a opção que mais te define antes de prosseguir",Toast.LENGTH_LONG);
    		toast.setGravity(Gravity.CENTER, 0, 0);
    		toast.show();
    	}
        
    } 
    
    /**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */
    public float[] calculaTalentoAntes() {
    	int nota = 0; 
    	String sRate = resposta[contador][2].toString();
    	float rate = Float.parseFloat(sRate);
    	int tempo = emSegundos(resposta[contador][3].toString());
    	float[] resp = new float[4];
    	
    	/*
    	 * O cara acaba de responder então temos:
    	 * ID AFIRMAÇÂO / ID TALENTO / LIKERT / TEMPO
    	 * 
    	 * 1o LIKERT
    	 * Se LIKERT = 1 => -4
    	 * 			 = 2 => -2
    	 * 			 = 3 => 1
    	 * 			 = 4 => 2
    	 * 			 = 4 => 4	
    	 */
    	
    	switch((int) rate) {
	    	case 1:
	    		nota = -4;
	    		break;
	    	case 2:
	    		nota = -2;
	    		break;
	    	case 4:
	    		nota = 2;
	    		break;
	    	case 5:
	    		nota = 4;
	    		break;
	    	default:
	    		nota = 1;
    	}
    	
    	resp[0] = Float.parseFloat(resposta[contador][1].toString());
    	resp[1] = Float.parseFloat(resposta[contador][2].toString());
    	resp[2] = (float) nota;
    	resp[3] = (float) tempo;
    	
    	return resp;
    }
    
    /**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */
    public void calculaTalentos() {
    	float[] tempo = new float[55];
    	float dpTempo = 0, maTempo = 0;
    	float nota[] = new float[55];
    	int constante = 0;
    	float[][] resp = new float[55][4];
    	float[] somaTal = new float[34];
    	
    	resp = respostas;
    	
    	for(int i = 0; i < respostas.length; i++) {
    		if(respostas[i] != null) {
    			tempo[i] = respostas[i][3];
    			nota[i] = respostas[i][2];
    		}
    	}
    	
    	maTempo = mediaAritmetica(tempo);
    	dpTempo = desvioPadrao(tempo); 
    	
    	for(float segundos : tempo) {
    		if(segundos > maTempo + dpTempo) {
    			constante = -1;
    		} else if(segundos < maTempo - dpTempo) {
    			constante = 1;
    		} else {
    			constante = 0;
    		}
    	}
    	
    	for(int i = 0; i < respostas.length; i++) {
    		if(respostas[i] != null) {
    			somaTalento[(int) respostas[i][1]] += respostas[i][2] + constante;
    		}
    	}
    	
    	somaTal = somaTalento;
    	
    	//somaTalento[idTalento] += Float.parseFloat(resposta[contador][2]) + constante;
    	
    	float desvioPadrao = desvioPadrao(somaTalento);
    	float mediaAritmetica = mediaAritmetica(somaTalento);
    	
    	int[] superTalentos = new int[34];
    	int contST = 0;
    	int[] medioTalentos = new int[341];
    	int contMT = 0;
    	int[] antiTalentos = new int[341];
    	int contAT = 0;
    	
    	//Separa os talentos em 3 grupos
    	
    	for(int i=0; i<somaTalento.length; i++) {
    		if(somaTalento[i] > mediaAritmetica + desvioPadrao) {
    			superTalentos[contST] = i;
    			contST++;
    		} else if(somaTalento[i] < mediaAritmetica - desvioPadrao) {
    			medioTalentos[contMT] = i;
    			contMT++;
    		} else {
    			antiTalentos[contAT] = i;
    			contAT++;
    		}
    	}

    	//TextView resultado = (TextView) findViewById(R.id.textView2);
    	//resultado.setText(superTalentos[0]);
    }
    
    
    
    /**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */
    public float mediaAritmetica(float[] Numeros) {
    	float somatorio = 0;
    	int i = 0;
    		
    	while(i < Numeros.length) {
    		somatorio += Numeros[i];
    		i++;
    	}
    	
    	return somatorio/Numeros.length;
    }
    
    /**
     * Obtém odesvio padrão de um Array
     * 
     * @param
     * @return
     */
    public float desvioPadrao(float[] Numeros) {
    	double conta = 0;
    	float desvio = 0;
    	
    	if(Numeros.length == 1)
    		return 0;
    	else {
    		float media = mediaAritmetica(Numeros);
    		float somatorio = 0;
    		
    		for(int i = 0; i < Numeros.length; i++) {
    			float resultado = Numeros[i] - media;
    			somatorio += resultado*resultado;
    		}
    		
    		conta = Math.sqrt((( 1/(float)(Numeros.length - 1))*somatorio));
    		desvio = (float) conta;
    		
    		return desvio;
    	}
    }
    
    /**
     * Obtém a média aritmética de um Array
     * 
     * @param
     * @return
     */
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
    
    /**
     * Envia e-mail com relatório
     * Para ser possível o envio, é necessário adicionar android.permission.INTERNET no Manifest
     * 
     * @param
     * @return
     */
    public void enviarEmail(String destinatario) {
    	Intent itEmail = new Intent(Intent.ACTION_SEND);
    	itEmail.setType("plain/text");
    	itEmail.putExtra(Intent.EXTRA_SUBJECT, "Título do E-mail");
    	itEmail.putExtra(Intent.EXTRA_TEXT, "Corpo do e-mail");
    	itEmail.putExtra(Intent.EXTRA_EMAIL, destinatario);
    	itEmail.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:/mnt/sdcard/relatorio.pdf"));
    	startActivity(Intent.createChooser(itEmail, "Escolha o aplicativo para envio do e-mail..."));
    	
    }
    
}
