package com.payline.payment.moneytrack.service.impl;

import com.payline.payment.moneytrack.bean.*;
import com.payline.payment.moneytrack.bean.appel.Initiate;
import com.payline.payment.moneytrack.bean.appel.Login;
import com.payline.pmapi.bean.common.Buyer;
import com.payline.pmapi.bean.payment.request.PaymentRequest;
import com.payline.pmapi.bean.payment.response.PaymentResponse;
import com.payline.pmapi.bean.payment.response.impl.PaymentResponseFailure;
import com.payline.pmapi.service.PaymentService;

import static com.payline.payment.moneytrack.utils.Constants.ContractConfigurationKeys.*;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public PaymentResponse paymentRequest(PaymentRequest request) {

        Login login = new Login.LoginBuilder()
                .withUsername(request.getContractConfiguration().getProperty(USERNAME).getValue())
                .withPassword(request.getContractConfiguration().getProperty(PASSWORD).getValue())
                .build();

        BillingAddress billingAddress = new BillingAddress.BillingAddressBuilder()
                .withAddressLine(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getStreet1())
                .withAddressLine2(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getStreet2())
                .withCity(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getCity())
                .withState(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getState())
                .withCountry(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getCountry())
                .withZip(request.getBuyer().getAddressForType(Buyer.AddressType.BILLING).getZipCode())
                .build();

        ConsumerData consumerData = new ConsumerData.ConsumerDataBuilder()
                .withFullName(request.getBuyer().getFullName().getFirstName() + request.getBuyer().getFullName().getLastName())
                .withEmail(request.getBuyer().getEmail())
                .withPhoneNumber(request.getBuyer().getPhoneNumberForType(Buyer.PhoneNumberType.CELLULAR))
                .withCultureName(request.getLocale().toString())
                .build();

        PlanData planData = new PlanData.PlanDataBuilder()
                .withAmount(request.getAmount())
                .withNumberOfInstallments(Integer.parseInt(request.getContractConfiguration().getProperty(NUMBER_OF_INSTALLMENTS).getValue()))
                .withRefOrderNumber(request.getTransactionId())
                .withAutoCapture(request.isCaptureNow())
                .build();

        PaymentWizardData paymentWizardData = new PaymentWizardData.PaymentWizardDataBuilder()
                .withRequestednumberOfInstallments(String.valueOf(request.getContractConfiguration().getProperty(REQUESTED_NUMBER_OF_INSTALLMENTS)))
                .withIsOpenedInIframe(false)
                .build();

        Initiate initiate = new Initiate.InitiateBuilder()
                .withPlanData(planData)
                .withBillingAddress(billingAddress)
                .withConsumerData(consumerData)
                .withPaymentWizardData(paymentWizardData)
                .build();

//        request.getContractConfiguration().getProperty(MYKEY).getValue()

            return PaymentResponseFailure.PaymentResponseFailureBuilder
                    .aPaymentResponseFailure()
                    .build();
    }
}
