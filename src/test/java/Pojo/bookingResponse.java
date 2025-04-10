package Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bookingResponse {

	@SerializedName("bookingid")
	@Expose
	private Integer bookingid;
	@SerializedName("booking")
	@Expose
	private Bookingdates booking;

	public Integer getBookingid() {
		return bookingid;
	}

	public void setBookingid(Integer bookingid) {
		this.bookingid = bookingid;
	}

	public Bookingdates getBooking() {
		return booking;
	}

	public void setBooking(Bookingdates booking) {
		this.booking = booking;
	}

}