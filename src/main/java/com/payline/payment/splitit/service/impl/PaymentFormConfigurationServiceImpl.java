package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.service.LogoPaymentFormConfigurationService;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.i18n.I18nService;
import com.payline.pmapi.bean.paymentform.bean.field.PaymentFormField;
import com.payline.pmapi.bean.paymentform.bean.field.PaymentFormInputFieldSelect;
import com.payline.pmapi.bean.paymentform.bean.field.SelectOption;
import com.payline.pmapi.bean.paymentform.bean.form.CustomForm;
import com.payline.pmapi.bean.paymentform.request.PaymentFormConfigurationRequest;
import com.payline.pmapi.bean.paymentform.response.configuration.PaymentFormConfigurationResponse;
import com.payline.pmapi.bean.paymentform.response.configuration.impl.PaymentFormConfigurationResponseSpecific;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PaymentFormConfigurationServiceImpl extends LogoPaymentFormConfigurationService {

    private I18nService i18n = I18nService.getInstance();
    private static final String NOW = "firstChargeDateNow.label";

    @Override
    public PaymentFormConfigurationResponse getPaymentFormConfiguration(PaymentFormConfigurationRequest paymentFormConfigurationRequest) {
        Locale locale = paymentFormConfigurationRequest.getLocale();
        List<PaymentFormField> fields = new ArrayList<>();
        List<SelectOption> options = new ArrayList<>();

        if (paymentFormConfigurationRequest.getContractConfiguration() == null) {
            // add the default cause this is not required if the first installment is on the same date of the initialisation
            options.add(SelectOption.SelectOptionBuilder.aSelectOption()
                    .withValue(i18n.getMessage(NOW, locale))
                    .withKey(Constants.ContractConfigurationKeys.FIRSTCHARGEDATENOW)
                    .build());
        } else {
            if (paymentFormConfigurationRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.FIRSTCHARGEDATENOW).getValue().equals("true")) {
                options.add(SelectOption.SelectOptionBuilder.aSelectOption()
                        .withValue(i18n.getMessage(NOW, locale))
                        .withKey(Constants.ContractConfigurationKeys.FIRSTCHARGEDATENOW)
                        .build());
            }

            if (paymentFormConfigurationRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.FIRSTCHARGEDATEONEWEEK).getValue().equals("true")) {
                options.add(SelectOption.SelectOptionBuilder.aSelectOption()
                        .withValue(i18n.getMessage("firstChargeDateOneWeek.label", locale))
                        .withKey(Constants.ContractConfigurationKeys.FIRSTCHARGEDATEONEWEEK)
                        .build());
            }
            if (paymentFormConfigurationRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.FIRSTCHARGEDATETWOWEEKS).getValue().equals("true")) {
                options.add(SelectOption.SelectOptionBuilder.aSelectOption()
                        .withValue(i18n.getMessage("firstChargeDateTwoWeeks.label", locale))
                        .withKey(Constants.ContractConfigurationKeys.FIRSTCHARGEDATETWOWEEKS)
                        .build());
            }

            if (paymentFormConfigurationRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.FIRSTCHARGEDATEONEMONTH).getValue().equals("true")) {
                options.add(SelectOption.SelectOptionBuilder.aSelectOption()
                        .withValue(i18n.getMessage("firstChargeDateOneMonth.label", locale))
                        .withKey(Constants.ContractConfigurationKeys.FIRSTCHARGEDATEONEMONTH)
                        .build());
            }

            if (paymentFormConfigurationRequest.getContractConfiguration().getProperty(Constants.ContractConfigurationKeys.FIRSTCHARGEDATETWOMONTHS).getValue().equals("true")) {
                options.add(SelectOption.SelectOptionBuilder.aSelectOption()
                        .withValue(i18n.getMessage("firstChargeDateTwoMonths.label", locale))
                        .withKey(Constants.ContractConfigurationKeys.FIRSTCHARGEDATETWOMONTHS)
                        .build());
            }
        }

        PaymentFormInputFieldSelect paymentFormInputFieldSelect = PaymentFormInputFieldSelect.PaymentFormFieldSelectBuilder
                .aPaymentFormInputFieldSelect()
                .withSelectOptions(options)
                .withKey(Constants.FormConfigurationKeys.OPTIONS)
                .withLabel(i18n.getMessage("option.label", locale))
                .withValidationErrorMessage(i18n.getMessage("validationErrorMessage.label", locale))
                .withPlaceholder(i18n.getMessage(NOW, locale))
                .build();

        fields.add(paymentFormInputFieldSelect);

        CustomForm customForm = CustomForm.builder()
                .withCustomFields(fields)
                .withButtonText(i18n.getMessage(Constants.FormConfigurationKeys.PAYMENTBUTTONTEXT, locale))
                .withDescription(i18n.getMessage(Constants.FormConfigurationKeys.PAYMENTBUTTONDESC, locale))
                .build();

        return PaymentFormConfigurationResponseSpecific.PaymentFormConfigurationResponseSpecificBuilder.aPaymentFormConfigurationResponseSpecific()
                .withPaymentForm(customForm)
                .build();
    }
}
