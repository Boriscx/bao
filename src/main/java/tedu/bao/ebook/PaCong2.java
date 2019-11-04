package tedu.bao.ebook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import tedu.bao.db.DB;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    //-----------------------------------get book start--------------------------------------------------------

    private ArrayList<Book> getBookList(Document document, Product p) {

        String author = document.getElementById("info").selectFirst("p").text();
        author = author.substring(author.indexOf("：") + 1);//作者

        ArrayList<Book> books = new ArrayList<>();

        Elements select = document.select("div.box_con div dd");
        for (Element e : select) {
            String href = "http://www.3hebao.co" + e.selectFirst("a").attr("href");
            String title = e.text();
            String skuId = href.substring(href.lastIndexOf('/') + 1, href.lastIndexOf(".html"));
            Book book = new Book(p.getId(), author, skuId, title, "", href);
            if (!books.contains(book)) {
                books.add(book);
            } else {
                //System.out.println(href + "已经存在");
            }
        }

        return books;
    }

    private void getTitleList(Product p) throws Exception {

        Document document = Jsoup.connect(p.getLink()).get();
        ArrayList<Book> books = getBookList(document, p);
        System.out.println("getTitleList books:" + books.size());
        if (p.getCount() == 0)
            updateProduct(p.getId(), books.size());
        if (books.size() < BOOK_SIZE) {
            if (p.getHaveCount() > 0) {
                singleSaveBook(books, p.getId());
            } else {
                moreSaveBook(books);
            }
        }
    }

    private String getContent(String link) {
        String content = "";
        try {
            Document document = Jsoup.connect(link).get();
            printTime();
            Element element = document.getElementById("content");
            // ['#\]+
            String regex = "[/\\\\_'#)(]";
            regex = "[^\\w\\u4e00-\\u9fa5,.?!\"“”:：]+";
            content = element.text().replaceAll("<[\\s\\S]*?>", "").replaceAll(regex, "");
        } catch (Exception e) {
            System.out.println("getContent error link:" + link);
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
        return content;
    }

    private ArrayList<String> getAlreadyHave(int link) {
        ArrayList<String> list = new ArrayList<>();
        String sql = "select * from book where linkId=" + link;
        try {
            ResultSet resultSet = db.getResultSet(sql);
            while (resultSet.next()) {
                String skuId = resultSet.getString("skuId");
                if (!list.contains(skuId))
                    list.add(skuId);
            }
        } catch (Exception e) {
            System.out.println("getAlreadyHave link:" + link);
            e.printStackTrace();
        }
        return list;
    }

    private void singleSaveBook(ArrayList<Book> books, int linkId) {
        ArrayList<String> skuIdList = getAlreadyHave(linkId);
        for (Book b : books) {
            if (!skuIdList.contains(b.getSkuId())) {
                b.setContent(getContent(b.getLink()));
                if (!"".equals(b.getContent()))
                    try {
                        db.execute(b.getInsertSQL());
                    } catch (Exception e) {
                        System.out.println("context is too long :" + b.getContent().length() + " link:" + b.getLink());
                        System.out.println(e.getMessage());
                        System.out.println(b.getInsertSQL());
                    }
                else {
                    System.out.println("saveBook: content is null , not save ");
                }
            } else {
                //System.out.println(b.getTitle() + " 已经存在!");
            }
        }
    }

    private void moreSaveBook(ArrayList<Book> books) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into book(linkId,author,skuId,title,content,link) values");
        int sbLength = sb.length();
        for (int i = 0; i < books.size(); i++) {
            Book b = books.get(i);
            b.setContent(getContent(b.getLink()));
            if (!"".equals(b.getContent())) {
                sb.append(b.getSingleValue());
                sb.append(",");
            }
            if ((i % 2 == 0 || i == books.size() - 1) && sb.length() > sbLength) {
                sb.delete(sb.lastIndexOf(","), sb.length());
                try {
                    db.execute(sb.toString());
                } catch (Exception e) {
                    System.out.println(sb.toString());
                }
                sb = new StringBuilder("insert into book(linkId,author,skuId,title,content,link) values");

            }
        }
    }

    private void getAllItemLinkFromDB() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT P.*, COUNT(B.LINKID) HAVECOUNT " +
                "FROM PRODUCT P LEFT JOIN BOOK B ON P.ID = B.LINKID " +
                "GROUP BY P.ID HAVING ( P.COUNT > COUNT(B.LINKID) OR HAVECOUNT = 0) AND P.COUNT < "+BOOK_SIZE+" " +
                "ORDER BY COUNT(B.LINKID) DESC " +
                "LIMIT " + PRO_COUNT_START + "," + PRO_COUNT_END;
        System.out.println(sql);
        ResultSet re = db.getResultSet(sql);
        try {
            while (re.next()) {
                int linkId = re.getInt("ID");
                String title = re.getString("TITLE");
                String url = re.getString("LINK");
                int count = re.getInt("COUNT");
                int haveCount = re.getInt("HAVECOUNT");
                Product p = new Product(linkId, title, url);
                p.setCount(count);
                p.setHaveCount(haveCount);
                products.add(p);
            }
            re.close();
        } catch (Exception e) {
            System.out.println("getAllItemLinkFromDB first");
            e.printStackTrace();
        }
        if (products.size() > 0) {
            for (Product product : products) {
                System.out.println("--------------------------------------------");
                System.out.println(product.getLink() + "<title>" + product.getTitle());
                try {
                    if (product.getCount() < BOOK_SIZE)
                        getTitleList(product);
                } catch (Exception e) {
                    System.out.println("getAllItemLinkFromDB product:" + product.getLink());
                    e.printStackTrace();
                }
            }
        }
    }

    private void printTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        System.out.println(sdf.format(new Date()));
    }

    private void updateProduct(int id, int count) {
        if (count > 0) {
            String sql = "update product set count=" + count + " where id= " + id;
            try {
                db.execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static final int PRO_COUNT_START = 0;

    public static final int PRO_COUNT_END = 500;

    private static final int BOOK_SIZE = 200;

    public static void main(String[] args) throws Exception {
        PaCong2 pc = new PaCong2();
        pc.getAllItemLinkFromDB();
    }
}


