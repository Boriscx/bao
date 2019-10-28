package tedu.bao.day17;

import java.io.*;

public class TestDemo {

    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws Exception{
        File dir = new File("g:/ebook/test12");
        File to = new File("g:/ebook/test12/test.txt");
        if (!to.exists()){
            to.createNewFile();
        }
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to)));

        for (File file : dir.listFiles()){
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String read ;
            while ((read = in.readLine())!=null){
                sb.append(read);
            }
            sb.append("\n");
            in.close();
        }
        out.write(sb.toString());
        out.flush();
        out.close();
//        do {
//            System.out.println(sb.toString());
//        }while (sb.length()>0);

    }
}
