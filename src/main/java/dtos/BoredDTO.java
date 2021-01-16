package dtos;

public class BoredDTO {
    
    private String activity, type, price;

    public BoredDTO(String activity, String type, String price) {
        this.activity = activity;
        this.type = type;
        this.price = price;
    }

    public BoredDTO() {
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    
    
}
