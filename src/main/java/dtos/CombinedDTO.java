package dtos;

public class CombinedDTO {

    private String breed;
    private String image;
    private String facts;
    private String info;
    private String wikipedia;

    public CombinedDTO(String breed, String info, String wikipedia, String image, String facts) {
        this.breed = breed;
        this.wikipedia = wikipedia;
        this.image = image;
        this.facts = facts;
        this.info = info;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

}
