package app_payload;

public class AdminCredentialsPayload {
	
	public static String credentialsPayload() {
		String payload = "{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}";
		return payload;
	}

}
