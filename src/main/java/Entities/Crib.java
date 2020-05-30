package Entities;

public class Crib{
	
	private String code;
	private String name;
	private boolean isInCrib;
	private float babyMovement;
	private float lastBabyMovement;
	private float cribRocking;
	private float babySound;
	private float lastBabySound;
	private float roomTemperature;
	private float lastTemperatureChange;
	private float toyMovement;
	private float babyInCribVoltage;
	
	public Crib() {
		super();
	}
	
	public Crib(String code,String name, float babyMovement, float cribRocking, float babySound, float roomTemperature, float toyMovement, boolean isInCrib) {
		this.code =code;
		this.name = name;
		this.isInCrib =isInCrib;
		this.babyMovement = babyMovement;
		this.cribRocking = cribRocking;
		this.babySound = babySound;
		this.roomTemperature =roomTemperature;
		this.toyMovement = toyMovement;
	}
	
	
	public float getBabyInCribVoltage() {
		return this.babyInCribVoltage;
	}
	
	public void setBabyInCribVoltage(float babyInCribVoltage) {
		this.babyInCribVoltage = babyInCribVoltage;
	}
	
	public String getCode() {
		return code;
	}

	public float getBabyMovement() {
		return this.babyMovement;
	}
	
	public boolean getIsInCrib() {
		return this.isInCrib;
	}
	
	public float getCribRocking() {
		return this.cribRocking;
	}
	
	public float getBabySound() {
		return this.babySound;
	}
	
	public float getRoomTemperature() {
		return this.roomTemperature;
	}
	
	public float getToyMovement() {
		return this.toyMovement;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public float getLastTemperatureChange() {
		return this.lastTemperatureChange;
	}
	
	public void setLastTemperatureChange(float lastTemperatureChange) {
		this.lastTemperatureChange = lastTemperatureChange;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setIsInCrib(boolean isInCrib) {
		this.isInCrib = isInCrib;
	}
	
	public void setBabyMovement(float babyMovement) {
		this.babyMovement = babyMovement;
	}
	
	public void setCribRocking(float cribRocking) {
		this.cribRocking = cribRocking;
	}
	
	public void setBabySound(float babySound) {
		this.babySound = babySound;
	}
	
	public void setRoomTemperature(float roomTemperature) {
		this.roomTemperature = roomTemperature;
	}
	
	public void setToyMovement(float toyMovement) {
		this.toyMovement = toyMovement;
	}
	
	public void setLastBabyMovement(float lastBabyMovement) {
		this.lastBabyMovement = lastBabyMovement;
	}
	
	public float getLastBabyMovement() {
		return this.lastBabyMovement;
	}
	
	public void setLastBabySound(float lastBabySound) {
		this.lastBabySound = lastBabySound;
	}
	
	public float getLastBabySound() {
		return this.lastBabySound;
	}
}
