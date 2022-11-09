package com.atguigu.webfluxdemo1;

import com.atguigu.webfluxdemo1.handler.UserHandler;
import com.atguigu.webfluxdemo1.service.UserService;
import com.atguigu.webfluxdemo1.service.impl.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


import com.atguigu.webfluxdemo1.handler.UserHandler;
import com.atguigu.webfluxdemo1.service.UserService;
import com.atguigu.webfluxdemo1.service.impl.UserServiceImpl;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctionDsl;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.toHttpHandler;
public class Server {

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.createReactorServer();
        System.out.println("enter to exit");
        System.in.read();
    }

    public RouterFunction<ServerResponse> routingFunction() {
        //创建handler对象
        UserService userService = new UserServiceImpl();
        UserHandler handler = new UserHandler(userService);
        //设置路由
        return RouterFunctions.route(
                        GET("/users/{id}").and(accept(APPLICATION_JSON)),handler::getUserById)
                .andRoute(GET("/users").and(accept(APPLICATION_JSON)),handler::getAllUsers);
    }

    public void createReactorServer(){
        RouterFunction<ServerResponse> route = routingFunction();
        HttpHandler httpHandler = toHttpHandler(route);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer httpServer=HttpServer.create();
        httpServer.handle(adapter).bindNow();
    }
}
