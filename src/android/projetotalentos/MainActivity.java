package android.projetotalentos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

import org.apache.pdfbox.exceptions.COSVisitorException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.projetotalentos.R.id;
import android.projetotalentos.R.layout;
import android.projetotalentos.R.raw;
import android.view.Gravity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ProjetoTalentoBeta
 * 
 * @param
 * @return
 */
public class MainActivity extends Activity {
	
	public int idAfirmacao = 1000; //salva o id da afirmacao que esta na tela
	public int idTalento = 0;
	public String[][] resposta = new String[170][4]; // salva os resultados da resposta do usuario // 0:id_afirmacao / 1:id_talento / 2:likert / 3:tempo
	public int contador = 0; //conta o numero de afirmacoes para terminar o teste
	public String[][] csvMain = new String[170][3]; //0: id-afirmacao/ 1:id-talento / 2: afirmacao
	public int[][] somaTalento = new int[34][2];
	public float[][] respostas = new float[170][4];
	public int[] naoRepete = new int[170];
	public int[] ids = new int[170];
	int[] finalTalentos = new int[34];
	
	/**
     * Carrega o CSV
     * 
     * @param
     * @return
     */
    public String[][] carregaCSV() {
    	//Ler CSV de afirmações
        InputStream inputStream = getResources().openRawResource(raw.csvafirmacoes);  //getResources().openRawResource(R.raw.csvafirmacoes);
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String linha;
        String[] valores = null;
        String[][] vetorCSV = new String[170][3];
        int i = 0;
        
        try {
        	while ((linha = reader.readLine()) != null) {
    	        valores = linha.split(";");
    	        vetorCSV[i][0] = valores[0];
    	        vetorCSV[i][1] = valores[1];
    	        vetorCSV[i][2] = valores[2];
    	        
    	        i++;
    	    }
        } catch(Exception e) {} 
        
        return vetorCSV;
    } 
    
    
    public int[] geraSeqAleatoria(int tamanho) {  
    	   Random random = new Random();  
    	  
    	   // Array em ordem  
    	   int[] seqAleatoria = new int[tamanho];  
    	   for (int i = 0; i < tamanho; i++) {  
    	      seqAleatoria[i] = i;  
    	   }  
    	  
    	   // Embaralhamos o array  
    	   for (int i = 0; i < tamanho - 1; i++) {  
    	      int max = tamanho - i - 1;  
    	      int ind = random.nextInt(max);  
    	  
    	      int aux = seqAleatoria[ind];  
    	      seqAleatoria[ind] = seqAleatoria[max];  
    	      seqAleatoria[max] = aux;  
    	   }  
    	   return seqAleatoria;  
    	}  

    
    /**
     * Escolhe uma afirmacao aleatoriamente
     * 
     * @param
     * @return
     */
    public String[] randomAfirmacao(int[] vetor) {
    	String[] afirmacao;
    	    	return afirmacao = csvMain[vetor[contador]]; 
    }
    
    /**
     * Criação da tela
     * 
     * @param
     * @return
     */   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        ids = geraSeqAleatoria(170);
        
        csvMain = carregaCSV();
        RatingBar nota = (RatingBar) findViewById(id.ratingBar1);
        		//(R.id.ratingBar1);

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
              String[] randAfirmacao = randomAfirmacao(ids);
        /*
         * Carrega as TextViews com a afirmação e numeração
         */
        TextView afirmacao = (TextView) findViewById(id.textView2);//(R.id.textView2);
        TextView contAfirmacao = (TextView) findViewById(id.textView3);
        
        contAfirmacao.setText(Integer.toString(contador + 1));
        afirmacao.setText(randAfirmacao[2].toString());
        
        try {
    		idAfirmacao = Integer.parseInt(randAfirmacao[0]);
    	} catch (NumberFormatException ex) { 
    		idAfirmacao = 0; 
    	} 
    	
    	try {
    		idTalento = Integer.parseInt(randAfirmacao[1]);
    	} catch (NumberFormatException ex) { 
    		idTalento = 0; 
    	}
        
