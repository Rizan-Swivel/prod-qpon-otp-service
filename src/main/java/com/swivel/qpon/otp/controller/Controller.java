package com.swivel.qpon.otp.controller;

import com.swivel.qpon.otp.enums.ErrorResponseStatusType;
import com.swivel.qpon.otp.enums.SuccessResponseStatusType;
import com.swivel.qpon.otp.wrapper.ErrorResponseWrapper;
import com.swivel.qpon.otp.wrapper.ResponseWrapper;
import com.swivel.qpon.otp.wrapper.SuccessResponseWrapper;
import com.swivel.qpon.otp.configuration.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Controller
 */
public class Controller {

    protected static final String APPLICATION_JSON = "application/json";

    private final Translator translator;

    @Autowired
    public Controller(Translator translator) {
        this.translator = translator;
    }

    /**
     * This method creates the empty data response for bad request.
     *
     * @param errorResponseStatusType errorResponseStatusType
     * @return bad request error response
     */
    protected ResponseEntity<ResponseWrapper> getBadRequestError(ErrorResponseStatusType errorResponseStatusType) {
        ResponseWrapper responseWrapper =
                new ErrorResponseWrapper(errorResponseStatusType,
                        translator.toLocale(errorResponseStatusType.getCodeString(errorResponseStatusType.getCode())),
                        null);
        return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method creates the empty data response for internal server error.
     *
     * @return internal server error response
     */
    protected ResponseEntity<ResponseWrapper> getInternalServerError() {
        ResponseWrapper responseWrapper =
                new ErrorResponseWrapper(ErrorResponseStatusType.INTERNAL_SERVER_ERROR,
                        translator.toLocale(ErrorResponseStatusType.INTERNAL_SERVER_ERROR
                                .getCodeString(ErrorResponseStatusType.INTERNAL_SERVER_ERROR.getCode())),
                        null);
        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method creates empty data response for success response
     *
     * @return ok
     */
    protected ResponseEntity<ResponseWrapper> getSuccessResponse(SuccessResponseStatusType successResponseStatusType) {
        ResponseWrapper responseWrapper = new SuccessResponseWrapper(successResponseStatusType,
                translator.toLocale(successResponseStatusType.getCode()),
                null);
        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }

}
