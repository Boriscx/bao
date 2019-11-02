package tedu.bao.util;

import org.junit.Test;

public class NumberUtil {


    private static char[] cnArr = new char[]{'零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '百', '千', '万', '亿'};

    public static int chineseNumberToInt(String chineseNumber) {
        int result = 0;
        int temp = 1;
        for (int i = chineseNumber.length() - 1; i >= 0; i--) {
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {
                if (c == cnArr[j]) {
                    if (j <= 9) {
                        result += temp * j;
                        temp = 1;
                    } else {
                        switch (j) {
                            case 13:
                                temp *= 10;
                            case 12:
                                temp *= 10;
                            case 11:
                                temp *= 10;
                            case 10:
                                if (i == 0) {
                                    result += temp * j;
                                } else {
                                    temp *= 10;
                                }
                            default:
                                break;
                        }                    }
                }
            }
        }
        return result;
    }

    @Test
    public void test() {
        String number = "十";
        System.out.println(chineseNumberToInt(number));
    }
}