        /*
         * Inicia cronômetro
         */
        Chronometer cronometro = (Chronometer) findViewById(id.chronometer1);//(R.id.chronometer1);
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
    	RatingBar nota = (RatingBar) findViewById(id.ratingBar1);//(R.id.ratingBar1);
    	if(nota.getRating() != 0) {
	        Resources res = getResources();  	
	    	
	        //Para cronometro
	        Chronometer cronometro = (Chronometer) findViewById(id.chronometer1);//(R.id.chronometer1);
	        cronometro.stop();
	        
	        contador++;
	       
	      if(contador < csvMain.length) {
	        //Atribui os valores de resposta ao vetor
	       resposta[contador-1][0] = Integer.toString(idAfirmacao);
	       resposta[contador-1][1] = Integer.toString(idTalento);
	       resposta[contador-1][2] = Float.toString(nota.getRating());
	       resposta[contador-1][3] = cronometro.getText().toString();
	        
	       respostas[contador-1] = calculaTalentoAntes();
	        //
	        
	        	
	        	
	        	String[] randAfirmacao = randomAfirmacao(ids);
	        	TextView afirmacao = (TextView) findViewById(id.textView2);//(R.id.textView2);
	            TextView contAfirmacao = (TextView) findViewById(id.textView3);
	            
	            contAfirmacao.setText(Integer.toString(contador + 1));
	            afirmacao.setText(randAfirmacao[2].toString());
	        	cronometro.setBase(SystemClock.elapsedRealtime());

	        	try {
	        		idAfirmacao = Integer.parseInt(randAfirmacao[0]);
	        	} catch (NumberFormatException ex) { 
	        		idAfirmacao = 0; 
	        	} 
	        	
	        	try {
	        		idTalento = Integer.parseInt(randAfirmacao[1]);
	        	} catch (NumberFormatException ex) { 
	        		idTalento = 0; 
	        	}

	        	
	            
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
     * Obt�m a m�dia aritm�tica de um Array
     * 
     * @param
     * @return
     */
    public float[] calculaTalentoAntes() {
    	int nota = 0; 
    	String sRate = resposta[contador-1][2].toString();
    	float rate = Float.parseFloat(sRate);
    	int tempo = emSegundos(resposta[contador-1][3].toString());
    	float[] resp = new float[4];
    	
    	/*
    	 * O cara acaba de responder ent�o temos:
    	 * ID AFIRMA��O / ID TALENTO / LIKERT / TEMPO
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
	    		nota = 0;
    	}
    	
    	try {
    		resp[0] = Float.parseFloat(resposta[contador-1][0]);
    	} catch (NumberFormatException ex) { 
    		resp[0] = 0; 
    	} 
    	
    	try {
    		resp[1] = Float.parseFloat(resposta[contador-1][1]);
    	} catch (NumberFormatException ex) { 
    		resp[1] = 0; 
    	}
    	    		
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

    	float[] tempo = new float[170];
    	//float[] tempo = new float[10];
    	float dpTempo = 0, maTempo = 0;
    	float nota[] = new float[170];
    	//float nota[] = new float[10];
    	int constante = 0;

    	int aux1SomaTal = 0;
    	int aux2SomaTal = 0;
    	
    	for(int i = 0; i < respostas.length; i++) {
    	//for(int i = 0; i < 10; i++) {
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
    	
    	for(int i=0;i<34;i++)
    		somaTalento[i][0] = i;
    	
    	for(int i = 0; i < respostas.length; i++) {
    		somaTalento[(int) respostas[i][1]][1] += (int) respostas[i][2] + constante;
    	}  	
   
    	int i = somaTalento.length;
    	int j = 0;
    	for(i = 0; i < somaTalento.length; i++) {
    		for(j = 0; j < somaTalento.length - 1; j++) {
    			if(somaTalento[j][1] < somaTalento[j+1][1]) {
    				aux1SomaTal = somaTalento[j][0];
    				aux2SomaTal = somaTalento[j][1];
    				somaTalento[j][0] = somaTalento[j+1][0];
    				somaTalento[j][1] = somaTalento[j+1][1];
    				somaTalento[j+1][0] = aux1SomaTal;
    				somaTalento[j+1][1] = aux2SomaTal;
    			}
    		}
    	} 	 	   	
    	
    	for(i=0;i<somaTalento.length;i++)
    		finalTalentos[i] = somaTalento[i][0];
    	
    	
    	//finalTalentos = somaTalento[0];
    	
    	geraRelatorio();
    	
    	/*
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
    	} */

    	//geraRelatorio(somaTalento);
    	//TextView resultado = (TextView) findViewById(R.id.textView2);
    	//resultado.setText(superTalentos[0]);
    }
    
    
    
    /**
     * Obt�m a m�dia aritm�tica de um Array
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
     * Obt�m odesvio padr�o de um Array
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
     * Obt�m a m�dia aritm�tica de um Array
     * 
     * @param
     * @return
     * @throws IOException 
     * @throws COSVisitorException 
     */
    public void geraRelatorio() /*throws IOException, COSVisitorException */ {
       	final ProgressDialog pbarDialog;
        pbarDialog = ProgressDialog.show(MainActivity.this,"Aguarde","Gerando relatório...",false,true);
        pbarDialog.setIcon(R.drawable.ic_launcher);
        pbarDialog.setCancelable(false);
        new Thread() {
         	  public void run() { 
         			  InputStream inputStream = getResources().openRawResource(raw.talentos);  //getResources().openRawResource(R.raw.csvafirmacoes);
         		      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
         		      String linha;
         		      String[] valores = null;
         		      String[][] vetorCSV = new String[34][3];
         		      int i = 0;
         		      try {
	     		    	  while ((linha = reader.readLine()) != null) {
	     		    		  valores = linha.split(";");
	     		     	      vetorCSV[i][0] = valores[0];
	     		     	      vetorCSV[i][1] = valores[1];
	     		     	      vetorCSV[i][2] = valores[2];
	     		     	      
	     		     	      i++;
	     		    	  }
         		      } catch(IOException e) {}
         		      
     		         try {	 
		 		         File outputDir = new File(Environment.getExternalStorageDirectory(), "ProjetoTalentos");
		 	        	 outputDir.mkdirs();
		 	        	 File outputFile = new File(outputDir, "Relatorio.txt");
		 	                
		 	        	 FileOutputStream fos = new FileOutputStream(outputFile);
		 	             //PrintWriter pw = new PrintWriter(fos);
		
		 	             // Escreve talentos primários
		 	             //pw.println("TALENTOS PRIMÁRIOS \n");
		 	             fos.write(("TALENTOS PRIMÁRIOS \n").getBytes());
		 	        	 for(i = 0; i < 5; i++) {
		 	            	//pw.println(Integer.toString(i+1) + "- " + vetorCSV[finalTalentos[i]][1]+ "\n");
		 	            	//pw.println(vetorCSV[finalTalentos[i]][2]);
		 	            	//pw.println("\n");
		 	        		fos.write((Integer.toString(i+1) + "- " + vetorCSV[finalTalentos[i]][1]+ "\n").getBytes());
		 	        		fos.write(vetorCSV[finalTalentos[i]][2].getBytes());
		 	        		fos.write(("\n").getBytes());
		 	             }
		 	             //pw.println("\n");
		 	             //pw.println("\n");
		 	             fos.write(("\n").getBytes());
		 	             fos.write(("\n").getBytes());
		 	            
		 	            
		 	             //Escreve talentos secundários
		 	             //pw.println("TALENTOS SECUNDÁRIOS \n");
		 	             fos.write(("TALENTOS SECUNDÁRIOS").getBytes());
		 	             for(i = 0; i < 5; i++) {
		 	            	//pw.println(Integer.toString(i+1) + "- " + vetorCSV[finalTalentos[i+5]][1]+ "\n");
		 	            	//pw.println(vetorCSV[finalTalentos[i+5]][2]);
		 	            	//pw.println("\n");
		 	            	fos.write((Integer.toString(i+1) + "- " + vetorCSV[finalTalentos[i+5]][1]+ "\n").getBytes());
		 	        		fos.write(vetorCSV[finalTalentos[i+5]][2].getBytes());
		 	            	fos.write(("\n").getBytes());
		 	             }
		 	             //pw.println("\n");
		 	             //pw.println("\n");
		 	             fos.write(("\n").getBytes());
		 	             fos.write(("\n").getBytes());
		 	            
		 	             
		 	             //Escreve anti talentos
		 	             //pw.println("ANTI TALENTOS \n");
		 	             fos.write(("ANTI TALENTOS").getBytes());
		 	             for(i = 0; i < 5; i++) {
		 	            	//pw.println(Integer.toString(i+1) + "- " + vetorCSV[finalTalentos[i+29]][1]+ "\n");
		 	            	//pw.println(vetorCSV[finalTalentos[i+29]][2]);
		 	            	//pw.println("\n");
		 	            	fos.write((Integer.toString(i+1) + "- " + vetorCSV[finalTalentos[i+29]][1]+ "\n").getBytes());
		 	        		fos.write(vetorCSV[finalTalentos[i+29]][2].getBytes());
		 	            	fos.write(("\n").getBytes());
		 	             }
		 	             //pw.println("\n");
		 	             //pw.println("\n");
		 	             fos.write(("\n").getBytes());
		 	             fos.write(("\n").getBytes());
		 	            
		 	             //pw.close();
		 	             fos.close();
		 	             
		 	            pbarDialog.dismiss();
     	        } catch (IOException e) {
     	            e.printStackTrace();
     	        }
         	  }
           }.start();
          
	     	 String attach = Environment.getExternalStorageDirectory() + "/ProjetoTalentos/Relatorio.txt"; //This is where you need to use the absolute path!!
	          Intent it = new Intent(Intent.ACTION_SEND);
	          it.setType("message/rfc822");
	          it.putExtra(Intent.EXTRA_SUBJECT, "Resultado do Teste de Talentos");
	          it.putExtra(Intent.EXTRA_TEXT   , "Segue o Relatório.");
	          it.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + attach)); //This is where you attach the file 
	          try {
	              startActivity(Intent.createChooser(it, "Enviar e-mail..."));}
	          catch (android.content.ActivityNotFoundException ex) {
	              } 
    	
    	//finish();
    	
    	
    	
    	
    	
    	
    	
    	
    	/*
    	try {
	    	PDDocument documento = new PDDocument();
	    	PDPage pagina = new PDPage();
	    	PDFMergerUtility pdfFinal = new PDFMergerUtility();
	    	AssetManager am = getAssets();
	    	
	    	pdfFinal.addSource(am.open("0.pdf"));
	    	pdfFinal.addSource(am.open("1.pdf"));
	    	pdfFinal.addSource(am.open("2.pdf"));
	    	
	    	pdfFinal.setDestinationFileName(Environment.getExternalStorageDirectory()
	                 + "/ptb/relatorio.pdf");
	    	pdfFinal.mergeDocuments();
    	} catch (Exception e) {} */
    	
    	//documento.addPage(pagina);
    	
    	//documento.save(Environment.getExternalStorageDirectory() + "/ProjetoTalento/Relatório.pdf");
    	
    	//documento.close();
    	
    	
    //	int[] antiTalentos = new int[5];{finalTalentos[33],finalTalentos[32],finalTalentos[31], finalTalentos[30],finalTalentos[29]};
    	//int[] secTalentos = new int[5];{finalTalentos[5],finalTalentos[6],finalTalentos[7], finalTalentos[8],finalTalentos[8]};;
    	//int[] primTalentos = new int[5];{finalTalentos[0],finalTalentos[1],finalTalentos[2], finalTalentos[3],finalTalentos[4]};
    
    	
    	
    	
    	
    	//fazer lógica do primTalentos
    	
    	
    	 
    	
       
    	
    	/*
    	try {
    		fos = openFileOutput("relatorio50.txt",MODE_PRIVATE);
    		
    		//while(cont < resposta.length ) {
    			fos.write(("Besteira").getBytes());
    			Toast.makeText(getApplicationContext(), "Arquivo criado com sucesso!", Toast.LENGTH_SHORT).show();
    		//}
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
    	} */
    	
    	//enviarEmail("allyson@outlook.com.br");
    
    }
    
    /**
     * Envia e-mail com relat�rio
     * Para ser poss�vel o envio, � necess�rio adicionar android.permission.INTERNET no Manifest
     * 
     * @param
     * @return
     */
    public void enviarEmail(String destinatario) {
    	Intent itEmail = new Intent(Intent.ACTION_SEND);
    	itEmail.setType("plain/text");
    	itEmail.putExtra(Intent.EXTRA_SUBJECT, "T�tulo do E-mail");
    	itEmail.putExtra(Intent.EXTRA_TEXT, "Corpo do e-mail");
    	itEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {destinatario});
    	itEmail.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:/mnt/sdcard/relatorio10.txt"));
    	startActivity(Intent.createChooser(itEmail, "Escolha o aplicativo para envio do e-mail..."));
    	
    }
    
}

