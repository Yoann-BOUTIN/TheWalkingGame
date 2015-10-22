package database;

public class Reanimer {
	private Integer id;
	private String joueur;
	private Integer score;
	private Integer scoreTarget;
	private Integer tempsAutorise;
	private Integer prototype;
	
	
	public User(){}
	
	public User(String joueur, Integer score) {
		this.joueur = joueur;
		this.score = score;
	}
	public User(String joueur, Integer score,Integer scoreTarget, Integer tempsAutorise, Integer prototype) {
		this.joueur = joueur;
		this.score = score;
		this.scoreTarget = scoreTarget;
		this.tempsAutorise = tempsAutorise;
		this.prototype = prototype;
	}
	public Integer getid() {
		return id;
	}
	public void setid(Integer id) {
		this.id = id;
	}
	public String getjoueur() {
		return joueur;
	}
	public void setjoueur(String joueur) {
		this.joueur = joueur;
	}
	public Integer getscore() {
		return score;
	}
	public void setscore(Integer score) {
		this.score = score;
	}
	public Integer getscoreTarget() {
		return scoreTarget;
	}
	public void setscoreTarget(Integer scoreTarget) {
		this.scoreTarget = scoreTarget;
	}
	public Integer getTempsAutorise() {
		return tempsAutorise;
	}
	public void setTempsAutorise(Integer tempsAutorise) {
		this.tempsAutorise = tempsAutorise;
	}
	public Integer getPrototype(){
		return prototype;
	}
	public void setPrototype(Integer prototype){
		this.prototype = prototype;
	}
}
