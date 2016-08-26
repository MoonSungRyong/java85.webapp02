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

import bit.dao.MemberDao;
import bit.vo.Member;


@WebServlet("/member/memberadd.do")
public class MemberAddServlet extends GenericServlet {
  private static final long serialVersionUID = 1L;

  MemberDao memberDao;

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config); //원래 GenericServlet 만든 메서드를 그대로 실행한다.
    
    // 모든 서블릿이 공유하는 ServletContext 창고를 알아낸다.
    ServletContext servletContext = config.getServletContext();
    
    // 창고에 보관된 Spring IoC 컨테이너를 꺼낸다.
    ApplicationContext applicationContext = 
        (ApplicationContext)servletContext.getAttribute("applicationContext");
    
    // Spring IoC 컨테이너에서 BoardDao 구현체를 꺼낸다.
    memberDao = applicationContext.getBean(MemberDao.class);
  }
  @Override
  public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
    // 클라이언트가 보내는 데이터는 UTF-8로 되어 있으니,
    // Unicode로 잘 변환하여 리턴하라!
    request.setCharacterEncoding("UTF-8");
    Member member = new Member();
    member.setMNM(request.getParameter("MNM"));
    member.setTEL(request.getParameter("tel"));
    member.setEmail(request.getParameter("email"));
    member.setPWD(request.getParameter("PWD"));
    
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    
    out.println("<html>");
    out.println("<head>");
    out.println("<title>멤버 등록하기</title>");
    // HTML 페이지에 Refresh 폭탄 심기!
    // HTML 페이지를 완전히 출력한 후 지정된 시간이 경과하면 특정 URL을 자동으로 요청하게 만든다
    out.println("<meta http-equiv='Refresh' content='1;url=list.do'>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 등록</h1>");
    
    try {
      memberDao.insert(member);
      out.println("등록 성공입니다.!");
      
    } catch (Exception e) {
      out.println("데이터 처리 오류입니다.!");
      e.printStackTrace();
    }
    out.println("</body>");
    out.println("</html>");
    
  }
}


