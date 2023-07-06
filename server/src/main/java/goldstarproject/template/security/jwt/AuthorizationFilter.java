package goldstarproject.template.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import goldstarproject.template.security.auth.PrincipalDetails;
import goldstarproject.template.member.entity.Member;
import goldstarproject.template.member.repository.MemberRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberRepository memberRepository;

    public AuthorizationFilter(AuthenticationManager authenticationManager,MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request,response);
            return;
        }
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(JwtProperties.TOKEN_PREFIX, "");

        //토큰 검증 (AuthenticationManager 필요 없음 )
        //내가 SecurityContext 에 직접 접근 -> 세션을 만들 때 자동으로 UserDetailsService 에 있는 loadByUsername 이 호출됨
        String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                .getClaim("username").asString();

        if (username != null) {
            Member member = memberRepository.findByUsername(username);

            //인증은 토큰 검증시 끝. 인증을 하기 위함이 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            //아래 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장
            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    principalDetails,  //추후 컨트롤러에서 DI해서 쓸 떄 사용
                    null,  //패스워드는 모르니까 null 처리,
                    principalDetails.getAuthorities());

            //스프링 시큐리티의 세션에 강제 접근
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }
}