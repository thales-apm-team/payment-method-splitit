package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.request.Cancel;
import com.payline.payment.splitit.bean.request.Login;
import com.payline.payment.splitit.bean.request.Refund;
import com.payline.payment.splitit.bean.response.LoginResponse;
import com.payline.payment.splitit.exception.PluginException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.PluginUtils;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.payment.splitit.utils.i18n.I18nService;
import com.payline.payment.splitit.utils.properties.ReleaseProperties;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.CheckboxParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.InputParameter;
import com.payline.pmapi.bean.configuration.parameter.impl.ListBoxParameter;
import com.payline.pmapi.bean.configuration.request.ContractParametersCheckRequest;
import com.payline.pmapi.logger.LogManager;
import com.payline.pmapi.service.ConfigurationService;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConfigurationServiceImpl implements ConfigurationService {
    private static final Logger LOGGER = LogManager.getLogger(ConfigurationServiceImpl.class);

    private ReleaseProperties releaseProperties = ReleaseProperties.getInstance();
    private I18nService i18n = I18nService.getInstance();
    private HttpClient client = HttpClient.getInstance();

    @Override
    public List<AbstractParameter> getParameters(Locale locale) {
        List<AbstractParameter> parameters = new ArrayList<>();

        AbstractParameter userName = new InputParameter();
        userName.setKey(Constants.ContractConfigurationKeys.USERNAME);
        userName.setLabel(i18n.getMessage("username.label", locale));
        userName.setDescription(i18n.getMessage("username.description", locale));
        userName.setRequired(true);
        parameters.add(userName);

        AbstractParameter password = new InputParameter();
        password.setKey(Constants.ContractConfigurationKeys.PASSWORD);
        password.setLabel(i18n.getMessage("password.label", locale));
        password.setDescription(i18n.getMessage("password.description", locale));
        password.setRequired(true);
        parameters.add(password);

        // checkbox for requestedNumberOfInstallment: list between 2 and 12
        List<String> requestedNumberOfInstallmentsList = new ArrayList<>();
        for (int i = 2; i < 13; i++) {
            requestedNumberOfInstallmentsList.add("REQUESTEDNUMBEROFINSTALLMENTS" + i);
            parameters.add(this.newCheckboxParameter("requestedNumberOfInstallments" + String.valueOf(i), requestedNumberOfInstallmentsList.get(i - 2), false, locale));
        }

        // if nothing is check, you can pay choose any installment
        CheckboxParameter requestedNumberOfInstallmentsDefault = new CheckboxParameter();
        requestedNumberOfInstallmentsDefault.setKey(Constants.ContractConfigurationKeys.REQUESTEDNUMBEROFINSTALLMENTSDEFAULT);
        requestedNumberOfInstallmentsDefault.setLabel(i18n.getMessage("requestedNumberOfInstallments.label", locale));
        requestedNumberOfInstallmentsDefault.setDescription(i18n.getMessage("requestedNumberOfInstallments.description", locale));
        requestedNumberOfInstallmentsDefault.setRequired(false);
        parameters.add(requestedNumberOfInstallmentsDefault);

        // can the shopper use 3DSecure ?
        CheckboxParameter attempt3DSecure = new CheckboxParameter();
        attempt3DSecure.setKey(Constants.ContractConfigurationKeys.ATTEMPT3DSECURE);
        attempt3DSecure.setLabel(i18n.getMessage("attempt3DSecure.label", locale));
        attempt3DSecure.setDescription(i18n.getMessage("attempt3DSecure.description", locale));
        attempt3DSecure.setRequired(true);
        parameters.add(attempt3DSecure);

        Map<String, String> refundStrategyMap = new HashMap<>();
        refundStrategyMap.put(Refund.refundStrategyEnum.NoRefunds.name(), i18n.getMessage("noRefund.value", locale));
        refundStrategyMap.put(Refund.refundStrategyEnum.FutureInstallmentsFirst.name(), i18n.getMessage("FutureInstallmentsFirst.value", locale));
        refundStrategyMap.put(Refund.refundStrategyEnum.FutureInstallmentsLast.name(), i18n.getMessage("FutureInstallmentsLast.value", locale));
        refundStrategyMap.put(Refund.refundStrategyEnum.FutureInstallmentsNotAllowed.name(), i18n.getMessage("FutureInstallmentsNotAllowed.value", locale));

        // refund strategy for the refund operation
        ListBoxParameter refundStrategy = new ListBoxParameter();
        refundStrategy.setKey(Constants.ContractConfigurationKeys.REFUNDSTRATEGY);
        refundStrategy.setList(refundStrategyMap);
        refundStrategy.setLabel(i18n.getMessage("refundStrategy.label", locale));
        refundStrategy.setDescription(i18n.getMessage("refundStrategy.description", locale));
        refundStrategy.setRequired(false);
        parameters.add(refundStrategy);

        // create the choice if the first installment is different from the others
        Map<String, String> firstInstallmentAmountMap = new HashMap<>();
        firstInstallmentAmountMap.put("30%", i18n.getMessage("firstInstallmentAmount30.value", locale));
        firstInstallmentAmountMap.put("40%", i18n.getMessage("firstInstallmentAmount40.value", locale));
        firstInstallmentAmountMap.put("50%", i18n.getMessage("firstInstallmentAmount50.value", locale));
        firstInstallmentAmountMap.put("60%", i18n.getMessage("firstInstallmentAmount60.value", locale));

        ListBoxParameter firstInstallmentAmount = new ListBoxParameter();
        firstInstallmentAmount.setKey(Constants.ContractConfigurationKeys.FIRSTINSTALLMENTAMOUNT);
        firstInstallmentAmount.setList(firstInstallmentAmountMap);
        firstInstallmentAmount.setLabel(i18n.getMessage("firstInstallmentAmount.label", locale));
        firstInstallmentAmount.setDescription(i18n.getMessage("firstInstallmentAmount.description", locale));
        firstInstallmentAmount.setRequired(false);
        parameters.add(firstInstallmentAmount);

        // refund strategy for the cancel operation
        Map<String, String> refundUnderCancellationMap = new HashMap<>();
        refundUnderCancellationMap.put(Cancel.RefundUnderCancellation.NoRefunds.name(), i18n.getMessage("noRefund.value", locale));
        refundUnderCancellationMap.put(Cancel.RefundUnderCancellation.OnlyIfAFullRefundIsPossible.name(), i18n.getMessage("OnlyIfAFullRefundIsPossible.value", locale));

        ListBoxParameter refundUnderCancellation = new ListBoxParameter();
        refundUnderCancellation.setKey(Constants.ContractConfigurationKeys.REFUNDUNDERCANCELLATION);
        refundUnderCancellation.setList(refundUnderCancellationMap);
        refundUnderCancellation.setLabel(i18n.getMessage("refundUnderCancellation.label", locale));
        refundUnderCancellation.setDescription(i18n.getMessage("refundUnderCancellation.description", locale));
        refundUnderCancellation.setRequired(false);
        parameters.add(refundUnderCancellation);

        // create the choice if the first date charge is different from the time of initialisation
        parameters.add(this.newCheckboxParameter("firstChargeDateNow", Constants.ContractConfigurationKeys.FIRSTCHARGEDATENOW, false, locale));
        parameters.add(this.newCheckboxParameter("firstChargeDateOneWeek", Constants.ContractConfigurationKeys.FIRSTCHARGEDATEONEWEEK, false, locale));
        parameters.add(this.newCheckboxParameter("firstChargeDateTwoWeeks", Constants.ContractConfigurationKeys.FIRSTCHARGEDATETWOWEEKS, false, locale));
        parameters.add(this.newCheckboxParameter("firstChargeDateOneMonth", Constants.ContractConfigurationKeys.FIRSTCHARGEDATEONEMONTH, false, locale));
        parameters.add(this.newCheckboxParameter("firstChargeDateTwoMonths", Constants.ContractConfigurationKeys.FIRSTCHARGEDATETWOMONTHS, false, locale));

        return parameters;
    }

    @Override
    public Map<String, String> check(ContractParametersCheckRequest request) {
        final Locale locale = request.getLocale();
        final Map<String, String> errors = new HashMap<>();
        final RequestConfiguration configuration = RequestConfiguration.build(request);

        try {
            String username = request.getAccountInfo().get(Constants.ContractConfigurationKeys.USERNAME);
            String password = request.getAccountInfo().get(Constants.ContractConfigurationKeys.PASSWORD);
            if (PluginUtils.isEmpty(username)) {
                errors.put(Constants.ContractConfigurationKeys.USERNAME, i18n.getMessage("username.empty", locale));
            } else if (PluginUtils.isEmpty(password)) {
                errors.put(Constants.ContractConfigurationKeys.PASSWORD, i18n.getMessage("password.empty", locale));
            } else {
                Login login = new Login.LoginBuilder().withUsername(username).withPassword(password).build();
                LoginResponse loginResponse = client.checkConnection(configuration, login);
                if (!loginResponse.getResponseHeader().isSucceeded()) {
                    errors.put(Constants.ContractConfigurationKeys.USERNAME, i18n.getMessage("login.invalid", locale));
                    errors.put(Constants.ContractConfigurationKeys.PASSWORD, "");
                }
            }
        } catch (PluginException e) {
            errors.put(ContractParametersCheckRequest.GENERIC_ERROR, e.getErrorCode());
        } catch (RuntimeException e) {
            LOGGER.error("unexpected plugin error", e);
        }
        return errors;
    }

    @Override
    public ReleaseInformation getReleaseInformation() {
        String date = releaseProperties.get("release.date");
        String version = releaseProperties.get("release.version");

        if (PluginUtils.isEmpty(date)){
            LOGGER.error("Date is not defined");
            throw new PluginException("Plugin error: Date is not defined");
        }

        if (PluginUtils.isEmpty(version)){
            LOGGER.error("Version is not defined");
            throw new PluginException("Plugin error: Version is not defined");
        }
        return ReleaseInformation.ReleaseBuilder.aRelease()
                .withDate(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .withVersion(version)
                .build();
    }

    @Override
    public String getName(Locale locale) {
        return i18n.getMessage("paymentMethod.name", locale);
    }

    /**
     * Create checkboxes
     *
     * @param prefix   the name for message.properties
     * @param key      key in Constants
     * @param required
     * @param locale
     * @return CheckboxParameter
     */
    private CheckboxParameter newCheckboxParameter(String prefix, String key, boolean required, Locale locale) {
        CheckboxParameter checkboxParameter = new CheckboxParameter();
        checkboxParameter.setKey(key);
        checkboxParameter.setLabel(i18n.getMessage(prefix + ".label", locale));
        checkboxParameter.setDescription(i18n.getMessage(prefix + ".description", locale));
        checkboxParameter.setRequired(required);
        return checkboxParameter;
    }
}