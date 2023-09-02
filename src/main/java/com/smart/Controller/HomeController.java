package com.smart.Controller;

import com.smart.dao.UserRepository;
import com.smart.helper.Message;
import com.smart.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author im_na
 */
@Controller
public class HomeController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manger");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About - Smart Contact Manger");

        return "about";
    }

    @RequestMapping("/signup")
    public String singUp(Model model) {
        model.addAttribute("title", "Sing UP - Smart Contact Manger");
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, @RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model, HttpSession session) {

        try {
             if(result.hasErrors()){
                model.addAttribute("user",user);
                return "signup";
            }else if (agreement) {
                user.setRole("ROLE_USER");
                user.setEnabled(true);
                user.setImageUrl("default.png");
                user.setPassword(passwordEncoder.encode(user.getPassword()));

                System.out.println("Agreement " + agreement);
                System.out.println("USER " + user);

                User saved = this.repository.save(user);


                model.addAttribute("user", new User());

                session.setAttribute("message", new Message("SuccessFully !! ", "alert-success"));

                return "signup";
            } else  {
                System.out.println("not check");
                throw new Exception("You Have Not Agreed The Terms and Conditions");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            System.out.println(e.getMessage());
            System.out.println(e.toString());
            session.setAttribute("message", new Message("Something Went Wrong  !! " + e.getMessage().toString(), "alert-danger"));
            return "signup";
        }
    }
    //handler for custom login
    @GetMapping("/signin")
    public String customLogin(Model model)
    {
        model.addAttribute("title","Login Page");
        return "login";
    }
}
