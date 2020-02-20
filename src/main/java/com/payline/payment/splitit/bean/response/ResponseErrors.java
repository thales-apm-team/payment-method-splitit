package com.payline.payment.splitit.bean.response;

public class ResponseErrors {
    String errorCode;
    String message;
    String additionalInfo;

    public static class ResponseErrorsBuilder {
        String errorCode;
        String message;
        String additionalInfo;

        public ResponseErrorsBuilder withErrorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public  ResponseErrorsBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseErrorsBuilder withAdditionalInfo(String additionalInfo) {
            this.additionalInfo = additionalInfo;
            return this;
        }

        public ResponseErrors build() {
            ResponseErrors responseErrors = new ResponseErrors();
            responseErrors.errorCode = errorCode;
            responseErrors.message = message;
            responseErrors.additionalInfo = additionalInfo;
            return responseErrors;
        }
    }
}
