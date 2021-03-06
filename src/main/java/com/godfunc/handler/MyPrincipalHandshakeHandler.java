package com.godfunc.handler;

import com.godfunc.domain.WebSocketUserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * 用户信息认证
 **/
@Slf4j
public class MyPrincipalHandshakeHandler extends DefaultHandshakeHandler {


    /**
     * 将user与websocket进行关联
     *
     * @param request
     * @param wsHandler
     * @param attributes
     * @return
     */
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {

        // 获取用户标示，用于发送点对点消息
        String user = null;
        try {
            user = request.getHeaders().get("id").get(0);
        } catch (Exception e) {
            log.error("登陆信息无效");
            return null;
        }

        if (StringUtils.isEmpty(user)) {
            log.error("登陆信息无效");
            return null;
        }
        log.info("user login = {}", user);
        return new WebSocketUserAuthentication(user);
    }

}
