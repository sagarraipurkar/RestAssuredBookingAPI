package Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bookingRequest {

@SerializedName("firstname")
@Expose
private static String firstname;
@SerializedName("lastname")
@Expose
private static String lastname;
@SerializedName("totalprice")
@Expose
private Integer totalprice;
@SerializedName("depositpaid")
@Expose
private Boolean depositpaid;
@SerializedName("bookingdates")
@Expose
private Bookingdates bookingdates;
@SerializedName("additionalneeds")
@Expose
private String additionalneeds;

public static String getFirstname() {
return firstname;
}

public void setFirstname(String firstname) {
this.firstname = firstname;
}

public static String getLastname() {
return lastname;
}

public void setLastname(String lastname) {
this.lastname = lastname;
}

public Integer getTotalprice() {
return totalprice;
}

public void setTotalprice(Integer string) {
this.totalprice = string;
}

public Boolean getDepositpaid() {
return depositpaid;
}

public void setDepositpaid(Boolean depositpaid) {
this.depositpaid = depositpaid;
}

public Bookingdates getBookingdates() {
return bookingdates;
}

public void setBookingdates(Bookingdates bookingdates) {
this.bookingdates = bookingdates;
}

public String getAdditionalneeds() {
return additionalneeds;
}

public void setAdditionalneeds(String additionalneeds) {
this.additionalneeds = additionalneeds;
}

}