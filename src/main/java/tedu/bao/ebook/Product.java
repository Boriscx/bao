package tedu.bao.ebook;

import java.util.Objects;

public class Product {
    private int id;
    private String title;
    private String link;

    private int count;
    private int haveCount;

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

    public int getCount() {
        return count;
    }

    public Product setCount(int count) {
        this.count = count;
        return this;
    }

    public int getHaveCount() {
        return haveCount;
    }

    public Product setHaveCount(int haveCount) {
        this.haveCount = haveCount;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(title, product.title) &&
                Objects.equals(link, product.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link);
    }
}
