import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet("/mul")
public class Multiplication extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArithmeticAction arithmeticAction = new ArithmeticAction(req, resp, Action.MULTIPLICATION);
        arithmeticAction.doAction();
    }
}
