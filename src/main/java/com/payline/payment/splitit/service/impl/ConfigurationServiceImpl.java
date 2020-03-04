package com.payline.payment.splitit.service.impl;

import com.payline.payment.splitit.bean.request.Cancel;
import com.payline.payment.splitit.bean.request.Login;
import com.payline.payment.splitit.bean.request.Refund;
import com.payline.payment.splitit.bean.configuration.RequestConfiguration;
import com.payline.payment.splitit.bean.response.LoginResponse;
import com.payline.payment.splitit.exception.PluginException;
import com.payline.payment.splitit.utils.Constants;
import com.payline.payment.splitit.utils.PluginUtils;
import com.payline.payment.splitit.utils.http.HttpClient;
import com.payline.payment.splitit.utils.i18n.I18nService;
import com.payline.payment.splitit.utils.properties.ReleaseProperties;
import com.payline.pmapi.bean.configuration.ReleaseInformation;
import com.payline.pmapi.bean.configuration.parameter.AbstractParameter;
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

        AbstractParameter numberOfInstallments = new ListBoxParameter();
        numberOfInstallments.setKey(Constants.ContractConfigurationKeys.NUMBER_OF_INSTALLMENTS);
        numberOfInstallments.setLabel(i18n.getMessage("numberOfInstallments.label", locale));
        numberOfInstallments.setDescription(i18n.getMessage("numberOfInstallments.decription", locale)); // todo il te manque la description dans les fichiers de traduction
        parameters.add(numberOfInstallments);


        AbstractParameter requestedNumberOfInstallments = new InputParameter();
        requestedNumberOfInstallments.setKey(Constants.ContractConfigurationKeys.REQUESTED_NUMBER_OF_INSTALLMENTS);
        requestedNumberOfInstallments.setLabel("requested number of installments"); // todo i18n.getMessage()
        requestedNumberOfInstallments.setDescription("Define a list of number of installments the shopper can choose " +
                "between 1 and 12");// todo i18n.getMessage() en plus t'as deja préparé le fichier de trad!
        requestedNumberOfInstallments.setRequired(true); // todo c'est vraiment obligatoire?
        parameters.add(requestedNumberOfInstallments);

        //
        Map<String, String> refundStrategyMap = new HashMap<>();
        refundStrategyMap.put(Refund.refundStrategyEnum.NoRefunds.toString(), "No refund");
        refundStrategyMap.put(Refund.refundStrategyEnum.FutureInstallmentsFirst.toString(), "Future installment first"); // todo peut etre traduire ces champs ( c'est pas forcé mais faut me convaincre :) )
        refundStrategyMap.put(Refund.refundStrategyEnum.FutureInstallmentsLast.toString(), "Future installment last");
        refundStrategyMap.put(Refund.refundStrategyEnum.FutureInstallmentsNotAllowed.toString(), "future installment not allowed");

        ListBoxParameter refundStrategy = new ListBoxParameter();
        refundStrategy.setKey(Constants.ContractConfigurationKeys.REFUND_STRATEGY);
        refundStrategy.setList(refundStrategyMap);
        refundStrategy.setLabel("refund strategy");// todo i18n.getMessage()
        refundStrategy.setDescription("how would you refund your amount for your installment ?");// todo i18n.getMessage()
        refundStrategy.setRequired(false);
        parameters.add(refundStrategy);

        Map<String, String> refundUnderCancelationMap = new HashMap<>(); // todo petite erreur de typo: cancellation
        refundUnderCancelationMap.put(Cancel.RefundUnderCancelation.NO_REFUNDS.toString(), "NoRefunds");// todo pareil: peut etre traduire ces champs ( c'est pas forcé mais faut me convaincre :) )
        refundUnderCancelationMap.put(Cancel.RefundUnderCancelation.ONLY_IF_A_FULL_REFUND_IS_POSSIBLE.toString(), "OnlyIfAFullRefundIsPossible");

        ListBoxParameter refundUnderCancelation = new ListBoxParameter();
        refundUnderCancelation.setKey(Constants.ContractConfigurationKeys.REFUND_UNDER_CANCELATION);
        refundUnderCancelation.setList(refundUnderCancelationMap);
        refundUnderCancelation.setLabel("refund under cancelation");// todo i18n.getMessage()
        refundUnderCancelation.setDescription("how do you want to cancel your installmentPlan ?");
        refundUnderCancelation.setRequired(false);
        parameters.add(refundUnderCancelation);

        return parameters;
    }

    @Override
    public Map<String, String> check(ContractParametersCheckRequest request) {
        final Locale locale = request.getLocale();
        final Map<String, String> errors = new HashMap<>();
        final RequestConfiguration configuration = new RequestConfiguration(
                request.getContractConfiguration()
                , request.getEnvironment()
                , request.getPartnerConfiguration()
        );
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
            // todo pense bien a logguer les Exceptions
            errors.put(ContractParametersCheckRequest.GENERIC_ERROR, e.getErrorCode());
        }
        // todo pense a ajouter le catch(RuntimeException)
        return errors;
    }

    @Override
    public ReleaseInformation getReleaseInformation() {
        return ReleaseInformation.ReleaseBuilder.aRelease()
                .withDate(LocalDate.parse(releaseProperties.get("release.date"), DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .withVersion(releaseProperties.get("release.version"))
                .build();
    }

    @Override
    public String getName(Locale locale) {
        return i18n.getMessage("paymentMethod.name", locale);
    }
}
