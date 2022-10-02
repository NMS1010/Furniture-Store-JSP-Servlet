package models.services.product_images;

import models.entities.ProductImage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.FileUtil;
import utils.HibernateUtils;
import view_models.product_images.ProductImageCreateRequest;
import view_models.product_images.ProductImageGetPagingRequest;
import view_models.product_images.ProductImageUpdateRequest;
import view_models.product_images.ProductImageViewModel;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class ProductImageService implements  IProductImageService{
    private static ProductImageService instance = null;
    public ProductImageService(){

    }
    public static ProductImageService getInstance(){
        if(instance == null){
            instance = new ProductImageService();
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
                ProductImage productImage = new ProductImage();

                productImage.setProductId(request.getProductId());
                productImage.setDefault(false);
                productImage.setImage(FileUtil.encodeBase64(f));

                session.persist(productImage);
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
        ProductImage img = session.find(ProductImage.class, request.productImageId);

        img.setImage(FileUtil.encodeBase64(request.getImage()));
        return HibernateUtils.merge(img);

    }

    @Override
    public boolean delete(Integer entityId) {
        Session session = HibernateUtils.getSession();
        ProductImage img = session.find(ProductImage.class, entityId);
        return HibernateUtils.remove(img);
    }
    private ProductImageViewModel getProductImageViewModel(ProductImage productImage){
        ProductImageViewModel productImageViewModel = new ProductImageViewModel();

        productImageViewModel.setId(productImage.getId());
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
        String cmd = HibernateUtils.getRetrieveAllQuery("ProductImage", request.getColumnName(),request.getSortBy(), request.getKeyword(), request.getTypeSort());

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
