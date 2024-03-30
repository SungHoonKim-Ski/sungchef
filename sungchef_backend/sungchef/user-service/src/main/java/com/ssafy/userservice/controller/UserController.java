package com.ssafy.userservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.userservice.db.entity.User;
import com.ssafy.userservice.dto.request.BookmarkReq;
import com.ssafy.userservice.dto.request.ContactReq;
import com.ssafy.userservice.dto.request.LoginReq;
import com.ssafy.userservice.dto.request.SignUpReq;
import com.ssafy.userservice.dto.request.UserInfoReq;
import com.ssafy.userservice.dto.response.UserBookmarkRecipe;
import com.ssafy.userservice.dto.response.UserBookmarkRecipeRes;
import com.ssafy.userservice.dto.response.UserInfoRes;
import com.ssafy.userservice.dto.response.UserMakeRecipe;
import com.ssafy.userservice.dto.response.UserMakeRecipeRes;
import com.ssafy.userservice.dto.response.UserSimpleInfoRes;
import com.ssafy.userservice.service.BookmarkService;
import com.ssafy.userservice.service.JwtService;
import com.ssafy.userservice.service.ResponseService;
import com.ssafy.userservice.service.SurveyService;
import com.ssafy.userservice.service.UserService;
import com.ssafy.userservice.exception.exception.UserNotFoundException;
import com.ssafy.userservice.exception.exception.UserRecipeNotExistException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final ResponseService responseService;
	private final UserService userService;
	private final BookmarkService bookmarkService;
	private final JwtService jwtService;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@Valid @RequestBody final SignUpReq req) {
		log.debug("/signup : {}", req);
		return  ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				userService.createUser(req)
				, "회원가입 성공")
		);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginReq req) {
		log.debug("/login : {}", req);
		return ResponseEntity.ok()
			.body(
				responseService.getSuccessSingleResult(
					userService.loginUser(req)
					, "로그인 성공")
			);
	}

	@PostMapping("/autologin")
	public ResponseEntity<?> autologin(HttpServletRequest request) {
		log.debug("/autologin called");
		jwtService.getUserSnsId(request);
		return ResponseEntity.ok(responseService.getSuccessMessageResult("자동 로그인 성공"));
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@RequestHeader("Refresh") final String refreshToken) {
		log.debug("/reissue : {}", refreshToken);
		return ResponseEntity.ok()
			.body(
				responseService.getSuccessSingleResult(
					userService.reissue(refreshToken)
					, "토큰 재발급 성공")
			);
	}

	@GetMapping("/exist/{nickname}")
	public ResponseEntity<?> nicknameExist(@PathVariable("nickname") final String nickname) {
		log.debug("/exist/{nickname} : {}", nickname);
		userService.nicknameExist(nickname);
		return ResponseEntity.ok(
			responseService.getSuccessMessageResult("사용 가능한 닉네임")
		);
	}

	/**
	 * 유저가 만든 레시피의 숫자, 북마크한 레시피의 숫자, 유저의 닉네임과 프로필 사진을 반환하는 함수
	 * @return
	 */
	@GetMapping("/simple")
	public ResponseEntity<?> getUserSimpleInfo(HttpServletRequest request) {

		String userSnsId = jwtService.getUserSnsId(request);
		return ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				userService.getUserSimpleInfo(userSnsId)
				, "유저 요약 정보 호출 성공"
			)
		);
	}

	@GetMapping("/recipe/{page}")
	public ResponseEntity<?> getUserRecipe(HttpServletRequest request, @PathVariable("page") final String page) {

		String userSnsId = jwtService.getUserSnsId(request);
		List<Integer> bookmarkRecipe = bookmarkService.getUserBoomMarkRecipeId(userSnsId);
		log.debug("/recipe/{page} : {}", Arrays.toString(bookmarkRecipe.toArray()));

		List<UserMakeRecipe> makeRecipeList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			makeRecipeList.add(UserMakeRecipe.builder()
				.makeRecipeCreateDate("2024-03-1" + i)
				.makeRecipeImage(
					"https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
				.makeRecipeReview("고양이가 귀여워요")
				.build());
		}
		try {
			log.debug("/recipe/{page} : {}", page);

			return ResponseEntity.ok().body(
				responseService.getSuccessSingleResult(
					UserMakeRecipeRes.builder()
						.makeRecipeCount(9)
						.makeRecipeList(makeRecipeList)
						.build()
					, "유저가 만든 음식 정보 호출 성공"
				)

			);
		} catch (UserRecipeNotExistException e) {
			return responseService.NO_CONTENT();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("/bookmark/{page}")
	public ResponseEntity<?> userRecipe(HttpServletRequest request, @PathVariable("page") final String page) {

		String userSnsId = jwtService.getUserSnsId(request);

		List<UserBookmarkRecipe> bookmarkRecipeList = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			bookmarkRecipeList.add(UserBookmarkRecipe.builder()
				.recipeId(i)
				.recipeImage(
					"https://flexible.img.hani.co.kr/flexible/normal/970/777/imgdb/resize/2019/0926/00501881_20190926.JPG")
				.build());
		}
		try {
			log.debug("/bookmark/{page} : {}", page);

			return ResponseEntity.ok().body(
				responseService.getSuccessSingleResult(
					UserBookmarkRecipeRes.builder()
						.bookmarkRecipeCount(9)
						.bookmarkRecipeList(bookmarkRecipeList)
						.build()
					, "유저가 즐겨찾기한 음식 정보 호출 성공"
				)

			);
		} catch (UserRecipeNotExistException e) {
			return responseService.NO_CONTENT();
		} catch (UserNotFoundException e) {
			return responseService.BAD_REQUEST();
		} catch (Exception e) {
			return responseService.INTERNAL_SERVER_ERROR();
		}
	}

	@GetMapping("")
	public ResponseEntity<?> userInfo(HttpServletRequest request) {

		String userSnsId = jwtService.getUserSnsId(request);
		log.debug("/user userSnsId: {}", userSnsId);
		User user = userService.getUserBySnsId(userSnsId);
		return ResponseEntity.ok().body(
			responseService.getSuccessSingleResult(
				UserInfoRes.builder()
					.userBirthdate(user.getUserBirthDate())
					.userNickName(user.getUserNickname())
					.userGender(user.getUserGenderType())
					.userImage(user.getUserImage())
					.build()
				, "유저 정보 호출 성공"
			)
		);
	}

	@PutMapping(value = "", consumes = {"multipart/form-data"})
	public ResponseEntity<?> updateUser(
		HttpServletRequest request
		, @ModelAttribute("userImage") final UserInfoReq req
	)
	{
		String userSnsId = jwtService.getUserSnsId(request);
		userService.updateUser(userSnsId, req);
		return ResponseEntity.ok().body(
			responseService.getSuccessMessageResult("유저 정보 변경 성공")
		);
	}

	@PostMapping("/contact")
	public ResponseEntity<?> contact(@RequestBody final ContactReq req) {
		// TODO
		log.debug("/contact : {}", req);
		return ResponseEntity.ok(
			responseService.getSuccessMessageResult("문의 완료")
		);
	}

	@PostMapping("/bookmark")
	public ResponseEntity<?> bookmark(HttpServletRequest request, @RequestBody BookmarkReq req) {
		log.debug("/bookmark : {}", req);
		String userSnsId = jwtService.getUserSnsId(request);
		bookmarkService.bookmarkRecipe(userSnsId, req);
		return ResponseEntity.ok(responseService.getSuccessMessageResult("북마크 성공"));
	}
}
