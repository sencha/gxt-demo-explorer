package com.sencha.gxt.examples.resources.server;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class LatestIEFilter implements Filter {
  @Override
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
    ((HttpServletResponse)resp).setHeader("X-UA-Compatible", "IE=edge");
    chain.doFilter(req, resp);
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {
    // no-op
  }
  @Override
  public void destroy() {
    // no-op
  }

}
