package org.shirdrn.tinyframework.server.jetty;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * A Servlet input filter that quotes all HTML active characters in the
 * parameter names and values. The goal is to quote the characters to make
 * all of the servlets resistant to cross-site scripting attacks.
 */
public class QuotingInputFilter implements Filter {

    public static class RequestQuoter extends HttpServletRequestWrapper {
      private final HttpServletRequest rawRequest;
      public RequestQuoter(HttpServletRequest rawRequest) {
        super(rawRequest);
        this.rawRequest = rawRequest;
      }
      
      /**
       * Return the set of parameter names, quoting each name.
       */
      @SuppressWarnings("unchecked")
      @Override
      public Enumeration<String> getParameterNames() {
        return new Enumeration<String>() {
          private Enumeration<String> rawIterator = 
            rawRequest.getParameterNames();
          @Override
          public boolean hasMoreElements() {
            return rawIterator.hasMoreElements();
          }

          @Override
          public String nextElement() {
            return HtmlQuoting.quoteHtmlChars(rawIterator.nextElement());
          }
        };
      }
      
      /**
       * Unquote the name and quote the value.
       */
      @Override
      public String getParameter(String name) {
        return HtmlQuoting.quoteHtmlChars(rawRequest.getParameter
                                     (HtmlQuoting.unquoteHtmlChars(name)));
      }
      
      @Override
      public String[] getParameterValues(String name) {
        String unquoteName = HtmlQuoting.unquoteHtmlChars(name);
        String[] unquoteValue = rawRequest.getParameterValues(unquoteName);
        String[] result = new String[unquoteValue.length];
        for(int i=0; i < result.length; ++i) {
          result[i] = HtmlQuoting.quoteHtmlChars(unquoteValue[i]);
        }
        return result;
      }

      @SuppressWarnings("unchecked")
      @Override
      public Map<String, String[]> getParameterMap() {
        Map<String, String[]> result = new HashMap<String,String[]>();
        Map<String, String[]> raw = rawRequest.getParameterMap();
        for (Map.Entry<String,String[]> item: raw.entrySet()) {
          String[] rawValue = item.getValue();
          String[] cookedValue = new String[rawValue.length];
          for(int i=0; i< rawValue.length; ++i) {
            cookedValue[i] = HtmlQuoting.quoteHtmlChars(rawValue[i]);
          }
          result.put(HtmlQuoting.quoteHtmlChars(item.getKey()), cookedValue);
        }
        return result;
      }
      
      /**
       * Quote the url so that users specifying the HOST HTTP header
       * can't inject attacks.
       */
      @Override
      public StringBuffer getRequestURL(){
        String url = rawRequest.getRequestURL().toString();
        return new StringBuffer(HtmlQuoting.quoteHtmlChars(url));
      }
      
      /**
       * Quote the server name so that users specifying the HOST HTTP header
       * can't inject attacks.
       */
      @Override
      public String getServerName() {
        return HtmlQuoting.quoteHtmlChars(rawRequest.getServerName());
      }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, 
                         ServletResponse response,
                         FilterChain chain
                         ) throws IOException, ServletException {
      HttpServletRequestWrapper quoted = 
        new RequestQuoter((HttpServletRequest) request);
      final HttpServletResponse httpResponse = (HttpServletResponse) response;
      // set the default to UTF-8 so that we don't need to worry about IE7
      // choosing to interpret the special characters as UTF-7
      httpResponse.setContentType("text/html;charset=utf-8");
      chain.doFilter(quoted, response);
    }

  }
