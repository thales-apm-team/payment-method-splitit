package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.service.LogoPaymentFormConfigurationService;
import com.payline.payment.splitit.utils.i18n.I18nService;
import com.payline.pmapi.bean.paymentform.bean.form.NoFieldForm;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.PaymentFormConfigurationResponse;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;

public class PaymentFormConfigurationServiceImpl extends LogoPaymentFormConfigurationService {
    private static final String NO_FIELD_TEXT = "form.button.text";// todo il te manque ce champs dans les fichiers de traduction
    private static final String NO_FIELD_DESCRIPTION = "form.button.description";// todo pareil

    private I18nService i18n = I18nService.getInstance();

    @Override
    public PaymentFormConfigurationResponse getPaymentFormConfiguration(PaymentFormConfigurationRequest paymentFormConfigurationRequest) {
        NoFieldForm noFieldForm = NoFieldForm.NoFieldFormBuilder.aNoFieldForm()
                .withDisplayButton(true)
                .withButtonText(i18n.getMessage(NO_FIELD_TEXT, paymentFormConfigurationRequest.getLocale()))
                .withDescription(i18n.getMessage(NO_FIELD_DESCRIPTION, paymentFormConfigurationRequest.getLocale()))
                .build();

        return PaymentFormConfigurationResponseSpecific.PaymentFormConfigurationResponseSpecificBuilder.aPaymentFormConfigurationResponseSpecific()
                .withPaymentForm(noFieldForm)
                .build();
    }
}
