package io.jamdyer.trading.portfolio.user;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public  String home(){
        return "This is Home";
    }
    
    @GetMapping("/basic")
    public  String basic(){
        return "This is Basic User";
    }

    @GetMapping("/admin")
    public  String admin(){
        return "This is Admin User";
    }

    @PostMapping("/create")
    public String create(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.create(username, password);
    }
    

}
