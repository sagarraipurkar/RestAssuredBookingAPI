package api_Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import Pojo.bookingRequest;
import Pojo.bookingResponse;
import app_endpoints.EndpointUrls;
import app_payload.Payload;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utilities.baseComponent;

public class CreateBooking {
	public static RequestSpecification request;
	public static Response response;
	public static ValidatableResponse validationreponse;
	Payload payload = new Payload();

	@Test(priority = 1)
	public void createBooking(ITestContext ITestContext) throws IOException {
		request = given().spec(baseComponent.RequestSpecification()).body(payload.AddBookingPayload());
		response = request.when().post(EndpointUrls.createBooking);
		validationreponse = response.then().statusCode(200).assertThat()
				.body(matchesJsonSchemaInClasspath("bookingResponseSchema.json"));
		validationreponse.spec(baseComponent.responseSpecification);

		String ParseReponse = response.asString();
		JsonPath json = new JsonPath(ParseReponse);
		String FirstName = json.get("booking.firstname");
		String LastName = json.get("booking.lastname");
		int bookingID = json.get("bookingid");
		String checkinDate = json.get("booking.checkin");
		String checkoutDate = json.get("booking.checkout");
		int totalPrice = json.get("booking.totalprice");

		assertThat(bookingID).isNotNull();
		assertThat(FirstName).isNotEmpty().isNotNull();
		assertThat(LastName).isNotEmpty().isNotNull();
		assertThat(checkinDate).isNotEmpty().isNotNull();
		assertThat(checkoutDate).isNotEmpty().isNotNull();
		assertThat(totalPrice).isGreaterThan(0);
		assertThat(checkinDate).isGreaterThanOrEqualTo(checkoutDate);
		bookingResponse bookingResponse = Payload.bookingResponse(ParseReponse);
		assertThat(bookingResponse.getBookingid()).isNotNull();
		assertThat(bookingRequest.getFirstname()).isEqualTo(FirstName);
//		assertThat(bookingRequest.getLastname()).isEqualTo(LastName);

		String responseHeader = response.getHeader("Content-Type");
		assertThat(responseHeader).contains("application/json");

		ITestContext.setAttribute("bookingid", bookingID);

		Long getResponseTime = response.getTime();
		Assert.assertTrue(getResponseTime < 2000, "Response time is not with in the range");

	}

	@Test(priority = 2)
	public void getBookingDetails(ITestContext ITestContext) throws IOException {
		Integer bookID = (Integer) ITestContext.getAttribute("bookingid");
		request = given().spec(baseComponent.RequestSpecification());
		response = request.when().get("booking/" + bookID);
		String ParseReponse = response.asString();
		JsonPath json = new JsonPath(ParseReponse);
		String FirstName = json.get("firstname");
		String LastName = json.get("lastname");
		Integer totalPrice = json.get("totalprice");
		String checkinDate = json.get("checkin");
		String checkoutDate = json.get("checkout");
		Integer bookingID = json.get("bookingid");

		response.then().statusCode(200);
		assertThat(bookingID).isEqualTo(bookID);
		Assert.assertEquals(FirstName, "James");
		Assert.assertNotNull(LastName);
		Assert.assertTrue(totalPrice > 0);
		Assert.assertNotNull(checkinDate);
		Assert.assertNotNull(checkoutDate);
		Assert.assertTrue(checkinDate.compareTo(checkoutDate) < 0, "Check-in date should be before checkout date");
		Assert.assertTrue(json.get("additionalInfo") == null || ((String) json.get("additionalInfo")).isEmpty(),
				"Additional info should be null or empty");
		// Optional: Validate response headers
		String responseContentType = response.getHeader("Content-Type");
		Assert.assertTrue(responseContentType.contains("application/json"),
				"Response should be of type application/json");

		long responseTime = response.getTime();
		Assert.assertTrue(responseTime < 2000, "Response time should be less than 2 seconds");
		response.then().assertThat().body(matchesJsonSchemaInClasspath("bookingResponseSchema.json"));
	}

	@Test(priority = 3)
	public void DeleteBooking(ITestContext ITestContext) throws IOException {
		Integer bookID = (Integer) ITestContext.getAttribute("bookingid");
		String token = baseComponent.getToken();
		ITestContext.setAttribute("token", token);
		request = given().spec(baseComponent.RequestSpecification()).cookie("token", token);
		response = request.when().delete("booking/" + bookID);
		validationreponse = response.then().statusCode(201);
	}
}
