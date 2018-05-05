package study.jigl.com.rxjavastudy.util;


import timber.log.Timber;

/**
 * Created by dawnxiao on 14-9-5.
 * 公用日志工具类
 */
public class JLGLLog {

    private static final String TAG = "JLGLLog";

    static {
        Timber.plant(new Timber.DebugTree());
    }

    /**
     * VERBOSE格式化日志输出
     *
     * @param tagName
     * @param format
     * @param logMsg
     */
    public static final void v(final String tagName, final String format, final Object... logMsg) {
        Timber.tag(tagName).v(format, logMsg);
    }

    /**
     * DEBUG格式化日志输出
     *
     * @param tagName
     * @param format
     * @param logMsg
     */
    public static final void d(final String tagName, final String format, final Object... logMsg) {
        Timber.tag(tagName).d(format, logMsg);
    }

    /**
     * INFO日志格式化输出
     *
     * @param tagName
     * @param format
     * @param logMsg
     */
    public static final void i(final String tagName, final String format, final Object... logMsg) {
        Timber.tag(tagName).i(format, logMsg);
    }

    /**
     * WARN日志格式化输出
     *
     * @param tagName
     * @param format
     * @param logMsg
     */
    public static final void w(final String tagName, final String format, final Object... logMsg) {
        Timber.tag(tagName).w(format, logMsg);
    }

    /**
     * WARN日志格式化输出
     *
     * @param tagName
     * @param format
     * @param tr
     * @param logMsg
     */
    public static final void w(final String tagName, final String format, final Throwable tr, final Object... logMsg) {
        Timber.tag(tagName).w(tr, format, logMsg);
    }

    /**
     * ERROR日志格式化输出
     *
     * @param tagName
     * @param format
     * @param logMsg
     */
    public static final void e(final String tagName, final String format, final Object... logMsg) {
        Timber.tag(tagName).e(format, logMsg);
    }

    public static final void e(final String tagName, final String format, final Throwable tr, final Object... logMsg) {
        Timber.tag(tagName).e(tr, format, logMsg);
    }
}
