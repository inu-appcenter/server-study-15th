package com.example.todo.service;

import com.example.todo.common.exception.token.TokenNotFoundException;
import com.example.todo.common.jwt.JwtProvider;
import com.example.todo.domain.Token;
import com.example.todo.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;
    final private int MONTH_TO_MINUTES = 43200;
    final private int ACCESS_TOKEN_EXPIRE_TIME = 30;

    /**
     * 엑세스 토큰 재발급 로직
     * @param refreshToken
     * @return accessToken
     */
    public String refreshAcessToken(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(TokenNotFoundException::new);
        String newAcessToken = jwtProvider.makeJwtToken(token.getUserId(), ACCESS_TOKEN_EXPIRE_TIME);
        return newAcessToken;
    }

    /**
     * 리프레쉬 토큰 갱신 로직
     * @param  refreshToken
     * @return refreshToken
     */
    public String updateRefreshToken(String refreshToken) {
        Token token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(TokenNotFoundException::new);
        String newRefreshToken = jwtProvider.makeJwtToken(token.getUserId(), MONTH_TO_MINUTES);
        token.updateRefreshToken(newRefreshToken);
        tokenRepository.save(token);
        return newRefreshToken;
    }

    /**
     * 새로 로그인 accessToken 발급
     * @param userId
     * @return
     */
    public String accessTokenGenerate(Long userId) {
        String newAccessToken = jwtProvider.makeJwtToken(userId, ACCESS_TOKEN_EXPIRE_TIME);
        return newAccessToken;
    }

    /**
     * 새로 로그인 refreshToken 발급
     * @param userId
     * @return
     */
    public String refreshTokenGenerate(Long userId) {
        String newRefreshToken = jwtProvider.makeJwtToken(userId, MONTH_TO_MINUTES);
        Token token = new Token(newRefreshToken, userId);
        tokenRepository.save(token);
        return newRefreshToken;
    }
}
