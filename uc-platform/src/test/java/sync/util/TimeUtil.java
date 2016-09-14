package sync.util;

/**
 * @author ChNan
 */
public class TimeUtil {
    public static String printCostTime(long cost) {
        if (cost < 1000) {
            return cost + "毫秒";
        }
        cost = cost / 1000;
        if (cost < 60) {
            return cost + "秒";
        }
        if (cost / 60 < 10) {
            return cost / 60 + "分" + cost % 60 + "秒";
        }
        return cost / 60 / 60 + "小时" + cost / 60 % 60 + "分" + cost % 60 % 60 + "秒";
    }

}
