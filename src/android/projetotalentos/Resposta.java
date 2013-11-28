package android.projetotalentos;

public class Resposta {
		
	int idTalento;
	int idAfirmacao;
	float nota;
	float tempo;
		
	/**
	 * Construtor da classe;
	 */
	public Resposta(int talento, int afirmacao, float nota, float tempo) {
		this.idTalento = talento;
		this.idAfirmacao = afirmacao;
		this.nota = nota;
		this.tempo = tempo;
	}
	
	/**
	 * Set e Get idTalento
	 */
	public int getIDTalento() {
		return this.idTalento;
	}
	
	public void setIDTalento(int talento) {
		this.idTalento = talento;
	}
	
	/**
	 * Set e Get idAfirmacao
	 */
	public int getIDAfirmacao() {
		return this.idAfirmacao;
	}
	
	public void setIDAfirmacao(int afirmacao) {
		this.idAfirmacao = afirmacao;
	}
	
	/**
	 * Set e Get nota
	 */
	public float getNota() {
		return this.nota;
	}
	
	public void setNota(float nota) {
		this.nota = nota;
	}
	
	/**
	 * Set e Get tempo
	 */
	public float getTempo() {
		return this.tempo;
	}
	
	public void setTempo(float tempo) {
		this.tempo = tempo;
	}
		
}
