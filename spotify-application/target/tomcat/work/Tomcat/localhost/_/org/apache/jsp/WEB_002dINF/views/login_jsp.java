/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2018-06-03 20:01:58 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("\t<head>\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
      out.write("\t\t<title>Sign In</title>\n");
      out.write("\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"webjars/bootstrap/4.1.1/dist/css/bootstrap.min.css\">\n");
      out.write("\t\t<link type=\"text/css\" rel=\"stylesheet\" href=\"stylesheets/global.css\">\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"webjars/bootstrap/4.1.1/dist/js/bootstrap.min.js\"></script>\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"webjars/jquery/3.1.1/jquery.min.js\"></script>\n");
      out.write("\t\t<script type=\"text/javascript\" src=\"webjars/popper.js/1.14.3/dist/umd/popper.min.js\"></script>\n");
      out.write("\t</head>\n");
      out.write("<body>\n");
      out.write("\t<div class=\"container-fluid bg\">\n");
      out.write("\t\t<div class=\"row\">\n");
      out.write("\t\t\t<div class=\"col-md-4 col-sm-4 col-xs-12\"></div>\n");
      out.write("\t\t\t<div class=\"col-md-4 col-sm-4 col-xs-12\">\n");
      out.write("\t\t\t\t<!-- Form Start -->\n");
      out.write("\t\t\t\t<form class=\"form-container\" action=\"\\login.do\" method=\"post\">\n");
      out.write("\t\t\t\t\t<h1>Sing Up</h1>\n");
      out.write("\t\t\t\t\t<div class=\"form-group\">\n");
      out.write("\t\t\t\t\t\t<label for=\"InputEmail\">Email Address</label>\n");
      out.write("\t\t\t\t\t\t<input type=\"email\" class=\"form-control\" id=\"email\" name=\"userId\" placeholder=\"Email\">\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"form-group\">\n");
      out.write("\t\t\t\t\t\t<label for=\"InputPassword\">Password</label>\n");
      out.write("\t\t\t\t\t\t<input type=\"password\" class=\"form-control\" id=\"password\" name=\"password\" placeholder=\"Password\">\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<button type=\"submit\" class=\"btn btn-success btn-block\">Sign In</button>\n");
      out.write("\t\t\t\t</form>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"col-md-4 col-sm-4 col-xs-12\"></div>\n");
      out.write("\t</div>\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}