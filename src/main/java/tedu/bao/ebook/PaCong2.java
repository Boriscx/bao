package tedu.bao.ebook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import tedu.bao.db.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaCong2 {

    private ArrayList<String> getFLList() throws Exception {
        ArrayList<String> list = new ArrayList<>();
        String host = "http://www.3hebao.co/xiaoshuo_";
        for (int i = 1; i <= 7; i++) {
            String url = host + i + "/";
            //list.add(url);
            //System.out.println(url);
            getPageLinkList(url);
        }
        return list;
    }

    private int getMaxPages(String url) throws Exception {
        int maxPages = 0;

        Document document = Jsoup.connect(url).get();
        Element element = document.selectFirst("div.page_b a.last");
        maxPages = Integer.parseInt(element.text());

        return maxPages;
    }

    private ArrayList<String> getPageLinkList(String url) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        int maxPages = getMaxPages(url);
        String s = url.substring(url.lastIndexOf('_') + 1, url.lastIndexOf('/'));
        for (int i = 1; i < maxPages; i++) {
            String link = url + "/" + s + "_" + i + ".html";
            //list.add(link);
            getItemLinkList(link);
        }
        return list;
    }

    private ArrayList<String> getItemLinkList(String url) {
        ArrayList<String> list = new ArrayList<>();
        try {
            Elements select = Jsoup.connect(url).get().select("div.l ul li");
            for (Element element : select) {
                Element e = element.selectFirst("span.s2 a");
                String title = e.text();
                String link = e.attr("href");
                //System.out.println(link + "\t" + title);
                outputDb(title, link);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private DB db = new DB();

    private void outputDb(String title, String link) throws Exception {

        System.out.println("------------------------------------------");
        String sql = "insert ignore into product(title,link) values('" + title + "','" + link + "')";
        System.out.println(sql);
        if (!isHave(link)) {
            if (db.execute(sql)) {
                System.out.println(title + " 存入成功!");
            } else {
                System.out.println(title + " 存入失败了");
            }
        } else {
            System.out.println(title + "已经存在!");
        }
    }

    private boolean isHave(String link) {
        boolean flag = false;
        String sql = "select * from product where link = '" + link + "'";
        ResultSet re = new DB().getResultSet(sql);
        if (re == null)
            return false;
        try {
            flag = re.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    private void getTitleList(int linkId, String url1) throws Exception {

        String url = "http://www.3hebao.co/9_9443/";
        Document document = Jsoup.connect(url).get();
        String author = document.getElementById("info").selectFirst("p").text();
        author = author.substring(author.indexOf("：") + 1);//作者
        ArrayList<Book> books = new ArrayList<>();
        Elements select = document.select("div.box_con div dd");
        for (Element e : select) {
            String href = "http://www.3hebao.co" + e.selectFirst("a").attr("href");
            String title = e.text();
            String skuId = href.substring(href.lastIndexOf('/') + 1, href.lastIndexOf(".html"));
            Book book = new Book(linkId, author, skuId, title, "", href);
            if (!books.contains(book)) {
                books.add(book);
            } else
                System.out.println(href + "已经存在");
        }
        saveProduct(books, linkId);
    }

    private void saveProduct(ArrayList<Book> books, int linkId) {
        long row = getTitleCount(linkId);
        if (row > 0) {
            if (row == books.size())
                return;
            for (Book b : books) {
                getContent(b);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("insert into book(linkId,author,skuId,title,content,link) values");
            for (int i = 0; i < books.size(); i++) {
                Book b = books.get(i);
                b.setContent(getContent(b.getLink()));
                sb.append(b.getSingleValue());
                sb.append(",");
                if (i % 20 == 0 || i == books.size() - 1) {
                    sb.delete(sb.lastIndexOf(","), sb.length());
                    db.execute(sb.toString());
                    sb = new StringBuilder("insert into book(linkId,author,skuId,title,content,link) values");
                }
            }
        }
    }

    private String getContent(String link) {
        String content = "";
        try {
            Document document = Jsoup.connect(link).get();
            Element element = document.getElementById("content");
            content = element.text().replaceAll("['#]+", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private void getContent(Book book) {
        if (!isBookHave(book.getSkuId()))
            try {
                Document document = Jsoup.connect(book.getLink()).get();
                Element element = document.getElementById("content");
                // [.'#]
                String content = element.text().replaceAll("[.'#]", "");
                book.setContent(content);
                db.execute(book.getInsertSQL());
            } catch (Exception e) {
                e.printStackTrace();
            }
        else {
            System.out.println(book.getTitle() + " 已经存在!");
        }
    }

    public long getTitleCount(int linkId) {
        String skuId = "";
        String sql = "select count(*) from book where linkId = '" + linkId + "'";
        long row = 0;
        ResultSet resultSet = db.getResultSet(sql);
        try {
            if (resultSet.next()) {
                row = (long) resultSet.getObject(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    private boolean isBookHave(String skuId) {
        String sql = "select * from book where skuId='" + skuId + "'";
        try {
            ResultSet resultSet = db.getResultSet(sql);
            boolean flag = resultSet != null && resultSet.next();
            assert resultSet != null;
            return flag;
        } catch (Exception e) {
            return false;
        }
    }

    private void getAllItemLinkFromDB() {
        ArrayList<Product> products = new ArrayList<>();
        ResultSet re = db.getResultSet("select * from product LIMIT 0,300");
        try {
            while (re.next()) {
                int linkId = re.getInt("id");
                String title = re.getString("title");
                String url = re.getString("link");
                products.add(new Product(linkId, title, url));
                //getTitleList(linkId, url);
            }
            re.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (products.size() > 0) {
            for (Product product : products) {
                try {
                    getTitleList(product.getId(), product.getLink());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void test() throws Exception {
        String url = "http://www.3hebao.co/18_18901/";
        Document document = Jsoup.connect(url).get();
        System.out.println(document);
    }

    public static void main(String[] args) throws Exception {
        PaCong2 pc = new PaCong2();
        pc.getAllItemLinkFromDB();
    }
}
