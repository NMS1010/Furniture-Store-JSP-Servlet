package models.services.category;

import models.entities.Category;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import view_models.categories.CategoryCreateRequest;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryUpdateRequest;
import view_models.categories.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService{
    private static CategoryService instance = null;
    public static CategoryService getInstance(){
        if(instance == null)
            instance = new CategoryService();
        return instance;
    }
    @Override
    public int insert(CategoryCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        Category category = new Category();

        category.setCategoryName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategoryId(request.getParentCategoryId());
        category.setImage(FileUtil.encodeBase64(request.getImage()));

        int categoryId = -1;
        try {
            tx = session.beginTransaction();
            session.persist(category);
            categoryId = category.getCategoryId();

            tx.commit();
        }catch(Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return categoryId;
    }

    @Override
    public boolean update(CategoryUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;
        Category category = session.find(Category.class, request.getCategoryId());

        category.setCategoryName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategoryId(request.getParentCategoryId());
        category.setImage(FileUtil.encodeBase64(request.getImage()));


        return HibernateUtils.merge(category);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Category category = session.find(Category.class, entityId);
        String cmd = "update Product set categoryId = null where categoryId =:s1";
        Query q = session.createQuery(cmd);
        q.setParameter("s1", category.getCategoryId());
        q.executeUpdate();
        return HibernateUtils.remove(category);
    }
    private CategoryViewModel getCategoryViewModel(Category category, Session session){
        CategoryViewModel categoryViewModel = new CategoryViewModel();

        categoryViewModel.setCategoryId(category.getCategoryId());
        categoryViewModel.setName(categoryViewModel.getName());
        categoryViewModel.setParentCategoryId(category.getParentCategoryId());

        Query q3 = session.createQuery("select categoryName from Category where categoryId =:s1" );
        q3.setParameter("s1", category.getParentCategoryId());
        String parentCategoryName = q3.getSingleResult().toString();
        categoryViewModel.setParentCategoryName(parentCategoryName);
        categoryViewModel.setImage(category.getImage());
        categoryViewModel.setDescription(category.getDescription());

        return categoryViewModel;
    }
    @Override
    public CategoryViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Category category = session.find(Category.class, entityId);

        CategoryViewModel categoryViewModel = getCategoryViewModel(category, session);
        session.close();

        return categoryViewModel;
    }

    @Override
    public ArrayList<CategoryViewModel> retrieveAll(CategoryGetPagingRequest request) {
        ArrayList<CategoryViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();
        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("Category", request.getColumnName(), request.getKeyword(), request.getTypeSort());
        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<Category> categories = q.list();

        for(Category category:categories){
            CategoryViewModel v = getCategoryViewModel(category, session);
            list.add(v);
        }
        session.close();
        return list;
    }
}
