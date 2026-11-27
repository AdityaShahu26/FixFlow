
package com.fixflow.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.util.StringUtils;

import java.util.function.Supplier;

public class SpaCsrfTokenRequestHandler
        implements CsrfTokenRequestHandler {

    private final CsrfTokenRequestHandler plain =
            new CsrfTokenRequestAttributeHandler();

    private final CsrfTokenRequestHandler xor =
            new XorCsrfTokenRequestAttributeHandler();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            Supplier<CsrfToken> csrfToken) {

        xor.handle(request, response, csrfToken);

        // Force token generation so the cookie is written.
        csrfToken.get();
    }

    @Override
    public String resolveCsrfTokenValue(
            HttpServletRequest request,
            CsrfToken csrfToken) {

        String headerValue =
                request.getHeader(csrfToken.getHeaderName());

        // React sends the raw token in the header.
        if (StringUtils.hasText(headerValue)) {
            return plain.resolveCsrfTokenValue(request, csrfToken);
        }

        // Fall back to the default XOR handling.
        return xor.resolveCsrfTokenValue(request, csrfToken);
    }
}
