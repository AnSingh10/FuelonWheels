package fire.auth.com.fuelonwheels;

public class Orders {

    String address, capacity, dnt, fuel_type, latitude, logitude, uid, price;

    public Orders(){}

    public Orders(String address, String capacity, String dnt, String fuel_type, String latitude, String logitude, String uid, String price) {
        this.address = address;
        this.capacity = capacity;
        this.dnt = dnt;
        this.fuel_type = fuel_type;
        this.latitude = latitude;
        this.logitude = logitude;
        this.uid = uid;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDnt() {
        return dnt;
    }

    public void setDnt(String dnt) {
        this.dnt = dnt;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
