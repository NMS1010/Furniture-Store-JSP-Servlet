package models.services.category;

import models.entities.Category;
import models.entities.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import view_models.categories.CategoryCreateRequest;
import view_models.categories.CategoryGetPagingRequest;
import view_models.categories.CategoryUpdateRequest;
import view_models.categories.CategoryViewModel;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        Category category = session.find(Category.class, request.getCategoryId());

        category.setCategoryName(request.getName());
        category.setDescription(request.getDescription());
        category.setParentCategoryId(request.getParentCategoryId());
        if(!request.getImage().getSubmittedFileName().equals(""))
            category.setImage(FileUtil.encodeBase64(request.getImage()));

        return HibernateUtils.merge(category);
    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        Category category = session.find(Category.class, entityId);

        Query q2 = session.createQuery("select categoryId from Category where parentCategoryId=:s1");
        q2.setParameter("s1",category.getCategoryId());
        List<Integer> categoryIds = q2.list();

        session.close();
        if(categoryIds.size() > 0){
            return false;
        }
        return HibernateUtils.remove(category);
    }
    private CategoryViewModel getCategoryViewModel(Category category, Session session){
        CategoryViewModel categoryViewModel = new CategoryViewModel();

        categoryViewModel.setCategoryId(category.getCategoryId());
        categoryViewModel.setName(category.getCategoryName());

        String parentCategoryName = "";
        if(category.getParentCategoryId() > 0) {
            Query q1 = session.createQuery("select categoryName from Category where categoryId =:s1");
            q1.setParameter("s1", category.getParentCategoryId());
            parentCategoryName = q1.getSingleResult() == null ? "" : q1.getSingleResult().toString();
        }
        categoryViewModel.setParentCategoryId(category.getParentCategoryId());
        categoryViewModel.setParentCategoryName(parentCategoryName);


        categoryViewModel.setImage(category.getImage());
        categoryViewModel.setDescription(category.getDescription());

        Query q2 = session.createQuery("select sum(quantity) from Product where categoryId=:s1");
        q2.setParameter("s1",category.getCategoryId());
        Object o2 = q2.getSingleResult();
        categoryViewModel.setTotalProduct(o2 == null ? 0 : (long)o2);

        Query q3 = session.createQuery("select sum(o.quantity) from OrderItem o inner join Product p on o.productId = p.productId where p.categoryId =:s1");
        q3.setParameter("s1",category.getCategoryId());
        Object o3 = q3.getSingleResult();
        categoryViewModel.setTotalSell(o3 == null ? 0 : (long)o3);


        Query q4 = session.createQuery("select categoryId from Category where parentCategoryId=:s1");
        q4.setParameter("s1",category.getCategoryId());
        categoryViewModel.setSubCategoryIds(q4.list());

        Query q5 = session.createQuery("select categoryName from Category where parentCategoryId=:s4");
        q5.setParameter("s4",category.getCategoryId());
        categoryViewModel.setSubCategoryNames(q5.list());

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
        String cmd = HibernateUtils.getRetrieveAllQuery("Category", request.getColumnName(), request.getSortBy(), request.getKeyword(), request.getTypeSort());
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

    @Override
    public HashMap<Integer, String> getParentCategory() {
        Session session = HibernateUtils.getSession();
        Query q = session.createQuery("from Category where parentCategoryId = 0");
        List<Category> l = q.list();
        HashMap<Integer, String> parentCategory = new HashMap<>();
        l.forEach(category ->
                parentCategory.put(category.getCategoryId(), category.getCategoryName())
        );

        return parentCategory;
    }
}
