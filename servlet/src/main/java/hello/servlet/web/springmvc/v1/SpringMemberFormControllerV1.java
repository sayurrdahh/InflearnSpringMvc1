package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
* controller 애노테이션이 있으면 자동으로 스프링 빈 등록
* */
@Controller
public class SpringMemberFormControllerV1 {

    //요청정보를 매핑한다.
    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process(){
        return new ModelAndView("new-form"); //webapp/jsp/members/new-form.jsp
    }

}
