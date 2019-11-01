package tedu.bao.ebook;

public class Product {
    private int id;
    private String title;
    private String link;

    public Product(int id, String title, String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Product setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Product setLink(String link) {
        this.link = link;
        return this;
    }
}
