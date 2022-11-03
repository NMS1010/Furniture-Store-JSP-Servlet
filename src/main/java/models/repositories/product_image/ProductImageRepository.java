package models.repositories.product_image;

import models.entities.ProductImage;
import models.view_models.product_images.ProductImageCreateRequest;
import models.view_models.product_images.ProductImageGetPagingRequest;
import models.view_models.product_images.ProductImageUpdateRequest;
import models.view_models.product_images.ProductImageViewModel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class ProductImageRepository implements IProductImageRepository{
    private static ProductImageRepository instance = null;
    public static ProductImageRepository getInstance(){
        if(instance == null){
            instance = new ProductImageRepository();
        }
        return instance;
    }
    @Override
    public int insert(ProductImageCreateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        List<Part> files = request.getImages();
        try {
            tx = session.beginTransaction();
            for(Part f: files){
                if(f != null && !f.getSubmittedFileName().equals("")){
                    ProductImage productImage = new ProductImage();

                    productImage.setProductId(request.getProductId());
                    productImage.setDefault(false);
                    productImage.setImage(FileUtil.encodeBase64(f));

                    session.persist(productImage);
                }
            }
            tx.commit();
        }
        catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return -1;
        }
        return 1;
    }

    @Override
    public boolean update(ProductImageUpdateRequest request) {
        Session session = HibernateUtils.getSession();
        Transaction tx = null;

        session.close();
        try {
            tx = session.beginTransaction();
            request.getProductImages().forEach((id, f) -> {
                ProductImage productImage = session.find(ProductImage.class, id);
                productImage.setDefault(false);
                if(f!= null && !f.getSubmittedFileName().equals(""))
                    productImage.setImage(FileUtil.encodeBase64(f));

                session.merge(productImage);
            });
            tx.commit();
        }
        catch (Exception e){
            if(tx != null)
                tx.rollback();
            e.printStackTrace();
            session.close();
            return false;
        }
        return true;

    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ProductImage img = session.find(ProductImage.class, entityId);
        session.close();
        return HibernateUtils.remove(img);
    }
    private ProductImageViewModel getProductImageViewModel(ProductImage productImage){
        ProductImageViewModel productImageViewModel = new ProductImageViewModel();

        productImageViewModel.setId(productImage.getProductImageId());
        productImageViewModel.setDefault(productImage.getDefault());
        productImageViewModel.setImage(productImage.getImage());
        productImageViewModel.setProductId(productImage.getProductId());

        return productImageViewModel;
    }
    @Override
    public ProductImageViewModel retrieveById(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ProductImage productImage = session.find(ProductImage.class, entityId);

        ProductImageViewModel productImageViewModel = getProductImageViewModel(productImage);
        session.close();
        return productImageViewModel;
    }

    @Override
    public ArrayList<ProductImageViewModel> retrieveAll(ProductImageGetPagingRequest request) {
        ArrayList<ProductImageViewModel> list = new ArrayList<>();
        Session session = HibernateUtils.getSession();

        int offset = (request.getPageIndex() - 1)*request.getPageSize();
        String cmd = HibernateUtils.getRetrieveAllQuery("ProductImage", request);

        Query q = session.createQuery(cmd);
        q.setFirstResult(offset);
        q.setMaxResults(request.getPageSize());
        List<ProductImage> productImages = q.list();

        for(ProductImage productImg:productImages){
            ProductImageViewModel v = getProductImageViewModel(productImg);
            list.add(v);
        }
        session.close();
        return list;
    }
}
