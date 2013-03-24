package org.shirdrn.tinyframework.server.jetty;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class EchoServlet extends HttpServlet {
  @SuppressWarnings("unchecked")
  @Override
  public void doGet(HttpServletRequest request, 
                    HttpServletResponse response
                    ) throws ServletException, IOException {
    PrintStream out = new PrintStream(response.getOutputStream());
    SortedSet<String> sortedKeys = new TreeSet<String>();
    Enumeration<String> keys = request.getParameterNames();
    while(keys.hasMoreElements()) {
      sortedKeys.add(keys.nextElement());
    }
    for(String key: sortedKeys) {
      out.print(key);
      out.print(':');
      out.print(request.getParameter(key));
      out.print('\n');
    }
    out.close();
  }    
}
