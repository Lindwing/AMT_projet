package ch.heigvd.amt.projet.shop_els.access;

import ch.heigvd.amt.projet.shop_els.model.Article;
import ch.heigvd.amt.projet.shop_els.util.HibUtil;
import org.hibernate.Session;

import java.util.List;


public class ArticleDao implements Dao<Article>{
    private Session session;

    @Override
    public void save(Article article) {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();


        session.save(article);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Article article) {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();

        // Get object to update
        Article updateArticle = session.get(Article.class, article.getIdArticle());
        updateArticle.setName(article.getName());
        updateArticle.setDescription(article.getDescription());
        updateArticle.setPrice(article.getPrice());
        updateArticle.setImageURL(article.getImageURL());
        updateArticle.setImageURL(article.getImageURL());

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Article get(int id) {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Article article = session.get(Article.class, id);

        session.close();
        return article;
    }

    @Override
    public List<Article> getAll() {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Article> articles = session.getNamedQuery("selectAllArticles").getResultList();

        session.close();
        return articles;
    }

    @Override
    public boolean delete(int id) {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Article article = session.get(Article.class, id);
        session.delete(article);
        List list = session.getNamedQuery("selectArticleId").setParameter("id", id).getResultList();

        session.getTransaction().commit();
        session.close();
        if(list.isEmpty()) return false;
        return true;
    }

    public List getNameFromName(String name) {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List listName = session.getNamedQuery("selectArticleName").setParameter("art", name).getResultList();

        session.close();
        return listName;
    }

    public boolean checkIfNameExists(String name) {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List listName = session.getNamedQuery("selectArticleName").setParameter("art", name).getResultList();

        session.close();
        return listName.isEmpty();
    }

    public List<Object[]> getArticleAndCategoryById(int id) {
        session = HibUtil.getSessionFactory().openSession();
        session.beginTransaction();

        List<Object[]> list = session.getNamedQuery("selectArticleAndCategoryById").setParameter("articleID", id)
                .getResultList();

        session.close();
        return list;
    }
}
