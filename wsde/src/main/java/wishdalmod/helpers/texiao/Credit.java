package wishdalmod.helpers.texiao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Credit {
    public static final String WEIBO = "微博";

    public static final String BILI = "bilibili";

    public static final String LOFTER = "lofter";

    public static final String PIXIV = "pixiv";

    String platform() default "";

    String username() default "";

    String link() default "";

    String title() default "";
}
