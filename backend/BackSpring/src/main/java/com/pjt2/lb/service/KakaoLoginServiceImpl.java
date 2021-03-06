package com.pjt2.lb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt2.lb.request.KakaoOAuthToken;
import com.pjt2.lb.request.KakaoProfile;

@Service
public class KakaoLoginServiceImpl implements KakaoLoginService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	@Value("${kakao.oauth2-id}")
	private String kakaoOauth2ClinetId;
	
	private final String frontendRedirectUrl = "https://j5A502.p.ssafy.io";

	@Override
	public KakaoOAuthToken getKakaoTokenApi(String code) {

		KakaoOAuthToken kakaoOAuthToken = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", kakaoOauth2ClinetId);
		params.add("redirect_uri", frontendRedirectUrl + "/joinsocial");
		params.add("code", code);

		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		String url = "https://kauth.kakao.com/oauth/token";

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, kakaoTokenRequest, String.class);

		try {
			kakaoOAuthToken = objectMapper.readValue(response.getBody(), KakaoOAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return kakaoOAuthToken;
	}

	@Override
	public KakaoProfile getUserByAccessToken(String accessToken) {
		
		KakaoProfile kakaoProfile = null;
		
		// HttpHeader ?????? ??????
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + accessToken);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		// HttpHeader??? HttpBody??? ????????? ????????? ?????????.
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(params, headers);

		String url = "https://kapi.kakao.com/v2/user/me";

		// POST ???????????? Http ??????
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, kakaoProfileRequest, String.class);
//		System.out.println(response);
		try {
			kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return kakaoProfile;


	}

}
