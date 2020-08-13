package com.ufpb.crdb.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ufpb.crdb.services.JWTService;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class TokenFilter extends GenericFilterBean {
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;

    String header = req.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inexistente ou mal formatado.");
      return;
    }

    String token = header.substring(7);

    try {
      Jwts.parser().setSigningKey(JWTService.TOKEN_SECRET).parseClaimsJws(token).getBody();
    } catch (SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException
      | UnsupportedJwtException | IllegalArgumentException ex) {
      
      ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
			return;
    }

    chain.doFilter(request, response);
  }
}
