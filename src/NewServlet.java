

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Servlet implementation class NewServlet
 */
@WebServlet("/NewServlet")
public class NewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServlet() {
        super();
        
        // TODO Auto-generated constructor stub
    } 
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		response.setContentType("text/html;charset=UTF-8");
        int tamanho = Integer.parseInt(request.getParameter("NumeroVariaveis"));
        double []variaveis = new double [tamanho];

        PrintWriter out = response.getWriter();
        //out.println(""+tamanho);
        
            for(int i = 0; i < tamanho; i++) {
                variaveis [i] = Double.parseDouble(request.getParameter("variaveis" + (i)));
                //out.println(variaveis[i]);
            }
            
            Teste teste = new Teste(variaveis);
            //out.println("oi " + teste.ret);

            double [] val = teste.valor;
            //out.println("val " + val[0]);
            
            String [] nome = teste.name;
            //out.println("nome " + nome[0]);
            
            String [] resp = new String[3];

            for(int i = 1; i <= tamanho; i+=3){
                if(val[i] != 0){
                    resp[0] = nome[i];
                }
                //out.println(val[i]);
                //out.println(nome[i]);
            }


            for(int i = 2; i <= tamanho; i+=3){
                if(val[i] != 0){
                    resp[1] = nome[i];
                }
            }


            for(int i = 3; i <= tamanho; i+=3){
                if(val[i] != 0){
                    resp[2] = nome[i];
                }
            }


            out.println("<body background='overwatch-world-cup-sydney-2017-1.webp' style='background-size: auto auto'>");

            out.println("<div style='background-color: darkgrey; opacity: 0.9; position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); -webkit-transform: translate(-50%, -50%); -moz-transform: translate(-50%, -50%); -o-transform: translate(-50%, -50%); -ms-transform: translate(-50%, -50%); border: solid #ccc; border-radius: 16px; border-width: 3px; height: 100px; width: auto; padding: 10px 10px; margin: auto; font-family: cursive; overflow: auto;'>");
            out.println("<p> O jogador " + resp[0] + " jogará na posição Ataque, o jogador " + resp[1] + " jogará na posição Defesa e o jogador " + resp[2] + " jogará na posição Suporte com um total de " + val[0] + " horas de jogo.</p>");
            //out.println("alguma coisa");
            out.println("</div>");

            out.println("</body>");

        
    }

}
