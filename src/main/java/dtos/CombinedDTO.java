package dtos;


public class CombinedDTO {
    
    private String activity, type, price, foodImage, programmerQuote, author, kanyeSays, dogPicture;

    public CombinedDTO(BoredDTO boredDTO, KanyeDTO kanyeDTO, RandomDogDTO randomDogDTO) {
        this.activity = boredDTO.getActivity();
        this.type = boredDTO.getType();
        this.price = boredDTO.getPrice();

        this.kanyeSays = kanyeDTO.getQuote();

        this.dogPicture = randomDogDTO.getMessage();
        
    }

    public CombinedDTO() {
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

    public String getImage() {
        return foodImage;
    }

    public void setImage(String image) {
        this.foodImage = image;
    }

    public String getEn() {
        return programmerQuote;
    }

    public void setEn(String en) {
        this.programmerQuote = en;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getQuote() {
        return kanyeSays;
    }

    public void setQuote(String quote) {
        this.kanyeSays = quote;
    }

    public String getMessage() {
        return dogPicture;
    }

    public void setMessage(String message) {
        this.dogPicture = message;
    }
    
    
    
    
    
}
