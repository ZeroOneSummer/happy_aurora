package com.aurora.happy.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * 国际化工具类
 */
@Component
public class I18nMessageUtils {

    private static MessageSource messageSource;

    @Autowired
    public I18nMessageUtils(MessageSource messageSource) {
        I18nMessageUtils.messageSource = messageSource;
    }

    public static String get(String msgKey) {
        try {
            return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
            return msgKey;
        }
    }
}