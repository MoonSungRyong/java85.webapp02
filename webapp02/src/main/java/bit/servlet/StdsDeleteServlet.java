package bit.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import org.springframework.context.ApplicationContext;

import example.dao.StdsDao;

@WebServlet("/stds/delete.do")
public class StdsDeleteServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  StdsDao stdsDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config); // 원래 GenericServlet 만든 메서드를 그대로 실행한다.
    ServletContext servletContext = config.getServletContext();
    ApplicationContext applicationContext = (ApplicationContext) servletContext.getAttribute("applicationContext");
    stdsDao = applicationContext.getBean(StdsDao.class);
  }

  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    int no = Integer.parseInt(request.getParameter("sno"));

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<html>");
    out.println("<head>");
    out.println("<title>게시물 삭제</title>");

    // HTML 페이지에 Refresh 폭탄 심기!
    // => HTML 페이지를 완전히 출력한 후 지정된 시간이 경과하면 특정 URL을 자동으로 요청하게 만든다.
    out.println("<meta http-equiv='Refresh' content='1;url=list.do'>");

    out.println("</head>");
    out.println("<body>");
    out.println("<h1>게시물 삭제</h1>");

    try {
      int count = stdsDao.delete(no);
      if (count == 0) {
        out.println("해당 번호의 게시물이 없습니다.");
      } else {
        out.println("삭제 성공입니다.");
        out.println("<p><a href='list.do'>리스트로 이동</a><p>");
      }
    } catch (Exception e) {
      out.println("데이터 목록 조회 오류입니다!");
      e.printStackTrace();
    }

    out.println("</body>");
    out.println("</html>");

  }

}
