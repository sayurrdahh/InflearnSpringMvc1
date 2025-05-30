package hello.servlet.web.frontController.v4;

import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberSaveControllerV4;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    //매핑정보 만들기
    private Map<String, ControllerV4> controllerV4Map = new HashMap<>();

    //생성자 만들기
    public FrontControllerServletV4() {
        controllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestURI = req.getRequestURI();

        ControllerV4 controllerV4 = controllerV4Map.get(requestURI);
        if(controllerV4 == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //메서드 추출 ctrl alt m
        Map<String, String> paramMap = createParamMap(req);
        Map<String, Object> model = new HashMap<>(); //추가됨
        String viewName = controllerV4.process(paramMap, model);

        MyView view = viewResolver(viewName);

        view.render(model, req, resp);
    }

    private MyView viewResolver(String viewName){
        return new MyView("/WEB-INF/views/"+viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest req) {
        //paramMap 넘겨줘야 한다
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap;
    }
}
