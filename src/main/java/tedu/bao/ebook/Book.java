package tedu.bao.ebook;

import java.util.Objects;

public class Book {
    private int id;//id
    private int linkId;// 链接id
    private String author;// 作者
    private String skuId;// 章节id
    private String title;// 章节标题
    private String content; // 章节内容
    private String link;// 章节连接地址

    public Book(int linkId, String author, String skuId, String title, String content, String link) {
        this.linkId = linkId;
        this.author = author;
        this.skuId = skuId;
        this.title = title;
        this.content = content;
        this.link = link;
    }

    public Book(int id, int linkId, String author, String skuId, String title, String content, String link) {
        this.id = id;
        this.linkId = linkId;
        this.author = author;
        this.skuId = skuId;
        this.title = title;
        this.content = content;
        this.link = link;
    }

    public int getLinkId() {
        return linkId;
    }

    public Book setLinkId(int linkId) {
        this.linkId = linkId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Book setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getSkuId() {
        return skuId;
    }

    public Book setSkuId(String skuId) {
        this.skuId = skuId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Book setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Book setContent(String content) {
        this.content = content;
        return this;
    }

    public String getLink() {
        return link;
    }

    public Book setLink(String link) {
        this.link = link;
        return this;
    }

    public String getInsertSQL() {
        return "insert into book(linkId,author,skuId,title,content,link) values(" +
                linkId + ",'" +
                author + "','" +
                skuId + "','" +
                title + "','" +
                content + "','" +
                link +
                "')";
    }

    public String getSingleValue() {
        return "(" +
                linkId + ",'" +
                author + "','" +
                skuId + "','" +
                title + "','" +
                content + "','" +
                link +
                "')";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                linkId == book.linkId &&
                Objects.equals(author, book.author) &&
                Objects.equals(skuId, book.skuId) &&
                Objects.equals(title, book.title) &&
                Objects.equals(content, book.content) &&
                Objects.equals(link, book.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, linkId, author, skuId, title, content, link);
    }
}
