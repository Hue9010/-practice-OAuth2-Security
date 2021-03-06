package practice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import practice.UnAuthenticationException;
import practice.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MemberAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(MemberAcceptanceTest.class);

	@Autowired
	private TestRestTemplate template;

	@Resource(name = "memberRepository")
	private MemberRepository memberRepository;

	@Test
	public void loginForm() {
		ResponseEntity<String> response = template.getForEntity("/users/login", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void createForm() {
		ResponseEntity<String> response = template.getForEntity("/users/create", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void 가입_성공() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = request(headers(), userParam("id1", "password", "testUser@email.com"));
		ResponseEntity<String> response = createTemplate("/users/create", request, String.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
	}

	@Test
	public void 로그인_성공() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = request(headers(), userParam("id1", "password", ""));
		ResponseEntity<String> response = createTemplate("/users/login", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().contains("/;jsessionid="));
	}
	
	public void 로그인_실패() {
		HttpEntity<MultiValueMap<String, Object>> request = request(headers(), userParam("id1", "password22", ""));
		ResponseEntity<String> response = createTemplate("/users/login", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("틀렸습니다"));
		fail();
	}
	
	private ResponseEntity<String> createTemplate(String url, HttpEntity<MultiValueMap<String, Object>> request, Class clazz) {
		ResponseEntity<String> response = template.postForEntity(url, request, clazz);
		return response;
	}

	private HttpEntity<MultiValueMap<String, Object>> request(HttpHeaders headers,
			MultiValueMap<String, Object> params) {
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params,
				headers);
		return request;
	}

	private HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}

	private MultiValueMap<String, Object> userParam(String memberId, String password, String email) {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("memberId", memberId);
		params.add("password", password);
		params.add("email", email);
		return params;
	}

}
