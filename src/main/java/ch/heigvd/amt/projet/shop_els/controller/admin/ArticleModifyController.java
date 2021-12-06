package ch.heigvd.amt.projet.shop_els.controller.admin;

import ch.heigvd.amt.projet.shop_els.access.ArticleCategoryDao;
import ch.heigvd.amt.projet.shop_els.access.ArticleDao;
import ch.heigvd.amt.projet.shop_els.access.CategoryDao;
import ch.heigvd.amt.projet.shop_els.model.Article;
import ch.heigvd.amt.projet.shop_els.model.Article_Category;
import ch.heigvd.amt.projet.shop_els.model.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/articleModify")
public class ArticleModifyController extends HttpServlet{
    private final ArticleDao articleDao = new ArticleDao();
    private final CategoryDao categoryDao = new CategoryDao();
    private final ArticleCategoryDao articleCategoryDao = new ArticleCategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        int id = Integer.parseInt(request.getParameter("id"));
        Article article = articleDao.get(id);

        //TODO, il faut récupérer la liste des éléments catégories deja set et envoyer à la vue
        List<Category> categories = categoryDao.getAll();
        List<String> categoriesArticle = articleCategoryDao.getCategoriesNameByArticleId(id);

        request.setAttribute("article", article);
        request.setAttribute("categories", categories);
        request.setAttribute("categoriesArticle", categoriesArticle);

        request.getRequestDispatcher("/WEB-INF/view/admin/articleModify.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String[] categories = request.getParameterValues("categories");
        int id = Integer.parseInt(request.getParameter("id"));

        List<Integer> categoriesOldConf = articleCategoryDao.getCategoriesIdByArticleId(id);
        List<Integer> categoriesNew = new ArrayList<>();
        // Parse id string to integer
        for(String category : categories) {
            categoriesNew.add(Integer.parseInt(category));
        }

        // If there is no change, we do nothing
        if (categoriesOldConf.equals(categoriesNew)) {
            response.sendRedirect("/shop/admin/articles");
        }
        else {
            for(int idCategory : categoriesNew) {
                if (!categoriesOldConf.contains(idCategory)) {
                    //on ajoute :D
                    Category category = categoryDao.get(idCategory);
                    Article article = articleDao.get(id);
                    Article_Category ac = new Article_Category();
                    ac.setCategory(category);
                    ac.setArticle(article);
                    articleCategoryDao.save(ac);
                }
            }
            for(int idCategory : categoriesOldConf){
                if(!categoriesNew.contains(idCategory)){
                    //ON DELETE
                    //ajout méthode get article_category id par rapport à idcategory et idarticle
                }
                // autre boucle qui détecte si OldConf est > à ca
                // => oe c'est ce que jai mis la hahah enfin qui check son contenu avec new
                //categoriesNew => si c'est le cas faut delete

                //t'arrête pas <3
                // LOL hihi bisous <3
            }
        }

        //categoryList.add(category);

        //response.sendRedirect("/shop/admin/articles");
    }
}
