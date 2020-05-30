package Entities;

public class Crib{
	
	//private int id;
	private String name;
	private boolean isInCrib;
	private int babyMovement;
	private int cribRocking;
	private int babySound;
	private int roomTemperature;
	private int toyMovement;
	
	public Crib() {
		super();
	}
	
	public Crib(String name, int babyMovement, int cribRocking, int babySound, int roomTemperature, int toyMovement, boolean isInCrib) {
		//this.id =id;
		this.name = name;
		this.isInCrib =isInCrib;
		this.babyMovement = babyMovement;
		this.cribRocking = cribRocking;
		this.babySound = babySound;
		this.roomTemperature =roomTemperature;
		this.toyMovement = toyMovement;
	}
	
	/**public int getId() {
		return id;
	}**/

	public int getBabyMovement() {
		return this.babyMovement;
	}
	
	public boolean isInCrib() {
		return this.isInCrib;
	}
	
	public int getCribRocking() {
		return this.cribRocking;
	}
	
	public int getBabySound() {
		return this.babyMovement;
	}
	
	public int getRoomTemperature() {
		return this.roomTemperature;
	}
	
	public int getToyMovement() {
		return this.toyMovement;
	}
	
	public String getName() {
		return this.name;
	}
	
	/**public void setId(int id) {
		this.id = id;
	}**/

	public void setName(String name) {
		this.name = name;
	}
	
	public void setIsInCrib(boolean isInCrib) {
		this.isInCrib = isInCrib;
	}
	
	public void setBabyMovement(int babyMovement) {
		this.babyMovement = babyMovement;
	}
	
	public void setCribRocking(int cribRocking) {
		this.cribRocking = cribRocking;
	}
	
	public void setBabySound(int babySound) {
		this.babySound = babySound;
	}
	
	public void setRoomTemperature(int roomTemperature) {
		this.roomTemperature = roomTemperature;
	}
	
	public void setToyMovement(int toyMovement) {
		this.toyMovement = toyMovement;
	}
}
