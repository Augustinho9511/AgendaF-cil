package AgendaFacil.controller;

import AgendaFacil.config.JwtUtil;

import AgendaFacil.repository.UsuarioRepository;
import AgendaFacil.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // DTOs (Data Transfer Objects) para requisição e resposta.
    // Usar 'record' é uma forma moderna e concisa de criar essas classes de dados.
    public record LoginRequest(String username, String password) {}
    public record LoginResponse(String token) {}
    public record RegisterRequest(String username, String password) {}

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Verifica se o usuário já existe
        if (usuarioRepository.findByUsername(registerRequest.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Erro: Nome de usuário já está em uso!");
        }

        // Cria um novo usuário
        Usuario newUser = new Usuario();
        newUser.setUsername(registerRequest.username());
        // Criptografa a senha antes de salvar
        newUser.setPassword(passwordEncoder.encode(registerRequest.password()));

        usuarioRepository.save(newUser);

        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );
        } catch (BadCredentialsException e) {
            // Se as credenciais estiverem erradas, lança uma exceção
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }

        // Se a autenticação for bem-sucedida, gera um token JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Retorna o token para o frontend
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}