package com.smart.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author im_na
 */
@Component
public class SessionHelper {
    public void removeMessageFromSession(){
        try {
            System.out.println("REmoving");
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            HttpSession session = request.getSession();
            session.removeAttribute("message");
            session.removeAttribute("message");
        } catch (RuntimeException ex) {
          ex.printStackTrace();
        }
    }
}
