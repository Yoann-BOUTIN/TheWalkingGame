package database;

public class User {
	private Integer userId;
	private String userPseudo;
	private Integer userScore;
	private double userPositionLongitude;
	private double userPositionLatitude;
	
	
	public User(){}
	
	public User(String userPseudo, Integer userScore) {
		this.userPseudo = userPseudo;
		this.userScore = userScore;
	}
	public User(String userPseudo, Integer userScore,double userPosLongitude, double userPosLatitude) {
		this.userPseudo = userPseudo;
		this.userScore = userScore;
		this.userPositionLongitude = userPosLongitude;
		this.userPositionLatitude = userPosLatitude;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserPseudo() {
		return userPseudo;
	}
	public void setUserPseudo(String userPseudo) {
		this.userPseudo = userPseudo;
	}
	public Integer getUserScore() {
		return userScore;
	}
	public void setUserScore(Integer userScore) {
		this.userScore = userScore;
	}
	public double getUserPositionLongitude() {
		return userPositionLongitude;
	}
	public void setUserPositionLongitude(double userPositionLongitude) {
		this.userPositionLongitude = userPositionLongitude;
	}
	public double getUserPositionLatitude() {
		return userPositionLatitude;
	}
	public void setUserPositionLatitude(double userPositionLatitude) {
		this.userPositionLatitude = userPositionLatitude;
	}
	
}
