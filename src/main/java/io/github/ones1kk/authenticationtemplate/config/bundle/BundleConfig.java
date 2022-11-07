package io.github.ones1kk.authenticationtemplate.config.bundle;

import io.github.ones1kk.authenticationtemplate.domain.Label;
import io.github.ones1kk.authenticationtemplate.domain.Message;
import io.github.ones1kk.authenticationtemplate.service.LabelService;
import io.github.ones1kk.authenticationtemplate.service.MessageService;
import io.github.ones1kk.authenticationtemplate.service.bundle.DynamicBundleGenerator;
import io.github.ones1kk.authenticationtemplate.service.bundle.constant.DynamicBundle;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class BundleConfig implements ApplicationRunner {

    private final LabelService labelService;

    private final MessageService messageService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Bundle of labels.
        Set<Label> labels = labelService.findLabelSetBy();
        DynamicBundleGenerator.builder(DynamicBundle.LABELS, labels)
                .addLocale(Locale.KOREA, Label::getLabelId, Label::getLabelKorName)
                .addLocale(Locale.US, Label::getLabelId, Label::getLabelEngNm)
                .addDefault(Label::getLabelId, Label::getLabelEngNm)
                .build()
                .make();
        // Bundle of messages.
        Set<Message> messages = messageService.findMessagesSetBy();
        DynamicBundleGenerator.builder(DynamicBundle.MESSAGES, messages)
                .addLocale(Locale.KOREA, Message::getMessageId, Message::getMessageKorName)
                .addLocale(Locale.US, Message::getMessageId, Message::getMessageEngNm)
                .addDefault(Message::getMessageId, Message::getMessageId)
                .build()
                .make();
    }
    @Bean
    @Primary
    public static MessageSource messageSource() {
        String[] baseNames = EnumSet.allOf(DynamicBundle.class).stream()
                .map(it -> "classpath:/" + DynamicBundleGenerator.DYNAMIC_RESOURCE_DIRECTORY_NAME + '/'
                        + it.getBundleName() + '/' + it.getBundleName())
                .toArray(String[]::new);

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setBasenames(baseNames);
        return messageSource;
    }
}
