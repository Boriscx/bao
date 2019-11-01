package tedu.bao.day17;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.*;
import java.util.LinkedList;

public class PaCong {

    private StringBuilder sbContent = new StringBuilder();
    private static boolean flag = true;

    public static void main(String[] args) {
        PaCong pc = new PaCong();
        pc.getList();
        File testFile = new File("g:/ebook/test2/test.txt");
        if (flag)
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testFile)));
                out.write(pc.sbContent.toString());
                out.flush();
                out.close();
            } catch (Exception e) {

            }

    }


    public void testGetTitle() {
        getTitle("3348241803");
    }

    private void getTitle(String id) {
        String title = "";
        String host = "https://lscg2.piao.qunar.com/shop/detail?productId=" + id;//3348241803;
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.70 Safari/537.36";
        try {
            Connection connect = Jsoup.connect(host).userAgent(userAgent);
            Document document = connect.get();
            Element element = document.selectFirst("h3.shop-item-name");
            //System.out.println(document);
            System.out.println(element.text());
            //.get().selectFirst("").text();
            System.out.println(title);

        } catch (Exception e) {
            System.out.println(id + "发生了异常：" + e.getMessage());
            flag = false;
        }
    }

    public String getContext(String host) {
//        String host = "http://www.3hebao.co/7_7054/3042526.html";
        String text = "";
        try {
            Document document = Jsoup.connect(host).get();
            Elements title = document.select("title");
            System.out.println(title.text());
            text = document.getElementById("content").text();
            //System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text;
    }
    @Test
    public void getList() {
        String host = "http://www.3hebao.co/1_1257/";
        LinkedList<String> linkedList = new LinkedList<>();
        try {
            Document document = Jsoup.connect(host).get();
            Element div_title = document.selectFirst("div title");
            System.out.println(div_title);
            Elements divs = document.select("div");
            for (Element element : divs) {
                String link = element.attr("id");
                if ("list".equals(link)) {
                    Elements dds = element.select("dd");

                    for (Element e : dds) {
                        Element e1 = e.selectFirst("a");

                        String l = "http://www.3hebao.co" + e1.attr("href") + "<title>" + e1.text();
                        if (!linkedList.contains(l))
                            linkedList.add(l);
                        System.out.println(l);
                    }
                    // System.out.println();
                }
            }

        } catch (Exception e) {
            flag = false;
        }
//        for (String s : linkedList) {
//            int index = s.lastIndexOf("<title>");
//            String title = s.substring(index + 7);
//            s = s.substring(0, index);
//            System.out.println(title);
//            System.out.println(s);
//            //handleFile(title, s);
//        }
    }

    private void handleFile(String title, String url) {
        File file = new File("G:/Ebook/test12/" + title + ".txt");
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            try {
                file.createNewFile();
                String content = getContext(url);
                if (content != null && !"".equals(content)) {
                    sbContent.append(title);
                    sbContent.append(content);
                    StringBuilder sb = new StringBuilder();
                    sb.append(title);
                    sb.append(content);

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                    bufferedWriter.write(sb.toString());
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    bufferedWriter.close();
                    System.out.println(title + "创建成功！");

                }
            } catch (IOException e) {
                System.out.println("打开输出流失败：" + e.getMessage());
                flag = false;
            }
        } else {
            System.out.println(title + "已存在！");
        }
    }

    @Test
    public void test() {
        String host = "http://www.3hebao.co/27_27118/7653470.html";
        host = "http://www.3hebao.co/1_1257/844814.html";
        String context = getContext(host);
        StringBuilder sb = new StringBuilder(context);
        int max = sb.length();
        int min = 50;
        for (int i =0 ;i<max;){
            min = Math.min(min,max-i);
            String substring = sb.substring(i, i+min);
            System.out.println(substring);
            i +=min;
        }

    }
}
