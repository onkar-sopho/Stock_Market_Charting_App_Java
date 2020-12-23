package com.onkar.stockMarket.service;

import com.onkar.stockMarket.dao.UserRepository;
import com.onkar.stockMarket.dao.VerificationTokenRepository;
import com.onkar.stockMarket.dto.AuthenticationResponse;
import com.onkar.stockMarket.dto.LoginRequest;
import com.onkar.stockMarket.dto.RefreshTokenRequest;
import com.onkar.stockMarket.dto.SignupRequest;
import com.onkar.stockMarket.exceptions.StockMarketException;
import com.onkar.stockMarket.model.NotificationMail;
import com.onkar.stockMarket.model.User;
import com.onkar.stockMarket.model.VerificationToken;
import com.onkar.stockMarket.security.JwtUtil;
import com.onkar.stockMarket.security.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final RefreshTokenService refreshTokenService;


    public void signup(SignupRequest signupRequest) {
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setMobNo(signupRequest.getMobNo());
        user.setUserType("Normal");
        user.setConfirmed(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        // We pass in the token along with URL,
        // we take the token from the url and find the corresponding user in the DB and then confirm his/her
        // status
        mailService.sendMail(new NotificationMail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Stock Market Charting Account, " +
                "In order to use our services, please activate your account by clicking on the below url: " +
                "http://localhost:8090/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        // fetching the Token object from the DB using the token string in URL (parameter)
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> {
            throw new StockMarketException("Invalid token!");
        });

        // fetching the username from the token
        String username = verificationToken.getUser().getUsername();

        // Searching if this username is present inside DB
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            throw new StockMarketException("Could not find any user with username " + username);
        });

        // Since user found, update the status
        user.setConfirmed(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        // Adding the authenticate object to the Security Context...
        // So if authenticate object is present inside the context, that means the user is logged in, else not
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtil.generateToken(authenticate);
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtUtil.generateTokenWithUsername(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtUtil.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }
}
