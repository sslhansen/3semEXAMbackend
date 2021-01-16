package dtos;


public class FoodDTO {
    private String image;
    

    public FoodDTO(String name) {
        this.image = name;
    
    }

    public FoodDTO() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String name) {
        this.image = name;
    }

   
    
    
    
}
