package Entities;

public class  CribCreation {
	

		private String name;
		private String code;
		private String userEmail;
		private String token;
		
		public CribCreation(String name, String code, String userEmail, String token) {
			this.name = name;
			this.code = code;
			this.userEmail = userEmail;
			this.token = token;
		}
		
		public String getName() {
			return name;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getEmail() {
			return userEmail;
		}
		
		public String getToken() {
			return token;
		}
		
		public void setName(String name) {
			this.name = name;
		}
}
