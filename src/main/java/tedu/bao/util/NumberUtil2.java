package tedu.bao.util;

import org.junit.Test;

public class NumberUtil2 {


    //private static char[] cnArr = new char[]{'零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '百', '千', '万', '亿'};
    private static String abc = "零一二三四五六七八九十百千万亿";

    public static int chineseNumberToInt(String chineseNumber) {
        int result = 0;
        int temp = 1;
        for (int i = chineseNumber.length() - 1; i >= 0; i--) {
            int j = abc.indexOf(chineseNumber.charAt(i));
            if (j != -1)
                if (j <= 9) {
                    result += temp * j;
                    temp = 1;
                } else {
                    for (int x = j; x > 9; x--) {
                        if (i == 0) {
                            result += temp * j;
                        } else {
                            temp *= 10;
                        }
                    }
                }
        }
        return result;
    }

    @Test
    public void test() {
        String number = "五十一";
        System.out.println(chineseNumberToInt(number));
    }
}
