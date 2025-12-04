package com.carpoolingapp.models;

public class Receipt {
    private String receiptId;
    private String bookingId;
    private String rideId;
    private String riderId;
    private String riderName;
    private String riderEmail;
    private String driverId;
    private String driverName;
    private String driverEmail;
    private String fromLocation;
    private String toLocation;
    private String date;
    private String time;
    private int seatsBooked;
    private double pricePerSeat;
    private double totalAmount;
    private String paymentMethod;
    private long timestamp;
    private String status; // "completed", "refunded"

    public Receipt() {
        // Required empty constructor for Firebase
    }

    public Receipt(String bookingId, String rideId, String riderId, String riderName, String riderEmail,
                   String driverId, String driverName, String driverEmail,
                   String fromLocation, String toLocation, String date, String time,
                   int seatsBooked, double pricePerSeat, double totalAmount, String paymentMethod) {
        this.bookingId = bookingId;
        this.rideId = rideId;
        this.riderId = riderId;
        this.riderName = riderName;
        this.riderEmail = riderEmail;
        this.driverId = driverId;
        this.driverName = driverName;
        this.driverEmail = driverEmail;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.date = date;
        this.time = time;
        this.seatsBooked = seatsBooked;
        this.pricePerSeat = pricePerSeat;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.timestamp = System.currentTimeMillis();
        this.status = "completed";
    }

    // Getters and Setters
    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName;
    }

    public String getRiderEmail() {
        return riderEmail;
    }

    public void setRiderEmail(String riderEmail) {
        this.riderEmail = riderEmail;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}