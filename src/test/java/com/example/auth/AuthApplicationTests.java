package com.example.auth;

import com.example.auth.domain.request.SignUpRequest;
import com.example.auth.domain.request.TeamRequest;
import com.example.auth.domain.response.UserResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Map;

@SpringBootTest
class AuthApplicationTests {
	@Autowired
	RestTemplate restTemplate;

	@Test
	void contextLoads() {
	}
	@Test
	void 통신(){
		SignUpRequest request = new SignUpRequest("1@2.com", "1234", "r", LocalDate.now(), "남");
		restTemplate
				.postForEntity(
						"http://192.168.0.12:8080/api/v1/auth/signup"
						,request
						, Void.class);
	}
	@Test
	void 통신1(){
		SignUpRequest request = new SignUpRequest("1@2.com", "1234", "r", LocalDate.now(), "남");
		Map<String, Object> res= restTemplate
				.postForEntity(
						"http://192.168.0.12:8080/api/v1/auth/signin"
						,request
						, Map.class).getBody();
		System.out.println(res);
	}
	@Test
	void 통신3(){
		TeamRequest request = new TeamRequest("안홍범", "7372");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization" ,"Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxQDIuY29tIiwiZXhwIjoxNzE1MjUyMjkzfQ.hsE9vwBVYL-1hfDT8JQMCse-gyVYNv7pLdIqTGuiUbgQSWIHD0HIelxNaSdLA1__");
		HttpEntity<TeamRequest> requestEntity = new HttpEntity<>(
				request,
				httpHeaders
		);

		Map res= restTemplate
				.postForEntity(
						"http://192.168.0.12:8080/api/v1/auth/token"
						, requestEntity
						,Map.class
				).getBody();
		System.out.println(res);
	}
//	eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxQDIuY29tIiwiZXhwIjoxNzE1MjUxMzAyfQ.eJOQAEf5Jam48kUCyw-v44WEvP_kWUQQw3f76riOcspafv-B3C_R7TzBZ1Igvk41

}
