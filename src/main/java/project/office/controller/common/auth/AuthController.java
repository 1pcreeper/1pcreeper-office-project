package project.office.controller.common.auth;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import project.office.mapper.AppUserMapper;
import project.office.model.dto.base.response.APIBaseResponseDTO;
import project.office.model.dto.objects.AppUserDTO;
import project.office.model.dto.request.LoginRequestDTO;
import project.office.model.dto.response.LoginResponseDTO;
import project.office.model.entity.AppUser;
import project.office.service.auth.AppUserDetailsService;
import project.office.service.common.AppUserService;
import project.office.service.manager.AppUserManagerService;
import project.office.util.JwtUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserDetailsService appUserDetailsService;
    private final AppUserService appUserService;
    private final AppUserManagerService appUserManagerService;
    private final AppUserMapper appUserMapper;

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<APIBaseResponseDTO<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO dto) {
        // Create authentication token using the submitted username and password
        AppUserDTO user = appUserService.findByName(dto.getName());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        user.getId(),
                        dto.getPassword()
                );

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // If authentication is successful, authentication object will not be null and isAuthenticated will be true
        if (!authentication.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // Retrieve principal (UserDetails)
        AppUser appUser = (AppUser) authentication.getPrincipal();

        // Generate JWT token
        String token = jwtUtil.generateToken(appUser);

        // Update last login time
        //appUserDetailsService.updateLastLogin(dto.getEmail());

        // Return the token
        return ResponseEntity.accepted().body(
                APIBaseResponseDTO.success(LoginResponseDTO.builder()
                        .accessToken(token)
                        .build()));
    }
    @GetMapping("/verify")
    @PermitAll
    public ResponseEntity<APIBaseResponseDTO<Object>> verify(@RequestHeader("Authorization") String authorizationHeader){
        String token;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
        }else {
            throw new BadCredentialsException("Bearer Token Format Invalid");
        }
        if( jwtUtil.validateToken(token)){
            return ResponseEntity.ok(
                    APIBaseResponseDTO.success(null)
            );
        }
        return ResponseEntity.ok(
                APIBaseResponseDTO.error(null)
        );
    }

}
