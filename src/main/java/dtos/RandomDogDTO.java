package dtos;


public class RandomDogDTO {
    private String message;

    public RandomDogDTO(String message) {
        this.message = message;
    }

    public RandomDogDTO() {
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
