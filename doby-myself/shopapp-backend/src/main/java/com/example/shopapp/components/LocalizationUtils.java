package com.example.shopapp.components;

import com.example.shopapp.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

@Component
@RequiredArgsConstructor
public class LocalizationUtils {

    private final MessageSource messageSource;
    private final LocaleResolver localResolver;

    public String getLocalizedMessage(String messageKey, Object ...params){
        HttpServletRequest request = WebUtils.getCurrentRequest();
        Locale locale = localResolver.resolveLocale(request);
        return messageSource.getMessage(messageKey, params, locale);
    }

}
