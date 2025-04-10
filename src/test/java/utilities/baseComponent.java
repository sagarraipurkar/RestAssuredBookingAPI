package utilities;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class baseComponent {
	public static RequestSpecification request;
	public static ResponseSpecification responseSpecification;

	@BeforeTest
	public static RequestSpecification RequestSpecification() throws IOException {
		if (request == null) {
			PrintStream log = new PrintStream(new FileOutputStream("log.txt"));
			request = new RequestSpecBuilder().setBaseUri(baseComponent.GetEnvUrls("baseurl"))
					.addHeader("Content-Type", "application/json").addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return request;
		}
		return request;
	}
	
	 public ResponseSpecification setResponseSpecification() {
	        responseSpecification = new ResponseSpecBuilder()
	                                .expectStatusCode(200)
	                                .expectContentType("application/json").expectResponseTime(Matchers.lessThan(2000L))
	                                .build(); 
	        return responseSpecification;
	 }    
	 
	public static String GetEnvUrls(String url) throws IOException {
		Properties prop = new Properties();
		FileInputStream fs = new FileInputStream(
				"C:\\Eclipse New\\NewWorkSpace\\PracticeRestAssured\\src\\test\\java\\utilities\\global.properties");
		prop.load(fs);
		return prop.getProperty(url).toString();
	}

	public static String getToken() throws IOException {
		request = given().spec(baseComponent.RequestSpecification())
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}");
		Response response = request.post("auth");
		String token = response.asString();
		return token;

	}

}
