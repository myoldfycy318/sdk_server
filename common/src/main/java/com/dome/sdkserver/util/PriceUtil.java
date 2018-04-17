package com.dome.sdkserver.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PriceUtil {
    private static final Log LOG = LogFactory.getLog(PriceUtil.class);

    /**
     * 把钱转换成分
     *
     * @param price
     * @return
     */
    public static long convertToFen(float price) {
        return PriceUtil.convertToFen(Float.toString(price));
    }

    /**
     * 把钱转换成分
     *
     * @param price
     * @return
     */
    public static long convertToFen(String price) {
        return new BigDecimal(price).multiply(new BigDecimal(100f)).longValue();
    }

    public static BigDecimal convertToFen(BigDecimal price) {
        return price.multiply(new BigDecimal("100"));
    }

    /**
     * BigDecimal转为分，返回取BigDecimal.longValue()
     * @param price
     * @return
     */
    public static BigDecimal convertToFenL(BigDecimal price) {
        return price.multiply(new BigDecimal("100"));
    }

    /**
     * BigDecimal转为元，返回取BigDecimal.doubleValue()
     * @param price
     * @return
     */
    public static BigDecimal convert2YuanD(BigDecimal price) {
        return price.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_DOWN);
    }


    /**
     * 格式化订单金额.
     * <pre>
     * 格式：整数位不前补零,小数位补齐2位即：不超过10位整数位+1位小数点+2位小数
     * 无效格式如123，.10，1.1
     * 有效格式如1.00，0.10
     * </pre>
     *
     * @param amount 以分为单位的金额
     * @return
     */
    public static String trans2YuanStr(Long amount) {
        String amountYuan = new BigDecimal(amount).divide(new BigDecimal("100")).setScale(2).toString();
        return amountYuan;
    }

    /**
     * 得到整形元
     *
     * @param ammount
     * @return
     */
    public static Long getLongPriceYuan(Long ammount) {
        return Long.valueOf(ammount / 100);
    }

    /**
     * 向上取整(保证价格整数元)
     *
     * @return
     */
    public static long getLongPrice(Long price) {
        try {
            if (price == null || price < 0) {
                return 0l;
            }
            BigDecimal p = new BigDecimal(price);
            return 100L * (long) (p.divide(new BigDecimal(100), 0, BigDecimal.ROUND_UP).floatValue());
        } catch (Exception e) {
            LOG.error(e);
        }
        return 0L;
    }

    /**
     * @param price
     * @return
     */
    public static float convertToYuan(final Long price) {
        if (price == null) {
            return 0f;
        }
        BigDecimal p = new BigDecimal(price);
        return p.divide(new BigDecimal("100"), 2, BigDecimal.ROUND_UP).floatValue();
    }


    private static DecimalFormat df = new DecimalFormat("0.##");

    /**
     * 数字计算到小数点后2位，去0
     */
    public static String convertToFenRemoveZero(Long price) {
        if (price == null) {
            return "0";
        }
        BigDecimal p = new BigDecimal(Double.valueOf(price) / 100);
        return df.format(p);
    }


    public static String formatDecimal(Object price) {
        DecimalFormat df = new DecimalFormat("####0.00");
        String strPrice = df.format(price);
        return strPrice;
    }

    public static float convertToYuan(final BigDecimal price) {
        return convertToYuan(price.longValue());
    }


    public static String moneyConvertStr(long price) {
        BigDecimal bigDecimal = new BigDecimal(price);
        return (bigDecimal.floatValue() / 100) + "";
    }

    public static Long convertToHoursForDistribution(Long minutes) {
        int min = Integer.parseInt(minutes.toString());
        int lesMin = min % 60;
        if (lesMin != 0) {
            return minutes / 60 + 1;
        } else {
            return minutes / 60;
        }
    }

    public static void main(String arg[]) {
        Long price = 232353L;
        BigDecimal p = new BigDecimal(price);
        float pr1 = p.divide(new BigDecimal(100), 0, BigDecimal.ROUND_UP).floatValue();
        float pr2 = p.divide(new BigDecimal(100), 0, BigDecimal.ROUND_DOWN).floatValue();
        float pr3 = p.divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP).floatValue();
        float pr4 = p.divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_DOWN).floatValue();
        System.out.println(pr1);
        System.out.println(pr2);
        System.out.println(pr3);
        System.out.println(pr4);
    }

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */

    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
