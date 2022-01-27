package com.example.securitybasic.interceptor;

import com.example.securitybasic.domain.Character;
import com.example.securitybasic.repository.CharacterRepository;
import com.example.securitybasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Component
public class CharacterInterceptor implements HandlerInterceptor {

    private final CharacterRepository characterRepository;

    //Filter 에서 이미 권한에 관한 것을 처리했으므로 인가에 관해서만 처리합니다.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // URI 즉 localhost:8080의 그 뒷부분이 해당합니다.
        String requestURI = request.getRequestURI();

        //session 을 통해서 어느 User 가 요청했는지 알 수 있습니다.
        String email = (String) request.getSession().getAttribute("email");


        //Post 에 관해서는 자신의 캐릭터를 만드는 것이므로 모두 접근 가능하지만 Patch 에 관해서는 자신이 소유한 것만 가능하므로 체크해줍니다.
        if(requestURI.contains("/character") && request.getMethod().equals("PATCH")){
            //Patch 를 하는 캐릭터의 id 입니다.
            long id = Long.parseLong(request.getParameter("id"));
            Character character = characterRepository.findFetchById(id).get();
            if(!character.getUser().getEmail().equals(email)){
                response.sendRedirect("/notAccess");
                return false;
            }
        }
        else{
            return true;
        }
        return true;
    }
}
