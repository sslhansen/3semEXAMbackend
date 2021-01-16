package dtos;


public class QuoteDTO {
    private String en, author;

    public QuoteDTO(String en, String author) {
        this.en = en;
        this.author = author;
    }

    public QuoteDTO() {
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
    
}
