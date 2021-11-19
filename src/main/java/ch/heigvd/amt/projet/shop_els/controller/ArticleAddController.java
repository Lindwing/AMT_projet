package ch.heigvd.amt.projet.shop_els.controller;

import ch.heigvd.amt.projet.shop_els.model.Category;
import ch.heigvd.amt.projet.shop_els.util.HibUtil;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/articleAdd")
public class ArticleAddController extends HttpServlet {
    private Session session;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.getNamedQuery("selectAllCategory");
        List<Category> results = query.getResultList();
        session.close();

        request.setAttribute("categories", results);
        request.getRequestDispatcher("/WEB-INF/view/admin/articleAdd.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");

        String name = (String) request.getParameter("name");
        String description = (String) request.getParameter("description");
        String[] categories = request.getParameterValues("categories");
        String price = request.getParameter("price");
        String imageURL = (String)  request.getParameter("imageURL");
        String stock = request.getParameter("stock");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "Article: " + "</h1>");
        out.println("<h2>" + "name " + name + "</h2>");
        out.println("<h2>" + "description " + description + "</h2>");
        out.println("<h2>" + "price " + price + "</h2>");
        out.println("<h2>" + "imageURL " + imageURL + "</h2>");
        out.println("<h2>" + "stock " + stock + "</h2>");
        out.println("</body></html>");

        // TODO:
        //  possible d'attribuer 1 ou plusieurs catégories à un article
        //      (impossible d'avoir des doublons dans les catégories attribuées à un article)
        //  cas simple : nom, une description, prix, visuel => insertion ok
        //  sans visuel : nom, description, prix mais aucune image => insertion ok avec image par défaut
        //  préannonce : nom, description, pas de prix (possible image par obligatoire) => ok mais impossible dans panier
        //  stock : doit être >= à 0
        //  Si on a pas : nom, description => impossible d'ajouter un article
        //  Si le nom doit être unique => s'il existe deja, impossible de créer et affiche l'article existant (info)

        /*
        session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("INSERT INTO Article ('name', 'description', 'price', 'imageURL', 'stock' ) VALUES (:valor1,:valor2)");
        session = HibUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("INSERT INTO Article ('name', 'description', 'price', 'imageURL', 'stock' ) VALUES (:name,:valor2)");
        query.setParameter("name",name );
        query.setParameter("description", description);
        query.setParameter("price", price);
        query.setParameter("imageURL", imageURL);
        query.setParameter("stock", stock);
        query.executeUpdate();

        /*Query query = session.getNamedQuery("selectAllArticles");
        List<Object[]> results = query.getResultList();
        session.close();
*/
        //request.setAttribute("articles", results);

        //request.getRequestDispatcher("/WEB-INF/view/admin/articles.jsp").forward(request, response);
    }
}
