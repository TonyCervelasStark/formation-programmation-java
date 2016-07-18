package fr.lteconsulting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartesServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		String nomUtilisateur = (String) request.getSession().getAttribute( "nom" );
		if( nomUtilisateur == null )
		{
			response.sendRedirect( "home" );
			return;
		}

		Rendu.listeCartes( ApplicationData.getCartes(), getServletContext(), request, response );
	}
}