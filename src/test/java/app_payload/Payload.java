package app_payload;

import com.google.gson.Gson;

import Pojo.Bookingdates;
import Pojo.bookingRequest;
import Pojo.bookingResponse;

public class Payload {

	public String AddBookingPayload() {
		
		bookingRequest bookingrequest = new bookingRequest();
		bookingrequest.setFirstname("James");
		bookingrequest.setLastname("David");
		bookingrequest.setTotalprice(3000);
		bookingrequest.setDepositpaid(true);
		bookingrequest.setAdditionalneeds("Breakfast");
		
		Bookingdates dates = new Bookingdates();
		dates.setCheckin("2025-02-23");
		dates.setCheckout("2025-02-25");
		bookingrequest.setBookingdates(dates);
		
		Gson gson = new Gson();
		String bookingRequest = gson.toJson(bookingrequest);
		return bookingRequest;
	}
	
	public static bookingResponse bookingResponse(String stringresponse) {
		Gson gson = new Gson();
		bookingResponse bookingresponse = gson.fromJson(stringresponse, bookingResponse.class);
		return bookingresponse;
	}
	public static bookingRequest bookingRequest(String stringresponse) {
		Gson gson = new Gson();
		bookingRequest bookingRequest = gson.fromJson(stringresponse, bookingRequest.class);
		return bookingRequest;
	}
}
