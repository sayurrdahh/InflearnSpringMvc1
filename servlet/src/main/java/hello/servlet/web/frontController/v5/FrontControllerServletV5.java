package hello.servlet.web.frontController.v5;

import hello.servlet.web.frontController.ModelView;
import hello.servlet.web.frontController.MyView;
import hello.servlet.web.frontController.v3.ControllerV3;
import hello.servlet.web.frontController.v3.FrontControllerServletV3;
import hello.servlet.web.frontController.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontController.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontController.v4.ControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontController.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontController.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontController.v5.adapter.ControllerV4HandlerAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    /*기존에는 controller가 지정이 되어 있었음
    이전 코드 :
    private Map<String, ControllerV4> controllerMap = new HashMap<>();
    */

    private final Map<String , Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5(){
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        /*v3*/
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        /*v4*/
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());

        handlerAdapters.add(new ControllerV4HandlerAdapter());

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //요청 정보를 가지고 핸들러를 찾아옴 : 핸들러 호출
        Object handler = getHandler(req);

        //오류 처리 로직
        if(handler == null){
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //handler adapter 찾아오기
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        //모델 뷰 반환
        ModelView mv = adapter.handle(req, resp, handler);
        
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), req, resp);
    }

    private Object getHandler(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        //.iter 하면 이터레이터 생성됨
        for (MyHandlerAdapter adapter : handlerAdapters) {
            //adapter가 handler을 지원하는가?
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw  new IllegalArgumentException("handler adapter을 찾을 수 없습니다.");
    }

    private MyView viewResolver(String viewName){
        return new MyView("/WEB-INF/views/"+viewName + ".jsp");
    }

}
