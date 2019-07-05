package com.aps.services.user.exception;

import com.aps.services.user.exception.usageerrors.UsageException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;

@ControllerAdvice
public class MessageExceptionHandler {
    @ExceptionHandler({UserNotLoggedInException.class})
    public String handleNotLoggedInUser() {
        return "login";
    }

    @ExceptionHandler({UsageException.class})
    public RedirectView handleUsageException(HttpServletRequest request, HttpServletResponse response, UsageException ue) {
        request.setAttribute(ERROR_MESSAGE, ue.getMessage());
        String url;
        if (ue.getReferTo() != null) {
            url = ue.getReferTo();
        } else {
            url = request.getRequestURI();
        }
        RedirectView rw = new RedirectView(url);
        FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
        if (fm != null) {
            fm.put(ERROR_MESSAGE, ue.getMessage());
        }
        return rw;
    }
}
