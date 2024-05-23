package gsm.devfest.global.error.handler;

import gsm.devfest.global.error.BasicException;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

@Order(-1)
@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties webProperties,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(errorAttributes, webProperties.getResources(), applicationContext);
        super.setMessageReaders(serverCodecConfigurer.getReaders());
        super.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::handleError);
    }

    private Mono<ServerResponse> handleError(ServerRequest request) {
        Throwable throwable = super.getError(request);
        if (throwable instanceof BasicException) {
            return buildErrorResponse((BasicException) throwable);
        } else if (throwable instanceof ConnectException) {
            return buildErrorResponse(new BasicException("Cannot Connect to Service", HttpStatus.INTERNAL_SERVER_ERROR));
        } else {
            return buildErrorResponse(new BasicException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    private Mono<ServerResponse> buildErrorResponse(BasicException ex) {
        return ServerResponse.status(ex.getStatus())
                .bodyValue(new ErrorResponse(ex.getErrorMessage(), ex.getStatus().value()));
    }
}
